package site.SpringWeb.modelos;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import site.SpringWeb.servicos.MarcaCarroService;

@Entity
@Table(name = "marca")
public class MarcaCarro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    // Getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public static List<MarcaCarro> findByVeiculo(MarcaCarroService marcaCarroService) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByVeiculo'");
    }

    /* public static List<MarcaCarro> findByVeiculo(MarcaCarroService marcaCarroService) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByVeiculo'");
    } */
}

