package br.com.marcos.projeto.thsaws.controller;


import br.com.marcos.projeto.thsaws.dto.DtoEmail;
import br.com.marcos.projeto.thsaws.model.ThsEmail;
import br.com.marcos.projeto.thsaws.repository.RepositoryEmail;
import br.com.marcos.projeto.thsaws.service.ServiceEmail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControllerThsEmail {

    @Autowired
    private ServiceEmail serviceEmail;

    @GetMapping("/")
    public ModelAndView index(HttpServletRequest request) {
        return serviceEmail.index(request);
    }

    @PostMapping("/envioDeEmail")
    public ModelAndView enviar(@Valid DtoEmail parametros, BindingResult bindingResult) {
       return serviceEmail.enviar(parametros, bindingResult);
    }


}