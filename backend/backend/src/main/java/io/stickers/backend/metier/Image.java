package io.stickers.backend.metier;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Image {


	private int id;
	private String nom;
	private String filename;
	private String contentType;
	private long size;
	private Set <Etiquette>etiquettes;
	private Set <Carte> cartes;




	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
	@OneToMany(mappedBy="image")
	public Set<Carte> getCartes() {
		return cartes;
	}

	public void setCartes(Set<Carte> cartes) {
		this.cartes = cartes;
	}



	@ManyToMany
	public Set<Etiquette> getEtiquettes() {
		return etiquettes;
	}

	public void setEtiquettes(Set<Etiquette> etiquettes) {
		this.etiquettes = etiquettes;
	}

	public Image(int id, String nom, String filename, String contentType, long size) {
		super();
		this.id = id;
		this.nom = nom;
		this.filename = filename;
		this.contentType = contentType;
		this.size = size;
	}

	public Image(){this(0,"","","",0);}

}
