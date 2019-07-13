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
import java.util.Random;
import javax.swing.JFrame;

import com.matheus.entidades.*;
import com.matheus.graficos.Spritesheet;
import com.matheus.mundo.Mundo;

public class Jogo extends Canvas implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;
	public static final int tamanho = 16;
	private Thread thread;
	private boolean isRunning;
	public static JFrame frame;
	public static final int WIDITH = 240, HEIGHT = 160, SCALE = 3;
	private int fpsJogo = 0;
	private BufferedImage background;
	public static List<Entidade> entidades;
	public static List<Inimigo> inimigo;
	public static List<CoracaoDeVida> lifePack;
	public static List<Municao> municao;
	public static List<Arma> arma;
	public static List<AtirarMunicao> balas;
	public static Spritesheet spritesheet;
	public static Jogador jogador;
	public static Mundo mundo;
	public static Random rand;
	private int fase=1,maxFases=3;
	public UI ui;

	public Jogo() {
		rand = new Random();
		addKeyListener(this);
		setPreferredSize(new Dimension(WIDITH * SCALE, HEIGHT * SCALE));// tamanho da janela
		iniciarFrame();
		ui = new UI();
		background = new BufferedImage(WIDITH, HEIGHT, BufferedImage.TYPE_INT_RGB);// imagem do fundo
		iniciarJogo();

	}

	public static void main(String[] args) {
		Jogo jogo = new Jogo();
		jogo.iniciar();
	}

	public static void iniciarJogo() {
		entidades = new ArrayList<Entidade>();
		inimigo = new ArrayList<Inimigo>();
		lifePack = new ArrayList<CoracaoDeVida>();
		municao = new ArrayList<Municao>();
		arma = new ArrayList<Arma>();
		balas = new ArrayList<AtirarMunicao>();
		spritesheet = new Spritesheet("/Spritesheet.png");
		jogador = new Jogador(35, 29, tamanho, tamanho, spritesheet.getSprite(0, 0, tamanho, tamanho));
		entidades.add(jogador);
		mundo = new Mundo("/nivel1.png");
	}

	public void iniciarFrame() {
		frame = new JFrame("The Prince's Journey v.1.0 by matheuslimadev.com");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void atualizar() {
		for (int i = 0; i < entidades.size(); i++) {
			Entidade e = entidades.get(i);
			e.atualizar();
		}
		// renderizar as balas na tela pq elas nao esta em entidades
		for (int i = 0; i < balas.size(); i++) {
			balas.get(i).atualizar();
		}
		if(inimigo.isEmpty()) {
			fase++;
			if(fase>maxFases) {
				System.exit(0);
			}
			String faseCarregar="nivel"+fase+".png";
			Mundo.carregarFase(faseCarregar);
		}
	}

	public void renderizar() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = background.getGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WIDITH, HEIGHT);

		/* renderização do jogo */
		// Graphics2D g2 = (Graphics2D) g;
		mundo.renderizar(g);
		for (int i = 0; i < entidades.size(); i++) {
			Entidade e = entidades.get(i);
			e.renderizar(g);
		}
		for (int i = 0; i < balas.size(); i++) {
			balas.get(i).renderizar(g);
		}
		/**/
		g.setColor(Color.YELLOW);
		g.setFont(new Font("Arial", Font.PLAIN, 10));
		g.drawString(String.valueOf(fpsJogo), 225, 10);

		ui.renderizar(g);

		g.dispose();// limpar dados da imagem que nao foram usados
		g = bs.getDrawGraphics();
		g.drawImage(background, 0, 0, WIDITH * SCALE, HEIGHT * SCALE, null);
		// aqui para ficar em cima da imagem de background
		g.setFont(new Font("Arial", Font.BOLD, 25));
		g.setColor(Color.WHITE);
		g.drawString("Munição: " + Jogo.jogador.numeroDeBalas, 36, 70);
		g.drawString((int) Jogo.jogador.vida + "/" + Jogador.MAX_LIFE, 120, 40);

		bs.show();
	}

	@Override
	public void run() {
		requestFocus();// FOCO AUTOMATICO NA JANELA
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
				fpsJogo = frames;
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
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			jogador.up = true;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			jogador.down = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			jogador.right = true;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			jogador.left = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			jogador.atirando = true;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			jogador.up = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			jogador.down = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			jogador.right = false;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			jogador.left = false;
		}

		
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}