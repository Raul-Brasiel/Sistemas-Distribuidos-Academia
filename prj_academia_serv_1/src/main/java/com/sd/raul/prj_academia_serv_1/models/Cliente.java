package com.sd.raul.prj_academia_serv_1.models;

import java.io.Serializable;
import java.time.LocalDate;
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
@Table(name = "clientes")
public class Cliente implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 11, unique = true)
    private String cpf;

    @Column(nullable = false, length = 150, unique = true)
    private String email;

    @Column(length = 20)
    private String telefone;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(length = 1)
    private String sexo;

    @Column(length = 255)
    private String endereco;

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

    public String getCpf(){
    	return cpf;
    }
    public void setCpf(String cpf){
    	this.cpf = cpf;
    }

    public String getEmail(){
    	return email;
    }
    public void setEmail(String email){
    	this.email = email;
    }

    public String getTelefone(){
    	return telefone;
    }
    public void setTelefone(String telefone){
    	this.telefone = telefone;
    }

    public LocalDate getDataNascimento(){
    	return dataNascimento;
    }
    public void setDataNascimento(LocalDate dataNascimento){
    	this.dataNascimento = dataNascimento;
    }

    public String getSexo(){
    	return sexo;
    }
    public void setSexo(String sexo){
    	this.sexo = sexo;
    }

    public String getEndereco(){
    	return endereco;
    }
    public void setEndereco(String endereco){
    	this.endereco = endereco;
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