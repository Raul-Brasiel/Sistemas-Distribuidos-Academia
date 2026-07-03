package com.sd.raul.prj_academia_serv_1.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sd.raul.prj_academia_serv_1.dtos.ClienteRecordDto;
import com.sd.raul.prj_academia_serv_1.models.Cliente;
import com.sd.raul.prj_academia_serv_1.services.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController{

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping
    public List<Cliente> getClientes(){
        return clienteService.getClientes();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/ativos")
    public List<Cliente> getClientesAtivos(){
        return clienteService.getClientesAtivos();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getCliente(@PathVariable UUID id){
        Cliente cliente = clienteService.getCliente(id);
        if(cliente == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(cliente);
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<Cliente> salvarCliente(@RequestBody ClienteRecordDto dto){
        return ResponseEntity.ok(clienteService.salvarCliente(dto));
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable UUID id, @RequestBody ClienteRecordDto dto){
        Cliente cliente = clienteService.atualizarCliente(id, dto);
        if(cliente == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(cliente);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCliente(@PathVariable UUID id){
        clienteService.excluirCliente(id);
        return ResponseEntity.noContent().build();
    }
}