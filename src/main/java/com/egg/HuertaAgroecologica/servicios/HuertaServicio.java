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
    
   
    
    @Transactional
    public void guardarHuerta(String nombre, String idCultivo, Usuario usuario) throws MiExcepcion {
        validar(nombre);
        Optional<String> respuesta = Optional.ofNullable(idCultivo);
        if (respuesta.isPresent()) {
            Cultivo cultivo = cultivoRepositorio.getOne(idCultivo);
            Huerta huerta = new Huerta();
            huerta.setCreado(new Date());
            huerta.setNombre(nombre);
            huerta.setUsuario(usuario);
            huerta.setCultivos(cultivo);
            if (cultivo.getEstado() == 1) {
                huerta.setPorcentajeDeConfiabilidad(0.0d);
            }else{
                huerta.setPorcentajeDeConfiabilidad(0.0d);
            }
            
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
                huerta.setPorcentajeDeConfiabilidad(0.0d);
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
    
   
    
    public List<Huerta> buscarHuertasPorUsuario(String idUsuario) {
        List<Huerta> huertas = new ArrayList();
        huertas = huertaRepositorio.buscarHuertaPorUsuario(idUsuario);
        return huertas;
    }
    
    public List<Huerta> buscarHuertasPorNombre(String nombre) {
        List<Huerta> huertas = new ArrayList();
        huertas = huertaRepositorio.buscarPorNombre(nombre);
        return huertas;
    }
    
    
    public void entregaDeHuertas(List<Huerta> listica){
        for (Huerta huerta : listica) {
            huerta.setPorcentajeDeConfiabilidad(calcularConfiabilidad(huerta.getNombre()));
            System.out.println(calcularConfiabilidad(huerta.getNombre()));
            huertaRepositorio.save(huerta);
        }
        
    }
    
    public Double calcularConfiabilidad(String nombre) {
        List<Huerta> huertas = huertaRepositorio.buscarPorNombre(nombre);
        System.out.println(huertas);
        Double acum = 0.0d;
        for (Huerta huerta : huertas) {
            if (huerta.getCultivos().getEstado() == 1) {
                
                acum = acum + 1.0d;
                System.out.println("acum" + acum);
            }
        }
        System.out.println(100/huertas.size()*acum);
        return (100/huertas.size()*acum);
    }
    
    
    public void validar(String nombre) throws MiExcepcion {
        if (nombre.isEmpty() || nombre == null) {
            throw new MiExcepcion("El nombre no puede ser nulo o vacio");
        }
        
    }
}
