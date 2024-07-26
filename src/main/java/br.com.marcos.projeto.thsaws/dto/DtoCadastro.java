package br.com.marcos.projeto.thsaws.dto;

import br.com.marcos.projeto.thsaws.model.ThsUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class DtoCadastro {

    @NotBlank
    @NotNull
    private String nome;

    @Email
    @NotBlank
    @NotNull
    private String email;

    @NotBlank
    @NotNull
    private String usuario;

    @NotBlank
    @NotNull
    private String senha;

    public ThsUsuario requisicao() {
        ThsUsuario cadastro = new ThsUsuario();
        cadastro.setNome(this.nome);
        cadastro.setEmail(this.email);
        cadastro.setUsuario(this.usuario);
        cadastro.setSenha(this.senha);
        return cadastro;
    }

}