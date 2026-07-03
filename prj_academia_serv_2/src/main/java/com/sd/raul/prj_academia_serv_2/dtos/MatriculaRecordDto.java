package com.sd.raul.prj_academia_serv_2.dtos;

import java.time.LocalDate;
import java.util.UUID;

public record MatriculaRecordDto(
    UUID id,
    UUID clienteId,
    UUID planoId,
    LocalDate dataInicio,
    LocalDate dataFim,
    String status,
    String observacoes
) {}