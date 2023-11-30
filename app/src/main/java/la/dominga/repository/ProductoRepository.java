package la.dominga.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import la.dominga.Connector.Connector;
import la.dominga.Connector.ProductoGateway;
import la.dominga.entity.Categoria;
import la.dominga.entity.Producto;
import la.dominga.entity.RespuestaServidor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductoRepository {

    private static ProductoRepository repository;

    private final ProductoGateway api;

    public static ProductoRepository getInstance(){
        if(repository == null){
            repository = new ProductoRepository();
        }
        return repository;
    }

    public ProductoRepository() {
        this.api = Connector.getProductoApi();
    }

    public LiveData<RespuestaServidor> listarProductosPorNombre(String nombre){
        final MutableLiveData<RespuestaServidor> mld = new MutableLiveData<>();
        this.api.listarProductosPorNombre(nombre).enqueue(new Callback<RespuestaServidor>() {
            @Override
            public void onResponse(Call<RespuestaServidor> call, Response<RespuestaServidor> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<RespuestaServidor> call, Throwable t) {
                mld.setValue(new RespuestaServidor<>());
                System.out.println("Se ha producido un error: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<RespuestaServidor> listarProductosPorCategoria(int idCategoria){
        final MutableLiveData<RespuestaServidor> mld = new MutableLiveData<>();
        this.api.listarProductosPorCategoria(idCategoria).enqueue(new Callback<RespuestaServidor>() {
            @Override
            public void onResponse(Call<RespuestaServidor> call, Response<RespuestaServidor> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<RespuestaServidor> call, Throwable t) {
                mld.setValue(new RespuestaServidor<>());
                System.out.println("Se ha producido un error: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<RespuestaServidor<Producto>> agregarProducto(Producto producto){
        final MutableLiveData<RespuestaServidor<Producto>> mld = new MutableLiveData<>();
        this.api.agregarProducto(producto).enqueue(new Callback<RespuestaServidor<Producto>>() {
            @Override
            public void onResponse(Call<RespuestaServidor<Producto>> call, Response<RespuestaServidor<Producto>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<RespuestaServidor<Producto>> call, Throwable t) {
                mld.setValue(new RespuestaServidor<>());
                System.out.println("Se ha producido un error: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<RespuestaServidor<List<Producto>>> listarProductosTop(){
        final MutableLiveData<RespuestaServidor<List<Producto>>> mld = new MutableLiveData<>();
        this.api.listarProductosTop().enqueue(new Callback<RespuestaServidor<List<Producto>>>() {
            @Override
            public void onResponse(Call<RespuestaServidor<List<Producto>>> call, Response<RespuestaServidor<List<Producto>>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<RespuestaServidor<List<Producto>>> call, Throwable t) {
                mld.setValue(new RespuestaServidor<>());
                System.out.println("Se ha producido un error: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}
