package la.dominga.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import la.dominga.Connector.Connector;
import la.dominga.Connector.TarjetaCreditoGateway;
import la.dominga.entity.RespuestaServidor;
import la.dominga.entity.TarjetaCredito;
import la.dominga.entity.dto.TarjetaCreditoDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TarjetaRepository {

    private static TarjetaRepository repository;

    private final TarjetaCreditoGateway api;

    public static TarjetaRepository getInstance(){
        if(repository == null){
            repository = new TarjetaRepository();
        }
        return repository;
    }

    public TarjetaRepository() {
        this.api = Connector.getTarjetaCreditoApi();
    }

    public LiveData<RespuestaServidor<TarjetaCredito>> guardarTarjeta(TarjetaCredito tarjeta){
        final MutableLiveData<RespuestaServidor<TarjetaCredito>> mld = new MutableLiveData<>();
        this.api.guardarTarjeta(tarjeta).enqueue(new Callback<RespuestaServidor<TarjetaCredito>>() {
            @Override
            public void onResponse(Call<RespuestaServidor<TarjetaCredito>> call, Response<RespuestaServidor<TarjetaCredito>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<RespuestaServidor<TarjetaCredito>> call, Throwable t) {
                mld.setValue(new RespuestaServidor<>());
                System.out.println("Se ha producido un error: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<RespuestaServidor<String>> validarTarjeta(String numeroTarjeta, String titular, String cvv, String mesAnio){
        final MutableLiveData<RespuestaServidor<String>> mld = new MutableLiveData<>();
        this.api.validarTarjeta(numeroTarjeta, titular, cvv, mesAnio).enqueue(new Callback<RespuestaServidor<String>>() {
            @Override
            public void onResponse(Call<RespuestaServidor<String>> call, Response<RespuestaServidor<String>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<RespuestaServidor<String>> call, Throwable t) {
                mld.setValue(new RespuestaServidor<>());
                System.out.println("Se ha producido un error: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<RespuestaServidor<List<TarjetaCreditoDTO>>> listarTarjetasPorUsuario(int usuarioId){
        final MutableLiveData<RespuestaServidor<List<TarjetaCreditoDTO>>> mld = new MutableLiveData<>();
        this.api.listarTarjetasPorUsuario(usuarioId).enqueue(new Callback<RespuestaServidor<List<TarjetaCreditoDTO>>>() {
            @Override
            public void onResponse(Call<RespuestaServidor<List<TarjetaCreditoDTO>>> call, Response<RespuestaServidor<List<TarjetaCreditoDTO>>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<RespuestaServidor<List<TarjetaCreditoDTO>>> call, Throwable t) {
                mld.setValue(new RespuestaServidor<>());
                System.out.println("Se ha producido un error: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}
