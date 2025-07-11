package com.fairoz.cardapp.service;

import java.util.List;

import com.fairoz.cardapp.vo.CardInfoVO;

public interface CardService {

	public void addCard( String bank , String number , String expiry );

    public String maskCardNumber(String number);

    public List<CardInfoVO> getSortedCards() ;

    
}
