package com.sd.raul.prj_academia_serv_1.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record PlanoRecordDto(
    UUID id,
    String nome,
    String descricao,
    int duracaoDias,
    BigDecimal preco,
    boolean ativo
) {}