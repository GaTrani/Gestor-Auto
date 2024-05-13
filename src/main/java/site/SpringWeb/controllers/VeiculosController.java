package site.SpringWeb.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import site.SpringWeb.modelos.Cliente;
import site.SpringWeb.modelos.MarcaCarro;
import site.SpringWeb.modelos.ModeloCarro;
import site.SpringWeb.modelos.Veiculo;
import site.SpringWeb.repositorio.VeiculosRepo;
import site.SpringWeb.servicos.ClienteService;
import site.SpringWeb.servicos.MarcaCarroService;
import site.SpringWeb.servicos.ModeloCarroService;
import site.SpringWeb.servicos.VeiculoService;

@Controller
public class VeiculosController {

    @Autowired
    public VeiculosRepo repo;

    @Autowired
    public VeiculoService veiculoService;

    @GetMapping("/veiculos")
    public String index(Model model) {
        List<Veiculo> veiculos = (List<Veiculo>) repo.findAll();
        model.addAttribute("veiculos", veiculos);
        return "veiculos/index";
    }

    @GetMapping("/veiculos/novo")
    public String exibirFormularioNovoVeiculo(Model model) {
        List<Cliente> listaClientes = ClienteService.buscarTodos();
        List<MarcaCarro> listaMarcasCarro = MarcaCarroService.buscarTodos();
        List<ModeloCarro> listaModelosCarro = ModeloCarroService.buscarTodos();
        model.addAttribute("listaClientes", listaClientes);
        model.addAttribute("listaMarcasCarro", listaMarcasCarro);
        model.addAttribute("listaModelosCarro", listaModelosCarro);
        return "veiculos/novo";
    }

    @GetMapping("/buscarPorMarca")
    @ResponseBody
    public List<ModeloCarro> buscarModelosPorMarca(@RequestParam Long marca) {
        return ModeloCarroService.buscarModelosPorMarca(marca);
    }

    /*
     * @PostMapping("/veiculos/criar")
     * public String criar(Veiculo veiculo, RedirectAttributes redirectAttributes) {
     * try {
     * repo.save(veiculo);
     * return "redirect:/veiculos";
     * } catch (DataIntegrityViolationException e) {
     * // Se ocorrer uma violação de restrição de integridade, significa que a placa
     * já
     * // existe
     * redirectAttributes.addFlashAttribute("erro",
     * "A placa do veículo já está em uso.");
     * return "redirect:/veiculos/novo"; // Redireciona de volta para a página de
     * criação de veículos
     * }
     * }
     */

    @PostMapping("/veiculos/criar")
    public String criar(@RequestParam("cliente") String clienteId,
            @RequestParam("marca") Long marcaId,
            @RequestParam("modelo") Long modeloId,
            @RequestParam("placa") String placa,
            @RequestParam("km") int km,
            RedirectAttributes redirectAttributes) {
        try {
            // Busca o cliente pelo ID
            // Cliente cliente = ClienteService.buscarPorId(clienteId);
            // Busca a marca pelo ID
            MarcaCarro marca = MarcaCarroService.buscarPorId(marcaId);

            // Busca os modelos de carro pela marca selecionada
            List<ModeloCarro> modelos = ModeloCarroService.buscarModelosPorMarca(marcaId);

            // Verifica se a marca e os modelos foram encontrados
            if (marca != null && modelos != null) {
                // Cria o objeto Veiculo e associa o cliente, marca e modelo
                Veiculo veiculo = new Veiculo();
                // veiculo.setCliente(cliente.getNome());
                veiculo.setMarca(marca.getNome());
                veiculo.setModelo(modeloId); // Aqui você pode definir o modelo selecionado
                veiculo.setPlaca(placa);
                veiculo.setKm(km);

                // Salva o veículo no banco de dados
                repo.save(veiculo);

                return "redirect:/veiculos";
            } else {
                // Se a marca ou os modelos não foram encontrados, redireciona de volta para a
                // página de criação
                // de veículos
                redirectAttributes.addFlashAttribute("erro", "Marca de carro ou modelos não encontrados.");
                return "redirect:/veiculos/novo";
            }
        } catch (DataIntegrityViolationException e) {
            // Se ocorrer uma violação de restrição de integridade, significa que a placa já
            // existe
            redirectAttributes.addFlashAttribute("erro", "A placa do veículo já está em uso.");
            return "redirect:/veiculos/novo"; // Redireciona de volta para a página de criação de veículos
        }
    }

    @GetMapping("/veiculos/{id}")
    public String busca(@PathVariable int id, Model model) {
        Optional<Veiculo> veiculo = repo.findById((long) id);
        try {
            model.addAttribute("veiculo", veiculo.get());
        } catch (Exception err) {
            return "redirect:/veiculos";
        }

        return "veiculos/editar";
    }

    @PostMapping("/veiculos/{id}/atualizar")
    public String atualizar(@PathVariable int id, Veiculo veiculo) {
        if (!repo.existsById((long) id)) {
            return "redirect:/veiculos";
        }

        repo.save(veiculo);

        return "redirect:/veiculos";
    }

    @GetMapping("/veiculos/{id}/excluir")
    public String excluir(@PathVariable int id) {
        repo.deleteById((long) id);
        return "redirect:/veiculos";
    }

}
