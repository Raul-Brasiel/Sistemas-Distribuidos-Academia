package com.sd.raul.prj_academia_serv_1.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "planos")
public class Plano implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "duracao_dias", nullable = false)
    private int duracaoDias;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(nullable = false)
    private boolean ativo = true;

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

    public String getDescricao(){
    	return descricao;
    }
    public void setDescricao(String descricao){
    	this.descricao = descricao;
    }

    public int getDuracaoDias(){
    	return duracaoDias;
    }
    public void setDuracaoDias(int duracaoDias)
    {
    	this.duracaoDias = duracaoDias;
    }

    public BigDecimal getPreco()
    {
    	return preco;
    }
    public void setPreco(BigDecimal preco)
    {
    	this.preco = preco;
    }

    public boolean isAtivo(){
    	return ativo;
    }
    public void setAtivo(boolean ativo){
    	this.ativo = ativo; 
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