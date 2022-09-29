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
import com.egg.HuertaAgroecologica.servicios.CultivoServicio;
import com.egg.HuertaAgroecologica.servicios.HuertaServicio;
import com.egg.HuertaAgroecologica.servicios.ProduccionServicio;
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
    
    @Autowired
    private HuertaServicio huertaServicio;
    
    @Autowired
    private ProduccionServicio produccionServicio;

    @GetMapping("/dashboard")
    public String panelAdministrativo() {
        return "index.html";
    }
   
   @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/listacultivos")
    public String listar(ModelMap modelo) {       
       
        List<Cultivo> cultivos = cultivoServicio.listarCultivos();
        modelo.addAttribute("cultivos", cultivos);
        return "cultivo-list.html";
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/listahuertas")
    public String listarhuertas(ModelMap modelo) {       
       
        List<Huerta> huertas = huertaServicio.listarTodasLasHuertas();
        modelo.addAttribute("huertas", huertas);
        return "huerta-list.html";
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/frutas")
    public String listarFrutas(ModelMap modelo) {
        List<Cultivo> cultivos = cultivoServicio.listarFrutas();
        modelo.addAttribute("cultivos", cultivos);
        return "cultivo-list.html";
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/verduras")
    public String listarVerduras(ModelMap modelo) {
        List<Cultivo> cultivos = cultivoServicio.listarVegetales();
        modelo.addAttribute("cultivos", cultivos);
        return "cultivo-list.html";
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GUEST')")
    @GetMapping("/listaproduccion")
    public String listarproduccion(ModelMap modelo, HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        if (logueado == null) {
            return "redirect:/login";
        }
        List<Produccion> producciones = produccionServicio.listarTodos();
        modelo.addAttribute("producciones", producciones);
        return "produccion-list.html";
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GUEST')")
    @GetMapping("display")
    public String barGraph(ModelMap modelo) {
        List<String> listaNombres = produccionServicio.listarProductosPorNombre();
        List<Double> listaCantidad = produccionServicio.listarProductosPorCantidad();
        List<String> listaMeses = produccionServicio.listarMesesDeProducto();
        double[] vectorPorcentajes = produccionServicio.listarPorCantidadesDeCultivo();
        modelo.addAttribute("listaNombres", listaNombres);
        modelo.addAttribute("listaMeses", listaMeses);
        modelo.addAttribute("listaCantidad", listaCantidad);
        modelo.addAttribute("vectorPorcentajes", vectorPorcentajes);
        return "dashboard";
    }
   
}
