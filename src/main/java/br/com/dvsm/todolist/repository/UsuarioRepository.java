package br.com.dvsm.todolist.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.dvsm.todolist.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario AS u WHERE u.login = :login")
    Optional<Usuario> buscarPorLogin(String login);

    @Query("SELECT u FROM Usuario AS u")
    List<Usuario> buscarTodos();

}
