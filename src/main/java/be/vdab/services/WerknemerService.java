package be.vdab.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import be.vdab.entities.Werknemer;

public interface WerknemerService
{
// List<Werknemer> findAll();
public Page<Werknemer> findAll(Pageable pageable);
}