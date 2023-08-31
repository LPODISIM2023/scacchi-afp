package it.univaq.disim.oop.scacchi.pezzi;

import java.util.*;

import com.google.common.collect.ImmutableList;

import it.univaq.disim.oop.scacchi.controller.ScacchieraController;
import it.univaq.disim.oop.scacchi.player.Colore;
import it.univaq.disim.oop.scacchi.scacchiera.Mossa;
import it.univaq.disim.oop.scacchi.scacchiera.Scacchiera;
import it.univaq.disim.oop.scacchi.scacchiera.Mossa.MossaAttaccoPedone;
import it.univaq.disim.oop.scacchi.scacchiera.Mossa.GrandeMossa;
import it.univaq.disim.oop.scacchi.scacchiera.Mossa.MuoviPedone;

public class Pedone extends Pezzo {

	private final static int[] MOSSE_POSSIBILI = { 8, 16, 7, 9 };

	public Pedone(final Colore colorePezzo, final int coordinatePezzo) {
		super(TipoPezzo.PEDONE, coordinatePezzo, colorePezzo, true);
	}
	
	public Pedone(final Colore colorePezzo, final int coordinatePezzo, final boolean primaMossa) {
		super(TipoPezzo.PEDONE, coordinatePezzo, colorePezzo, primaMossa);
	}

	@Override
	public Collection<Mossa> calcolaMosseLegali(final Scacchiera scacchiera) {

		final List<Mossa> possibiliMosse = new ArrayList<Mossa>();

		for (final int insiemePosizioneCorrente : MOSSE_POSSIBILI) {

			final int coordinateArrivo = this.coordinatePezzo
					+ (this.getColorePezzo().getDirezione() * insiemePosizioneCorrente);

			if (!ScacchieraController.casellaDisponibile(coordinateArrivo)) {
				continue;
			}

			if (insiemePosizioneCorrente == 8 && !scacchiera.getCasella(coordinateArrivo).occupata()) {
				// TODO more work to do here(occuparsi delle promozioni)
				possibiliMosse.add(new GrandeMossa(scacchiera, this, coordinateArrivo));
			} else if (insiemePosizioneCorrente == 16 && this.primaMossa()
					&& ((ScacchieraController.RANGO_SETTE[this.coordinatePezzo] && this.getColorePezzo().isNero())
							|| (ScacchieraController.RANGO_DUE[this.coordinatePezzo]
									&& this.getColorePezzo().isBianco()))) {
				final int casellaPrecedenteArrivo = this.coordinatePezzo + (this.colorePezzo.getDirezione() * 8);
				if (!scacchiera.getCasella(casellaPrecedenteArrivo).occupata()
						&& !scacchiera.getCasella(coordinateArrivo).occupata()) {
					possibiliMosse.add(new MuoviPedone(scacchiera, this, coordinateArrivo));
				}
			} else if (insiemePosizioneCorrente == 7 && !((ScacchieraController.OTTAVA_COLONNA[this.coordinatePezzo]
					&& this.colorePezzo.isBianco()
					|| (ScacchieraController.PRIMA_COLONNA[this.coordinatePezzo] && this.colorePezzo.isNero())))) {
				if (scacchiera.getCasella(coordinateArrivo).occupata()) {
					final Pezzo pezzoArrivo = scacchiera.getCasella(coordinateArrivo).getPezzo();
					if (this.colorePezzo != pezzoArrivo.getColorePezzo()) {
						// TODO more to do here
						possibiliMosse.add(new MossaAttaccoPedone(scacchiera, this, coordinateArrivo, pezzoArrivo));
					}
				}
			} else if (insiemePosizioneCorrente == 9 && !((ScacchieraController.PRIMA_COLONNA[this.coordinatePezzo]
					&& this.colorePezzo.isBianco()
					|| (ScacchieraController.OTTAVA_COLONNA[this.coordinatePezzo] && this.colorePezzo.isNero())))) {
				if (scacchiera.getCasella(coordinateArrivo).occupata()) {
					final Pezzo pezzoArrivo = scacchiera.getCasella(coordinateArrivo).getPezzo();
					if (this.colorePezzo != pezzoArrivo.getColorePezzo()) {
						// TODO more to do here
						possibiliMosse.add(new MossaAttaccoPedone(scacchiera, this, coordinateArrivo, pezzoArrivo));
					}
				}
			}
		}
		return ImmutableList.copyOf(possibiliMosse);
	}

	@Override
	public String toString() {
		return TipoPezzo.PEDONE.toString();
	}

	@Override
	public Pedone pezzoMosso(Mossa mossa) {
		return new Pedone(mossa.getPezzoMosso().getColorePezzo(), mossa.getCoordinateDestinazione());
	}

}
