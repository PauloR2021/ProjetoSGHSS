package com.pauloricardo.sghss.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.pauloricardo.sghss.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

}
