package com.promilo.automation.mentorship.mentor;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MentorIntrestPage {

	
	private final Page page;
	private final Locator preferenceHeaderText;
	private final Locator categoryTextInterestCard;
	private final Locator cardDetailValue;
	
	public MentorIntrestPage(Page page) {
		
		this.page= page;
		
		this.preferenceHeaderText = page.locator("//div[@class='preferance-header-text']");
	      this.categoryTextInterestCard = page.locator("//div[@class='category-text-interest-card text-truncate text-wrap']");
	     this.cardDetailValue = page.locator("//span[@class='card_detail-value']");
		
		
		
		
	}	
		
	
	
    public Locator getPreferenceHeaderText() {
        return preferenceHeaderText;
    }

    public Locator getCategoryTextInterestCard() {
        return categoryTextInterestCard;
    }

    public Locator getCardDetailValue() {
        return cardDetailValue;
    }


}
