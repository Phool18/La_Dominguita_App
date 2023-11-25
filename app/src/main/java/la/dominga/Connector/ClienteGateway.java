package la.dominga.Connector;

import java.util.List;
import la.dominga.entity.Cliente;
import la.dominga.entity.RespuestaServidor;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ClienteGateway {

    String base = "/cliente";

    @POST(base)
    Call<RespuestaServidor<Cliente>> save(@Body Cliente c);
    @GET(base)
    Call<RespuestaServidor<List<Cliente>>> listarClientes();

}
