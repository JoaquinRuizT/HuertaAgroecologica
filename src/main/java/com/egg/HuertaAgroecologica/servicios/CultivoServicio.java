package com.egg.HuertaAgroecologica.servicios;

import com.egg.HuertaAgroecologica.entidades.Cultivo;
import com.egg.HuertaAgroecologica.entidades.Foto;
import com.egg.HuertaAgroecologica.entidades.Usuario;
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
    public void crearCultivo(String nombre, String tipoCultivo, double temperatura, String agua, String luz, String suelo, String estacion, String viento, String observaciones, MultipartFile archivo, Usuario usuario) throws MiExcepcion {

        validarCultivo(temperatura);
        Cultivo cultivo = new Cultivo();

        cultivo.setNombre(nombre);
        cultivo.setTipoCultivo(tipoCultivo);
        cultivo.setEstado(2);
        cultivo.setTemperatura(temperatura);
        cultivo.setAgua(agua);
        cultivo.setLuz(luz);
        cultivo.setSuelo(suelo);
        cultivo.setEstacion(estacion);
        cultivo.setViento(viento);
        cultivo.setObservaciones(observaciones);
        cultivo.setFecha(new Date());
        cultivo.setUsuario(usuario);

        Foto foto = fotoServicio.guardar(archivo);
        cultivo.setImagenCultivo(foto);

        cultivoRepositorio.save(cultivo);

    }

    @Transactional
    public void modificarCultivo(String id, String nombre, String tipoCultivo, double temperatura, String agua, String luz, String suelo, String estacion, String viento, String observaciones, MultipartFile archivo) throws MiExcepcion, Exception {
        //vamos a hacer metodo validar?
        Optional<Cultivo> respuesta = cultivoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Cultivo cultivo = respuesta.get();

            cultivo.setNombre(nombre);
            cultivo.setTipoCultivo(tipoCultivo);
            cultivo.setTemperatura(temperatura);
            cultivo.setAgua(agua);
            cultivo.setEstado(2);
            cultivo.setLuz(luz);
            cultivo.setSuelo(suelo);
            cultivo.setEstacion(estacion);
            cultivo.setViento(viento);
            cultivo.setObservaciones(observaciones);
            cultivo.setFecha(new Date());

            if (!archivo.isEmpty()) {
                String idFoto = null;

                if (cultivo.getImagenCultivo() != null) {
                    idFoto = cultivo.getImagenCultivo().getId();
                }

                Foto foto = fotoServicio.actualizar(idFoto, archivo);
                cultivo.setImagenCultivo(foto);
            }

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

        entidad.setEstado(1);
        cultivoRepositorio.save(entidad);
    }

    public List<Cultivo> buscarCultivosPorUsuario(String idUsuario) {
        List<Cultivo> cultivos = new ArrayList();
        cultivos = cultivoRepositorio.buscarCultivoPorUsuario(idUsuario);
        return cultivos;
    }

    /*método para "eliminar" sigue en la base de datos pero esta en el estado de BAJA*/
    public void baja(String id) {

        Cultivo entidad = cultivoRepositorio.getOne(id);

        entidad.setEstado(0);
        cultivoRepositorio.save(entidad);
    }

    public void validarCultivo(double temperatura) throws MiExcepcion {

        if (temperatura < 0.0d || temperatura > 40.0d) {
            throw new MiExcepcion("No es una temperatura válida");
        }
    }
}
