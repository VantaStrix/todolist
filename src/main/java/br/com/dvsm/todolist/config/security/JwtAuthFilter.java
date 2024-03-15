package br.com.dvsm.todolist.config.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.dvsm.todolist.model.Usuario;
import br.com.dvsm.todolist.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (request.getRequestURI().equals("/login") && request.getMethod().equals("POST")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getJwtTokenFromrequest(request);

        if (token == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        try {
            String userLogin = jwtService.getSubjectFromToken(token);
            Usuario usuario = usuarioRepository.buscarPorLogin(userLogin)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário '" + userLogin + "' não encontrado"));
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usuario,
                    null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (JWTVerificationException ex) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().println(ex.getMessage().toUpperCase());
            return;
        }

        
        filterChain.doFilter(request, response);

    }

    private String getJwtTokenFromrequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            return authHeader.replace("Bearer ", "").trim();
        }

        return null;
    }

}
