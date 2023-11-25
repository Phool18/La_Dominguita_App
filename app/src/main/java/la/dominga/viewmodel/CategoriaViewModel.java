package la.dominga.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import la.dominga.entity.Categoria;
import la.dominga.entity.RespuestaServidor;
import la.dominga.repository.CategoriaRepository;

public class CategoriaViewModel extends AndroidViewModel {

    private final CategoriaRepository repository;

    public CategoriaViewModel(@NonNull Application application) {
        super(application);
        this.repository = CategoriaRepository.getInstance();
    }

    public LiveData<RespuestaServidor<List<Categoria>>> listarCategoriasBD() {
        return repository.listarCategoriasBD();
    }

    public LiveData<RespuestaServidor<Categoria>> agregarCategoria(Categoria categoria) {
        return repository.agregarCategoria(categoria);
    }
}
