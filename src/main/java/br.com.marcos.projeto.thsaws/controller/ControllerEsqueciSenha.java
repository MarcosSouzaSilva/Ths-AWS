package br.com.marcos.projeto.thsaws.controller;

import br.com.marcos.projeto.thsaws.dto.DtoRecSenha;
import br.com.marcos.projeto.thsaws.service.ServiceRecSenha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControllerEsqueciSenha {

    @Autowired
    private ServiceRecSenha serviceRecSenha;

    @GetMapping("/esqueciSenha")
    public ModelAndView pagina() {
        return serviceRecSenha.pagina();
    }

    @PostMapping("/esqueciSenha")
    public ModelAndView esqueciSenha(DtoRecSenha dtoRecSenha, BindingResult bindingResult) {
        return serviceRecSenha.recuperarSenha(dtoRecSenha, bindingResult);
    }
}