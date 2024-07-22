package br.com.marcos.projeto.thsaws.dto;

import br.com.marcos.projeto.thsaws.model.ThsEntrar;
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

    public ThsEntrar requisicao (){
        ThsEntrar thsEntrar = new ThsEntrar();
        thsEntrar.setUsuario(this.usuario);
        thsEntrar.setSenha(this.senha);
        return thsEntrar;
    }

}