package com.egg.HuertaAgroecologica.servicios;

import com.egg.HuertaAgroecologica.entidades.Foto;
import com.egg.HuertaAgroecologica.excepciones.MiExcepcion;
import com.egg.HuertaAgroecologica.repositorios.FotoRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoServicio {

    @Autowired
    private FotoRepositorio fotoRepositorio;

    public Foto guardar(MultipartFile archivo) throws MiExcepcion {
        if (archivo != null) {

            try {
                Foto foto = new Foto();

                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());

                return fotoRepositorio.save(foto);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    public Foto actualizar(MultipartFile archivo, String idFoto) throws MiExcepcion {
        if (archivo != null) {

            try {
                Foto foto = new Foto();

                if (idFoto != null) {
                    Optional<Foto> respuesta = fotoRepositorio.findById(idFoto);

                    if (respuesta.isPresent()) {
                        foto = respuesta.get();
                    }
                }

                guardar(archivo);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    //@Transactional
    public void eliminarFoto(String id) {

        Optional<Foto> respuesta = fotoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Foto foto = respuesta.get();
            fotoRepositorio.delete(foto);
            //dejamos delete? o le damos de baja con un atributo de tipo boleano?
        }
    }

    //agregar este metodo en Foto?
    public List<Foto> listarFotos() {
        List<Foto> fotos = new ArrayList();
        fotos = fotoRepositorio.findAll();
        return fotos;
    }

    public Foto buscarPorId(String id) {
        Optional<Foto> respuesta = fotoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Foto foto = respuesta.get();
            return foto;

        }
        return new Foto();

    }
}
