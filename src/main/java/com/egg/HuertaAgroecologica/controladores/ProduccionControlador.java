/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.HuertaAgroecologica.controladores;

import com.egg.HuertaAgroecologica.entidades.Cultivo;
import com.egg.HuertaAgroecologica.entidades.Produccion;
import com.egg.HuertaAgroecologica.excepciones.MiExcepcion;
import com.egg.HuertaAgroecologica.servicios.CultivoServicio;
import com.egg.HuertaAgroecologica.servicios.ProduccionServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Usuario
 */
@Controller
@RequestMapping("/produccion")
public class ProduccionControlador {
    
    @Autowired
    private ProduccionServicio produccionServicio;
    
    @Autowired
    private CultivoServicio cultivoServicio;
    
    @GetMapping("/registrar")
    public String registrar(ModelMap modelo, @RequestParam(required = false) String idCultivo) {
        List<Cultivo> cultivos = cultivoServicio.listarCultivos();
        modelo.addAttribute("cultivos", cultivos);
        return "form-produccion.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) String cantidad, @RequestParam(required = false) String mes,@RequestParam(required = false) String year, @RequestParam(required = false) String idCultivo, ModelMap modelo) {
        try {
            produccionServicio.crearProduccion(cantidad, mes, year, idCultivo);
            modelo.put("exito", "Se ha registrado la producción correctamente");
            return "error-produccion.html";
        } catch (MiExcepcion ex) {
            modelo.put("error", "No se ha podido registrar correctamente");
            return "error-produccion.html";
        }
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Produccion> producciones = produccionServicio.listarTodos();
        modelo.addAttribute("producciones", producciones);
        return "produccion-list.html";
    }
    
    @GetMapping("/baja/{id}")
    public String baja(ModelMap modelo, @PathVariable String id) {

        try {
            produccionServicio.baja(id);
            return "redirect:../lista";
        } catch (Exception e) {
            return "redirect:/";
        }

    }

    @GetMapping("/alta/{id}")
    public String alta(ModelMap modelo, @PathVariable String id) {

        try {
            produccionServicio.alta(id);
            return "redirect:../lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
}
