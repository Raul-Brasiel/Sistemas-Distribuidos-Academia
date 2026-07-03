package com.sd.raul.prj_academia_serv_2.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "matriculas")
public class Matricula implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID")
    private UUID id;

    // Referência ao Cliente do Serviço 1 — apenas UUID, sem FK entre bancos
    @Column(name = "cliente_id", nullable = false, columnDefinition = "UUID")
    private UUID clienteId;

    // Referência ao Plano do Serviço 1 — apenas UUID, sem FK entre bancos
    @Column(name = "plano_id", nullable = false, columnDefinition = "UUID")
    private UUID planoId;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @Column(nullable = false, length = 20)
    private String status = "ativa";

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;

    // Relacionamento real dentro do mesmo banco (Serviço 2)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "matricula", fetch = FetchType.LAZY)
    private Set<Pagamento> pagamentos = new HashSet<>();

    @PrePersist
    public void prePersist(){
        criadoEm = LocalDateTime.now();
        atualizadoEm = LocalDateTime.now();
        if(dataInicio == null) dataInicio = LocalDate.now();
    }

    @PreUpdate
    public void preUpdate() {
        atualizadoEm = LocalDateTime.now();
    }

    public UUID getId(){
    	return id;
    }
    public void setId(UUID id){
    	this.id = id;
    }

    public UUID getClienteId(){
    	return clienteId;
    }
    public void setClienteId(UUID clienteId){
    	this.clienteId = clienteId;
    }

    public UUID getPlanoId(){
    	return planoId;
    }
    public void setPlanoId(UUID planoId){
    	this.planoId = planoId;
    }

    public LocalDate getDataInicio(){
    	return dataInicio;
    }
    public void setDataInicio(LocalDate dataInicio){
    	this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim(){
    	return dataFim;
    }
    public void setDataFim(LocalDate dataFim){
    	this.dataFim = dataFim;
    }

    public String getStatus(){
    	return status;
    }
    public void setStatus(String status){
    	this.status = status;
    }

    public String getObservacoes(){
    	return observacoes;
    }
    public void setObservacoes(String observacoes){
    	this.observacoes = observacoes;
    }

    public LocalDateTime getCriadoEm(){
    	return criadoEm;
    }
    public void setCriadoEm(LocalDateTime criadoEm){
    	this.criadoEm = criadoEm;
    }

    public LocalDateTime getAtualizadoEm(){
    	return atualizadoEm;
    }
    public void setAtualizadoEm(LocalDateTime atualizadoEm){
    	this.atualizadoEm = atualizadoEm;
    }

    public Set<Pagamento> getPagamentos(){
    	return pagamentos;
    }
    public void setPagamentos(Set<Pagamento> pagamentos){
    	this.pagamentos = pagamentos;
    }
}