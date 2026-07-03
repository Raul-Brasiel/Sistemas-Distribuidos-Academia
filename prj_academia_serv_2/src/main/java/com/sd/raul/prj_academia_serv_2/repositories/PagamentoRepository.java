package com.sd.raul.prj_academia_serv_2.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sd.raul.prj_academia_serv_2.models.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, UUID>{
    List<Pagamento> findByMatriculaId(UUID matriculaId);
    List<Pagamento> findByStatus(String status);
}