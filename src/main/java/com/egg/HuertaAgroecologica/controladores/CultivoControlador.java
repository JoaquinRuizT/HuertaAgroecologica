package com.egg.HuertaAgroecologica.controladores;

import com.egg.HuertaAgroecologica.entidades.Cultivo;
import com.egg.HuertaAgroecologica.entidades.Usuario;
import com.egg.HuertaAgroecologica.excepciones.MiExcepcion;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/cultivo")
public class CultivoControlador{

    @Autowired
    private CultivoServicio cultivoServicio;
    
    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "form-cultivo.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String tipoCultivo,
            @RequestParam String temperatura, @RequestParam String agua, @RequestParam String luz,
            @RequestParam String suelo, @RequestParam String estacion, String observaciones, @RequestParam MultipartFile archivo, ModelMap modelo, HttpSession session) {
        try {
            Usuario logueado = (Usuario) session.getAttribute("usuariosession");
            Usuario usuario = usuarioServicio.buscarPorId(logueado.getId());
            cultivoServicio.crearCultivo(nombre, tipoCultivo, true, temperatura, agua, luz, suelo, estacion, suelo, observaciones, archivo, usuario);

            modelo.put("exito", "Cultivo registrado correctamente!");
            return "form-cultivo.html";
        } catch (MiExcepcion e) {
            modelo.put("error", e.getMessage());
            return "form-cultivo.html";
        }
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GUEST')")
    @GetMapping("/lista")
    public String listar(ModelMap modelo, HttpSession session) {
        
       Usuario logueado = (Usuario) session.getAttribute("usuariosession");
       if(logueado == null){
            return "redirect:/login";
        }
        List<Cultivo> cultivos = cultivoServicio.buscarCultivosPorUsuario(logueado.getId());
        modelo.addAttribute("cultivos", cultivos);
        return "cultivo-list.html";
    }
    
    @GetMapping("/frutas")
    public String listarFrutas(ModelMap modelo) {
        List<Cultivo> cultivos = cultivoServicio.listarFrutas();
        modelo.addAttribute("cultivos", cultivos);
        return "cultivo-list.html";
    }
    
    @GetMapping("/verduras")
    public String listarVerduras(ModelMap modelo) {
        List<Cultivo> cultivos = cultivoServicio.listarVegetales();
        modelo.addAttribute("cultivos", cultivos);
        return "cultivo-list.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        Cultivo cultivo = cultivoServicio.getOne(id);
        modelo.put("cultivo", cultivo);
        return "cultivo-modificar.html";

    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, @RequestParam String nombre, @RequestParam String tipoCultivo,
            @RequestParam String temperatura, @RequestParam String agua, @RequestParam String luz,
            @RequestParam String suelo, @RequestParam String estacion, String observaciones, @RequestParam MultipartFile archivo, ModelMap modelo) throws Exception {
        try {
            cultivoServicio.modificarCultivo(id, nombre, tipoCultivo, temperatura, agua, luz, suelo, estacion, suelo, observaciones, archivo);
            return "redirect:../lista";
        } catch (MiExcepcion ex) {
            // TODO Auto-generated catch block
            modelo.put("error", "No se pudo editar");
            return "/";
        }

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/baja/{id}")
    public String baja(ModelMap modelo, @PathVariable String id) {

        try {
            cultivoServicio.baja(id);
            return "cultivo-list.html";
        } catch (Exception e) {
            return "cultivo-list.html";
        }

    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/alta/{id}")
    public String alta(ModelMap modelo, @PathVariable String id) {

        try {
            cultivoServicio.alta(id);
            return "redirect:/admin/listacultivos";
        } catch (Exception e) {
            return "redirect:/admin/listacultivos";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id) {

        try {
            cultivoServicio.baja(id);
            return "redirect:/admin/listacultivos";
        } catch (Exception e) {
            return "redirect:/admin/listacultivos";
        }

    }

}
