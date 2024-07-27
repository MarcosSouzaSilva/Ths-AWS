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

    @GetMapping("/editar")
    public ModelAndView perfilUsuario(@RequestParam(name = "id") Long id, DtoPerfil dtoPerfil, HttpServletRequest request) {
        return servicePerfil.editarGet(id, dtoPerfil,request);
    }

    @PostMapping("/editarPerfil")
    public ModelAndView editarPerfil(@RequestParam(name = "id") Long id, DtoPerfil dto, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        return servicePerfil.editarPost(id, dto, bindingResult, request, response);
    }

    @GetMapping("/delete")
    public ModelAndView deletar(@RequestParam(name = "id") Long id, HttpServletRequest request, HttpServletResponse response) {
        return servicePerfil.sairDaConta(id, request, response);
    }
}