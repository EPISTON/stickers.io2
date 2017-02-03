package io.stickers.backend.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.stickers.backend.metier.Image;

/**
 * 
 * @author Bertrand COTE
 *
 */
public interface ImageRepository extends CrudRepository<Image, Integer>, 
										 ImageRepositoryCustom {
	
}
