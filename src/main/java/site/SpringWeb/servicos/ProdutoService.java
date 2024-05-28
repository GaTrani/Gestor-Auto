package site.SpringWeb.servicos;

import org.springframework.stereotype.Service;
import site.SpringWeb.modelos.Produto;
import site.SpringWeb.repositorio.ProdutosRepo;
import java.util.List;


@Service
public class ProdutoService {

    private final ProdutosRepo produtoRepo;

    public ProdutoService(ProdutosRepo produtoRepo) {
        this.produtoRepo = produtoRepo;
    }

    public List<Produto> listarProdutos() {
        return (List<Produto>) produtoRepo.findAll();
    }

    public double buscarValorUnitarioPorId(int idProduto) {
        // Aqui você deve implementar a lógica para buscar o valor unitário do produto pelo ID
        // Por exemplo, se você tiver um repositório ProdutoRepo que possua um método para buscar o produto pelo ID, poderia fazer algo assim:
        Produto produto = produtoRepo.findById(idProduto).orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + idProduto));
        return produto.getPrecoVenda();
    }
}
