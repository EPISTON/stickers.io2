package io.stickers.backend.metier;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.ManyToOne;

@Entity
public class Carte {


	private int id;
	private String nom;
	private String prenom;
	private String email;
	private String titre;
	private String adresse;
	private String telephone;
	private StyleCarte stylecartes;
	private  Image image;




	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	@ManyToOne
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
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

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@ManyToOne
	public StyleCarte getStylecartes() {
		return stylecartes;
	}

	public void setStylecartes(StyleCarte stylecartes) {
		this.stylecartes = stylecartes;
	}

	public Carte(int id, String nom, String prenom, String email, String titre, String adresse, String telephone
			) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.titre = titre;
		this.adresse = adresse;
		this.telephone = telephone;

	}


	public Carte(){this(0,"","","","","","");}

}
