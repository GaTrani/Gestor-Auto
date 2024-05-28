package site.SpringWeb.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import site.SpringWeb.modelos.PDVVendas;
import site.SpringWeb.repositorio.PDVVendasRepo;

@Service
public class PDVVendasService {
    @Autowired
    private PDVVendasRepo pdvVendasRepo;

    public void salvarPDVVendas(PDVVendas pdvVendas) {
        pdvVendasRepo.save(pdvVendas);
    }
}
