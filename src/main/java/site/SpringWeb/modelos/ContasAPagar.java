package site.SpringWeb.modelos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "contas_a_pagar")
public class ContasApagar {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "fornecedor", length = 255, nullable = false)
  private String fornecedor;

  @Column(name = "cnpj", length = 14, nullable = false)
  private String cnpj;

  @Column(name = "valor", precision = 10, scale = 2, nullable = false)
  private BigDecimal valor;

  @Column(name = "recorrencia", length = 50)
  private String recorrencia;

  @Column(name = "parcelas")
  private Integer parcelas;

  /* @Temporal(TemporalType.DATE) */
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  @Column(name = "data_vencimento")
  private LocalDate dataVencimento;

  /* @DateTimeFormat(pattern = "dd-MM-yyyy")
  @Column(name = "data_vencimento", nullable = false)
  public LocalDate dataVencimento; */

  // @Column(name = "string_vencimento", length = 10)
  // private String stringVencimento;

  @Column(name = "valor_pago", precision = 10, scale = 2)
  private BigDecimal valorPago;

  /* @Column(name = "data_pagamento")
  private LocalDate dataPagamento; */

  // Getters e Setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFornecedor() {
    return fornecedor;
  }

  public void setFornecedor(String fornecedor) {
    this.fornecedor = fornecedor;
  }

  public String getCnpj() {
    return cnpj;
  }

  public void setCnpj(String cnpj) {
    this.cnpj = cnpj;
  }

  public BigDecimal getValor() {
    return valor;
  }

  public void setValor(BigDecimal valor) {
    this.valor = valor;
  }

  public String getRecorrencia() {
    return recorrencia;
  }

  public void setRecorrencia(String recorrencia) {
    this.recorrencia = recorrencia;
  }

  public Integer getParcelas() {
    return parcelas;
  }

  public void setParcelas(Integer parcelas) {
    this.parcelas = parcelas;
  }

  public LocalDate getDataVencimento() {
    return dataVencimento;
  }

  public void setDataVencimento(LocalDate dataVencimento) {
    this.dataVencimento = dataVencimento;
  }

  /*
   * public String getStringVencimento() {
   * return stringVencimento;
   * }
   * 
   * public void setStringVencimento(String stringVencimento) {
   * this.stringVencimento = stringVencimento;
   * }
   */
  public BigDecimal getValorPago() {
    return valorPago;
  }

  public void setValorPago(BigDecimal valorPago) {
    this.valorPago = valorPago;
  }

  /* public LocalDate getDataPagamento() {
    return dataPagamento;
  }

  public void setDataPagamento(LocalDate dataPagamento) {
    this.dataPagamento = dataPagamento;
  } */
}
