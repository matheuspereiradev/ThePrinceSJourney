package com.matheus.mundo;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import com.matheus.entidades.*;
import com.matheus.game.Jogo;
import com.matheus.game.Sons;
import com.matheus.graficos.Spritesheet;

public class Mundo {

	public static Tile[] tiles;
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

					if(Jogo.rand.nextInt(10)<4) {
					tiles[atual] = new FloorTile(xx * Jogo.tamanho, yy * Jogo.tamanho, Tile.TILE_FLOOR_2);
					}else {
					tiles[atual] = new FloorTile(xx * Jogo.tamanho, yy * Jogo.tamanho, Tile.TILE_FLOOR);
					}
					//padrão é ser grama

					if (pixels[atual] == 0xFF000000) {
						tiles[atual] = new FloorTile(xx * Jogo.tamanho, yy * Jogo.tamanho, Tile.TILE_FLOOR);
						// chao
					} else if (pixels[atual] == 0xFFFFFFFF) {
						tiles[atual] = new WallTile(xx * Jogo.tamanho, yy * Jogo.tamanho, Tile.TILE_WALL);
						// parede
					} else if (pixels[atual] == 0xFF2A00FF) {
						Jogo.jogador.setX(xx * Jogo.tamanho);
						Jogo.jogador.setY(yy * Jogo.tamanho);
						// Jogo.jogador.setMask(1, 1, 15, 15);
						// Jogador
					} else if (pixels[atual] == 0xFFBC7BF2) {
						tiles[atual] = new FloorTile(xx * Jogo.tamanho, yy * Jogo.tamanho, Tile.TILE_FLOOR_TERRA);
						// areia
					}

					else if (pixels[atual] == 0xFF00FF21) {
						InimigoCaveira caveira = new InimigoCaveira(xx * Jogo.tamanho, yy * Jogo.tamanho, Jogo.tamanho,
								Jogo.tamanho, Entidade.inimigoCaveira, 3);
						Jogo.entidades.add(caveira);
						Jogo.inimigo.add(caveira);
						// inimigo caveira
					} else if (pixels[atual] == 0xFF89FFFD) {
						InimigoAlien alien = new InimigoAlien(xx * Jogo.tamanho, yy * Jogo.tamanho, Jogo.tamanho,
								Jogo.tamanho, Entidade.inimigoAlien, 6);
						Jogo.entidades.add(alien);
						Jogo.inimigo.add(alien);
					} else if (pixels[atual] == 0xFFFF0000) {
						CoracaoDeVida pack = new CoracaoDeVida(xx * Jogo.tamanho, yy * Jogo.tamanho, Jogo.tamanho,
								Jogo.tamanho, Entidade.coracaoVida);
						// pack.setMask(maskX, maskY, maskW, maskH); SE QUISER COLOCAR MASCARA
						Jogo.entidades.add(pack);
						Jogo.lifePack.add(pack);
						// vida
					} else if (pixels[atual] == 0xFFFFD800) {
						Arma arma = new Arma(xx * Jogo.tamanho, yy * Jogo.tamanho, Jogo.tamanho, Jogo.tamanho,
								Entidade.arma);
						Jogo.entidades.add(arma);
						Jogo.arma.add(arma);
						// arma
					} else if (pixels[atual] == 0xFFFF00DC) {
						Municao balas = new Municao(xx * Jogo.tamanho, yy * Jogo.tamanho, Jogo.tamanho, Jogo.tamanho,
								Entidade.municaoBalas);
						Jogo.entidades.add(balas);
						Jogo.municao.add(balas);
						// munição
					}
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isFree(int xprox, int yprox) {
		int x1 = xprox / Jogo.tamanho;
		int y1 = yprox / Jogo.tamanho;

		int x2 = (xprox + Jogo.tamanho - 1) / Jogo.tamanho;
		int y2 = yprox / Jogo.tamanho;

		int x3 = xprox / Jogo.tamanho;
		int y3 = (yprox + Jogo.tamanho - 1) / Jogo.tamanho;

		int x4 = (xprox + Jogo.tamanho - 1) / Jogo.tamanho;
		int y4 = (yprox + Jogo.tamanho - 1) / Jogo.tamanho;

		return !((tiles[x1 + (y1 * Mundo.WIDTH_WORD)] instanceof WallTile)
				|| (tiles[x2 + y2 * Mundo.WIDTH_WORD] instanceof WallTile)
				|| (tiles[x3 + y3 * Mundo.WIDTH_WORD] instanceof WallTile)
				|| (tiles[x4 + y4 * Mundo.WIDTH_WORD] instanceof WallTile));

	}

	public static void carregarFase(int level) {
		if(!Jogo.mute)
		Sons.proxFase.play();
		
		Jogo.entidades.clear();
		Jogo.inimigo.clear();
		Jogo.lifePack.clear();
		Jogo.municao.clear();
		Jogo.arma.clear();
		Jogo.balas.clear();
		Jogo.entidades = new ArrayList<Entidade>();
		Jogo.inimigo = new ArrayList<Inimigo>();
		Jogo.lifePack = new ArrayList<CoracaoDeVida>();
		Jogo.municao = new ArrayList<Municao>();
		Jogo.arma = new ArrayList<Arma>();
		Jogo.balas = new ArrayList<AtirarMunicao>();
		Jogo.spritesheet = new Spritesheet("/Spritesheet.png");
		Jogo.jogador = new Jogador(35, 29, Jogo.tamanho, Jogo.tamanho,
				Jogo.spritesheet.getSprite(0, 0, Jogo.tamanho, Jogo.tamanho));
		Jogo.entidades.add(Jogo.jogador);
		Jogo.mundo = new Mundo("/nivel" + level + ".png");
	}

	public void renderizar(Graphics g) {
		// int xstart = Camera.x >> 4;Jogo.tamanho
		int xstart = Camera.x / Jogo.tamanho;
		int ystart = Camera.y / Jogo.tamanho;

		int xfinal = xstart + (Jogo.WIDITH / Jogo.tamanho) + 1;
		int yfinal = ystart + (Jogo.HEIGHT / Jogo.tamanho) + 1;

		for (int xx = xstart; xx <= xfinal; xx++) {
			for (int yy = ystart; yy <= yfinal; yy++) {
				if (xx < 0 || yy < 0 || xx >= WIDTH_WORD || yy >= HEIGHT_WORD) {
					continue;
				}
				Tile tile = tiles[xx + (yy * WIDTH_WORD)];
				tile.renderizar(g);
			}
		}
	}

}
