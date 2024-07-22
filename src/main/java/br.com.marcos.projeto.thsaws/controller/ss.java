package br.com.marcos.projeto.thsaws.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ss {

        @GetMapping("/teste")
        public String home(ModelMap model) {
            Map<String, String> usuario = new HashMap<>();
            usuario.put("id", "1");
            usuario.put("nome", "João da Silva");
            usuario.put("tipo", "funcionario");

            model.addAttribute("usuario", usuario);

            return "teste";
        }
    }