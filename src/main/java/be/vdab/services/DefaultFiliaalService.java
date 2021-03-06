package be.vdab.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;

//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Isolation;
//import org.springframework.transaction.annotation.Transactional;

import be.vdab.entities.Filiaal;
import be.vdab.exceptions.FiliaalHeeftNogWerknemersException;
import be.vdab.mail.MailSender;
import be.vdab.repositories.FiliaalRepository;
import be.vdab.valueobjects.PostcodeReeks;

//@Service
//@Transactional(readOnly = true , isolation = Isolation.READ_COMMITTED)
@ReadOnlyTransactionalService
public class DefaultFiliaalService implements FiliaalService 
{
	private final FiliaalRepository filiaalRepository;
	private final MailSender mailSender;
	
	
	DefaultFiliaalService(FiliaalRepository filiaalRepository,MailSender mailSender) 
	{
			this.filiaalRepository = filiaalRepository;
			this.mailSender = mailSender;
	}
	
	@Override
	@ModifyingTransactionalServiceMethod
//	public void create(Filiaal filiaal)
	public void create(Filiaal filiaal, String urlAlleFilialen)
	{
//	filiaalRepository.create(filiaal);
	filiaalRepository.save(filiaal);
//	mailSender.nieuwFiliaalMail(filiaal);
	mailSender.nieuwFiliaalMail(filiaal,urlAlleFilialen + '/' + filiaal.getId());
	}
	@Override
	public Optional<Filiaal> read(long id) 
	{
//	return filiaalRepository.read(id);
	return Optional.ofNullable(filiaalRepository.findOne(id));
	}
	@Override
	@ModifyingTransactionalServiceMethod
	public void update(Filiaal filiaal)
	{
//	filiaalRepository.update(filiaal);
	filiaalRepository.save(filiaal);
	}
	@Override
	@ModifyingTransactionalServiceMethod
	public void delete(long id)
	{
//	if (filiaalRepository.findAantalWerknemers(id) != 0) 
//	{
//	throw new FiliaalHeeftNogWerknemersException();
//	}
//	filiaalRepository.delete(id);
//		Optional<Filiaal> optionalFiliaal = filiaalRepository.read(id);
		Optional<Filiaal> optionalFiliaal = Optional.ofNullable(filiaalRepository.findOne(id));
		if (optionalFiliaal.isPresent())
		{
		if ( ! optionalFiliaal.get().getWerknemers().isEmpty()) {
		throw new FiliaalHeeftNogWerknemersException();
		}
		filiaalRepository.delete(id);
		}	
		
	}
	
	
	
	@Override
	public List<Filiaal> findAll()
	{
	return filiaalRepository.findAll();
	}
	@Override
	public long findAantalFilialen() 
	{
//	return filiaalRepository.findAantalFilialen();
	return filiaalRepository.count();
	}
	@Override
	@PreAuthorize("hasAuthority('manager')")
	public List<Filiaal> findByPostcodeReeks(PostcodeReeks reeks)
	{
//	return filiaalRepository.findByPostcodeReeks(reeks);
	return filiaalRepository.findByAdresPostcodeBetweenOrderByNaam(
			reeks.getVanpostcode(),
			reeks.getTotpostcode());
	}
	
	@Override
	public List<Filiaal> findNietAfgeschreven() {
	return filiaalRepository.findByWaardeGebouwNot(BigDecimal.ZERO);
	}
	@Override
	@ModifyingTransactionalServiceMethod
	public void afschrijven(Filiaal filiaal) {
	filiaal.afschrijven(); // je wijzigt een entity binnen een transactie.
	// JPA wijzigt dan automatisch het bijbehorende record bij de commit
	}
}
