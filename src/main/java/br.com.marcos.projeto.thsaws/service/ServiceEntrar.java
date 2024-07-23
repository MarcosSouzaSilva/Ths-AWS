package br.com.marcos.projeto.thsaws.service;
import br.com.marcos.projeto.thsaws.dto.DtoEntrar;
import br.com.marcos.projeto.thsaws.model.ThsCadastro;
import br.com.marcos.projeto.thsaws.repository.RepositoryCadastro;
import br.com.marcos.projeto.thsaws.repository.RepositoryEntrar;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;



@Service
public class ServiceEntrar {

    @Autowired
    private RepositoryCadastro repositoryCadastro;

    @Autowired
    private RepositoryEntrar repositoryEntrar;


    public ModelAndView paginaLogin() {

        ModelAndView view = new ModelAndView("entrar");

        DtoEntrar dtoEntrar = new DtoEntrar();

        view.addObject("dtoEntrar", dtoEntrar);

        return view;
    }

    public ModelAndView verificao(@Valid DtoEntrar dtoEntrar, BindingResult bindingResult, HttpSession session) {

        ModelAndView view = new ModelAndView("entrar");

        ThsCadastro cadastro = dtoEntrar.requisicao();

        if (bindingResult.hasErrors()) {
            System.out.println(cadastro.toString());
            return view;

        }

        var credenciaisValidas = repositoryCadastro.findByUsuarioAndSenha(cadastro.getUsuario(), cadastro.getSenha());

        if (credenciaisValidas.isPresent() ) {

            ThsCadastro thsCadastro =  credenciaisValidas.get();

            session.setAttribute("usuario", thsCadastro.getUsuario()); // o nome do usuario foi armazenado na sessão após um cadastro bem sucedido
            session.setAttribute("id", thsCadastro.getId());

            return new ModelAndView("redirect:/");

        } else {

            bindingResult.rejectValue("senha", "error.dtoEntrar", "Usuário ou senha incorretos.");
        }

        return view;
    }
}