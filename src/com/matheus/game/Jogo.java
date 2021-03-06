package com.matheus.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import com.matheus.entidades.*;
import com.matheus.graficos.Spritesheet;
import com.matheus.graficos.UI;
import com.matheus.mundo.*;

public class Jogo extends Canvas implements Runnable, KeyListener, MouseListener {

	public static Font fontCelticMd;

	private static final long serialVersionUID = 1L;
	public static final int tamanho = 16;
	private Thread thread;
	private boolean isRunning;
	public static JFrame frame;
	public static final int WIDITH = 300, HEIGHT = 220, SCALE = 3;
	private int fpsJogo = 0;
	private BufferedImage background;
	public static List<Entidade> entidades;
	public static List<NPC> npc;
	public static List<Inimigo> inimigo;
	public static List<CoracaoDeVida> lifePack;
	public static List<Municao> municao;
	public static List<Arma> arma;
	public static List<AtirarMunicao> balas;
	public static List<BlocoDeDano> lava;
	public static List<InimigoMorte> morte;
	public static List<InimigoGiganteDeAco> gigantes;
	public static Spritesheet spritesheet;
	public static Jogador jogador;
	public static Mundo mundo;
	public static Random rand;
	public static int fase = 1, maxFases = 6;
	public static String status = "MENU";
	public boolean exibirMensagemGameOver = false;
	private int framesGameOver = 0, maxGameOver = 20;
	private boolean restartJogo = false;
	public static boolean mute = true;
	public boolean saveGame = false;
	public Menu menu;
	public UI ui;
	public int[] pixels, luzPixels;
	public BufferedImage luz;
	public static BufferedImage minimapa;
	public static boolean exibirMiniMap = false;
	public static int[] minimapapixels;
	public static boolean fullScreen = false;// eu defino se quero o jogo full screan ou nao
	// cutscene
	public static boolean pularCena;
	public static int entrada = 1;
	public static int comecar = 2;
	public static int jogando = 3;
	public static int estado_cena = entrada;

	public Jogo() {
		if (!mute) {
			Sons.musica.loop();
		}
		rand = new Random();
		addKeyListener(this);
		addMouseListener(this);
		if (fullScreen) {
			setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		} else {
			setPreferredSize(new Dimension(WIDITH * SCALE, HEIGHT * SCALE));// tamanho da janela
		}
		iniciarFrame();
		InputStream steam = ClassLoader.getSystemClassLoader().getResourceAsStream("fonts/coolpbl.ttf");
		iniciarFont(steam);
		ui = new UI();
		background = new BufferedImage(WIDITH, HEIGHT, BufferedImage.TYPE_INT_RGB);// imagem do fundo

		// luz
		try {
			luz = ImageIO.read(getClass().getResource("/luz.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		luzPixels = new int[luz.getWidth() * luz.getHeight()];
		luz.getRGB(0, 0, luz.getWidth(), luz.getHeight(), luzPixels, 0, luz.getWidth());

		// fim da luz

		pixels = ((DataBufferInt) background.getRaster().getDataBuffer()).getData();
		iniciarJogo();
		menu = new Menu("/Banner.png");
		// iniciando minimapa
		minimapa = new BufferedImage(Mundo.WIDTH_WORD, Mundo.HEIGHT_WORD, BufferedImage.TYPE_INT_RGB);
		minimapapixels = ((DataBufferInt) minimapa.getRaster().getDataBuffer()).getData();
	}

	public static void main(String[] args) {
		Jogo jogo = new Jogo();
		jogo.iniciar();
	}

	public void iniciarFont(InputStream inputsteam) {
		try {
			fontCelticMd = Font.createFont(Font.TRUETYPE_FONT, inputsteam).deriveFont(30f);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void iniciarJogo() {

		npc = new ArrayList<NPC>();
		entidades = new ArrayList<Entidade>();
		inimigo = new ArrayList<Inimigo>();
		lifePack = new ArrayList<CoracaoDeVida>();
		municao = new ArrayList<Municao>();
		arma = new ArrayList<Arma>();
		balas = new ArrayList<AtirarMunicao>();
		lava = new ArrayList<BlocoDeDano>();
		morte = new ArrayList<InimigoMorte>();
		gigantes = new ArrayList<InimigoGiganteDeAco>();
		spritesheet = new Spritesheet("/Spritesheet.png");
		jogador = new Jogador(0, 0, 16, 16, spritesheet.getSprite(0, 0, tamanho, tamanho));
		entidades.add(jogador);
		mundo = new Mundo("/nivel1.png");

	}

	public void iniciarFrame() {
		frame = new JFrame("The Prince's Journey v.2.0 by matheuslimadev.com");
		frame.add(this);
		if (fullScreen) {
			frame.setUndecorated(true);// tela cheia para tirar os bot�es e barra supeior
		}
		frame.setResizable(false);
		frame.pack();
		Image imageIcon = null;
		try {
			imageIcon = ImageIO.read(getClass().getResource("/icone.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame.setIconImage(imageIcon);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image imagem = toolkit.getImage(getClass().getResource("/seta.png"));
		Cursor c = toolkit.createCustomCursor(imagem, new Point(0, 0), "img");
		frame.setCursor(c);

		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void atualizar() {
		if (status.equals("NORMAL")) {

			if (this.saveGame) {
				this.saveGame = false;
				String[] opt1 = { "level", "vida" };
				int[] opt2 = { fase, (int) jogador.vida };
				Salvar.salvarJogo(opt1, opt2, 19);
				System.out.println("Salvo");
			}

			restartJogo = false;

			if (Jogo.estado_cena == Jogo.jogando) {
				for (int i = 0; i < entidades.size(); i++) {
					Entidade e = entidades.get(i);
					e.atualizar();
				}
				for (int i = 0; i < npc.size(); i++) {
					NPC e = npc.get(i);
					e.atualizar();
				}
			} else {
				if (Jogo.estado_cena == Jogo.entrada) {
					if (Jogo.jogador.getX() < 200) {
						Jogo.jogador.x++;
						Jogo.jogador.ultimoClicado = Jogo.jogador.right_dir;
						Jogo.jogador.frames++;
						if (Jogo.jogador.frames == Jogo.jogador.maxFrames) {
							Jogo.jogador.frames = 0;
							Jogo.jogador.index++;
							if (Jogo.jogador.index > Jogo.jogador.maxIndex) {
								Jogo.jogador.index = 0;
							}
						}
						Camera.x = Camera.clamp(Jogo.jogador.getX() - (Jogo.WIDITH / 2),
								Mundo.WIDTH_WORD * Jogo.tamanho - Jogo.WIDITH, 0);
						Camera.y = Camera.clamp(Jogo.jogador.getY() - (Jogo.HEIGHT / 2),
								Mundo.HEIGHT_WORD * Jogo.tamanho - Jogo.HEIGHT, 0);
					} else {
						Jogo.estado_cena = comecar;
					}
				} else if (Jogo.estado_cena == comecar) {
					
					if (pularCena) {
						pularCena=false;
						Jogo.estado_cena = jogando;
					}
				}
			}

			// renderizar as balas na tela pq elas nao esta em entidades
			for (int i = 0; i < balas.size(); i++) {
				balas.get(i).atualizar();
			}
			ui.atualizar();
			if (inimigo.isEmpty()) {
				fase++;
				if (fase > maxFases) {
					System.exit(0);
				}

				Mundo.carregarFase(fase);
			}
		} else if (status.equals("GAME_OVER")) {
			framesGameOver++;
			if (framesGameOver == maxGameOver) {
				framesGameOver = 0;
				exibirMensagemGameOver = !exibirMensagemGameOver;

			}
			if (restartJogo) {
				restartJogo = false;
				fase = 1;
				Mundo.carregarFase(fase);
				status = "NORMAL";
			}
		} else if (status.equals("MENU")) {
			menu.atualizar();
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

		/* renderiza��o do jogo */
		// Graphics2D g2 = (Graphics2D) g;
		mundo.renderizar(g);

		Collections.sort(entidades, Entidade.entidadeSorter);

		for (int i = 0; i < entidades.size(); i++) {
			Entidade e = entidades.get(i);
			e.renderizar(g);
		}
		for (int i = 0; i < npc.size(); i++) {
			NPC n = npc.get(i);
			n.renderizar(g);
		}
		for (int i = 0; i < balas.size(); i++) {
			balas.get(i).renderizar(g);
		}
		/**/
		g.setColor(Color.YELLOW);
		g.setFont(new Font("Arial", Font.PLAIN, 10));
		g.drawString(String.valueOf(fpsJogo), 225, 10);
		if (Jogo.estado_cena == comecar) {
			g.setColor(Color.black);
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			g.drawString("Clique c para come�ar", 60, 130);
		}
		// aplicarLuz();

		ui.renderizar(g);

		g.dispose();// limpar dados da imagem que nao foram usados
		g = bs.getDrawGraphics();
		// desenharRetangulo(40,40);

		if (fullScreen) {
			g.drawImage(background, 0, 0, (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
					(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight(), null);
		} else {
			g.drawImage(background, 0, 0, WIDITH * SCALE, HEIGHT * SCALE, null);
			// TELA COMUM
		}
		// aqui para ficar em cima da imagem de background
		g.setFont(new Font("Arial", Font.BOLD, 25));
		g.setColor(Color.WHITE);
		g.drawString("Muni��o: " + Jogo.jogador.numeroDeBalas, 36, 72);
		g.drawString("Vida:" + (int) Jogo.jogador.vida + "/" + Jogador.MAX_LIFE, 200, 72);
		if (exibirMiniMap) {
			Mundo.rederizarMiniMap();
			g.drawImage(minimapa, ((WIDITH * SCALE) - (Mundo.WIDTH_WORD * 5) - 30), 27, Mundo.WIDTH_WORD * 5,
					Mundo.HEIGHT_WORD * 5, null);
		}
		if (status == "GAME_OVER") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0, 0, 0, 100));
			g2.fillRect(0, 0, WIDITH * SCALE, HEIGHT * SCALE);
			g2.setColor(new Color(255, 255, 255, 255));
			g2.setFont(new Font("arial", Font.BOLD, 50));
			g2.drawString("Game over", 230, (HEIGHT * SCALE) / 2);
			if (exibirMensagemGameOver) {
				g2.setFont(new Font("arial", Font.PLAIN, 25));
				g2.drawString("Pressione a tecla R para reiniciar", 190, ((HEIGHT * SCALE) / 2) + 50);
			}
		} else if (status.equals("MENU")) {
			menu.renderizar(g);
		}

		bs.show();
	}

	public void desenharRetangulo(int xOff, int yOff) {
		for (int xx = 0; xx < 32; xx++) {
			for (int yy = 0; yy < 32; yy++) {
				int offX = xx + xOff;
				int offY = yy + yOff;
				if (offX < 0 || offY < 0 || offX > WIDITH || offY > HEIGHT)
					continue;
				pixels[offX + (offY * WIDITH)] = 0xFFFF0000;
			}
		}
	}

	public void aplicarLuz() {
		for (int xx = 0; xx < Jogo.WIDITH; xx++) {
			for (int yy = 0; yy < Jogo.HEIGHT; yy++) {
				if (luzPixels[xx + (yy * WIDITH)] == 0xFFFFFFFF) {
					pixels[xx + (yy * WIDITH)] = 0xFF000000;
				}
			}
		}
	}

	@Override
	public void run() {
		requestFocus();// FOCO AUTOMATICO NA JANELA
		long lastTime = System.nanoTime();// ultima vez que foi executada a atualia��o
		double amountOfTicks = 60.0;// quantidade de atualiza��es por segundo
		double ns = 1000000000 / amountOfTicks;// "constante" do momento certo do update do jogo para ficar na
												// quantidade de fps descritas no amountOfTicks
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		while (isRunning) {
			long now = System.nanoTime();// tempo atual
			delta += (now - lastTime) / ns;
			lastTime = now;// substitui a ultima execu��o do while pelo tempo atual

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
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			if (status.equals("NORMAL"))
				jogador.up = true;
			else
				menu.up = true;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			if (status.equals("NORMAL"))
				jogador.down = true;
			else
				menu.down = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			jogador.right = true;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			jogador.left = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			jogador.atirando = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_R) {
			restartJogo = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			menu.enter = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			Menu.pausa = true;
			status = "MENU";
		}
		if (e.getKeyCode() == KeyEvent.VK_Z) {
			if (status == "NORMAL")
				this.saveGame = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_C) {
			if (Jogo.estado_cena == comecar)
				pularCena = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_J) {
			exibirMiniMap = !exibirMiniMap;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			jogador.up = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			jogador.down = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			jogador.right = false;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			jogador.left = false;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		menu.clickDoMouse = true;
		menu.mouseX = e.getX();
		menu.mouseY = e.getY();

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}