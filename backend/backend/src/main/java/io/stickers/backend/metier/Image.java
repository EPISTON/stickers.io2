package io.stickers.backend.metier;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonView;
import io.stickers.backend.utils.JsonPageable;

/**
 *    Carte (n)----------(1) StyleCarte
 * 		(n)
 * 		 |
 * 		 |
 * 		 |
 * 		(1)
 * 	  Image (n)----------(n) Etiquette
 * 
 * @author Bertrand COTE
 * @author Berenice SREIFF
 *
 */
@Entity
public class Image {
	
	public static class ImageOnly extends JsonPageable.PaginatedResult {}

	@JsonView( { ImageOnly.class } )
	private int id;
	@JsonView( { ImageOnly.class } )
	private String nom;
	@JsonView( { ImageOnly.class } )
	private String filename;
	@JsonView( { ImageOnly.class } )
	private String contentType;
	@JsonView( { ImageOnly.class } )
	private long size;
	private Set <Etiquette>etiquettes;
	private Set <Carte> cartes;
	
	// =========================================
	// ========== Getters and Setters ==========
	// =========================================
	
	@Id
	@GeneratedValue
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public String getNom() { return nom; }
	public void setNom(String nom) { this.nom = nom; }

	public String getFilename() { return filename; }
	public void setFilename(String filename) { this.filename = filename; }

	public String getContentType() { return contentType; }
	public void setContentType(String contentType) { this.contentType = contentType; }

	public long getSize() { return size; }
	public void setSize(long size) { this.size = size; }
	
	@OneToMany(mappedBy="image")
	public Set<Carte> getCartes() { return cartes; }
	public void setCartes(Set<Carte> cartes) { this.cartes = cartes; }

	@ManyToMany
	public Set<Etiquette> getEtiquettes() { return etiquettes; }
	public void setEtiquettes(Set<Etiquette> etiquettes) { this.etiquettes = etiquettes; }
	
	// ===========================
	// ========== Utils ==========
	// ===========================
	
	public boolean addCarte( Carte carte ) {
		return this.getCartes().add( carte );
	}

	
	public boolean addEtiquette( Etiquette etiquette ) {
		return this.getEtiquettes().add( etiquette );
	}
	
	// ==================================
	// ========== Constructors ==========
	// ==================================

	public Image( int id, String nom, String filename, String contentType, long size ) {
		this.id = id;
		this.nom = nom;
		this.filename = filename;
		this.contentType = contentType;
		this.size = size;
	}
	public Image(){this(0,"","","",0);}

}





















