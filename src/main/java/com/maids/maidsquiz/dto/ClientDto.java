package com.maids.maidsquiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ClientDto {
	private String name;
	private String lastName;
	private String mobile;
	private String email;
	private String address;
}
