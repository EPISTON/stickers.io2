package io.stickers.backend.repositories;

import java.io.File;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import io.stickers.backend.metier.Image;

import io.stickers.backend.utils.FileStorageManager;

/**
 * 
 * @author Bertrand COTE
 *
 */
public class ImageRepositoryImpl  implements ImageRepositoryCustom {
	
	private static Logger log = LogManager.getLogger( ImageRepositoryImpl.class );
	
	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private FileStorageManager fileStorageManager;

	// =========================================
	// ========== Getters and setters ==========
	// =========================================
	
	public FileStorageManager getFileStorageManager() { return fileStorageManager; }
	public void setFileStorageManager(FileStorageManager fileStorageManager) { this.fileStorageManager = fileStorageManager; }
	
	public ImageRepository getImageRepository() { return imageRepository; }
	public void setImageRepository(ImageRepository imageRepository) { this.imageRepository = imageRepository; }
	
	// ===================================================
	// ========== Custom methods implementation ==========
	// ===================================================
	
	@Override
	@Transactional
	public boolean saveImage( Image image, File file ) {
		
		Image savedImage = this.imageRepository.save( image );
		return this.fileStorageManager.saveFile( Image.class.getName(), savedImage.getId(), file );
	}

	@Override
	public boolean saveImage( int id, File file ) { 
		return getFileStorageManager().saveFile( Image.class.getName(), id, file );
	}
	
	@Override
	public Optional<File> loadImage( int id ) {
		return getFileStorageManager().getFile( Image.class.getName(), id );
	}
	
}
































