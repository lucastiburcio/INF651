package main;

public class Requisicao {

	private String nome;
	private double tamanhoDados, atraso;

	public Requisicao(String nome, double tamanhoDados, double atraso) {
		super();
		this.nome = nome;
		this.tamanhoDados = tamanhoDados;
		this.atraso = atraso;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getTamanhoDados() {
		return tamanhoDados;
	}

	public void setTamanhoDados(double tamanhoDados) {
		this.tamanhoDados = tamanhoDados;
	}

	public double getAtraso() {
		return atraso;
	}

	public void setAtraso(double atraso) {
		this.atraso = atraso;
	}

}
