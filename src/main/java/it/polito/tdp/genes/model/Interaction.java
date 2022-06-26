package it.polito.tdp.genes.model;

public class Interaction implements Comparable<Interaction>{
	
	private Genes g1;
	private Genes g2;
	private Double expressionCorr;
	
	public Interaction(Genes g1, Genes g2, Double expressionCorr) {
		super();
		this.g1 = g1;
		this.g2 = g2;
		this.expressionCorr = expressionCorr;
	}

	public Genes getG1() {
		return g1;
	}

	public void setG1(Genes g1) {
		this.g1 = g1;
	}

	public Genes getG2() {
		return g2;
	}

	public void setG2(Genes g2) {
		this.g2 = g2;
	}

	public Double getExpressionCorr() {
		return expressionCorr;
	}

	public void setExpressionCorr(Double expressionCorr) {
		this.expressionCorr = expressionCorr;
	}

	@Override
	public int compareTo(Interaction other) {
		return other.expressionCorr.compareTo(this.expressionCorr);
	}

}
