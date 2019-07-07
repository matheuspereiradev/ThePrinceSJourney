package com.matheus.mundo;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Mundo {

	private Tile[] tiles;
	public static int WIDTH_WORD, HEIGHT_WORD;

	public Mundo(String path) {
		try {
			BufferedImage mapa = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[mapa.getWidth() * mapa.getHeight()];
			WIDTH_WORD = mapa.getWidth();
			HEIGHT_WORD = mapa.getHeight();
			tiles = new Tile[mapa.getWidth() * mapa.getHeight()];
			mapa.getRGB(0, 0, mapa.getWidth(), mapa.getHeight(), pixels, 0, mapa.getWidth());
			for (int xx = 0; xx < mapa.getWidth(); xx++) {
				for (int yy = 0; yy < mapa.getHeight(); yy++) {
					int atual = xx + (yy * mapa.getWidth());
 
					if (pixels[atual] == 0xFF000000) {
						tiles[atual]=new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR); 
						// chao
					} else if (pixels[atual] == 0xFFFFFFFF) {
						tiles[atual]=new FloorTile(xx*16, yy*16, Tile.TILE_WALL);
						// parede
					} else if (pixels[atual] == 0xFFFF0000) {
						tiles[atual]=new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR); 
						//Jogador
					}else {
						tiles[atual]=new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR); 
						//chao
					}
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void renderizar(Graphics g) {
		for (int xx = 0; xx < WIDTH_WORD; xx++) {
			for (int yy = 0; yy < HEIGHT_WORD; yy++) {
				Tile tile = tiles[xx + (yy * WIDTH_WORD)];
				tile.renderizar(g);
			}
		}
	}

}
