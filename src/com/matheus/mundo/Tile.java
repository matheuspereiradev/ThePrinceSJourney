package com.matheus.mundo;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.matheus.game.Jogo;

public class Tile {
	public static BufferedImage TILE_FLOOR = Jogo.spritesheet.getSprite(144, 0, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage TILE_FLOOR_TERRA = Jogo.spritesheet.getSprite(144, 48, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage TILE_WALL = Jogo.spritesheet.getSprite(144, 16, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage TILE_FLOOR_GRAMA = Jogo.spritesheet.getSprite(176, 0, Jogo.tamanho, Jogo.tamanho);
	
	
	private BufferedImage sprite;
	private int x, y;

	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}

	public void renderizar(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}
}
