package br.com.marcos.projeto.thsaws.repository;
import br.com.marcos.projeto.thsaws.model.ThsCadastro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@Repository
public interface RepositoryCadastro extends JpaRepository<ThsCadastro, Long> {

    @Query("SELECT u FROM ThsCadastro u WHERE u.usuario = :usuario AND u.senha = :senha ")
    Optional<ThsCadastro> findByUsuarioAndSenha(@Param("usuario")String usuario,@Param("senha") String senha);

    @Query("SELECT u FROM ThsCadastro u WHERE u.usuario = :usuario")
    Optional<ThsCadastro> findByUsuario(@Param("usuario") String usuario);

    @Query("SELECT u FROM ThsCadastro u WHERE u.email = :email")
    Optional<ThsCadastro> findByEmail(@Param("email") String email);


}