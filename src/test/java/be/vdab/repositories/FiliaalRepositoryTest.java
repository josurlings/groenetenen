package be.vdab.repositories;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import be.vdab.entities.Filiaal;
import be.vdab.valueobjects.Adres;
import datasource.TestDataSourceConfig;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes={TestDataSourceConfig.class,RepositoriesConfig.class,})
@Transactional // omringt elke test met een transactie, na de test rollback
public class FiliaalRepositoryTest 
{
@Autowired private FiliaalRepository filiaalRepository;
@Test
public void create()
{
	Filiaal filiaal = new Filiaal("TestNaam", true, BigDecimal.ONE,
	LocalDate.now(), new Adres("Straat", "HuisNr", 1000, "Gemeente"));
					filiaalRepository.create(filiaal);
	assertNotEquals(0, filiaal.getId()); // id moet autonumber hebben:
}
}
