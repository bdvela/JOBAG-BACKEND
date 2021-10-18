package com.example.jobagapi;
import com.example.jobagapi.domain.model.Employeer;
import com.example.jobagapi.domain.model.Postulant;
import com.example.jobagapi.domain.repository.EmployeerRepository;
import com.example.jobagapi.domain.service.EmployeerService;
import com.example.jobagapi.service.EmployeerServiceImpl;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class EmployeerServiceImplTest {

    @Mock
    private EmployeerRepository employeerRepository;
    @InjectMocks
    private EmployeerService employeerService= new EmployeerServiceImpl();

    @TestConfiguration
    static class EmployeerServiceImplTestConfiguration
    {
        public EmployeerService employeerService()
        {
            return new EmployeerServiceImpl();
        }
    }

    @Test
    @DisplayName("When getEmployeerById With Valid Title Then Returns Employeer")
    public void whenGetEmployeerByIdWithValidIdThenReturnsEmployeer() {
        // Arrange
        Long Id = 1L;
        String name = "example@upc.edu.pe";
        String password = "Nota#20";
        Employeer employeer = new Employeer(Id, name, "Villegas", "email", 2L, password, "document","civil");

        when(employeerRepository.save(Mockito.any(Employeer.class))).thenReturn(new Employeer());
        when(employeerRepository.findById(Id)).thenReturn(Optional.of(employeer));
        Employeer savedEmployer = employeerService.getEmployeerById(employeer.getId());
        // Assert
        assertEquals(Id,savedEmployer.getId());

    }

    @Test
    @DisplayName("When getEmployeerByPosicion With Valid Title Then Returns Employeer")
    public void whenGetEmployeerByPosicionWithValidPosicionThenReturnsEmployeer() {
        // Arrange
        String posicion = "aea";
        Employeer employeer = new Employeer().setPosicion(posicion);
        when(employeerRepository.findByPosicion(posicion))
                .thenReturn(Optional.of(employeer));

        // Act
       // Employeer foundEmployeer = employeerRepository.findByPosicion(posicion);

        // Assert
      //  assertThat(foundEmployeer.getPosicion()).isEqualTo(posicion);

    }




}