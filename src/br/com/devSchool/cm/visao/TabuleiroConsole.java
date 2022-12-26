package br.com.devSchool.cm.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.devSchool.cm.excecao.ExplosaoException;
import br.com.devSchool.cm.excecao.SairException;
import br.com.devSchool.cm.modelo.Tabuleiro;

public class TabuleiroConsole {
	
	private Tabuleiro tabuleiro;
	Scanner scan = new Scanner(System.in);
	
	public TabuleiroConsole(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		
		executarJogo();
	}

	private void executarJogo() {
		try {
			boolean continuar = true;
			while(continuar) {
				
				cicloJogo();
				
				System.out.print("Outra partida? [S/n]: ");
				String resposta = scan.nextLine();
				
				if("n".equalsIgnoreCase(resposta)) {
					continuar = false;
				}
				else {
					tabuleiro.reiniciar();
				}
				
			}
		} catch (SairException e) {
			System.out.println("Tchau!!!");
		}
		finally {
			scan.close();
		}
	}
	
	private void cicloJogo() {
		try {
			
			while(!tabuleiro.objetivoAlcancaco()) {
				System.out.println(tabuleiro);
				
				String digitado = capturarValorDigitado("Digite [x, y]: ");
				
				Iterator<Integer> xy = Arrays.stream(digitado.split(","))
				.map(e -> Integer.parseInt(e.trim()))
				.iterator();
				
				digitado = capturarValorDigitado("[ 1 ] - Abrir || [ 2 ] - (des)Marcar: ");
				
				if("1".equals(digitado)) {
					tabuleiro.abrir(xy.next(), xy.next());
				}
				else if("2".equals(digitado)) {
					tabuleiro.alternarMarcacao(xy.next(), xy.next());
				}
				
			}
			System.out.println(tabuleiro);
			System.out.println("Voce ganhou!!");
		}
		catch (ExplosaoException e) {
			System.out.println(tabuleiro);
			System.out.println("Voce perdeu!!");
		}
	}
	
	private String capturarValorDigitado(String texto) {
		
		System.out.print(texto);
		String digitado = scan.nextLine();
		
		if("sair".equalsIgnoreCase(digitado)) {
			throw new SairException();
		}
		return digitado;
	}
}
