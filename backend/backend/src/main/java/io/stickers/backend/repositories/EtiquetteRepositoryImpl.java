package io.stickers.backend.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import io.stickers.backend.metier.Etiquette;

public class EtiquetteRepositoryImpl implements EtiquetteRepositoryCustom {
	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional(readOnly=true)
	public Page<Etiquette> findRelatedToImage(int imageId, Pageable page){
		TypedQuery<Etiquette> qdata = em.createQuery("select t from Etiquette as t left join t.images as a where a.id=:aid", Etiquette.class);
		TypedQuery<Long> qcount = em.createQuery("select count(t.id) from Etiquette as t left join t.images as a where a.id=:aid",	Long.class);
		qdata.setParameter("aid", imageId);
		qcount.setParameter("aid", imageId);
		qdata.setFirstResult(page.getOffset());
		qdata.setMaxResults(page.getPageSize());
		return new PageImpl<>(qdata.getResultList(), page, qcount.getSingleResult());

	}

}
