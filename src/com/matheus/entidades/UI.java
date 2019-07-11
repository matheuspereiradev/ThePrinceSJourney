package com.matheus.entidades;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class UI {
	
	public void renderizar(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString("Principe", 155, 25);
		
		g.setColor(Color.RED);
		g.fillRect(155,5, 82, 10);
		g.setColor(new Color(0,127,14));
		g.fillRect(155,5, (int) ((Jogador.vida/Jogador.MAX_LIFE)*82), 10);
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.PLAIN,10));
		g.drawString((int)Jogador.vida+"/"+Jogador.MAX_LIFE, 180, 14);
	}

}
