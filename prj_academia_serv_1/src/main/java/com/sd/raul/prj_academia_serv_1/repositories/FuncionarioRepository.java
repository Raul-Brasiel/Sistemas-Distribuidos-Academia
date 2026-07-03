package com.sd.raul.prj_academia_serv_1.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sd.raul.prj_academia_serv_1.models.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, UUID> {
    List<Funcionario> findByAtivo(boolean ativo);
    List<Funcionario> findByCargo(String cargo);
}