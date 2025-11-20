/*Classe responsável por criar a Tabelas do Banco para dados dos Pacientes */

package com.pauloricardo.sghss.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
public class Patient {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @Column(unique = true)
    private String cpd;

    private String email;
    private String phone;

}
