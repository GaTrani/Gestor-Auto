package site.SpringWeb.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import site.SpringWeb.modelos.Cliente;
import site.SpringWeb.modelos.Veiculo;

public interface VeiculosRepo extends CrudRepository<Veiculo, Long> {

    @SuppressWarnings("null")
    @Query(value = "select CASE WHEN count(1) > 0 THEN true ELSE false END from Veiculo where id = :id")
    public boolean existsById(Long id);

    List<Veiculo> findByCliente(Cliente cliente);

    /* @PostMapping("/veiculos/criar")
    default String criar(String clienteId, Long marcaId, Long modeloId, String placa, int km,
            RedirectAttributes redirectAttributes) {
        try {
            // Busca o cliente pelo ID
            // Cliente cliente = ClienteService.buscarPorId(clienteId);
            // Busca a marca pelo ID
            MarcaCarro marca = MarcaCarroService.buscarPorId(marcaId);

            // Busca os modelos de carro pela marca selecionada
            List<ModeloCarro> modelos = ModeloCarroService.buscarModelosPorMarca(marcaId);

            // Verifica se a marca e os modelos foram encontrados
            if (marca != null && modelos != null) {
                // Cria o objeto Veiculo e associa o cliente, marca e modelo
                Veiculo veiculo = new Veiculo();
                // veiculo.setCliente(cliente.getNome());
                veiculo.setMarca(marca.getNome());
                veiculo.setModelo(modeloId); // Aqui você pode definir o modelo selecionado
                veiculo.setPlaca(placa);
                veiculo.setKm(km);

                // Salva o veículo no banco de dados
                save(veiculo);

                return "redirect:/veiculos";
            } else {
                // Se a marca ou os modelos não foram encontrados, redireciona de volta para a
                // página de criação
                // de veículos
                redirectAttributes.addFlashAttribute("erro", "Marca de carro ou modelos não encontrados.");
                return "redirect:/veiculos/novo";
            }
        } catch (DataIntegrityViolationException e) {
            // Se ocorrer uma violação de restrição de integridade, significa que a placa já
            // existe
            redirectAttributes.addFlashAttribute("erro", "A placa do veículo já está em uso.");
            return "redirect:/veiculos/novo"; // Redireciona de volta para a página de criação de veículos
        }
    } */
}

