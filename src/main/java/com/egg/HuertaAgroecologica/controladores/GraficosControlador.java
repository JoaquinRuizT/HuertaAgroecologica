/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.HuertaAgroecologica.controladores;

import com.egg.HuertaAgroecologica.servicios.ProduccionServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Usuario
 */
@Controller
@RequestMapping("/graficos")
public class GraficosControlador {

    @Autowired
    private ProduccionServicio produccionServicio;

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
