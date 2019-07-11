package com.matheus.entidades;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.matheus.game.Jogo;
import com.matheus.mundo.Camera;

public class Entidade {
	
	public static BufferedImage coracaoVida=Jogo.spritesheet.getSprite(128, 16, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage municaoBalas=Jogo.spritesheet.getSprite(96,16, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage arma=Jogo.spritesheet.getSprite(128, 48, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage inimigoCaveira=Jogo.spritesheet.getSprite(16, 48, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage inimigoAlien=Jogo.spritesheet.getSprite(48, 16, Jogo.tamanho, Jogo.tamanho);
	

	protected int width, height;
	protected double x, y;
	protected BufferedImage sprite;
	
	protected int maskX,maskY,maskW,maskH;

	public Entidade(double x, double y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		this.maskX=0;
		this.maskY=0;
		//por padr�o a mascara � do tamanho inteiro do jogador passado ao criar uma entidade
		this.maskW=width;
		this.maskH=height;
	}
	
	public void setMask(int maskX, int maskY, int maskW, int maskH) {
		this.maskX=maskX;
		this.maskY=maskY;
		this.maskW=maskW;
		this.maskH=maskH;
	}

	public int getX() {
		return (int)this.x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public int getY() {
		return (int)this.y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public static boolean isColidding(Entidade e1,Entidade e2) {
		Rectangle mask1 = new Rectangle(e1.getX() + e1.maskX, e1.getY() + e1.maskY, e1.maskW, e1.maskH);
		Rectangle mask2 = new Rectangle(e2.getX() + e2.maskX, e2.getY() + e2.maskY, e2.maskW, e2.maskH);
		return mask1.intersects(mask2);
	}

	public void atualizar() {
		
	}

	public void renderizar(Graphics g) {
		g.drawImage(this.sprite, this.getX()-Camera.x, this.getY()-Camera.y, null);
	}

}
