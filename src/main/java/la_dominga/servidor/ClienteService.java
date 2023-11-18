package la_dominga.servidor;

import la_dominga.entidades.Cliente;
import la_dominga.repositorio.ClienteRepository;
import la_dominga.configuraciones.RespuestaServidor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final Validator validator;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, Validator validator) {
        this.clienteRepository = clienteRepository;
        this.validator = validator;
    }

    public RespuestaServidor<Optional<Cliente>> guardarCliente(Cliente cliente) {
        Set<ConstraintViolation<Cliente>> violaciones = validator.validate(cliente);
        if (!violaciones.isEmpty()) {
            StringBuilder mensajes = new StringBuilder();
            for (ConstraintViolation<Cliente> violacion : violaciones) {
                mensajes.append(violacion.getMessage()).append("\n");
            }
            return new RespuestaServidor<>("Error", 400, mensajes.toString(), Optional.empty());
        }

        try {
            Cliente clienteGuardado = clienteRepository.save(cliente);
            return new RespuestaServidor<>("Success", 200, "Cliente guardado con éxito", Optional.of(clienteGuardado));
        } catch (Exception e) {
            return new RespuestaServidor<>("Error", 500, "Error al guardar el cliente: " + e.getMessage(), Optional.empty());
        }
    }
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }
}
