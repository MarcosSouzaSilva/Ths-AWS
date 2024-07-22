package br.com.marcos.projeto.thsaws.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class ThsEmail {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank
    @NotNull
    private String nome;

    @NotBlank
    @NotNull
    @Email
    private String email;

    @NotBlank
    @NotNull
    private String descricao;

    public ThsEmail() {
    }


    @Override
    public String toString() {
        return "ArchAWS{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}