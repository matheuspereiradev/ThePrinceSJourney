package com.matheus.entidades;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.matheus.game.Jogo;

public class Inimigo extends Entidade {

	public Inimigo(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}
	
	public static boolean colisaoComJogador(int x, int y, int mascaraX, int mascaraY, int width,
			int height) {
		Rectangle inimigoAtual = new Rectangle(x + mascaraX, y + mascaraY, width, height);
		Rectangle jogador = new Rectangle(Jogo.jogador.getX(), Jogo.jogador.getY(), Jogo.tamanho, Jogo.tamanho);
		return inimigoAtual.intersects(jogador);
	}
	
	public static boolean colisaoComJogador(int x, int y, int mascaraWidth,
			int mascaraHeight) {
		Rectangle inimigoAtual = new Rectangle(x , y, mascaraWidth, mascaraHeight);
		Rectangle jogador = new Rectangle(Jogo.jogador.getX(), Jogo.jogador.getY(), Jogo.tamanho, Jogo.tamanho);
		return inimigoAtual.intersects(jogador);
	}
	
	public void testarAtaqueNoPlayer(int probabilidadeEmPorcentagem, int forcaAtaque) {
		if (Jogo.jogador.vida >= 0) {
			if (Jogo.rand.nextInt(100) < probabilidadeEmPorcentagem) {
				Jogo.jogador.vida-=Jogo.rand.nextInt(forcaAtaque);
			}
		}else {
			System.exit(0);
		}
	}

}
