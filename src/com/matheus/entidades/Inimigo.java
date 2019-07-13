package com.matheus.entidades;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import com.matheus.game.Jogo;

public class Inimigo extends Entidade {

	protected int vida;
	protected int danoFrames=10,currentDano=0;
	protected boolean sofrendoDano=false;

	public Inimigo(double x, double y, int width, int height, BufferedImage sprite, int vida) {
		super(x, y, width, height, sprite);
		this.vida = vida;
	}

	public static boolean colisaoComJogador(int x, int y, int mascaraX, int mascaraY, int width, int height) {
		Rectangle inimigoAtual = new Rectangle(x + mascaraX, y + mascaraY, width, height);
		Rectangle jogador = new Rectangle(Jogo.jogador.getX(), Jogo.jogador.getY(), Jogo.tamanho, Jogo.tamanho);
		return inimigoAtual.intersects(jogador);
	}

	public void verificarVida() {
		if (vida <= 0) {
			Jogo.entidades.remove(this);
			Jogo.inimigo.remove(this);
		}
	}

	public void colisaoComBala() {
		for (int i = 0; i < Jogo.balas.size(); i++) {
			Entidade e = Jogo.balas.get(i);
			if (e instanceof AtirarMunicao) {
				if (isColidding(e, this)) {
					vida--;
					sofrendoDano=true;
					Jogo.balas.remove(e);
					return;
				}
			}

		}
	}

	public static void testarAtaqueNoPlayer(int probabilidadeEmPorcentagem) {
		if (Jogo.rand.nextInt(100) < probabilidadeEmPorcentagem) {
			Jogo.jogador.vida--;
			Jogo.jogador.sofrendoDano = true;
		}

	}

}
