package com.promilo.automation.resources;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MailsaurCredentials {
	
	
	private Page page;
	
	private Locator MialsaurMail;
	private Locator MailsaurPassword;
	private Locator MailsaurContinue;
	private Locator MailsaurLogin;
	
	public MailsaurCredentials(Page page) {
		
		
		
		
		this.MialsaurMail=	 page.locator("//input[@placeholder='Enter your email address']");
		MialsaurMail.fill("karthiktestuser42@gmail.com");
		
		
		
	    this.MailsaurContinue= page.locator("//button[text()='Continue']");
	    		MailsaurContinue.click();
	   this.MailsaurPassword=  page.locator("//input[@placeholder='Enter your password']");
	   MailsaurPassword.fill("Karthik@88");
	    this.MailsaurLogin= page.locator("//button[text()='Log in']");
	    		MailsaurLogin.click();
	}
	
	public Locator MialsaurMail() {return MialsaurMail;}
	public Locator MailsaurPassword() {return MailsaurPassword;}
	public Locator MailsaurContinue() {return MailsaurContinue;}
	public Locator MailsaurLogin() {return MailsaurLogin;}
	
	
	


}
