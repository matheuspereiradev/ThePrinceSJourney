package com.matheus.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Entidade {
	
	private int x,y,width,height;
	private BufferedImage sprite;

	public Entidade(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite=sprite;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
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
		g.drawImage(this.sprite, this.getX(), this.getY(), null);
	}

}
