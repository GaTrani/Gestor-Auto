package site.SpringWeb.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;

import site.SpringWeb.modelos.ModeloCarro;

public interface ModelosRepo extends JpaRepository<ModeloCarro, Long> {

  @SuppressWarnings("null")
  @Query(value = "select CASE WHEN count(1) > 0 THEN true ELSE false END from ModeloCarro where id = :id")
  public boolean existsById(Long id);

  // joao
  // @Query(value = "SELECT m FROM modelo m WHERE m.marca =: marca")
  // public List<ModeloCarro> carModels(String marca);

  /* @Query(value = "SELECT m FROM ModeloCarro m WHERE m.marca.id = :marcaId")
  public List<ModeloCarro> findByMarcaId(Long marcaId); */

  @Query("SELECT m FROM ModeloCarro m WHERE m.marca.id = :marcaId")
    List<ModeloCarro> findByMarcaId(Long marcaId);

  @Query("SELECT m FROM ModeloCarro m WHERE m.marca.id = :marcaId")
  public List<ModeloCarro> findByMarca(String marca);

  @Query("SELECT m FROM ModeloCarro m WHERE m.marca.id = :marcaId")
  public List<ModeloCarro> findById(String marca);

  @SuppressWarnings("null")
  @Query(value = "SELECT m FROM ModeloCarro m WHERE m.id = :id")
  Optional<ModeloCarro> findById(Long id);; // Corrigido para esperar um Long como parâmetro
}
