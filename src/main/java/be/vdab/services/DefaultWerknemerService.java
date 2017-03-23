package be.vdab.services;

import java.util.List;

import org.springframework.data.domain.Sort;

import be.vdab.entities.Werknemer;
import be.vdab.repositories.WerknemerRepository;

@ReadOnlyTransactionalService
class DefaultWerknemerService implements WerknemerService
{
private final WerknemerRepository werknemerRepository;
DefaultWerknemerService(WerknemerRepository werknemerRepository)
{
this.werknemerRepository = werknemerRepository;
}
@Override
public List<Werknemer> findAll()
{
return werknemerRepository.findAll(new Sort("familienaam", "voornaam")); 
}
}