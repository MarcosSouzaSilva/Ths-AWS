package br.com.marcos.projeto.thsaws.dto;

import br.com.marcos.projeto.thsaws.model.ThsEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoEmail {

    @NotBlank(message = "O nome não pode estar vazio !")
    @NotNull
    private String assunto;

    @NotBlank(message = "A descrição não pode estar vazia !")
    @NotNull
    private String mensagem;


    public ThsEmail requisicao() {
        ThsEmail thsEmail = new ThsEmail();
        thsEmail.setAssunto(this.assunto);
        thsEmail.setMensagem(this.mensagem);
        return thsEmail;
    }
}