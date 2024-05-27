package site.SpringWeb.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import site.SpringWeb.modelos.Cliente;
import site.SpringWeb.modelos.PDV;
import site.SpringWeb.modelos.PDVVendas;
import site.SpringWeb.modelos.Produto;
import site.SpringWeb.repositorio.PDVRepo;
import site.SpringWeb.repositorio.PDVVendasRepo;
import site.SpringWeb.servicos.ClienteService;
import site.SpringWeb.servicos.PDVService;
import site.SpringWeb.servicos.ProdutoService;

@Controller
@RequestMapping("/pdv")
public class PDVController {

    private final PDVRepo pdvRepo;
    private final PDVVendasRepo pdvVendasRepo;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PDVService pdvService;

    public PDVController(PDVRepo pdvRepo, PDVVendasRepo pdvVendasRepo) {
        this.pdvRepo = pdvRepo;
        this.pdvVendasRepo = pdvVendasRepo;
    }

    @GetMapping
    public String index(Model model) {
        List<PDV> pdvs = pdvService.listarPDVs();
        model.addAttribute("pdvs", pdvs);
        return "pdv/index";
    }

    @GetMapping("/novo")
    public String exibirFormularioNovoPDV(Model model) {
        List<Produto> listaProdutos = produtoService.listarProdutos();
        model.addAttribute("produtos", listaProdutos);
        List<Cliente> clientes = clienteService.buscarTodos();
        model.addAttribute("clientes", clientes);
        return "pdv/novo";
    }

    @GetMapping("/novo/{id}")
    public String exibirFormularioNovoPDV(@PathVariable int id, Model model) {
        List<Produto> listaProdutos = produtoService.listarProdutos();
        model.addAttribute("produtos", listaProdutos);
        model.addAttribute("pdvId", id);
        return "pdv/novo";
    }

    @PostMapping("/salvar/{id}")
    public String salvarPDV(@PathVariable int id, PDV pdv) {
        pdv.setId(id); // Certifique-se de que o ID do PDV é configurado corretamente
        pdvRepo.save(pdv);
        return "redirect:/pdv";
    }

    @GetMapping("/produtos")
    @ResponseBody
    public List<Produto> exibirListaProduto() {
        return produtoService.listarProdutos();
    }

    /*
     * @PostMapping("/criar")
     * public String criar(@RequestParam("clienteId") int clienteId, Model model) {
     * Cliente cliente = clienteService.buscarPorId2(clienteId).orElseThrow(() ->
     * new IllegalArgumentException("Cliente não encontrado"));
     * PDV pdv = new PDV();
     * pdv.setCliente(cliente);
     * pdvRepo.save(pdv);
     * model.addAttribute("pdvId", pdv.getId());
     * return "redirect:/pdv";
     * }
     */

    @PostMapping("/criar")
    public String criarPDV(@RequestParam("clienteId") int clienteId) {
        PDV pdv = new PDV();

        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        pdv.setCliente(cliente);

        // Aqui você pode adicionar outras configurações do PDV, como veículo, data de
        // entrada, total, etc.

        pdvService.salvarPDV(pdv);

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
        pdv.setId(id); // Certifique-se de que o ID do PDV é configurado corretamente
        pdvRepo.save(pdv);
        return "redirect:/pdv";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable int id) {
        pdvRepo.deleteById(id);
        return "redirect:/pdv";
    }

    @PostMapping("/adicionar-produto-parametros")
    @ResponseBody
    public String adicionarProdutoAoPDVComParametros(@RequestParam("id_pdv") int idPdv,
            @RequestParam("produto") String produto) {
        try {
            PDV pdv = pdvRepo.findById(idPdv)
                    .orElseThrow(() -> new IllegalArgumentException("ID do PDV inválido: " + idPdv));

            PDVVendas venda = new PDVVendas();
            venda.setPdv(pdv);
            venda.setProduto(produto);

            pdvVendasRepo.save(venda);
            return "Produto adicionado ao PDV com sucesso";
        } catch (Exception e) {
            return "Erro ao adicionar produto ao PDV: " + e.getMessage();
        }
    }

    @PostMapping("/adicionar-produto-body")
    @ResponseBody
    public String adicionarProdutoAoPDVComBody(@RequestBody PDVVendas request) {
        try {
            PDV pdv = pdvRepo.findById(request.getPdv().getId())
                    .orElseThrow(() -> new IllegalArgumentException("PDV não encontrado"));

            PDVVendas venda = new PDVVendas();
            venda.setPdv(pdv);
            venda.setProduto(request.getProduto());

            pdvVendasRepo.save(venda);
            return "Produto adicionado ao PDV com sucesso";
        } catch (Exception e) {
            return "Erro ao adicionar produto ao PDV: " + e.getMessage();
        }
    }

    @GetMapping("/ultimo-id")
    @ResponseBody
    public Integer obterUltimoId() {
        return pdvRepo.obterUltimoId();
    }

    @GetMapping("/pdv/{id}/produtos")
    @ResponseBody
    public List<PDVVendas> listarProdutosDoPDV(@PathVariable("id") int idPdv) {
        return pdvVendasRepo.findByPdvId(idPdv);
    }

    @GetMapping("/listar")
    public String listarPDVs(Model model) {
        List<PDV> pdvs = pdvService.listarPDVs();
        model.addAttribute("pdvs", pdvs);
        return "pdv/index"; // Nome do seu arquivo HTML
    }
}
