package com.example.jobagapi.domain.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName="id")
@OnDelete(action = OnDeleteAction.CASCADE)
@Table(name = "employeers")
public class Employeer extends User{
        private String posicion;

        public Employeer(){
                super();
        }

        public Employeer(Long id,   String firstname,  String lastname,   String email,  Long number, String password, String document,String posicion) {
                super(id, firstname, lastname, email, number, password, document);
                this.posicion = posicion;

        }

        public String getPosicion() {
                return posicion;
        }

        public Employeer setPosicion(String posicion) {
                this.posicion = posicion;
                return this;
        }


}
