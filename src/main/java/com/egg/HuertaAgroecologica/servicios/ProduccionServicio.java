/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.HuertaAgroecologica.servicios;

import com.egg.HuertaAgroecologica.entidades.Cultivo;
import com.egg.HuertaAgroecologica.entidades.Produccion;
import com.egg.HuertaAgroecologica.excepciones.MiExcepcion;
import com.egg.HuertaAgroecologica.repositorios.CultivoRepositorio;
import com.egg.HuertaAgroecologica.repositorios.ProduccionRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Usuario
 */
@Service
public class ProduccionServicio {
    
    @Autowired
    private ProduccionRepositorio produccionRepositorio;
    
    @Autowired
    private CultivoRepositorio cultivoRepositorio;
    
    @Transactional
    public void crearProduccion(String cantidad, String mes, String year, String idCultivo) throws MiExcepcion {
        /*Validar*/
        Double cantidadDecimal = Double.parseDouble(cantidad);
        Integer yearEntero = Integer.parseInt(year);
        Produccion produccion = new Produccion();
        validar(cantidadDecimal, yearEntero);
        Optional<String> respuesta = Optional.ofNullable(idCultivo);
        if(respuesta.isPresent()){
            Cultivo cultivo = cultivoRepositorio.getOne(idCultivo);
            produccion.setCultivo(cultivo);
            produccion.setCantidad(cantidadDecimal);
            produccion.setMes(mes);
            produccion.setAlta(true);
            produccion.setYear(yearEntero);
            produccionRepositorio.save(produccion);
        }
        
    }
    
    
    @Transactional(readOnly = true)
    public List<Produccion> listarTodos(){
        List<Produccion> listaDeProductos = new ArrayList();
        listaDeProductos = produccionRepositorio.findAll();
        return listaDeProductos;
    }
    
    @Transactional(readOnly = true)
    public List<Produccion> listarCantidadesPorMes(String mes){
        List<Produccion> listaProductos = produccionRepositorio.buscarPorMes(mes);
        return listaProductos;
    }
    
    @Transactional(readOnly = true)
    public List<String> listarProductosPorNombre(){
        List<Produccion> listaTotal = listarTodos();
        List<String> listaDeNombres = new ArrayList();
        for (Produccion produccion : listaTotal) {
            listaDeNombres.add(produccion.getCultivo().getNombre());
        }
        return listaDeNombres;
    }
    
     @Transactional(readOnly = true)
     public List<Produccion> listaBuscarPorNombre(String nombre){
         return produccionRepositorio.buscarPorNombre(nombre);
     }
    
    @Transactional(readOnly = true)
    public List<Double> listarProductosPorCantidad(){
        List<Produccion> listaTotal = listarTodos();
        List<Double> listaDeCantidades = new ArrayList();
        for (Produccion produccion : listaTotal) {
            listaDeCantidades.add(produccion.getCantidad());
        }
        return listaDeCantidades;
    }
    
    @Transactional(readOnly = true)
    public List<String> listarMesesDeProducto(){
        List<Produccion> listaTotal = listarTodos();
        List<String> listaDeMeses = new ArrayList();
        for (Produccion produccion : listaTotal) {
            listaDeMeses.add(produccion.getMes());
        }
        return listaDeMeses;
    }
    
    @Transactional(readOnly = true)
    public List<Integer> listarYearsDeProducto(){
        List<Produccion> listaTotal = listarTodos();
        List<Integer> listaDeYears = new ArrayList();
        for (Produccion produccion : listaTotal) {
            listaDeYears.add(produccion.getYear());
        }
        return listaDeYears;
    }
    
    
    @Transactional(readOnly = true)
    public double [] listarPorCantidadesDeCultivo(){
        List<Produccion> listaTotal = listarTodos();
        double[] vectorCantidad = new double[2];
        Double acumVegetales = 0d;
        Double acumFrutas = 0d;
        for (Produccion produccion : listaTotal) {
            if(produccion.getCultivo().getTipoCultivo().equalsIgnoreCase("Vegetales")){
                acumVegetales = acumVegetales + produccion.getCantidad();
            }else {
                acumFrutas = acumFrutas + produccion.getCantidad();
            }
        }
        vectorCantidad[0] = acumFrutas;
        vectorCantidad[1] = acumVegetales;
        return vectorCantidad;
    }
    
    /*método para "eliminar" sigue en la base de datos pero esta en el estado de BAJA*/
    public void alta(String id) {

        Produccion entidad = produccionRepositorio.getOne(id);

        entidad.setAlta(true);
        produccionRepositorio.save(entidad);
    }

    /*método para "eliminar" sigue en la base de datos pero esta en el estado de BAJA*/
    public void baja(String id) {

        Produccion entidad = produccionRepositorio.getOne(id);

        entidad.setAlta(false);
        produccionRepositorio.save(entidad);
    }
    
    public void validar(Double cantidad, Integer year) throws MiExcepcion{
        
        if(cantidad < 0){
            throw new MiExcepcion("Debes colocar una cantidad válida");
        }
        
        Date fechaActual = new Date();
        
        if(year < 1970 || year > fechaActual.getYear()){
            throw new MiExcepcion("Debes colocar un año válido");
        }
    }
}
