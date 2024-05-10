package site.SpringWeb.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import site.SpringWeb.modelos.PDVVendas;

@Repository
public interface PDVVendasRepo extends CrudRepository<PDVVendas, Integer> {

}

