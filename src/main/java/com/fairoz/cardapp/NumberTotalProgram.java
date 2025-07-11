package com.fairoz.cardapp;

public class NumberTotalProgram {
    public static void main(String[] args) {
        String numberStr = "23456";

        try {
            int input = Integer.parseInt( numberStr );
            int sum = total(Math.abs(input));
            System.out.println("Sum of digits: " + sum);
        } catch ( Exception ex ) {
            System.out.println("Invalid input. Make sure you enter a valid integer.");
        }
    }
    
    public static int total(int number) {
        if (number > 0) {
            int lastNum = number % 10;
            int remaining = number / 10;
            
            int retVal = lastNum + total(remaining);
            System.out.println(remaining+"\t"+ lastNum+"\t "+ retVal );
            return retVal;
        }
        return 0;
    }

}
