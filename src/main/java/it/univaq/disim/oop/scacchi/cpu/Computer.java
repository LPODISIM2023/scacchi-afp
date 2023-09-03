package it.univaq.disim.oop.scacchi.cpu;

import it.univaq.disim.oop.scacchi.scacchiera.Mossa;
import it.univaq.disim.oop.scacchi.scacchiera.Scacchiera;
import it.univaq.disim.oop.scacchi.scacchiera.TransizioneMossa;

public class Computer implements StrategiaMossa {

	private final ValutaScacchiera vs;
	private final int cercaFine;

	public Computer(final int fine) {
		this.vs = new ScacchieraStandard();
		this.cercaFine = fine;
	}

	@Override
	public String toString() {
		return "COM";
	}

	public Mossa esegui(Scacchiera scacchiera) {

		Mossa m = null;

		int valoreMassimo = Integer.MIN_VALUE;
		int valoreMinore = Integer.MIN_VALUE;
		int valoreAttuale;

		System.out.println(scacchiera.giocatoreAttuale() + "Pensa:" + this.cercaFine);

		// eseguaimo la mossa e iniziamo la ricorsione per scegliere la mossa successiva
		for (final Mossa mossa : scacchiera.giocatoreAttuale().getMosseLegali()) {
			final TransizioneMossa tm = scacchiera.giocatoreAttuale().mossaFatta(mossa);
			if (tm.getStatoMossa().isFatto()) {
				valoreAttuale = scacchiera.giocatoreAttuale().getColore().isBianco()
						? min(tm.getInScacchiera(), this.cercaFine - 1)
						: max(tm.getInScacchiera(), this.cercaFine - 1);
				if (scacchiera.giocatoreAttuale().getColore().isBianco() && valoreAttuale >= valoreMassimo) {
					valoreMassimo = valoreAttuale;
					m = mossa;
				} else if (scacchiera.giocatoreAttuale().getColore().isNero() && valoreAttuale <= valoreMinore) {
					valoreMinore = valoreAttuale;
					m = mossa;
				}
			}
		}
		return m;
	}

	// uso logica di un alberi per la gestione delle mosse fatte dal computer
	// in questo caso analizziamo tutte le possibili mosse che può eseguire un
	// giocatore
	// registro il loro valore (la foglia associata ad ogni mossa) di ogni singola
	// mossa e prendo quella di valore minore
	public int min(final Scacchiera scacchiera, final int fine) {
		if (fine == 0 || isFine(scacchiera)) {
			return this.vs.valuta(scacchiera, fine);
		}
		if (isFine(scacchiera)) {
			return this.vs.valuta(scacchiera, fine);
		}
		int valoreMinore = Integer.MAX_VALUE;
		for (final Mossa mossa : scacchiera.giocatoreAttuale().getMosseLegali()) {
			final TransizioneMossa tm = scacchiera.giocatoreAttuale().mossaFatta(mossa);
			if (tm.getStatoMossa().isFatto()) {
				final int cv = max(tm.getInScacchiera(), fine - 1);
				if (cv <= valoreMinore) {
					valoreMinore = cv;
				}
			}
		}
		return valoreMinore;
	}

	public int max(final Scacchiera scacchiera, final int fine) {
		if (fine == 0 || isFine(scacchiera)) {
			return this.vs.valuta(scacchiera, fine);
		}
		if (isFine(scacchiera)) {
			return this.vs.valuta(scacchiera, fine);
		}
		int valoreMaggiore = Integer.MAX_VALUE;
		for (final Mossa mossa : scacchiera.giocatoreAttuale().getMosseLegali()) {
			final TransizioneMossa tm = scacchiera.giocatoreAttuale().mossaFatta(mossa);
			if (tm.getStatoMossa().isFatto()) {
				final int cv = max(tm.getInScacchiera(), fine - 1);
				if (cv >= valoreMaggiore) {
					valoreMaggiore = cv;
				}
			}
		}
		return valoreMaggiore;
	}

	private static boolean isFine(final Scacchiera scacchiera) {
		return scacchiera.giocatoreAttuale().isInScaccoMatto() || scacchiera.giocatoreAttuale().isInScaccoMatto();
	}

}
