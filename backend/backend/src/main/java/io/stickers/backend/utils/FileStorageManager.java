package io.stickers.backend.utils;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Dans la configuration spring, je définirais le répertoire
 * de base pour le stockage par ex : c:\stockage....
 * 
 * Quand un repository (ou autre) a besoin de stocker des fichiers
 * il demande a notre classe de le faire, en le fichier et surtout son contenu
 * il indique aussi qui a besoin de ce stockage (ex: daoAvatar)
 * si le rep n'existe pas, le FileStorageManager creer un repertoire pour chaque
 * utilisateur/dao du service
 * tous les noms de fichiers et de répertoires sont générés avec MD5 (hashage)
 * 
 * FileStorageManager:
 * ----------------------------------------------------------------------------------------------------
 * ---------- Configuration ---------------------------------------------------------------------------
 * ----------------------------------------------------------------------------------------------------
 * 
 * 
 * Ajouter dans "applicationContext.xml":
 * 
 * 		<bean id="fileStorageManager"
 * 			  class="io.stickers.backend.utils.FileStorageManager">
 * 			<property name="storageRoot"  value="C:\stockageStickers"/>
 * 		</bean>
 * 
 * 
 * Ajouter dans "pom.xml"
 * 
 * 		<dependency>
 * 			<groupId>commons-codec</groupId>
 * 			<artifactId>commons-codec</artifactId>
 * 			<version>1.10</version>
 * 		</dependency>
 * 
 * 
 * Créer le répertoire "C:\stockageStickers" en local s'il n'existe pas
 * 
 * ----------------------------------------------------------------------------------------------------
 * 
 * @author Bertrand COTE
 * @author Vincent COURTALON
 */
public class FileStorageManager {
	private static Logger log = LogManager.getLogger(FileStorageManager.class); 
	
	// reprtoire racine de notre stockage
	private File storageRoot;
	public File getStorageRoot() {return storageRoot;}
	public void setStorageRoot(File storageRoot) {this.storageRoot = storageRoot;}
	
	public FileStorageManager() {}
	
	// cette fonction servira a stocker le fichier a la demande d'un DAO
	public boolean saveFile(String entityName, int id, File f) {
		if (storageRoot == null
			 || !storageRoot.exists()
			 || !storageRoot.isDirectory()) {
			// probleme, pas de repertoire de base correct
			log.error("repertoire de base de stockage non disponnible");
			return false;
		}
		// generation du nom du répertoire pour notre entitée
		String md5name = DigestUtils.md5Hex(entityName);
		File entityRep = new File(storageRoot.getAbsolutePath() + File.separatorChar + md5name);
		
		// si le repertoire pour mon dao/entite n'eciste pas, je le cree
		if (!entityRep.exists())
			entityRep.mkdirs();
		
		if (!entityRep.isDirectory()) {
			log.error("impossible de creer la repertoire de stockage pour " + entityName);
			return false;
		}
		try {
			// copier, depuis le fichier temporaire uplaodé
			// vers un fichier nommé avec MD5(type + id)
			// dans le reprtoire personnel de ce DAO ->  MD5(entityName)
			Files.copy(f.toPath(), Paths.get(entityRep.getAbsolutePath(),
					"file_" + DigestUtils.md5Hex(entityName + id)),
					StandardCopyOption.REPLACE_EXISTING);
			return true;
		} catch (IOException e) { log.error(e);}
		return false;
	}

	public Optional<File> getFile(String entityName, int id) {
		if (storageRoot == null
				 || !storageRoot.exists()
				 || !storageRoot.isDirectory()) {
				// probleme, pas de repertoire de base correct
				log.error("repertoire de base de stockage non disponnible");
				return Optional.empty();
		}
		String md5name = DigestUtils.md5Hex(entityName);
		File entityRep = new File(storageRoot.getAbsolutePath() + File.separatorChar + md5name);
		
		// si le repertoire n'existe pas, pas de fichier a renvoyer
		if (!entityRep.exists() || !entityRep.isDirectory())
			return Optional.empty();
		
		File f = new File(entityRep.getAbsolutePath(),
				"file_" + DigestUtils.md5Hex(entityName + id));
		// si le fichier existe, le renvoyer
		if (f.exists() && f.isFile())
			return Optional.of(f);
		else
			return Optional.empty();
		
		
	}
	
}