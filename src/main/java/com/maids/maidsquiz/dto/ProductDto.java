package com.maids.maidsquiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProductDto {
	private String name;
	private String description;
	private String category;
	private Integer  quantity;
	private Double price;
}
