package it.univaq.disim.oop.scacchi.scacchiera;

public class TransizioneMossa {

	private final Scacchiera transizioneScacchiera;
	private final Mossa mossa;
	private final StatoMossa statoMossa;

	public TransizioneMossa(final Scacchiera transizioneScacchiera, final Mossa mossa, final StatoMossa statoMossa) {
		this.transizioneScacchiera = transizioneScacchiera;
		this.mossa = mossa;
		this.statoMossa = statoMossa;
	}

	public StatoMossa getStatoMossa() {
		return this.statoMossa;
	}

	public Scacchiera getTransizioneScacchiera() {
		return this.transizioneScacchiera;
	}
}
