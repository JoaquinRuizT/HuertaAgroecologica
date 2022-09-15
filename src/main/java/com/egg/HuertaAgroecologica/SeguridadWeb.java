/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.HuertaAgroecologica;

import com.egg.HuertaAgroecologica.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author POSITIVO BGH
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadWeb extends WebSecurityConfigurerAdapter {
    @Autowired
    public UsuarioServicio usuarioServicio;
    
    @Autowired
   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(usuarioServicio)
               .passwordEncoder(new BCryptPasswordEncoder());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
           .authorizeRequests()
                .antMatchers("/admin/*").hasRole("ADMIN")
                .antMatchers("/css/*", "/js/*", "/img/*", "/**")
                .permitAll()
            .and().formLogin()
                .loginPage("/login")//esto indica cual es la url donde se va a encontrar nuestro formulario de login
                .loginProcessingUrl("/logincheck")//es la url con la cual autentica y procesa nuestro inicio de sesion, debe coinicdir con el action del formulario de logueo
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/inicio")//si genera un login corrrecto
                .permitAll()
        .and().logout()//salida del sistema
                .logoutUrl("/logout")//cuando se cierra
                .logoutSuccessUrl("/login")//va a la url de login 
                .permitAll()
        .and().csrf()
                .disable();
        
    }

  

    
}
