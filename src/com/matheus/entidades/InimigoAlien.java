package com.matheus.entidades;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.matheus.game.Jogo;
import com.matheus.mundo.Mundo;

public class InimigoAlien extends Entidade {

	private double speed=0.9;
	private int maskX=8,maskY=8,maskW=10,maskH=10;
	
	public InimigoAlien(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}
	
	public boolean isColidindo(int xnext, int ynext) {
		Rectangle alienAtual=new Rectangle(xnext+maskX,ynext+maskX, maskW,maskH);
		for(int i=0; i<Jogo.aliens.size();i++) {
			InimigoAlien inimigo=Jogo.aliens.get(i);
			if(inimigo==this) {
				//verifica se ele é ele proprio exemplo um aliem semore vai estar colidindo com ele mesmo
				continue;
			}
			Rectangle alienTeste=new Rectangle(inimigo.getX()+maskX, inimigo.getY()+maskY,  maskW,maskH);
			if(alienTeste.intersects(alienAtual)) {
				return true;
			}
			
		}
		
		return false;
	}
	
	public void atualizar() {

			if ((int) x < Jogo.jogador.getX() && Mundo.isFree((int) (x + speed), this.getY()) && !isColidindo((int) (x + speed), this.getY())) {
				x += speed;
			} else if ((int) x > Jogo.jogador.getX() && Mundo.isFree((int) (x - speed), this.getY()) && !isColidindo((int) (x - speed), this.getY())) {
				x -= speed;
			}

			if ((int) y < Jogo.jogador.getY() && Mundo.isFree(this.getX(), (int) (y + speed)) && !isColidindo(this.getX(), (int) (y + speed))) {
				y += speed;
			} else if ((int) y > Jogo.jogador.getY() && Mundo.isFree(this.getX(), (int) (y - speed)) && !isColidindo(this.getX(), (int) (y - speed))) {
				y -= speed;
			
		}
	}
	
	public void renderizar(Graphics g) {
		super.renderizar(g);
		//para vizualizar melhor oq esta acontecdo descomenta
		//g.setColor(Color.BLUE);
		//g.fillRect(this.getX()+maskX-Camera.x, this.getY()+maskY-Camera.y, maskW,maskH);
	}

}
