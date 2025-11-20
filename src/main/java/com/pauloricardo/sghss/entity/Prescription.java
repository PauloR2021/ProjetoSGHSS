/*Classe responsável por criar a Tabelas do Banco para salvr a Prescrição */
package com.pauloricardo.sghss.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
public class Prescription {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Professional professional;

    @Column(columnDefinition = "TEXT")
    private String content;

}
