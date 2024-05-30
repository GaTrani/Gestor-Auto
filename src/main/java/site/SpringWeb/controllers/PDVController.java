package site.SpringWeb.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import site.SpringWeb.modelos.Cliente;
import site.SpringWeb.modelos.PDV;
import site.SpringWeb.modelos.PDVVendas;
import site.SpringWeb.modelos.Produto;
import site.SpringWeb.repositorio.PDVRepo;
import site.SpringWeb.repositorio.PDVVendasRepo;
import site.SpringWeb.servicos.ClienteService;
import site.SpringWeb.servicos.PDVService;
import site.SpringWeb.servicos.PDVVendasService;
//import site.SpringWeb.servicos.PDVVendasService;
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

    @Autowired
    private PDVVendasService pdvVendasService;

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
        /* pdv.setKm(pdv.getKm()); */
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
    public String criarPDV(
            @RequestParam("clienteId") int clienteId,
            @RequestParam("veiculo") String veiculo,
            @RequestParam("km") int km,
            @RequestParam("formaPagamento") String formaPagamento,
            @RequestParam("descontoEmReais") double descontoEmReais,
            @RequestParam("produtosJson") String produtosJson) {

        // Converte a string JSON de produtos em uma lista de objetos Produto
        ObjectMapper objectMapper = new ObjectMapper();
        List<Produto> produtosAdicionados = new ArrayList<>();
        try {
            produtosAdicionados = objectMapper.readValue(produtosJson, new TypeReference<List<Produto>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // Lidar com a exceção apropriadamente (ex: redirecionar para uma página de
            // erro)
        }

        // Exibir a lista de produtos no console para verificação
        for (Produto produto : produtosAdicionados) {
            System.out.println("Produto ID: " + produto.getId() +
                    ", Produto: " + produto.getProduto() +
                    ", Quantidade: " + produto.getQuantidade() +
                    ", unitario: " + produto.getPrecoVenda() + ", total: "
                    + produto.getQuantidade() * produto.getPrecoVenda());
        }

        // Crie o objeto PDV e configure os detalhes (cliente, veículo, etc.)
        PDV pdv = new PDV();
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        pdv.setCliente(cliente);
        pdv.setVeiculo(veiculo);
        pdv.setKm(km);
        pdv.setFormaPagamento(formaPagamento);
        pdv.setDesconto(descontoEmReais);
        pdv.setDataEntrada(new Date());

        // Calcular o total dos produtos
        double totalProdutos = 0;
        for (Produto produto : produtosAdicionados) {
            totalProdutos += produto.getQuantidade() * produto.getPrecoVenda();
        }

        // Definir o total no objeto PDV
        pdv.setTotal(totalProdutos);

        // Salve o PDV
        pdvService.salvarPDV(pdv);

        // Agora, itere pelos produtos selecionados e crie registros na tabela
        // pdv_vendas
        for (Produto produto : produtosAdicionados) {
            PDVVendas pdvVenda = new PDVVendas();
            pdvVenda.setPdv(pdv); // Associe o PDV ao registro de venda
            pdvVenda.setIdProduto(produto.getId()); // Defina o ID do produto
            pdvVenda.setQuantidade(produto.getQuantidade());
            pdvVenda.setValorUnitario(produto.getPrecoVenda());
            pdvVenda.setTotal(produto.getQuantidade() * produto.getPrecoVenda());

            // Salve o registro de venda
            pdvVendasService.salvarPDVVendas(pdvVenda);
        }

        return "redirect:/pdv";
    }

    /*
     * @PostMapping("/adicionar-produtos-pdv-vendas")
     * public String adicionarProdutosAoPDV(Model model, @RequestParam("clienteId")
     * int clienteId) {
     * // Adicione esta linha para imprimir uma mensagem ao acessar a rota
     * System.out.
     * println("Rota /adicionar-produtos-pdv-vendas foi acessada com sucesso.");
     * 
     * // Agora você pode adicionar o restante da lógica conforme necessário
     * 
     * try {
     * // Recuperar o último PDV criado
     * Integer ultimoId = pdvRepo.obterUltimoId();
     * PDV pdv = pdvRepo.findById(ultimoId)
     * .orElseThrow(() -> new IllegalArgumentException("PDV não encontrado"));
     * 
     * // Lista fixa de produtos
     * String[] produtos = { "produto1", "produto2", "produto3" };
     * 
     * // Salvar cada produto na tabela pdv_vendas
     * for (String produto : produtos) {
     * PDVVendas venda = new PDVVendas();
     * venda.setPdv(pdv);
     * venda.setProduto(produto);
     * venda.setQuantidade(1);
     * venda.setValorUnitario(11.0);
     * venda.setTotal(11.0);
     * pdvVendasRepo.save(venda);
     * }
     * 
     * // Redirecionar para a página de PDV
     * return "redirect:/pdv";
     * } catch (Exception e) {
     * // Em caso de erro, redirecionar para a página de PDV com uma mensagem de
     * erro
     * return "redirect:/pdv?error=" + e.getMessage();
     * }
     * }
     */

    /*
     * @GetMapping("/{id}")
     * public String buscarPdv(@PathVariable int id, Model model) {
     * Optional<PDV> pdv = pdvRepo.findById(id);
     * pdv.ifPresent(value -> model.addAttribute("pdv", value));
     * return pdv.map(pdv1 -> "pdv/editar").orElse("redirect:/pdv");
     * }
     */

    @GetMapping("/{id}")
    public String buscarPdv(@PathVariable int id, Model model) {
        Optional<PDV> pdv = pdvRepo.findById(id);
        if (pdv.isPresent()) {
            model.addAttribute("pdv", pdv.get());
            List<Cliente> clientes = clienteService.buscarTodos();
            model.addAttribute("clientes", clientes);
            return "pdv/editar";
        } else {
            return "redirect:/pdv";
        }
    }

    /*
     * @PostMapping("/{id}/atualizar")
     * public String atualizar(@PathVariable int id, PDV pdv) {
     * if (!pdvRepo.existsById(id)) {
     * return "redirect:/pdv";
     * }
     * pdv.setId(id); // Certifique-se de que o ID do PDV é configurado corretamente
     * pdvRepo.save(pdv);
     * return "redirect:/pdv";
     * }
     */

    @PostMapping("/pdv/{id}/atualizar")
    public String atualizar(@PathVariable int id, @ModelAttribute PDV pdv, @RequestParam("clienteId") int clienteId,
            @RequestParam("km") int km) {
        System.out.println("entrou no atualizar");
        if (!pdvRepo.existsById(id)) {
            return "redirect:/pdv";
        }

        // Buscar o PDV existente
        Optional<PDV> pdvExistenteOptional = pdvRepo.findById(id);
        if (pdvExistenteOptional.isPresent()) {
            PDV pdvExistente = pdvExistenteOptional.get();

            // Atualizar os campos do PDV existente
            pdvExistente.setCliente(clienteService.buscarPorId(clienteId).orElse(null));
            pdvExistente.setKm(1000);

            // Salvar as alterações
            pdvRepo.save(pdvExistente);
        }

        return "redirect:/pdv";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable int id) {
        pdvRepo.deleteById(id);
        return "redirect:/pdv";
    }

    /*
     * @PostMapping("/adicionar-produto-parametros")
     * 
     * @ResponseBody
     * public String adicionarProdutoAoPDVComParametros(@RequestParam("id_pdv") int
     * idPdv,
     * 
     * @RequestParam("produto") String produto) {
     * try {
     * PDV pdv = pdvRepo.findById(idPdv)
     * .orElseThrow(() -> new IllegalArgumentException("ID do PDV inválido: " +
     * idPdv));
     * 
     * PDVVendas venda = new PDVVendas();
     * venda.setPdv(pdv);
     * venda.setProduto(produto);
     * 
     * pdvVendasRepo.save(venda);
     * return "Produto adicionado ao PDV com sucesso";
     * } catch (Exception e) {
     * return "Erro ao adicionar produto ao PDV: " + e.getMessage();
     * }
     * }
     * 
     * @PostMapping("/adicionar-produto-body")
     * 
     * @ResponseBody
     * public String adicionarProdutoAoPDVComBody(@RequestBody PDVVendas request) {
     * try {
     * PDV pdv = pdvRepo.findById(request.getPdv().getId())
     * .orElseThrow(() -> new IllegalArgumentException("PDV não encontrado"));
     * 
     * PDVVendas venda = new PDVVendas();
     * venda.setPdv(pdv);
     * venda.setProduto(request.getProduto());
     * 
     * pdvVendasRepo.save(venda);
     * return "Produto adicionado ao PDV com sucesso";
     * } catch (Exception e) {
     * return "Erro ao adicionar produto ao PDV: " + e.getMessage();
     * }
     * }
     */

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

    @GetMapping("/listar") // lista os pdvs no index
    public String listarPDVs(Model model) {
        List<PDV> pdvs = pdvService.listarPDVs();
        model.addAttribute("pdvs", pdvs);
        return "pdv/index";
    }

    @GetMapping("/cliente/{clienteId}/veiculos")
    @ResponseBody
    public List<String> obterVeiculosPorCliente(@PathVariable int clienteId) {
        return clienteService.buscarVeiculosPorClienteId(clienteId);
    }

    @GetMapping("/buscar_valor_unitario")
    @ResponseBody
    public ResponseEntity<Double> buscarValorUnitario(@RequestParam("produtoId") int produtoId) {
        // Aqui você irá realizar a lógica para buscar o valor unitário do produto no
        // banco de dados
        double valorUnitario = produtoService.buscarValorUnitarioPorId(produtoId);
        return ResponseEntity.ok(valorUnitario);
    }

}
