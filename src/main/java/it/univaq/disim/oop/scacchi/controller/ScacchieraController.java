package it.univaq.disim.oop.scacchi.controller;

public class ScacchieraController {

	public static final boolean[] PRIMA_COLONNA = null; //array di caselle della prima colonna
	public static final boolean[] SECONDA_COLONNA = null;
	public static final boolean[] SETTIMA_COLONNA = null;
	public static final boolean[] OTTAVA_COLONNA = null;

	private ScacchieraController() {
		throw new RuntimeException("Non puoi instanziarmi!");
	}
	
	public static boolean casellaDisponibile(int coordinate) {
		return coordinate >= 0 && coordinate < 64;
	}

}
