package com.matheus.mundo;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.matheus.entidades.*;
import com.matheus.game.Jogo;

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
					tiles[atual] = new FloorTile(xx * Jogo.tamanho, yy * Jogo.tamanho, Tile.TILE_FLOOR);// padrão é ser chão
					if (pixels[atual] == 0xFF000000) {
						tiles[atual] = new FloorTile(xx * Jogo.tamanho, yy * Jogo.tamanho, Tile.TILE_FLOOR);
						// chao
					}
					else if (pixels[atual] == 0xFFFFFFFF) {
						tiles[atual] = new WallTile(xx * Jogo.tamanho, yy * Jogo.tamanho, Tile.TILE_WALL);
						// parede
					} else if (pixels[atual] == 0xFF2A00FF) {
						Jogo.jogador.setX(xx * Jogo.tamanho);
						Jogo.jogador.setY(yy * Jogo.tamanho);
						// Jogador
					}else if(pixels[atual]==0xFFBC7BF2) {
						tiles[atual] = new FloorTile(xx * Jogo.tamanho, yy * Jogo.tamanho, Tile.TILE_FLOOR_TERRA);
					}
					
					else if (pixels[atual] == 0xFF00FF21) {
						Jogo.entidades.add(new InimigoCaveira(xx * Jogo.tamanho, yy * Jogo.tamanho, Jogo.tamanho, Jogo.tamanho, Entidade.inimigoCaveira));
						// inimigo caveira
					} else if(pixels[atual]==0xFF89FFFD) {
						InimigoAlien alien=new InimigoAlien(xx * Jogo.tamanho, yy * Jogo.tamanho, Jogo.tamanho, Jogo.tamanho, Entidade.inimigoAlien);
						Jogo.entidades.add(alien);
						Jogo.inimigo.add(alien);
					} 
					else if (pixels[atual] == 0xFFFF0000) {
						CoracaoDeVida pack=new CoracaoDeVida(xx * Jogo.tamanho, yy * Jogo.tamanho, Jogo.tamanho, Jogo.tamanho, Entidade.coracaoVida);
						//pack.setMask(maskX, maskY, maskW, maskH); SE QUISER COLOCAR MASCARA
						Jogo.entidades.add(pack);
						Jogo.lifePack.add(pack);
						// vida
					} else if (pixels[atual] == 0xFFFFD800) {
						Jogo.entidades.add(new Arma(xx * Jogo.tamanho, yy * Jogo.tamanho, Jogo.tamanho, Jogo.tamanho, Entidade.arma));
						// arma
					} else if (pixels[atual] == 0xFFFF00DC) {
						Municao balas=new Municao(xx * Jogo.tamanho, yy * Jogo.tamanho, Jogo.tamanho, Jogo.tamanho, Entidade.municaoBalas);
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

	public static boolean isFree(int xprox,int yprox) {
		int x1=xprox/Jogo.tamanho;
		int y1=yprox/Jogo.tamanho;
		
		int x2=(xprox+Jogo.tamanho-1)/Jogo.tamanho;
		int y2=yprox/Jogo.tamanho;
		
		int x3=xprox/Jogo.tamanho;
		int y3=(yprox+Jogo.tamanho-1)/Jogo.tamanho;
		
		int x4=(xprox+Jogo.tamanho-1)/Jogo.tamanho;
		int y4=(yprox+Jogo.tamanho-1)/Jogo.tamanho;
		
		
		return !((tiles[x1+(y1*Mundo.WIDTH_WORD)] instanceof WallTile)||
				(tiles[x2+y2*Mundo.WIDTH_WORD] instanceof WallTile)||
				(tiles[x3+y3*Mundo.WIDTH_WORD] instanceof WallTile)||
				(tiles[x4+y4*Mundo.WIDTH_WORD] instanceof WallTile)
				);
		
	}

	public void renderizar(Graphics g) {
		//int xstart = Camera.x >> 4;Jogo.tamanho
		int xstart = Camera.x/Jogo.tamanho;
		int ystart = Camera.y/Jogo.tamanho;

		int xfinal = xstart + (Jogo.WIDITH/Jogo.tamanho);
		int yfinal = ystart + (Jogo.HEIGHT/Jogo.tamanho);

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
