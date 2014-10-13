package org.kanomchan.formula.bean;

// Generated Oct 10, 2014 3:15:49 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * FormulaEffectId generated by hbm2java
 */
@Embeddable
public class FormulaEffectId implements java.io.Serializable {

	private Long idFormula;
	private String symbol;
	private int seq;

	public FormulaEffectId() {
	}

	public FormulaEffectId(Long idFormula, String symbol, int seq) {
		this.idFormula = idFormula;
		this.symbol = symbol;
		this.seq = seq;
	}

	@Column(name = "ID_FORMULA", nullable = false)
	public Long getIdFormula() {
		return this.idFormula;
	}

	public void setIdFormula(Long idFormula) {
		this.idFormula = idFormula;
	}

	@Column(name = "SYMBOL", nullable = false, length = 20)
	public String getSymbol() {
		return this.symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	@Column(name = "SEQ", nullable = false)
	public int getSeq() {
		return this.seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof FormulaEffectId))
			return false;
		FormulaEffectId castOther = (FormulaEffectId) other;

		return ((this.getIdFormula() == castOther.getIdFormula()) || (this.getIdFormula() != null && castOther.getIdFormula() != null && this.getIdFormula().equals(castOther.getIdFormula())))
				&& ((this.getSymbol() == castOther.getSymbol()) || (this.getSymbol() != null && castOther.getSymbol() != null && this.getSymbol().equals(castOther.getSymbol())))
				&& (this.getSeq() == castOther.getSeq());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getIdFormula() == null ? 0 : this.getIdFormula().hashCode());
		result = 37 * result + (getSymbol() == null ? 0 : this.getSymbol().hashCode());
		result = 37 * result + this.getSeq();
		return result;
	}

}
