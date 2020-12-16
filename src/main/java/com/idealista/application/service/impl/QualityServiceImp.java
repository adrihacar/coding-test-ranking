package com.idealista.application.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.idealista.application.service.QualityService;
import com.idealista.infrastructure.api.QualityAd;
import com.idealista.infrastructure.persistence.AdVO;
import com.idealista.infrastructure.persistence.InMemoryPersistence;

@Service
public class QualityServiceImp implements QualityService{
	public List<QualityAd> getQualityAds(){
		InMemoryPersistence memory;
		List<AdVO> notRelevantAds;

		memory = new InMemoryPersistence();
		List<QualityAd>qualityAds =new ArrayList<QualityAd>();
		notRelevantAds = memory.getNotRelevantAds();

		for(AdVO ad : notRelevantAds) {
			qualityAds.add(new QualityAd(ad));
		}

		return qualityAds;
	}
}
