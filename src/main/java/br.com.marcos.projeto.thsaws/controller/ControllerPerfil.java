package br.com.marcos.projeto.thsaws.controller;


import br.com.marcos.projeto.thsaws.dto.DtoPerfil;
import br.com.marcos.projeto.thsaws.service.ServicePerfil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControllerPerfil {

    @Autowired
    private ServicePerfil servicePerfil;

    @GetMapping("/perfil")
    public ModelAndView perfil() {
        return servicePerfil.paginaPerfil();
    }

    @GetMapping("/editar/{id}")
    public ModelAndView perfilUsuario(@PathVariable Long id, DtoPerfil dtoPerfil, HttpServletRequest request) {
        return servicePerfil.editarGet(id, dtoPerfil,request);
    }

    @PostMapping("/editarPerfil/{id}")
    public ModelAndView editarPerfil(@PathVariable Long id, DtoPerfil dto, BindingResult bindingResult, HttpServletRequest request) {
        return servicePerfil.editarPost(id, dto, bindingResult, request);
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deletar(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        return servicePerfil.sairDaConta(id, request, response);
    }
}