package site.SpringWeb.servicos;

import java.util.List;

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

    public void salvarPDV(PDV pdv) {
        pdvRepository.save(pdv);
    }

    public List<PDV> listarPDVs() {
        return (List<PDV>) pdvRepository.findAll();
    }

    public PDV findById(Long id) {
        return pdvRepository.findById(id).orElse(null);
    }

    public void save(PDV pdv) {
        pdvRepository.save(pdv);
    }
}
