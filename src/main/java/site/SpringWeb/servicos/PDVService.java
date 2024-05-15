package site.SpringWeb.servicos;

import org.springframework.stereotype.Service;

import site.SpringWeb.modelos.PDV;
import site.SpringWeb.repositorio.PDVRepo;

@Service
public class PDVService {

    private final PDVRepo pdvRepository;

    public PDVService(PDVRepo pdvRepository) {
        this.pdvRepository = pdvRepository;
    }

    public PDV salvar(PDV pdv) {
        return pdvRepository.save(pdv);
    }
}
