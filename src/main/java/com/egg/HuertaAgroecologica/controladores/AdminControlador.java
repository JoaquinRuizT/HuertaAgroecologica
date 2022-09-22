/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.HuertaAgroecologica.controladores;

import com.egg.HuertaAgroecologica.entidades.Cultivo;
import com.egg.HuertaAgroecologica.entidades.Usuario;
import com.egg.HuertaAgroecologica.servicios.CultivoServicio;
import com.egg.HuertaAgroecologica.servicios.UsuarioServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author julietagamez
 */
@Controller
@RequestMapping("/admin")
public class AdminControlador {
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Autowired
    private CultivoServicio cultivoServicio;

    @GetMapping("/dashboard")
    public String panelAdministrativo() {
        return "index.html";
    }
   
   @GetMapping("/lista")
   public String lista(ModelMap modelo, HttpSession session){
       Usuario logueado = (Usuario) session.getAttribute("usuariosession");
       List<Usuario> usuarios = usuarioServicio.listarUsuarios();
       modelo.addAttribute("adminActual", logueado);
       modelo.addAttribute("usuarios", usuarios);
       return "usuario-list";
   }
 
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/guest/{id}")
    public String guest(@PathVariable String id) {
        try {
            usuarioServicio.guest(id);
            return "redirect:/admin/lista";
        } catch (Exception e) {
            return "redirect:/admin/lista";
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/{id}")
    public String admin(@PathVariable String id) {
        try {
            usuarioServicio.admin(id);
            return "redirect:/admin/lista";
        } catch (Exception e) {
            return "redirect:/admin/lista";
        }
    }
    
    @GetMapping("/listacultivos")
    public String listar(ModelMap modelo) {       
       
        List<Cultivo> cultivos = cultivoServicio.listarCultivos();
        modelo.addAttribute("cultivos", cultivos);
        return "cultivo-list.html";
    }
   
}
