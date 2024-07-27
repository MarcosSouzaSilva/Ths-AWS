package br.com.marcos.projeto.thsaws.service;

import br.com.marcos.projeto.thsaws.dto.DtoPerfil;
import br.com.marcos.projeto.thsaws.model.ThsUsuario;
import br.com.marcos.projeto.thsaws.repository.RepositoryCadastro;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.Duration;

@Service
public class ServicePerfil {

    @Autowired
    private RepositoryCadastro repositoryCadastro;


    public ModelAndView paginaPerfil() {

        ModelAndView mv = new ModelAndView("perfil");
        DtoPerfil dtoPerfil = new DtoPerfil();
        mv.addObject("dtoPerfil", dtoPerfil);
        return mv;

    }

    public ModelAndView editarPost(@RequestParam(name = "id") Long id, @Valid DtoPerfil dtoPerfil, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("perfil");

        ThsUsuario cadastro = dtoPerfil.requisicaoCadastro();

        var infoUsuario = repositoryCadastro.findByUsuario(cadastro.getUsuario());

        var emailInvalido = cadastro.getEmail().contains("@gmail.com") || cadastro.getEmail().contains("@outlook.com") || cadastro.getEmail().contains("@hotmail.com");

        mv.addObject("dtoPerfil", dtoPerfil);

        if (bindingResult.hasErrors()) {
            System.err.println("Houve erro do tipo bindingResult");
            return mv;
        }

        if (!emailInvalido){
            bindingResult.rejectValue("email", "error.dtoCadastro", "Email inválido !");
            return mv;
        }

        Cookie[] cookies = request.getCookies();
        Long usuarioId = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("id".equals(cookie.getName())) {
                    usuarioId = Long.parseLong(cookie.getValue());
                }
            }
        }

        if (usuarioId != null) {

            var usuarioOpt = repositoryCadastro.findById(usuarioId);

            if (usuarioOpt.isPresent()) {
                ThsUsuario usuario = usuarioOpt.get();
                if (!usuario.getUsuario().equals(cadastro.getUsuario()) && repositoryCadastro.findByUsuario(cadastro.getUsuario()).isPresent()) {

                    bindingResult.rejectValue("usuario", "error.dtoPerfil", "O Usuario ja existe");
                    return mv;
                }

                boolean update = false;

                if (!usuario.getNome().equals(cadastro.getNome())) {
                    usuario.setNome(cadastro.getNome());
                    update = true;
                }
                if (!usuario.getEmail().equals(cadastro.getEmail())) {
                    usuario.setEmail(cadastro.getEmail());
                    update = true;
                }
                if (!usuario.getUsuario().equals(cadastro.getUsuario())) {
                    usuario.setUsuario(cadastro.getUsuario());
                    update = true;
                }

                if (update) {

                    repositoryCadastro.save(usuario);

                    request.getSession().setAttribute("usuario", usuario.getUsuario());
                    request.getSession().setAttribute("id", usuario.getId());

                    // Atualizar cookies
                    Cookie userCookie = new Cookie("usuario", usuario.getUsuario());
                    userCookie.setMaxAge((int) Duration.ofMinutes(5).getSeconds());
                    userCookie.setPath("/");
                    userCookie.setHttpOnly(true);
                    userCookie.setSecure(true);
                    response.addCookie(userCookie);

                    Cookie idCookie = new Cookie("id", usuario.getId().toString());
                    idCookie.setMaxAge((int) Duration.ofMinutes(5).getSeconds());
                    idCookie.setPath("/");
                    idCookie.setHttpOnly(true);
                    idCookie.setSecure(true);
                    response.addCookie(idCookie);

                    System.out.println("Informações atualizadas com sucesso");

                }
            }
        } else {
            System.err.println("Usuario não existe !!");
        }
        return new ModelAndView("redirect:/");
    }

    public ModelAndView editarGet(@RequestParam(name = "id") Long id, DtoPerfil dtoPerfil, HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("perfil");

        var optionalCadastro = repositoryCadastro.findById(id);

        Long usuarioLogadoId = (Long) request.getSession().getAttribute("id");

        if (usuarioLogadoId == null) {
            System.err.println("Usuário não está logado. Redirecionando para a página inicial.");
            return new ModelAndView("redirect:/");
        }

        if (optionalCadastro.isPresent()) {

            ThsUsuario cadastro = optionalCadastro.get();

            dtoPerfil.fromDtoCadastro(cadastro);

            mv.addObject("dtoPerfil", dtoPerfil);

            mv.addObject("usuarioLogadoId", usuarioLogadoId);


        } else {
            System.err.println("Id não encontrado no banco");
        }

        return mv;
    }

    public ModelAndView sairDaConta(@RequestParam(name = "id") Long id, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("perfil");

        var verificandoUsuario = repositoryCadastro.findById(id);

        if (verificandoUsuario.isPresent()) {

            Long usuarioLogadoId = (Long) request.getSession().getAttribute("id");

            mv.addObject("usuarioLogadoId", usuarioLogadoId);

            repositoryCadastro.deleteById(verificandoUsuario.get().getId());

            HttpSession session = request.getSession(false); // Obtendo a Sessão Atual

            if (session != null) {
                session.invalidate(); // Invalidando a Sessão
            }

            Cookie[] cookies = request.getCookies(); // Obtém Todos os Cookies da Requisição

            if (cookies != null) { // Verifica se Existe Algum Cookie
                for (Cookie cookie : cookies) {
                    if ("usuario".equals(cookie.getName()) || "id".equals(cookie.getName())) { // Verifica se o Nome do Cookie é "usuario" ou "id"
                        cookie.setMaxAge(0); // indica que ele deve ser removido, por causa do valor 0
                        cookie.setPath("/");
                        response.addCookie(cookie);
                    }
                }
            }

            return new ModelAndView("redirect:/");

        } else {
            return mv;

        }
    }
}