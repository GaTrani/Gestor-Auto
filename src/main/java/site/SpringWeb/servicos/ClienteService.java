package site.SpringWeb.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import site.SpringWeb.modelos.Cliente;
import site.SpringWeb.repositorio.ClientesRepo;

@Service
public class ClienteService {

    private static ClientesRepo clientesRepo;

    public ClienteService(ClientesRepo clientesRepo) {
        ClienteService.clientesRepo = clientesRepo;
    }

    public List<Cliente> buscarTodos() {
        return (List<Cliente>) clientesRepo.findAll();
    }

    public static Cliente buscarPorId(Integer clienteId) {
        Optional<Cliente> clienteOptional = clientesRepo.findById(clienteId);
        return clienteOptional.orElse(null);
    }

    public Optional<Cliente> buscarPorId2(Integer id) {
        return clientesRepo.findById(id);
    }

    //public static Cliente buscarPorId(String clienteId) {
        // TODO Auto-generated method stub
    //    throw new UnsupportedOperationException("Unimplemented method 'buscarPorId'");
    //}
}