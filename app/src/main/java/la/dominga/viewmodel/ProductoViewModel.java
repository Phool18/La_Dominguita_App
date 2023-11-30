package la.dominga.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import la.dominga.entity.Categoria;
import la.dominga.entity.Producto;
import la.dominga.entity.RespuestaServidor;
import la.dominga.repository.ProductoRepository;

public class ProductoViewModel extends AndroidViewModel {

    private final ProductoRepository repository;

    public ProductoViewModel(@NonNull Application application) {
        super(application);
        this.repository = ProductoRepository.getInstance();
    }

    public LiveData<RespuestaServidor> listarProductosPorNombre(String nombre) {
        return repository.listarProductosPorNombre(nombre);
    }

    public LiveData<RespuestaServidor> listarProductosPorCategoria(int idCategoria) {
        return repository.listarProductosPorCategoria(idCategoria);
    }

    public LiveData<RespuestaServidor<Producto>> agregarProducto(Producto producto) {
        return repository.agregarProducto(producto);
    }

    public LiveData<RespuestaServidor<List<Producto>>> listarProductosTop(){
        return repository.listarProductosTop();
    }
}
