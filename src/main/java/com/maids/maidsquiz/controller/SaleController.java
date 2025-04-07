package com.maids.maidsquiz.controller;

import com.maids.maidsquiz.data.Product;
import com.maids.maidsquiz.data.Sale;
import com.maids.maidsquiz.dto.SaleDto;
import com.maids.maidsquiz.dto.SaleReportDto;
import com.maids.maidsquiz.dto.SaleUpdateDto;
import com.maids.maidsquiz.service.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping ("/sales")
public class SaleController {
	private final SaleService saleService;

	public SaleController(SaleService saleService) {
		this.saleService = saleService;
	}

	@GetMapping
	public List<Sale> getAllSales() {
		return saleService.getAllSales();
	}

	@PostMapping
	public ResponseEntity<String> createSale(@RequestBody SaleDto saleDto) {
		try {
			Sale createdSale = saleService.createSale(saleDto.getClientId());
			return ResponseEntity.status(HttpStatus.CREATED).body("Sale created successfully with ID: " + createdSale.getId());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create sale: " + e.getMessage());
		}
	}

	@PostMapping(value = "/update")
	public ResponseEntity<String> addToSale(@RequestBody SaleUpdateDto saleUpdateDto) throws Exception {
		return ResponseEntity.ok(saleService.updateSale(saleUpdateDto));
	}

	@GetMapping(value = "/report")
	public ResponseEntity<String> getReport(@RequestBody SaleReportDto saleReportDto) throws ParseException {
		return saleService.generateReport(saleReportDto);
	}
}
