package la.dominga.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import la.dominga.api.ConfigApi;
import la.dominga.api.UsuarioApi;
import la.dominga.entity.RespuestaServidor;
import la.dominga.entity.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioRepository {

    private static UsuarioRepository repository;

    private final UsuarioApi api;

    public static UsuarioRepository getInstance(){
        if(repository == null){
            repository = new UsuarioRepository();
        }
        return repository;
    }
    public UsuarioRepository() {
        this.api = ConfigApi.getUsuarioApi();
    }

    public LiveData<RespuestaServidor<Usuario>> login(String correo, String clave){
        final MutableLiveData<RespuestaServidor<Usuario>> mld = new MutableLiveData<>();
        this.api.login(correo, clave).enqueue(new Callback<RespuestaServidor<Usuario>>() {
            @Override
            public void onResponse(Call<RespuestaServidor<Usuario>> call, Response<RespuestaServidor<Usuario>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<RespuestaServidor<Usuario>> call, Throwable t) {
                mld.setValue(new RespuestaServidor<>());
                System.out.println("SE HA PRODUCIDO UN ERROR: "+ t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<RespuestaServidor<Usuario>> guardarUsuario(Usuario usuario){
        final MutableLiveData<RespuestaServidor<Usuario>> mld = new MutableLiveData<>();
        this.api.save(usuario).enqueue(new Callback<RespuestaServidor<Usuario>>() {
            @Override
            public void onResponse(Call<RespuestaServidor<Usuario>> call, Response<RespuestaServidor<Usuario>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<RespuestaServidor<Usuario>> call, Throwable t) {
                mld.setValue(new RespuestaServidor<>());
                System.out.println("SE HA PRODUCIDO UN ERROR: "+ t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}
