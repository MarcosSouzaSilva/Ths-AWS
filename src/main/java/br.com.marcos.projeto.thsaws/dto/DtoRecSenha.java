package br.com.marcos.projeto.thsaws.dto;

import br.com.marcos.projeto.thsaws.model.ThsCadastro;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoRecSenha {

    @Email
    @NotNull
    @NotBlank(message = "O email não pode estar vazio !")
    private String email;


    public DtoRecSenha() {
    }

    public ThsCadastro requisicao (){
        ThsCadastro thsCadastro = new ThsCadastro();
        thsCadastro.setEmail(this.email);
        return thsCadastro;
    }

}