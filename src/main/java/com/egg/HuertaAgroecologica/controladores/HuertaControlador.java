/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.HuertaAgroecologica.controladores;

import com.egg.HuertaAgroecologica.entidades.Usuario;
import com.egg.HuertaAgroecologica.excepciones.MiExcepcion;
import com.egg.HuertaAgroecologica.servicios.HuertaServicio;
import com.egg.HuertaAgroecologica.servicios.UsuarioServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
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
    
    @GetMapping("/registrar")
    public String registrar(){
        return "form-huerta";
    }
    
    @PostMapping("/registro")
    public String registro(ModelMap modelo, HttpSession session, @RequestParam String nombre, String idCultivo){
        
        try {
            
            Usuario logueado = (Usuario) session.getAttribute("usuariosession");
            Usuario usuario = usuarioServicio.buscarPorId(logueado.getId());
            huertaServicio.guardarHuerta(nombre, idCultivo, usuario);
        } catch (MiExcepcion ex) {
            Logger.getLogger(HuertaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "form-huerta";
    }
    
}
