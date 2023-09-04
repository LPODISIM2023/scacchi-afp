package it.univaq.disim.oop.scacchi.cpu;

import java.util.concurrent.atomic.AtomicLong;

import it.univaq.disim.oop.scacchi.controller.ScacchieraController;
import it.univaq.disim.oop.scacchi.player.*;
import it.univaq.disim.oop.scacchi.scacchiera.Mossa;
import it.univaq.disim.oop.scacchi.scacchiera.Scacchiera;
import it.univaq.disim.oop.scacchi.scacchiera.TransizioneMossa;

public class Computer implements StrategiaMossa {

	private final ValutaScacchiera vs;
	private final int cercaFine;
	private long scacchieraValutate;
	private RigaTabella[] frequenzaTabella;
	private int contatoreTabella;

	public Computer(final int fine) {
		this.vs = new ScacchieraStandard();
		this.cercaFine = fine;
		scacchieraValutate = 0;
	}

	@Override
	public String toString() {
		return "COM";
	}

	@Override
	public long getScacchiereValutate() {
		return this.scacchieraValutate;
	}

	public Mossa esegui(Scacchiera scacchiera) {

		Mossa m = null;

		int massimo = Integer.MIN_VALUE;
		int minore = Integer.MAX_VALUE;
		int valoreAttuale;

		System.out.println(scacchiera.giocatoreAttuale() + "Pensa:" + this.cercaFine);

		this.frequenzaTabella = new RigaTabella[scacchiera.giocatoreAttuale().getMosseLegali().size()];
		this.scacchieraValutate = 0;
		int contatoreMossa = 1;
		int numeroMossa = scacchiera.giocatoreAttuale().getMosseLegali().size();

		// eseguaimo la mossa e iniziamo la ricorsione per scegliere la mossa successiva
		for (final Mossa mossa : scacchiera.giocatoreAttuale().getMosseLegali()) {
			final TransizioneMossa tm = scacchiera.giocatoreAttuale().mossaFatta(mossa);
			if (tm.getStatoMossa().isFatto()) {
				final RigaTabella riga = new RigaTabella(mossa);
				this.frequenzaTabella[this.contatoreTabella] = riga;
				valoreAttuale = scacchiera.giocatoreAttuale().getColore().isBianco()
						? min(tm.getInScacchiera(), this.cercaFine - 1)
						: max(tm.getInScacchiera(), this.cercaFine - 1);
				System.out.println("\t" + toString() + "anali mossa(" + contatoreMossa + "/" + numeroMossa + ")" + mossa
						+ "punteggio" + valoreAttuale + "" + this.frequenzaTabella[this.contatoreTabella]);
				this.contatoreTabella++;
				if (scacchiera.giocatoreAttuale().getColore().isBianco() && valoreAttuale >= massimo) {
					massimo = valoreAttuale;
					m = mossa;
				} else if (scacchiera.giocatoreAttuale().getColore().isNero() && valoreAttuale <= massimo) {
					minore = valoreAttuale;
					m = mossa;
				} else {
					System.out.println("\t" + toString() + "non posso eseguire mossa(" + contatoreMossa + "/"
							+ numeroMossa + ")" + mossa);
				}
				contatoreMossa++;
			}
			long t = 0;
			for (final RigaTabella riga : this.frequenzaTabella) {
				if (riga != null) {
					t += riga.getContateo();
				}
			}
		}

		return m;
	}

	// uso logica di un alberi per la gestione delle mosse fatte dal computer
	// in questo caso analizziamo tutte le possibili mosse che pu� eseguire un
	// giocatore
	// registro il loro valore (la foglia associata ad ogni mossa) di ogni singola
	// mossa e prendo quella di valore minore
	public int min(final Scacchiera scacchiera, final int fine) {
		if (fine == 0) {
			this.scacchieraValutate++;
			this.frequenzaTabella[this.contatoreTabella].incrementa();
			return this.vs.valuta(scacchiera, fine);
		}
		if (isFine(scacchiera)) {
			return this.vs.valuta(scacchiera, fine);
		}
		int minore = Integer.MAX_VALUE;
		for (final Mossa mossa : scacchiera.giocatoreAttuale().getMosseLegali()) {
			final TransizioneMossa tm = scacchiera.giocatoreAttuale().mossaFatta(mossa);
			if (tm.getStatoMossa().isFatto()) {
				final int cv = max(tm.getInScacchiera(), fine - 1);
				if (cv <= minore) {
					minore = cv;
				}
			}
		}
		return minore;
	}

	public int max(final Scacchiera scacchiera, final int fine) {
		if (fine == 0) {
			this.scacchieraValutate++;
			this.frequenzaTabella[this.contatoreTabella].incrementa();
			return this.vs.valuta(scacchiera, fine);
		}
		if (isFine(scacchiera)) {
			return this.vs.valuta(scacchiera, fine);
		}
		int maggiore = Integer.MAX_VALUE;
		for (final Mossa mossa : scacchiera.giocatoreAttuale().getMosseLegali()) {
			final TransizioneMossa tm = scacchiera.giocatoreAttuale().mossaFatta(mossa);
			if (tm.getStatoMossa().isFatto()) {
				final int cv = max(tm.getInScacchiera(), fine - 1);
				if (cv >= maggiore) {
					maggiore = cv;
				}
			}
		}
		return maggiore;
	}

	private static boolean isFine(final Scacchiera scacchiera) {
		return scacchiera.giocatoreAttuale().isInScaccoMatto() || scacchiera.giocatoreAttuale().isInScaccoMatto();
	}

	public static class RigaTabella {

		private final Mossa mossa;
		private final AtomicLong contatore;

		RigaTabella(final Mossa mossa) {
			this.contatore = new AtomicLong();
			this.mossa = mossa;
		}

		long getContateo() {
			return this.contatore.get();
		}

		void incrementa() {
			this.contatore.incrementAndGet();
		}

	}

}
