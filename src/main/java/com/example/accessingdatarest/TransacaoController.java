package com.example.accessingdatarest;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RepositoryRestController
@RequestMapping(value="/transacoes")
public class TransacaoController {

	@Autowired 
    private ContaRepository contaRepository;
	@Autowired 
    private TransacaoRepository transacaoRepository;

    @RequestMapping(method = RequestMethod.POST, value = "/adicionar") 
    public @ResponseBody ResponseEntity<?> adicionar(@RequestBody Transacao transacao) {
    	transacao.setContaDestinatario(contaRepository.findById(transacao.getContaDestinatario().getId()).get());
    	transacao.setContaRemetente(contaRepository.findById(transacao.getContaRemetente().getId()).get());
    	transacao.setDataTransacao(LocalDate.now());
    	transacao.setStatus(StatusTransacao.EFETIVADA);
    	transacaoRepository.save(transacao);
    	transacao.getContaDestinatario().setSaldo(transacao.getContaDestinatario().getSaldo() + transacao.getValor());
    	transacao.getContaRemetente().setSaldo(transacao.getContaRemetente().getSaldo() - transacao.getValor());
    	contaRepository.save(transacao.getContaDestinatario());
    	contaRepository.save(transacao.getContaRemetente());
        return ResponseEntity.ok(transacao); 
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/agendar") 
    public @ResponseBody ResponseEntity<?> agendar(@RequestBody Transacao transacao) {
    	transacao.setContaDestinatario(contaRepository.findById(transacao.getContaDestinatario().getId()).get());
    	transacao.setContaRemetente(contaRepository.findById(transacao.getContaRemetente().getId()).get());
    	transacao.setStatus(StatusTransacao.AGENDADA);
    	transacaoRepository.save(transacao);
    	return ResponseEntity.ok(transacao); 
    }

    @RequestMapping(method = RequestMethod.POST, value = "/parcelar") 
    public @ResponseBody ResponseEntity<?> parcelar(@RequestBody TransacaoDTO transacaoDTO) {
    	Double valorParcela = transacaoDTO.getValor() / transacaoDTO.getNumParcelas();
    	transacaoDTO.setValor(valorParcela);
    	Transacao transacao;
    	LocalDate primeiraData = transacaoDTO.getDataTransacao();
    	for (int i = 0; i < transacaoDTO.getNumParcelas(); i++) {
    		transacaoDTO.setDataTransacao(primeiraData.plusMonths(i));
    		transacao = new Transacao(transacaoDTO.getIdContaRemetente(), transacaoDTO.getIdContaDestinatario(), transacaoDTO.getValor(), transacaoDTO.getDataTransacao());
    		agendar(transacao);
		}
    	return ResponseEntity.ok("FEITO"); 
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}/reverter") 
    public @ResponseBody ResponseEntity<?> reverter(@PathVariable Long id) {
    	Transacao transacao = transacaoRepository.findById(id).get();
    	if (StatusTransacao.EFETIVADA.equals(transacao.getStatus())) {    		
	    	transacao.setContaDestinatario(contaRepository.findById(transacao.getContaDestinatario().getId()).get());
	    	transacao.setContaRemetente(contaRepository.findById(transacao.getContaRemetente().getId()).get());
	    	transacao.setStatus(StatusTransacao.REVERTIDA);
	    	transacaoRepository.save(transacao);
	    	transacao.getContaDestinatario().setSaldo(transacao.getContaDestinatario().getSaldo() - transacao.getValor());
	    	transacao.getContaRemetente().setSaldo(transacao.getContaRemetente().getSaldo() + transacao.getValor());
	    	contaRepository.save(transacao.getContaDestinatario());
	    	contaRepository.save(transacao.getContaRemetente());
    	}
    	return ResponseEntity.ok(transacao); 
    }
    
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}/cancelar") 
    public @ResponseBody ResponseEntity<?> cancelar(@PathVariable Long id) {
    	Transacao transacao = transacaoRepository.findById(id).get();
    	if (StatusTransacao.AGENDADA.equals(transacao.getStatus())) {    		
    		transacao.setContaDestinatario(contaRepository.findById(transacao.getContaDestinatario().getId()).get());
    		transacao.setContaRemetente(contaRepository.findById(transacao.getContaRemetente().getId()).get());
    		transacao.setStatus(StatusTransacao.CANCELADA);
    		transacaoRepository.save(transacao);
    		transacao.getContaDestinatario().setSaldo(transacao.getContaDestinatario().getSaldo() - transacao.getValor());
    		transacao.getContaRemetente().setSaldo(transacao.getContaRemetente().getSaldo() + transacao.getValor());
    		contaRepository.save(transacao.getContaDestinatario());
    		contaRepository.save(transacao.getContaRemetente());
    	}
    	return ResponseEntity.ok(transacao); 
    }

}
