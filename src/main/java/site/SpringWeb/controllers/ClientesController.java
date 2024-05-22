package site.SpringWeb.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/clientes/criar")
    public String criar(@ModelAttribute("cliente") Cliente cliente, @RequestParam("placas[]") List<String> placas,
            BindingResult result) {
        // Salvar o cliente na tabela Cliente
        Cliente clienteSalvo = repo.save(cliente);
        if (result.hasFieldErrors("numero")) {
            cliente.setNumero(null);
        }
        // Para cada placa, salvar na tabela Veiculo
        for (String placa : placas) {
            if (placa != null && !placa.trim().isEmpty()) {
                Veiculo veiculo = new Veiculo();
                veiculo.setCliente(clienteSalvo); // Definir o cliente associado ao veículo
                veiculo.setPlaca(placa); // Definir a placa do veículo
                veiculosRepo.save(veiculo); // Salvar o veículo na tabela Veiculo
            }
        }

        return "redirect:/clientes";
    }

    @GetMapping("/clientes/{id}")
    public String busca(@PathVariable int id, Model model) {
        Optional<Cliente> cliente = repo.findById(id);
        if (cliente.isPresent()) {
            model.addAttribute("cliente", cliente.get());
            List<Veiculo> veiculos = veiculosRepo.findByCliente(cliente.get());
            model.addAttribute("veiculos", veiculos);
            return "clientes/editar";
        } else {
            return "redirect:/clientes";
        }
    }

    @PostMapping("/clientes/{id}/atualizar")
    public String atualizar(@PathVariable int id, @ModelAttribute("cliente") Cliente cliente,
            @RequestParam("placas[]") List<String> placas, BindingResult result) {
        if (result.hasFieldErrors("numero")) {
            cliente.setNumero(null);
        }

        if (!repo.existsById(id)) {
            return "redirect:/clientes";
        }

        // Atualizar o cliente
        cliente.setId(id);
        Cliente clienteAtualizado = repo.save(cliente);

        // Obter os veículos antigos do cliente
        List<Veiculo> veiculosAntigos = veiculosRepo.findByCliente(clienteAtualizado);

        // Atualizar ou adicionar novos veículos
        for (String placa : placas) {
            if (placa != null && !placa.trim().isEmpty()) {
                Optional<Veiculo> veiculoOpt = veiculosAntigos.stream()
                        .filter(v -> v.getPlaca().equals(placa))
                        .findFirst();

                if (veiculoOpt.isPresent()) {
                    // Veículo já existe, removê-lo da lista de veículos antigos para não ser
                    // deletado
                    veiculosAntigos.remove(veiculoOpt.get());
                } else {
                    // Veículo não existe, criar um novo
                    Veiculo veiculo = new Veiculo();
                    veiculo.setCliente(clienteAtualizado);
                    veiculo.setPlaca(placa);
                    veiculosRepo.save(veiculo);
                }
            }
        }

        // Veículos restantes em veiculosAntigos são aqueles que foram removidos na
        // atualização
        veiculosRepo.deleteAll(veiculosAntigos);

        return "redirect:/clientes";
    }

    @GetMapping("/clientes/{id}/excluir")
    public String excluir(@PathVariable int id) {
        if (repo.existsById(id)) {
            Cliente cliente = repo.findById(id).get();
            List<Veiculo> veiculos = veiculosRepo.findByCliente(cliente);
            veiculosRepo.deleteAll(veiculos);
            repo.deleteById(id);
        }
        return "redirect:/clientes";
    }

}
