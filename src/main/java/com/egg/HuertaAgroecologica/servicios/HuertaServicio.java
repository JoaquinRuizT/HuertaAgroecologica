/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.HuertaAgroecologica.servicios;

import com.egg.HuertaAgroecologica.entidades.Cultivo;
import com.egg.HuertaAgroecologica.entidades.Huerta;
import com.egg.HuertaAgroecologica.entidades.Usuario;
import com.egg.HuertaAgroecologica.excepciones.MiExcepcion;
import com.egg.HuertaAgroecologica.repositorios.CultivoRepositorio;
import com.egg.HuertaAgroecologica.repositorios.HuertaRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Usuario
 */
@Service
public class HuertaServicio {
    
    @Autowired
    private HuertaRepositorio huertaRepositorio;
    
    @Autowired
    private CultivoRepositorio cultivoRepositorio;
    
    /*private String id;
    private String nombre;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;

    @Temporal(TemporalType.TIMESTAMP)
    private Date editado;
    
    @OneToOne
    private List<Cultivo> cultivos;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Usuario usuario;*/
    
    @Transactional
    public void guardarHuerta(String nombre, String idCultivo, Usuario usuario) throws MiExcepcion {
        Optional<String> respuesta = Optional.ofNullable(idCultivo);
        if (respuesta.isPresent()) {
            Cultivo cultivo = cultivoRepositorio.getOne(idCultivo);
            Huerta huerta = new Huerta();
            huerta.setCreado(new Date());
            huerta.setNombre(nombre);
            huerta.setUsuario(usuario);
            huerta.setCultivos(cultivo);
            huerta.setActivo(2);
            huertaRepositorio.save(huerta);
        }
    }
    
    @Transactional
    public void modificarHuerta(String nombre, String idCultivo, Usuario usuario) throws MiExcepcion {
        Optional<String> respuesta = Optional.ofNullable(idCultivo);
        if (respuesta.isPresent()) {
            Cultivo cultivo = cultivoRepositorio.getOne(idCultivo);
            Huerta huerta = new Huerta();
            huerta.setEditado(new Date());
            huerta.setNombre(nombre);
            huerta.setUsuario(usuario);
            huerta.setCultivos(cultivo);
            huerta.setActivo(2);
            huertaRepositorio.save(huerta);
        }
    }
    
    
    @Transactional(readOnly = true)
    public List<Huerta> listarTodasLasHuertas(){
        return huertaRepositorio.findAll();
    }
    
    @Transactional(readOnly = true)
    public Huerta buscarPorId(String id){
        return huertaRepositorio.getOne(id);
    }
    
    public void alta(String id){
        Huerta entidad = buscarPorId(id);
        entidad.setActivo(3);
    }
    
    public void baja(String id){
        Huerta entidad = buscarPorId(id);
        entidad.setActivo(1);
    }
    
    public void revision(String id){
        Huerta entidad = buscarPorId(id);
        entidad.setActivo(2);
    }
    
}
