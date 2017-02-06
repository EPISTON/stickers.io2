package io.stickers.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.stickers.backend.metier.Etiquette;

public interface EtiquetteRepositoryCustom {
	Page<Etiquette> findRelatedToImage(int imageId, Pageable page);

}
