package it.univaq.disim.oop.scacchi.pezzi;

import java.util.Set;

import it.univaq.disim.oop.scacchi.player.Giocatore;
import it.univaq.disim.oop.scacchi.scacchiera.Mossa;
import it.univaq.disim.oop.scacchi.scacchiera.Scacchiera;

public class Partita {

	private Integer Id;
	private Giocatore G1;
	private Giocatore G2;
	private Scacchiera S;
	private Giocatore giocatoreDiTurno;

	private Scacchiera mossaScacchiera;
	private Set<Giocatore> giocatori;
	private Set<Mossa> mosse;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Giocatore getG1() {
		return G1;
	}

	public void setG1(Giocatore g1) {
		G1 = g1;
	}

	public Giocatore getG2() {
		return G2;
	}

	public void setG2(Giocatore g2) {
		G2 = g2;
	}

	public Scacchiera getS() {
		return S;
	}

	public void setS(Scacchiera s) {
		S = s;
	}

	public Giocatore getGiocatoreDiTurno() {
		return giocatoreDiTurno;
	}

	public void setGiocatoreDiTurno(Giocatore giocatoreDiTurno) {
		this.giocatoreDiTurno = giocatoreDiTurno;
	}

	public Scacchiera getMossaScacchiera() {
		return mossaScacchiera;
	}

	public void setMossaScacchiera(Scacchiera mossaScacchiera) {
		this.mossaScacchiera = mossaScacchiera;
	}

	public Set<Giocatore> getGiocatori() {
		return giocatori;
	}

	public void setGiocatori(Set<Giocatore> giocatori) {
		this.giocatori = giocatori;
	}

	public Set<Mossa> getMosse() {
		return mosse;
	}

	public void setMosse(Set<Mossa> mosse) {
		this.mosse = mosse;
	}

}