package com.sd.raul.prj_academia_serv_1.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sd.raul.prj_academia_serv_1.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    List<Cliente> findByAtivo(boolean ativo);
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
}