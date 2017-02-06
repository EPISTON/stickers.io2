package io.stickers.backend.repositories;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import io.stickers.backend.metier.Image;

import io.stickers.backend.utils.FileStorageManager;

/**
 * 
 * @author Bertrand COTE
 *
 */
public class ImageRepositoryImpl  implements ImageRepositoryCustom {
	
	private static Logger log = LogManager.getLogger( ImageRepositoryImpl.class );
	
	@PersistenceContext
	private EntityManager em;
	
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
	public boolean saveImage(int id, InputStream s ) {
		return this.getFileStorageManager().saveFile( Image.class.getName(), id, s );
	}
	
	@Override
	public Optional<File> loadImage( int id ) {
		return getFileStorageManager().getFile( Image.class.getName(), id );
	}
	
	
	@Override
	@Transactional(readOnly=true)
	public Page<Image> findByEtiquetteList(List<Integer> etiquetteids, Pageable page, boolean includeEtiquettes) {

		String countRequest = "SELECT count(i.id) FROM Image AS i";
		String pageRequest = "SELECT DISTINCT i FROM Image AS i";
		if (includeEtiquettes)
			pageRequest += " LEFT JOIN FETCH i.etiquettes";
		
		StringBuilder ejbstring = new StringBuilder("");
		if (etiquetteids != null && etiquetteids.size() > 0)
		{
			int nb_params = etiquetteids.size();
			for (int i = 1; i <= nb_params; i++) {
				ejbstring.append(", IN(i.etiquettes) ta");
				ejbstring.append(i);
			}
			ejbstring.append(" WHERE");
			for (int i = 1; i <= nb_params; i++)
			{
				if (i > 1)
					ejbstring.append(" AND");
				ejbstring.append(" ta");
				ejbstring.append(i);
				ejbstring.append(".id = :tp");
				ejbstring.append(i);
			}
		}
		countRequest += ejbstring.toString();
		pageRequest += ejbstring.toString();
		log.info(countRequest);
		log.info(pageRequest);
		// creation des requettes de comptage et de donnée
		TypedQuery<Long> qCount = em.createQuery(countRequest, Long.class);
		TypedQuery<Image> qData = em.createQuery(pageRequest, Image.class);
		
		// binding des parametres
		int index_param = 1;
		for (int tid : etiquetteids)
		{
			qCount.setParameter("tp" + index_param, tid);
			qData.setParameter("tp" + index_param, tid);
			index_param++;
		}
		// sur la requette donnée, je fait la pagination
		qData.setFirstResult(page.getOffset());
		qData.setMaxResults(page.getPageSize());
		
		// je rerourne un objet Page contenant mes données, et le nombre total d'élément
		PageImpl<Image> pResult = new PageImpl<>(qData.getResultList(), page, qCount.getSingleResult());
		return pResult;
	}
	
	@Override
	@Transactional(readOnly=true)
	public Image findOneIncludingEtiquettes(int id) {
		TypedQuery<Image> q = em.createQuery("DELECT i FROM Image AS i LEFT JOIN FETCH i.etiquettes WHERE i.id=:imageId", Image.class);
		q.setParameter("imageId", id);
		return q.getSingleResult();
	}
	
}
































