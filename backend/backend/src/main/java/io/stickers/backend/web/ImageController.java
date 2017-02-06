package io.stickers.backend.web;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;

import io.stickers.backend.metier.Image;
import io.stickers.backend.metier.Image.ImageOnly;
import io.stickers.backend.repositories.CarteRepository;
import io.stickers.backend.repositories.EtiquetteRepository;
import io.stickers.backend.repositories.ImageRepository;
import io.stickers.backend.repositories.StyleCarteRepository;
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
 * 
 * @controller -> declaration d'un controller spring mvc
 * l'equivalent d'une action struts2
 * 
 * @RequestMapping permet d'indiquer le dispatch d'une requette HTTP
 * ici, toutes les methodes de notre controller répondes a des urls commencant par "/rest"
 * 
 * @author Bertrand COTE
 */
@Controller
@RequestMapping("/rest")
public class ImageController {
	private static Logger log = LogManager.getLogger(ImageController.class);


	// @Autowired permet de demander a spring d'injecter uen dépendance
	// ici, un DAO/repository pour les images
	// attention, @autowired passe par l'attribut
	@Autowired
	private ImageRepository imageRepository;
	public ImageRepository getImageRepository() { return this.imageRepository; }
	public void setImageRepository(ImageRepository imageRepository) { this.imageRepository = imageRepository; }

	
	@Autowired
	private EtiquetteRepository etiquetteRepository;
	public EtiquetteRepository getEtiquetteRepository() { return this.etiquetteRepository; }
	public void setEtiquetteRepository(EtiquetteRepository tagRepository) { this.etiquetteRepository = tagRepository; }

	@Autowired
	private CarteRepository carteRepository;
	public CarteRepository getCarteRepository() { return carteRepository; }
	public void setCarteRepository(CarteRepository carteRepository) { this.carteRepository = carteRepository; }


	@Autowired
	private StyleCarteRepository styleCarteRepository;
	public StyleCarteRepository getStyleCarteRepository() { return styleCarteRepository; }
	public void setStyleCarteRepository(StyleCarteRepository styleCarteRepository) { this.styleCarteRepository = styleCarteRepository; }
	/*
	 * comme j'ai rajouté un @RequestMapping devant la méthode
	 * il concatene l'url fournie, et vérifie si d'autre contrainte sont spécifié
	 *  "/images" -> "/rest/images"
	 *  method= "RequestMethod.GET" -> ne répond qu'au requette GET
	 *  produces -> quel est le type de donnée retourné
	 *  
	 *  cette fonction renvoie la liste des images en base de donnée
	 *  attention, on parle des meta-informations, pas des fichiers images eux-meme
	 *  
	 *  la combinaison de @RespondeBody et "produces "application/json" indique
	 *  a spring que nous devons convertire la valeur renvoyée en json
	 *  
	 *  @JsonView permet ici d'indiquer a JackSon
	 *  que l'on ne veut que les informations de l'image en json, et pas les tags associés
	 *  
	 *  spring est capable de récupérer les informations de pagination
	 *  depuis la requette http, avec 2 arguments, la taille de la page, et son numéro
	 *  ils nous les injectera dans une PageRequest
	 *  c'est indiqué a spring via @PageableDefault
	 */
	@RequestMapping(value="/images", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	@JsonView(ImageOnly.class)
	public Page<Image> liste(@PageableDefault(page=0, size=10) Pageable pageRequest) {
		return JsonPageable.fromPage( this.getImageRepository().findAll(pageRequest));
	}
	
//	@RequestMapping(value="/images/full", method=RequestMethod.GET, produces="application/json")
//	@ResponseBody
//	@JsonView(AssetAndTags.class)
//	public Page<Image> listeFull(@PageableDefault(page=0, size=10) Pageable pageRequest) {
//		return JsonPageable.fromPage( getImageRepository().findByTagList(new ArrayList<>(), pageRequest, true) );
//	}
	
	
//	@RequestMapping(value="/images/staged", method=RequestMethod.GET, produces="application/json")
//	@ResponseBody
//	@JsonView(AssetOnly.class)
//	public Page<Image> listeStaged(@PageableDefault(page=0, size=10) Pageable pageRequest) {
//		List<Integer> ids = new ArrayList<Integer>();
//		ids.add(tagRepository.findByLibelle(TagRepository.UPLOADED).getId());
//		return JsonPageable.fromPage(getImageRepository().findByTagList(ids, pageRequest, false));
//	}

//	@RequestMapping(value="/images/stagedfull", method=RequestMethod.GET, produces="application/json")
//	@ResponseBody
//	@JsonView(AssetAndTags.class)
//	public Page<Image> listeStagedIncludingTags(@PageableDefault(page=0, size=10) Pageable pageRequest) {
//		List<Integer> ids = new ArrayList<Integer>();
//		ids.add(tagRepository.findByLibelle(TagRepository.UPLOADED).getId());
//		return JsonPageable.fromPage(getImageRepository().findByTagList(ids, pageRequest, true));
//	}

	
//	@RequestMapping(value="/images/staged/tagSearchFull/{ids:[0-9,]+}", method=RequestMethod.GET, produces="application/json")
//	@ResponseBody
//	@JsonView(AssetAndTags.class)
//	public Page<Image> findStagedByTagSearchFull(@PageableDefault(page=0, size=10) Pageable pageRequest,
//									 @PathVariable("ids") List<Integer> ids) {
//		log.info("recherche par tags " + ids + " demandée");
//		ids.add(tagRepository.findByLibelle(TagRepository.UPLOADED).getId());
//		return JsonPageable.fromPage(getImageRepository().findByTagList(ids, pageRequest, true));
//	}

	
	@RequestMapping(value="/images/{id:[0-9]+}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	@JsonView(ImageOnly.class)
	public Image findById(@PathVariable("id") int id) {
		log.info("image no " + id + " demandée");
		Image img = getImageRepository().findOne(id);
		log.info("image " + img.getId() + " name " + img.getFilename() );
		return img;
	}

//	@RequestMapping(value="/images/delete/{ids:[0-9,]+}", method=RequestMethod.DELETE, produces="application/json")
//	@ResponseBody
//	@JsonView(AssetOnly.class)
//	public HashMap<String, Object> removeById(@PathVariable("ids") List<Integer> ids) {
//		Image img = null;
//		int deletedCount = 0;
//		int deletedAsked = 0;
//		for (Integer id : ids) {
//			log.info("effacement image no " + id + " demandée");
//			deletedAsked++;
//			img = getImageRepository().findOne(id);
//			if (img != null) {
//				getImageRepository().delete(img);
//				getImageRepository().removeImageFile(img.getId());
//				getImageRepository().removeImageThumbFile(img.getId());
//				log.info("image " + img.getId() + " deleted");
//				deletedCount++;
//			}
//			else {
//				log.info("image " + id + " not found");
//			}
//		}
//		HashMap<String, Object> result = new HashMap<>();
//		result.put("deletedCount", deletedCount);
//		result.put("deletedAsked", deletedAsked);
//		return result;
//	}

	@RequestMapping(value="/images/etiquetteSearch/{ids:[0-9,]+}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	@JsonView(ImageOnly.class)
	public Page<Image> findByTagSearch(@PageableDefault(page=0, size=10) Pageable pageRequest,
									 @PathVariable("ids") List<Integer> ids) {
		log.info("recherche par tags " + ids + " demandée");
		return JsonPageable.fromPage(getImageRepository().findByEtiquetteList(ids, pageRequest, false));
	}
	
	@RequestMapping(value="/images/etiquetteSearchFull/{ids:[0-9,]+}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	@JsonView(ImageOnly.class)
	public Page<Image> findByTagSearchFull(@PageableDefault(page=0, size=10) Pageable pageRequest,
									 @PathVariable("ids") List<Integer> ids) {
		log.info("recherche par tags " + ids + " demandée");
		return JsonPageable.fromPage(getImageRepository().findByEtiquetteList(ids, pageRequest, true));
	}
	
	
	
	@RequestMapping(value="/images/save/{cartesId:[0-9]+}/{etiquettesId:[0-9]+}", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	@JsonView(ImageOnly.class)
	public Image save(
			@RequestBody Image image,
			@RequestParam("cartesId") Optional<List<Integer>> cartesId,
			@RequestParam("etiquettesId") Optional<List<Integer>> etiquettesId) {
		
		Image oldImage = getImageRepository().findOne( image.getId() );
		if ( oldImage == null )
			throw new HttpClientErrorException( HttpStatus.NOT_FOUND, "pas d'image existante correspondant a l'edition" );
		
		if( cartesId.isPresent() )
			cartesId.get().forEach( id -> image.addCarte( this.getCarteRepository().findOne( id ) ) );
		
		if( etiquettesId.isPresent() )
			etiquettesId.get().forEach( id -> image.addEtiquette( this.getEtiquetteRepository().findOne( id ) ) );
		
		oldImage.setNom( image.getNom() );
		oldImage.setFilename( image.getFilename() );
		oldImage.setContentType( image.getContentType() );
		oldImage.setSize( image.getSize() );
		
		return getImageRepository().save(oldImage);
	}
	
//	@RequestMapping(value="/images/saveunstage/{licenseId:[0-9]+}/{sourceId:[0-9]+}", method=RequestMethod.POST, produces="application/json")
//	@ResponseBody
//	@JsonView(ImageOnly.class)
//	public Image saveUnStage(@RequestBody Image image, @PathVariable("licenseId") int licenseId, @PathVariable("sourceId") int sourceId) {
//		Image oldImage = getImageRepository().findOneIncludingTags(image.getId());
//		if (oldImage == null) {
//			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "pas d'image existante correspondant a l'edition");
//		}
//		if (oldImage.getLicense().getId() != licenseId) {
//			oldImage.setLicense(getLicenseTypeRepository().findOne(licenseId));
//		}
//		if (oldImage.getSource().getId() != sourceId) {
//			oldImage.setSource(getAssetSourceRepository().findOne(sourceId));
//		}
//		oldImage.setName(image.getName());
//		oldImage.setDescription(image.getDescription());
//		oldImage.setFileName(image.getFileName());
//		oldImage.removeTag(getTagRepository().findByLibelle(TagRepository.UPLOADED));
//		return getImageRepository().save(oldImage);
//	}
	
	
	/*
	 *  fonction d'upload d'une nouvelle image
	 * 	file -> fichier image
	 *  licenseId -> license a associer
	 *  sourceId -> source a associer
	 *  tagsId -> etiquettes a associer
	 */
	@RequestMapping(value="/images/data", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	@JsonView(ImageOnly.class)
	public Image upload(
			@RequestParam("file") MultipartFile file,
			@RequestParam("cartesId") Optional<List<Integer>> cartesId,
			@RequestParam("etiquettesId") Optional<List<Integer>> etiquettesId) {
		Image savedImage = null;
		try {
			// Image(int id, String nom, String filename, String contentType, long size)
			
			Image newImage = new Image( 0, "", file.getOriginalFilename(), file.getContentType(), file.getSize() );
			savedImage = getImageRepository().save( newImage );
			
			this.getImageRepository().saveImage( savedImage.getId(), file.getInputStream () );
			
			final Image image = savedImage;
			
			if( cartesId.isPresent() )
				cartesId.get().forEach( id -> image.addCarte( this.getCarteRepository().findOne( id ) ) );
			
			if( etiquettesId.isPresent() )
				etiquettesId.get().forEach( id -> image.addEtiquette( this.getEtiquetteRepository().findOne( id ) ) );
			
			
			this.getImageRepository().save( image );
			
		} catch (IOException e) {
			log.error(e);
			throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "could not save uploaded image");
		}
		return savedImage;
	}
	
	@RequestMapping(value="/images/data/{id:[0-9]+}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<FileSystemResource> downloadImage(@PathVariable("id") int id) {
		Image img = imageRepository.findOne(id);
		if (img == null)
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "image not found");
		HttpHeaders respHeaders = new HttpHeaders();
		respHeaders.setContentType(MediaType.parseMediaType( img.getContentType()) );
		respHeaders.setContentLength(img.getSize());
		respHeaders.setContentDispositionFormData( "attachment", img.getFilename() );
		
		Optional<File> f =  getImageRepository().loadImage( img.getId() );
		if (f.isPresent()) {
			log.info("fichier pour image no " + id + " trouvé");
			FileSystemResource fsr = new FileSystemResource(f.get());
			return new ResponseEntity<FileSystemResource>(fsr, respHeaders, HttpStatus.OK);
		}
		else {
			log.info("fichier pour image no " + id + " introuvable");
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "image file not found");
		}
	}
	
//	@RequestMapping(value="/images/thumbdata/{id:[0-9]+}", method=RequestMethod.GET)
//	@ResponseBody
//	public ResponseEntity<FileSystemResource> downloadThumbImage(@PathVariable("id") int id) {
//		Image img = imageRepository.findOne(id);
//		HttpHeaders respHeaders = new HttpHeaders();
//		respHeaders.setContentType(MediaType.parseMediaType("image/jpeg"));
//		respHeaders.setContentDispositionFormData("attachment", "thumb_" + id + ".jpeg");
//		
//		Optional<File> f =  getImageRepository().getImageThumbFile(img.getId());
//		if (f.isPresent()) {
//			log.info("fichier pour thumbnail image no " + id + " trouvé");
//			FileSystemResource fsr = new FileSystemResource(f.get());
//			return new ResponseEntity<FileSystemResource>(fsr, respHeaders, HttpStatus.OK);
//		}
//		else {
//			log.info("fichier pour thumbnail image no " + id + " introuvable");
//			return new ResponseEntity<FileSystemResource>(HttpStatus.NOT_FOUND);
//		}
//	}
	
}