package com.sd.raul.prj_academia_serv_2.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sd.raul.prj_academia_serv_2.dtos.MatriculaRecordDto;
import com.sd.raul.prj_academia_serv_2.models.Matricula;
import com.sd.raul.prj_academia_serv_2.services.MatriculaService;

@RestController
@RequestMapping("/api/matriculas")
public class MatriculaController{

    private final MatriculaService matriculaService;

    public MatriculaController(MatriculaService matriculaService){
        this.matriculaService = matriculaService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping
    public List<Matricula> getMatriculas(){
        return matriculaService.getMatriculas();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/cliente/{clienteId}")
    public List<Matricula> getMatriculasPorCliente(@PathVariable UUID clienteId){
        return matriculaService.getMatriculasPorCliente(clienteId);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/plano/{planoId}")
    public List<Matricula> getMatriculasPorPlano(@PathVariable UUID planoId){
        return matriculaService.getMatriculasPorPlano(planoId);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/status/{status}")
    public List<Matricula> getMatriculasPorStatus(@PathVariable String status){
        return matriculaService.getMatriculasPorStatus(status);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public ResponseEntity<Matricula> getMatricula(@PathVariable UUID id){
        Matricula matricula = matriculaService.getMatricula(id);
        if(matricula == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(matricula);
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<Matricula> salvarMatricula(@RequestBody MatriculaRecordDto dto){
        return ResponseEntity.ok(matriculaService.salvarMatricula(dto));
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/{id}")
    public ResponseEntity<Matricula> atualizarMatricula(@PathVariable UUID id, @RequestBody MatriculaRecordDto dto){
        Matricula matricula = matriculaService.atualizarMatricula(id, dto);
        if(matricula == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(matricula);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirMatricula(@PathVariable UUID id){
        matriculaService.excluirMatricula(id);
        return ResponseEntity.noContent().build();
    }
}