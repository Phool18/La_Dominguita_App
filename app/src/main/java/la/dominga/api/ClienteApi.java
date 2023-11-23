package la.dominga.api;

import la.dominga.entity.Cliente;
import la.dominga.entity.RespuestaServidor;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ClienteApi {

    String base = "/cliente";

    @POST(base)
    Call<RespuestaServidor<Cliente>> guardarCliente(@Body Cliente c);
}
