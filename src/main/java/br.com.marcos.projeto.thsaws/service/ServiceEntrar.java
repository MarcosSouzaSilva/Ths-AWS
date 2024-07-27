package br.com.marcos.projeto.thsaws.service;

import br.com.marcos.projeto.thsaws.dto.DtoEntrar;
import br.com.marcos.projeto.thsaws.model.ThsUsuario;
import br.com.marcos.projeto.thsaws.repository.RepositoryCadastro;
import br.com.marcos.projeto.thsaws.repository.RepositoryEntrar;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.time.Duration;


@Service
public class ServiceEntrar {

    @Autowired
    private RepositoryCadastro repositoryCadastro;

    public ModelAndView paginaLogin() {

        ModelAndView view = new ModelAndView("entrar");

        DtoEntrar dtoEntrar = new DtoEntrar();

        view.addObject("dtoEntrar", dtoEntrar);

        return view;
    }

    public ModelAndView verificao(@Valid DtoEntrar dtoEntrar, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("entrar");

        ThsUsuario cadastro = dtoEntrar.requisicao();

        if (bindingResult.hasErrors()) {
            System.out.println(cadastro.toString());
            return mv;

        }

        var senhaInvalida = cadastro.getSenha().length() <= 7 || cadastro.getSenha().length() >= 19;

        if (senhaInvalida) {
            bindingResult.rejectValue("senha", "error.dtoCadastro", "A senha deve possuir entre 8 a 20 caracteres.");
            return mv;
        }

        var credenciaisValidas = repositoryCadastro.findByUsuarioAndSenha(cadastro.getUsuario(), cadastro.getSenha());

        if (credenciaisValidas.isPresent()) {

            ThsUsuario thsCadastro = credenciaisValidas.get();

            request.getSession().setAttribute("usuario", thsCadastro.getUsuario()); // o nome do usuario foi armazenado na sessão após um cadastro bem sucedido
            request.getSession().setAttribute("id", thsCadastro.getId());

            Cookie[] cookies = request.getCookies();
            Long usuarioId = null;
            for (Cookie cookie : cookies) {
                if ("id".equals(cookie.getName())) {
                    usuarioId = Long.parseLong(cookie.getValue());
                }
            }

            // Atualizar cookies
            Cookie userCookie = new Cookie("usuario", thsCadastro.getUsuario());
            userCookie.setMaxAge((int) Duration.ofMinutes(5).getSeconds());
            userCookie.setPath("/");
            userCookie.setHttpOnly(true);
            userCookie.setSecure(true);
            response.addCookie(userCookie);

            Cookie idCookie = new Cookie("id", thsCadastro.getId().toString());
            idCookie.setMaxAge((int) Duration.ofMinutes(5).getSeconds());
            idCookie.setPath("/");
            idCookie.setHttpOnly(true);
            idCookie.setSecure(true);
            response.addCookie(idCookie);

            return new ModelAndView("redirect:/");


        } else {

            bindingResult.rejectValue("senha", "error.dtoEntrar", "Usuário ou senha inválidos.");
        }

        return mv;
    }
}