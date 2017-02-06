package io.stickers.backend.metier;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonView;

import io.stickers.backend.metier.Image.ImageOnly;
import io.stickers.backend.utils.JsonPageable;

@Entity
public class Etiquette {
	
	public static class EtiquetteOnly extends JsonPageable.PaginatedResult {}

	@JsonView( { ImageOnly.class } )
	private int id;
	@JsonView( { ImageOnly.class } )
	private String label;
	
	private Set <Image>images;
	
	
	@ManyToMany(mappedBy="etiquettes")
	public Set<Image> getImages() {
		return images;
	}
	public void setImages(Set<Image> images) {
		this.images = images;
	}
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Etiquette(int id, String label) {
		super();
		this.id = id;
		this.label = label;
	}
	
	public Etiquette (){this(0,"");}
	
	
	

	
	
}
