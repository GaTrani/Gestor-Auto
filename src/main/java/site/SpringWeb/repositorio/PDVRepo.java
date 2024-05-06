package site.SpringWeb.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import site.SpringWeb.modelos.PDV;


public interface PDVRepo extends CrudRepository<PDV, Integer> {

    @Query(value = "select CASE WHEN count(1) > 0 THEN 'true' ELSE 'false' END from pdv where id = :id", nativeQuery = true)
    public boolean exist(int id);

    Optional<PDV> findById(int id); // Adicionado para buscar por ID
}
