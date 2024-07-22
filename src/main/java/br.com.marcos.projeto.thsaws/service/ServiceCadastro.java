package br.com.marcos.projeto.thsaws.service;

import br.com.marcos.projeto.thsaws.dto.DtoCadastro;
import br.com.marcos.projeto.thsaws.model.ThsCadastro;
import br.com.marcos.projeto.thsaws.repository.RepositoryCadastro;
import br.com.marcos.projeto.thsaws.repository.RepositoryEntrar;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;


@Service
public class ServiceCadastro {

    @Autowired
    private RepositoryCadastro repoCadastro;

    @Autowired
    private RepositoryEntrar repositoryEntrar;


    public ModelAndView cadastro() {

        ModelAndView view = new ModelAndView("cadastro");

        DtoCadastro dtoCadastro = new DtoCadastro();

        view.addObject("dtoCadastro", new ThsCadastro());
        return view;
    }

    public ModelAndView adicionar(@Valid DtoCadastro dtoParametrosDoCadastro, BindingResult bindingResult, HttpSession session) {
        ModelAndView mv = new ModelAndView("cadastro");

        ThsCadastro thsCadastro = dtoParametrosDoCadastro.requisicao();

        if (bindingResult.hasErrors()) {
            System.out.println(" **** Ocorreu um erro na pagina de cadastro !! **** ");
            System.out.println(thsCadastro.toString());
            return mv;
        }

        var verificacaoUsuario = repoCadastro.findByUsuario(thsCadastro.getUsuario());

        if (verificacaoUsuario.isEmpty()) {

            System.out.println("Entrou no método is empty !!!!!!!!");

            this.repoCadastro.save(thsCadastro);

            session.setAttribute("usuario", thsCadastro.getUsuario()); // o nome do usuario foi armazenado na sessão após um cadastro bem sucedido
            session.setAttribute("id", thsCadastro.getId());

            return new ModelAndView("redirect:/");
        } else {
            System.err.println("Informações estão incorretas !");
            bindingResult.rejectValue("usuario", "error.dtoCadastro", "O Usuário já existe !");
        }

        return mv;
    }
}