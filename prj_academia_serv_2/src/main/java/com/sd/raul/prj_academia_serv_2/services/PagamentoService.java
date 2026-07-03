package com.sd.raul.prj_academia_serv_2.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sd.raul.prj_academia_serv_2.dtos.PagamentoRecordDto;
import com.sd.raul.prj_academia_serv_2.models.Matricula;
import com.sd.raul.prj_academia_serv_2.models.Pagamento;
import com.sd.raul.prj_academia_serv_2.repositories.MatriculaRepository;
import com.sd.raul.prj_academia_serv_2.repositories.PagamentoRepository;

@Service
public class PagamentoService{

    private final PagamentoRepository pagamentoRepository;
    private final MatriculaRepository matriculaRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository, MatriculaRepository matriculaRepository){
        this.pagamentoRepository = pagamentoRepository;
        this.matriculaRepository = matriculaRepository;
    }

    public List<Pagamento> getPagamentos(){
        return pagamentoRepository.findAll();
    }

    public List<Pagamento> getPagamentosPorMatricula(UUID matriculaId){
        return pagamentoRepository.findByMatriculaId(matriculaId);
    }

    public List<Pagamento> getPagamentosPorStatus(String status){
        return pagamentoRepository.findByStatus(status);
    }

    public Pagamento getPagamento(UUID id){
        return pagamentoRepository.findById(id).orElse(null);
    }

    public Pagamento salvarPagamento(PagamentoRecordDto dto){
        Matricula matricula = matriculaRepository.findById(dto.matriculaId()).orElse(null);
        if(matricula == null) return null;

        Pagamento pagamento = new Pagamento();
        pagamento.setMatricula(matricula);
        pagamento.setValor(dto.valor());
        pagamento.setDataVencimento(dto.dataVencimento());
        pagamento.setDataPagamento(dto.dataPagamento());
        pagamento.setMetodo(dto.metodo());
        pagamento.setStatus(dto.status() != null ? dto.status() : "pendente");
        pagamento.setComprovanteUrl(dto.comprovanteUrl());
        return pagamentoRepository.save(pagamento);
    }

    public Pagamento atualizarPagamento(UUID id, PagamentoRecordDto dto){
        Pagamento pagamento = pagamentoRepository.findById(id).orElse(null);
        if(pagamento == null) return null;

        if(dto.matriculaId() != null){
            Matricula matricula = matriculaRepository.findById(dto.matriculaId()).orElse(null);
            if(matricula != null) pagamento.setMatricula(matricula);
        }

        pagamento.setValor(dto.valor());
        pagamento.setDataVencimento(dto.dataVencimento());
        pagamento.setDataPagamento(dto.dataPagamento());
        pagamento.setMetodo(dto.metodo());
        pagamento.setStatus(dto.status());
        pagamento.setComprovanteUrl(dto.comprovanteUrl());
        return pagamentoRepository.save(pagamento);
    }

    public void excluirPagamento(UUID id){
        pagamentoRepository.deleteById(id);
    }
}