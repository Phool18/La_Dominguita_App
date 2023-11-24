package la.dominga.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import la.dominga.api.ConfigApi;
import la.dominga.api.FotoApi;
import la.dominga.entity.Foto;
import la.dominga.entity.RespuestaServidor;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FotoRepository {
    private final FotoApi api;
    private static FotoRepository repository;

    public FotoRepository() {
        this.api = ConfigApi.getFotoApi();
    }

    public static FotoRepository getInstance(){
        if(repository == null){
            repository = new FotoRepository();
        }
        return repository;
    }

    public LiveData<RespuestaServidor<Foto>> savePhoto(MultipartBody.Part part, RequestBody requestBody){
        final MutableLiveData<RespuestaServidor<Foto>> mld = new MutableLiveData<>();
        this.api.save(part, requestBody).enqueue(new Callback<RespuestaServidor<Foto>>() {
            @Override
            public void onResponse(Call<RespuestaServidor<Foto>> call, Response<RespuestaServidor<Foto>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<RespuestaServidor<Foto>> call, Throwable t) {
                mld.setValue(new RespuestaServidor<>());
                System.out.println("Se ha producido un error : " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}
