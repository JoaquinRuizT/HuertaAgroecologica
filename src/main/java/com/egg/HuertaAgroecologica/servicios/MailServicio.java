/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.HuertaAgroecologica.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 *
 * @author POSITIVO BGH
 */
@Service
public class MailServicio {
    
    @Autowired
    private JavaMailSender mailSender;
    @Async
    public void enviarMail (String cuerpo, String titulo, String mail){
        SimpleMailMessage mensaje= new SimpleMailMessage();
        mensaje.setTo(mail);
        mensaje.setFrom("huerta.agroecologica.misiones@gmail.com");
        mensaje.setSubject(titulo);
        mensaje.setText(cuerpo);
        
        mailSender.send(mensaje);
        
        
    }
    
}
