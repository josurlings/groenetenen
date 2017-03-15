package be.vdab.web;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import be.vdab.entities.Filiaal;
import be.vdab.exceptions.FiliaalHeeftNogWerknemersException;
import be.vdab.services.FiliaalService;
import be.vdab.valueobjects.PostcodeReeks;

@Controller 
@RequestMapping("/filialen") 
class FiliaalController 
{
	private static final String FILIALEN_VIEW = "filialen/filialen";
	private static final String TOEVOEGEN_VIEW = "filialen/toevoegen";
	private static final String REDIRECT_URL_NA_TOEVOEGEN = "redirect:/filialen";
	private static final String FILIAAL_VIEW = "filialen/filiaal";
	private static final String REDIRECT_URL_FILIAAL_NIET_GEVONDEN =
			"redirect:/filialen";
	private static final String REDIRECT_URL_NA_VERWIJDEREN =
			"redirect:/filialen/{id}/verwijderd";
	private static final String REDIRECT_URL_HEEFT_NOG_WERKNEMERS =
			"redirect:/filialen/{id}";
	private static final String VERWIJDERD_VIEW = "filialen/verwijderd";


	private static final Logger LOGGER =
			Logger.getLogger(FiliaalController.class.getName());
	private final FiliaalService filiaalService;

	FiliaalController(FiliaalService filiaalService) 
	{
		// Spring injecteert de parameter filiaalService met de bean die de interface
		// FiliaalService implementeert: DefaultFiliaalService
		this.filiaalService = filiaalService;
	}

	@GetMapping 
	//String findAll()
	//{
	//return FILIALEN_VIEW; 
	//}
	ModelAndView findAll()
	{
		return new ModelAndView(FILIALEN_VIEW, "filialen", filiaalService.findAll())
					.addObject("aantalFilialen", filiaalService.findAantalFilialen());
		//		.addObject("werknemers", werknemerService.findAll());
	}


	@GetMapping("toevoegen") 
	String createForm()
	{
		return TOEVOEGEN_VIEW;
	}

	@PostMapping 
	String create() {
		// later voeg je een record toe aan de database
		LOGGER.info("filiaal record toevoegen aan database");
		return REDIRECT_URL_NA_TOEVOEGEN; 
	}

	public FiliaalService getFiliaalService() {
		return filiaalService;
	}
	/*
@GetMapping(params = "id") 
ModelAndView read(long id) 
{ 
ModelAndView modelAndView = new ModelAndView(FILIAAL_VIEW);
filiaalService.read(id) 
.ifPresent(filiaal -> modelAndView.addObject(filiaal)); 
return modelAndView;
}
	 */

	@GetMapping("{id}") 
	ModelAndView read(@PathVariable long id) 
	{ 
		ModelAndView modelAndView = new ModelAndView(FILIAAL_VIEW);
		filiaalService.read(id) 
		.ifPresent(filiaal -> modelAndView.addObject(filiaal)); 
		return modelAndView;
	}

	@PostMapping("{id}/verwijderen")
	String delete(@PathVariable long id, RedirectAttributes redirectAttributes)
	{
		Optional<Filiaal> optionalFiliaal = filiaalService.read(id);
		if (! optionalFiliaal.isPresent()) 
		{ 
			return REDIRECT_URL_FILIAAL_NIET_GEVONDEN;
		}
		try
		{
			filiaalService.delete(id);
			redirectAttributes.addAttribute("id", id) 
			.addAttribute("naam", optionalFiliaal.get().getNaam()); 
			return REDIRECT_URL_NA_VERWIJDEREN; 
		} catch (FiliaalHeeftNogWerknemersException ex) 
		{
			redirectAttributes.addAttribute("id", id)
			.addAttribute("fout", "Filiaal heeft nog werknemers");
			return REDIRECT_URL_HEEFT_NOG_WERKNEMERS;
		}
	}

	@GetMapping("{id}/verwijderd")
	String deleted()
	{
	return VERWIJDERD_VIEW;
	}
	
	private static final String PER_POSTCODE_VIEW = "filialen/perpostcode";
	@GetMapping("perpostcode")
	ModelAndView findByPostcodeReeks() {
	PostcodeReeks reeks = new PostcodeReeks();
	//reeks.setVanpostcode(1000);
	//reeks.setTotpostcode(9999);
	return new ModelAndView(PER_POSTCODE_VIEW).addObject(reeks); 
	}
	
	@GetMapping(params = {"vanpostcode", "totpostcode"}) 
	ModelAndView findByPostcodeReeks(PostcodeReeks reeks) { 
	return new ModelAndView(PER_POSTCODE_VIEW,
	"filialen", filiaalService.findByPostcodeReeks(reeks)); 
	}
	
	@InitBinder("postcodeReeks") 
	void initBinderPostcodeReeks(DataBinder dataBinder) 
	{ 
	dataBinder.setRequiredFields("vanpostcode", "totpostcode"); 
	}
	
}
