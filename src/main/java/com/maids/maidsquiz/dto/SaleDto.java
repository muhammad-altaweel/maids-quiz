package com.maids.maidsquiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class SaleDto {
	private List<Long> productsIds;
	private Long clientId;
}
