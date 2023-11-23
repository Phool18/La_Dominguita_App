package la.dominga.api;

import la.dominga.entity.RespuestaServidor;
import la.dominga.entity.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UsuarioApi {

    String base= "/usuario";

    @POST(base)
    Call<RespuestaServidor<Usuario>> save (@Body Usuario usuario);

    @FormUrlEncoded
    @POST(base+"/iniciarSesion")
    Call<RespuestaServidor<Usuario>> login(@Field("correo")String correo, @Field("clave") String clave);

}
