package la.dominga.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import la.dominga.Connector.CategoriaGateway;
import la.dominga.Connector.Connector;
import la.dominga.entity.Categoria;
import la.dominga.entity.RespuestaServidor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class CategoriaRepository {

    private static CategoriaRepository repository;

    private final CategoriaGateway api;

    public static CategoriaRepository getInstance(){
        if(repository == null){
            repository = new CategoriaRepository();
        }
        return repository;
    }

    public CategoriaRepository() {
        this.api = Connector.getCategoriaApi();
    }

    public LiveData<RespuestaServidor<List<Categoria>>> listarCategoriasBD(){
        final MutableLiveData<RespuestaServidor<List<Categoria>>> mld = new MutableLiveData<>();
        this.api.listarCategoriasBD().enqueue(new Callback<RespuestaServidor<List<Categoria>>>() {
            @Override
            public void onResponse(Call<RespuestaServidor<List<Categoria>>> call, Response<RespuestaServidor<List<Categoria>>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<RespuestaServidor<List<Categoria>>> call, Throwable t) {
                mld.setValue(new RespuestaServidor<>());
                System.out.println("SE HA PRODUCIDO UN ERROR: "+ t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<RespuestaServidor<Categoria>> agregarCategoria(Categoria categoria){
        final MutableLiveData<RespuestaServidor<Categoria>> mld = new MutableLiveData<>();
        this.api.agregarCategoria(categoria).enqueue(new Callback<RespuestaServidor<Categoria>>() {
            @Override
            public void onResponse(Call<RespuestaServidor<Categoria>> call, Response<RespuestaServidor<Categoria>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<RespuestaServidor<Categoria>> call, Throwable t) {
                mld.setValue(new RespuestaServidor<>());
                System.out.println("SE HA PRODUCIDO UN ERROR: "+ t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}
