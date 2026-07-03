package com.sd.raul.prj_academia_servGestor.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LoadBalancerService{

    // Lista completa de instâncias configuradas no application.properties
    private final List<String> todasInstancias;

    // Conjunto thread-safe das instâncias que estão respondendo
    private final Set<String> instanciasAtivas;

    // Contador para o round-robin
    private final AtomicInteger indice = new AtomicInteger(0);

    private final RestTemplate restTemplate = new RestTemplate();

    public LoadBalancerService(@Value("${serv1.instancias}") String instanciasConfig){
        this.todasInstancias = Arrays.asList(instanciasConfig.split(","));
        this.instanciasAtivas = new CopyOnWriteArraySet<>(todasInstancias);
        System.out.println("[LoadBalancer] Instâncias configuradas: " + todasInstancias);
    }

    // Retorna a próxima instância ativa em round-robin
    public String getProximaInstancia(){
        List<String> ativas = new ArrayList<>(instanciasAtivas);
        if(ativas.isEmpty()) return null;

        int idx = Math.abs(indice.getAndIncrement() % ativas.size());
        return ativas.get(idx);
    }

    // Marca uma instância como falha e a remove da rotação
    public void marcarComoFalha(String url){
        if(instanciasAtivas.remove(url)){
            System.out.println("[LoadBalancer] Instância REMOVIDA da rotação: " + url);
            System.out.println("[LoadBalancer] Instâncias ativas restantes: " + instanciasAtivas);
        }
    }

    // Retorna quantas instâncias existem no total (para limitar tentativas)
    public int getTotalInstancias(){
        return todasInstancias.size();
    }

    // Retorna as instâncias ativas (para o endpoint de status)
    public Set<String> getInstanciasAtivas(){
        return instanciasAtivas;
    }

    // Retorna todas as instâncias (para o endpoint de status)
    public List<String> getTodasInstancias(){
        return todasInstancias;
    }

    // Health check — roda a cada 30 segundos (configurável no application.properties)
    // Tenta reativar instâncias que estavam fora
    @Scheduled(fixedDelayString = "${serv1.healthcheck.intervalo}")
    public void verificarInstanciasInativas(){
        List<String> inativas = todasInstancias.stream().filter(i -> !instanciasAtivas.contains(i)).toList();

        if(inativas.isEmpty()) return;
        
        System.out.println("[LoadBalancer] Verificando instâncias inativas: " + inativas);

        for(String instancia : inativas){
            try{
                restTemplate.getForEntity(instancia + "/api/planos", String.class);
                instanciasAtivas.add(instancia);
                System.out.println("[LoadBalancer] Instância RECUPERADA: " + instancia);
            } 
            catch (Exception e){
                System.out.println("[LoadBalancer] Instância ainda indisponível: " + instancia);
            }
        }
    }
}