package la_dominga.repositorio;


import la_dominga.entidades.Cliente;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClienteRepository extends CrudRepository<Cliente, Integer> {
    List<Cliente> findAll();

}