package com.fairoz.cardapp.service;


import org.springframework.stereotype.Service;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import com.fairoz.cardapp.vo.CardInfoVO;

import java.util.*;

@Service
public class CardServiceImpl implements CardService {

	  	private List<CardInfoVO> cardList = new ArrayList<>();

	  	public CardServiceImpl() {
			// TODO Auto-generated constructor stub
	  		
	  		cardList.add(new CardInfoVO("HSBC Canada", maskCardNumber("5601234534465678"), "11/17"));
	  		cardList.add(new CardInfoVO("Royal Bank of Canada", maskCardNumber("4519453245242456"), "10/17"));
	  		cardList.add(new CardInfoVO("American Express", maskCardNumber("378673348965345"), "12/18"));
		}
	    // Add card to the list
	    public void addCard( String bank , String number , String expiry ) {
	    	if( isValidCard ( bank ,number , expiry )) {
	    		CardInfoVO card = new CardInfoVO();
	    		card.setBankName(bank);
	    		card.setCardNumber( maskCardNumber(number) );
	    		card.setExpiryDate(expiry);
	    		cardList.add(card);
	    	}
	    		
	    	else
	    		throw new RuntimeException( "Invalid Card info provided" );
	    }

	    
	    // Mask except last 4
	    public String maskCardNumber(String number) {
	        if (number == null || number.length() <= 4) {
	            return number; // Nothing to mask
	        }

	        int length = number.length();
	        StringBuilder masked = new StringBuilder();

	        // Append 'x' for all characters except the last 4
	        for (int i = 0; i < length - 4; i++) {
	            masked.append('x');
	        }

	        // Append the last 4 digits as-is
	        masked.append(number.substring(length - 4));

	        return masked.toString();
	    }

	    // Sort cards by expiry
	    public List<CardInfoVO> getSortedCards() {
	        cardList.sort((a, b) -> b.getExpiryDate().compareTo(a.getExpiryDate()));
	        return cardList;
	    }

	    public boolean isValidCard( String bank , String number , String expiry ) {
	    	if( bank == null || bank.trim().length() == 0 )
	    		return false;

	    	if( isValidCardNumber( number ) == false )
	    		return false;
	    	
	    	if( isExpired( expiry ) == false )
	    		return true;
	    		
	    	return true;
	    }

		 // Luhn algorithm:
		 // 1. Starting from the right, double every second digit.
		 // 2. If result > 9, subtract 9.
		 // 3. Sum all digits.
		 // 4. If sum % 10 == 0, number is valid.
	    private static boolean isValidCardNumber( String cardNumber  ) {
	        cardNumber = cardNumber.replaceAll("\\s", "").replaceAll("-", "");

	        if (!cardNumber.matches("\\d{13,19}")) {
	            return false;
	        }

	        int sum = 0;
	        boolean alternate = false;
	        for (int i = cardNumber.length() - 1; i >= 0; i--) {
	            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
	            if (alternate) {
	                n *= 2;
	                if (n > 9) {
	                    n -= 9;
	                }
	            }
	            sum += n;
	            alternate = !alternate;
	        }
	        return (sum % 10 == 0);
	    }
	    
	    private static boolean isExpired(String expiry) {
	        try {
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
	            YearMonth yearMonth = YearMonth.parse(expiry, formatter);
	            YearMonth now = YearMonth.now();

	            // Must be current month or future
	            return yearMonth.isAfter(now) || yearMonth.equals(now);
	        } catch (DateTimeParseException e) {
	            return false;
	        }
	    }
}
