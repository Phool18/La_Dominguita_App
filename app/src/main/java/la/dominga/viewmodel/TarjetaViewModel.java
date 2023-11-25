package la.dominga.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import la.dominga.entity.RespuestaServidor;
import la.dominga.entity.TarjetaCredito;
import la.dominga.entity.dto.TarjetaCreditoDTO;
import la.dominga.repository.TarjetaRepository;

import java.util.List;

public class TarjetaViewModel extends AndroidViewModel {

    private final TarjetaRepository repository;

    public TarjetaViewModel(@NonNull Application application) {
        super(application);
        this.repository = TarjetaRepository.getInstance();
    }

    public LiveData<RespuestaServidor<TarjetaCredito>> guardarTarjeta(TarjetaCredito tarjeta){
        return repository.guardarTarjeta(tarjeta);
    }

    public LiveData<RespuestaServidor<String>> validarTarjeta(String numeroTarjeta, String titular, String cvv, String mesAnio){
        return repository.validarTarjeta(numeroTarjeta, titular, cvv, mesAnio);
    }

    public LiveData<RespuestaServidor<List<TarjetaCreditoDTO>>> listarTarjetasPorUsuario(int usuarioId){
        return repository.listarTarjetasPorUsuario(usuarioId);
    }
}
