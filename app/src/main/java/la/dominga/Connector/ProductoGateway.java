package la.dominga.Connector;

import la.dominga.entity.Producto;
import la.dominga.entity.RespuestaServidor;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.POST;

public interface ProductoGateway {

    String base = "/productos";

    @GET(base + "/nombre/{nombre}")
    Call<RespuestaServidor> listarProductosPorNombre(@Path("nombre") String nombre);

    @GET(base + "/categoria/{idCategoria}")
    Call<RespuestaServidor> listarProductosPorCategoria(@Path("idCategoria") int idCategoria);

    @POST(base + "/guardar")
    Call<RespuestaServidor<Producto>> agregarProducto(@Body Producto producto);
}