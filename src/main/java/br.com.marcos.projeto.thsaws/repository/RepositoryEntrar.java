package br.com.marcos.projeto.thsaws.repository;

import br.com.marcos.projeto.thsaws.model.ThsCadastro;
import br.com.marcos.projeto.thsaws.model.ThsEntrar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositoryEntrar extends JpaRepository<ThsEntrar, Long> {


    @Query("SELECT u FROM ThsEntrar u WHERE u.usuario = :usuario AND u.senha = :senha ")
    Optional<ThsEntrar> findByUsuarioAndSenha(@Param("usuario")String usuario,@Param("senha") String senha);

    @Query("SELECT u FROM ThsEntrar u WHERE u.usuario = :usuario")
    Optional<ThsEntrar> findByUsuario(@Param("usuario") String usuario);

    @Query("SELECT u FROM ThsEntrar u WHERE u.email = :email")
    Optional<ThsEntrar> findByEmail(@Param("email") String email);


}