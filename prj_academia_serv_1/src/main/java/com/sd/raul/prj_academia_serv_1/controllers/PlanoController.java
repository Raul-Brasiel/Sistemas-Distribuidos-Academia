package com.sd.raul.prj_academia_serv_1.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sd.raul.prj_academia_serv_1.dtos.PlanoRecordDto;
import com.sd.raul.prj_academia_serv_1.models.Plano;
import com.sd.raul.prj_academia_serv_1.services.PlanoService;

@RestController
@RequestMapping("/api/planos")
public class PlanoController{

    private final PlanoService planoService;

    public PlanoController(PlanoService planoService){
        this.planoService = planoService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping
    public List<Plano> getPlanos(){
        return planoService.getPlanos();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public ResponseEntity<Plano> getPlano(@PathVariable UUID id){
        Plano plano = planoService.getPlano(id);
        if (plano == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(plano);
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<Plano> salvarPlano(@RequestBody PlanoRecordDto dto){
        return ResponseEntity.ok(planoService.salvarPlano(dto));
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/{id}")
    public ResponseEntity<Plano> atualizarPlano(@PathVariable UUID id, @RequestBody PlanoRecordDto dto){
        Plano plano = planoService.atualizarPlano(id, dto);
        if (plano == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(plano);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPlano(@PathVariable UUID id){
        planoService.excluirPlano(id);
        return ResponseEntity.noContent().build();
    }
}