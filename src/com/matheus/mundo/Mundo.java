package com.matheus.mundo;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.matheus.entidades.*;
import com.matheus.game.Jogo;

public class Mundo {

	private Tile[] tiles;
	public static int WIDTH_WORD, HEIGHT_WORD;

	public Mundo(String path) {
		try {
			BufferedImage mapa = ImageIO.read(getClass().getResource(path));
			WIDTH_WORD = mapa.getWidth();
			HEIGHT_WORD = mapa.getHeight();
			int[] pixels = new int[WIDTH_WORD * HEIGHT_WORD];

			tiles = new Tile[WIDTH_WORD * HEIGHT_WORD];
			mapa.getRGB(0, 0, WIDTH_WORD, HEIGHT_WORD, pixels, 0, WIDTH_WORD);
			for (int xx = 0; xx < WIDTH_WORD; xx++) {
				for (int yy = 0; yy < HEIGHT_WORD; yy++) {
					int atual = xx + (yy * WIDTH_WORD);
					tiles[atual] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);//padrão é ser chão
					if (pixels[atual] == 0xFF000000) {
						tiles[atual] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);
						// chao
					} else if (pixels[atual] == 0xFFFFFFFF) {
						tiles[atual] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL);
						// parede
					} else if (pixels[atual] == 0xFF2A00FF) {
						Jogo.jogador.setX(xx*16);
						Jogo.jogador.setY(yy*16);
						// Jogador
					} else if (pixels[atual] == 0xFF00FF21) {
						Jogo.entidades.add(new Inimigo(xx*16, yy*16, 16, 16, Entidade.inimigoCaveira));
						// inimigo
					} else if (pixels[atual] == 0xFFFF0000) {
						Jogo.entidades.add(new CoracaoDeVida(xx*16, yy*16, 16, 16, Entidade.coracaoVida));
						// vida
					} else if (pixels[atual] == 0xFFFFD800) {
						Jogo.entidades.add(new Arma(xx*16, yy*16, 16, 16, Entidade.arma));
						// arma
					} else if (pixels[atual] == 0xFFFF00DC) {
						Jogo.entidades.add(new Municao(xx*16, yy*16, 16, 16, Entidade.municaoBalas));
						// munição
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
