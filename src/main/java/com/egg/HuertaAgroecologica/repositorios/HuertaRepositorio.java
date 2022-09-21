/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.HuertaAgroecologica.repositorios;

import com.egg.HuertaAgroecologica.entidades.Huerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Usuario
 */
@Repository
public interface HuertaRepositorio extends JpaRepository<Huerta, String>{
    
    @Query("SELECT h from Huerta h WHERE h.nombre = :nombre")
    public Huerta buscarPorNombre(@Param("nombre") String nombre);
}
