package com.matheus.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.matheus.game.Jogo;
import com.matheus.mundo.Camera;

public class Entidade {
	
	public static BufferedImage coracaoVida=Jogo.spritesheet.getSprite(128, 16, 16, 16);
	public static BufferedImage municaoBalas=Jogo.spritesheet.getSprite(96,16, 16, 16);
	public static BufferedImage arma=Jogo.spritesheet.getSprite(112, 16, 16, 16);
	public static BufferedImage inimigoCaveira=Jogo.spritesheet.getSprite(16, 48, 16, 16);
	public static BufferedImage inimigoAlien=Jogo.spritesheet.getSprite(48, 16, 16, 16);
	

	protected int width, height;
	protected double x, y;
	protected BufferedImage sprite;

	public Entidade(double x, double y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
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

	public void atualizar() {
		
	}

	public void renderizar(Graphics g) {
		g.drawImage(this.sprite, this.getX()-Camera.x, this.getY()-Camera.y, null);
	}

}
