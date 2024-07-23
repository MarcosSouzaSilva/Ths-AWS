package br.com.marcos.projeto.thsaws.service;

import br.com.marcos.projeto.thsaws.dto.DtoCadastro;
import br.com.marcos.projeto.thsaws.model.ThsCadastro;
import br.com.marcos.projeto.thsaws.repository.RepositoryCadastro;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;


@Service
public class ServiceCadastro {

    @Autowired
    private RepositoryCadastro repoCadastro;


    public ModelAndView paginaCadastro() {

        ModelAndView view = new ModelAndView("cadastro");

        DtoCadastro dtoCadastro = new DtoCadastro();

        view.addObject("dtoCadastro", new ThsCadastro());
        return view;
    }

    public ModelAndView cadastrando(@Valid DtoCadastro dtoCadastro, BindingResult bindingResult, HttpSession session) {
        ModelAndView mv = new ModelAndView("cadastro");

        ThsCadastro thsCadastro = dtoCadastro.requisicao();

        if (bindingResult.hasErrors()) {
            System.err.println(" **** Ocorreu um erro na pagina de cadastro !! **** ");

            return mv;
        }

        var verificacaoUsuarioExistente = repoCadastro.findByUsuario(thsCadastro.getUsuario());

        if (verificacaoUsuarioExistente.isEmpty()) {

            this.repoCadastro.save(thsCadastro);

            session.setAttribute("usuario", thsCadastro.getUsuario()); // o nome do usuario foi armazenado na sessão após um cadastro bem sucedido
            session.setAttribute("id", thsCadastro.getId());

            return new ModelAndView("redirect:/");

        }

        if (thsCadastro.getNome() == null || thsCadastro.getNome().isEmpty()) {
            bindingResult.rejectValue("nome", "error.required", "Os campos precisam ser preenchidos !");

        } else {
            bindingResult.rejectValue("usuario", "error.dtoCadastro", "O Usuário já existe !");
        }
        return mv;
    }
}