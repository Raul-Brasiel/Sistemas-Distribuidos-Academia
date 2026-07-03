package com.sd.raul.prj_academia_serv_2.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sd.raul.prj_academia_serv_2.dtos.PagamentoRecordDto;
import com.sd.raul.prj_academia_serv_2.models.Pagamento;
import com.sd.raul.prj_academia_serv_2.services.PagamentoService;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController{

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService){
        this.pagamentoService = pagamentoService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping
    public List<Pagamento> getPagamentos(){
        return pagamentoService.getPagamentos();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/matricula/{matriculaId}")
    public List<Pagamento> getPagamentosPorMatricula(@PathVariable UUID matriculaId){
        return pagamentoService.getPagamentosPorMatricula(matriculaId);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/status/{status}")
    public List<Pagamento> getPagamentosPorStatus(@PathVariable String status){
        return pagamentoService.getPagamentosPorStatus(status);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> getPagamento(@PathVariable UUID id){
        Pagamento pagamento = pagamentoService.getPagamento(id);
        if(pagamento == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(pagamento);
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<Pagamento> salvarPagamento(@RequestBody PagamentoRecordDto dto){
        Pagamento pagamento = pagamentoService.salvarPagamento(dto);
        if(pagamento == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(pagamento);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/{id}")
    public ResponseEntity<Pagamento> atualizarPagamento(@PathVariable UUID id, @RequestBody PagamentoRecordDto dto){
        Pagamento pagamento = pagamentoService.atualizarPagamento(id, dto);
        if(pagamento == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(pagamento);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPagamento(@PathVariable UUID id){
        pagamentoService.excluirPagamento(id);
        return ResponseEntity.noContent().build();
    }
}