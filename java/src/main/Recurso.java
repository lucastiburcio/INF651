package main;

public class Recurso implements Cloneable {

	private String nome;
	private double bandWidth, propagacao, mips;

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	public Recurso(String nome, double bandWidth, double propagacao, double mips) {
		super();
		this.nome = nome;
		this.bandWidth = bandWidth;
		this.propagacao = propagacao;
		this.mips = mips;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getBandWidth() {
		return bandWidth;
	}

	public void setBandWidth(double bandWidth) {
		this.bandWidth = bandWidth;
	}

	public double getPropagacao() {
		return propagacao;
	}

	public void setPropagacao(double propagacao) {
		this.propagacao = propagacao;
	}

	public double getMips() {
		return mips;
	}

	public void setMips(double mips) {
		this.mips = mips;
	}
}
