package br.com.marcos.projeto.thsaws.controller;


import br.com.marcos.projeto.thsaws.dto.DtoCadastro;
import br.com.marcos.projeto.thsaws.service.ServiceCadastro;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControllerThsCadastro {

    @Autowired
    private ServiceCadastro serviceCadastro;

    @GetMapping("/cadastro")
    public ModelAndView cadastro() {
        return serviceCadastro.paginaCadastro();
    }

    @PostMapping("/cadastro")
    public ModelAndView cadastrar( DtoCadastro dtoParametrosDoCadastro, BindingResult bindingResult, HttpSession session) {
        return serviceCadastro.cadastrando(dtoParametrosDoCadastro, bindingResult,session);
    }

}