package site.SpringWeb.servicos;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import site.SpringWeb.modelos.Cliente;
import site.SpringWeb.modelos.Veiculo;
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

    public static Cliente buscarPorId3(Integer clienteId) {
        Optional<Cliente> clienteOptional = clientesRepo.findById(clienteId);
        return clienteOptional.orElse(null);
    }

    public Optional<Cliente> buscarPorId2(Integer id) {
        return clientesRepo.findById(id);
    }

    public Optional<Cliente> buscarPorId(int id) {
        return clientesRepo.findById(id);
    }

    public Cliente buscarClientePorId(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarClientePorId'");
    }

    public List<Cliente> findAll() {
        return (List<Cliente>) clientesRepo.findAll();
    }

    public List<String> buscarVeiculosPorClienteId(int clienteId) {
        Optional<Cliente> cliente = clientesRepo.findById(clienteId);
        if (cliente.isPresent()) {
            return cliente.get().getVeiculos().stream().map(Veiculo::getPlaca).collect(Collectors.toList());
        }
        return List.of();
    }
    // public static Cliente buscarPorId(String clienteId) {
    // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method
    // 'buscarPorId'");
    // }
}