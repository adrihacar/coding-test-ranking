package com.idealista.infrastructure.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.idealista.application.service.impl.PublicServiceImp;
import com.idealista.application.service.impl.QualityServiceImp;
import com.idealista.application.service.impl.ScoreServiceImp;

@RestController
public class AdsController {

	ScoreServiceImp scoreService = new ScoreServiceImp();
	PublicServiceImp publicService = new PublicServiceImp();
	QualityServiceImp qualityService = new QualityServiceImp();

	@GetMapping(value = "/quality")
	public ResponseEntity<List<QualityAd>> qualityListing() {
		List<QualityAd>qualityAds = qualityService.getQualityAds();
		return ResponseEntity.ok(qualityAds);
	}

	@GetMapping(value = "/public")
	public ResponseEntity<List<PublicAd>> publicListing() {
		List<PublicAd>publicAds = publicService.getPublicAds();
		return ResponseEntity.ok(publicAds);
	}

	@PostMapping(value = "/score")
	public ResponseEntity<Void> calculateScore() {
		scoreService.computeScore(); 
		return ResponseEntity.ok().build();
	}
}
