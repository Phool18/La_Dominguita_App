package la.dominga.Connector;

import java.util.List;

import la.dominga.entity.TarjetaCredito;
import la.dominga.entity.RespuestaServidor;
import la.dominga.entity.dto.TarjetaCreditoDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Path;

public interface TarjetaCreditoGateway {

    String base = "/tarjeta";

    @POST(base + "/guardar")
    Call<RespuestaServidor<TarjetaCredito>> guardarTarjeta(@Body TarjetaCredito tarjeta);

    @GET(base + "/validar")
    Call<RespuestaServidor<String>> validarTarjeta(
            @Query("numeroTarjeta") String numeroTarjeta,
            @Query("titular") String titular,
            @Query("cvv") String cvv,
            @Query("mesAnio") String mesAnio
    );

    @GET(base + "/usuario/{usuarioId}")
    Call<RespuestaServidor<List<TarjetaCreditoDTO>>> listarTarjetasPorUsuario(@Path("usuarioId") int usuarioId);
}
