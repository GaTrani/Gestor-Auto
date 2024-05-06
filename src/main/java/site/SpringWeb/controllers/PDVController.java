package site.SpringWeb.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import site.SpringWeb.modelos.PDV;
import site.SpringWeb.repositorio.PDVRepo;

@Controller
public class PDVController {

    @Autowired
    private PDVRepo pdvRepo;

    @GetMapping("/pdv")
    public String index(Model model) {
        List<PDV> pdv = (List<PDV>) pdvRepo.findAll();
        model.addAttribute("pdv", pdv);
        return "pdv/index";
    }

    @GetMapping("/pdv/novo")
    public String novo() {
        return "pdv/novo";
    }

    @PostMapping("/pdv/criar")
    public String criar(PDV pdv) {
        pdvRepo.save(pdv);
        return "redirect:/pdv";
    }

    @GetMapping("/pdv/{id}")
    public String busca(@PathVariable int id, Model model) {
        Optional<PDV> pdv = pdvRepo.findById(id);
        try {
            model.addAttribute("pdv", pdv.get());
        } catch (Exception err) {
            return "redirect:/pdv";
        }

        return "pdv/editar";
    }

    @PostMapping("/pdv/{id}/atualizar")
    public String atualizar(@PathVariable int id, PDV pdv) {
        if (!pdvRepo.existsById(id)) {
            return "redirect:/pdv";
        }

        pdvRepo.save(pdv);

        return "redirect:/pdv";
    }

    @GetMapping("/pdv/{id}/excluir")
    public String excluir(@PathVariable int id) {
        pdvRepo.deleteById(id);
        return "redirect:/pdv";
    }

}
