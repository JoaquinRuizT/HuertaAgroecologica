package com.egg.HuertaAgroecologica.controladores;

import com.egg.HuertaAgroecologica.entidades.Cultivo;
import com.egg.HuertaAgroecologica.excepciones.MiExcepcion;
import com.egg.HuertaAgroecologica.servicios.CultivoServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/cultivo")
public class CultivoControlador {

    @Autowired
    private CultivoServicio cultivoServicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "form-cultivo.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String tipoCultivo, 
            @RequestParam String temperatura, @RequestParam String agua, @RequestParam String luz,
            @RequestParam String suelo, @RequestParam String estacion, String observaciones, MultipartFile archivo, ModelMap modelo){
        try {
            cultivoServicio.crearCultivo(nombre, tipoCultivo, true, temperatura, agua, luz, suelo, estacion, suelo, observaciones, archivo);
            
            modelo.put("exito", "Cultivo registrado correctamente!");
            return "index.html";
        } catch (MiExcepcion e) {
            modelo.put("error", e.getMessage());
            return "form-cultivo.html";
        }
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Cultivo> cultivos = cultivoServicio.listarCultivos();
        modelo.addAttribute("cultivos", cultivos);
        return "cultivo-list.html";
    }

}
