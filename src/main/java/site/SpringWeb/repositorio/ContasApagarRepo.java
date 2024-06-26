package site.SpringWeb.repositorio;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import site.SpringWeb.modelos.ContasApagar;

@Repository
public interface ContasApagarRepo extends PagingAndSortingRepository<ContasApagar, Integer> {

    @Query(value = "SELECT CASE WHEN count(1) > 0 THEN 'true' ELSE 'false' END FROM ContasApagar WHERE id = :id", nativeQuery = true)
    boolean existsById(@Param("id") int id);

    List<ContasApagar> findByDataVencimento(LocalDate data_vencimento);

    @Query("SELECT c FROM ContasApagar c WHERE c.valorPago IS NOT NULL")
    Page<ContasApagar> findByValorPagoIsNotNull(Pageable pageable);
}
