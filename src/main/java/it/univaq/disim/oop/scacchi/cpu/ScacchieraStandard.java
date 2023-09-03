package it.univaq.disim.oop.scacchi.cpu;

import it.univaq.disim.oop.scacchi.pezzi.Pezzo;
import it.univaq.disim.oop.scacchi.player.Giocatore;
import it.univaq.disim.oop.scacchi.scacchiera.Scacchiera;

public final class ScacchieraStandard implements ValutaScacchiera {
	
	private static final int SCACCO = 50;
	private static final int SCACCO_MATTO = 10000;
	private static final int BONUS = 100;

	public int valuta(final Scacchiera scacchiera, int fine) {
		
		return punteggioGiocatore(scacchiera, scacchiera.giocatoreBianco(), fine) -
				punteggioGiocatore(scacchiera, scacchiera.giocatoreNero(), fine);
	}

	private int punteggioGiocatore(final Scacchiera scacchiera,
			Giocatore giocatore, int fondo) {
		
		return punteggioPezzi(giocatore) + opzioni(giocatore) + scacco(giocatore) + scaccoMatto(giocatore, fondo);
	}
	
	private static int scaccoMatto(Giocatore giocatore, int fine) {
		// TODO Auto-generated method stub
		return giocatore.getAvversario().isInScaccoMatto()? SCACCO_MATTO * bonus(fine): 0;
	}

	private static int bonus(int fine) {
		return fine == 0 ?1 : BONUS * fine;
	}

	private static int scacco(final Giocatore giocatore) {
		return giocatore.getAvversario().isInScacco()? SCACCO : 0;
	}
	
	//dice il numero di mosse possibili per un giocatore da fare
	private static int opzioni(final Giocatore giocatore) {
		return giocatore.getMosseLegali().size();
	}
	
	private int punteggioPezzi(final Giocatore giocatore) {
		int punteggio = 0;
		for(final Pezzo pezzo: giocatore.getPezziAttivi()) {
			punteggio += pezzo.getValorePezzo();
		}
		return punteggio;
	}

}
