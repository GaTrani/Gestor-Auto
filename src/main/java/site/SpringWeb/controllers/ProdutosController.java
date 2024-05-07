package site.SpringWeb.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import site.SpringWeb.modelos.Fornecedor;
import site.SpringWeb.modelos.Produto;
import site.SpringWeb.repositorio.ProdutosRepo;
import site.SpringWeb.servicos.FornecedorService;

@Controller
public class ProdutosController {

    @Autowired
    private ProdutosRepo repo;

    @GetMapping("/produtos")
    public String index(Model model) {
        List<Produto> produtos = (List<Produto>) repo.findAll();
        model.addAttribute("produtos", produtos);
        return "produtos/index";
    }


    @GetMapping("/produtos/novo")
    public String exibirFormularioNovoProduto(Model model) {
        List<Fornecedor> listaFornecedores = FornecedorService.buscarTodos();
        model.addAttribute("listaFornecedores", listaFornecedores);
        return "produtos/novo";
    }

    @PostMapping("/produtos/criar")
    public String criar(Produto produto) {
        repo.save(produto);
        return "redirect:/produtos";
    }

    @GetMapping("/produtos/{id}")
    public String busca(@PathVariable int id, Model model) {
        Optional<Produto> produto = repo.findById(id);

        // Busca a lista de fornecedores
        List<Fornecedor> listaFornecedores = FornecedorService.buscarTodos();
        model.addAttribute("listaFornecedores", listaFornecedores);

        try {
            model.addAttribute("produto", produto.get());
        } catch (Exception err) {
            return "redirect:/produtos";
        }

        return "produtos/editar";
    }

    @PostMapping("/produtos/{id}/atualizar")
    public String atualizar(@PathVariable int id, Produto produto) {
        if (!repo.existsById(id)) {
            return "redirect:/produtos";
        }

        repo.save(produto);

        return "redirect:/produtos";
    }

    @GetMapping("/produtos/{id}/excluir")
    public String excluir(@PathVariable int id) {
        repo.deleteById(id);
        return "redirect:/produtos";
    }
}
