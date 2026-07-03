package com.sd.raul.prj_academia_serv_1.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record FuncionarioRecordDto(
    UUID id,
    String nome,
    String cpf,
    String email,
    String telefone,
    String cargo,
    BigDecimal salario,
    LocalDate dataAdmissao,
    LocalDate dataDemissao,
    boolean ativo
) {}