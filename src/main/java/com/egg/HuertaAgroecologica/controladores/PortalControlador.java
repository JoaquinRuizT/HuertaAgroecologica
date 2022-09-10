/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.HuertaAgroecologica.controladores;

import com.egg.HuertaAgroecologica.entidades.Usuario;
import com.egg.HuertaAgroecologica.excepciones.MiExcepcion;
import com.egg.HuertaAgroecologica.servicios.UsuarioServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author POSITIVO BGH
 */

@Controller
@RequestMapping("/")
public class PortalControlador { //localhost:8080/??
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @GetMapping("/")
    public String login() {
        return "login.html";
    }
    
    @PostMapping("/logincheck")
    public String loginCheck(@RequestParam String email, @RequestParam String password, ModelMap modelo){
        if(usuarioServicio.loginCheck(email, password)){
             return "index.html";
        }
        modelo.put("error", "Email o contraseña incorrecta");
        return "login.html";
    }
    
    @GetMapping("/registrar")
    public String registrar() {
        return "registro.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String password, 
            String password2, ModelMap modelo, MultipartFile archivo){
        try {
            
            usuarioServicio.registrar(archivo, nombre, email, password, password2);

            modelo.put("exito", "Usuario registrado correctamente!");
            
            return "index.html";
        } catch (MiExcepcion e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            
            return "registro.html";
        }
    }
    
    @GetMapping("/inicio")
    public String index() {
        return "index.html";
    }    
    
//    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
//    @GetMapping("/inicio")
//    public String inicio(HttpSession session) {
//        
//        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
//        
//        if (logueado.getRol().toString().equals("ADMIN")) {
//            return "redirect:/admin/dashboard";
//        }
//        
//           return "index.html";
//    }
//    
}
