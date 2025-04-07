package com.maids.maidsquiz.repository;

import com.maids.maidsquiz.data.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
	List<Sale> findByCreationDateBetween(Date startDate, Date endDate);
}
