package com.matheus.entidades;

import java.awt.image.BufferedImage;

import com.matheus.game.Jogo;
import com.matheus.mundo.Mundo;

public class InimigoAlien extends Entidade {

	private double speed=0.9;
	public InimigoAlien(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}
	
	public void atualizar() {

			if (((int) x < Jogo.jogador.getX()) && (Mundo.isFree((int) (x + speed), this.getY()))) {
				x += speed;
			} else if ((int) x > Jogo.jogador.getX() && Mundo.isFree((int) (x - speed), this.getY())) {
				x -= speed;
			}

			if ((int) y < Jogo.jogador.getY() && Mundo.isFree(this.getX(), (int) (y + speed))) {
				y += speed;
			} else if ((int) y > Jogo.jogador.getY() && Mundo.isFree(this.getX(), (int) (y - speed))) {
				y -= speed;
			
		}
	}

}
