package com.stock.inventory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.stock.inventory.model.Stock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StockControllerIntegrationTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {

	}

	@Test
	public void testGetAllStocks() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/stocks",
				HttpMethod.GET, entity, String.class);
		
		assertNotNull(response.getBody());
	}

	@Test
	public void testGetStockById() {
		Stock stock = restTemplate.getForObject(getRootUrl() + "/stocks/1", Stock.class);
		System.out.println(stock.getStockName());
		assertNotNull(stock);
	}

	@Test
	public void testCreateStock() {
		Stock stock = new Stock();
		stock.setStockName("Pen");
		stock.setPrice(12.50f);
		stock.setQuantity(25);

		ResponseEntity<Stock> postResponse = restTemplate.postForEntity(getRootUrl() + "/stocks", stock, Stock.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdateStock() {
		int id = 1;
		Stock stock = restTemplate.getForObject(getRootUrl() + "/stocks/" + id, Stock.class);
		stock.setStockName("Pen");
		stock.setPrice(12.50f);

		restTemplate.put(getRootUrl() + "/stocks/" + id, stock);

		Stock updatedStock = restTemplate.getForObject(getRootUrl() + "/Stocks/" + id, Stock.class);
		assertNotNull(updatedStock);
	}

	@Test
	public void testDeleteStock() {
		int id = 2;
		Stock stock = restTemplate.getForObject(getRootUrl() + "/stocks/" + id, Stock.class);
		assertNotNull(stock);

		restTemplate.delete(getRootUrl() + "/stocks/" + id);

		try {
			stock = restTemplate.getForObject(getRootUrl() + "/stocks/" + id, Stock.class);
		} catch (final HttpClientErrorException e) {
			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}
}
