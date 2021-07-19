package main;

import org.junit.Test;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.concurrent.ThreadLocalRandom;

public class TestMain {

	public static double PORCENTAGEM_PROCURADA = 0.05; // custo 10% menor

//	@Test
	public void test() throws IOException {
		
		//tente 1000000 de cenários
		for (int z = 0; z < 1000000; z++) {
			Randons randons = new Randons();
			List<Recurso> recursos = randons.getRecursos(10);
			List<Requisicao> requisicoes = randons.getRequisicoes(20);
			
			Main main = new Main();
			
			// armazena a solução gulosa
			Solucao solucaoGulosa = main.getSolucaoGulosa(requisicoes, recursos);

			// armazena a melhor solução 10% melhor, caso exista
			Solucao melhorSolucao = new Solucao("", 0.0);

			double custoProcurado = solucaoGulosa.getCusto() * (1 - PORCENTAGEM_PROCURADA);

			for (int i = 0; i < 100000; i++) {
				for (int x = 2; x < 6; x++) {
					Solucao solucaoAleatoria = main.getSolucaoAleatoria(requisicoes, recursos, x);
					if (solucaoAleatoria.getCusto() < custoProcurado) {
						custoProcurado = solucaoAleatoria.getCusto();
						melhorSolucao.setCaminho(solucaoAleatoria.getCaminho());
						melhorSolucao.setCusto(solucaoAleatoria.getCusto());
					}
				}
			}
			if (melhorSolucao.getCaminho() != "") {
				main.imprimeRecursos(recursos);
				main.imprimeRequisicoes(requisicoes);
				System.out.println("Gulosa: " + solucaoGulosa.getCaminho() + " > " + solucaoGulosa.getCusto() + "("
						+ (100 - (PORCENTAGEM_PROCURADA * 100)) + "% = " + custoProcurado + ")");
				System.out.println("Melhor: " + melhorSolucao.getCaminho() + " > " + melhorSolucao.getCusto() + "("
						+ (100 - (melhorSolucao.getCusto() / solucaoGulosa.getCusto() * 100)) + "%)");
				// adiciona treino
				adicionaTreino(recursos, requisicoes,
						melhorSolucao.getCaminho().replace("[", "").replace("]", "").replace(" ", "").split(","));
			}
		}
		System.out.println("Fim.");
	}

	public void adicionaTreino(List<Recurso> recursos, List<Requisicao> requisicoes, String[] chosens) throws IOException {
		Main main = new Main();
		String entradas = "";
		String saidas = "";
		double[][] calculos = new double[recursos.size()][3];
		double custo = 0;
		int ii = 0;
		for (String r : chosens) {
			custo = custo + main.calculaCaminho(ii++, requisicoes, recursos, calculos, r);
			entradas = entradas + main.normalize(calculos, recursos) + "\n";
			saidas = saidas + getIndexByLetter(r) + "\n";
		}
		adicionarEntradas(entradas);
		adicionarSaidas(saidas);
	}

	public String getIndexByLetter(String l) {
		String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
		String x = "";
		for (int i = 0; i < 10; i++) {
			if (l.equals(letters[i]))
				x = x + "1,";
			else
				x = x + "0,";
		}
		return x;
	}

	public void adicionarEntradas(String x) throws IOException {
		BufferedWriter f = new BufferedWriter(new FileWriter("entradas.txt", true));
		f.append(x);
		f.close();
	}

	public void adicionarSaidas(String x) throws IOException {
		BufferedWriter f = new BufferedWriter(new FileWriter("saidas.txt", true));
		f.append(x);
		f.close();
	}
	
	static int N = 5000;
	String[] arrEntradas = new String[N];
	int[] aux = new int[N];

	@Test
	public void embaralhar() throws IOException {
		
		System.out.println("Embaralhando...");
		
		for (int i = 0; i < N; i++) {
			boolean escolhido = false;
			while (!escolhido) {
				int r = ThreadLocalRandom.current().nextInt(0, N);
				if (aux[r] == 0) {
					escolhido = true;
					aux[r] = i;
				}
			}
		}

		BufferedReader f1 = new BufferedReader(new FileReader("entradas.txt"));
		int i = 0;
		while (f1.ready()) {
			String linha = f1.readLine();
			arrEntradas[aux[i]] = linha;
			i++;
		}
		f1.close();

		BufferedWriter f2 = new BufferedWriter(new FileWriter("entradasEmbaralhadas.txt", true));
		for (int j = 0; j < N; j++) {
			if (arrEntradas[j] != null) {
				f2.append(arrEntradas[j]);
				f2.newLine();
			}
		}
		f2.close();

		BufferedReader f3 = new BufferedReader(new FileReader("saidas.txt"));
		i = 0;
		while (f3.ready()) {
			String linha = f3.readLine();
			arrEntradas[aux[i]] = linha;
			i++;
		}
		f3.close();

		BufferedWriter f4 = new BufferedWriter(new FileWriter("saidasEmbaralhadas.txt", true));
		for (int j = 0; j < N; j++) {
			if (arrEntradas[j] != null) {
				f4.append(arrEntradas[j]);
				f4.newLine();
			}
		}
		f4.close();
		
		System.out.println("Fim.");
	}
}
