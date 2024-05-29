package site.SpringWeb.dto;

public class ProdutoDTO {
    private Long id;
    private String nome;
    private Double precoVenda;
    private Integer quantidade;
    private Double valorTotal;

    // Construtor padrão
    public ProdutoDTO() {
    }

    // Construtor com parâmetros
    public ProdutoDTO(Long id, String nome, Double precoVenda, Integer quantidade, Double valorTotal) {
        this.id = id;
        this.nome = nome;
        this.precoVenda = precoVenda;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
    }

    // Getters e Setters
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

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(Double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    // Método toString
    @Override
    public String toString() {
        return "ProdutoDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", precoVenda=" + precoVenda +
                ", quantidade=" + quantidade +
                ", valorTotal=" + valorTotal +
                '}';
    }
}
