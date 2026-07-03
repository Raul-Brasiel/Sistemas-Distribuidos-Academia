package com.sd.raul.prj_academia_serv_1.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sd.raul.prj_academia_serv_1.dtos.ClienteRecordDto;
import com.sd.raul.prj_academia_serv_1.models.Cliente;
import com.sd.raul.prj_academia_serv_1.repositories.ClienteRepository;

@Service
public class ClienteService{

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> getClientes(){
        return clienteRepository.findAll();
    }

    public List<Cliente> getClientesAtivos(){
        return clienteRepository.findByAtivo(true);
    }

    public Cliente getCliente(UUID id){
        return clienteRepository.findById(id).orElse(null);
    }

    public Cliente salvarCliente(ClienteRecordDto dto){
        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setCpf(dto.cpf());
        cliente.setEmail(dto.email());
        cliente.setTelefone(dto.telefone());
        cliente.setDataNascimento(dto.dataNascimento());
        cliente.setSexo(dto.sexo());
        cliente.setEndereco(dto.endereco());
        cliente.setAtivo(dto.ativo());
        return clienteRepository.save(cliente);
    }

    public Cliente atualizarCliente(UUID id, ClienteRecordDto dto){
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        if (cliente == null) return null;
        cliente.setNome(dto.nome());
        cliente.setCpf(dto.cpf());
        cliente.setEmail(dto.email());
        cliente.setTelefone(dto.telefone());
        cliente.setDataNascimento(dto.dataNascimento());
        cliente.setSexo(dto.sexo());
        cliente.setEndereco(dto.endereco());
        cliente.setAtivo(dto.ativo());
        return clienteRepository.save(cliente);
    }

    public void excluirCliente(UUID id){
        clienteRepository.deleteById(id);
    }
}