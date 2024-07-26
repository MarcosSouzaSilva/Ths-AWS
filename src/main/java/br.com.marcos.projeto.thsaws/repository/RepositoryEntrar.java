package br.com.marcos.projeto.thsaws.repository;


import br.com.marcos.projeto.thsaws.model.ThsEntrar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RepositoryEntrar extends JpaRepository<ThsEntrar, Long> {



}