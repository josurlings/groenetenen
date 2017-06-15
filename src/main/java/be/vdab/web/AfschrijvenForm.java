package be.vdab.web;

import javax.validation.constraints.NotNull;

import be.vdab.entities.Filiaal;

public class AfschrijvenForm {
	
	@NotNull
	private Filiaal filiaal;
	

	public Filiaal getFiliaal() {
		return filiaal;
	}

	public void setFiliaal(Filiaal filiaal) {
		this.filiaal = filiaal;
	}
	
}
