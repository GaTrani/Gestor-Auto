package site.SpringWeb.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import site.SpringWeb.modelos.Cliente;
import site.SpringWeb.modelos.Veiculo;
import site.SpringWeb.repositorio.ClientesRepo;
import site.SpringWeb.repositorio.VeiculosRepo;

@Controller
public class ClientesController {

    @Autowired
    private ClientesRepo repo;

    @Autowired
    private VeiculosRepo veiculosRepo;

    @Autowired
    private ClientesRepo clientesRepo;

    @GetMapping("/clientes")
    public String index(Model model) {
        List<Cliente> clientes = (List<Cliente>) repo.findAll();
        model.addAttribute("clientes", clientes);
        return "clientes/index";
    }

    @GetMapping("/clientes/novo")
    public String novo() {
        return "clientes/novo";
    }

    /*
     * @PostMapping("/clientes/criar")
     * public String criarCliente(Cliente cliente, RedirectAttributes
     * redirectAttributes) {
     * try {
     * // Salva o cliente no banco de dados
     * Cliente novoCliente = clientesRepo.save(cliente);
     * 
     * // Verifica se há veículos associados ao cliente
     * List<Veiculo> veiculos = cliente.getVeiculos();
     * if (veiculos != null && !veiculos.isEmpty()) {
     * // Para cada veículo associado ao cliente, associa o cliente e salva no banco
     * de
     * // dados
     * for (Veiculo veiculo : veiculos) {
     * // Associa o cliente ao veículo
     * veiculo.setCliente(novoCliente);
     * // Salva o veículo no banco de dados
     * veiculosRepo.save(veiculo);
     * }
     * }
     * 
     * return "redirect:/clientes";
     * } catch (Exception e) {
     * // Se ocorrer algum erro, redireciona de volta para a página de criação de
     * // clientes
     * redirectAttributes.addFlashAttribute("erro", "Erro ao criar cliente: " +
     * e.getMessage());
     * return "redirect:/clientes/novo";
     * }
     * }
     */

    @PostMapping("/clientes/criar")
    public String criar(@ModelAttribute("cliente") Cliente cliente, @RequestParam("placas[]") List<String> placas) {
        // Salvar o cliente na tabela Cliente
        Cliente clienteSalvo = repo.save(cliente);

        // Para cada placa, salvar na tabela Veiculo
        for (String placa : placas) {
            Veiculo veiculo = new Veiculo();
            veiculo.setCliente(clienteSalvo); // Definir o cliente associado ao veículo
            veiculo.setPlaca(placa); // Definir a placa do veículo
            veiculosRepo.save(veiculo); // Salvar o veículo na tabela Veiculo
        }

        return "redirect:/clientes";
    }

    /*
     * @PostMapping("/clientes/criar")
     * public String criar(Cliente cliente) {
     * // Salva o cliente no banco de dados
     * Cliente novoCliente = repo.save(cliente);
     *
     * // Verifica se há veículos associados ao cliente
     * List<Veiculo> veiculos = cliente.getVeiculos();
     * if (veiculos != null && !veiculos.isEmpty()) {
     * // Para cada veículo associado ao cliente, associa o cliente e salva no banco
     * de
     * // dados
     * for (Veiculo veiculo : veiculos) {
     * veiculo.setCliente(novoCliente);
     * veiculosRepo.save(veiculo);
     * }
     * }
     *
     * return "redirect:/clientes";
     * }
     */

    @GetMapping("/clientes/{id}")
    public String busca(@PathVariable int id, Model model) {
        Optional<Cliente> cliente = repo.findById(id);
        try {
            model.addAttribute("cliente", cliente.get());
        } catch (Exception err) {
            return "redirect:/clientes";
        }

        return "clientes/editar";
    }

    @PostMapping("/clientes/{id}/atualizar")
    public String atualizar(@PathVariable int id, Cliente cliente) {
        if (!repo.existsById(id)) {
            return "redirect:/clientes";
        }

        repo.save(cliente);

        return "redirect:/clientes";
    }

    @GetMapping("/clientes/{id}/excluir")
    public String excluir(@PathVariable int id) {
        repo.deleteById(id);
        return "redirect:/clientes";
    }

}
