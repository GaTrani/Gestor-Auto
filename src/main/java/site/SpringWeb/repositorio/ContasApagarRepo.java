package site.SpringWeb.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import site.SpringWeb.modelos.ContasApagar;

@Repository
public interface ContasApagarRepo extends JpaRepository<ContasApagar, Integer> {

    @Query(value = "SELECT CASE WHEN count(1) > 0 THEN 'true' ELSE 'false' END FROM ContasApagar WHERE id = :id", nativeQuery = true)
    boolean existsById(@Param("id") int id);

    // Outros métodos de consulta podem ser adicionados conforme necessário
}
