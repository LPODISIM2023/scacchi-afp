package it.univaq.disim.oop.scacchi.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import it.univaq.disim.oop.scacchi.controller.ScacchieraController;
import it.univaq.disim.oop.scacchi.domain.Mossa.Muovi;

public class Pedone extends Pezzo {

	private final static int[] MOSSE_POSSIBILI = { 8, 16, 7, 9 };

	Pedone(final Colore colorePezzo, final int coordinatePezzo) {
		super(TipoPezzo.PEDONE, coordinatePezzo, colorePezzo);

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
				possibiliMosse.add(new Muovi(scacchiera, this, coordinateArrivo));
			} else if (insiemePosizioneCorrente == 16 && this.primaMossa()
					&& (ScacchieraController.SECONDA_RIGA[this.coordinatePezzo] && this.getColorePezzo().isNero())
					|| (ScacchieraController.SETTIMA_RIGA[this.coordinatePezzo] && this.getColorePezzo().isBianco())) {
				final int casellaArrivo = this.coordinatePezzo + (this.colorePezzo.getDirezione() * 8);
				if (!scacchiera.getCasella(casellaArrivo).occupata()
						&& !scacchiera.getCasella(coordinateArrivo).occupata()) {
					possibiliMosse.add(new Muovi(scacchiera, this, coordinateArrivo));
				}
			} else if (insiemePosizioneCorrente == 7 && !((ScacchieraController.OTTAVA_COLONNA[this.coordinatePezzo]
					&& this.colorePezzo.isBianco()
					|| (ScacchieraController.PRIMA_COLONNA[this.coordinatePezzo] && this.colorePezzo.isNero())))) {
				if (scacchiera.getCasella(coordinateArrivo).occupata()) {
					final Pezzo pezzoArrivo = scacchiera.getCasella(coordinateArrivo).getPezzo();
					if (this.colorePezzo != pezzoArrivo.getColorePezzo()) {
						// TODO more to do here
						possibiliMosse.add(new Muovi(scacchiera, this, coordinateArrivo));
					}
				}
			} else if (insiemePosizioneCorrente == 9 && !((ScacchieraController.PRIMA_COLONNA[this.coordinatePezzo]
					&& this.colorePezzo.isBianco()
					|| (ScacchieraController.OTTAVA_COLONNA[this.coordinatePezzo] && this.colorePezzo.isNero())))) {
				if (scacchiera.getCasella(coordinateArrivo).occupata()) {
					final Pezzo pezzoArrivo = scacchiera.getCasella(coordinateArrivo).getPezzo();
					if (this.colorePezzo != pezzoArrivo.getColorePezzo()) {
						// TODO more to do here
						possibiliMosse.add(new Muovi(scacchiera, this, coordinateArrivo));
					}
				}
			}
		}

		return Collections.unmodifiableList(possibiliMosse);
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
