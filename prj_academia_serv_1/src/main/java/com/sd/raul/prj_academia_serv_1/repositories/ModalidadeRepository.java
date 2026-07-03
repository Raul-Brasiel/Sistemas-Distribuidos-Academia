package com.sd.raul.prj_academia_serv_1.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sd.raul.prj_academia_serv_1.models.Modalidade;

public interface ModalidadeRepository extends JpaRepository<Modalidade, UUID> {
    List<Modalidade> findByAtiva(boolean ativa);
    List<Modalidade> findByInstrutorId(UUID instrutorId);
}