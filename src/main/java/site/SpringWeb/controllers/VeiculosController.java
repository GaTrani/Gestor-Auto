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
    public VeiculosRepo veiculosRepo;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    public VeiculoService veiculoService;

    @Autowired
    private MarcaCarroService marcaCarroService;

    @Autowired
    private ModeloCarroService modeloCarroService;

    @GetMapping("/veiculos")
    public String index(Model model) {
        List<Veiculo> veiculos = (List<Veiculo>) veiculosRepo.findAll();
        model.addAttribute("veiculos", veiculos);
        return "veiculos/index";
    }

    @GetMapping("/veiculos/novo")
    public String exibirFormularioNovoVeiculo(Model model) {
        List<Cliente> listaClientes = clienteService.buscarTodos();
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

    @PostMapping("/veiculos/criar")
    public String criar(@RequestParam("cliente") int clienteId,
            @RequestParam("marca") Long marcaId,
            @RequestParam("modelo") Long modeloId,
            @RequestParam("placa") String placa,
            @RequestParam("ano") int ano,
            @RequestParam("km") int km,
            RedirectAttributes redirectAttributes) {
        try {
            // Busca o cliente pelo ID
            Optional<Cliente> clienteOpt = clienteService.buscarPorId(clienteId);
            if (!clienteOpt.isPresent()) {
                redirectAttributes.addFlashAttribute("erro", "Cliente não encontrado.");
                return "redirect:/veiculos/novo";
            }
            Cliente cliente = clienteOpt.get();

            // Busca a marca pelo ID
            Optional<MarcaCarro> marcaOpt = marcaCarroService.buscarPorId(marcaId);
            if (!marcaOpt.isPresent()) {
                redirectAttributes.addFlashAttribute("erro", "Marca não encontrada.");
                return "redirect:/veiculos/novo";
            }
            MarcaCarro marca = marcaOpt.get();

            // Busca o modelo pelo ID
            Optional<ModeloCarro> modeloOpt = modeloCarroService.buscarPorId(modeloId);
            if (!modeloOpt.isPresent()) {
                redirectAttributes.addFlashAttribute("erro", "Modelo não encontrado.");
                return "redirect:/veiculos/novo";
            }
            ModeloCarro modelo = modeloOpt.get();

            // Cria o objeto Veiculo e associa o cliente, marca e modelo
            Veiculo veiculo = new Veiculo();
            veiculo.setCliente(cliente);
            veiculo.setMarca(marca.getNome());
            veiculo.setModelo(modelo.getNome());
            veiculo.setPlaca(placa);
            veiculo.setAno(ano);
            veiculo.setKm(km);

            veiculosRepo.save(veiculo);

            return "redirect:/veiculos";
        } catch (DataIntegrityViolationException e) {
            // Se ocorrer uma violação de restrição de integridade, significa que a placa já
            // existe
            redirectAttributes.addFlashAttribute("erro", "A placa do veículo já está em uso.");
            return "redirect:/veiculos/novo";
        }
    }

    @GetMapping("/veiculos/{id}")
    public String busca(@PathVariable int id, Model model) {
        List<Cliente> listaClientes = clienteService.buscarTodos();
        List<MarcaCarro> listaMarcasCarro = MarcaCarroService.buscarTodos();
        List<ModeloCarro> listaModelosCarro = ModeloCarroService.buscarTodos();
        model.addAttribute("listaClientes", listaClientes);
        model.addAttribute("listaMarcasCarro", listaMarcasCarro);
        model.addAttribute("listaModelosCarro", listaModelosCarro);
        Optional<Veiculo> veiculo = veiculosRepo.findById((long) id);
        try {
            model.addAttribute("veiculo", veiculo.get());
        } catch (Exception err) {
            return "redirect:/veiculos";
        }

        return "veiculos/editar";
    }

    @PostMapping("/veiculos/{id}/atualizar")
    public String atualizar(@PathVariable int id, Veiculo veiculo) {
        if (!veiculosRepo.existsById((long) id)) {
            return "redirect:/veiculos";
        }

        veiculosRepo.save(veiculo);

        return "redirect:/veiculos";
    }

    /*
     * @GetMapping("/veiculos/{id}/excluir")
     * public String excluir(@PathVariable int id) {
     * veiculosRepo.deleteById((long) id);
     * return "redirect:/veiculos";
     * }
     */

    @PostMapping("/veiculos/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Veiculo> veiculoOpt = veiculosRepo.findById(id);
        if (veiculoOpt.isPresent()) {
            veiculosRepo.deleteById(id);
            redirectAttributes.addFlashAttribute("mensagem", "Veículo excluído com sucesso.");
        } else {
            redirectAttributes.addFlashAttribute("erro", "Veículo não encontrado.");
        }
        return "redirect:/veiculos";
    }

}
