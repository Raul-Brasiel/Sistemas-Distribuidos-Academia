package com.sd.raul.prj_academia_serv_2.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

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
@Table(name = "pagamentos")
public class Pagamento implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID")
    private UUID id;

    // FK real — Pagamento e Matricula estão no mesmo banco (Serviço 2)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "matricula_id", nullable = false)
    private Matricula matricula;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;

    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    @Column(length = 30)
    private String metodo;

    @Column(nullable = false, length = 20)
    private String status = "pendente";

    @Column(name = "comprovante_url", columnDefinition = "TEXT")
    private String comprovanteUrl;

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

    public Matricula getMatricula(){
    	return matricula;
    }
    public void setMatricula(Matricula matricula){
    	this.matricula = matricula;
    }

    public BigDecimal getValor(){
    	return valor;
    }
    public void setValor(BigDecimal valor){
    	this.valor = valor;
    }

    public LocalDate getDataVencimento(){
    	return dataVencimento;
    }
    public void setDataVencimento(LocalDate dataVencimento){
    	this.dataVencimento = dataVencimento;
    }

    public LocalDate getDataPagamento(){
    	return dataPagamento;
    }
    public void setDataPagamento(LocalDate dataPagamento){
    	this.dataPagamento = dataPagamento;
    }

    public String getMetodo(){
    	return metodo;
    }
    public void setMetodo(String metodo){
    	this.metodo = metodo;
    }

    public String getStatus(){
    	return status;
    }
    public void setStatus(String status){
    	this.status = status;
    }

    public String getComprovanteUrl(){
    	return comprovanteUrl;
    }
    public void setComprovanteUrl(String comprovanteUrl){
    	this.comprovanteUrl = comprovanteUrl;
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