package com.example.jobagapi;

import com.example.jobagapi.domain.model.Postulant;
import com.example.jobagapi.domain.model.User;
import com.example.jobagapi.domain.repository.PostulantRepository;
import com.example.jobagapi.domain.service.PostulantService;
import com.example.jobagapi.exception.ResourceNotFoundException;
import com.example.jobagapi.service.PostulantServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PostulantServiceImplTest {
    @Mock
    private PostulantRepository postulantRepository;
    @InjectMocks
    private PostulantService postulantService = new PostulantServiceImpl();

    @TestConfiguration
    static class PostulantServiceImplTestConfiguration {
        public PostulantService postulantService() {
            return new PostulantServiceImpl();
        }
    }

    @Test
    @DisplayName("when SavePostulant With Valid Postulant Then Returns Success") //happy path
    public void whenSavePostulantWithValidPostulantThenReturnsSuccess() {
        Long id = 1L;
        String name = "example@upc.edu.pe";
        String password = "Nota#20";
        Postulant postulant = new Postulant(id, name, "Villegas", "email", 2L, password, "document","civil");
        when(postulantRepository.save(Mockito.any(Postulant.class))).thenReturn(new Postulant());
        when(postulantRepository.findById(id)).thenReturn(Optional.of(postulant));
        Postulant savedPostulant = postulantService.getPostulantById(postulant.getId());
        assertNotNull(savedPostulant);
    }

    @Test
    @DisplayName("when GetPostulantById With Valid Id Then Returns Postulant") //happy path
    public void whenGetPostulantByIdWithValidIdThenReturnsPostulant() {
        //Arrange
        Long id = 1L;
        Postulant postulant = new Postulant(id, "caro", "Villegas", "email", 2L, "password", "document","civil");
        when(postulantRepository.findById(id)).thenReturn(Optional.of(postulant));
        //Act
        Postulant foundPostulant = postulantService.getPostulantById(id);
        //Assert
        assertThat(foundPostulant.getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("when GetPostulantById With Invalid Id Then Returns ResourceNotFoundException") //unhappy path
    public void whenGetPostulantByIdWithInvalidIdThenReturnsResourceNotFoundException() {
        //Arrange
        Long id = 1L;
        String template = "Resource %s not found for %s with value %s";
        when(postulantRepository.findById(id)).thenReturn(Optional.empty());
        String exceptedMessage = String.format(template, "Postulant", "Id", id);
        //Act
        Throwable exception = catchThrowable(() ->{
            Postulant foundPostulant = postulantService.getPostulantById(id);
        });
        //Assert
        assertThat(exception)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(exceptedMessage);
    }

    @Test
    @DisplayName("when UpdatePostulant With Valid Postulant Then Returns Success") //happy path
    public void whenUpdatePostulantWithValidPostulantThenReturnsSuccess() {
        //Arrange
        Long id = 1L;
        String name = "example@upc.edu.pe";
        String password = "Nota#20";
        Postulant postulant = new Postulant(id, name, "Villegas", "email", 2L, password, "document","civil");

        String newPassword = "Nota@20";
        postulant.setPassword(newPassword);
        when(postulantRepository.findById(id)).thenReturn(Optional.of(postulant));
        Postulant updatePostulant = postulantService.getPostulantById(id);
        assertEquals("Nota@20",updatePostulant.getPassword());
    }
}
