package br.com.marcos.projeto.thsaws.service;
import br.com.marcos.projeto.thsaws.dto.DtoRecSenha;
import br.com.marcos.projeto.thsaws.model.ThsCadastro;
import br.com.marcos.projeto.thsaws.repository.RepositoryCadastro;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Service
public class ServiceRecSenha {

    @Autowired
    private RepositoryCadastro repositoryCadastro;

    public ModelAndView pagina() {
        ModelAndView mv = new ModelAndView("recuperamentoDeSenha");
        DtoRecSenha dtoRecSenha = new DtoRecSenha();
        mv.addObject("dtoRecSenha", dtoRecSenha);
        return mv;
    }

    public ModelAndView recuperarSenha(@Valid @ModelAttribute("dtoRecSenha") DtoRecSenha dtoRecSenha, BindingResult bindingResult) {
        ModelAndView mv = new ModelAndView("recuperamentoDeSenha");

        if (bindingResult.hasErrors()) {
            System.out.println(dtoRecSenha.toString());
            return mv;
        }

        ThsCadastro thsCadastro = dtoRecSenha.requisicao();
        var verificaoDeEmail = repositoryCadastro.findByEmail(thsCadastro.getEmail());

        if (verificaoDeEmail.isPresent()) {
            System.out.println("Aqui vai ser enviado o e-mail passado, porque o e-mail existe no banco de dados");
        } else {
            System.out.println("O e-mail não existe no banco de dados!");
            bindingResult.rejectValue("email", "error.dtoRecSenha", "O e-mail não existe!");
        }

        return mv;
    }
}