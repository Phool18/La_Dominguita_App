package la.dominga.Connector;

import la.dominga.entity.Categoria;
import la.dominga.entity.RespuestaServidor;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface CategoriaGateway {

    String base = "/categorias";

    @GET(base)
    Call<RespuestaServidor<List<Categoria>>> listarNombresDeCategorias();

    @POST(base)
    Call<RespuestaServidor<Categoria>> agregarCategoria(@Body Categoria categoria);
}
