package br.com.marcos.projeto.thsaws.service;
import br.com.marcos.projeto.thsaws.dto.DtoEntrar;
import br.com.marcos.projeto.thsaws.model.ThsEntrar;
import br.com.marcos.projeto.thsaws.repository.RepositoryCadastro;
import br.com.marcos.projeto.thsaws.repository.RepositoryEntrar;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;


@Service
public class ServiceEntrar {

    @Autowired
    private RepositoryCadastro repositoryCadastro;

    @Autowired
    private RepositoryEntrar repositoryEntrar;


    public ModelAndView entrar() {

        ModelAndView view = new ModelAndView("entrar");

        DtoEntrar dtoEntrar = new DtoEntrar();

        view.addObject("dtoEntrar", dtoEntrar);

        System.out.println("Método entrar");

        return view;
    }

    public ModelAndView verificao(@Valid DtoEntrar dtoEntrar, BindingResult bindingResult, HttpSession session) {

        System.out.println("Entrou no método entrar de login -----------------------");

        ModelAndView view = new ModelAndView("entrar");

        ThsEntrar thsEntrar = dtoEntrar.requisicao();

        if (bindingResult.hasErrors()) {
            System.out.println(thsEntrar.toString());
            return view;

        }

        var credenciaisValidas = repositoryCadastro.findByUsuarioAndSenha(thsEntrar.getUsuario(), thsEntrar.getSenha());

        if (credenciaisValidas.isPresent()) {

            session.setAttribute("usuario", thsEntrar.getUsuario()); // o nome do usuario foi armazenado na sessão após um cadastro bem sucedido
            session.setAttribute("id", thsEntrar.getId());

            return new ModelAndView("redirect:/");

        } else {

            bindingResult.rejectValue("senha", "error.dtoEntrar", "Usuário ou senha incorretos.");
        }

        return view;
    }
}