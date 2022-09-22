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
import java.util.ArrayList;
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
    public void modificarHuerta(String idHuerta, String nombre, String idCultivo, Usuario usuario) throws MiExcepcion {
        Optional<String> respuesta = Optional.ofNullable(idCultivo);
        if (respuesta.isPresent()) {
            Cultivo cultivo = cultivoRepositorio.getOne(idCultivo);
            Optional<Huerta> respuestaHuerta = huertaRepositorio.findById(idHuerta);
            if (respuestaHuerta.isPresent()) {
                Huerta huerta = respuestaHuerta.get();
                huerta.setEditado(new Date());
                huerta.setNombre(nombre);
                huerta.setUsuario(usuario);
                huerta.setCultivos(cultivo);
                huerta.setActivo(2);
                huertaRepositorio.save(huerta);
            }
            
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
    
    public List<Huerta> buscarHuertasPorUsuario(String idUsuario) {
        List<Huerta> huertas = new ArrayList();
        huertas = huertaRepositorio.buscarHuertaPorUsuario(idUsuario);
        return huertas;
    }
}
