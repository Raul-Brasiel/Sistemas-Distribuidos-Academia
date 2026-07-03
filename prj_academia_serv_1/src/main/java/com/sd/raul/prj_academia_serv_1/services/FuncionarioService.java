package com.sd.raul.prj_academia_serv_1.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sd.raul.prj_academia_serv_1.dtos.FuncionarioRecordDto;
import com.sd.raul.prj_academia_serv_1.models.Funcionario;
import com.sd.raul.prj_academia_serv_1.repositories.FuncionarioRepository;

@Service
public class FuncionarioService{

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository){
        this.funcionarioRepository = funcionarioRepository;
    }

    public List<Funcionario> getFuncionarios(){
        return funcionarioRepository.findAll();
    }

    public List<Funcionario> getFuncionariosAtivos(){
        return funcionarioRepository.findByAtivo(true);
    }

    public List<Funcionario> getFuncionariosPorCargo(String cargo){
        return funcionarioRepository.findByCargo(cargo);
    }

    public Funcionario getFuncionario(UUID id){
        return funcionarioRepository.findById(id).orElse(null);
    }

    public Funcionario salvarFuncionario(FuncionarioRecordDto dto){
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(dto.nome());
        funcionario.setCpf(dto.cpf());
        funcionario.setEmail(dto.email());
        funcionario.setTelefone(dto.telefone());
        funcionario.setCargo(dto.cargo());
        funcionario.setSalario(dto.salario());
        funcionario.setDataAdmissao(dto.dataAdmissao());
        funcionario.setDataDemissao(dto.dataDemissao());
        funcionario.setAtivo(dto.ativo());
        return funcionarioRepository.save(funcionario);
    }

    public Funcionario atualizarFuncionario(UUID id, FuncionarioRecordDto dto){
        Funcionario funcionario = funcionarioRepository.findById(id).orElse(null);
        if (funcionario == null) return null;
        funcionario.setNome(dto.nome());
        funcionario.setCpf(dto.cpf());
        funcionario.setEmail(dto.email());
        funcionario.setTelefone(dto.telefone());
        funcionario.setCargo(dto.cargo());
        funcionario.setSalario(dto.salario());
        funcionario.setDataAdmissao(dto.dataAdmissao());
        funcionario.setDataDemissao(dto.dataDemissao());
        funcionario.setAtivo(dto.ativo());
        return funcionarioRepository.save(funcionario);
    }

    public void excluirFuncionario(UUID id){
        funcionarioRepository.deleteById(id);
    }
}