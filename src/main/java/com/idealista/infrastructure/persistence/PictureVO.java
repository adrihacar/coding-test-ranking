package com.idealista.infrastructure.persistence;

import java.util.ArrayList;
import java.util.List;

public class PictureVO {

	private Integer id;
	private String url;
	private String quality;

	public PictureVO() {}

	public PictureVO(Integer id, String url, String quality) {
		this.id = id;
		this.url = url;
		this.quality = quality;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public static List<String> getPicturesUrlFromId(List<Integer>idPictures) {
		InMemoryPersistence memory = new InMemoryPersistence();
		List<PictureVO>listPictures = memory.getPictures();
		List<String>resultUrl = new ArrayList<String>();

		for(int idPicture : idPictures) {
			for(PictureVO picture : listPictures) {
				if(idPicture == (int)picture.getId()) {
					resultUrl.add(picture.getUrl());
					break;
				}
			}
		}
		return resultUrl;

	}

}
