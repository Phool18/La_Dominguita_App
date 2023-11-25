package la.dominga.Connector;

import la.dominga.entity.dto.CrearPedidoDTO;
import la.dominga.entity.dto.ImprimirPedidosDTO;
import la.dominga.entity.RespuestaServidor;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface CarritoDeComprasGateway {

    String base = "/carritoDeCompras";

    @GET(base + "/misCompras/{idCliente}")
    Call<RespuestaServidor<List<ImprimirPedidosDTO>>> obtenerMisCompras(@Path("idCliente") int idCliente);

    @POST(base)
    Call<RespuestaServidor> generarPedidoAlCliente(@Body CrearPedidoDTO crearPedidoDTO);
}
