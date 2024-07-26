package br.com.marcos.projeto.thsaws.dto;

import br.com.marcos.projeto.thsaws.model.ThsUsuario;
import br.com.marcos.projeto.thsaws.model.ThsEntrar;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoPerfil {

    @NotBlank
    @NotNull
    private String nome;

    @NotBlank
    @NotNull
    private String usuario;

    @NotBlank
    @NotNull
    private String email;


    public ThsUsuario requisicaoCadastro(){
        ThsUsuario cadastro = new ThsUsuario();
        cadastro.setNome(this.nome);
        cadastro.setUsuario(this.usuario);
        cadastro.setEmail(this.email);
        return cadastro;
    }


    public ThsEntrar requisicaoEntrar(){
        ThsEntrar entrar = new ThsEntrar();
        entrar.setNome(this.nome);
        entrar.setUsuario(this.usuario);
        entrar.setEmail(this.email);
        return entrar;
    }

    public void fromDtoCadastro(ThsUsuario thsCadastro) {
        this.nome = thsCadastro.getNome();
        this.usuario = thsCadastro.getUsuario();
        this.email = thsCadastro.getEmail();
    }

    public void fromDtoEntrar(ThsEntrar thsEntrar) {
        this.nome = thsEntrar.getNome();
        this.usuario = thsEntrar.getUsuario();
        this.email = thsEntrar.getEmail();
    }
}
