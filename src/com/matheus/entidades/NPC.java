package com.matheus.entidades;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.matheus.game.Jogo;
import com.matheus.mundo.Camera;

public class NPC extends Entidade {

	public static BufferedImage npc_pirata_1=Jogo.spritesheet.getSprite(64, 128, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage npc_pirata_2=Jogo.spritesheet.getSprite(80, 128, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage npc_agente_1=Jogo.spritesheet.getSprite(64, 144, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage npc_agente_2=Jogo.spritesheet.getSprite(80, 144, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage npc_cabecao_1=Jogo.spritesheet.getSprite(64, 160, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage npc_cabecao_2=Jogo.spritesheet.getSprite(80, 160, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage npc_colorido_1=Jogo.spritesheet.getSprite(0, 128, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage npc_colorido_2=Jogo.spritesheet.getSprite(16, 128, Jogo.tamanho, Jogo.tamanho);
	
	
	public boolean exibirDialogo=false;
	public boolean exibindo=false;
	public String frases[];
	int frames = 0, index = 0;

	public NPC(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		this.depth = 3;
		frases=new String[5];
		frases[0]="Oi eu sou goku";
	}

	public void atualizar() {
		
		if (calcularDistancia(Jogo.jogador.getX(), this.getX(), Jogo.jogador.getY(), this.getY())<8) {
			if (!exibindo) {
				exibirDialogo=true;
				exibindo=true;
				}
		}else {
			exibirDialogo=false;
			exibindo=false;
		}

	}

	public void renderizar(Graphics g) {
		super.renderizar(g);
		if (exibirDialogo) {
			g.setColor(Color.yellow);
			g.drawString(frases[0], 80, 80);
			
		}
	}
}
