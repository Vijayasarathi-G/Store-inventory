package com.stock.inventory.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "stocks")
public class Stock {
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "stock_name", nullable = false)
	private String stockName;

	@Column(name = "price", nullable = false)
	private float price;

	@CreationTimestamp
	@Temporal(TemporalType.DATE)
	@Column(name = "purchase_date", nullable = false)
	private Date purchaseDate;

	@Column(name = "quantity", nullable = false)
	private int quantity;

	public Stock(long id, String stockName, float price, Date purchaseDate, int quantity) {
		this.id = id;
		this.stockName = stockName;
		this.price = price;
		this.purchaseDate = purchaseDate;
		this.quantity = quantity;
	}
	
	

	public Stock() {
		
	}

	@Override
	public String toString() {
		return "Stock{" +
				"id=" + id +
				", stockName='" + stockName + '\'' +
				", price=" + price +
				", purchaseDate=" + purchaseDate +
				", quantity=" + quantity +
				'}';
	}
}
