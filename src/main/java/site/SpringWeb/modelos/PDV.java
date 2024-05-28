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

    @Column(name = "km")
    private int km;

    @Column(name = "data_entrada")
    private Date dataEntrada;

    @Column(name = "desconto")
    private double desconto;

    @Column(name = "total")
    private double total;

    @Column(name = "forma_pagamento", length = 100, nullable = true)
    private String formaPagamento;

    @OneToMany(mappedBy = "pdv", cascade = CascadeType.ALL)
    private List<PDVVendas> vendas;

    // Getters e Setters
    // Método para adicionar uma venda

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public List<PDVVendas> getVendas() {
        return vendas;
    }

    public void setVendas(List<PDVVendas> vendas) {
        this.vendas = vendas;
    }

    // Método para adicionar uma venda
    public void adicionarVenda(PDVVendas venda) {
        this.vendas.add(venda);
        venda.setPdv(this); // Ajuste aqui para definir o PDV para a venda
    }
}
