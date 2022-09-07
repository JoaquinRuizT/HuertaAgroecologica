package com.egg.HuertaAgroecologica.repositorios;

import com.egg.HuertaAgroecologica.entidades.Cultivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CultivoRepositorio extends JpaRepository<Cultivo, Long>{

    
}