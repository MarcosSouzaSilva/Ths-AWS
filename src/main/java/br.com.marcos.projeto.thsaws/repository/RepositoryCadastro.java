package br.com.marcos.projeto.thsaws.repository;
import br.com.marcos.projeto.thsaws.model.ThsUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositoryCadastro extends JpaRepository<ThsUsuario, Long> {

    @Query("SELECT u FROM ThsUsuario u WHERE u.usuario = :usuario AND u.senha = :senha ")
    Optional<ThsUsuario> findByUsuarioAndSenha(@Param("usuario")String usuario, @Param("senha") String senha);

    @Query("SELECT u FROM ThsUsuario u WHERE u.usuario = :usuario")
    Optional<ThsUsuario> findByUsuario(@Param("usuario") String usuario);

    @Query("SELECT u FROM ThsUsuario u WHERE u.senha = :senha")
    Optional<ThsUsuario> findBySenha(@Param("senha") String senha);

    @Query("SELECT u FROM ThsUsuario u WHERE u.email = :email")
    Optional<ThsUsuario> findByEmail(@Param("email") String email);


}