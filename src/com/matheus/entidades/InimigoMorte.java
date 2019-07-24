package com.matheus.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.matheus.game.Jogo;
import com.matheus.mundo.Camera;
import com.matheus.mundo.Mundo;

public class InimigoMorte extends Inimigo{
	
	private double speed = 1.2;
	private int index = 0, frames = 0, maxFrames = 10, maxIndex = 2;
	private BufferedImage[] inimigoMorte;

	public InimigoMorte(double x, double y, int width, int height, BufferedImage sprite, int vida) {
		super(x, y, width, height, sprite, vida);
		inimigoMorte=new BufferedImage[3];
		inimigoMorte[0] = Jogo.spritesheet.getSprite(176, 16, Jogo.tamanho, Jogo.tamanho);
		inimigoMorte[1] = Jogo.spritesheet.getSprite(176, 32, Jogo.tamanho, Jogo.tamanho);
		inimigoMorte[2] = Jogo.spritesheet.getSprite(192, 16, Jogo.tamanho, Jogo.tamanho);
	}
	
	public void atualizar() {
		// movimentação
		if (!Inimigo.colisaoComJogador(this.getX(), this.getY(), this.maskX, this.maskY, this.maskW, this.maskH)) {
			if (Jogo.rand.nextInt(100) < 30) {

				if (((int) x < Jogo.jogador.getX()) && (Mundo.isFree((int) (x + speed), this.getY()))) {
					x += speed;
				} else if ((int) x > Jogo.jogador.getX() && Mundo.isFree((int) (x - speed), this.getY())) {
					x -= speed;
				}

				if ((int) y < Jogo.jogador.getY() && Mundo.isFree(this.getX(), (int) (y + speed))) {
					y += speed;
				} else if ((int) y > Jogo.jogador.getY() && Mundo.isFree(this.getX(), (int) (y - speed))) {
					y -= speed;
				}
			}
		} else {
			Inimigo.testarAtaqueNoPlayer(5);// aqui chama o metodo e passa a probabilidade de o ataque dele acertar
		}

		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			if (index > maxIndex) {
				index = 0;
			}
		}
		colisaoComBala();
		verificarVida();
		if(sofrendoDano) {
			currentDano++;
			if(currentDano==danoFrames) {
				currentDano=0;
				sofrendoDano=false;
			}
		}
	}
	
	public void renderizar(Graphics g) {
		if(!sofrendoDano) {
			g.drawImage(inimigoMorte[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}else {
			g.drawImage(inimigoMorteDano, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		
	}

}
