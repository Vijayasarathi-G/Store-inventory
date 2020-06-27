package com.stock.inventory.controller;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.stock.inventory.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stock.inventory.exception.ResourceNotFoundException;
import com.stock.inventory.model.Stock;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class StockController {
	@Autowired
	private StockRepository stockRepository;

	@GetMapping("/stocks")
	public List<Stock> getAllStocks() {
		return stockRepository.findAll();
	}

	@GetMapping("/stocks/{id}")
	public ResponseEntity<Stock> getStockById(@PathVariable(value = "id") Long stockId)
			throws ResourceNotFoundException {
		Stock stock = stockRepository.findById(stockId)
				.orElseThrow(() -> new ResourceNotFoundException("Stock not found for this id :: " + stockId));
		return ResponseEntity.ok().body(stock);
	}

	@PostMapping("/stocks")
	public Stock createStock(@Valid @RequestBody Stock stock) {
		return stockRepository.save(stock);
	}

	@PutMapping("/stocks/{id}")
	public ResponseEntity<Stock> updateStock(@PathVariable(value = "id") Long stockId,
			@Valid @RequestBody Stock stockDetails) throws ResourceNotFoundException {
		Stock stock = stockRepository.findById(stockId)
				.orElseThrow(() -> new ResourceNotFoundException("Stock not found for this id :: " + stockId));
		stock.setPrice(stockDetails.getPrice());
		stock.setPurchaseDate(stockDetails.getPurchaseDate());
		stock.setPrice(stockDetails.getPrice());
		stock.setQuantity(stockDetails.getQuantity());
		final Stock updatedStock = stockRepository.save(stock);
		return ResponseEntity.ok(updatedStock);
	}

	@DeleteMapping("/stocks/{id}")
	public Map<String, Boolean> deleteStock(@PathVariable(value = "id") Long stockId)
			throws ResourceNotFoundException {
		Stock stock = stockRepository.findById(stockId)
				.orElseThrow(() -> new ResourceNotFoundException("Stock not found for this id :: " + stockId));

		stockRepository.delete(stock);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
