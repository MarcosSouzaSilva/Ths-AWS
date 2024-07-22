package br.com.marcos.projeto.thsaws.service;

import br.com.marcos.projeto.thsaws.dto.DtoPerfil;
import br.com.marcos.projeto.thsaws.model.ThsCadastro;
import br.com.marcos.projeto.thsaws.model.ThsEntrar;
import br.com.marcos.projeto.thsaws.repository.RepositoryCadastro;
import br.com.marcos.projeto.thsaws.repository.RepositoryEntrar;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Service
public class ServicePerfil {

    @Autowired
    private RepositoryCadastro repositoryCadastro;

    @Autowired
    private RepositoryEntrar repositoryEntrar;

    public ModelAndView paginaPerfil() {

        ModelAndView mv = new ModelAndView("perfil");
        DtoPerfil dtoPerfil = new DtoPerfil();
        mv.addObject("dtoPerfil", dtoPerfil);
        return mv;

    }

    public ModelAndView editarPost(@PathVariable Long id, @Valid DtoPerfil dtoPerfil, BindingResult bindingResult) {

        ModelAndView mv = new ModelAndView("perfil");

        ThsCadastro cadastro = dtoPerfil.requisicaoCadastro();

        ThsEntrar entrar = dtoPerfil.requisicaoEntrar();


        var usuarioExiste = repositoryCadastro.findByUsuario(cadastro.getUsuario());

        if (bindingResult.hasErrors()) {

            return mv;
        } else if (usuarioExiste.isPresent()) {

            bindingResult.rejectValue("usuario", "error.dtoPerfil", "O Usuario ja existe");
            return new ModelAndView("perfil");

        } else {

            mv.addObject("dtoPerfil", dtoPerfil);
            return mv;
        }
    }

    public ModelAndView editarGet(@PathVariable Long id, DtoPerfil dtoPerfil, HttpSession session) {

        ModelAndView mv = new ModelAndView("perfil");

        var optionalCadastro = repositoryCadastro.findById(id);


        if (optionalCadastro.isPresent()) {

            System.out.println("Entrou no is present");

            ThsCadastro cadastro = optionalCadastro.get();

            dtoPerfil.fromDtoPerfil(cadastro);

            mv.addObject("dtoPerfil", dtoPerfil);

            Long usuarioLogadoId = (Long) session.getAttribute("id");

            mv.addObject("usuarioLogadoId", usuarioLogadoId);

        } else {
            System.out.println("Id não encontrado no banco");
        }
        return mv;
    }

    public ModelAndView sairDaConta (@PathVariable Long id, HttpSession session) {
        ModelAndView mv = new ModelAndView("perfil");

        var verificandoUsuario = repositoryCadastro.findById(id);

        if (verificandoUsuario.isPresent()){

            Long usuarioLogadoId = (Long) session.getAttribute("id");

            mv.addObject("usuarioLogadoId", usuarioLogadoId);

            session.invalidate();
            repositoryCadastro.deleteById(verificandoUsuario.get().getId());
            return new ModelAndView("redirect:/");

        } else {
            return mv;

        }
    }
}