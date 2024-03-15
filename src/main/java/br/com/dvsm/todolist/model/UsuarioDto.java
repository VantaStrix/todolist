package br.com.dvsm.todolist.model;

public record UsuarioDto(String login,
        String nome,
        String senha,
        boolean ativo) {

    public UsuarioDto(String login, String nome, String senha) {
        this(login, nome, senha, true);
    }

}
