package site.SpringWeb.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import site.SpringWeb.modelos.Cliente;
//import java.util.List;


public interface ClientesRepo extends CrudRepository<Cliente, Integer> {

  @Query(value = "select CASE WHEN count(1) > 0 THEN 'true' ELSE 'false' END from clientes where id = :id", nativeQuery = true)
  public boolean exist(int id);

  Optional<Cliente> findById(int id); // Adicionado para buscar por ID

  // @Query(value="select * from clientes where email = :email and senha =
  // :senha", nativeQuery = true)
  // public Cliente login(String email, String senha);

  // @Query(value="select * from clientes where nome like %:nome% or email like
  // %:email%", nativeQuery = true)
  // public ArrayList<Cliente> findAllByNomeEmail(@Param("nome") String nome,
  // @Param("email") String email);
}
