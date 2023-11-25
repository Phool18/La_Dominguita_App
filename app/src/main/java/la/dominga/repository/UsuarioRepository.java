package la.dominga.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import la.dominga.Connector.Connector;
import la.dominga.Connector.UsuarioGateway;
import la.dominga.entity.RespuestaServidor;
import la.dominga.entity.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioRepository {

    private static UsuarioRepository repository;

    private final UsuarioGateway api;

    public static UsuarioRepository getInstance(){
        if(repository == null){
            repository = new UsuarioRepository();
        }
        return repository;
    }
    public UsuarioRepository() {
        this.api = Connector.getUsuarioApi();
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

    public LiveData<RespuestaServidor<Usuario>> save (Usuario u){
        final MutableLiveData<RespuestaServidor<Usuario>> mld = new MutableLiveData<>();
        this.api.save(u).enqueue(new Callback<RespuestaServidor<Usuario>>() {
            @Override
            public void onResponse(Call<RespuestaServidor<Usuario>> call, Response<RespuestaServidor<Usuario>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<RespuestaServidor<Usuario>> call, Throwable t) {
                mld.setValue(new RespuestaServidor<>());
                System.out.println("Se ha producido un error : " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<RespuestaServidor<List<Usuario>>> listarUsuarios() {
        final MutableLiveData<RespuestaServidor<List<Usuario>>> mld = new MutableLiveData<>();
        this.api.listarUsuarios().enqueue(new Callback<RespuestaServidor<List<Usuario>>>() {
            @Override
            public void onResponse(Call<RespuestaServidor<List<Usuario>>> call, Response<RespuestaServidor<List<Usuario>>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<RespuestaServidor<List<Usuario>>> call, Throwable t) {
                mld.setValue(new RespuestaServidor<>());
                System.out.println("Se ha producido un error: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<RespuestaServidor<Usuario>> actualizarUsuario(Usuario usuario) {
        final MutableLiveData<RespuestaServidor<Usuario>> mld = new MutableLiveData<>();
        this.api.actualizarUsuario(usuario).enqueue(new Callback<RespuestaServidor<Usuario>>() {
            @Override
            public void onResponse(Call<RespuestaServidor<Usuario>> call, Response<RespuestaServidor<Usuario>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<RespuestaServidor<Usuario>> call, Throwable t) {
                mld.setValue(new RespuestaServidor<>());
                System.out.println("Se ha producido un error: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<RespuestaServidor<List<Usuario>>> buscarUsuarioPorNombre(String nombreCompleto) {
        final MutableLiveData<RespuestaServidor<List<Usuario>>> mld = new MutableLiveData<>();
        this.api.buscarUsuarioPorNombre(nombreCompleto).enqueue(new Callback<RespuestaServidor<List<Usuario>>>() {
            @Override
            public void onResponse(Call<RespuestaServidor<List<Usuario>>> call, Response<RespuestaServidor<List<Usuario>>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<RespuestaServidor<List<Usuario>>> call, Throwable t) {
                mld.setValue(new RespuestaServidor<>());
                System.out.println("Se ha producido un error: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }



}
