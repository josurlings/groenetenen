package be.vdab.valueobjects;

import javax.validation.constraints.NotNull;

//import org.hibernate.validator.constraints.Range;

import be.vdab.constraints.Postcode;
import be.vdab.constraints.PostcodeReeksVanKleinerDanOfGelijkAanTot;

@PostcodeReeksVanKleinerDanOfGelijkAanTot
public class PostcodeReeks 
{
//@NotNull @Range(min = MIN_POSTCODE, max = MAX_POSTCODE)
@NotNull @Postcode
private Integer vanpostcode; 
//@NotNull @Range(min = MIN_POSTCODE, max = MAX_POSTCODE)
@NotNull @Postcode
private Integer totpostcode;
//private final static int MIN_POSTCODE = 1000;
//private final static int MAX_POSTCODE = 9999;

public boolean bevat(Integer postcode) {
// bevat de reeks een bepaalde postcode ? (gebruikt in de repository layer)
return postcode >= vanpostcode && postcode <= totpostcode;
}

public Integer getVanpostcode() {
	return vanpostcode;
}

public Integer getTotpostcode() {
	return totpostcode;
}
/*
public void setVanpostcode(Integer vanpostcode) 
{
// valideer(vanpostcode);
this.vanpostcode = vanpostcode;
}
public void setTotpostcode(Integer totpostcode)
{
// valideer(totpostcode);
this.totpostcode = totpostcode;
}


private void valideer(int postcode) {
if (postcode < MIN_POSTCODE || postcode > MAX_POSTCODE) {
throw new IllegalArgumentException();
}
}
*/


}