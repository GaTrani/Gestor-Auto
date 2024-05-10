package site.SpringWeb.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import site.SpringWeb.modelos.PDVVendas;
import java.util.List;

@Repository
public interface PDVVendasRepo extends CrudRepository<PDVVendas, Integer> {

    List<PDVVendas> findByPdvId(int idPdv);
}

