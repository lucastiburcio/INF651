package main;

public class Solucao {
	private String caminho;
	private double custo;

	public Solucao(String caminho, double custo) {
		super();
		this.caminho = caminho;
		this.custo = custo;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public double getCusto() {
		return custo;
	}

	public void setCusto(double custo) {
		this.custo = custo;
	}

}
