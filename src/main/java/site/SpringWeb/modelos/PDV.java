package site.SpringWeb.modelos;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pdv")
public class PDV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(name = "veiculo", length = 100, nullable = true)
    private String veiculo;

    @Column(name = "data_entrada")
    private Date dataEntrada;

    @Column(name = "total")
    private double total;

    @ManyToMany
    @JoinTable(name = "pdv_produto",
            joinColumns = @JoinColumn(name = "pdv_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id"))
    private List<Produto> produtos;

    // Getters e Setters
}
