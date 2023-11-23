package la.dominga.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import la.dominga.entity.Cliente;
import la.dominga.entity.RespuestaServidor;
import la.dominga.repository.ClienteRepository;

public class ClienteViewModel extends AndroidViewModel {

    private final ClienteRepository repository;
    public ClienteViewModel(@NonNull Application application) {
        super(application);
        this.repository = ClienteRepository.getInstance();
    }

    public LiveData<RespuestaServidor<Cliente>> guardarCliente(Cliente cliente){
        return repository.guardarCliente(cliente);
    }

}
