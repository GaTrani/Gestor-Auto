package site.SpringWeb.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import site.SpringWeb.modelos.ContasApagar;
import site.SpringWeb.repositorio.ContasApagarRepo;

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
    public String novo() {
        return "contasapagar/novo";
    }

    @PostMapping("/contasapagar/criar")
    public String criar(ContasApagar contaApagar) {
        repo.save(contaApagar);
        return "redirect:/contasapagar";
    }

    @GetMapping("/contasapagar/{id}")
    public String busca(@PathVariable int id, Model model) {
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
}
