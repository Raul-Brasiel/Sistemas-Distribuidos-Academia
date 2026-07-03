package com.sd.raul.prj_academia_serv_1.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sd.raul.prj_academia_serv_1.models.Plano;

public interface PlanoRepository extends JpaRepository<Plano, UUID> {}