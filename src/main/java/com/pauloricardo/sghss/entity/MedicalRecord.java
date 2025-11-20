/*Classe responsável por criar a Tabelas do Banco para salvr o Prontuário */
package com.pauloricardo.sghss.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
public class MedicalRecord {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    private Appointment appointment;

    @Column(columnDefinition = "TEXT")
    private String description;

}
