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

}
