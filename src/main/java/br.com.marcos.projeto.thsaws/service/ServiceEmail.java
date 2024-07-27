package br.com.marcos.projeto.thsaws.service;

import br.com.marcos.projeto.thsaws.dto.DtoEmail;
import br.com.marcos.projeto.thsaws.model.ThsEmail;
import br.com.marcos.projeto.thsaws.repository.RepositoryEmail;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

@Service
public class ServiceEmail {

    @Autowired
    private RepositoryEmail repository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    public String enviarEmailDeTexto(String assunto, String mensagem) {

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(remetente);
            simpleMailMessage.setTo("souzasilvam582@gmail.com");
            simpleMailMessage.setSubject(assunto);
            simpleMailMessage.setText(mensagem);
            javaMailSender.send(simpleMailMessage);

            return "Email Enviado";
        } catch (Exception e) {
            return "Erro ao enviar email" + e.getLocalizedMessage();
        }
    }

    public ModelAndView index(HttpServletRequest request) {

        ModelAndView view = new ModelAndView("index");

        // Recuperar dados dos cookies
        String usuario = null;
        Long id = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("usuario".equals(cookie.getName())) {
                    usuario = cookie.getValue();
                }
                if ("id".equals(cookie.getName())) {
                    try {
                        id = Long.parseLong(cookie.getValue());
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao converter ID do cookie: " + e.getMessage());
                    }
                }
            }
        }

        if (usuario != null && id != null) {
            request.getSession().setAttribute("usuario", usuario);
            request.getSession().setAttribute("id", id);
        }

        // Usar getSession().getAttribute() em vez de request.getAttribute()
        String usuarioLogado = (String) request.getSession().getAttribute("usuario"); // pega a sessão e o atributo setado anteriormente
        Long usuarioLogadoId = (Long) request.getSession().getAttribute("id");

        view.addObject("usuarioLogadoId", usuarioLogadoId);
        view.addObject("usuarioLogado", usuarioLogado);

        DtoEmail parametros = new DtoEmail();
        view.addObject("parametros", parametros);

        return view;
    }


    public ModelAndView enviar(@Valid DtoEmail parametros, BindingResult bindingResult) {

        ModelAndView mv = new ModelAndView("index");


        if (bindingResult.hasErrors()) {
            System.out.println("Erros de validação encontrados");
            return new ModelAndView("redirect:/");

        } else {
            ThsEmail thsEmail = parametros.requisicao();
            this.repository.save(thsEmail);

            enviarEmailDeTexto(thsEmail.getAssunto(), thsEmail.getMensagem());

            return new ModelAndView("redirect:/");
        }
    }
}