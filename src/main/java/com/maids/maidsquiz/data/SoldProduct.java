package com.maids.maidsquiz.data;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "sold_product")
@Getter
@Setter
@Table
public class SoldProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long quantity;

    private Double price;

    @ManyToOne
    @JoinColumn(name="product_id", nullable=true)
    private Product product;

    @ManyToOne
    @JoinColumn(name="sale_id", nullable=true)
    private Sale sale;

}
