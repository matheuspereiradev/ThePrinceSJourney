package com.matheus.graficos;

import java.awt.Color;
import java.awt.Graphics;

import com.matheus.entidades.Jogador;
import com.matheus.game.Jogo;

public class UI {

	public void renderizar(Graphics g) {

		g.setColor(Color.RED);
		g.fillRect(12, 5, 82, 10);
		g.setColor(new Color(0, 127, 14));
		g.fillRect(12, 5, (int) ((Jogo.jogador.vida / Jogador.MAX_LIFE) * 82), 10);

	}

}
