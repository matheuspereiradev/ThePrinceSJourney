package com.matheus.game;

import java.applet.Applet;
import java.applet.AudioClip;


public class Sons {

	private AudioClip song;
	public static final Sons musica=new Sons("/Windless Slopes.wav");
	public static final Sons danoSong=new Sons("/hurt.wav");

	public Sons(String path) {
		try {
			song = Applet.newAudioClip(getClass().getResource(path));
		} catch (Throwable e) {
			System.out.println(e);
		}
	}

	public void play() {
		try {
			new Thread() {
				public void run() {
					song.play();
				}
			}.start();
		} catch (Throwable e) {
			System.out.println(e);
		}
	}
	
	public void loop() {
		try {
			new Thread() {
				public void run() {
					song.loop();
				}
			}.start();
		} catch (Throwable e) {
			System.out.println(e);
		}
	}
}
