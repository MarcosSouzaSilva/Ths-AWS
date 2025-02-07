package br.com.marcos.projeto.thsaws.service;

import br.com.marcos.projeto.thsaws.dto.DtoCadastro;
import br.com.marcos.projeto.thsaws.model.ThsUsuario;
import br.com.marcos.projeto.thsaws.repository.RepositoryCadastro;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.time.Duration;


@Service
public class ServiceCadastro {

    @Autowired
    private RepositoryCadastro repoCadastro;

    @Autowired
    private ServiceEmail serviceEmail;


    public ModelAndView paginaCadastro() {

        ModelAndView view = new ModelAndView("cadastro");

        view.addObject("dtoCadastro", new ThsUsuario());
        return view;
    }

    public ModelAndView cadastrando(@Valid DtoCadastro dtoCadastro, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelAndView mv = new ModelAndView("cadastro");

        ThsUsuario thsCadastro = dtoCadastro.requisicao();

        if (bindingResult.hasErrors()) {
            System.err.println(" **** Ocorreu um erro na pagina de cadastro !! **** ");
            return mv;
        }

        var verificacaoUsuarioExistente = repoCadastro.findByUsuario(thsCadastro.getUsuario());

        var senhaInvalida = thsCadastro.getSenha().length() <= 7 || thsCadastro.getSenha().length() >= 19;

        var emailInvalido = thsCadastro.getEmail().contains("@gmail.com") || thsCadastro.getEmail().contains("@outlook.com") || thsCadastro.getEmail().contains("@hotmail.com");


        if (verificacaoUsuarioExistente.isPresent()) {
            bindingResult.rejectValue("usuario", "error.dtoCadastro", "Este Usuário já existe !");
            return mv;
        }

        if (senhaInvalida) {
            bindingResult.rejectValue("senha", "error.dtoCadastro", "A senha deve possuir entre 8 a 20 caracteres.");
            return mv;
        }

        var emailExiste = repoCadastro.findByEmail(thsCadastro.getEmail());

        if (emailExiste.isPresent()){
            bindingResult.rejectValue("email", "error.dtoCadastro", "Já existe um usuario com este email !");
            return mv;
        }

        if (!emailInvalido){
            bindingResult.rejectValue("email", "error.dtoCadastro", "Email inválido !");
            return mv;
        }

            if (repoCadastro.findByUsuario(thsCadastro.getUsuario()).isEmpty()) {
                try {

                    this.repoCadastro.save(thsCadastro);

                    request.getSession().setAttribute("usuario", thsCadastro.getUsuario()); // o nome do usuario foi armazenado na sessão após um cadastro bem sucedido
                    request.getSession().setAttribute("id", thsCadastro.getId());

                    Cookie userCookie = new Cookie("usuario", thsCadastro.getUsuario());
                    userCookie.setMaxAge((int) Duration.ofMinutes(5).getSeconds()); // expira em 5 minutos
                    userCookie.setPath("/"); // disponível para toda a aplicação
                    userCookie.setHttpOnly(true); // significa que o cookie não pode ser acessado via JavaScript no navegador do cliente.
                    userCookie.setSecure(true); // isso significa que o cookie só será enviado para o servidor em conexões seguras, ou seja, via HTTPS.
                    response.addCookie(userCookie);

                    Cookie idCookie = new Cookie("id", String.valueOf(thsCadastro.getId())); // Corrigir aqui
                    idCookie.setMaxAge((int) Duration.ofMinutes(5).getSeconds());
                    idCookie.setPath("/");
                    idCookie.setHttpOnly(true);
                    idCookie.setSecure(true);
                    response.addCookie(idCookie);

                    response.getWriter().println("Atributo armazenado na sessão: " + thsCadastro.getId());

                    return new ModelAndView("redirect:/");
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ocorreu um erro ao processar o cadastro, tente novamente mais tarde.");
                    return null;
                }
            }
        return mv;
    }
}