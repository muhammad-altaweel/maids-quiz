package com.maids.maidsquiz.service;



import com.maids.maidsquiz.data.*;
import com.maids.maidsquiz.dto.SaleReportDto;
import com.maids.maidsquiz.dto.SaleUpdateDto;
import com.maids.maidsquiz.repository.ClientRepository;
import com.maids.maidsquiz.repository.ProductRepository;
import com.maids.maidsquiz.repository.SaleRepository;
import com.maids.maidsquiz.repository.SoldProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SaleService {
	private final SaleRepository saleRepository;
	private final ProductRepository productRepository;
	private final ClientRepository      clientRepository;
	private final SoldProductRepository soldProductRepository;

	public SaleService(SaleRepository saleRepository, ProductRepository productRepository, ClientRepository clientRepository, SoldProductRepository soldProductRepository) {
		this.saleRepository = saleRepository;
		this.productRepository = productRepository;
		this.clientRepository = clientRepository;
		this.soldProductRepository = soldProductRepository;
	}

	public List<Sale> getAllSales() {
		return saleRepository.findAll();
	}

	public Sale getSaleById(Long id) throws Exception {
		return saleRepository.findById(id)
				.orElseThrow(() -> new Exception("something"));
	}

	public Sale createSale( Long clientId) {
		Optional<Client> optionalClient = clientRepository.findById(clientId);
		Sale sale = new Sale();
		optionalClient.ifPresent(sale::setClient);
		return saleRepository.save(sale);
	}

	public String updateSale(SaleUpdateDto saleUpdateDto)  {
		Product product = productRepository.findById(saleUpdateDto.getProductId()).get();
		Sale sale = saleRepository.findById(saleUpdateDto.getSaleId()).get();
		SoldProduct soldProduct = new SoldProduct();
		soldProduct.setSale(sale);
		soldProduct.setProduct(product);
		soldProduct.setQuantity(saleUpdateDto.getQuantity());
		soldProduct.setPrice(product.getPrice());
		sale.setTotal(saleUpdateDto.getQuantity() * soldProduct.getPrice() + sale.getTotal());
		soldProductRepository.save(soldProduct);
		saleRepository.save(sale);
		return "saved sale:" + sale;
	}

	public void deleteSale(Long id) throws Exception{
		Sale sale = getSaleById(id);
		saleRepository.delete(sale);
	}

	public ResponseEntity<String> generateReport(SaleReportDto saleReportDto) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate =dateFormat.parse(saleReportDto.getStartDate());
		Date endDate = dateFormat.parse(saleReportDto.getEndDate());
		StringBuilder report = new StringBuilder();

		// Filter sales within the specified date range
		List<Sale> sales = saleRepository.findByCreationDateBetween(startDate, endDate);

		// Total number of sales
		int totalSales = sales.size();
		report.append("Total number of sales: ").append(totalSales).append("\n\n");

		// Total revenue
		double totalRevenue = sales.stream()
				.mapToDouble(Sale::getTotal)
				.sum();
		report.append("Total revenue: ").append(totalRevenue).append("\n\n");

		// Top-selling products
		List<SoldProduct> soldProducts = sales.stream()
				.flatMap(sale -> sale.getSoldProducts().stream()).toList();
		Map<Product, Long> topSellingProducts = soldProducts.stream()
				.collect(Collectors.groupingBy(SoldProduct::getProduct, Collectors.summingLong(SoldProduct::getQuantity)));
		report.append("Top-selling products:\n");
		int topProductsLimit = 5; // Adjust the limit as per your requirement
		int productRank = 1;
		for (Map.Entry<Product, Long> entry : topSellingProducts.entrySet()) {
			if (productRank > topProductsLimit) {
				break;
			}
			report.append("Rank ").append(productRank).append(": ").append(entry.getKey().getName()).append("\n");
			report.append("Quantity Sold: ").append(entry.getValue()).append("\n");
			report.append("\n");
			productRank++;
		}
		report.append("\n");

		// Top-performing sellers
		Map<User, Double> sellerRevenueMap = sales.stream()
				.collect(Collectors.groupingBy(Sale::getSeller, Collectors.summingDouble(Sale::getTotal)));
		List<User> topPerformingSellers = sellerRevenueMap.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.map(Map.Entry::getKey).toList();
		report.append("Top-performing sellers:\n");
		int topSellersLimit = 5; // Adjust the limit as per your requirement
		int sellerRank = 1;
		for (User seller : topPerformingSellers) {
			if (sellerRank > topSellersLimit) {
				break;
			}
			report.append("Rank ").append(sellerRank).append(": ").append(seller.getUsername()).append("\n");
			report.append("Total Revenue: ").append(sellerRevenueMap.get(seller)).append("\n");
			report.append("\n");
			sellerRank++;
		}
		report.append("\n");

		// Finally, return the report as a string
		return ResponseEntity.ok(report.toString());
	}
}
