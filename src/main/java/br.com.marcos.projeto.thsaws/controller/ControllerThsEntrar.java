package br.com.marcos.projeto.thsaws.controller;


import br.com.marcos.projeto.thsaws.dto.DtoEntrar;
import br.com.marcos.projeto.thsaws.service.ServiceEntrar;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControllerThsEntrar {

    @Autowired
    private ServiceEntrar serviceEntrar;


    @GetMapping("/entrar")
    public ModelAndView entrar() {

        return serviceEntrar.entrar();
    }


    @PostMapping("/entrar")
    public ModelAndView verificacao(@Valid @ModelAttribute("dtoEntrar") DtoEntrar dtoEntrar, BindingResult bindingResult, HttpSession session) {

        return serviceEntrar.verificao(dtoEntrar, bindingResult, session);
    }
}