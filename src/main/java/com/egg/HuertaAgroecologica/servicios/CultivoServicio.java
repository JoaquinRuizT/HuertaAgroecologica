package com.egg.HuertaAgroecologica.servicios;

import com.egg.HuertaAgroecologica.entidades.Cultivo;
import com.egg.HuertaAgroecologica.entidades.Foto;
import com.egg.HuertaAgroecologica.excepciones.MiExcepcion;
import com.egg.HuertaAgroecologica.repositorios.CultivoRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author POSITIVO BGH
 */
@Service
public class CultivoServicio {

    @Autowired
    private CultivoRepositorio cultivoRepositorio;

    @Autowired
    private FotoServicio fotoServicio;

    @Transactional
    public void crearCultivo(String nombre, String tipoCultivo, boolean alta, String temperatura, String agua, String luz, String suelo, String estacion, String viento, String observaciones, MultipartFile archivo) throws MiExcepcion {
//vamos a hacer metodo validar?
        Cultivo cultivo = new Cultivo();

        cultivo.setNombre(nombre);
        cultivo.setTipoCultivo(tipoCultivo);
        cultivo.setAlta(true);
        cultivo.setTemperatura(temperatura);
        cultivo.setAgua(agua);
        cultivo.setLuz(luz);
        cultivo.setSuelo(suelo);
        cultivo.setEstacion(estacion);
        cultivo.setViento(viento);
        cultivo.setObservaciones(observaciones);

        Foto foto = fotoServicio.guardar(archivo);
        cultivo.setImagenCultivo(foto);

        cultivoRepositorio.save(cultivo);

    }

    @Transactional
    public void modificarCultivo(String id, String nombre, String tipoCultivo, String temperatura, String agua, String luz, String suelo, String estacion, String viento, String observaciones, MultipartFile archivo) throws MiExcepcion {
        //vamos a hacer metodo validar?
        Optional<Cultivo> respuesta = cultivoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Cultivo cultivo = respuesta.get();

            cultivo.setNombre(nombre);
            cultivo.setTipoCultivo(tipoCultivo);
            cultivo.setTemperatura(temperatura);
            cultivo.setAgua(agua);
            cultivo.setLuz(luz);
            cultivo.setSuelo(suelo);
            cultivo.setEstacion(estacion);
            cultivo.setViento(viento);
            cultivo.setObservaciones(observaciones);

            Foto foto = fotoServicio.guardar(archivo);
            cultivo.setImagenCultivo(foto);

            cultivoRepositorio.save(cultivo);
        }
    }

    @Transactional
    public void eliminarCultivo(String id) {

        Optional<Cultivo> respuesta = cultivoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Cultivo cultivo = respuesta.get();
            cultivoRepositorio.delete(cultivo);
        }
    }

    //en el video de spring agrega un metodo para listar todos los Cultivos (en este caso) que tiene la BD
    //lo agregué para cumplir el CRUD (Read)
    public List<Cultivo> listarCultivos() {
        List<Cultivo> cultivos = new ArrayList();
        cultivos = cultivoRepositorio.findAll();
        return cultivos;
    }

    public List<Cultivo> listarVegetales() {
        List<Cultivo> vegetales = new ArrayList();
        vegetales = cultivoRepositorio.buscarPorTipoCultivo("Vegetales");
        return vegetales;
    }

    public List<Cultivo> listarFrutas() {
        List<Cultivo> frutas = new ArrayList();
        frutas = cultivoRepositorio.buscarPorTipoCultivo("Frutas");
        return frutas;
    }

    /*Metodo para leer 1 solo cultivo*/
    @Transactional(readOnly = true)
    public Cultivo getOne(String id) {
        return cultivoRepositorio.getOne(id);
    }
    
    /*método para "eliminar" sigue en la base de datos pero esta en el estado de BAJA*/
    public void alta(String id) {

        Cultivo entidad = cultivoRepositorio.getOne(id);

        entidad.setAlta(true);
        cultivoRepositorio.save(entidad);
    }
    
    /*método para "eliminar" sigue en la base de datos pero esta en el estado de BAJA*/
    public void baja(String id) {

        Cultivo entidad = cultivoRepositorio.getOne(id);

        entidad.setAlta(false);
        cultivoRepositorio.save(entidad);
    }

    //ver si agregamos validarCultivo para crear/modificar (llamar al validar en esos métodos)
//    private void validarCultivo(String id,String nombre, String tipoCultivo, Date fecha, boolean alta, 
//            double temperatura, String agua, String luz, String suelo, String estacion, String viento, 
//            String observaciones, MultipartFile archivo) throws MiExcepcion {
//
//        if (nombre.isEmpty() || nombre == null) {
//            throw new MiExcepcion("El nombre no puede ser nulo o estar vacío");
//        }
//        if (tipoCultivo.isEmpty() || tipoCultivo == null) {
//            throw new MiExcepcion("El tipo de cultivo no puede ser nulo o estar vacío");
//        }
//        //si modificamos un cultivo, se toma la fecha de modificacion?
//         if (fecha == null) {//fecha.isEmpty() larga error,ver boolean y double tambien dan error
//            throw new MiExcepcion("La fecha del cultivo no puede ser nula");
//        }
//         
//        if (agua.isEmpty() || agua == null) {
//            throw new MiExcepcion("El agua del cultivo no puede ser nula o estar vacía");
//        }
//        
//        if (luz.isEmpty() || luz == null) {
//            throw new MiExcepcion("La luz del cultivo no puede ser nula o estar vacía");
//        }
//        
//        if (suelo.isEmpty() || suelo == null) {
//            throw new MiExcepcion("El suelo del cultivo no puede ser nulo o estar vacío");
//        }
//        
//        if (estacion.isEmpty() || estacion == null) {
//            throw new MiExcepcion("La estación del cultivo no puede ser nula o estar vacía");
//        }
//        
//        if (viento.isEmpty() || viento == null) {
//            throw new MiExcepcion("El viento del cultivo no puede ser nulo o estar vacío");
//        }
//        
//        //observaciones podrían ser nulas/vacías
//        
//    }
}