package com.paineltarefas.api.repository;

import com.paineltarefas.api.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetoRepository extends JpaRepository<Projeto, Integer> {

}
