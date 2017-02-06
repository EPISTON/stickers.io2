package io.stickers.backend.metier;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonView;
import io.stickers.backend.utils.JsonPageable;

@Entity
public class StyleCarte {
	
	public static class StyleCarteOnly extends JsonPageable.PaginatedResult {}


	@JsonView( { StyleCarteOnly.class } )
	private int id;
	@JsonView( { StyleCarteOnly.class } )
	private String nom;
	@JsonView( { StyleCarteOnly.class } )
	private String police;
	@JsonView( { StyleCarteOnly.class } )
	private String couleur;
	@JsonView( { StyleCarteOnly.class } )
	private String template; 

	private Set <Carte> cartes;




	@OneToMany(mappedBy="stylecartes")
	public Set<Carte> getCartes() {
		return cartes;
	}
	public void setCartes(Set<Carte> cartes) {
		this.cartes = cartes;
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;}
	public void setId(int id) {
		this.id = id;}
	public String getNom() {
		return nom;}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPolice() {
		return police;
	}

	public void setPolice(String police) {
		this.police = police;
	}
	public String getCouleur() {
		return couleur;
	}
	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	public StyleCarte(int id, String nom, String police, String couleur, String template) {
		super();
		this.id = id;
		this.nom = nom;
		this.police = police;
		this.couleur = couleur;
		this.template = template;
	}


	public StyleCarte(){this(0,"","","","");}


}




