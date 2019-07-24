package com.matheus.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.matheus.game.Jogo;
import com.matheus.mundo.Camera;
import com.matheus.mundo.Mundo;

public class InimigoMorte extends Inimigo {

	private double speed = 1.8;
	private int index = 0, frames = 0, maxFrames = 10, maxIndex = 2;
	private BufferedImage[] inimigoMorteLeft, inimigoMorteRight, inimigoMorteUp, inimigoMorteDown;
	private int dir_up = 1, dir_right = 0, dir_down = 2, dir_left = 3;
	private int direcao = dir_down;

	public InimigoMorte(double x, double y, int width, int height, BufferedImage sprite, int vida) {
		super(x, y, width, height, sprite, vida);
		inimigoMorteLeft = new BufferedImage[3];
		inimigoMorteRight = new BufferedImage[3];
		inimigoMorteUp = new BufferedImage[3];
		inimigoMorteDown = new BufferedImage[3];
		for (int i = 0; i < inimigoMorteLeft.length; i++) {
			inimigoMorteLeft[i] = Jogo.spritesheet.getSprite(208 + (Jogo.tamanho * i), 48, Jogo.tamanho, Jogo.tamanho);
		}
		for (int i = 0; i < inimigoMorteRight.length; i++) {
			inimigoMorteRight[i] = Jogo.spritesheet.getSprite(208 + (Jogo.tamanho * i), 32, Jogo.tamanho, Jogo.tamanho);
		}
		for (int i = 0; i < inimigoMorteUp.length; i++) {
			inimigoMorteUp[i] = Jogo.spritesheet.getSprite(208 + (Jogo.tamanho * i), 64, Jogo.tamanho, Jogo.tamanho);
		}
		for (int i = 0; i < inimigoMorteDown.length; i++) {
			inimigoMorteDown[i] = Jogo.spritesheet.getSprite(208 + (Jogo.tamanho * i), 80, Jogo.tamanho, Jogo.tamanho);
		}
	}

	public void atualizar() {
		movendo = false;
		// movimentação
		if (!Inimigo.colisaoComJogador(this.getX(), this.getY(), this.maskX, this.maskY, this.maskW, this.maskH)) {
			if (Jogo.rand.nextInt(100) < 30) {

				if ((int) x < Jogo.jogador.getX() && Mundo.isFree((int) (x + speed), this.getY())) {
					movendo = true;
					direcao = dir_right;
					x += speed;
				} else if ((int) x > Jogo.jogador.getX() && Mundo.isFree((int) (x - speed), this.getY())) {
					movendo = true;
					direcao = dir_left;
					x -= speed;
				}
				if ((int) y < Jogo.jogador.getY() && Mundo.isFree(this.getX(), (int) (y + speed))) {
					movendo = true;
					direcao = dir_down;
					y += speed;
				} else if ((int) y > Jogo.jogador.getY() && Mundo.isFree(this.getX(), (int) (y - speed))) {
					movendo = true;
					direcao = dir_up;
					y -= speed;
				}
			}
		} else {
			Inimigo.testarAtaqueNoPlayer(5);// aqui chama o metodo e passa a probabilidade de o ataque dele acertar
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
		colisaoComBala();
		verificarVida();
		if (sofrendoDano) {
			currentDano++;
			if (currentDano == danoFrames) {
				currentDano = 0;
				sofrendoDano = false;
			}
		}
	}

	public void renderizar(Graphics g) {
		if (!sofrendoDano) {
			if (direcao == dir_right) {
				g.drawImage(inimigoMorteRight[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if (direcao == dir_left) {
				g.drawImage(inimigoMorteLeft[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
			if (direcao == dir_up) {
				g.drawImage(inimigoMorteUp[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if (direcao == dir_down) {
				g.drawImage(inimigoMorteDown[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		} else {
			g.drawImage(inimigoMorteDano, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}

	}

}
