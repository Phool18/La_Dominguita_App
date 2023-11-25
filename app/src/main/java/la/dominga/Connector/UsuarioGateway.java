package la.dominga.Connector;

import java.util.List;

import la.dominga.entity.RespuestaServidor;
import la.dominga.entity.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface UsuarioGateway {

    String base= "/usuario";

    @POST(base)
    Call<RespuestaServidor<Usuario>> save (@Body Usuario usuario);

    @FormUrlEncoded
    @POST(base+"/iniciarSesion")
    Call<RespuestaServidor<Usuario>> login(@Field("correo")String correo, @Field("clave") String clave);
    @GET(base + "/listar")
    Call<RespuestaServidor<List<Usuario>>> listarUsuarios();

    @PUT(base + "/actualizar")
    Call<RespuestaServidor<Usuario>> actualizarUsuario(@Body Usuario usuario);

    @GET(base + "/buscarPorNombre")
    Call<RespuestaServidor<List<Usuario>>> buscarUsuarioPorNombre(@Query("nombreCompleto") String nombreCompleto);

}
