package br.com.dvsm.todolist.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tarefas")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String descricao;
    @Column(nullable = false)
    private LocalDate dataCriacao = LocalDate.now();
    @Column(nullable = false)
    private LocalDate dataConclusao;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusTarefa status;

    public Tarefa() {
    }

    public Tarefa(String descricao, LocalDate dataCriacao, LocalDate dataConclusao, StatusTarefa status) {
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.dataConclusao = dataConclusao;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public StatusTarefa getStatus() {
        return status;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public void setStatus(StatusTarefa status) {
        this.status = status;
    }

        @Override
    public int hashCode() {
        return Objects.hash(this.id, this.descricao);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Tarefa t = (Tarefa) o;

        return Objects.equals(this.id, t.getId())
                && Objects.equals(this.descricao, t.getDescricao());
    }

}
