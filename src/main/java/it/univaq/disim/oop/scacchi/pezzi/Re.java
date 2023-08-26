package it.univaq.disim.oop.scacchi.pezzi;

import java.util.*;

import com.google.common.collect.ImmutableList;

import it.univaq.disim.oop.scacchi.controller.ScacchieraController;
import it.univaq.disim.oop.scacchi.scacchiera.Casella;
import it.univaq.disim.oop.scacchi.scacchiera.Mossa;
import it.univaq.disim.oop.scacchi.scacchiera.Scacchiera;
import it.univaq.disim.oop.scacchi.scacchiera.Mossa.Attacco;
import it.univaq.disim.oop.scacchi.scacchiera.Mossa.Muovi;

public class Re extends Pezzo {

	private final static int[] MOSSE_POSSIBILI = { -9, -8, -7, -1, 1, 7, 8, 9 };

	public Re(final Colore colorePezzo, final int coordinatePezzo) {
		super(TipoPezzo.RE, coordinatePezzo, colorePezzo);
	}

	@Override
	public Collection<Mossa> calcolaMosseLegali(Scacchiera scacchiera) {

		final List<Mossa> possibiliMosse = new ArrayList<Mossa>();

		for (final int insiemePosizioneCorrente : MOSSE_POSSIBILI) {
			final int coordinateArrivo = this.coordinatePezzo + insiemePosizioneCorrente;
			if (primaColonnaEsclusa(this.coordinatePezzo, insiemePosizioneCorrente)
					|| ottavaColonnaEsclusa(this.coordinatePezzo, insiemePosizioneCorrente)) {
				continue;
			}
			if (ScacchieraController.casellaDisponibile(coordinateArrivo)) {
				final Casella casellaArrivo = scacchiera.getCasella(coordinateArrivo);
				if (!casellaArrivo.occupata()) {
					possibiliMosse.add(new Muovi(scacchiera, this, coordinateArrivo));
				} else {
					final Pezzo pezzoArrivo = casellaArrivo.getPezzo();
					final Colore colorePezzo = pezzoArrivo.getColorePezzo();
					if (this.colorePezzo != colorePezzo) {
						possibiliMosse.add(new Attacco(scacchiera, this, coordinateArrivo, pezzoArrivo));
					}
				}
			}
		}

		return ImmutableList.copyOf(possibiliMosse);
	}
	
	@Override
	public String toString() {
		return TipoPezzo.RE.toString();
	}

	private static boolean primaColonnaEsclusa(final int posizioneAttuale, final int possibilePosizione) {
		return ScacchieraController.PRIMA_COLONNA[posizioneAttuale]
				&& (possibilePosizione == -9 || possibilePosizione == -1 || possibilePosizione == 7);
	}

	private static boolean ottavaColonnaEsclusa(final int posizioneAttuale, final int possibilePosizione) {
		return ScacchieraController.OTTAVA_COLONNA[posizioneAttuale]
				&& (possibilePosizione == -7 || possibilePosizione == 1 || possibilePosizione == 9);
	}

	@Override
	public Re pezzoMosso(Mossa mossa) {
		return new Re(mossa.getPezzoMosso().getColorePezzo(), mossa.getCoordinateDestinazione());
	}

}
