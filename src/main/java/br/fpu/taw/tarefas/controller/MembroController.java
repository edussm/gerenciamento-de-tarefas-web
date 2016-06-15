package br.fpu.taw.tarefas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.fpu.taw.tarefas.repository.MembroRepository;

@RestController
@RequestMapping("/api/v1/membro")
public class MembroController {
	private MembroRepository membroRepository;
	
	@Autowired
	public MembroController(MembroRepository membroRepository) {
		this.membroRepository = membroRepository;
	}
	
}
