package com.sd.raul.prj_academia_serv_1.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "modalidades")
public class Modalidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "duracao_minutos", nullable = false)
    private int duracaoMinutos;

    @Column(name = "capacidade_max", nullable = false)
    private int capacidadeMax;

    @Column(nullable = false, length = 30)
    private String nivel = "Todos";

    // FK para Funcionario (instrutor) — dentro do mesmo serviço/banco
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "instrutor_id")
    @JsonProperty("instrutor")
    private Funcionario instrutor;

    @Column(nullable = false)
    private boolean ativa = true;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;

    @PrePersist
    public void prePersist(){
        criadoEm = LocalDateTime.now();
        atualizadoEm = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        atualizadoEm = LocalDateTime.now();
    }

    public UUID getId(){
    	return id;
    }
    public void setId(UUID id){
    	this.id = id;
    }

    public String getNome(){
    	return nome;
    }
    public void setNome(String nome){
    	this.nome = nome;
    }

    public String getDescricao(){return descricao;
    }
    public void setDescricao(String descricao){
    	this.descricao = descricao;
    }

    public int getDuracaoMinutos(){
    	return duracaoMinutos;
    }
    public void setDuracaoMinutos(int duracaoMinutos){
    	this.duracaoMinutos = duracaoMinutos;
    }

    public int getCapacidadeMax(){
    	return capacidadeMax;
    }
    public void setCapacidadeMax(int capacidadeMax){
    	this.capacidadeMax = capacidadeMax;
    }

    public String getNivel(){
    	return nivel;
    }
    public void setNivel(String nivel){
    	this.nivel = nivel;
    }

    public Funcionario getInstrutor(){
    	return instrutor;
    }
    public void setInstrutor(Funcionario instrutor){
    	this.instrutor = instrutor;
    }

    public boolean isAtiva(){
    	return ativa;
    }
    public void setAtiva(boolean ativa){this.ativa = ativa;
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
}