package la.dominga.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import la.dominga.Connector.Connector;
import la.dominga.Connector.PictureGateway;
import la.dominga.entity.Picture;
import la.dominga.entity.RespuestaServidor;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PictureRepository {
    private final PictureGateway api;
    private static PictureRepository repository;

    public PictureRepository() {
        this.api = Connector.getFotoApi();
    }

    public static PictureRepository getInstance(){
        if(repository == null){
            repository = new PictureRepository();
        }
        return repository;
    }

    public LiveData<RespuestaServidor<Picture>> savePhoto(MultipartBody.Part part, RequestBody requestBody){
        final MutableLiveData<RespuestaServidor<Picture>> mld = new MutableLiveData<>();
        this.api.save(part, requestBody).enqueue(new Callback<RespuestaServidor<Picture>>() {
            @Override
            public void onResponse(Call<RespuestaServidor<Picture>> call, Response<RespuestaServidor<Picture>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<RespuestaServidor<Picture>> call, Throwable t) {
                mld.setValue(new RespuestaServidor<>());
                System.out.println("Se ha producido un error : " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}
