package com.maids.maidsquiz.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity (name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table
public class Product {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long              id;
	@CreatedDate
	private Date              creationDate;
	private String            name;
	private String            description;
	private String            category;
	private Integer           quantity;
	private Double            price;
	@JsonIgnore
	@OneToMany (mappedBy = "product")
	private List<SoldProduct> soldProducts;
}
