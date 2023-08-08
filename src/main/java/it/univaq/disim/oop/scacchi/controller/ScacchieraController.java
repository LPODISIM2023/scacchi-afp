package it.univaq.disim.oop.scacchi.controller;

public class ScacchieraController {

	//costanti rappresentanti le colonne della schacchiera
	public static final boolean[] PRIMA_COLONNA = iniziallizzaColonna(0); 
	public static final boolean[] SECONDA_COLONNA = iniziallizzaColonna(1);
	public static final boolean[] SETTIMA_COLONNA = iniziallizzaColonna(6);
	public static final boolean[] OTTAVA_COLONNA = iniziallizzaColonna(7);

	public static final int NUM_CASELLA = 64;
	public static final int NUM_CASELLA_PER_RIGA = 8;
	
	private ScacchieraController() {
		throw new RuntimeException("Non puoi instanziarmi!");
	}
	
	private static boolean[] iniziallizzaColonna(int numeroColonna) {
		final boolean[] colonna = new boolean[NUM_CASELLA];
		do {
			colonna[numeroColonna] = true;
			numeroColonna += NUM_CASELLA_PER_RIGA;
		}while(numeroColonna < NUM_CASELLA);
		
			return colonna;
	}

	public static boolean casellaDisponibile(final int coordinate) {
		return coordinate >= 0 && coordinate < NUM_CASELLA;
	}

}
