package site.SpringWeb.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import site.SpringWeb.modelos.PDVVendas;
import site.SpringWeb.repositorio.PDVVendasRepo;
import java.util.List;
import java.util.ArrayList;

@Service
public class PDVVendasService {
    @Autowired
    private PDVVendasRepo pdvVendasRepo;

    public void salvarPDVVendas(PDVVendas pdvVendas) {
        pdvVendasRepo.save(pdvVendas);
    }

    public List<PDVVendas> listarVendasPorPdv(int pdvId) {
        List<PDVVendas> vendas = pdvVendasRepo.findByPdvId(pdvId);
        return vendas != null ? vendas : new ArrayList<>();
    }

    public void removerVenda(int vendaId) {
        pdvVendasRepo.deleteById(vendaId);
    }

    public void removerPDVVendasPorPdvId(int idPdv) {
        List<PDVVendas> vendas = pdvVendasRepo.findByPdvId(idPdv);
        pdvVendasRepo.deleteAll(vendas);
    }
}
