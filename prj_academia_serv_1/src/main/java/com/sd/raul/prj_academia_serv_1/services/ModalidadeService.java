package com.sd.raul.prj_academia_serv_1.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sd.raul.prj_academia_serv_1.dtos.ModalidadeRecordDto;
import com.sd.raul.prj_academia_serv_1.models.Funcionario;
import com.sd.raul.prj_academia_serv_1.models.Modalidade;
import com.sd.raul.prj_academia_serv_1.repositories.FuncionarioRepository;
import com.sd.raul.prj_academia_serv_1.repositories.ModalidadeRepository;

@Service
public class ModalidadeService{

    private final ModalidadeRepository modalidadeRepository;
    private final FuncionarioRepository funcionarioRepository;

    public ModalidadeService(ModalidadeRepository modalidadeRepository,
                              FuncionarioRepository funcionarioRepository){
        this.modalidadeRepository = modalidadeRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    public List<Modalidade> getModalidades(){
        return modalidadeRepository.findAll();
    }

    public List<Modalidade> getModalidadesAtivas(){
        return modalidadeRepository.findByAtiva(true);
    }

    public List<Modalidade> getModalidadesPorInstrutor(UUID instrutorId){
        return modalidadeRepository.findByInstrutorId(instrutorId);
    }

    public Modalidade getModalidade(UUID id){
        return modalidadeRepository.findById(id).orElse(null);
    }

    public Modalidade salvarModalidade(ModalidadeRecordDto dto){
        Modalidade modalidade = new Modalidade();
        modalidade.setNome(dto.nome());
        modalidade.setDescricao(dto.descricao());
        modalidade.setDuracaoMinutos(dto.duracaoMinutos());
        modalidade.setCapacidadeMax(dto.capacidadeMax());
        modalidade.setNivel(dto.nivel());
        modalidade.setAtiva(dto.ativa());

        if(dto.instrutorId() != null){
            Funcionario instrutor = funcionarioRepository.findById(dto.instrutorId()).orElse(null);
            modalidade.setInstrutor(instrutor);
        }
        return modalidadeRepository.save(modalidade);
    }

    public Modalidade atualizarModalidade(UUID id, ModalidadeRecordDto dto){
        Modalidade modalidade = modalidadeRepository.findById(id).orElse(null);
        if(modalidade == null) return null;

        modalidade.setNome(dto.nome());
        modalidade.setDescricao(dto.descricao());
        modalidade.setDuracaoMinutos(dto.duracaoMinutos());
        modalidade.setCapacidadeMax(dto.capacidadeMax());
        modalidade.setNivel(dto.nivel());
        modalidade.setAtiva(dto.ativa());

        if(dto.instrutorId() != null){
            Funcionario instrutor = funcionarioRepository.findById(dto.instrutorId()).orElse(null);
            modalidade.setInstrutor(instrutor);
        } else{
            modalidade.setInstrutor(null);
        }
        return modalidadeRepository.save(modalidade);
    }

    public void excluirModalidade(UUID id){
        modalidadeRepository.deleteById(id);
    }
}