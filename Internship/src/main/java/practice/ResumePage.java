package practice;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ResumePage {
	private final Page page;
	
	
	private final Locator myStuff;
	private final Locator myAccount;
	private final Locator mainMenu;
	private final Locator myResume;
	private final Locator addEmployment;
	private final Locator radioButton;
	private final Locator fullTimeTab;
	private final Locator dropDrop;
	private final Locator companyType;
	
	
	public ResumePage(Page page) {
	this.page=page;
	this.myStuff=page.locator("//span[text()='My Stuff']");
    this.myAccount=page.locator("//button[text()='My Account ']");
    this.mainMenu=page.locator("//button[text()='Main Menu']");
	this.myResume=page.locator("//a[text()='My Resume']");
	this.addEmployment=page.locator("//span[text()='ADD EMPLOYMENT']");
	this.radioButton=page.locator("#No");
	this.fullTimeTab=page.locator("#FullTime");
	this.dropDrop=page.locator("//div[text()='Search or select previous company name']");
	this.companyType=page.locator("//div[text()='    PRO TECH 12345']");
	}
	
	public void clickMyStuff(){
		myStuff.click();
	}
	
	public void clickMyAccount() {
		myAccount.click();
	}
	
	public void clickmainMenu() {
		mainMenu.click();
	}
		public void clickOnResume() {
			myResume.click();
		
	}
		public void clickEmployment() {
			addEmployment.click();
		}
	   public void clickRadioButton() {
		   radioButton.click();
	   }
	  public void clickOnTab() {
		  fullTimeTab.click();
	  }
	  
	  public void clickDropDown() {
		  dropDrop.click();
	  }
	  
	  public void selectCompanyType() {
		  companyType.click();
	  }
}


