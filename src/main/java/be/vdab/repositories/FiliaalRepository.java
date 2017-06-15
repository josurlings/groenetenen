package be.vdab.repositories;

import java.math.BigDecimal;
import java.util.List;
//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import be.vdab.entities.Filiaal;
//import be.vdab.valueobjects.PostcodeReeks;


public interface FiliaalRepository extends JpaRepository<Filiaal, Long>
{ 

	List<Filiaal> findByAdresPostcodeBetweenOrderByNaam(int van, int tot); 
	
	List<Filiaal> findByWaardeGebouwNot(BigDecimal waarde);
	
//	List<Filiaal> findMetHoogsteWaardeGebouw();
	
	
//	void create(Filiaal filiaal);
	
//	Optional<Filiaal> read(long id);
	
//	void update(Filiaal filiaal);
	
//	void delete(long id);
	
//	List<Filiaal> findAll();
	
//	long findAantalFilialen();
	
	// het aantal werknemers van een filiaal:
//	long findAantalWerknemers(long id);
	
//	List<Filiaal> findByPostcodeReeks(PostcodeReeks reeks);
	
		
}
