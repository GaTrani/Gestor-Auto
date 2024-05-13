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
