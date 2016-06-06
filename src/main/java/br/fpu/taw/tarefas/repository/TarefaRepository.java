package br.fpu.taw.tarefas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fpu.taw.tarefas.domain.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long>{

}
