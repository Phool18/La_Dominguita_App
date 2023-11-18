package la_dominga.controlador;


import la_dominga.configuraciones.RespuestaServidor;
import la_dominga.entidades.Usuario;
import la_dominga.servidor.UsuarioService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("usuario")
public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public RespuestaServidor guardar(@RequestBody Usuario u){
        return this.service.guardarUsuario(u);
    }

    @PostMapping("/login")
    public RespuestaServidor<Usuario> iniciarSesion(HttpServletRequest request){
        String email = request.getParameter("correo");
        String contrasenia = request.getParameter("clave");
        return this.service.iniciarSesion(email, contrasenia);
    }
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return service.listarUsuarios();
    }
}