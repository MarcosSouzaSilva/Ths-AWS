package br.com.marcos.projeto.thsaws.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "usuario")})
public class ThsUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String email;

    @NotBlank
    private String usuario;

    @NotBlank
    private String senha;


    public ThsUsuario() {
    }

    public ThsUsuario(String nome, String email, String usuario, String senha) {
        this.nome = nome;
        this.email = email;
        this.usuario = usuario;
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Usuário :" + usuario + " Senha:" +  senha;
    }
}