package com.example.BankStatement.dto;

public class BankRequestDTO {

	
	public boolean isByAmount;
	
	public boolean isByDate;
	
	public String dateFrom;

	public String dateTo;

	public String amountFrom;

	public String amountTo;

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getAmountFrom() {
		return amountFrom;
	}

	public void setAmountFrom(String amountFrom) {
		this.amountFrom = amountFrom;
	}

	public String getAmountTo() {
		return amountTo;
	}

	public void setAmountTo(String amountTo) {
		this.amountTo = amountTo;
	}

//	public boolean isByAmount() {
//		return byAmount;
//	}
//
//	public void setByAmount(boolean byAmount) {
//		this.byAmount = byAmount;
//	}
//
//	public boolean isByDate() {
//		return byDate;
//	}
//
//	public void setByDate(boolean byDate) {
//		this.byDate = byDate;
//	}
	
	

	public String getDateTo() {
		return dateTo;
	}

	public boolean isByAmount() {
		return isByAmount;
	}

	public void setByAmount(boolean isByAmount) {
		this.isByAmount = isByAmount;
	}

	public boolean isByDate() {
		return isByDate;
	}

	public void setByDate(boolean isByDate) {
		this.isByDate = isByDate;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	
}
