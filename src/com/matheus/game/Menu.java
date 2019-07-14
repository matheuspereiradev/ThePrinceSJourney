package com.matheus.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Menu {

	public String[] opcoes = { "Novo jogo", "Carregar jogo", "Sons", "Sair" };

	public int currentOpcao = 0;
	public int maxOpcoes = opcoes.length - 1;
	public BufferedImage banner;
	public boolean up, down, enter, pausa = false;

	public Menu(String path) {
		try {
			banner = ImageIO.read(getClass().getResource(path));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void atualizar() {

		if (up) {
			if (!Jogo.mute) {
				Sons.bipMenu.play();
			}
			up = false;
			currentOpcao--;
			if (currentOpcao < 0) {
				currentOpcao = maxOpcoes;
			}

		}
		if (down) {
			if (!Jogo.mute) {
				Sons.bipMenu.play();
			}
			down = false;
			currentOpcao++;
			if (currentOpcao > maxOpcoes) {
				currentOpcao = 0;
			}
		}

		if (enter) {
			if (opcoes[currentOpcao] == "Novo jogo") {
				Jogo.status = "NORMAL";
				pausa = false;
				enter = false;
			} else if (opcoes[currentOpcao] == "Carregar jogo") {
				if (!Jogo.mute) {
					Sons.naoPodeSong.play();
				}
				enter = false;
			} else if (opcoes[currentOpcao] == "Sons") {
				Jogo.mute = !Jogo.mute;
				if (Jogo.mute)
					Sons.musica.stop();
				else
					Sons.musica.play();

				enter = false;
			} else if (opcoes[currentOpcao] == "Sair") {
				System.exit(0);
			}
		}
	}

	public void renderizar(Graphics g) {
		if (!pausa)
			g.drawImage(banner, 0, 0, Jogo.WIDITH * Jogo.SCALE, Jogo.HEIGHT * Jogo.SCALE, null);
		else {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0, 0, 0, 200));
			g.fillRect(0, 0, Jogo.WIDITH * Jogo.SCALE, Jogo.HEIGHT * Jogo.SCALE);
		}

		// opcoes do menu
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.BOLD, 30));
		if (!pausa) {
			g.drawString("Novo jogo", 380, 250);
		} else {
			g.drawString("Continuar", 380, 250);
		}
		g.setColor(Color.darkGray);
		g.drawString("Carregar jogo", 350, 300);
		g.setColor(Color.WHITE);
		if (Jogo.mute) {
			g.drawString("Efeitos: off", 380, 350);
		} else {
			g.drawString("Efeitos: on", 380, 350);
		}
		g.drawString("Sair", 410, 400);

		if (opcoes[currentOpcao] == "Novo jogo") {
			g.drawString(">", 360, 250);
		} else if (opcoes[currentOpcao] == "Carregar jogo") {
			g.setColor(Color.darkGray);
			g.drawString(">", 330, 300);
		} else if (opcoes[currentOpcao] == "Sons") {
			g.drawString(">", 360, 350);
		} else {
			g.drawString(">", 390, 400);
		}

	}
}
