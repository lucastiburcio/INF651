package main;

public class Estimativa {

	private String nome;
	private double estimativa = 0;

	public Estimativa(String nome, double estimativa) {
		super();
		this.nome = nome;
		this.estimativa = estimativa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getEstimativa() {
		return estimativa;
	}

	public void setEstimativa(double estimativa) {
		this.estimativa = estimativa;
	}

}
