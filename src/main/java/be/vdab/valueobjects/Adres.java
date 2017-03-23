package be.vdab.valueobjects;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import be.vdab.constraints.Postcode;

@Embeddable
@XmlAccessorType(XmlAccessType.FIELD)
@JsonAutoDetect(fieldVisibility=Visibility.ANY)
public class Adres implements Serializable 
{
	private static final long serialVersionUID = 1L;
	@NotBlank
	@Length(min = 1, max = 50)
	@SafeHtml
	private String straat;
	@NotBlank
	@Length(min = 1, max = 7)
	@SafeHtml
	private String huisNr;
	@NotNull
	@Postcode
	private Integer postcode;
	@NotBlank
	@Length(min = 1, max = 50)
	@SafeHtml
	private String gemeente;
	
	public Adres()
	{}
		
	public Adres(String straat, String huisNr,
	Integer postcode, String gemeente)
	{
	this.straat = straat;
	this.huisNr = huisNr;
	this.postcode = postcode;
	this.gemeente = gemeente;
	}
	public String getStraat() {
		return straat;
	}
	public void setStraat(String straat) {
		this.straat = straat;
	}
	public String getHuisNr() {
		return huisNr;
	}
	public void setHuisNr(String huisNr) {
		this.huisNr = huisNr;
	}
	public Integer getPostcode() {
		return postcode;
	}
	public void setPostcode(Integer postcode) {
		this.postcode = postcode;
	}
	public String getGemeente() {
		return gemeente;
	}
	public void setGemeente(String gemeente) {
		this.gemeente = gemeente;
	}
			
}
