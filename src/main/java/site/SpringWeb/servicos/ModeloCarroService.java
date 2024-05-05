package site.SpringWeb.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import site.SpringWeb.modelos.ModeloCarro;
import site.SpringWeb.repositorio.ModelosRepo;

@Service
public class ModeloCarroService {

    private static ModelosRepo modelosRepo = null;

    public static List<ModeloCarro> buscarModelosPorMarca(Long marcaId) {
        // Aqui você precisa implementar a lógica para buscar os modelos de carro por
        // marca
        // Por exemplo, você pode usar o método findByMarcaId do repositório
        return modelosRepo.findByMarcaId(marcaId);
    }

    public ModeloCarroService(ModelosRepo modelosRepo) {
        ModeloCarroService.modelosRepo = modelosRepo;
    }

    public static List<ModeloCarro> buscarTodos() {
        return modelosRepo.findAll();
    }

    public List<ModeloCarro> buscarPorMarca(Long marcaId) {
        return modelosRepo.findByMarcaId(marcaId);
    }

    public static Optional<ModeloCarro> buscarPorId(Long id) {
        return modelosRepo.findById(id);
    }

    // Outros métodos do serviço podem ser adicionados conforme necessário
}
