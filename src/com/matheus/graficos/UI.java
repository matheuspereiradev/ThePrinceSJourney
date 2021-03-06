package com.matheus.graficos;


import java.awt.Graphics;

import com.matheus.entidades.Entidade;
import com.matheus.game.Jogo;

public class UI {

	public int quantidadeDeCoracoes = 11;
	public int CoracoesMax = 11;

	public void atualizar() {
		quantidadeDeCoracoes = (int) (Jogo.jogador.vida / 10) + 1;
	}
	
	public void renderizar(Graphics g) {

/*	COLOCAR BARRA DE LIFE
 * 	g.setColor(Color.RED);
	g.fillRect(12, 5, 82, 10);
	g.setColor(new Color(0, 127, 14));
	g.fillRect(12, 5, (int) ((Jogo.jogador.vida / Jogador.MAX_LIFE) * 82), 10);*/

		for (int i = 0; i < CoracoesMax; i ++) {
			g.drawImage(Entidade.coracao_2, 5 + (i * 10), 5, null);
		}
		for (int i = 0; i < quantidadeDeCoracoes; i ++) {
			g.drawImage(Entidade.coracao, 5 + (i * 10), 5, null);
		}
	}

}
