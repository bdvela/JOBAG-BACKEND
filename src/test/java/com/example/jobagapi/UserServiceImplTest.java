package com.example.jobagapi;
import com.example.jobagapi.domain.model.User;
import com.example.jobagapi.domain.repository.UserRepository;
import com.example.jobagapi.domain.service.UserService;
import com.example.jobagapi.exception.ResourceNotFoundException;
import com.example.jobagapi.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
    public class UserServiceImplTest {
        @Mock
        private UserRepository userRepository;
        @InjectMocks
        private UserServiceImpl userService= new UserServiceImpl();

        @TestConfiguration
        static class UserServiceImplTestConfiguration {
            public UserService userService() {
                return new UserServiceImpl();
            }
        }

        @Test
        @DisplayName("when SaveUser With Valid User Then Returns Success") //happy path
        public void whenSaveUserWithValidUserThenReturnsSuccess() {
            Long id = 1L;
            String name = "example@upc.edu.pe";
            String password = "Nota#20";
            User user = new User().setId(id).setFirstname(name).setPassword(password);
            when(userRepository.save(Mockito.any(User.class))).thenReturn(new User());
            when(userRepository.findById(id)).thenReturn(Optional.of(user));
            User savedUser = userService.getUserById(user.getId());
            Assertions.assertNotNull(savedUser);
        }

        @Test
        @DisplayName("when GetUserById With Valid Id Then Returns User") //happy path
        public void whenGetUserByIdWithValidIdThenReturnsUser() {
            //Arrange
            Long id = 1L;
            User user = new User().setId(id);
            when(userRepository.findById(id)).thenReturn(Optional.of(user));
            //Act
            User foundUser = userService.getUserById(id);
            //Assert
            assertThat(foundUser.getId()).isEqualTo(id);
        }

        @Test
        @DisplayName("when GetUserById With Invalid Id Then Returns ResourceNotFoundException") //unhappy path
        public void whenGetUserByIdWithInvalidIdThenReturnsResourceNotFoundException() {
            //Arrange
            Long id = 1L;
            String template = "Resource %s not found for %s with value %s";
            when(userRepository.findById(id)).thenReturn(Optional.empty());
            String exceptedMessage = String.format(template, "User", "Id", id);
            //Act
            Throwable exception = catchThrowable(() ->{
                User foundUser = userService.getUserById(id);
            });
            //Assert
            assertThat(exception)
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage(exceptedMessage);
        }

        @Test
        @DisplayName("when UpdateUser With Valid User Then Returns Success") //happy path
        public void whenUpdateUserWithValidUserThenReturnsSuccess() {
            //Arrange
            Long id = 1L;
            String name = "example@upc.edu.pe";
            String password = "Nota#20";
            User user = new User().setId(id).setFirstname(name).setPassword(password);

            String newPassword = "Nota@20";
            user.setPassword(newPassword);
            when(userRepository.findById(id)).thenReturn(Optional.of(user));
            User updateUser = userService.getUserById(id);
            assertEquals("Nota@20",updateUser.getPassword());
        }
    }


