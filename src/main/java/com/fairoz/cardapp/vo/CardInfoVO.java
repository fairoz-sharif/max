package com.fairoz.cardapp.vo;

public class CardInfoVO {

	private String bankName;
    private String cardNumber;
    private String expiryDate;

    public CardInfoVO() { }

    public CardInfoVO(String bankName, String cardNumber, String expiryDate) {
        this.bankName = bankName;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
    }

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
}
