package site.SpringWeb.servicos;

import java.util.List;
import java.util.Optional;

//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import site.SpringWeb.repositorio.MarcasRepo;
//import site.SpringWeb.modelos.Cliente;
import site.SpringWeb.modelos.MarcaCarro;

@Service
public class MarcaCarroService {

    private static MarcasRepo marcasRepo;

    public MarcaCarroService(MarcasRepo marcasRepo) {
        MarcaCarroService.marcasRepo = marcasRepo;
    }

    public static List<MarcaCarro> buscarTodos() {
        return (List<MarcaCarro>) marcasRepo.findAll();
    }

    public List<MarcaCarro> obterMarcasCarro() {
        return MarcaCarro.findByVeiculo(this);
    }

    /* public static MarcaCarro buscarPorId(Long id) {
        Optional<MarcaCarro> marcaOptional = marcasRepo.findById(id);
        return marcaOptional.orElse(null);
    } */

    public static MarcaCarro buscarPorId2(Long marcaId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorId'");
    }

    public static MarcaCarro buscarPorId3(Long marcaId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorId'");
    }

    public Optional<MarcaCarro> buscarPorId(Long id) {
        return marcasRepo.findById(id);
    }

}