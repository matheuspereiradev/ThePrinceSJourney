package com.matheus.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Entidade {

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
		g.drawImage(this.sprite, this.getX(), this.getY(), null);
	}

}
