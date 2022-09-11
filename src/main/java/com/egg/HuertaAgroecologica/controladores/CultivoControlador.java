package com.egg.HuertaAgroecologica.controladores;

import com.egg.HuertaAgroecologica.entidades.Cultivo;
import com.egg.HuertaAgroecologica.servicios.CultivoServicio;
import java.util.List;
import com.egg.HuertaAgroecologica.excepciones.MiExcepcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String registro() {
        return null;
    }

    @GetMapping("/frutas")
        public String frutas (ModelMap modelo){
        List<Cultivo> listaFrutas = cultivoServicio.listarFrutas();
            modelo.addAttribute("listaFrutas", listaFrutas);
            return "frutas.html";
        }
        
     @GetMapping("/verduras")
        public String verduras (ModelMap modelo){
        List<Cultivo> listaVerduras = cultivoServicio.listarVegetales();
            modelo.addAttribute("listaVerduras", listaVerduras);
            return "verduras.html";
        }
}
