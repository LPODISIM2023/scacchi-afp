package it.univaq.disim.oop.scacchi.domain;

public class Giocatore {

	private Integer Id;
	private String nome;
	private Colore colore;
	private Partita partite;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Colore getColore() {
		return colore;
	}

	public void setColore(Colore colore) {
		this.colore = colore;
	}

	public Partita getPartite() {
		return partite;
	}

	public void setPartite(Partita partite) {
		this.partite = partite;
	}

}