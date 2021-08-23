package com.example.accessingdatarest;

import java.time.LocalDate;

public class TransacaoDTO {
	
	private Long idContaRemetente;
	private Long idContaDestinatario;
    private Double valor;
    private LocalDate dataTransacao;
    private StatusTransacao status;
	private Integer numParcelas;

	public Long getIdContaRemetente() {
		return idContaRemetente;
	}
	public void setIdContaRemetente(Long idContaRemetente) {
		this.idContaRemetente = idContaRemetente;
	}
	public Long getIdContaDestinatario() {
		return idContaDestinatario;
	}
	public void setIdContaDestinatario(Long idContaDestinatario) {
		this.idContaDestinatario = idContaDestinatario;
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
	public Integer getNumParcelas() {
		return numParcelas;
	}
	public void setNumParcelas(Integer numParcelas) {
		this.numParcelas = numParcelas;
	}
}
