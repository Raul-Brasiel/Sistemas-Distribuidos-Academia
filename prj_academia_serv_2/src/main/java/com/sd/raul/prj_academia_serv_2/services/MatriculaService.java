package com.sd.raul.prj_academia_serv_2.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sd.raul.prj_academia_serv_2.dtos.MatriculaRecordDto;
import com.sd.raul.prj_academia_serv_2.models.Matricula;
import com.sd.raul.prj_academia_serv_2.repositories.MatriculaRepository;

@Service
public class MatriculaService{

    private final MatriculaRepository matriculaRepository;

    public MatriculaService(MatriculaRepository matriculaRepository){
        this.matriculaRepository = matriculaRepository;
    }

    public List<Matricula> getMatriculas(){
        return matriculaRepository.findAll();
    }

    public List<Matricula> getMatriculasPorCliente(UUID clienteId){
        return matriculaRepository.findByClienteId(clienteId);
    }

    public List<Matricula> getMatriculasPorPlano(UUID planoId){
        return matriculaRepository.findByPlanoId(planoId);
    }

    public List<Matricula> getMatriculasPorStatus(String status){
        return matriculaRepository.findByStatus(status);
    }

    public Matricula getMatricula(UUID id){
        return matriculaRepository.findById(id).orElse(null);
    }

    public Matricula salvarMatricula(MatriculaRecordDto dto){
        Matricula matricula = new Matricula();
        matricula.setClienteId(dto.clienteId());
        matricula.setPlanoId(dto.planoId());
        matricula.setDataInicio(dto.dataInicio());
        matricula.setDataFim(dto.dataFim());
        matricula.setStatus(dto.status() != null ? dto.status() : "ativa");
        matricula.setObservacoes(dto.observacoes());
        return matriculaRepository.save(matricula);
    }

    public Matricula atualizarMatricula(UUID id, MatriculaRecordDto dto){
        Matricula matricula = matriculaRepository.findById(id).orElse(null);
        if(matricula == null) return null;
        matricula.setClienteId(dto.clienteId());
        matricula.setPlanoId(dto.planoId());
        matricula.setDataInicio(dto.dataInicio());
        matricula.setDataFim(dto.dataFim());
        matricula.setStatus(dto.status());
        matricula.setObservacoes(dto.observacoes());
        return matriculaRepository.save(matricula);
    }

    public void excluirMatricula(UUID id){
        matriculaRepository.deleteById(id);
    }
}