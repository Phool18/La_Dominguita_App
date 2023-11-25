package la.dominga.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import la.dominga.Connector.CarritoDeComprasGateway;
import la.dominga.Connector.Connector;
import la.dominga.entity.dto.CrearPedidoDTO;
import la.dominga.entity.dto.ImprimirPedidosDTO;
import la.dominga.entity.RespuestaServidor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class CarritoDeComprasRepository {

    private static CarritoDeComprasRepository repository;

    private final CarritoDeComprasGateway api;

    public static CarritoDeComprasRepository getInstance(){
        if(repository == null){
            repository = new CarritoDeComprasRepository();
        }
        return repository;
    }

    public CarritoDeComprasRepository() {
        this.api = Connector.getCarritoDeComprasApi();
    }

    public LiveData<RespuestaServidor<List<ImprimirPedidosDTO>>> obtenerMisCompras(int idCliente){
        final MutableLiveData<RespuestaServidor<List<ImprimirPedidosDTO>>> mld = new MutableLiveData<>();
        this.api.obtenerMisCompras(idCliente).enqueue(new Callback<RespuestaServidor<List<ImprimirPedidosDTO>>>() {
            @Override
            public void onResponse(Call<RespuestaServidor<List<ImprimirPedidosDTO>>> call, Response<RespuestaServidor<List<ImprimirPedidosDTO>>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<RespuestaServidor<List<ImprimirPedidosDTO>>> call, Throwable t) {
                mld.setValue(new RespuestaServidor<>());
                System.out.println("SE HA PRODUCIDO UN ERROR: "+ t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<RespuestaServidor> generarPedidoAlCliente(CrearPedidoDTO crearPedidoDTO){
        final MutableLiveData<RespuestaServidor> mld = new MutableLiveData<>();
        this.api.generarPedidoAlCliente(crearPedidoDTO).enqueue(new Callback<RespuestaServidor>() {
            @Override
            public void onResponse(Call<RespuestaServidor> call, Response<RespuestaServidor> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<RespuestaServidor> call, Throwable t) {
                mld.setValue(new RespuestaServidor<>());
                System.out.println("SE HA PRODUCIDO UN ERROR: "+ t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}
