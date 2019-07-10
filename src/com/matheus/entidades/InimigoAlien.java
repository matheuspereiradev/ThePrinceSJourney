package com.matheus.entidades;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.matheus.game.Jogo;
import com.matheus.mundo.Camera;
import com.matheus.mundo.Mundo;

public class InimigoAlien extends Entidade {

	private double speed = 0.9;
	private int maskX = 8, maskY = 8, maskW = 10, maskH = 10;
	private int dir_up = 1, dir_right = 0, dir_down = 2, dir_left = 3;
	private int direcao = dir_down;
	private boolean movendo = false;
	private int index = 0, frames = 0, maxFrames = 10, maxIndex = 2, tamanhoArray = 3;

	private BufferedImage[] rightAlien;
	private BufferedImage[] leftAlien;
	private BufferedImage[] upAlien;
	private BufferedImage[] downAlien;

	public InimigoAlien(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		rightAlien = new BufferedImage[tamanhoArray];
		leftAlien = new BufferedImage[tamanhoArray];
		upAlien = new BufferedImage[tamanhoArray];
		downAlien = new BufferedImage[tamanhoArray];

		for (int i = 0; i < rightAlien.length; i++) {
			rightAlien[i] = Jogo.spritesheet.getSprite(64 + (Jogo.tamanho * i), 32, Jogo.tamanho, Jogo.tamanho);
		}
		for (int i = 0; i < leftAlien.length; i++) {
			leftAlien[i] = Jogo.spritesheet.getSprite(16 + (Jogo.tamanho * i), 32, Jogo.tamanho, Jogo.tamanho);
		}
		for (int i = 0; i < upAlien.length; i++) {
			upAlien[i] = Jogo.spritesheet.getSprite(112 + (Jogo.tamanho * i), 32, Jogo.tamanho, Jogo.tamanho);
		}
		for (int i = 0; i < downAlien.length; i++) {
			downAlien[i] = Jogo.spritesheet.getSprite(48 + (Jogo.tamanho * i), 16, Jogo.tamanho, Jogo.tamanho);
		}

	}

	public static boolean colisaoComJogador(int x, int y, int mascaraX, int mascaraY, int mascaraWidth,
			int mascaraHeight) {
		Rectangle alienAtual = new Rectangle(x + mascaraX, y + mascaraY, mascaraWidth, mascaraHeight);
		Rectangle jogador = new Rectangle(Jogo.jogador.getX(), Jogo.jogador.getY(), Jogo.tamanho, Jogo.tamanho);
		return alienAtual.intersects(jogador);
	}

	public boolean isColidindo(int xnext, int ynext) {
		Rectangle alienAtual = new Rectangle(xnext + maskX, ynext + maskX, maskW, maskH);
		for (int i = 0; i < Jogo.aliens.size(); i++) {
			InimigoAlien inimigo = Jogo.aliens.get(i);
			if (inimigo == this) {
				// verifica se ele � ele proprio exemplo um aliem semore vai estar colidindo com
				// ele mesmo
				continue;
			}
			Rectangle alienTeste = new Rectangle(inimigo.getX() + maskX, inimigo.getY() + maskY, maskW, maskH);
			if (alienTeste.intersects(alienAtual)) {
				return true;
			}

		}

		return false;
	}

	public void atualizar() {
		movendo = false;
		
		if(!colisaoComJogador(this.getX(), this.getY(), this.maskX, this.maskY, this.maskW, this.maskH)) {
		
		if ((int) x < Jogo.jogador.getX() && Mundo.isFree((int) (x + speed), this.getY())
				&& !isColidindo((int) (x + speed), this.getY())) {
			movendo = true;
			direcao = dir_right;
			x += speed;
		} else if ((int) x > Jogo.jogador.getX() && Mundo.isFree((int) (x - speed), this.getY())
				&& !isColidindo((int) (x - speed), this.getY())) {
			movendo = true;
			direcao = dir_left;
			x -= speed;
		}
		if ((int) y < Jogo.jogador.getY() && Mundo.isFree(this.getX(), (int) (y + speed))
				&& !isColidindo(this.getX(), (int) (y + speed))) {
			movendo = true;
			direcao = dir_down;
			y += speed;
		} else if ((int) y > Jogo.jogador.getY() && Mundo.isFree(this.getX(), (int) (y - speed))
				&& !isColidindo(this.getX(), (int) (y - speed))) {
			movendo = true;
			direcao = dir_up;
			y -= speed;
		}
		
		}else {
			if(Jogo.rand.nextInt(100)<10) {
				Jogo.jogador.vida--;
			}
		}

		if (movendo) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex) {
					index = 0;
				}
			}
		}
	}

	public void renderizar(Graphics g) {

		if (direcao == dir_right) {
			g.drawImage(rightAlien[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		} else if (direcao == dir_left) {
			g.drawImage(leftAlien[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		if (direcao == dir_up) {
			g.drawImage(upAlien[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		} else if (direcao == dir_down) {
			g.drawImage(downAlien[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		// para vizualizar melhor oq esta acontecdo descomenta
		// super.renderizar(g);
		// g.setColor(Color.BLUE);
		// g.fillRect(this.getX()+maskX-Camera.x, this.getY()+maskY-Camera.y,
		// maskW,maskH);
	}

}
