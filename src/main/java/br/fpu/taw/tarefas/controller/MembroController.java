package br.fpu.taw.tarefas.controller;

import java.util.Collection;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.fpu.taw.tarefas.domain.Membro;
import br.fpu.taw.tarefas.repository.MembroRepository;

@RestController
@RequestMapping("/api/v1/membro")
public class MembroController {
	private MembroRepository membroRepository;
	
	@Autowired
	public MembroController(MembroRepository membroRepository) {
		this.membroRepository = membroRepository;
	}
	
	@RequestMapping(value = "/{idMembro}", method = RequestMethod.GET)
	public ResponseEntity<Membro> get(@PathVariable @NotNull Long idMembro) {
		Membro membro = membroRepository.findOne(idMembro);
		if (membro != null) {
			return ResponseEntity.ok(membro);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<Membro>> getAll() {
		return ResponseEntity.ok(membroRepository.findAll());
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Membro> create(@RequestBody Membro membro) {
		membro.setDataCriacao(new Date());
		Membro novoMembro = membroRepository.save(membro);
		return ResponseEntity.ok(novoMembro);
	}

	@RequestMapping(value = "/{idMembro}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable @NotNull Long idMembro) {
			membroRepository.delete(idMembro);
			return ResponseEntity.ok().build();
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Membro> edit(@RequestBody @NotNull Membro membro) {
		if (membro.getId() == null 
				|| !membroRepository.exists(membro.getId())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		Membro novoMembro = membroRepository.save(membro);
		return ResponseEntity.ok(novoMembro);
	}
	
}
