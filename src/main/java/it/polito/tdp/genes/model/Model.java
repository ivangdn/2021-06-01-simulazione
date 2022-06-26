package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	
	private Graph<Genes, DefaultWeightedEdge> grafo;
	private GenesDao dao;
	private List<Genes> essentialGenes;
	private Map<String, Genes> essentialGenesMap;
	
	public Model() {
		this.dao = new GenesDao();
	}
	
	public List<Genes> getVertici() {
		if(this.essentialGenes==null) {
			this.essentialGenes = dao.getEssentialGenes();
			this.essentialGenesMap = new HashMap<String, Genes>();
			for(Genes g : essentialGenes) {
				this.essentialGenesMap.put(g.getGeneId(), g);
			}
		}

		return this.essentialGenes;
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public void creaGrafo() {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, getVertici());
		
		for(Interaction i : dao.getInteractions(this.essentialGenesMap)) {
			if(i.getG1().getChromosome() != i.getG2().getChromosome()) {
				Graphs.addEdge(grafo, i.getG1(), i.getG2(), Math.abs(i.getExpressionCorr()));
			} else {
				Graphs.addEdge(grafo, i.getG1(), i.getG2(), 2.0*Math.abs(i.getExpressionCorr()));
			}
		}
	}
	
	public List<Interaction> geniAdiacenti(Genes g) {
		List<Interaction> adiacenti = new ArrayList<>();
		for(Genes vicino : Graphs.neighborListOf(grafo, g)) {
			double peso = this.grafo.getEdgeWeight(this.grafo.getEdge(g, vicino));
			adiacenti.add(new Interaction(g, vicino, peso));
		}
		Collections.sort(adiacenti);
		return adiacenti;
	}
	
	public Map<Genes, Integer> simula(int N, Genes gStart) {
		try {
			Simulator sim = new Simulator(this.grafo);
			sim.init(N, gStart);
			sim.run();
			return sim.getGeniIng();
		} catch(IllegalArgumentException e) {
			return null;
		}
	}
	
}
