package la.dominga.Connector;

import la.dominga.entity.DatosCompra;
import la.dominga.entity.dto.CrearPedidoDTO;
import la.dominga.entity.dto.ImprimirPedidosDTO;
import la.dominga.entity.RespuestaServidor;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

import java.util.List;

public interface CarritoDeComprasGateway {

    String base = "/carritoDeCompras";

    @GET(base + "/misCompras/{idCliente}")
    Call<RespuestaServidor<List<ImprimirPedidosDTO>>> obtenerMisCompras(@Path("idCliente") int idCliente);

    @POST(base)
    Call<RespuestaServidor> generarPedidoAlCliente(@Body CrearPedidoDTO crearPedidoDTO);

    @GET(base + "/detalles/{idCarrito}")
    Call<RespuestaServidor<List<DatosCompra>>> obtenerDetallesCarrito(@Path("idCarrito") int idCarrito);
    @Streaming // Importante para manejar correctamente la descarga del archivo
    @GET(base + "/boleta/{idCompra}")
    Call<ResponseBody> descargarBoleta(@Path("idCompra") int idCompra);

}
