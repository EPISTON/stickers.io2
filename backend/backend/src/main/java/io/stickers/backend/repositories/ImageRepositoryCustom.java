package io.stickers.backend.repositories;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.stickers.backend.metier.Image;

/**
 * 
 * @author Bertrand COTE
 *
 */
public interface ImageRepositoryCustom {

	boolean saveImage( Image image, File file );

	boolean saveImage(int id, InputStream s);
	
	boolean saveImage( int id, File file );
	
	Optional<File> loadImage( int id );
	
	
	// la recherche par liste de tag
	Page<Image> findByEtiquetteList( List<Integer> etiquetteids, Pageable page, boolean includeEtiquettes);
	Image findOneIncludingEtiquettes(int id);
}