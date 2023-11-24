package la.dominga.api;

import la.dominga.entity.Foto;
import la.dominga.entity.RespuestaServidor;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FotoApi {
    String base = "foto";
    @Multipart
    @POST(base)
    Call<RespuestaServidor<Foto>> save(@Part MultipartBody.Part file, @Part("nombre") RequestBody requestBody);
}
