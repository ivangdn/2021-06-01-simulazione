package it.polito.tdp.genes.model;

public class Event implements Comparable<Event>{
	
	private int tempo;
	private int ing;
	private Genes gene;
	
	public Event(int tempo, int ing, Genes gene) {
		super();
		this.tempo = tempo;
		this.ing = ing;
		this.gene = gene;
	}

	public int getTempo() {
		return tempo;
	}

	public int getIng() {
		return ing;
	}

	public Genes getGene() {
		return gene;
	}

	@Override
	public int compareTo(Event o) {
		return this.tempo - o.tempo;
	}

}
