package com.promilo.automation.course.advertiser;

import java.io.IOException;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.advertiser.jobcampaign.BudgetAndCost;
import com.promilo.automation.advertiser.jobcampaign.CreateJobPosting;
import com.promilo.automation.advertiser.jobcampaign.Feedback;
import com.promilo.automation.advertiser.jobcampaign.Myaudience;
import com.promilo.automation.advertiser.jobcampaign.ScreeningQuestions;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class CreateCampaign extends Baseclass{
	
	
	 private ExtentReports extent;
	    private ExtentTest test;
	    private Page page;
	@Test
    public void performLoginTest() throws IOException, InterruptedException {
       
            extent = ExtentManager.getInstance();
            test = extent.createTest("🚀 Campaign Creation via Excel Data");

            String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
            ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

            String email = excel.getCellData(1, 2);
            String InputValue = excel.getCellData(1, 3);
            String campaignName = excel.getCellData(1, 4);
            String jobRole = excel.getCellData(1, 5);
            String brandName = excel.getCellData(1, 6);
            String description = excel.getCellData(1, 7);
            String minExp = excel.getCellData(1, 8);
            String maxExp = excel.getCellData(1, 9);
            String location = excel.getCellData(1, 10);
            String startDate = excel.getCellData(1, 11);
            String endDate = excel.getCellData(1, 12);
            String salaryMin = excel.getCellData(1, 13);
            String salaryMax = excel.getCellData(1, 14);
            String assignmentTitle = excel.getCellData(1, 15);
            String assignmentTopic = excel.getCellData(1, 16);
            String assignmentDesc = excel.getCellData(1, 17);

            page = initializePlaywright();
            page.navigate(prop.getProperty("stageUrl"));
            page.setViewportSize(1000, 668);
            Thread.sleep(5000);

            AdvertiserLoginPage login = new AdvertiserLoginPage(page);
            Assert.assertTrue(login.signInContent().isVisible(), "❌ Sign-in content is not visible.");
            Assert.assertTrue(login.talkToAnExpert().isVisible(), "Talk To expert content is visible");

            login.loginMailField().fill("TestAutomation1756802090533@ofuk8kzb.mailosaur.net");
            login.loginPasswordField().fill("John@12345");
            login.signInButton().click();

            page.locator("//a[@class='nav-menu-main menu-toggle hidden-xs is-active nav-link']").click();
            AdvertiserHomepage campaign = new AdvertiserHomepage(page);
            campaign.campaign().click();
            
            page.locator("//span[text()='Add New Campaign']").click();
            Thread.sleep(2000);
            page.locator("//div[@class='pointer card-body']").first().click();
            page.locator("//input[@placeholder='Enter Campaign Name']").fill("Bangalore college of institue");
            page.locator("//input[@placeholder='Enter Product Title']").fill("Bsangalore college technolgy");
            page.locator("//input[@placeholder='Enter your Display Brand Name content']").fill("Bangalore university college");
            page.locator("#dropdown-basic").click();
            page.locator("//button[text()='Engineering']").click();
            page.locator("//a[text()='B.E. / B.Tech']").click();
            page.locator("//textarea[@name='description']").fill("something");
            
            page.locator("//div[text()='Select']").click();
            page.locator("//div[text()='Year']").click();
            page.locator("//input[@placeholder='Enter Numbers']").fill("3");
            page.locator("#ChooseAppointmentPage svg").nth(1).click();
            page.locator("//div[text()='Ahmedabad']").first().click();
            page.locator("//input[@name='startDate']").fill("2025-09-10");
            page.locator("//input[@name='endDate']").fill("2026-09-10");
            page.locator("//div[text()='Select Delivery Mode']").click();
            
            page.locator("//div[text()='Regular / Offline']").click();
            
          Locator upload= page.locator("//span[text()='Upload Image']");
          upload.click();
          upload.setInputFiles(Paths.get("C:/Users/Admin/Downloads/pexels-moh-adbelghaffar-771742.jpg"));
          page.locator("//button[@class='crop-next-buttonN btn btn-secondary']").click();
          page.locator("//button[text()='Save & Next']").click();
          
          
          page.locator("//a[text()='+ Add Title']").click();
          
          Thread.sleep(5000);

          Locator checkboxes = page.locator("//input[@type='checkbox']");

          int count = checkboxes.count();

          for (int i = 0; i < Math.min(3, count); i++) {
              checkboxes.nth(i).click();

              if (!checkboxes.nth(i).isChecked()) {
                  checkboxes.nth(i).click();
              }
          }
          
          
          Thread.sleep(5000);

          // Now click the Add button only after selections
          page.locator("//button[text()='Add']").click();
          
          Thread.sleep(2000);

page.locator("//div[@role='textbox']").first().fill("A vibrant and welcoming space that blends modern design with comfort. The atmosphere is lively yet relaxed, making it perfect for collaboration, creativity, or just unwinding. Every detail is thoughtfully crafted to create a balance between functionality and aesthetics.\"");
Thread.sleep(2000);

page.locator("//a[text()='Courses & Fees']").click();
page.locator("//div[@role='textbox']").nth(1).fill("A vibrant and welcoming space that blends modern design with comfort. The atmosphere is lively yet relaxed, making it perfect for collaboration, creativity, or just unwinding. Every detail is thoughtfully crafted to create a balance between functionality and aesthetics.\"");
Thread.sleep(2000);

page.locator("//a[text()='Syllabus']").click();

page.locator("//div[@role='textbox']").nth(2).fill("A vibrant and welcoming space that blends modern design with comfort. The atmosphere is lively yet relaxed, making it perfect for collaboration, creativity, or just unwinding. Every detail is thoughtfully crafted to create a balance between functionality and aesthetics.\"");

Thread.sleep(2000);

page.locator("//span[text()='Save & Next']").click();

page.locator("//div[text()='Upload Link']").click();
          
          page.locator("//input[@placeholder='E.g. https://drive.google.com/mydocument']").fill("https://www.youtube.com/watch?v=a0cJ7gZQQEs");
          
          page.locator("//input[@type='submit']").click();
          
        Locator uploadVideo= page.locator("//div[text()='Add Photos or Videos']");
        uploadVideo.click();
        uploadVideo.setInputFiles(Paths.get("C:/Users/Admin/Downloads/pexels-moh-adbelghaffar-771742.jpg"));
          
          
            
            
            
            page.locator("//button[text()='Crop']").click();
            page.locator("//p[text()='Save & Next']").click();
            
            
            CreateJobPosting choseDateandTime = new CreateJobPosting(page);
            choseDateandTime.ChoseTimeSlot().click();
            page.keyboard().type("30");
            page.keyboard().press("Enter");

          
            choseDateandTime.Monday().click();

                // Create 10 slots for the day
                for (int i = 0; i < 10; i++) {
                    page.locator("//button[@class='plus-icon btn btn-secondary']").click();
                    Thread.sleep(1000);

                    // Select nth slot
                    page.locator("//div[@class='time-slot-select__input-container css-19bb58m']")
                        .nth(i + 1).click();
                    Thread.sleep(1000);

                    page.keyboard().type(String.format("%02d", (i % 24)));
                    Thread.sleep(500);

                    page.keyboard().press("Enter");
                    Thread.sleep(500);
                }

                choseDateandTime.English().click();
                    choseDateandTime.Hindi().click();
                    choseDateandTime.Kannada().click();
                    choseDateandTime.SaveButton().click();

                    ScreeningQuestions screening = new ScreeningQuestions(page);
                    Feedback feedback = new Feedback(page);
                    screening.AddButton().click();
                    page.locator("input[placeholder='Type Question Here']").fill("are you an immediate joiner");
                    feedback.EnteroptionField().fill("yes");
                    
                    page.locator("//button[@class='py-0 px-1 ms-0 btn btn-primary']").click();
                    
                    page.locator("//input[@class='border-0 outline-0 ms-1 form-control']").nth(1).fill("no0");
                    screening.SaveButton().click();

                    // Feedback again
                    feedback.AddButton().click();
                    page.locator("input[placeholder='Type Question Here']").fill("are you an immediate joiner");
                    feedback.EnteroptionField().fill("yes");
                    page.locator("//button[@class='py-0 px-1 ms-0 btn btn-primary']").click();
                    
                    page.locator("//input[@class='border-0 outline-0 ms-1 form-control']").nth(1).fill("no0");
                    feedback.FeedbackSavebutton().click();

              page.locator("//input[@placeholder='URL Title']").fill("Bangalore university College");
              page.locator("//input[@name='campaignUrl']").fill("https://stagebusiness.promilo.com/");
              page.locator("//input[@placeholder='Document Title']").fill("Bnaglore colleg PDF");
              
           Locator uploadDocument=  page.locator("//span[text()='Upload Document']");
           uploadDocument.click();
           uploadDocument.setInputFiles(Paths.get("C:\\Users\\Admin\\Downloads\\large_sample_test_file.pdf"));
           
           
           page.locator("//button[text()='Save & Next']").click();
           
        Locator
        dropdown=  page.locator("//input[@placeholder='Select Audience Industry']");
        dropdown.click();
        
       Locator option1= page.locator("//li[text()='Animation & VFX']");
       option1.click();
        
        
       Locator dropdown2= page.locator("//span[text()='Select Functional Area']");
        dropdown2.click();
        
       Locator option2 =   page.locator("//span[text()='Engineering - Electrical']");
       option2.click();
       

       
      Locator dropdown3= page.locator("//input[@placeholder='Select Relevant Title']");
      dropdown3.click();
      
      Thread.sleep(4000);
       
      Locator option3 = page.locator("//li[text()='Electrical Engineer']");
      option3.click();
      
      
      page.keyboard().type("accounts");
      page.keyboard().press("Enter");

      
    Locator keywords= page.locator("//button[text()='Electrical Engineer']");
    keywords.click();
    
    
    
    
    Myaudience audience = new Myaudience(page);
    audience.MinAge().first().click();
    page.keyboard().type("20");
    page.keyboard().press("Enter");
    audience.Maxage().nth(1).click();
    page.keyboard().type("29");
    page.keyboard().press("Enter");

 
 
 
 page.locator("[name='search_name_input']").nth(1).click();
 page.keyboard().type("Bangalore");
 page.keyboard().press("Enter");
 
 page.locator("//button[text()='Save & Next']").click();
 
 page.locator("//input[@id='Send Email']").click();
 page.locator("//input[@id='Send SMS']").click();
 
 page.locator("//input[@id='Notification']").click();
 
 page.locator("//input[@placeholder='Enter the total cost of solution']").fill("450000");
 
 page.locator("//input[@placeholder='Enter the number of prospect you want to connect']").fill("6500008");
 
 BudgetAndCost budget = new BudgetAndCost(page);

  
 
 budget.evaluate().click();

 
 page.locator("//button[@type='submit']").first().click();
 
 page.locator("//button[@class='btn-close']").click();
 
 
 page.locator("//span[text()='Publish']").click();
 
 
 
        
        
           
              
              
              
              
              
              
              
                 
                 
                 
            

	
	
	
	
	
	
	}
	
	
	
}