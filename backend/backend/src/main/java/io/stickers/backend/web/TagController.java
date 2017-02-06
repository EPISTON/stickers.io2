package io.stickers.backend.web;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
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

import io.stickers.backend.metier.Etiquette;
import io.stickers.backend.metier.Etiquette.EtiquetteOnly;
import io.stickers.backend.metier.Image;
import io.stickers.backend.repositories.EtiquetteRepository;
import io.stickers.backend.repositories.ImageRepository;
import io.stickers.backend.utils.JsonPageable;

@Controller
@RequestMapping(value="/tags")
public class TagController {

	private static Logger log = LogManager.getLogger(TagController.class);
	
	@Autowired
	private EtiquetteRepository etiquetteRepository;
	public EtiquetteRepository getEtiquetteRepository() {	return etiquetteRepository;}
	public void setEtiquetteRepository(EtiquetteRepository etiquetteRepository) {this.etiquetteRepository = etiquetteRepository;}

	@Autowired
	private ImageRepository imageRepository;
	public ImageRepository getImageRepository() {return imageRepository;}
	public void setImageRepository(ImageRepository imageRepository) {this.imageRepository = imageRepository;}
	
	@RequestMapping(value="/", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	@JsonView(EtiquetteOnly.class)
	public Page<Etiquette> liste(@PageableDefault(page=0, size=10, sort="label", direction=Direction.ASC) Pageable pageRequest) {
		return JsonPageable.fromPage(getEtiquetteRepository().findAll(pageRequest));
	}

	// recuperer le tag d'identifiant id
	@RequestMapping(value="/{id:[0-9]+}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	@JsonView(EtiquetteOnly.class)
	public Etiquette findById(@PathVariable("id")  int id) {
		return getEtiquetteRepository().findOne(id);
	}
	

	// trouver tag lié a une asset particuliere
	@RequestMapping(value="/findRelated/{assetId:[0-9]+}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	@JsonView(EtiquetteOnly.class)
	public Page<Etiquette> findRelated(@PathVariable("assetId") int assetId, @PageableDefault(page=0, size=10) Pageable pageRequest) {
		return JsonPageable.fromPage(getEtiquetteRepository().findRelatedToImage(assetId, pageRequest));
	}


	@RequestMapping(value="/", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	@JsonView(EtiquetteOnly.class)
	public Etiquette save(@RequestBody Etiquette tag) {
		Etiquette original = getEtiquetteRepository().findOne(tag.getId());
		if (original == null) {
			return getEtiquetteRepository().save(tag);
		}
		original.setLabel(tag.getLabel());
		return getEtiquetteRepository().save(original);
	}
	
	@RequestMapping(value="/add/{assetId:[0-9]+}/{tagId:[0-9]+}", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	@JsonView(EtiquetteOnly.class)
	public Etiquette AddTag(@PathVariable("assetId") int assetId, @PathVariable("tagId") int tagId) {
		Etiquette t = getEtiquetteRepository().findOne(tagId);
		if (t == null) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "tag inconnu");
		}

			log.info("ajout tag no " + tagId + "  sur asset no " + assetId);
			Image a = getImageRepository().findOneIncludingEtiquettes(assetId);
			if (a != null) {
				a.addEtiquette(t);
				getImageRepository().save(a);
			}
			else {
				log.info("ajout impossible, asset inexistant");
			}
		return t;
	}

	@RequestMapping(value="/remove/{assetId:[0-9]+}/{tagId:[0-9]+}", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	@JsonView(EtiquetteOnly.class)
		public Etiquette removeTag(@PathVariable("assetId") int assetId, @PathVariable("tagId") int tagId) {
		Etiquette t = getEtiquetteRepository().findOne(tagId);
		if (t == null) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "tag inconnu");
		}
			log.info("retrait tag no " + tagId + "  sur asset no " + assetId);
			Image a = getImageRepository().findOneIncludingEtiquettes(assetId);
			if (a != null) {
				a.removeEtiquette(t);
				getImageRepository().save(a);
			}
			else {
				log.info("retrait impossible,asset inexistant");
			}
		return t;
	}

}