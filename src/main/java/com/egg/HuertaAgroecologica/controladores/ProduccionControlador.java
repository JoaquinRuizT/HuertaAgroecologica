/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.HuertaAgroecologica.controladores;

import com.egg.HuertaAgroecologica.entidades.Cultivo;
import com.egg.HuertaAgroecologica.entidades.Huerta;
import com.egg.HuertaAgroecologica.entidades.Produccion;
import com.egg.HuertaAgroecologica.entidades.Usuario;
import com.egg.HuertaAgroecologica.excepciones.MiExcepcion;
import com.egg.HuertaAgroecologica.servicios.CultivoServicio;
import com.egg.HuertaAgroecologica.servicios.ProduccionServicio;
import com.egg.HuertaAgroecologica.servicios.UsuarioServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @GetMapping("/registrar")
    public String registrar(ModelMap modelo, @RequestParam(required = false) String idCultivo) {
        List<Cultivo> cultivos = cultivoServicio.listarCultivos();
        modelo.addAttribute("cultivos", cultivos);
        return "form-produccion.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) String cantidad, @RequestParam(required = false) String mes,@RequestParam(required = false) String year, @RequestParam(required = false) String idCultivo, ModelMap modelo, HttpSession session) {
        try {
            Usuario logueado = (Usuario) session.getAttribute("usuariosession");
            Usuario usuario = usuarioServicio.buscarPorId(logueado.getId());
            produccionServicio.crearProduccion(cantidad, mes, year, idCultivo, usuario);
            modelo.put("exito", "Se ha registrado la producción correctamente");
            return "error-produccion.html";
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            return "error-produccion.html";
        }
    }
    
    
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GUEST')")
    @GetMapping("/lista")
    public String listar(ModelMap modelo, HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        if (logueado == null) {
            return "redirect:/login";
        }
        List<Produccion> producciones = produccionServicio.listarTodos();
        modelo.addAttribute("producciones", producciones);
        return "produccion-list.html";
    }
    
    
    @PreAuthorize("hasRole('ROLE_GUEST')")
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo, HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        if (logueado == null) {
            return "redirect:/login";
        }
        Produccion produccion = produccionServicio.buscarPorId(id);
        modelo.put("produccion", produccion);
        List<Cultivo> cultivos = cultivoServicio.listarCultivos();
        modelo.addAttribute("cultivos", cultivos);
        return "produccion-modificar.html";

    }
    
    
    @PreAuthorize("hasRole('ROLE_GUEST')")
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo, HttpSession session, @RequestParam(required = false) String cantidad, @RequestParam(required = false) String mes,@RequestParam(required = false) String year, @RequestParam(required = false) String idCultivo) {
        try {
            Usuario logueado = (Usuario) session.getAttribute("usuariosession");
            Usuario usuario = usuarioServicio.buscarPorId(logueado.getId());
            produccionServicio.modificarProduccion(idCultivo, cantidad, mes, year, idCultivo, usuario);
            modelo.put("exito", "Se ha modificado la producción correctamente");
            return "produccion-modificar.html";
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            return "produccion-modificar.html";
        }
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
