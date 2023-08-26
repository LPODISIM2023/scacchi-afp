package it.univaq.disim.oop.scacchi.scacchiera;


import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import it.univaq.disim.oop.scacchi.controller.ScacchieraController;
import it.univaq.disim.oop.scacchi.pezzi.*;
import it.univaq.disim.oop.scacchi.player.Colore;
import it.univaq.disim.oop.scacchi.player.Giocatore;
import it.univaq.disim.oop.scacchi.player.GiocatoreBianco;
import it.univaq.disim.oop.scacchi.player.GiocatoreNero;
public class Scacchiera{

	private Integer Id;
	private Boolean vittoria;
	private Boolean pareggio;
	private Pezzo pezzo;
	private Partita partite;
	private Set<Pezzo> pezzi;
	
	private final List<Casella> scacchiera;
	private final Collection<Pezzo> pezziBianchi;
	private final Collection<Pezzo> pezziNeri;
	
	private final GiocatoreBianco giocatoreBianco;
	private final GiocatoreNero giocatoreNero;
	private final Giocatore giocatoreAttuale;
	

	private Scacchiera(final Costruttore costruttore) {
		this.scacchiera = creaScacchiera(costruttore);
		this.pezziBianchi = calcolaPezziAttivi(this.scacchiera, Colore.BIANCO);
		this.pezziNeri = calcolaPezziAttivi(this.scacchiera, Colore.NERO);
		final Collection<Mossa> mosseStandardLegaliBianco = calcolaMosseLegali(this.pezziBianchi);
		final Collection<Mossa> mosseStandardLegaliNero = calcolaMosseLegali(this.pezziNeri);		
		this.giocatoreBianco = new GiocatoreBianco(this, mosseStandardLegaliBianco, mosseStandardLegaliNero);
		this.giocatoreNero = new GiocatoreNero(this, mosseStandardLegaliBianco, mosseStandardLegaliNero);
		this.giocatoreAttuale = costruttore.prossimaMossaFatta.scegliGiocatore(this.giocatoreBianco,this.giocatoreNero);
	}
	
	@SuppressWarnings("unused")
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

	public Giocatore giocatoreBianco() {
		return this.giocatoreBianco();
	}
	
	public Giocatore giocatoreNero() {
		return this.giocatoreNero();
	}
	
	public Giocatore giocatoreAttuale() {
		return this.giocatoreAttuale;
	}
	
	public Collection<Pezzo> getPezziNeri(){
		return this.pezziNeri;
	}
	
	public Collection<Pezzo> getPezziBianchi(){
		return this.pezziBianchi;
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
		
		return ImmutableList.copyOf(pezziAttivi);
	}

	public Casella getCasella(final int casellaCoordinata) {
		return scacchiera.get(casellaCoordinata);
	}

	private static List<Casella> creaScacchiera(final Costruttore costruttore) {
		final Casella[] caselle = new Casella[ScacchieraController.NUM_CASELLE];
		for (int i = 0; i < ScacchieraController.NUM_CASELLE; i++) {
			caselle[i] = Casella.creaCasella(i, costruttore.scacchieraConfig.get(i));
		}
		return ImmutableList.copyOf(caselle);
	}

	
	// serve per creare la scacchiera con i pezzi nelle posizioni di partenza
	public static Scacchiera creaScacchieraStandard() {
		final Costruttore costruttore = new Costruttore();
		// Lato Nero
		costruttore.setPezzo(new Torre(Colore.NERO, 0));
		costruttore.setPezzo(new Cavallo(Colore.NERO, 1));
		costruttore.setPezzo(new Alfiere(Colore.NERO, 2));
		costruttore.setPezzo(new Regina(Colore.NERO, 3));
		costruttore.setPezzo(new Re(Colore.NERO, 4));	
		costruttore.setPezzo(new Alfiere(Colore.NERO, 5));
		costruttore.setPezzo(new Cavallo(Colore.NERO, 6));
		costruttore.setPezzo(new Torre(Colore.NERO, 7));
		costruttore.setPezzo(new Pedone(Colore.NERO, 8));
		costruttore.setPezzo(new Pedone(Colore.NERO, 9));
		costruttore.setPezzo(new Pedone(Colore.NERO, 10));
		costruttore.setPezzo(new Pedone(Colore.NERO, 11));
		costruttore.setPezzo(new Pedone(Colore.NERO, 12));
		costruttore.setPezzo(new Pedone(Colore.NERO, 13));
		costruttore.setPezzo(new Pedone(Colore.NERO, 14));
		costruttore.setPezzo(new Pedone(Colore.NERO, 15));
		// Lato Bianco
		costruttore.setPezzo(new Pedone(Colore.BIANCO, 48));
		costruttore.setPezzo(new Pedone(Colore.BIANCO, 49));
		costruttore.setPezzo(new Pedone(Colore.BIANCO, 50));
		costruttore.setPezzo(new Pedone(Colore.BIANCO, 51));
		costruttore.setPezzo(new Pedone(Colore.BIANCO, 52));
		costruttore.setPezzo(new Pedone(Colore.BIANCO, 53));
		costruttore.setPezzo(new Pedone(Colore.BIANCO, 54));
		costruttore.setPezzo(new Pedone(Colore.BIANCO, 55));
		costruttore.setPezzo(new Torre(Colore.BIANCO, 56));
		costruttore.setPezzo(new Cavallo(Colore.BIANCO, 57));
		costruttore.setPezzo(new Alfiere(Colore.BIANCO, 58));
		costruttore.setPezzo(new Regina(Colore.BIANCO, 59));
		costruttore.setPezzo(new Re(Colore.BIANCO, 60));
		costruttore.setPezzo(new Alfiere(Colore.BIANCO, 61));
		costruttore.setPezzo(new Cavallo(Colore.BIANCO, 62));
		costruttore.setPezzo(new Torre(Colore.BIANCO, 63));
		// bianco da spostare
		costruttore.setMossaFatta(Colore.BIANCO);

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

	public Iterable<Mossa> getMossePossibili() {
		return Iterables.unmodifiableIterable(Iterables.concat(this.giocatoreBianco.getMosseLegali(), this.giocatoreNero.getMosseLegali()));
	}

}