package com.matheus.entidades;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.matheus.game.Jogo;
import com.matheus.mundo.Camera;
import com.matheus.mundo.Mundo;
import com.matheus.mundo.WallTile;

public class InimigoGiganteDeAco extends Inimigo {

	private BufferedImage[] rightGigante;
	private BufferedImage[] leftGigante;
	private BufferedImage[] upGigante;
	private BufferedImage[] downGigante;
	private double speed = 0.9;
	private int dir_up = 1, dir_right = 0, dir_down = 2, dir_left = 3;
	private int direcao = dir_down;
	private int index = 0, frames = 0, maxFrames = 10, maxIndex = 2;

	public InimigoGiganteDeAco(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		this.power = 30;
		this.vida = 25;

		rightGigante = new BufferedImage[3];
		leftGigante = new BufferedImage[3];
		upGigante = new BufferedImage[3];
		downGigante = new BufferedImage[3];

		for (int i = 0; i < rightGigante.length; i++) {
			rightGigante[i] = Jogo.spritesheet.getSprite(0 + (32 * i), 224, 32, 32);
		}
		for (int i = 0; i < leftGigante.length; i++) {
			leftGigante[i] = Jogo.spritesheet.getSprite(0 + (32 * i), 192, 32, 32);
		}
		for (int i = 0; i < upGigante.length; i++) {
			upGigante[i] = Jogo.spritesheet.getSprite(0 + (32 * i), 256, 32, 32);
		}
		for (int i = 0; i < downGigante.length; i++) {
			downGigante[i] = Jogo.spritesheet.getSprite(0 + (32 * i), 288, 32, 32);
		}
	}

	public void atualizar() {
		movendo = false;

		if (!colisaoComJogador(this.getX(), this.getY(), this.maskX, this.maskY, this.maskW, this.maskH)) {

			if ((int) x < Jogo.jogador.getX() && this.isFree((int) (x + speed), this.getY())
					&& !isColidindo((int) (x + speed), this.getY())) {
				movendo = true;
				direcao = dir_right;
				x += speed;
			} else if ((int) x > Jogo.jogador.getX() && this.isFree((int) (x - speed), this.getY())
					&& !isColidindo((int) (x - speed), this.getY())) {
				movendo = true;
				direcao = dir_left;
				x -= speed;
			}
			if ((int) y < Jogo.jogador.getY() && this.isFree(this.getX(), (int) (y + speed))
					&& !isColidindo(this.getX(), (int) (y + speed))) {
				movendo = true;
				direcao = dir_down;
				y += speed;
			} else if ((int) y > Jogo.jogador.getY() && this.isFree(this.getX(), (int) (y - speed))
					&& !isColidindo(this.getX(), (int) (y - speed))) {
				movendo = true;
				direcao = dir_up;
				y -= speed;
			}

		} else {
			testarAtaqueNoPlayer();// aqui chama o metodo e passa a probabilidade de o ataque dele acertar
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

	public static boolean isFree(int xprox, int yprox) {
		int x1 = xprox / 16;
		int y1 = yprox / 16;

		int x2 = (xprox + 32 - 2) / 16;
		int y2 = yprox / 16;

		int x3 = xprox / 16;
		int y3 = (yprox + 32 - 2) / 16;

		int x4 = (xprox + 32 - 2) / 16;
		int y4 = (yprox + 32 - 2) / 16;

		return !((Mundo.tiles[x1 + (y1 * Mundo.WIDTH_WORD)] instanceof WallTile)
				|| (Mundo.tiles[x2 + y2 * Mundo.WIDTH_WORD] instanceof WallTile)
				|| (Mundo.tiles[x3 + y3 * Mundo.WIDTH_WORD] instanceof WallTile)
				|| (Mundo.tiles[x4 + y4 * Mundo.WIDTH_WORD] instanceof WallTile));
	}

	public boolean isColidindo(int xnext, int ynext) {
		Rectangle gigante = new Rectangle(xnext + maskX, ynext + maskX, maskW, maskH);
		for (int i = 0; i < Jogo.gigantes.size(); i++) {
			InimigoGiganteDeAco gigante2 = Jogo.gigantes.get(i);
			if (gigante2 == this) {
				// verifica se ele é ele proprio exemplo um aliem semore vai estar colidindo com
				// ele mesmo
				continue;
			}
			Rectangle giganteTeste = new Rectangle(gigante2.getX() + maskX, gigante2.getY() + maskY, maskW, maskH);
			if (giganteTeste.intersects(gigante)) {
				return true;
			}
		}
		return false;
	}

	public void renderizar(Graphics g) {

		if (!sofrendoDano) {

			if (direcao == dir_right) {
				g.drawImage(rightGigante[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if (direcao == dir_left) {
				g.drawImage(leftGigante[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
			if (direcao == dir_up) {
				g.drawImage(upGigante[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if (direcao == dir_down) {
				g.drawImage(downGigante[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		} else {
			g.drawImage(Entidade.inimigoGiganteDano, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		// para vizualizar melhor oq esta acontecdo descomenta
		// super.renderizar(g);
		// g.setColor(Color.BLUE);
		// g.fillRect(this.getX()+maskX-Camera.x, this.getY()+maskY-Camera.y,
		// maskW,maskH);
	}
}
