package io.stickers.backend.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;


import com.fasterxml.jackson.annotation.JsonView;

import io.stickers.backend.metier.Carte;
import io.stickers.backend.metier.StyleCarte;
import io.stickers.backend.metier.StyleCarte.StyleCarteOnly;
import io.stickers.backend.repositories.CarteRepository;
import io.stickers.backend.repositories.StyleCarteRepository;



/**
 * 
 * @author Eric PISTON
 *
 */

@Controller
@RequestMapping("/rest")
public class StyleCarteController {
	
	@Autowired
	private StyleCarteRepository styleCarteRepository;
	public StyleCarteRepository getStyleCarteRepository() {return styleCarteRepository;}
	public void setStyleCarteRepository(StyleCarteRepository styleCarteRepository) {this.styleCarteRepository = styleCarteRepository;}
	
	@Autowired
	private CarteRepository carteRepository;
	public CarteRepository getCarteRepository() {return carteRepository;}
	public void setCarteRepository(CarteRepository carteRepository) {this.carteRepository = carteRepository;}
	
	
	
	@RequestMapping(value="/stylecarte/save", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	@JsonView(StyleCarteOnly.class)
	public StyleCarte save(@RequestBody StyleCarte styleCarte){
		StyleCarte oldStyle=styleCarteRepository.findOne(styleCarte.getId());
		if (oldStyle == null) {
			return getStyleCarteRepository().save(styleCarte);
		}
		else {
			oldStyle.setNom(styleCarte.getNom());
			oldStyle.setCouleur(styleCarte.getCouleur());
			oldStyle.setPolice(styleCarte.getPolice());
			oldStyle.setTemplate(styleCarte.getTemplate());
		return getStyleCarteRepository().save(styleCarte);
		}
	
	}
	

}
