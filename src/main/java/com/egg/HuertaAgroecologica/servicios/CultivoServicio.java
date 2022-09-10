
package com.egg.HuertaAgroecologica.servicios;

import com.egg.HuertaAgroecologica.entidades.Cultivo;
import com.egg.HuertaAgroecologica.entidades.Foto;
import com.egg.HuertaAgroecologica.excepciones.MiExcepcion;
import com.egg.HuertaAgroecologica.repositorios.CultivoRepositorio;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public void crearCultivo(String nombre, String tipoCultivo, Date fecha, boolean alta, double temperatura, String agua, String luz, String suelo, String estacion, String viento, String observaciones, MultipartFile archivo) throws MiExcepcion{
        Cultivo cultivo= new Cultivo();
        
        cultivo.setNombre(nombre);
        cultivo.setTipoCultivo(tipoCultivo);
        cultivo.setFecha(fecha);
        cultivo.setAlta(alta);
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
    public void modificarCultivo (String id,String nombre, String tipoCultivo, Date fecha, boolean alta, double temperatura, String agua, String luz, String suelo, String estacion, String viento, String observaciones, MultipartFile archivo) throws MiExcepcion{
        //vamos a hacer metodo validar?
        Optional<Cultivo> respuesta = cultivoRepositorio.findById(id);
        if (respuesta.isPresent()){
            Cultivo cultivo = respuesta.get();
            
        cultivo.setNombre(nombre);
        cultivo.setTipoCultivo(tipoCultivo);
        cultivo.setFecha(fecha);
        cultivo.setAlta(alta);
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
    public void eliminarCultivo(String id){
       
         Optional<Cultivo> respuesta = cultivoRepositorio.findById(id);
        if (respuesta.isPresent()){
            Cultivo cultivo = respuesta.get();
            cultivoRepositorio.delete(cultivo);
    }
    }
}
