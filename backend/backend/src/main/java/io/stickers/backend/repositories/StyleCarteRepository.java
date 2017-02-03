package io.stickers.backend.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import io.stickers.backend.metier.StyleCarte;

/**
 * 
 * @author Eric PISTON
 *
 */
public interface StyleCarteRepository extends PagingAndSortingRepository<StyleCarte, Integer> {

}
