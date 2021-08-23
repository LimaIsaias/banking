package com.example.accessingdatarest;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Transacao {
	@Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Conta contaRemetente;

    @ManyToOne
    @JoinColumn
    private Conta contaDestinatario;

    @Column
    private Double valor;

    @Column
    private LocalDate dataTransacao;
    
    @Enumerated(EnumType.ORDINAL)
    private StatusTransacao status;

    public Transacao() {
    }

    public Transacao(Long idContaRemetente, Long idContaDestinatario, Double valor, LocalDate dataTransacao) {
		this.contaRemetente = new Conta();
		this.contaDestinatario = new Conta();
		contaRemetente.setId(idContaRemetente);
		contaDestinatario.setId(idContaDestinatario);
		this.valor = valor;
		this.dataTransacao = dataTransacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Conta getContaRemetente() {
		return contaRemetente;
	}

	public void setContaRemetente(Conta contaRemetente) {
		this.contaRemetente = contaRemetente;
	}

	public Conta getContaDestinatario() {
		return contaDestinatario;
	}

	public void setContaDestinatario(Conta contaDestinatario) {
		this.contaDestinatario = contaDestinatario;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public LocalDate getDataTransacao() {
		return dataTransacao;
	}

	public void setDataTransacao(LocalDate dataTransacao) {
		this.dataTransacao = dataTransacao;
	}

	public StatusTransacao getStatus() {
		return status;
	}

	public void setStatus(StatusTransacao status) {
		this.status = status;
	}

}
