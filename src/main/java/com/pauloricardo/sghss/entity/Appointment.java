/*Classe responsável por criar a Tabelas do Banco para salva a Consulta */

package com.pauloricardo.sghss.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Appointment {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Professional professional;

    private LocalDateTime dateTime;

    private String status; // AGENDADA, CANCELADA, REALIZADA

}
