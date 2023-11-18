package la_dominga.entidades;

import javax.persistence.*;
import java.util.List;
@Entity
public class CarritoDeCompras {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Usuario usuario;

    @OneToMany(mappedBy = "carritoDeCompras", cascade = CascadeType.ALL)
    private List<ItemCarrito> items;


    public CarritoDeCompras() {
    }

    public CarritoDeCompras(Long id, Usuario usuario, List<ItemCarrito> items) {
        this.id = id;
        this.usuario = usuario;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ItemCarrito> getItems() {
        return items;
    }

    public void setItems(List<ItemCarrito> items) {
        this.items = items;
    }
}
