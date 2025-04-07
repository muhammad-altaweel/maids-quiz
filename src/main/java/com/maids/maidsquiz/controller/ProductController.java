package com.maids.maidsquiz.controller;

import com.maids.maidsquiz.data.Product;
import com.maids.maidsquiz.dto.ProductDto;
import com.maids.maidsquiz.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping (value = "/products")
public class ProductController {
	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}

	@GetMapping("/{id}")
	public Product getProductById(@PathVariable Long id) throws Exception {
		return productService.getProductById(id);
	}

	@PostMapping
	public Product createProduct(@RequestBody ProductDto product) {
		return productService.createProduct(product);
	}

	@PutMapping("/{id}")
	public Product updateProduct(@PathVariable Long id, @RequestBody ProductDto updatedProduct) throws Exception {
		return productService.updateProduct(id, updatedProduct);
	}

	@DeleteMapping ("/{id}")
	public void deleteProduct(@PathVariable Long id) throws Exception {
		productService.deleteProduct(id);
	}

	@GetMapping("/report")
	public ResponseEntity<String> generateReport(){
		return productService.generateReport();
	}
}

