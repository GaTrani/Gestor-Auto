package site.SpringWeb.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import site.SpringWeb.modelos.Cliente;
import site.SpringWeb.modelos.PDV;
import site.SpringWeb.modelos.PDVVendas;
import site.SpringWeb.modelos.Produto;
import site.SpringWeb.repositorio.PDVRepo;
import site.SpringWeb.repositorio.PDVVendasRepo;
import site.SpringWeb.servicos.ClienteService;
import site.SpringWeb.servicos.ProdutoService;

@Controller
@RequestMapping("/pdv")
public class PDVController {

    private final PDVRepo pdvRepo;
    private final PDVVendasRepo pdvVendasRepo;

    public PDVController(PDVRepo pdvRepo, PDVVendasRepo pdvVendasRepo) {
        this.pdvRepo = pdvRepo;
        this.pdvVendasRepo = pdvVendasRepo;
    }

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public String index(Model model) {
        List<PDV> pdvList = (List<PDV>) pdvRepo.findAll();
        model.addAttribute("pdv", pdvList);
        return "pdv/index";
    }

    @GetMapping("/novo")
    public String exibirFormularioNovoProduto(Model model) {
    List<Produto> listaProdutos = produtoService.listarProdutos();
    model.addAttribute("produtos", listaProdutos);
    List<Cliente> clientes = ClienteService.buscarTodos();
    model.addAttribute("clientes", clientes);
    return "pdv/novo";
    }

    @GetMapping("/novo/{id}")
    public String exibirFormularioNovoPDV(@PathVariable int id, Model model) {
        List<Produto> listaProdutos = produtoService.listarProdutos();
        model.addAttribute("produtos", listaProdutos);
        model.addAttribute("pdvId", id); // Passando o ID do PDV para a view
        return "pdv/novo";
    }

    @PostMapping("/salvar/{id}")
    public String salvarPDV(PDV pdv) {
        pdvRepo.save(pdv);
        return "redirect:/pdv"; // Redirecionar para a página inicial dos PDVs
    }

    @GetMapping("/produtos")
    @ResponseBody
    public List<Produto> exibirListaProduto(Model model) {
        List<Produto> listaProdutos = produtoService.listarProdutos();
        return listaProdutos;
    }

    @PostMapping("/criar")
    public String criar(PDV pdv) {
        pdvRepo.save(pdv);
        return "redirect:/pdv";
    }

    @GetMapping("/{id}")
    public String buscarPdv(@PathVariable int id, Model model) {
        Optional<PDV> pdv = pdvRepo.findById(id);
        pdv.ifPresent(value -> model.addAttribute("pdv", value));
        return pdv.map(pdv1 -> "pdv/editar").orElse("redirect:/pdv");
    }

    @PostMapping("/{id}/atualizar")
    public String atualizar(@PathVariable int id, PDV pdv) {
        if (!pdvRepo.existsById(id)) {
            return "redirect:/pdv";
        }
        pdvRepo.save(pdv);
        return "redirect:/pdv";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable int id) {
        pdvRepo.deleteById(id);
        return "redirect:/pdv";
    }

    // Controlador
    @PostMapping("/adicionar-produto-parametros")
    @ResponseBody
    public String adicionarProdutoAoPDVComParametros(@RequestParam("id_pdv") int idPdv,
            @RequestParam("produto") String produto) {
        try {
            // Recupere o PDV com base no ID fornecido
            PDV pdv = pdvRepo.findById(idPdv)
                    .orElseThrow(() -> new IllegalArgumentException("ID do PDV inválido: " + idPdv));

            // Criar uma nova instância de PDVVendas
            PDVVendas venda = new PDVVendas();
            // Definir os atributos do objeto PDVVendas
            venda.setPdv(pdv);
            venda.setProduto(produto);
            // Salvar o objeto no banco de dados
            pdvVendasRepo.save(venda);
            return "Produto adicionado ao PDV com sucesso";
        } catch (Exception e) {
            return "Erro ao adicionar produto ao PDV: " + e.getMessage();
        }
    }

    @PostMapping("/adicionar-produto-body")
    public String adicionarProdutoAoPDVComBody(@RequestBody PDVVendas request) {
        // Verificar se o PDV existe
        if (!pdvRepo.existsById(request.getId())) {
            return "PDV não encontrado";
        }

        // Criar uma instância de PDVVendas e preencher com os dados recebidos
        PDVVendas venda = new PDVVendas();
        venda.setPdv(pdvRepo.findById(request.getId()).orElse(null)); // Buscar o PDV pelo ID
        venda.setProduto(request.getProduto());
        // Preencher outras informações, como quantidade, valor unitário, total, etc.

        // Salvar a venda na tabela pdv_vendas
        pdvVendasRepo.save(venda);

        return "Produto adicionado ao PDV com sucessossss";
    }

    @GetMapping("/ultimo-id")
    @ResponseBody
    public Integer obterUltimoId() {
        Integer ultimoId = pdvRepo.obterUltimoId(); // Supondo que você tenha um repositório para a tabela
                                                    // pdv_vendas
        return ultimoId;
    }

    @GetMapping("/pdv/{id}/produtos")
    @ResponseBody
    public List<PDVVendas> listarProdutosDoPDV(@PathVariable("id") int idPdv) {
        // Recuperar os produtos do PDV com base no ID do PDV
        List<PDVVendas> produtos = pdvVendasRepo.findByPdvId(idPdv);
        return produtos;
    }
}
