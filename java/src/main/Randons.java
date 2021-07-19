package main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Randons {
	public static int MIN_BANDWIDTH = 5;
	public static int MAX_BANDWIDTH = 10;
	public static int MIN_PROPAGACAO = 1;
	public static int MAX_PROPAGACAO = 1;
	public static int MIN_MIPS = 1000;
	public static int MAX_MIPS = 5000;
	public static int MIN_TAMANHO_DADOS = 1000;
	public static int MAX_TAMANHO_DADOS = 5000;
	public static int MIN_ATRASO = 10;
	public static int MAX_ATRASO = 50;
	
	String[] nomeRecursos = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N" };

	public int getRandomBandWidth() {
		return ThreadLocalRandom.current().nextInt(MIN_BANDWIDTH, MAX_BANDWIDTH + 1);
	}

	public int getRandomPropagacao() {
		return ThreadLocalRandom.current().nextInt(MIN_PROPAGACAO, MAX_PROPAGACAO + 1);
	}

	public int getRandomMips() {
		return ThreadLocalRandom.current().nextInt(MIN_MIPS, MAX_MIPS + 1);
	}

	public int getRandomTamanhoDados() {
		return ThreadLocalRandom.current().nextInt(MIN_TAMANHO_DADOS, MAX_TAMANHO_DADOS + 1);
	}

	public int getRandomAtraso() {
		return ThreadLocalRandom.current().nextInt(MIN_ATRASO, MAX_ATRASO + 1);
	}

	public Recurso getRandomRecurso(int i) {
		return new Recurso(nomeRecursos[i], getRandomBandWidth(), getRandomPropagacao(), getRandomMips());
	}

	public Requisicao getRandomRequisicao(int i) {
		return new Requisicao("s" + (i + 1), getRandomTamanhoDados(), getRandomAtraso());
	}

	List<Recurso> getRecursos(int q) {
		List<Recurso> recursos = new ArrayList<>();
		for (int i = 0; i < q; i++) {
			recursos.add(getRandomRecurso(i));
		}
		return recursos;
	}

	List<Requisicao> getRequisicoes(int q) {
		List<Requisicao> requisicoes = new ArrayList<>();
		for (int i = 0; i < q; i++) {
			requisicoes.add(getRandomRequisicao(i));
		}
		return requisicoes;
	}
}
