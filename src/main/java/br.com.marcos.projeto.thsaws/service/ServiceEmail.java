package br.com.marcos.projeto.thsaws.service;
import br.com.marcos.projeto.thsaws.controller.ControllerThsEmail;
import br.com.marcos.projeto.thsaws.dto.DtoEmail;
import br.com.marcos.projeto.thsaws.model.ThsEmail;
import br.com.marcos.projeto.thsaws.repository.RepositoryEmail;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;

@Service
public class ServiceEmail {



    @Autowired
    private RepositoryEmail repository;

    public ModelAndView index(HttpSession session) {

        ModelAndView view = new ModelAndView("index");

        DtoEmail parametros = new DtoEmail();

        String usuarioLogado = (String)session.getAttribute("usuario");

        Long usuarioLogadoId = (Long)session.getAttribute("id");

        view.addObject("usuarioLogadoId", usuarioLogadoId);

        view.addObject("usuarioLogado", usuarioLogado);

        view.addObject("parametros", parametros);

        return view;
    }


    public ModelAndView enviar(@Valid @ModelAttribute("parametros") DtoEmail parametros, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("Erros de validação encontrados");
            return new ModelAndView("/");
        } else {
            ThsEmail archAWS = parametros.requisicao();
            this.repository.save(archAWS);
            return new ModelAndView("redirect:/");
        }
    }
}