package com.sd.raul.prj_academia_serv_2.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sd.raul.prj_academia_serv_2.models.Matricula;

public interface MatriculaRepository extends JpaRepository<Matricula, UUID>{
    List<Matricula> findByClienteId(UUID clienteId);
    List<Matricula> findByPlanoId(UUID planoId);
    List<Matricula> findByStatus(String status);
}