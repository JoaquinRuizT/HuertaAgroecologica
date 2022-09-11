package com.egg.HuertaAgroecologica.controladores;

import com.egg.HuertaAgroecologica.entidades.Cultivo;
import com.egg.HuertaAgroecologica.excepciones.MiExcepcion;
import com.egg.HuertaAgroecologica.servicios.CultivoServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
            @RequestParam String suelo, @RequestParam String estacion, String observaciones, @RequestParam MultipartFile archivo, ModelMap modelo) {
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

    @GetMapping("/modificar/{id}")
    public String modificarAp(@PathVariable String id, ModelMap modelo) {
        Cultivo cultivo = cultivoServicio.getOne(id);
        modelo.put("cultivo", cultivo);
        return "cultivo-modificar.html";

    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, @RequestParam String nombre, @RequestParam String tipoCultivo,
            @RequestParam String temperatura, @RequestParam String agua, @RequestParam String luz,
            @RequestParam String suelo, @RequestParam String estacion, String observaciones, @RequestParam MultipartFile archivo, ModelMap modelo) {
        try {
            cultivoServicio.modificarCultivo(id, nombre, tipoCultivo, temperatura, agua, luz, suelo, estacion, suelo, observaciones, archivo);
            return "redirect:../cultivo-list";
        } catch (MiExcepcion ex) {
            // TODO Auto-generated catch block
            modelo.put("error", "No se pudo editar");
            return "/";
        }

    }

    @GetMapping("/baja/{id}")
    public String baja(ModelMap modelo, @PathVariable String id) {

        try {
            cultivoServicio.baja(id);
            return "redirect:../cultivo-list";
        } catch (Exception e) {
            return "redirect:/";
        }

    }

    @GetMapping("/alta/{id}")
    public String alta(ModelMap modelo, @PathVariable String id) {

        try {
            cultivoServicio.alta(id);
            return "redirect:../cultivo-list";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id) {

        try {
            cultivoServicio.baja(id);
            return "redirect:../lista";
        } catch (Exception e) {
            return "redirect:..index";
        }

    }

}
