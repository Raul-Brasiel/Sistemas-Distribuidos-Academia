package com.sd.raul.prj_academia_serv_1.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sd.raul.prj_academia_serv_1.dtos.PlanoRecordDto;
import com.sd.raul.prj_academia_serv_1.models.Plano;
import com.sd.raul.prj_academia_serv_1.repositories.PlanoRepository;

@Service
public class PlanoService{

    private final PlanoRepository planoRepository;

    public PlanoService(PlanoRepository planoRepository){
        this.planoRepository = planoRepository;
    }

    public List<Plano> getPlanos(){
        return planoRepository.findAll();
    }

    public Plano getPlano(UUID id){
        return planoRepository.findById(id).orElse(null);
    }

    public Plano salvarPlano(PlanoRecordDto dto){
        Plano plano = new Plano();
        plano.setNome(dto.nome());
        plano.setDescricao(dto.descricao());
        plano.setDuracaoDias(dto.duracaoDias());
        plano.setPreco(dto.preco());
        plano.setAtivo(dto.ativo());
        return planoRepository.save(plano);
    }

    public Plano atualizarPlano(UUID id, PlanoRecordDto dto){
        Plano plano = planoRepository.findById(id).orElse(null);
        if (plano == null) return null;
        plano.setNome(dto.nome());
        plano.setDescricao(dto.descricao());
        plano.setDuracaoDias(dto.duracaoDias());
        plano.setPreco(dto.preco());
        plano.setAtivo(dto.ativo());
        return planoRepository.save(plano);
    }

    public void excluirPlano(UUID id){
        planoRepository.deleteById(id);
    }
}