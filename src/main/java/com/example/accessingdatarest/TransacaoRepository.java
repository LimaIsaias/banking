package com.example.accessingdatarest;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "transacoes", path = "transacoes")
public interface TransacaoRepository extends PagingAndSortingRepository<Transacao, Long> {
	List<Transacao> findByStatus(@Param("status") StatusTransacao status);
	List<Transacao> findByContaRemetenteId(@Param("id") Long id);
	List<Transacao> findByContaDestinatarioId(@Param("id") Long id);
}