package site.SpringWeb.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import site.SpringWeb.modelos.Veiculo;
import site.SpringWeb.repositorio.VeiculosRepo;

@Service
public class VeiculoService {

    @Autowired
    private VeiculosRepo veiculosRepo;

    public Veiculo salvar(Veiculo veiculo) {
        return veiculosRepo.save(veiculo);
    }
}
