package com.sd.raul.prj_academia_serv_1.dtos;

import java.time.LocalDate;
import java.util.UUID;

public record ClienteRecordDto(
    UUID id,
    String nome,
    String cpf,
    String email,
    String telefone,
    LocalDate dataNascimento,
    String sexo,
    String endereco,
    boolean ativo
) {}