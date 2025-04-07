package com.maids.maidsquiz.data;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name ="sale")
@Getter
@Setter
@Table
public class Sale {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long         id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	@ManyToOne
	private Client       client;
	@ManyToOne
	private User         seller;
	@JsonIgnore
	@OneToMany(mappedBy = "sale")
	List<SoldProduct> soldProducts;

	private double       total;


	@Override public String toString() {
		return "Sale{" +
				"id=" + id +
				", creationDate=" + creationDate +
				", client=" + client +
				", seller=" + seller +
				", soldProducts=" + soldProducts +
				", total=" + total +
				'}';
	}
}
