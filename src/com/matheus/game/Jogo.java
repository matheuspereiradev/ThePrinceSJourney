package com.matheus.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.matheus.entidades.Entidade;
import com.matheus.entidades.Jogador;
import com.matheus.graficos.Spritesheet;
import com.matheus.mundo.Mundo;

public class Jogo extends Canvas implements Runnable,KeyListener {

	private static final long serialVersionUID = 1L;
	public static final int tamanho=16;
	private Thread thread;
	private boolean isRunning;
	public static JFrame frame;
	private final int WIDITH = 240, HEIGHT = 160, SCALE = 3;
	private int fpsJogo=0;
	private BufferedImage background;
	public List<Entidade> entidades;
	public static Spritesheet spritesheet;
	private Jogador jogador;
	public static Mundo mundo;


	public Jogo() {
		addKeyListener(this);
		setPreferredSize(new Dimension(WIDITH * SCALE, HEIGHT * SCALE));// tamanho da janela
		iniciarFrame();
		mundo=new Mundo("/mapa.png");
		background = new BufferedImage(WIDITH, HEIGHT, BufferedImage.TYPE_INT_RGB);// imagem do fundo
		entidades=new ArrayList<Entidade>();
		spritesheet=new Spritesheet("/Spritesheet.png");
		jogador=new Jogador(35, 29, tamanho, tamanho, spritesheet.getSprite(0, 0, tamanho, tamanho));
		entidades.add(jogador);
	}

	public static void main(String[] args) {
		Jogo jogo = new Jogo();
		jogo.iniciar();
	}

	public void iniciarFrame() {
		frame = new JFrame("The Prince's Journey");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void atualizar() {
		for(int i=0;i<entidades.size();i++) {
			Entidade e= entidades.get(i);
			e.atualizar();
		}
	}

	public void renderizar() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = background.getGraphics();
		g.setColor(new Color(0,200,0));
		g.fillRect(0, 0, WIDITH, HEIGHT);

		g.setColor(Color.YELLOW);
		g.setFont(new Font("Arial", Font.PLAIN, 14));
		g.drawString(String.valueOf(fpsJogo), 0, 10);

		/* renderização do jogo */
		//Graphics2D g2 = (Graphics2D) g;
		for(int i=0;i<entidades.size();i++) {
			Entidade e= entidades.get(i);
			e.renderizar(g);
		}
		/**/

		g.dispose();// limpar dados da imagem que nao foram usados
		g = bs.getDrawGraphics();
		g.drawImage(background, 0, 0, WIDITH * SCALE, HEIGHT * SCALE, null);
		bs.show();
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();// ultima vez que foi executada a atualiação
		double amountOfTicks = 60.0;// quantidade de atualizações por segundo
		double ns = 1000000000 / amountOfTicks;// "constante" do momento certo do update do jogo para ficar na
												// quantidade de fps descritas no amountOfTicks
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		while (isRunning) {
			long now = System.nanoTime();// tempo atual
			delta += (now - lastTime) / ns;
			lastTime = now;// substitui a ultima execução do while pelo tempo atual

			if (delta >= 1) {
				atualizar();
				renderizar();
				frames++;
				delta--;
			}

			if (System.currentTimeMillis() - timer >= 1000) {
				fpsJogo=frames;
				frames = 0;
				timer = System.currentTimeMillis();// atualiza o tempo para o tempo atual
				// ou timer+=1000; para dizer que se passaram 1000 milesegundos desde o valor
				// inicial do timer

			}

		}
		parar();

	}

	public synchronized void iniciar() {
		thread = new Thread(this);
		thread.start();// chama o run da thread
		isRunning = true;

	}

	public synchronized void parar() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_UP) {
			jogador.up=true;
		}else if(e.getKeyCode()==KeyEvent.VK_DOWN) {
			jogador.down=true;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
			jogador.right=true;
		}else if(e.getKeyCode()==KeyEvent.VK_LEFT) {
			jogador.left=true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_UP) {
			jogador.up=false;
		}else if(e.getKeyCode()==KeyEvent.VK_DOWN) {
			jogador.down=false;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
			jogador.right=false;
		}else if(e.getKeyCode()==KeyEvent.VK_LEFT) {
			jogador.left=false;
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}