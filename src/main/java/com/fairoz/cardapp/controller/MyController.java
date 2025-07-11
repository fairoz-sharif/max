
package com.fairoz.cardapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.RedirectView;

import com.fairoz.cardapp.service.CardService;
import com.fairoz.cardapp.vo.CardInfoVO;


import java.io.*;
import java.util.*;

@Controller
public class MyController {

	@Autowired
    private final CardService cardService = null;

    @GetMapping("/")
    public String home(Model model) {
    	System.out.println(" Rendering Index.html ");
        model.addAttribute("cards", cardService.getSortedCards());
        return "index";
    }

    @GetMapping("/addnew")
    public String addCardNew() {
    	System.out.println(" Add Card NEW function invoked ");

        return "redirect:/";
    }

    
    @PostMapping("/add")
    public ModelAndView addCard( @RequestParam String bank , @RequestParam String number , @RequestParam String expiry ) {
    		System.out.println(" Add Card function invoked ");
            System.out.println("Bank: " + bank);
            System.out.println("Number: " + number);
            System.out.println("Expiry: " + expiry);

            ModelAndView mav = new ModelAndView();
            try {
				
            	cardService.addCard( bank, number, expiry );
            	mav.addObject("message",  "Card Added Successfully ");
			} catch (Exception e) {
				mav.addObject("message", e.getMessage());
				e.printStackTrace();
			}            // redirect to home page
            
            mav.setViewName( "index" );
            mav.addObject("cards", cardService.getSortedCards() );
            return mav;
    }

    @PostMapping("/upload")
    public ModelAndView upload(@RequestParam("file") MultipartFile file) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String line;
        List<CardInfoVO> cards = new ArrayList<>();
        int count = 0;
        int totalCount = 0;
        ModelAndView mav = new ModelAndView();
        
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");

            try {
            	totalCount++;
            	cardService.addCard( parts[ 0 ], parts[ 1 ] , parts[ 2 ] ) ;
            	count++;
            	
			} catch (Exception e) {
				// TODO: handle exception
			}
        }
        mav.addObject("message", "Added Total Cards "+count +"/ "+totalCount +" from Csv " );
        mav.addObject("cards", cardService.getSortedCards() );
       
        mav.setViewName( "index" );
        return mav;
    }
}
