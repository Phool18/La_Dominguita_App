package la.dominga.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import la.dominga.entity.Picture;
import la.dominga.entity.RespuestaServidor;
import la.dominga.repository.PictureRepository;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PictureViewModel extends AndroidViewModel {
    private final PictureRepository repository;

    public PictureViewModel(@NonNull Application application) {
        super(application);
        this.repository = PictureRepository.getInstance();
    }
    public LiveData<RespuestaServidor<Picture>> save(MultipartBody.Part part, RequestBody requestBody){
        return this.repository.savePhoto(part, requestBody);
    }
}
