package com.sd.raul.prj_academia_servGestor.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class SistemasService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final LoadBalancerService loadBalancer;

    private final String urlServ2;
    private final String urlServ3;

    public SistemasService(
            LoadBalancerService loadBalancer,
            @Value("${serv2.url}") String urlServ2,
            @Value("${serv3.url}") String urlServ3) {
        this.loadBalancer = loadBalancer;
        this.urlServ2 = urlServ2;
        this.urlServ3 = urlServ3;
    }

    // ============================================================
    // MÉTODO CENTRAL — Serviço 1
    // ============================================================
    private ResponseEntity<String> chamarServ1(String path, HttpMethod method, String body){
        int maxTentativas = loadBalancer.getTotalInstancias();

        for(int tentativa = 0; tentativa < maxTentativas; tentativa++){
            String instancia = loadBalancer.getProximaInstancia();

            if(instancia == null){
                return ResponseEntity.status(503).body("{\"erro\": \"Nenhuma instância do Serviço 1 está disponível\"}");
            }
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> entity = new HttpEntity<>(body, headers);

                ResponseEntity<String> resposta = restTemplate.exchange(instancia + path, method, entity, String.class);

                System.out.println("[LoadBalancer] Requisição atendida por: " + instancia);
                return resposta;

            }
            catch(ResourceAccessException e){
                System.out.println("[LoadBalancer] Falha na instância: " + instancia + " — tentando próxima...");
                loadBalancer.marcarComoFalha(instancia);
            }
        }
        return ResponseEntity.status(503).body("{\"erro\": \"Todas as instâncias do Serviço 1 falharam\"}");
    }

 // ============================================================
    // MÉTODO AUXILIAR — Serviços 2 e 3
    // ============================================================
    // Método privado que retorna uma resposta HTTP completa (ResponseEntity) contendo um texto String.
    private ResponseEntity<String> chamarServico(String url, HttpMethod method, String body){
        try{
            // Cria um objeto para armazenar os "Cabeçalhos" (Headers) da requisição HTTP.
            HttpHeaders headers = new HttpHeaders();
            
            // Avisa ao serviço de destino que o conteúdo que estamos enviando (o body) está no formato JSON.
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            // Empacota o corpo da mensagem (body) junto com as configurações de cabeçalho (headers) em uma única "entidade" (pacote).
            HttpEntity<String> entity = new HttpEntity<>(body, headers);
            
            // O restTemplate.exchange é quem viaja rede. 
            // Ele dispara a requisição para a URL, usando o método escolhido, levando o pacote (entity) 
            return restTemplate.exchange(url, method, entity, String.class);
        }
        catch(Exception e){
            // Monta uma resposta manual com o Status HTTP 503 (Service Unavailable - Serviço Indisponível) 
            return ResponseEntity.status(503).body("{\"erro\": \"Serviço indisponível: " + e.getMessage() + "\"}");
        }
    }

    // ============================================================
    // SERVIÇO 1 — PLANOS
    // ============================================================
    public ResponseEntity<String> getPlanos(){
        return chamarServ1("/api/planos", HttpMethod.GET, null);
    }

    public ResponseEntity<String> getPlano(String id){
        return chamarServ1("/api/planos/" + id, HttpMethod.GET, null);
    }

    public ResponseEntity<String> salvarPlano(String body){
        return chamarServ1("/api/planos", HttpMethod.POST, body);
    }

    public ResponseEntity<String> atualizarPlano(String id, String body){
        return chamarServ1("/api/planos/" + id, HttpMethod.PUT, body);
    }

    public ResponseEntity<String> excluirPlano(String id){
        return chamarServ1("/api/planos/" + id, HttpMethod.DELETE, null);
    }

    // ============================================================
    // SERVIÇO 1 — CLIENTES
    // ============================================================
    public ResponseEntity<String> getClientes(){
        return chamarServ1("/api/clientes", HttpMethod.GET, null);
    }

    public ResponseEntity<String> getClientesAtivos(){
        return chamarServ1("/api/clientes/ativos", HttpMethod.GET, null);
    }

    public ResponseEntity<String> getCliente(String id){
        return chamarServ1("/api/clientes/" + id, HttpMethod.GET, null);
    }

    public ResponseEntity<String> salvarCliente(String body){
        return chamarServ1("/api/clientes", HttpMethod.POST, body);
    }

    public ResponseEntity<String> atualizarCliente(String id, String body){
        return chamarServ1("/api/clientes/" + id, HttpMethod.PUT, body);
    }

    public ResponseEntity<String> excluirCliente(String id){
        return chamarServ1("/api/clientes/" + id, HttpMethod.DELETE, null);
    }

    // ============================================================
    // SERVIÇO 1 — FUNCIONÁRIOS
    // ============================================================
    public ResponseEntity<String> getFuncionarios(){
        return chamarServ1("/api/funcionarios", HttpMethod.GET, null);
    }

    public ResponseEntity<String> getFuncionariosAtivos(){
        return chamarServ1("/api/funcionarios/ativos", HttpMethod.GET, null);
    }

    public ResponseEntity<String> getFuncionariosPorCargo(String cargo){
        return chamarServ1("/api/funcionarios/cargo/" + cargo, HttpMethod.GET, null);
    }

    public ResponseEntity<String> getFuncionario(String id){
        return chamarServ1("/api/funcionarios/" + id, HttpMethod.GET, null);
    }

    public ResponseEntity<String> salvarFuncionario(String body){
        return chamarServ1("/api/funcionarios", HttpMethod.POST, body);
    }

    public ResponseEntity<String> atualizarFuncionario(String id, String body){
        return chamarServ1("/api/funcionarios/" + id, HttpMethod.PUT, body);
    }

    public ResponseEntity<String> excluirFuncionario(String id){
        return chamarServ1("/api/funcionarios/" + id, HttpMethod.DELETE, null);
    }

    // ============================================================
    // SERVIÇO 1 — MODALIDADES
    // ============================================================
    public ResponseEntity<String> getModalidades(){
        return chamarServ1("/api/modalidades", HttpMethod.GET, null);
    }

    public ResponseEntity<String> getModalidadesAtivas(){
        return chamarServ1("/api/modalidades/ativas", HttpMethod.GET, null);
    }

    public ResponseEntity<String> getModalidadesPorInstrutor(String instrutorId){
        return chamarServ1("/api/modalidades/instrutor/" + instrutorId, HttpMethod.GET, null);
    }

    public ResponseEntity<String> getModalidade(String id){
        return chamarServ1("/api/modalidades/" + id, HttpMethod.GET, null);
    }

    public ResponseEntity<String> salvarModalidade(String body){
        return chamarServ1("/api/modalidades", HttpMethod.POST, body);
    }

    public ResponseEntity<String> atualizarModalidade(String id, String body){
        return chamarServ1("/api/modalidades/" + id, HttpMethod.PUT, body);
    }

    public ResponseEntity<String> excluirModalidade(String id){
        return chamarServ1("/api/modalidades/" + id, HttpMethod.DELETE, null);
    }

    // ============================================================
    // SERVIÇO 2 — MATRÍCULAS
    // ============================================================
    public ResponseEntity<String> getMatriculas(){
        return chamarServico(urlServ2 + "/api/matriculas", HttpMethod.GET, null);
    }

    public ResponseEntity<String> getMatriculasPorCliente(String clienteId){
        return chamarServico(urlServ2 + "/api/matriculas/cliente/" + clienteId, HttpMethod.GET, null);
    }

    public ResponseEntity<String> getMatriculasPorPlano(String planoId){
        return chamarServico(urlServ2 + "/api/matriculas/plano/" + planoId, HttpMethod.GET, null);
    }

    public ResponseEntity<String> getMatriculasPorStatus(String status){
        return chamarServico(urlServ2 + "/api/matriculas/status/" + status, HttpMethod.GET, null);
    }

    public ResponseEntity<String> getMatricula(String id){
        return chamarServico(urlServ2 + "/api/matriculas/" + id, HttpMethod.GET, null);
    }

    public ResponseEntity<String> salvarMatricula(String body){
        return chamarServico(urlServ2 + "/api/matriculas", HttpMethod.POST, body);
    }

    public ResponseEntity<String> atualizarMatricula(String id, String body){
        return chamarServico(urlServ2 + "/api/matriculas/" + id, HttpMethod.PUT, body);
    }

    public ResponseEntity<String> excluirMatricula(String id){
        return chamarServico(urlServ2 + "/api/matriculas/" + id, HttpMethod.DELETE, null);
    }

    // ============================================================
    // SERVIÇO 2 — PAGAMENTOS
    // ============================================================
    public ResponseEntity<String> getPagamentos(){
        return chamarServico(urlServ2 + "/api/pagamentos", HttpMethod.GET, null);
    }

    public ResponseEntity<String> getPagamentosPorMatricula(String matriculaId){
        return chamarServico(urlServ2 + "/api/pagamentos/matricula/" + matriculaId, HttpMethod.GET, null);
    }

    public ResponseEntity<String> getPagamentosPorStatus(String status){
        return chamarServico(urlServ2 + "/api/pagamentos/status/" + status, HttpMethod.GET, null);
    }

    public ResponseEntity<String> getPagamento(String id){
        return chamarServico(urlServ2 + "/api/pagamentos/" + id, HttpMethod.GET, null);
    }

    public ResponseEntity<String> salvarPagamento(String body){
        return chamarServico(urlServ2 + "/api/pagamentos", HttpMethod.POST, body);
    }

    public ResponseEntity<String> atualizarPagamento(String id, String body){
        return chamarServico(urlServ2 + "/api/pagamentos/" + id, HttpMethod.PUT, body);
    }

    public ResponseEntity<String> excluirPagamento(String id){
        return chamarServico(urlServ2 + "/api/pagamentos/" + id, HttpMethod.DELETE, null);
    }

    // ============================================================
    // SERVIÇO 3 — TREINOS
    // ============================================================
    public ResponseEntity<String> getTreinos(){
        return chamarServico(urlServ3 + "/api/treinos", HttpMethod.GET, null);
    }

    public ResponseEntity<String> getTreinosPorCliente(String clienteId){
        return chamarServico(urlServ3 + "/api/treinos/cliente/" + clienteId, HttpMethod.GET, null);
    }

    public ResponseEntity<String> getTreinosPorFuncionario(String funcionarioId){
        return chamarServico(urlServ3 + "/api/treinos/funcionario/" + funcionarioId, HttpMethod.GET, null);
    }

    public ResponseEntity<String> getTreino(String id){
        return chamarServico(urlServ3 + "/api/treinos/" + id, HttpMethod.GET, null);
    }

    public ResponseEntity<String> salvarTreino(String body){
        return chamarServico(urlServ3 + "/api/treinos", HttpMethod.POST, body);
    }

    public ResponseEntity<String> atualizarTreino(String id, String body){
        return chamarServico(urlServ3 + "/api/treinos/" + id, HttpMethod.PUT, body);
    }

    public ResponseEntity<String> excluirTreino(String id){
        return chamarServico(urlServ3 + "/api/treinos/" + id, HttpMethod.DELETE, null);
    }

    // ============================================================
    // SERVIÇO 3 — AVALIAÇÕES FÍSICAS
    // ============================================================
    public ResponseEntity<String> getAvaliacoes(){
        return chamarServico(urlServ3 + "/api/avaliacoes", HttpMethod.GET, null);
    }

    public ResponseEntity<String> getAvaliacoesPorCliente(String clienteId){
        return chamarServico(urlServ3 + "/api/avaliacoes/cliente/" + clienteId, HttpMethod.GET, null);
    }

    public ResponseEntity<String> getAvaliacoesPorFuncionario(String funcionarioId){
        return chamarServico(urlServ3 + "/api/avaliacoes/funcionario/" + funcionarioId, HttpMethod.GET, null);
    }

    public ResponseEntity<String> getAvaliacao(String id){
        return chamarServico(urlServ3 + "/api/avaliacoes/" + id, HttpMethod.GET, null);
    }

    public ResponseEntity<String> salvarAvaliacao(String body){
        return chamarServico(urlServ3 + "/api/avaliacoes", HttpMethod.POST, body);
    }

    public ResponseEntity<String> atualizarAvaliacao(String id, String body){
        return chamarServico(urlServ3 + "/api/avaliacoes/" + id, HttpMethod.PUT, body);
    }

    public ResponseEntity<String> excluirAvaliacao(String id){
        return chamarServico(urlServ3 + "/api/avaliacoes/" + id, HttpMethod.DELETE, null);
    }
}