package la.dominga.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import la.dominga.entity.DatosCompra;
import la.dominga.entity.dto.CrearPedidoDTO;
import la.dominga.entity.dto.ImprimirPedidosDTO;
import la.dominga.entity.RespuestaServidor;
import la.dominga.repository.CarritoDeComprasRepository;
import okhttp3.ResponseBody;

public class CarritoDeComprasViewModel extends AndroidViewModel {

    private final CarritoDeComprasRepository repository;

    public CarritoDeComprasViewModel(@NonNull Application application) {
        super(application);
        this.repository = CarritoDeComprasRepository.getInstance();
    }

    public LiveData<RespuestaServidor<List<ImprimirPedidosDTO>>> obtenerMisCompras(int idCliente){
        return repository.obtenerMisCompras(idCliente);
    }

    public LiveData<RespuestaServidor> generarPedidoAlCliente(CrearPedidoDTO crearPedidoDTO){
        return repository.generarPedidoAlCliente(crearPedidoDTO);
    }

    public LiveData<RespuestaServidor<List<DatosCompra>>> obtenerDetallesCarrito(int idCarrito) {
        return repository.obtenerDetallesCarrito(idCarrito);
    }

    public LiveData<ResponseBody> descargarBoleta(int idCompra) {
        return repository.descargarBoleta(idCompra);
    }


}
