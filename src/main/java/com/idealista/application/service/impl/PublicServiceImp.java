package com.idealista.application.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.idealista.application.service.PublicService;
import com.idealista.infrastructure.api.PublicAd;
import com.idealista.infrastructure.persistence.AdVO;
import com.idealista.infrastructure.persistence.InMemoryPersistence;
import com.idealista.infrastructure.persistence.PictureVO;

@Service
public class PublicServiceImp implements PublicService{
	public List<PublicAd> getPublicAds(){
		InMemoryPersistence memory;
		List<AdVO> relevantAds;

		memory = new InMemoryPersistence();
		List<PublicAd>publicAds =new ArrayList<PublicAd>();
		relevantAds = memory.getRelevantAds();

		relevantAds.sort(Comparator.comparing(AdVO::getScore).reversed());

		for(AdVO ad : relevantAds) {
			publicAds.add(new PublicAd(ad));
		}

		return publicAds;
	}
}