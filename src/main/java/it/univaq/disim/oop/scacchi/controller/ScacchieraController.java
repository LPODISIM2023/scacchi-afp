package it.univaq.disim.oop.scacchi.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class ScacchieraController {

	public static final boolean[] PRIMA_COLONNA = initColonna(0); // array di caselle della prima colonna
	public static final boolean[] SECONDA_COLONNA = initColonna(1);
	public static final boolean[] SETTIMA_COLONNA = initColonna(6);
	public static final boolean[] OTTAVA_COLONNA = initColonna(7);

	public static final boolean[] RANGO_OTTO = initRiga(0);
	public static final boolean[] RANGO_SETTE = initRiga(8);
	public static final boolean[] RANGO_SEI = initRiga(16);
	public static final boolean[] RANGO_CINQUE = initRiga(24);
	public static final boolean[] RANGO_QUATTRO = initRiga(32);
	public static final boolean[] RANGO_TRE = initRiga(40);
	public static final boolean[] RANGO_DUE = initRiga(48);
	public static final boolean[] RANGO_UNO = initRiga(56);

	public static final String[] NOTAZIONE_ALGEBRICA = inizializzaNotazioneAlgebrica();
	public static final Map<String, Integer> POSIZIONE_IN_COORDINATE = inizializzaPosizioneInCoordinateMappa();

	public static final int INDICE_CASELLA_INIZIALE = 0;
	public static final int NUM_CASELLE_PER_RIGA = 8;
	public static final int NUM_CASELLE = 64;

	private ScacchieraController() {
		throw new RuntimeException("Non puoi instanziarmi!");
	}

	private static String[] inizializzaNotazioneAlgebrica() {
		return new String[] { "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8", "a7", "b7", "c7", "d7", "e7", "f7", "g7",
				"h7", "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6", "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
				"a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4", "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3", "a2",
				"b2", "c2", "d2", "e2", "f2", "g2", "h2", "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1" };
	}

	private static Map<String, Integer> inizializzaPosizioneInCoordinateMappa() {
		final Map<String, Integer> posizioneInCoordinate = new HashMap<String, Integer>();
		for (int i = INDICE_CASELLA_INIZIALE; i < NUM_CASELLE; i++) {
			posizioneInCoordinate.put(NOTAZIONE_ALGEBRICA[i], i);
		}
		return ImmutableMap.copyOf(posizioneInCoordinate);
	}

	private static boolean[] initColonna(int numeroColonna) {
		final boolean[] colonna = new boolean[NUM_CASELLE];
		do {
			colonna[numeroColonna] = true;
			numeroColonna += NUM_CASELLE_PER_RIGA;
		} while (numeroColonna < NUM_CASELLE);
		return colonna;
	}

	private static boolean[] initRiga(int numeroRiga) {
		final boolean[] riga = new boolean[NUM_CASELLE];
		do {
			riga[numeroRiga] = true;
			numeroRiga++;
		} while (numeroRiga % NUM_CASELLE_PER_RIGA != 0);
		return riga;
	}

	public static boolean casellaDisponibile(int coordinate) {
		return coordinate >= 0 && coordinate < NUM_CASELLE;
	}

	public int getCoordinateInPosizione(final String posizione) {
		return POSIZIONE_IN_COORDINATE.get(posizione);
	}

	public static String getPosizioneInCoordinate(final int coordinate) {
		return NOTAZIONE_ALGEBRICA[coordinate];
	}

}
