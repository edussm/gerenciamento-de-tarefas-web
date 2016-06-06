package br.fpu.taw.tarefas.controller;

import java.util.Collection;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.fpu.taw.tarefas.domain.Tarefa;
import br.fpu.taw.tarefas.repository.TarefaRepository;

@RestController
@RequestMapping("/api/v1/tarefa")
public class TarefaController {
	private TarefaRepository tarefaRepository;
	
	@Autowired
	public TarefaController(TarefaRepository tarefaRepository) {
		this.tarefaRepository = tarefaRepository;
	}
	
	@RequestMapping(value = "/{idTarefa}", method = RequestMethod.GET)
	public ResponseEntity<Tarefa> get(@PathVariable @NotNull Long idTarefa) {
		Tarefa tarefa = tarefaRepository.findOne(idTarefa);
		if (tarefa != null) {
			return ResponseEntity.ok(tarefa);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<Tarefa>> getAll() {
		return ResponseEntity.ok(tarefaRepository.findAll());
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Tarefa> create(@RequestBody Tarefa tarefa) {
		tarefa.setDataCriacao(new Date());
		tarefa.setCriador(getAuthenticatedUserName());
		Tarefa novaTarefa = tarefaRepository.save(tarefa);
		return ResponseEntity.ok(novaTarefa);
	}
	
	private String getAuthenticatedUserName() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user.getUsername();
	}

	@RequestMapping(value = "/{idTarefa}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable @NotNull Long idTarefa) {
			tarefaRepository.delete(idTarefa);
			return ResponseEntity.ok().build();
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Tarefa> edit(@RequestBody @NotNull Tarefa tarefa) {
		if (tarefa.getId() == null 
				|| !tarefaRepository.exists(tarefa.getId())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		Tarefa novaTarefa = tarefaRepository.save(tarefa);
		return ResponseEntity.ok(novaTarefa);
	}
}
