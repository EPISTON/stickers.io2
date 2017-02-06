package io.stickers.backend.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import io.stickers.backend.metier.Image;

/**
 * 
 * @author Bertrand COTE
 *
 */
public interface ImageRepository extends PagingAndSortingRepository<Image, Integer>, 
										 ImageRepositoryCustom {
	
}
