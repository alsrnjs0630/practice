package com.project.domain;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Item {
	private Integer itemId;				// 상품 ID
	private String itemName;			// 상품명
	private Integer price;				// 상품가격
	private String description;			// 상품설명
	private MultipartFile picture;		// 상품이미지
	private String pictureUrl;			// 이미지 url
	private MultipartFile preview;		// 이미지 미리보기
	private String previewUrl;			// 미리보기 url
}
