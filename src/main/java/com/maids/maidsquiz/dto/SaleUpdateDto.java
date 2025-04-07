package com.maids.maidsquiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SaleUpdateDto {
	private Long saleId;
	private Long productId;
	private Long quantity;
}
