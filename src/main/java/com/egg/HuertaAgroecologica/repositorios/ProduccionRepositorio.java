/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.HuertaAgroecologica.repositorios;

import com.egg.HuertaAgroecologica.entidades.Produccion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Usuario
 */
@Repository
public interface ProduccionRepositorio extends JpaRepository<Produccion, String>{
    
    @Query("SELECT p FROM Produccion p WHERE p.mes = :mes")
    public List<Produccion> buscarPorMes(@Param("mes") String mes);
    
    @Query("SELECT p FROM Produccion p, IN(p.cultivo) m WHERE m.nombre LIKE :nombre")
    public List<Produccion> buscarPorNombre(@Param("nombre") String nombre);
}
