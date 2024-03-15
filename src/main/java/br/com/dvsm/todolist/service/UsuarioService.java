package br.com.dvsm.todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.dvsm.todolist.model.Usuario;
import br.com.dvsm.todolist.model.UsuarioDto;
import br.com.dvsm.todolist.repository.UsuarioRepository;

public class UsuarioService implements UserDetailsService {

    private UsuarioRepository repository;

   

    public UsuarioService(PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository) {
        this.repository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.buscarPorLogin(username).orElseThrow(() -> new UsernameNotFoundException("Usuário '" + username + "' não encontrado"));
    }

}
