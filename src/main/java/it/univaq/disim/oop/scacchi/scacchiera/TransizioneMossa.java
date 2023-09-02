package it.univaq.disim.oop.scacchi.scacchiera;

import it.univaq.disim.oop.scacchi.scacchiera.Mossa.StatoMossa;

public class TransizioneMossa {

	private final Scacchiera daScacchiera;
    private final Scacchiera inScacchiera;
	private final Mossa transizioneScacchiera;
	private final StatoMossa statoMossa;

	public TransizioneMossa(final Scacchiera daScacchiera, final Scacchiera inScacchiera,
			final Mossa transizioneMossa, final StatoMossa statoMossa) {

		this.daScacchiera = daScacchiera;
		this.inScacchiera = inScacchiera;
		this.transizioneScacchiera = transizioneMossa;
		this.statoMossa = statoMossa;
	}

	public Scacchiera getDaScacchiera() {
		return this.daScacchiera;
	}

	public Scacchiera getInScacchiera() {
		return this.inScacchiera;
	}

	public Mossa getTransizioneScacchiera() {
		return this.transizioneScacchiera;
	}

	public StatoMossa getStatoMossa() {
		return this.statoMossa;
	}
}
