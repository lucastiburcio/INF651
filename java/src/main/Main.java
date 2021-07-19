package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

	public void imprimeRecursos(List<Recurso> recursos) {
		System.out.println("-- RECURSOS --");
		for (int i = 0; i < recursos.size(); i++) {
			System.out.println(String.format("%3s %5s %5s %8s", recursos.get(i).getNome(),
					recursos.get(i).getBandWidth(), recursos.get(i).getPropagacao(), recursos.get(i).getMips()));
		}
	}

	public void imprimeRequisicoes(List<Requisicao> requisicoes) {
		System.out.println("-- REQUISIÇÕES --");
		for (Requisicao requisicao : requisicoes) {
			System.out.println(String.format("%4s %8s %6s", requisicao.getNome(), requisicao.getTamanhoDados(),
					requisicao.getAtraso()));
		}
	}

	public void decrementaLiberacao(double atraso, double[][] calculos) {
		for (int i = 0; i < calculos.length; i++) {
			calculos[i][0] -= atraso;
			if (calculos[i][0] < 0)
				calculos[i][0] = 0.0;
		}
	}

	public Solucao getSolucaoGulosa(List<Requisicao> requisicoes, List<Recurso> recursos) {
		return getSolucaoAleatoria(requisicoes,recursos, 1);
	}

	public Solucao getSolucaoAleatoria(List<Requisicao> requisicoes, List<Recurso> recursos, int q) {
		double[][] calculos = new double[recursos.size()][3];
		String[] escolhidos = new String[requisicoes.size()];
		double custo = 0;
		int indiceArrayOrdemEscolha = 0;
		for (Requisicao requisicao : requisicoes) {
			decrementaLiberacao(requisicao.getAtraso(), calculos);
			for (int j = 0; j < recursos.size(); j++) {
				calculos[j][2] = getEstimativaPura(requisicao, recursos.get(j));
				calculos[j][1] = calculos[j][0] + calculos[j][2];
			}
			Estimativa menorEstimativa = getMenoresEstimativas(requisicao, recursos, calculos, q)
					.get(ThreadLocalRandom.current().nextInt(0, q));
			int indice = getIndiceRecursoPorEstimativa(menorEstimativa.getNome(), recursos);
			calculos[indice][0] += menorEstimativa.getEstimativa();
			escolhidos[indiceArrayOrdemEscolha++] = menorEstimativa.getNome();
			custo += calculos[indice][1];
		}
		return new Solucao(Arrays.toString(escolhidos), custo);
	}

	public double getEstimativaPura(Requisicao requisicao, Recurso recurso) {
		return (requisicao.getTamanhoDados() / recurso.getBandWidth()) + recurso.getPropagacao()
				+ (requisicao.getTamanhoDados() / recurso.getMips());
	}

	public int getIndiceRecursoPorEstimativa(String r, List<Recurso> recursos) {
		for (int i = 0; i < recursos.size(); i++) {
			if (recursos.get(i).getNome().equals(r)) {
				return i;
			}
		}
		return -1;
	}

	public List<Estimativa> getMenoresEstimativas(Requisicao requisicao, List<Recurso> recursos, double[][] calculos,
			int q) {
		List<Estimativa> estimativas = new ArrayList<>();
		for (int i = 0; i < recursos.size(); i++) {
			calculos[i][1] = calculos[i][0] + calculos[i][2];
			Estimativa estimativa = new Estimativa(recursos.get(i).getNome(), calculos[i][1]);
			estimativas.add(estimativa);
		}
		// ordena crescente
		Collections.sort(estimativas, new Comparator<Estimativa>() {
			@Override
			public int compare(Estimativa a, Estimativa b) {
				if (a.getEstimativa() < b.getEstimativa()) {
					return -1;
				}
				if (a.getEstimativa() > b.getEstimativa()) {
					return 1;
				}
				return 0;
			}
		});
		return new ArrayList<>(estimativas.subList(0, q));
	}
	
	public double calculaCaminho(int indiceRequisicao, List<Requisicao> requisicoes, List<Recurso> recursos,
			double[][] calculos, String r) {
		// decrementa
		for (int i = 0; i < recursos.size(); i++) {
			calculos[i][0] -= requisicoes.get(indiceRequisicao).getAtraso();
			if (calculos[i][0] < 0)
				calculos[i][0] = 0.0;
		}
		// calcula estimativas
		for (int i = 0; i < recursos.size(); i++) {
			calculos[i][2] = getEstimativaPura(requisicoes.get(indiceRequisicao), recursos.get(i));
			calculos[i][1] = calculos[i][0] + calculos[i][2];
		}
		int indiceRecurso = getIndiceRecursoPorEstimativa(r, recursos);
		calculos[indiceRecurso][0] += calculos[indiceRecurso][1];
		return calculos[indiceRecurso][1];
	}
	
	public String normalize(double[][] calculos, List<Recurso> recursos) {
		String x = "";
		double maxBandWidth = 0.0;
		double maxPropagacao = 0.0;
		double maxMips = 0.0;
		double maxEstimativa = 0.0;
		for (int i = 0; i < recursos.size(); i++) {
			if (maxBandWidth < recursos.get(i).getBandWidth())
				maxBandWidth = recursos.get(i).getBandWidth();
			if (maxPropagacao < recursos.get(i).getPropagacao())
				maxPropagacao = recursos.get(i).getPropagacao();
			if (maxMips < recursos.get(i).getMips())
				maxMips = recursos.get(i).getMips();
			if (maxEstimativa < calculos[i][1])
				maxEstimativa = calculos[i][1];
		}
		for (int i = 0; i < recursos.size(); i++) {
			x = x + recursos.get(i).getBandWidth() / maxBandWidth + ","
					+ recursos.get(i).getPropagacao() / maxPropagacao + "," + recursos.get(i).getMips() / maxMips + ","
					+ calculos[i][1] / maxEstimativa + ",";
		}
		return x;
	}

	List<Recurso> getRecursos() {
		List<Recurso> recursos = new ArrayList<>();
		recursos.add(new Recurso("A", 8, 1, 2869));
		recursos.add(new Recurso("B", 8, 1, 1500));
		recursos.add(new Recurso("C", 7, 1, 2761));
		recursos.add(new Recurso("D", 7, 1, 10384));
		recursos.add(new Recurso("E", 7, 1, 4982));
		recursos.add(new Recurso("F", 8, 1, 4350));
		recursos.add(new Recurso("G", 7, 1, 1532));
		recursos.add(new Recurso("H", 7, 1, 2894));
		recursos.add(new Recurso("I", 8, 1, 3623));
		recursos.add(new Recurso("J", 7, 1, 2507));
		return recursos;

	}

	List<Requisicao> getRequisicoes() {
		List<Requisicao> requisicoes = new ArrayList<>();
		requisicoes.add(new Requisicao("s1", 5490, 0));
		requisicoes.add(new Requisicao("s2", 1001, 56));
		requisicoes.add(new Requisicao("s3", 8141, 78));
		requisicoes.add(new Requisicao("s4", 2639, 40));
		requisicoes.add(new Requisicao("s5", 7900, 97));
		requisicoes.add(new Requisicao("s6", 6385, 58));
		requisicoes.add(new Requisicao("s7", 3402, 53));
		requisicoes.add(new Requisicao("s8", 9577, 37));
		requisicoes.add(new Requisicao("s9", 6392, 40));
		requisicoes.add(new Requisicao("s10", 8634, 48));
		requisicoes.add(new Requisicao("s11", 9462, 56));
		requisicoes.add(new Requisicao("s12", 3657, 62));
		requisicoes.add(new Requisicao("s13", 6178, 65));
		requisicoes.add(new Requisicao("s14", 9507, 69));
		requisicoes.add(new Requisicao("s15", 6444, 29));
		requisicoes.add(new Requisicao("s16", 4948, 75));
		requisicoes.add(new Requisicao("s17", 2569, 42));
		requisicoes.add(new Requisicao("s18", 5043, 22));
		requisicoes.add(new Requisicao("s19", 4371, 41));
		requisicoes.add(new Requisicao("s20", 2933, 58));
		return requisicoes;
	}
}