package com.pauloricardo.sghss.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
public class Professional {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String specialty;

    @Column(unique = true)
    private String crm;

    private String email;
}
