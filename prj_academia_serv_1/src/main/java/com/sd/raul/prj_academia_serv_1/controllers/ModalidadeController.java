package com.sd.raul.prj_academia_serv_1.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sd.raul.prj_academia_serv_1.dtos.ModalidadeRecordDto;
import com.sd.raul.prj_academia_serv_1.models.Modalidade;
import com.sd.raul.prj_academia_serv_1.services.ModalidadeService;

@RestController
@RequestMapping("/api/modalidades")
public class ModalidadeController{

    private final ModalidadeService modalidadeService;

    public ModalidadeController(ModalidadeService modalidadeService){
        this.modalidadeService = modalidadeService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping
    public List<Modalidade> getModalidades(){
        return modalidadeService.getModalidades();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/ativas")
    public List<Modalidade> getModalidadesAtivas(){
        return modalidadeService.getModalidadesAtivas();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/instrutor/{instrutorId}")
    public List<Modalidade> getModalidadesPorInstrutor(@PathVariable UUID instrutorId){
        return modalidadeService.getModalidadesPorInstrutor(instrutorId);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public ResponseEntity<Modalidade> getModalidade(@PathVariable UUID id){
        Modalidade modalidade = modalidadeService.getModalidade(id);
        if(modalidade == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(modalidade);
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<Modalidade> salvarModalidade(@RequestBody ModalidadeRecordDto dto){
        return ResponseEntity.ok(modalidadeService.salvarModalidade(dto));
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/{id}")
    public ResponseEntity<Modalidade> atualizarModalidade(@PathVariable UUID id, @RequestBody ModalidadeRecordDto dto){
        Modalidade modalidade = modalidadeService.atualizarModalidade(id, dto);
        if(modalidade == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(modalidade);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirModalidade(@PathVariable UUID id){
        modalidadeService.excluirModalidade(id);
        return ResponseEntity.noContent().build();
    }
}