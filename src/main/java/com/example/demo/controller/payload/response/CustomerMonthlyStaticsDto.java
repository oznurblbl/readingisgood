package com.example.demo.controller.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerMonthlyStaticsDto {

	private String month;
	private Integer totalOrderCount;
	private Integer totalBookCount;
	private Double totalPurchasedAmount;
}
