package br.com.devSchool.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.com.devSchool.cm.excecao.ExplosaoException;
public class Tabuleiro {
	
	private int linhas;
	private int colunas;
	private int minas;
	
	private final List<Campo> campos = new ArrayList<>();

	public Tabuleiro(int linhas, int colunas, int minas) {
		
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}

	public void abrir(int linha, int coluna) {
		try {
			campos.parallelStream()
			.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
			.findFirst()
			.ifPresent(c -> c.abrir());
			
		}catch (ExplosaoException e) {
			campos.forEach(c -> c.setAberto(true));
			throw e;
		}
	}

	public void alternarMarcacao(int linha, int coluna) {
		campos.parallelStream()
		.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
		.findFirst()
		.ifPresent(c -> c.alternarMarcacao());
	}

	private void gerarCampos() {
		for(int lin = 0; lin< linhas; lin++) {
			for(int col = 0; col< colunas; col++) {
				campos.add(new Campo(lin, col));
			}
		}
	}
	
	private void associarVizinhos() {
		for(Campo c1: campos) {
			for(Campo c2: campos) {
				c1.adicionarVizinho(c2);
			}
		}
	}
	
	private void sortearMinas() {
		long minasArmadas = 0;
		Predicate<Campo> minado = c -> c.isMinado();
		
		do {
			int aleatorio = (int)(Math.random() * campos.size());
			
			campos.get(aleatorio).minar();
			
			minasArmadas = campos.stream().filter(minado).count();
			

		} while(minasArmadas < minas);
	}
	
	public boolean objetivoAlcancaco() {
		return campos.stream().allMatch(c -> c.objetivoAlcancaco());
	}
	
	public void reiniciar() {
		campos.stream().forEach(c -> c.reiniciar());
		sortearMinas();
	}
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("   ");
		for(int x = 0; x < linhas; x++) {
			sb.append(" " + x + " ");
		}
		sb.append("\n");
		
		sb.append("  ");
		for(int y=0;y< linhas;y++) {
			sb.append("___");
		}
		sb.append("\n");
		int  i = 0;
		for(int lin = 0; lin < linhas; lin++) {
			sb.append(lin + "| ");
			
			for(int col = 0; col < colunas; col++) {
				sb.append(" ");
				sb.append(campos.get(i));
				sb.append(" ");
				i++;
			}
			
			sb.append("\n");
			
		}
		
		return sb.toString();
		
	}
}
