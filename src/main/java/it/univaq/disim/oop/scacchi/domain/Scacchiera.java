package it.univaq.disim.oop.scacchi.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.univaq.disim.oop.scacchi.controller.ScacchieraController;

public class Scacchiera extends Giocatore {

	private Integer Id;
	private Boolean vittoria;
	private Boolean pareggio;
	private Pezzo pezzo;
	private Partita partite;
	private Set<Pezzo> pezzi;
	
	private final List<Casella> scacchiera;
	private final Collection<Pezzo> pezziBianchi;
	private final Collection<Pezzo> pezziNeri;
	

	private Scacchiera(Costruttore costruttore) {
		this.scacchiera = creaScacchiera(costruttore);
		this.pezziBianchi = calcolaPezziAttivi(this.scacchiera, Colore.Bianco);
		this.pezziNeri = calcolaPezziAttivi(this.scacchiera, Colore.Nero);
		
		final Collection<Mossa> mosseStandardlegaliBianco = calcolaMosseLegali(this.pezziBianchi);
		final Collection<Mossa> mosseStandardLegaliNero = calcolaMosseLegali(this.pezziNeri);
	}
	
	private static String stampaBella(Casella casella) {
		return casella.toString();
	}
	
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		for(int i = 0; i < ScacchieraController.NUM_CASELLE; i++) {
			final String testoCasella = this.scacchiera.get(i).toString();
			builder.append(String.format("%3s", testoCasella));
			if((i + 1) % ScacchieraController.NUM_CASELLE_PER_RIGA == 0) {
				builder.append("\n");
			}
		}
		return builder.toString();
	}

	private Collection<Mossa> calcolaMosseLegali(final Collection<Pezzo> pezzi) {
		final List<Mossa> mosseLegali = new ArrayList<Mossa>();
		for(final Pezzo pezzo : pezzi) {
			mosseLegali.addAll(pezzo.calcolaMosseLegali(this));
		}
		return Collections.unmodifiableList(mosseLegali);
	}

	private static Collection<Pezzo> calcolaPezziAttivi(final List<Casella> scacchiera, final Colore colore) {
		final List<Pezzo> pezziAttivi = new ArrayList<Pezzo>();
		for(final Casella casella : scacchiera) {
			if(casella.occupata()) {
				final Pezzo pezzo = casella.getPezzo();
				if(pezzo.getColorePezzo() == colore) {
					pezziAttivi.add(pezzo);
				}
			}
		}
		
		return Collections.unmodifiableList(pezziAttivi);
	}

	public Casella getCasella(final int casellaCoordinata) {
		return scacchiera.get(casellaCoordinata);
	}

	private static List<Casella> creaScacchiera(final Costruttore costruttore) {
		final Casella[] caselle = new Casella[ScacchieraController.NUM_CASELLE];
		for (int i = 0; i < ScacchieraController.NUM_CASELLE; i++) {
			caselle[i] = Casella.creaCasella(i, costruttore.scacchieraConfig.get(i));
		}
		return Collections.unmodifiableList(caselle);
	}

	// serve per creare la scacchiera con i pezzi nelle posizioni di partenza
	public static Scacchiera creaScacchieraStandard() {
		final Costruttore costruttore = new Costruttore();
		// Lato Nero
		costruttore.setPezzo(new Torre(Colore.Nero, 0));
		costruttore.setPezzo(new Cavallo(Colore.Nero, 1));
		costruttore.setPezzo(new Alfiere(Colore.Nero, 2));
		costruttore.setPezzo(new Regina(Colore.Nero, 3));
		costruttore.setPezzo(new Re(Colore.Nero, 4));	
		costruttore.setPezzo(new Alfiere(Colore.Nero, 5));
		costruttore.setPezzo(new Cavallo(Colore.Nero, 6));
		costruttore.setPezzo(new Torre(Colore.Nero, 7));
		costruttore.setPezzo(new Pedone(Colore.Nero, 8));
		costruttore.setPezzo(new Pedone(Colore.Nero, 9));
		costruttore.setPezzo(new Pedone(Colore.Nero, 10));
		costruttore.setPezzo(new Pedone(Colore.Nero, 11));
		costruttore.setPezzo(new Pedone(Colore.Nero, 12));
		costruttore.setPezzo(new Pedone(Colore.Nero, 13));
		costruttore.setPezzo(new Pedone(Colore.Nero, 14));
		costruttore.setPezzo(new Pedone(Colore.Nero, 15));
		// Lato Bianco
		costruttore.setPezzo(new Pedone(Colore.Bianco, 48));
		costruttore.setPezzo(new Pedone(Colore.Bianco, 49));
		costruttore.setPezzo(new Pedone(Colore.Bianco, 50));
		costruttore.setPezzo(new Pedone(Colore.Bianco, 51));
		costruttore.setPezzo(new Pedone(Colore.Bianco, 52));
		costruttore.setPezzo(new Pedone(Colore.Bianco, 53));
		costruttore.setPezzo(new Pedone(Colore.Bianco, 54));
		costruttore.setPezzo(new Pedone(Colore.Bianco, 55));
		costruttore.setPezzo(new Torre(Colore.Bianco, 56));
		costruttore.setPezzo(new Cavallo(Colore.Bianco, 57));
		costruttore.setPezzo(new Alfiere(Colore.Bianco, 58));
		costruttore.setPezzo(new Regina(Colore.Bianco, 59));
		costruttore.setPezzo(new Re(Colore.Bianco, 60));
		costruttore.setPezzo(new Alfiere(Colore.Bianco, 61));
		costruttore.setPezzo(new Cavallo(Colore.Bianco, 62));
		costruttore.setPezzo(new Torre(Colore.Bianco, 63));
		// bianco da spostare
		costruttore.setMossaFatta(Colore.Bianco);

		return costruttore.crea();
	}

	public static class Costruttore {
		Map<Integer, Pezzo> scacchieraConfig;
		Colore prossimaMossaFatta;

		public Costruttore() {
			this.scacchieraConfig = new HashMap<Integer, Pezzo>();
		}

		public Costruttore setPezzo(final Pezzo pezzo) {
			this.scacchieraConfig.put(pezzo.getCoordinatePezzo(), pezzo);
			return this;
		}

		public Costruttore setMossaFatta(final Colore prossimaMossaFatta) {
			this.prossimaMossaFatta = prossimaMossaFatta;
			return this;
		}

		public Scacchiera crea() {
			return new Scacchiera(this);
		}

	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Boolean getVittoria() {
		return vittoria;
	}

	public void setVittoria(Boolean vittoria) {
		this.vittoria = vittoria;
	}

	public Boolean getPareggio() {
		return pareggio;
	}

	public void setPareggio(Boolean pareggio) {
		this.pareggio = pareggio;
	}

	public Pezzo getPezzo() {
		return pezzo;
	}

	public void setPezzo(Pezzo pezzo) {
		this.pezzo = pezzo;
	}

	public Partita getPartite() {
		return partite;
	}

	public void setPartite(Partita partite) {
		this.partite = partite;
	}

	public Set<Pezzo> getPezzi() {
		return pezzi;
	}

	public void setPezzi(Set<Pezzo> pezzi) {
		this.pezzi = pezzi;
	}

	

}