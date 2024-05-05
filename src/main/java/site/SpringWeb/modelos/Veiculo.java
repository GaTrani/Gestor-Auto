package site.SpringWeb.modelos;

//import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;
@Component
@Entity
@Table(name = "veiculos")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cliente", length = 100)
    private String cliente;

    @Column(name = "marca", length = 50)
    private String marca;

    @Column(name = "modelo", length = 50)
    private Long modelo;

    @Column(name = "placa", length = 10, unique = true)
    private String placa;

    @Column(name = "km")
    private int km;

    // Getters
    public Long getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public String getMarca() {
        return marca;
    }

    public Long getModelo() {
        return modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public int getKm() {
        return km;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(Long modeloId) {
        this.modelo = modeloId;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public void setKm(int km) {
        this.km = km;
    }

    //public List<MarcaCarro> obterMarcasCarro() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'obterMarcasCarro'");
    //}
}

