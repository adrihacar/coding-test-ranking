package com.idealista.application.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.idealista.application.service.ScoreService;
import com.idealista.infrastructure.persistence.AdVO;
import com.idealista.infrastructure.persistence.InMemoryPersistence;
import com.idealista.infrastructure.persistence.PictureVO;

@Service
public class ScoreServiceImp implements ScoreService{
	InMemoryPersistence memory;
	List<AdVO> ads;
	List<PictureVO> pictures;

	private static final int NO_PHOTO = -10; 
	private static final int PHOTO_HD = 20;
	private static final int PHOTO_NO_HD = 10;
	private static final int DESCRIPTIVE_TEXT = 5;
	private static final int FLAT_WORDS_TEXT_1 = 10;
	private static final int FLAT_WORDS_TEXT_2 = 30;
	private static final int CHALET_WORDS_TEXT = 20;
	private static final int KEY_WORDS_TEXT = 5;
	private static final int COMPLETE = 40;


	public void computeScore(){
		memory = new InMemoryPersistence();
		ads = memory.getAds();
		pictures = memory.getPictures();

		for(AdVO ad : ads) {
			scoreToZero(ad);
			computePhotoPoints(ad);
			computeDescriptionPoints(ad);
			computeCompletnessPoints(ad);
			boundPoints(ad);
			computeRelevancy(ad);
		}
	}


	/*Init score at zero */
	private void scoreToZero(AdVO ad) {
		for(int i=0; i<ads.size(); i++) {
			ad.setScore(0);
		}
	}

	private void computePhotoPoints(AdVO ad) {
		List<Integer>adPictures = ad.getPictures();
		PictureVO pic;
		if(adPictures.isEmpty()) {
			ad.setScore(ad.getScore()+NO_PHOTO);
		}else {
			for(int idPicture: adPictures) {
				pic = memory.getPictureById(idPicture);
				if(pic.getQuality().equals("HD")) {
					ad.setScore(ad.getScore()+PHOTO_HD);
				}else{
					ad.setScore(ad.getScore()+PHOTO_NO_HD);
				}
			}
		}

	}


	private void computeDescriptionPoints(AdVO ad) {
		int points =0;
		if(ad.getDescription() != null && !ad.getDescription().isEmpty()) {
			ad.setScore(ad.getScore()+DESCRIPTIVE_TEXT);
			/*Check if is it a flat or chalet to add extra points based on description size*/
			if(ad.getTypology().equalsIgnoreCase("CHALET") || ad.getTypology().equalsIgnoreCase("FLAT")) {
				points = extraDescriptionSizePoints(ad.getDescription(), ad.getTypology());
				points += extraDescriptionWordsPoints(ad.getDescription());
				ad.setScore(ad.getScore()+points); 
			}
		}
	}

	/*Returns the extra points based on the description size*/
	private int extraDescriptionSizePoints(String description, String topology){
		int extraPoints, numWords;
		String[]words;

		extraPoints =0;
		words = description.split("\\s+");
		numWords = words.length;

		if(topology.equalsIgnoreCase("CHALET")) {
			if(numWords > 50) {
				extraPoints = CHALET_WORDS_TEXT;
			}
		}else if(topology.equalsIgnoreCase("FLAT")) {
			if(numWords >= 50) {
				extraPoints = FLAT_WORDS_TEXT_2;
			}else if(numWords >= 20) {
				extraPoints = FLAT_WORDS_TEXT_1;
			}
		}



		return extraPoints;

	}

	private int extraDescriptionWordsPoints(String description) {
		int extraPoints =0;

		String[]pointWords= {"luminoso","nuevo","céntrico", "reformado", "atico"};

		for(int i=0; i<pointWords.length;i++) {
			if(description.toLowerCase().contains(pointWords[i])) {
				extraPoints+=KEY_WORDS_TEXT;
			}
		}

		return extraPoints;

	}

	private void computeCompletnessPoints(AdVO ad){
		boolean complete = true;

		/*Check if it has description and it is not a garage*/
		if(!ad.getTypology().equalsIgnoreCase("GARAGE") && (ad.getDescription() == null || ad.getDescription().isEmpty())) {
			complete = false; 
		}
		/*Check at least there is one photo*/
		if(ad.getPictures().isEmpty()) {
			complete = false;
		}
		/*Check topology*/
		if(ad.getTypology().equalsIgnoreCase("FLAT")) {
			if(ad.getHouseSize() == null) {
				complete =false;
			}
		}else if(ad.getTypology().equalsIgnoreCase("CHALET")) {
			if(ad.getHouseSize() == null || ad.getGardenSize() == null) {
				complete = false;
			}
		}

		if(complete) {
			ad.setScore(ad.getScore()+COMPLETE); 
		}


	}

	/*Makes sure that the points are between 0-100*/
	private void boundPoints(AdVO ad) {
		if(ad.getScore()>100) {
			ad.setScore(100);
		}else if(ad.getScore()<0) {
			ad.setScore(0);
		}
	}


	private void computeRelevancy(AdVO ad) {
		if(ad.getScore() < 40) {
			ad.setIrrelevantSince(new Date());
		}

	}

}
