package it.polito.tdp.genes.model;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Simulator {
	
	//dati in ingresso
	private Graph<Genes, DefaultWeightedEdge> grafo;
	private int N; //numero di bioingegneri
	private Genes gStart;
	private int tempoMax = 36;
	
	//dati in uscita
	//sono il modello del mondo al termine della simulazione
	
	//modello del mondo
	private Map<Genes, Integer> mapGeniIng;
	
	//coda degli eventi
	private PriorityQueue<Event> queue;
	
	public Simulator(Graph<Genes, DefaultWeightedEdge> grafo) {
		this.grafo = grafo;
	}
	
	public void init(int N, Genes gStart) {
		this.N = N;
		this.gStart = gStart;
		
		if(this.grafo.degreeOf(gStart)==0) {
			throw new IllegalArgumentException("Vertice di partenza isolato");
		}
		
		this.mapGeniIng = new HashMap<>();
		this.mapGeniIng.put(gStart, this.N);
		
		this.queue = new PriorityQueue<>();
		for(int ing=0; ing<N; ing++) {
			queue.add(new Event(0, ing, this.gStart));
		}
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			
			int tempo = e.getTempo();
			Genes g = e.getGene();
			int ing = e.getIng();
			
			if(tempo<this.tempoMax) {
				if(Math.random() < 0.3) {
					//ing continua a studiare lo stesso gene
					this.queue.add(new Event(tempo+1, ing, g));
					
				} else {
					//ing studia un gene adiacente
					this.mapGeniIng.put(g, this.mapGeniIng.get(g)-1);
					
					double S = 0.0;
					for(DefaultWeightedEdge edge : this.grafo.edgesOf(g)) {
						S += this.grafo.getEdgeWeight(edge);
					}
					
					double R = Math.random();
					
					double sommaParziale = 0.0;
					Genes nuovo = null;
					for(DefaultWeightedEdge edge : this.grafo.edgesOf(g)) {
						sommaParziale += this.grafo.getEdgeWeight(edge);
						if(sommaParziale/S > R) {
							nuovo = Graphs.getOppositeVertex(this.grafo, edge, g);
							break;
						}
					}
					
					if(this.mapGeniIng.containsKey(nuovo)) {
						this.mapGeniIng.put(nuovo, this.mapGeniIng.get(nuovo)+1);
					} else {
						this.mapGeniIng.put(nuovo, 1);
					}
					
					this.queue.add(new Event(tempo+1, ing, nuovo));
				}
			}
		}
	}
	
	public Map<Genes, Integer> getGeniIng() {
		Map<Genes, Integer> mapFinale = new HashMap<>();
		for(Genes g : this.mapGeniIng.keySet()) {
			if(this.mapGeniIng.get(g)>0) {
				mapFinale.put(g, this.mapGeniIng.get(g));
			}
		}
		return mapFinale;
	}

}
