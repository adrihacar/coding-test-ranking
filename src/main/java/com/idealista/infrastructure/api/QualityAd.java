package com.idealista.infrastructure.api;

import java.util.Date;
import java.util.List;
import com.idealista.infrastructure.persistence.AdVO;
import com.idealista.infrastructure.persistence.PictureVO;

public class QualityAd {

	private Integer id;
	private String typology;
	private String description;
	private List<String> pictureUrls;
	private Integer houseSize;
	private Integer gardenSize;
	private Integer score;
	private Date irrelevantSince;

	/*Constructor creates a QualityAd based on AdVO*/
	public QualityAd(AdVO ad) {
		this.id = ad.getId();
		this.typology= ad.getTypology();
		this.description= ad.getDescription();
		this.pictureUrls=PictureVO.getPicturesUrlFromId((ad.getPictures()));
		this.houseSize = ad.getHouseSize();
		this.gardenSize = ad.getGardenSize();
		this.score = ad.getScore();
		this.irrelevantSince = new Date(ad.getIrrelevantSince().getTime());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTypology() {
		return typology;
	}

	public void setTypology(String typology) {
		this.typology = typology;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getPictureUrls() {
		return pictureUrls;
	}

	public void setPictureUrls(List<String> pictureUrls) {
		this.pictureUrls = pictureUrls;
	}

	public Integer getHouseSize() {
		return houseSize;
	}

	public void setHouseSize(Integer houseSize) {
		this.houseSize = houseSize;
	}

	public Integer getGardenSize() {
		return gardenSize;
	}

	public void setGardenSize(Integer gardenSize) {
		this.gardenSize = gardenSize;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Date getIrrelevantSince() {
		return irrelevantSince;
	}

	public void setIrrelevantSince(Date irrelevantSince) {
		this.irrelevantSince = irrelevantSince;
	}
}
