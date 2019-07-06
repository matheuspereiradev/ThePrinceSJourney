package com.matheus.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.matheus.game.Jogo;

public class Jogador extends Entidade {

	public boolean left, right, up, down;
	public double speed = 0.5;
	public int right_dir = 0, left_dir = 1,up_dir=2, down_dir=3;
	public int ultimoClicado=right_dir;
	private BufferedImage[] rightplayer;
	private BufferedImage[] leftplayer;
	private BufferedImage[] upplayer;
	private BufferedImage[] downplayer;
	private int index=0, frames=0,maxFrames=10,maxIndex=2;
	private boolean movimentando;

	public Jogador(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		rightplayer = new BufferedImage[3];
		leftplayer = new BufferedImage[3];
		upplayer = new BufferedImage[3];
		downplayer = new BufferedImage[3];
		for (int i = 0; i < 3; i++) {
			rightplayer[i] = Jogo.spritesheet.getSprite(48 + (i * Jogo.tamanho), 0, Jogo.tamanho, Jogo.tamanho);
		}
		for (int i = 0; i < 3; i++) {
			leftplayer[i] = Jogo.spritesheet.getSprite(96 + (i * Jogo.tamanho), 0, Jogo.tamanho, Jogo.tamanho);
		}
		for (int i = 0; i < 3; i++) {
			upplayer[i] = Jogo.spritesheet.getSprite(0 + (i * Jogo.tamanho), 16, Jogo.tamanho, Jogo.tamanho);
		}
		for (int i = 0; i < 3; i++) {
			downplayer[i] = Jogo.spritesheet.getSprite(0 + (i * Jogo.tamanho), 0, Jogo.tamanho, Jogo.tamanho);
		}
	}

	public void atualizar() {
		movimentando=false;
		if (up) {
			movimentando=true;
			ultimoClicado=up_dir;
			y -= speed;
		} else if (down) {
			movimentando=true;
			ultimoClicado=down_dir;
			y += speed;
		}
		if (left) {
			movimentando=true;
			ultimoClicado=left_dir;
			x -= speed;
		} else if (right) {
			movimentando=true;
			ultimoClicado=right_dir;
			x += speed;
		}
		//animar
		
		if(movimentando) {
			frames++;
			if(frames==maxFrames) {
				frames=0;
				index++;
				if(index>maxIndex) {
					index=0;
				}
			}
		}

	}

	public void renderizar(Graphics g) {
		if (ultimoClicado==right_dir) {
			g.drawImage(rightplayer[index], this.getX(), this.getY(), null);
		} else if (ultimoClicado==left_dir) {
			g.drawImage(leftplayer[index], this.getX(), this.getY(), null);
		}
		if (ultimoClicado==up_dir) {
			g.drawImage(upplayer[index], this.getX(), this.getY(), null);
		} else if (ultimoClicado==down_dir) {
			g.drawImage(downplayer[index], this.getX(), this.getY(), null);
		}
		
		
	}

}
