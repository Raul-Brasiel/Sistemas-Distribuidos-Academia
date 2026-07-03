package com.sd.raul.prj_academia_serv_1.dtos;

import java.util.UUID;

public record ModalidadeRecordDto(
    UUID id,
    String nome,
    String descricao,
    int duracaoMinutos,
    int capacidadeMax,
    String nivel,
    UUID instrutorId,
    boolean ativa
) {}