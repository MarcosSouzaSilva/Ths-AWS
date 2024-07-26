package br.com.marcos.projeto.thsaws.service;
import br.com.marcos.projeto.thsaws.dto.DtoRecSenha;
import br.com.marcos.projeto.thsaws.model.ThsUsuario;
import br.com.marcos.projeto.thsaws.repository.RepositoryCadastro;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

@Service
public class ServiceRecSenha {

    @Autowired
    private RepositoryCadastro repositoryCadastro;

    @Autowired
    private ServiceEmail serviceEmail;

    public ModelAndView pagina() {
        ModelAndView mv = new ModelAndView("reset-password");
        DtoRecSenha dtoRecSenha = new DtoRecSenha();
        mv.addObject("dtoRecSenha", dtoRecSenha);
        return mv;
    }

    public ModelAndView recuperarSenha(@Valid DtoRecSenha dtoRecSenha, BindingResult bindingResult) {
        ModelAndView mv = new ModelAndView("reset-password");

        if (bindingResult.hasErrors()) {
            System.out.println(dtoRecSenha.toString());
            return mv;
        }

        ThsUsuario thsCadastro = dtoRecSenha.requisicao();
        var verificaoDeEmail = repositoryCadastro.findByEmail(thsCadastro.getEmail());

        var emailInvalido = thsCadastro.getEmail().contains("@gmail.com") || thsCadastro.getEmail().contains("@outlook.com") || thsCadastro.getEmail().contains("@hotmail.com");

        if (!emailInvalido){
            bindingResult.rejectValue("email", "error.dtoCadastro", "Email inválido !");
            return mv;
        }

        if (verificaoDeEmail.isPresent()) {
            System.out.println("Aqui vai ser enviado o e-mail passado, porque o e-mail existe no banco de dados");
            serviceEmail.enviarEmailDeTexto(thsCadastro.getEmail(), "Recuperação de email", "Olá, para recuperar sua senha clique no link abaixo localhost:8080/");
        } else {
            System.out.println("O e-mail não existe no banco de dados!");
            bindingResult.rejectValue("email", "error.dtoRecSenha", "Não existe uma conta com este email !");
        }

        return mv;
    }
}