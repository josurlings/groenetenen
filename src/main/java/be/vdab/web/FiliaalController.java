package be.vdab.web;

import java.util.List;
//import java.util.Optional;
//import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
//import org.springframework.validation.DataBinder;
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

import org.springframework.http.MediaType;

@Controller 
//@RequestMapping("/filialen")
@RequestMapping(path = "/filialen", produces = MediaType.TEXT_HTML_VALUE)
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
	private static final String WIJZIGEN_VIEW = "filialen/wijzigen";
	private static final String REDIRECT_URL_NA_WIJZIGEN = "redirect:/filialen";
	private static final String REDIRECT_URL_NA_LOCKING_EXCEPTION =
			"redirect:/filialen/{id}?optimisticlockingexception=true";


	//private static final Logger LOGGER =
	//		Logger.getLogger(FiliaalController.class.getName());
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

	/*
	@GetMapping("toevoegen") 
	String createForm()
	{
		return TOEVOEGEN_VIEW;
	}
	*/
	
	@GetMapping("toevoegen")
	ModelAndView createForm()
	{
	return new ModelAndView(TOEVOEGEN_VIEW).addObject(new Filiaal());
	}
	
	@PostMapping
	String create(@Valid Filiaal filiaal, BindingResult bindingResult)
	{
	if (bindingResult.hasErrors()) 
	{
	return TOEVOEGEN_VIEW;
	}
	filiaalService.create(filiaal);
	return REDIRECT_URL_NA_TOEVOEGEN;
	}
		
	/*
	@PostMapping 
	String create() {
		// later voeg je een record toe aan de database
		LOGGER.info("filiaal record toevoegen aan database");
		return REDIRECT_URL_NA_TOEVOEGEN; 
	}

	public FiliaalService getFiliaalService() {
		return filiaalService;
	}

@GetMapping(params = "id") 
ModelAndView read(long id) 
{ 
ModelAndView modelAndView = new ModelAndView(FILIAAL_VIEW);
filiaalService.read(id) 
.ifPresent(filiaal -> modelAndView.addObject(filiaal)); 
return modelAndView;
}


	@GetMapping("{id}") 
	ModelAndView read(@PathVariable long id) 
	{ 
		ModelAndView modelAndView = new ModelAndView(FILIAAL_VIEW);
		filiaalService.read(id) 
		.ifPresent(filiaal -> modelAndView.addObject(filiaal)); 
		return modelAndView;
	}

	*/
		
	@GetMapping("{filiaal}") 
	ModelAndView read(@PathVariable Filiaal filiaal) 
	{ 
		ModelAndView modelAndView = new ModelAndView(FILIAAL_VIEW);
		if (filiaal != null)
		{
			modelAndView.addObject(filiaal);
		}
		return modelAndView;
	}
	
	/*
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
	*/
	@PostMapping("{filiaal}/verwijderen")
	String delete(@PathVariable Filiaal filiaal,
	RedirectAttributes redirectAttributes) {
	if (filiaal == null) {
	return REDIRECT_URL_FILIAAL_NIET_GEVONDEN;
	}
	long id = filiaal.getId();
	try {
	filiaalService.delete(id);
	redirectAttributes.addAttribute("id", id)
	.addAttribute("naam", filiaal.getNaam());
	return REDIRECT_URL_NA_VERWIJDEREN;
	} catch (FiliaalHeeftNogWerknemersException ex) {
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
	
	@PostMapping("{id}/wijzigen")
	String update(@Valid Filiaal filiaal, BindingResult bindingResult) 
	{
	if (bindingResult.hasErrors()) 
	{
	return WIJZIGEN_VIEW;
	}
//	filiaalService.update(filiaal);
//	return REDIRECT_URL_NA_WIJZIGEN;
	try {
		filiaalService.update(filiaal);
		return REDIRECT_URL_NA_WIJZIGEN;
		} catch (ObjectOptimisticLockingFailureException ex)
		{
		return REDIRECT_URL_NA_LOCKING_EXCEPTION;
		}
	}
	
	private static final String PER_POSTCODE_VIEW = "filialen/perpostcode";
	@GetMapping("perpostcode")
	ModelAndView findByPostcodeReeks() {
		PostcodeReeks reeks = new PostcodeReeks();
		//reeks.setVanpostcode(1000);
		//reeks.setTotpostcode(9999);
		return new ModelAndView(PER_POSTCODE_VIEW).addObject(reeks); 
	}
	/*
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
	*/
	
	@InitBinder("postcodeReeks") 
	void initBinderPostcodeReeks(WebDataBinder binder) 
	{ 
		binder.initDirectFieldAccess(); 
	}
	
	@GetMapping(params = {"vanpostcode", "totpostcode"})
	ModelAndView findByPostcodeReeks(@Valid PostcodeReeks reeks,
			BindingResult bindingResult) 
	{ 
		ModelAndView modelAndView = new ModelAndView(PER_POSTCODE_VIEW);
		/*
		if ( ! bindingResult.hasErrors())
		{ 
		modelAndView.addObject("filialen",filiaalService.findByPostcodeReeks(reeks));
		}
		 */
		if ( ! bindingResult.hasErrors()) 
		{
			List<Filiaal> filialen = filiaalService.findByPostcodeReeks(reeks);
			if (filialen.isEmpty()) 
			{
				bindingResult.reject("geenFilialen"); 
			}
			else 
			{
				modelAndView.addObject("filialen", filialen);
			}
		}
		return modelAndView;
	}
	
	@InitBinder("filiaal")
	void initBinderFiliaal(WebDataBinder binder)
	{
		binder.initDirectFieldAccess(); 
	}
	
	/*
	@GetMapping("{id}/wijzigen")
	ModelAndView updateForm(@PathVariable long id)
	{
	Optional<Filiaal> optionalFiliaal = filiaalService.read(id);
	if (! optionalFiliaal.isPresent()) 
	{
	return new ModelAndView(REDIRECT_URL_FILIAAL_NIET_GEVONDEN);
	}
	return new ModelAndView(WIJZIGEN_VIEW).addObject(optionalFiliaal.get());
	}
	*/
	@GetMapping("{filiaal}/wijzigen")
	ModelAndView updateForm(@PathVariable Filiaal filiaal) {
	if (filiaal == null) {
	return new ModelAndView(REDIRECT_URL_FILIAAL_NIET_GEVONDEN);
	}
	return new ModelAndView(WIJZIGEN_VIEW).addObject(filiaal);
	}
	
}
