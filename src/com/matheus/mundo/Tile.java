package com.matheus.mundo;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.matheus.game.Jogo;

public class Tile {
	public static BufferedImage TILE_FLOOR = Jogo.spritesheet.getSprite(144, 0, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage TILE_FLOOR_TERRA = Jogo.spritesheet.getSprite(144, 48, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage TILE_WALL = Jogo.spritesheet.getSprite(144, 16, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage TILE_FLOOR_2 = Jogo.spritesheet.getSprite(176, 0, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage TILE_LAVA =Jogo.spritesheet.getSprite(208, 16, 16, 16);
	public static BufferedImage TILE_LAVA_2 =Jogo.spritesheet.getSprite(224, 16, 16, 16);
	
	private BufferedImage sprite;
	private int x, y;

	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
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

	public void renderizar(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}
}
