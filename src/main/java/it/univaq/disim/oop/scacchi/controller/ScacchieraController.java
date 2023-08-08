package it.univaq.disim.oop.scacchi.controller;

public class ScacchieraController {

	public static final boolean[] PRIMA_COLONNA = initColonna(0); //array di caselle della prima colonna
	public static final boolean[] SECONDA_COLONNA = initColonna(1);
	public static final boolean[] SETTIMA_COLONNA = initColonna(6);
	public static final boolean[] OTTAVA_COLONNA = initColonna(7);
	
	public static final boolean[] SECONDA_RIGA = null;
	public static final boolean[] SETTIMA_RIGA = null;
	
	public static final int NUM_CASELLE = 64;
	public static final int NUM_CASELLE_PER_RIGA = 8;

	private ScacchieraController() {
		throw new RuntimeException("Non puoi instanziarmi!");
	}
	
	private static boolean[] initColonna(int numeroColonna) {
		final boolean[] colonna = new boolean[NUM_CASELLE];
		do {
			colonna[numeroColonna] = true;
			numeroColonna += NUM_CASELLE_PER_RIGA;
		}while(numeroColonna < NUM_CASELLE);
		return colonna;
	}
	
	public static boolean casellaDisponibile(int coordinate) {
		return coordinate >= 0 && coordinate < NUM_CASELLE;
	}

}
