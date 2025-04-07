package com.maids.maidsquiz.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name="client")
@NoArgsConstructor
@Setter
@Getter
@Table
public class Client {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String lastName;
	private String mobile;
	private String email;
	private String address;
	@JsonIgnore
	@OneToMany
	private List<Sale> sales;

	@Override public String toString() {
		return "Client{" +
				"id=" + id +
				", name='" + name + '\'' +
				", lastName='" + lastName + '\'' +
				", mobile='" + mobile + '\'' +
				", email='" + email + '\'' +
				", address='" + address + '\'' +
				'}';
	}
}
