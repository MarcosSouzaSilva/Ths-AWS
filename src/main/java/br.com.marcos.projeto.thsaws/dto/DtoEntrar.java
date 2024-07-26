package br.com.marcos.projeto.thsaws.dto;

import br.com.marcos.projeto.thsaws.model.ThsUsuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoEntrar {

    @NotBlank
    @NotNull
    private String usuario;

    @NotBlank
    @NotNull
    private String senha;

    public ThsUsuario requisicao (){
        ThsUsuario cadastro = new ThsUsuario();
        cadastro.setUsuario(this.usuario);
        cadastro.setSenha(this.senha);
        return cadastro;
    }
}