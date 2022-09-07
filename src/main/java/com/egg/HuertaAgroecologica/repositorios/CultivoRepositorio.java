package com.egg.HuertaAgroecologica.repositorios;

import com.egg.HuertaAgroecologica.entidades.Cultivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CultivoRepositorio extends JpaRepository<Cultivo, Integer>{
    
    @Query("SELECT c FROM Cultivo c WHERE c.nombre = :nombre")
    public Cultivo buscarPorNombre(@Param("nombre") String nombre);
    
}