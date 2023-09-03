package it.univaq.disim.oop.scacchi.player;

import java.util.*;
import com.google.common.collect.ImmutableList;

import it.univaq.disim.oop.scacchi.pezzi.Pezzo;
import it.univaq.disim.oop.scacchi.pezzi.Re;
import it.univaq.disim.oop.scacchi.scacchiera.Mossa;
import it.univaq.disim.oop.scacchi.scacchiera.Mossa.StatoMossa;
import it.univaq.disim.oop.scacchi.scacchiera.Scacchiera;
import it.univaq.disim.oop.scacchi.scacchiera.TransizioneMossa;

public abstract class Giocatore {

	protected final Scacchiera scacchiera;
	protected final Re giocatoreRe;
	protected final Collection<Mossa> mosseLegali;
	private final boolean isInScacco;

	Giocatore(final Scacchiera scacchiera, final Collection<Mossa> mosseLegali,
			final Collection<Mossa> mosseAvversario) {

		this.scacchiera = scacchiera;
		this.giocatoreRe = stabilireRe();
		this.mosseLegali = ImmutableList.copyOf(mosseLegali);
		this.isInScacco = !Giocatore.calcolaAttaccoSuCasella(this.giocatoreRe.getCoordinatePezzo(), mosseAvversario)
				.isEmpty();
	}

	public boolean isInScacco() {
		return this.isInScacco;
	}

	public boolean isInScaccoMatto() {
		return this.isInScacco && !hasMosseDiFuga();
	}

	public boolean isInStallo() {
		return !this.isInScacco && !hasMosseDiFuga(); // non � n� in scacco n� ha mosse di fuga
	}

	public Re getGiocatoreRe() {
		return this.giocatoreRe;
	}

	public Collection<Mossa> getMosseLegali() {
		return this.mosseLegali;
	}

	private static Collection<Mossa> calcolaAttaccoSuCasella(int coordinatePezzo, Collection<Mossa> mosse) {
		final List<Mossa> mosseAttacco = new ArrayList<Mossa>();
		for (final Mossa mossa : mosse) {
			if (coordinatePezzo == mossa.getCoordinateDestinazione()) {
				mosseAttacco.add(mossa);
			}
		}
		return ImmutableList.copyOf(mosseAttacco);
	}

	private Re stabilireRe() {
		for (final Pezzo pezzo : getPezziAttivi()) {
			if (pezzo.getTipoPezzo().isRe()) {
				return (Re) pezzo;
			}
		}
		throw new RuntimeException("Non dovrebbe arrivare qui!");
	}
	private boolean hasMosseDiFuga() { // per verificare le mosse di fuga del re
		return this.mosseLegali.stream()
                .anyMatch(mossa -> mossaFatta(mossa)
                .getStatoMossa().isFatto());
	}


	public boolean isArroccato() {
		return false;
	}

	public TransizioneMossa mossaFatta(final Mossa mossa) {
		if (!this.mosseLegali.contains(mossa)) {
			return new TransizioneMossa(this.scacchiera, this.scacchiera, mossa, StatoMossa.MOSSA_ILLEGALE);
		}
		final Scacchiera transizioneScacchiera = mossa.esegui();
		final Collection<Mossa> attacchiRe = Giocatore.calcolaAttaccoSuCasella(
				transizioneScacchiera.giocatoreAttuale().getAvversario().getGiocatoreRe().getCoordinatePezzo(),
				transizioneScacchiera.giocatoreAttuale().getMosseLegali());
		if (!attacchiRe.isEmpty()) {
			return new TransizioneMossa(this.scacchiera, this.scacchiera, mossa, StatoMossa.LASCIA_GIOCATORE_IN_SCACCO);
		}
		return new TransizioneMossa(this.scacchiera,transizioneScacchiera, mossa, StatoMossa.FATTO);
	}

	public TransizioneMossa mossaNonFatta(final Mossa mossa) {
		return new TransizioneMossa(this.scacchiera, mossa.undo(), mossa, StatoMossa.FATTO);
	}

	public abstract Collection<Pezzo> getPezziAttivi();

	public abstract Colore getColore();

	public abstract Giocatore getAvversario();
}
