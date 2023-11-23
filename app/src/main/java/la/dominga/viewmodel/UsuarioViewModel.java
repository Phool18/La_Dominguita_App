package la.dominga.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import la.dominga.entity.Cliente;
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
        return repository.guardarUsuario(usuario);
    }

}
