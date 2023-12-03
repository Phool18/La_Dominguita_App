package la.dominga.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import la.dominga.entity.RespuestaServidor;
import la.dominga.entity.Usuario;
import la.dominga.repository.UsuarioRepository;

public class UsuarioViewModel extends AndroidViewModel {

    private final UsuarioRepository repository;

    public UsuarioViewModel(@NonNull @NotNull Application application) {
        super(application);
        this.repository = UsuarioRepository.getInstance();
    }

    public LiveData<RespuestaServidor<Usuario>> login(String correo, String clave){
        return repository.login(correo, clave);
    }

    public LiveData<RespuestaServidor<Usuario>> save(Usuario usuario){
        return repository.save(usuario);
    }


    public LiveData<RespuestaServidor<List<Usuario>>> listarUsuarios() {
        return repository.listarUsuarios();
    }

    public LiveData<RespuestaServidor<Usuario>> actualizarUsuario(Usuario usuario) {
        return repository.actualizarUsuario(usuario);
    }

    public LiveData<RespuestaServidor<List<Usuario>>> buscarUsuarioPorNombre(String nombreCompleto) {
        return repository.buscarUsuarioPorNombre(nombreCompleto);
    }



}
