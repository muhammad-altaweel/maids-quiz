package com.maids.maidsquiz.repository;

import com.maids.maidsquiz.data.Client;
import com.maids.maidsquiz.data.SoldProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoldProductRepository extends JpaRepository<SoldProduct, Long> {
}

