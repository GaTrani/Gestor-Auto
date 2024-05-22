package site.SpringWeb.modelos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "modelo")
public class ModeloCarro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String modelo;

    @ManyToOne
    private MarcaCarro marca;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String nome) {
        this.modelo = nome;
    }

    public MarcaCarro getMarca() {
        return marca;
    }

    public void setMarca(MarcaCarro marca) {
        this.marca = marca;
    }

    public String getNome() {
        return modelo;
    }
}
