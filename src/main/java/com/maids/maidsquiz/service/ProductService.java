package com.maids.maidsquiz.service;

import com.maids.maidsquiz.data.Product;
import com.maids.maidsquiz.data.SoldProduct;
import com.maids.maidsquiz.dto.ProductDto;
import com.maids.maidsquiz.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Product getProductById(Long id) throws Exception {
		return productRepository.findById(id)
				.orElseThrow(() -> new Exception("Product not found"));
	}

	public Product createProduct(ProductDto product) {
		Product newProduct = new Product();
		newProduct.setName(product.getName());
		newProduct.setDescription(product.getDescription());
		newProduct.setCategory(product.getCategory());
		newProduct.setQuantity(product.getQuantity());
		newProduct.setPrice(product.getPrice());
		return productRepository.save(newProduct);
	}

	public Product updateProduct(Long id, ProductDto updatedProduct) throws Exception {
		Product product = getProductById(id);
		product.setName(updatedProduct.getName());
		product.setDescription(updatedProduct.getDescription());
		product.setCategory(updatedProduct.getCategory());
		product.setQuantity(updatedProduct.getQuantity());
		product.setPrice(updatedProduct.getPrice());
		return productRepository.save(product);
	}

	public void deleteProduct(Long id) throws Exception {
		Product product = getProductById(id);
		productRepository.delete(product);
	}


	public ResponseEntity<String> generateReport(){
		StringBuilder report = new StringBuilder();

		List<Product> products = productRepository.findAll();

		// Total number of products
		int totalProducts = products.size();
		report.append("Total number of products: ").append(totalProducts).append("\n");

		// Products with name, price, and quantity
		for (Product product : products) {
			report.append("Product: ").append(product.getName()).append("\n");
			report.append("Price: ").append(product.getPrice()).append("\n");
			report.append("Quantity: ").append(product.getQuantity()).append("\n");
			report.append("\n");
		}

		// Maximum price
		Double maxPrice = products.stream()
				.map(Product::getPrice)
				.max(Double::compare)
				.orElse(0.0);
		report.append("Maximum price: ").append(maxPrice).append("\n");

		// Minimum price
		Double minPrice = products.stream()
				.map(Product::getPrice)
				.min(Double::compare)
				.orElse(0.0);
		report.append("Minimum price: ").append(minPrice).append("\n");

		// Sales performance analysis
		List<SoldProduct> soldProducts = products.stream()
				.flatMap(product -> product.getSoldProducts().stream())
				.collect(Collectors.toList());

		// Calculate total sales
		double totalSales = soldProducts.stream()
				.mapToDouble(soldProduct -> soldProduct.getQuantity() * soldProduct.getPrice())
				.sum();

		// Calculate average price
		double averagePrice = soldProducts.stream()
				.mapToDouble(SoldProduct::getPrice)
				.average()
				.orElse(0.0);

		// Calculate revenue
		double revenue = soldProducts.stream()
				.mapToDouble(soldProduct -> soldProduct.getPrice() * soldProduct.getQuantity())
				.sum();

		// Calculate best-selling product
		SoldProduct bestSellingProduct = soldProducts.stream()
				.max(Comparator.comparingLong(SoldProduct::getQuantity))
				.orElse(null);

		// Calculate highest revenue product
		SoldProduct highestRevenueProduct = soldProducts.stream()
				.max(Comparator.comparingDouble(soldProduct -> soldProduct.getPrice() * soldProduct.getQuantity()))
				.orElse(null);

		// Add the sales performance analysis to the report
		report.append("Sales Performance Analysis\n");
		report.append("Total Sales: ").append(totalSales).append("\n");
		report.append("Average Price: ").append(averagePrice).append("\n");
		report.append("Revenue: ").append(revenue).append("\n");
		report.append("Best-selling Product: ").append(bestSellingProduct != null ? bestSellingProduct.getProduct().getName() : "N/A").append("\n");
		report.append("Highest Revenue Product: ").append(highestRevenueProduct != null ? highestRevenueProduct.getProduct().getName() : "N/A").append("\n");
		report.append("\n");
		return ResponseEntity.ok(report.toString());
	}
}

