package site.SpringWeb.controllers;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import site.SpringWeb.modelos.ContasApagar;
import site.SpringWeb.modelos.Fornecedor;
import site.SpringWeb.repositorio.ContasApagarRepo;
import site.SpringWeb.servicos.FornecedorService;

@Controller
public class ContasApagarController {

    @Autowired
    private ContasApagarRepo repo;

    @GetMapping("/contasapagar")
    public String index(Model model) {
        List<ContasApagar> contasApagar = (List<ContasApagar>) repo.findAll();
        model.addAttribute("contasApagar", contasApagar);
        return "contasapagar/index";
    }

    @GetMapping("/contasapagar/novo")
    public String novo(Model model) {
        List<Fornecedor> listaFornecedores = FornecedorService.buscarTodos();
        model.addAttribute("listaFornecedores", listaFornecedores);
        return "contasapagar/novo";
    }

    /*
     * @PostMapping("/contasapagar/criar")
     * public String criar(@RequestParam("dataVencimento") @DateTimeFormat(pattern =
     * "dd/MM/yyyy") LocalDate dataVencimento, ContasApagar contasApagar) {
     * // Formata a data para o formato 'yyyy-MM-dd' antes de salvar no banco
     * DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
     * String dataFormatada = dataVencimento.format(formatter);
     * contasApagar.setDataVencimento(LocalDate.parse(dataFormatada));
     * repo.save(contasApagar);
     * return "redirect:/contasapagar";
     * }
     */

    @PostMapping("/contasapagar/criar")
    public String criar(
            @RequestParam("dataVencimento") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataVencimento,
            ContasApagar contasApagar) {
        contasApagar.setDataVencimento(dataVencimento);
        repo.save(contasApagar);
        return "redirect:/contasapagar";
    }

    @GetMapping("/contasapagar/{id}")
    public String busca(@PathVariable int id, Model model) {

        // Busca a lista de fornecedores
        List<Fornecedor> listaFornecedores = FornecedorService.buscarTodos();
        model.addAttribute("listaFornecedores", listaFornecedores);

        Optional<ContasApagar> contaApagar = repo.findById(id);
        try {
            model.addAttribute("contaApagar", contaApagar.get());
        } catch (Exception err) {
            return "redirect:/contasapagar";
        }

        return "contasapagar/editar";
    }

    @PostMapping("/contasapagar/{id}/atualizar")
    public String atualizar(@PathVariable int id, ContasApagar contaApagar) {
        if (!repo.existsById(id)) {
            return "redirect:/contasapagar";
        }

        repo.save(contaApagar);

        return "redirect:/contasapagar";
    }

    @GetMapping("/contasapagar/{id}/excluir")
    public String excluir(@PathVariable int id) {
        repo.deleteById(id);
        return "redirect:/contasapagar";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
        });
    }
}
