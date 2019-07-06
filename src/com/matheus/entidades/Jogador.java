package com.matheus.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Jogador extends Entidade {

	public boolean left,right,up,down;
	public double speed=0.7;
	
	public Jogador(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}
	
	public void atualizar() {
		if(up) {
			y-=speed;
		}else if(down) {
			y+=speed;
		}
		if(left) {
			x-=speed;
		}else if(right) {
			x+=speed;
		}

	}

	public void renderizar(Graphics g) {
		g.drawImage(this.sprite, this.getX(), this.getY(), null);
	}

	

}
