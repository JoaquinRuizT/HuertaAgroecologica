package com.egg.HuertaAgroecologica.controladores;

import com.egg.HuertaAgroecologica.entidades.Foto;
import com.egg.HuertaAgroecologica.excepciones.MiExcepcion;
import com.egg.HuertaAgroecologica.servicios.FotoServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/foto")
public class FotoControlador {

    @Autowired
    private FotoServicio fotoServicio;
    
    @GetMapping("/fotografias/{id}")
    public ResponseEntity<byte[]> foto(@PathVariable String id){
        Foto foto = fotoServicio.buscarPorId(id);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(foto.getContenido(),headers, HttpStatus.OK);
    }
    
//    @GetMapping("/lista")
//    public String lista(ModelMap modelo){
//        List<Foto> fotos = fotoServicio.listarFotos();
//        modelo.addAttribute("fotos", fotos);
//        return
//    }
}