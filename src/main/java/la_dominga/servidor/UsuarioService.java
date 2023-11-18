package la_dominga.servidor;



import la_dominga.configuraciones.RespuestaServidor;
import la_dominga.entidades.Usuario;
import la_dominga.repositorio.UsuarioRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static la_dominga.configuraciones.Global.*;


@Service
@Transactional
public class UsuarioService {
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }
    //Método para iniciar sesión
    public RespuestaServidor<Usuario> iniciarSesion(String correo, String clave){
        Optional<Usuario> optU = this.repository.iniciarSesion(correo, clave);
        if(optU.isPresent()){
            return new RespuestaServidor<Usuario>(TIPO_AUTH, RPTA_OK, "Haz iniciado sesión correctamente", optU.get());
        }else{
            return new RespuestaServidor<Usuario>(TIPO_AUTH, RPTA_WARNING, "Lo sentimos, ese usuario no existe", new Usuario());
        }
    }
    //Método para guardar credenciales del usuario
    public RespuestaServidor guardarUsuario(Usuario u){
        Optional<Usuario> optU = this.repository.findById(u.getId());
        int idf = optU.isPresent() ? optU.get().getId() : 0;
        if(idf == 0){
            return new RespuestaServidor(TIPO_DATA, RPTA_OK, "Usuario Registrado Correctamente", this.repository.save(u));
        }else{
            return new RespuestaServidor(TIPO_DATA, RPTA_OK, "Datos del usuario actualizados", this.repository.save(u));
        }
    }
    public List<Usuario> listarUsuarios() {
        return repository.findAll();
    }
}