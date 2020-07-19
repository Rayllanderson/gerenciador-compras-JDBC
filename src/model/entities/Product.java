package model.entities;

public class Product {

    private Integer id;
    private String nome;
    private Double precoEstipulado; //quanto que acho que vai ser tal produto
    private Double precoReal; //quanto que ele realmente foi
    
    public Product(String nome, Double precoEstipulado, Double precoReal) {
	this.nome = nome;
	this.precoEstipulado = precoEstipulado;
	this.precoReal = precoReal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPrecoEstipulado() {
        return precoEstipulado;
    }

    public void setPrecoEstipulado(Double precoEstipulado) {
        this.precoEstipulado = precoEstipulado;
    }

    public Double getPrecoReal() {
        return precoReal;
    }

    public void setPrecoReal(Double precoReal) {
        this.precoReal = precoReal;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Product other = (Product) obj;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Id: [" + id + "]\nNome: " + nome + "\nValor Estipulado: R$" + String.format("%.2f", precoEstipulado) + "\nValor Real: R$"
		+ String.format("%.2f", precoReal);
    }
    
    
    
    
}
