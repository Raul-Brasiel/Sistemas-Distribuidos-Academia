package com.sd.raul.prj_academia_servGestor.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sd.raul.prj_academia_servGestor.services.LoadBalancerService;
import com.sd.raul.prj_academia_servGestor.services.SistemasService;

@RestController
@RequestMapping("/api/gestor")
public class SistemasController{

    private final SistemasService sistemasService;
    private final LoadBalancerService loadBalancer;

    public SistemasController(SistemasService sistemasService, LoadBalancerService loadBalancer){
        this.sistemasService = sistemasService;
        this.loadBalancer = loadBalancer;
    }

    // ============================================================
    // STATUS DO LOAD BALANCER
    // ============================================================
    @CrossOrigin(origins = "*")
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getStatus(){
        return ResponseEntity.ok(Map.of(
                "todasInstancias", loadBalancer.getTodasInstancias(),
                "instanciasAtivas", loadBalancer.getInstanciasAtivas(),
                "instanciasInativas", loadBalancer.getTodasInstancias().stream()
                 .filter(i -> !loadBalancer.getInstanciasAtivas().contains(i))
                 .toList()
        ));
    }

    // ============================================================
    // SERVIÇO 1 — PLANOS
    // ============================================================
    @CrossOrigin(origins = "*")
    @GetMapping("/planos")
    public ResponseEntity<String> getPlanos(){
        return sistemasService.getPlanos();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/planos/{id}")
    public ResponseEntity<String> getPlano(@PathVariable String id){
        return sistemasService.getPlano(id);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/planos")
    public ResponseEntity<String> salvarPlano(@RequestBody String body){
        return sistemasService.salvarPlano(body);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/planos/{id}")
    public ResponseEntity<String> atualizarPlano(@PathVariable String id, @RequestBody String body){
        return sistemasService.atualizarPlano(id, body);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/planos/{id}")
    public ResponseEntity<String> excluirPlano(@PathVariable String id){
        return sistemasService.excluirPlano(id);
    }

    // ============================================================
    // SERVIÇO 1 — CLIENTES
    // ============================================================
    @CrossOrigin(origins = "*")
    @GetMapping("/clientes")
    public ResponseEntity<String> getClientes(){
        return sistemasService.getClientes();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/clientes/ativos")
    public ResponseEntity<String> getClientesAtivos(){
        return sistemasService.getClientesAtivos();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/clientes/{id}")
    public ResponseEntity<String> getCliente(@PathVariable String id){
        return sistemasService.getCliente(id);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/clientes")
    public ResponseEntity<String> salvarCliente(@RequestBody String body){
        return sistemasService.salvarCliente(body);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/clientes/{id}")
    public ResponseEntity<String> atualizarCliente(@PathVariable String id, @RequestBody String body){
        return sistemasService.atualizarCliente(id, body);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<String> excluirCliente(@PathVariable String id){
        return sistemasService.excluirCliente(id);
    }

    // ============================================================
    // SERVIÇO 1 — FUNCIONÁRIOS
    // ============================================================
    @CrossOrigin(origins = "*")
    @GetMapping("/funcionarios")
    public ResponseEntity<String> getFuncionarios(){
        return sistemasService.getFuncionarios();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/funcionarios/ativos")
    public ResponseEntity<String> getFuncionariosAtivos(){
        return sistemasService.getFuncionariosAtivos();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/funcionarios/cargo/{cargo}")
    public ResponseEntity<String> getFuncionariosPorCargo(@PathVariable String cargo){
        return sistemasService.getFuncionariosPorCargo(cargo);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/funcionarios/{id}")
    public ResponseEntity<String> getFuncionario(@PathVariable String id){
        return sistemasService.getFuncionario(id);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/funcionarios")
    public ResponseEntity<String> salvarFuncionario(@RequestBody String body){
        return sistemasService.salvarFuncionario(body);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/funcionarios/{id}")
    public ResponseEntity<String> atualizarFuncionario(@PathVariable String id, @RequestBody String body){
        return sistemasService.atualizarFuncionario(id, body);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/funcionarios/{id}")
    public ResponseEntity<String> excluirFuncionario(@PathVariable String id){
        return sistemasService.excluirFuncionario(id);
    }

    // ============================================================
    // SERVIÇO 1 — MODALIDADES
    // ============================================================
    @CrossOrigin(origins = "*")
    @GetMapping("/modalidades")
    public ResponseEntity<String> getModalidades(){
        return sistemasService.getModalidades();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/modalidades/ativas")
    public ResponseEntity<String> getModalidadesAtivas(){
        return sistemasService.getModalidadesAtivas();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/modalidades/instrutor/{instrutorId}")
    public ResponseEntity<String> getModalidadesPorInstrutor(@PathVariable String instrutorId){
        return sistemasService.getModalidadesPorInstrutor(instrutorId);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/modalidades/{id}")
    public ResponseEntity<String> getModalidade(@PathVariable String id){
        return sistemasService.getModalidade(id);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/modalidades")
    public ResponseEntity<String> salvarModalidade(@RequestBody String body){
        return sistemasService.salvarModalidade(body);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/modalidades/{id}")
    public ResponseEntity<String> atualizarModalidade(@PathVariable String id, @RequestBody String body){
        return sistemasService.atualizarModalidade(id, body);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/modalidades/{id}")
    public ResponseEntity<String> excluirModalidade(@PathVariable String id){
        return sistemasService.excluirModalidade(id);
    }

    // ============================================================
    // SERVIÇO 2 — MATRÍCULAS
    // ============================================================
    @CrossOrigin(origins = "*")
    @GetMapping("/matriculas")
    public ResponseEntity<String> getMatriculas(){
        return sistemasService.getMatriculas();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/matriculas/cliente/{clienteId}")
    public ResponseEntity<String> getMatriculasPorCliente(@PathVariable String clienteId){
        return sistemasService.getMatriculasPorCliente(clienteId);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/matriculas/plano/{planoId}")
    public ResponseEntity<String> getMatriculasPorPlano(@PathVariable String planoId){
        return sistemasService.getMatriculasPorPlano(planoId);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/matriculas/status/{status}")
    public ResponseEntity<String> getMatriculasPorStatus(@PathVariable String status){
        return sistemasService.getMatriculasPorStatus(status);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/matriculas/{id}")
    public ResponseEntity<String> getMatricula(@PathVariable String id){
        return sistemasService.getMatricula(id);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/matriculas")
    public ResponseEntity<String> salvarMatricula(@RequestBody String body){
        return sistemasService.salvarMatricula(body);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/matriculas/{id}")
    public ResponseEntity<String> atualizarMatricula(@PathVariable String id, @RequestBody String body){
        return sistemasService.atualizarMatricula(id, body);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/matriculas/{id}")
    public ResponseEntity<String> excluirMatricula(@PathVariable String id){
        return sistemasService.excluirMatricula(id);
    }

    // ============================================================
    // SERVIÇO 2 — PAGAMENTOS
    // ============================================================
    @CrossOrigin(origins = "*")
    @GetMapping("/pagamentos")
    public ResponseEntity<String> getPagamentos(){
        return sistemasService.getPagamentos();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/pagamentos/matricula/{matriculaId}")
    public ResponseEntity<String> getPagamentosPorMatricula(@PathVariable String matriculaId){
        return sistemasService.getPagamentosPorMatricula(matriculaId);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/pagamentos/status/{status}")
    public ResponseEntity<String> getPagamentosPorStatus(@PathVariable String status){
        return sistemasService.getPagamentosPorStatus(status);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/pagamentos/{id}")
    public ResponseEntity<String> getPagamento(@PathVariable String id){
        return sistemasService.getPagamento(id);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/pagamentos")
    public ResponseEntity<String> salvarPagamento(@RequestBody String body){
        return sistemasService.salvarPagamento(body);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/pagamentos/{id}")
    public ResponseEntity<String> atualizarPagamento(@PathVariable String id, @RequestBody String body){
        return sistemasService.atualizarPagamento(id, body);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/pagamentos/{id}")
    public ResponseEntity<String> excluirPagamento(@PathVariable String id){
        return sistemasService.excluirPagamento(id);
    }

    // ============================================================
    // SERVIÇO 3 — TREINOS
    // ============================================================
    @CrossOrigin(origins = "*")
    @GetMapping("/treinos")
    public ResponseEntity<String> getTreinos(){
        return sistemasService.getTreinos();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/treinos/cliente/{clienteId}")
    public ResponseEntity<String> getTreinosPorCliente(@PathVariable String clienteId){
        return sistemasService.getTreinosPorCliente(clienteId);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/treinos/funcionario/{funcionarioId}")
    public ResponseEntity<String> getTreinosPorFuncionario(@PathVariable String funcionarioId){
        return sistemasService.getTreinosPorFuncionario(funcionarioId);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/treinos/{id}")
    public ResponseEntity<String> getTreino(@PathVariable String id){
        return sistemasService.getTreino(id);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/treinos")
    public ResponseEntity<String> salvarTreino(@RequestBody String body){
        return sistemasService.salvarTreino(body);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/treinos/{id}")
    public ResponseEntity<String> atualizarTreino(@PathVariable String id, @RequestBody String body){
        return sistemasService.atualizarTreino(id, body);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/treinos/{id}")
    public ResponseEntity<String> excluirTreino(@PathVariable String id){
        return sistemasService.excluirTreino(id);
    }

    // ============================================================
    // SERVIÇO 3 — AVALIAÇÕES FÍSICAS
    // ============================================================
    @CrossOrigin(origins = "*")
    @GetMapping("/avaliacoes")
    public ResponseEntity<String> getAvaliacoes() {
        return sistemasService.getAvaliacoes();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/avaliacoes/cliente/{clienteId}")
    public ResponseEntity<String> getAvaliacoesPorCliente(@PathVariable String clienteId){
        return sistemasService.getAvaliacoesPorCliente(clienteId);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/avaliacoes/funcionario/{funcionarioId}")
    public ResponseEntity<String> getAvaliacoesPorFuncionario(@PathVariable String funcionarioId){
        return sistemasService.getAvaliacoesPorFuncionario(funcionarioId);
    }
    
    @CrossOrigin(origins = "*")
    @GetMapping("/avaliacoes/{id}")
    public ResponseEntity<String> getAvaliacao(@PathVariable String id){
        return sistemasService.getAvaliacao(id);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/avaliacoes")
    public ResponseEntity<String> salvarAvaliacao(@RequestBody String body){
        return sistemasService.salvarAvaliacao(body);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/avaliacoes/{id}")
    public ResponseEntity<String> atualizarAvaliacao(@PathVariable String id, @RequestBody String body){
        return sistemasService.atualizarAvaliacao(id, body);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/avaliacoes/{id}")
    public ResponseEntity<String> excluirAvaliacao(@PathVariable String id){
        return sistemasService.excluirAvaliacao(id);
    }
}