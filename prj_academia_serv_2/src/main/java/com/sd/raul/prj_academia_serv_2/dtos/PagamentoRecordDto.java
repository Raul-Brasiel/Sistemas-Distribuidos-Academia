package com.sd.raul.prj_academia_serv_2.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PagamentoRecordDto(
    UUID id,
    UUID matriculaId,
    BigDecimal valor,
    LocalDate dataVencimento,
    LocalDate dataPagamento,
    String metodo,
    String status,
    String comprovanteUrl
) {}