package com.egg.HuertaAgroecologica.controladores;

import com.egg.HuertaAgroecologica.servicios.CultivoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

}
