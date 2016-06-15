package br.fpu.taw.tarefas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fpu.taw.tarefas.domain.Membro;

public interface MembroRepository extends JpaRepository<Membro, Long>{

}
