package io.stickers.backend.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import io.stickers.backend.metier.Image;

/**
 * 
 * @author Bertrand COTE
 *
 */
public interface ImageRepository extends PagingAndSortingRepository<Image, Integer>, 
										 ImageRepositoryCustom {
	@Query("select a from Image as a left join fetch a.etiquettes where a.id=:assetId")
	Image findOneIncludingEtiquettes(@Param("assetId") int assetId);

}
