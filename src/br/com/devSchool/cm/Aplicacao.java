package br.com.devSchool.cm;

import br.com.devSchool.cm.modelo.Tabuleiro;
import br.com.devSchool.cm.visao.TabuleiroConsole;

public class Aplicacao {
	
	public static void main(String[] args) {
		
		Tabuleiro tabuleiro = new Tabuleiro(6, 6, 6);
		
		new TabuleiroConsole(tabuleiro);

	}
}
