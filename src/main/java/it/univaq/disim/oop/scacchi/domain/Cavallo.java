package it.univaq.disim.oop.scacchi.domain;

import java.util.*;

import it.univaq.disim.oop.scacchi.controller.ScacchieraController;

public class Cavallo extends Pezzo {

	private final static int [] MOSSE_POSSIBILI = {-17, -15, -10, -6, 6, 10, 15, 17};
	
	Cavallo(final int coordinatePezzo, final Colore colorePezzo) {
		super(coordinatePezzo, colorePezzo);
	}

	@Override
	public Collection<Mossa> mosseLegali(Scacchiera scacchiera) {
		
		final List<Mossa> possibiliMosse = new ArrayList<Mossa>();
		
		for(final int insiemePosizioneCorrente : MOSSE_POSSIBILI) {
			
			int coordinateArrivo = this.coordinatePezzo + insiemePosizioneCorrente;
			
			if(ScacchieraController.casellaDisponibile(coordinateArrivo)) {
				
				if(primaColonnaEsclusa(this.coordinatePezzo, insiemePosizioneCorrente) ||
						secondaColonnaEsclusa(this.coordinatePezzo, insiemePosizioneCorrente)||
						settimaColonnaEsclusa(this.coordinatePezzo, insiemePosizioneCorrente)||
						ottavaColonnaEsclusa(this.coordinatePezzo, insiemePosizioneCorrente)) {
					continue;
				}
				
				final Casella casellaArrivo = scacchiera.getCasella(coordinateArrivo);
				
				if(!casellaArrivo.occupata()) {
					possibiliMosse.add(new Mossa());
				}else {
					
					final Pezzo pezzoArrivo = casellaArrivo.getPezzo();
					final Colore colorePezzo = pezzoArrivo.getColorePezzo();
					
					if(this.colorePezzo != colorePezzo) {
						possibiliMosse.add(new Mossa());
					}
				}
				
			}
			
		}
		return Collections.unmodifiableList(possibiliMosse);
	}

	private static boolean primaColonnaEsclusa(final int posizioneAttuale, final int possibilePosizione) {
		
		return ScacchieraController.PRIMA_COLONNA[posizioneAttuale] && (possibilePosizione == -17 || 
				possibilePosizione == -10 || possibilePosizione == 6 || possibilePosizione == 15);
	}
	
	private static boolean secondaColonnaEsclusa(final int posizioneAttuale, final int possibilePosizione) {
		
		return ScacchieraController.SECONDA_COLONNA[posizioneAttuale] && (possibilePosizione == -10 || possibilePosizione == 6);
	}
	
	private static boolean settimaColonnaEsclusa(final int posizioneAttuale, final int possibilePosizione) {
		
		return ScacchieraController.SETTIMA_COLONNA[posizioneAttuale] && (possibilePosizione == -6 || possibilePosizione == 10);
	}
	
	private static boolean ottavaColonnaEsclusa(final int posizioneAttuale, final int possibilePosizione) {
		
		return ScacchieraController.OTTAVA_COLONNA[posizioneAttuale] && (possibilePosizione == -15 || 
				possibilePosizione == -6 || possibilePosizione == 10 || possibilePosizione == 17);
	}
	
}
