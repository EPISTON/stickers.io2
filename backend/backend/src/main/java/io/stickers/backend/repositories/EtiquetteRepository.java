package io.stickers.backend.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import io.stickers.backend.metier.Etiquette;

/**
 * 
 * @author Eric PISTON
 *
 */
public interface EtiquetteRepository extends PagingAndSortingRepository<Etiquette, Integer>, EtiquetteRepositoryCustom {

}
