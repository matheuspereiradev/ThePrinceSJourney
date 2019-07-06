package com.matheus.mundo;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Mundo {

	public Mundo(String path) {
		try {
			BufferedImage mapa=ImageIO.read(getClass().getResource(path));
			int[]pixels=new int[mapa.getWidth()*mapa.getHeight()];
			mapa.getRGB(0, 0, mapa.getWidth(), mapa.getHeight(), pixels, 0, mapa.getWidth());
			for(int i=0;i<pixels.length;i++) {
				if(pixels[i]==0xFFFFFFFF) {
					System.out.println("Branco");
				}
				if(pixels[i]==0xFFFF0000) {
					System.out.println("Vermelho");
				}
				if(pixels[i]==0xFFFFD800) {
					System.out.println("amarelo");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
