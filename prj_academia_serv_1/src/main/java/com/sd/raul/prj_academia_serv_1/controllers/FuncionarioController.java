package com.sd.raul.prj_academia_serv_1.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sd.raul.prj_academia_serv_1.dtos.FuncionarioRecordDto;
import com.sd.raul.prj_academia_serv_1.models.Funcionario;
import com.sd.raul.prj_academia_serv_1.services.FuncionarioService;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController{

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService){
        this.funcionarioService = funcionarioService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping
    public List<Funcionario> getFuncionarios(){
        return funcionarioService.getFuncionarios();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/ativos")
    public List<Funcionario> getFuncionariosAtivos(){
        return funcionarioService.getFuncionariosAtivos();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/cargo/{cargo}")
    public List<Funcionario> getFuncionariosPorCargo(@PathVariable String cargo){
        return funcionarioService.getFuncionariosPorCargo(cargo);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> getFuncionario(@PathVariable UUID id){
        Funcionario funcionario = funcionarioService.getFuncionario(id);
        if(funcionario == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(funcionario);
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<Funcionario> salvarFuncionario(@RequestBody FuncionarioRecordDto dto){
        return ResponseEntity.ok(funcionarioService.salvarFuncionario(dto));
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> atualizarFuncionario(@PathVariable UUID id, @RequestBody FuncionarioRecordDto dto){
        Funcionario funcionario = funcionarioService.atualizarFuncionario(id, dto);
        if(funcionario == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(funcionario);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirFuncionario(@PathVariable UUID id){
        funcionarioService.excluirFuncionario(id);
        return ResponseEntity.noContent().build();
    }
}