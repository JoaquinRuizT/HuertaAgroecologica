package com.egg.HuertaAgroecologica.repositorios;

import com.egg.HuertaAgroecologica.entidades.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoRepositorio extends JpaRepository<Foto, String>{

}