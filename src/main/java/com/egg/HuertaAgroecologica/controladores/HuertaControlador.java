/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.HuertaAgroecologica.controladores;

import com.egg.HuertaAgroecologica.entidades.Cultivo;
import com.egg.HuertaAgroecologica.entidades.Huerta;
import com.egg.HuertaAgroecologica.entidades.Usuario;
import com.egg.HuertaAgroecologica.excepciones.MiExcepcion;
import com.egg.HuertaAgroecologica.servicios.CultivoServicio;
import com.egg.HuertaAgroecologica.servicios.HuertaServicio;
import com.egg.HuertaAgroecologica.servicios.UsuarioServicio;
import java.util.List;

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
@RequestMapping("/huerta")
public class HuertaControlador {
    
    @Autowired
    private HuertaServicio huertaServicio;
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Autowired
    private CultivoServicio cultivoServicio;
    
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GUEST')")
    @GetMapping("/registrar")
    public String registrar(ModelMap modelo){
        List<Cultivo> cultivos = cultivoServicio.listarCultivos();
        modelo.addAttribute("cultivos", cultivos);
        return "form-huerta";
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GUEST')")
    @PostMapping("/registro")
    public String registro(ModelMap modelo, HttpSession session, @RequestParam String nombre, @RequestParam String idCultivo){
        
        try {
            
            Usuario logueado = (Usuario) session.getAttribute("usuariosession");
            Usuario usuario = usuarioServicio.buscarPorId(logueado.getId());
            huertaServicio.guardarHuerta(nombre, idCultivo, usuario);
            modelo.put("exito",  "¡Huerta registrada correctamente! Ahora quedará en revisión");
            return "form-huerta";
        } catch (MiExcepcion e) {
            modelo.put("error", e.getMessage());
            return "form-huerta";
        }
        
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GUEST')")
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        Huerta huerta = huertaServicio.buscarPorId(id);
        modelo.addAttribute("huerta", huerta);
        List<Cultivo> cultivos = cultivoServicio.listarCultivos();
        modelo.addAttribute("cultivos", cultivos);
        return "huerta-modificar.html";

    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GUEST')")
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id,@RequestParam  String nombre,@RequestParam  String idCultivo, HttpSession session, ModelMap modelo) {
       
        try {
            Usuario logueado = (Usuario) session.getAttribute("usuariosession");
            Usuario usuario = usuarioServicio.buscarPorId(logueado.getId());
            huertaServicio.modificarHuerta(id, nombre, idCultivo, usuario);
            modelo.put("exito",  "¡Huerta modificada correctamente! Ahora quedará en revisión");
            return "redirect:../lista";
        } catch (MiExcepcion e) {
            modelo.put("error", e.getMessage());
            return "huerta-modificar";
        }
    }
    
    
    
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GUEST')")
    @GetMapping("/lista")
    public String listar(ModelMap modelo, HttpSession session) {
        
       Usuario logueado = (Usuario) session.getAttribute("usuariosession");
       if(logueado == null){
            return "redirect:/login";
        }
        List<Huerta> huertas = huertaServicio.buscarHuertasPorUsuario(logueado.getId());
        modelo.addAttribute("huertas", huertas);
        return "huerta-list.html";
    }
    
}
