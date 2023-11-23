package la.dominga.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import la.dominga.api.ClienteApi;
import la.dominga.api.ConfigApi;
import la.dominga.entity.Cliente;
import la.dominga.entity.RespuestaServidor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClienteRepository {

    private static ClienteRepository repository;

    private final ClienteApi api;

    public static ClienteRepository getInstance(){
        if(repository == null){
            repository = new ClienteRepository();
        }
        return repository;
    }
    public ClienteRepository() {
        this.api = ConfigApi.getClienteApi();
    }

    public LiveData<RespuestaServidor<Cliente>> guardarCliente(Cliente cliente){
        final MutableLiveData<RespuestaServidor<Cliente>> mld = new MutableLiveData<>();
        this.api.guardarCliente(cliente).enqueue(new Callback<RespuestaServidor<Cliente>>() {
            @Override
            public void onResponse(Call<RespuestaServidor<Cliente>> call, Response<RespuestaServidor<Cliente>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<RespuestaServidor<Cliente>> call, Throwable t) {
                mld.setValue(new RespuestaServidor<>());
                System.out.println("SE HA PRODUCIDO UN ERROR: "+ t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }



}
