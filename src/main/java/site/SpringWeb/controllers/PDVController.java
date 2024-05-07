package site.SpringWeb.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import site.SpringWeb.modelos.PDV;
import site.SpringWeb.modelos.Produto;
import site.SpringWeb.repositorio.PDVRepo;
import site.SpringWeb.servicos.ProdutoService;

@Controller
@RequestMapping("/pdv")
public class PDVController {

    @Autowired
    private PDVRepo pdvRepo;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public String index(Model model) {
        List<PDV> pdvList = (List<PDV>) pdvRepo.findAll();
        model.addAttribute("pdv", pdvList);
        return "pdv/index";
    }

    @GetMapping("/novo")
    public String exibirFormularioNovoProduto(Model model) {
        List<Produto> listaProdutos = produtoService.listarProdutos();
        model.addAttribute("produtos", listaProdutos);
        return "pdv/novo";
    }

    @PostMapping("/criar")
    public String criar(PDV pdv) {
        pdvRepo.save(pdv);
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
        pdvRepo.save(pdv);
        return "redirect:/pdv";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable int id) {
        pdvRepo.deleteById(id);
        return "redirect:/pdv";
    }

}
