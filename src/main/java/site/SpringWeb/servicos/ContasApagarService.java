package site.SpringWeb.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import site.SpringWeb.modelos.ContasApagar;
import site.SpringWeb.repositorio.ContasApagarRepo;

@Service
public class ContasApagarService {

    private final ContasApagarRepo contasApagarRepo;

    public ContasApagarService(ContasApagarRepo contasApagarRepo) {
        this.contasApagarRepo = contasApagarRepo;
    }

    // Métodos para operações relacionadas às contas a pagar

    public boolean existeContaPorId(int id) {
        return contasApagarRepo.existsById(id);
    }

    public List<ContasApagar> buscarTodos() {
        return contasApagarRepo.findAll();
    }

    public Optional<ContasApagar> buscarPorId(int id) {
        return contasApagarRepo.findById(id);
    }

    // Outros métodos conforme necessário
}

