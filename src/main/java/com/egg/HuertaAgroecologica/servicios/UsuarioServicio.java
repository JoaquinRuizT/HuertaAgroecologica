package com.egg.HuertaAgroecologica.servicios;

import com.egg.HuertaAgroecologica.entidades.Foto;
import com.egg.HuertaAgroecologica.entidades.Usuario;
import com.egg.HuertaAgroecologica.enumeraciones.Rol;
import com.egg.HuertaAgroecologica.excepciones.MiExcepcion;
import com.egg.HuertaAgroecologica.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private FotoServicio fotoServicio;

    @Transactional
    public void registrar(MultipartFile archivo, String nombre, String email, String password, String password2) throws MiExcepcion {

        validarUsuario(nombre, email, password, password2);

        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setActivo(true);
        usuario.setCreado(new Date());
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        if (email.equals("joaquin@admin.com") || email.equals("julieta@admin.com") || email.equals("belen@admin.com") || email.equals("pedro@admin.com")) {
			usuario.setRol(Rol.ADMIN);
		}else {
			usuario.setRol(Rol.GUEST);
		}
        

        //AGREGAMOS FOTO DE PERFIL A USUARIO?
        usuarioRepositorio.save(usuario);

    }

    private void validarUsuario(String nombre, String email, String password, String password2) throws MiExcepcion {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiExcepcion("Debes colocar un nombre");
        }
        if (email.isEmpty() || email == null) {
            throw new MiExcepcion("Debes colocar un email");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiExcepcion("La contraseña debe tener 6 dígitos o más");
        }

        if (!password.equals(password2)) {
            throw new MiExcepcion("Las contraseñas ingresadas deben ser iguales");
        }
        
        if(buscarUsuarioPorEmail(email) != null){
            throw new MiExcepcion("Ya hay un usuario registrado con ese email");
        }

    }

    @Transactional
    public void modificarUsuario(MultipartFile archivo, String nombre, String email, String password, String password2, String id) throws MiExcepcion {
        validarUsuario(nombre, email, password, password2);
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setEmail(email);
            usuario.setNombre(nombre);
            usuario.setPassword(password);
            usuario.setEditado(new Date());
            //usuario.getRol();
            usuarioRepositorio.save(usuario);
        }
    }

    @Transactional
    public boolean loginCheck(String email, String password) {
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
        if (usuario != null && usuario.getPassword().equals(password)) {
            return true;
        }
        return false;

    }

    @Transactional
    public void eliminarUsuario(String id) {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuarioRepositorio.delete(usuario);
            //dejamos delete? o le damos de baja con un atributo de tipo boleano?
        }
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
        if (usuario != null) {
            return usuario;
        } else {
            return null;
        }
    }
    
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList();
        usuarios = usuarioRepositorio.findAll();
        return usuarios;
    }

    public Usuario buscarPorId(String id) {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            return usuario;
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", usuario);

            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        } else {
            return null;
        }
    }

    @Transactional
    public Usuario alta(String id) throws Exception {
        Usuario entidadUsuario = usuarioRepositorio.getOne(id);
        entidadUsuario.setActivo(true);
        return usuarioRepositorio.save(entidadUsuario);
    }

    @Transactional
    public Usuario baja(String id) throws Exception {
        Usuario entidadUsuario = usuarioRepositorio.getOne(id);
        entidadUsuario.setActivo(false);
        return usuarioRepositorio.save(entidadUsuario);
    }
    
    @Transactional
    public Usuario admin(String id) throws Exception{
        Usuario entidadUsuario = usuarioRepositorio.getOne(id);
        entidadUsuario.setRol(Rol.ADMIN);
        return usuarioRepositorio.save(entidadUsuario);
    }
    
    @Transactional
    public Usuario guest(String id) throws Exception{
        Usuario entidadUsuario = usuarioRepositorio.getOne(id);
        entidadUsuario.setRol(Rol.GUEST);
        return usuarioRepositorio.save(entidadUsuario);
    }

}
