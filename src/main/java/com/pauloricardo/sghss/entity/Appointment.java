package com.pauloricardo.sghss.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
public class Appointment {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    private Patient patient;

    @ManyToOne(optional = false)
    private Professional professional;

    @Column(nullable = false)
    private OffsetDateTime dateTime;

    @Column(nullable = false)
    private String status = "AGENDADA"; // AGENDADA, CANCELADA, REALIZADA

    @Column(nullable = false)
    private boolean telemedicine = false;

    @Column(nullable = false)
    private boolean cancelled = false;
}
