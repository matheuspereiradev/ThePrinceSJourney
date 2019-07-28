package com.matheus.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.matheus.game.Jogo;
import com.matheus.mundo.Camera;

public class NPC extends Entidade {

	public static BufferedImage npc_pirata[];
	int frames = 0, index = 0;

	public NPC(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		this.depth = 3;
		npc_pirata = new BufferedImage[3];
		npc_pirata[0] = Jogo.spritesheet.getSprite(64, 128, 16, 16);
		npc_pirata[1] = Jogo.spritesheet.getSprite(80, 128, 16, 16);
	}

	public void atualizar() {
		frames++;
		if (frames == 80) {
			frames = 0;
			index++;
			if (index == 2) {
				index = 0;
			}

		}

	}

	public void renderizar(Graphics g) {
		g.drawImage(npc_pirata[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
}
