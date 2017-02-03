package io.stickers.backend.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import io.stickers.backend.metier.Carte;

/**
 * 
 * @author Eric PISTON
 *
 */
public interface CarteRepository extends PagingAndSortingRepository<Carte, Integer> {

}
