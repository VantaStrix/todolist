package br.com.dvsm.todolist.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.dvsm.todolist.model.Tarefa;
import br.com.dvsm.todolist.model.Usuario;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    @Query("SELECT t FROM Tarefa AS t WHERE t.descricao ILIKE :descricao%")
    List<Tarefa> buscarPorDescricaoParcial(String descricao);

    @Query("SELECT t FROM Tarefa AS t")
    List<Usuario> buscarTodos();

}
