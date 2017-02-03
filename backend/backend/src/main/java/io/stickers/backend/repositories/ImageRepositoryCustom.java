package io.stickers.backend.repositories;

import java.io.File;
import java.util.Optional;

import io.stickers.backend.metier.Image;

/**
 * 
 * @author Bertrand COTE
 *
 */
public interface ImageRepositoryCustom {

	boolean saveImage( Image image, File file );
	
	boolean saveImage( int id, File file );
	
	Optional<File> loadImage( int id );
}