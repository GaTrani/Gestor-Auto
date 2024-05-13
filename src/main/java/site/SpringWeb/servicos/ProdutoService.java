package site.SpringWeb.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import site.SpringWeb.modelos.Produto;
import site.SpringWeb.modelos.Veiculo;
import site.SpringWeb.repositorio.ProdutosRepo;
import site.SpringWeb.repositorio.VeiculosRepo;

import java.util.List;

import javax.transaction.Transactional;

@Service
public class ProdutoService {

    private final ProdutosRepo produtoRepo;

    @Autowired
    private VeiculosRepo veiculosRepo;

    public ProdutoService(ProdutosRepo produtoRepo) {
        this.produtoRepo = produtoRepo;
    }

    public List<Produto> listarProdutos() {
        return (List<Produto>) produtoRepo.findAll();
    }

    /* @Transactional
    public void criarVeiculo(String clienteId, Long marcaId, Long modeloId, String placa, int km,
            RedirectAttributes redirectAttributes) {
        try { */
            // Lógica para buscar a marca e modelos de carro...

            // Cria o objeto Veiculo e associa o cliente, marca e modelo
            //Veiculo veiculo = new Veiculo();
            /*
             * veiculo.setMarca(marca.getNome());
             * veiculo.setModelo(modeloId);
             */
           /*  veiculo.setPlaca(placa);
            veiculo.setKm(km);

            // Salva o veículo no banco de dados usando o repositório injetado
            veiculosRepo.save(veiculo);

            return;
        } catch (DataIntegrityViolationException e) {
            // Tratamento de exceção...
        }
    } */

}
