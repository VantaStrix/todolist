package br.com.dvsm.todolist.controller;

import org.springframework.web.bind.annotation.RestController;

import br.com.dvsm.todolist.config.security.JwtService;
import br.com.dvsm.todolist.model.UsuarioDetailsDto;
import br.com.dvsm.todolist.model.Usuario;
import br.com.dvsm.todolist.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity efetuarLogin(@RequestBody UsuarioDetailsDto usuarioDto) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                usuarioDto.login(), usuarioDto.senha());
        System.out.println(authToken);

        try {
            authenticationManager.authenticate(authToken);
            Usuario usuario = usuarioRepository.buscarPorLogin(usuarioDto.login())
                    .orElseThrow(() -> new UsernameNotFoundException(
                            "Usuário '" + usuarioDto.login() + "' não encontrado."));
            // System.out.println(loginDto);
            String tokenJwt = jwtService.generateTokenFromUsuario(usuario);
            return ResponseEntity.ok(tokenJwt);
        } catch (AuthenticationException ex) {
            System.out.println(ex);
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }
    }

}
