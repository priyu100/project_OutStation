package com.datadriven.framework.test.LoginTest;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.datadriven.framework.base.BaseUI;

public class loginTest extends BaseUI {
	
	@BeforeClass
	public void browser() {
		invokeBrowser("chrome");
	}

	@Test
	public void testOne() throws InterruptedException, IOException {
		openURL("websiteURL");
		elementClick("cab");
		elementClick("oneway");
		enterlocation("from","New Delhi");
		enterlocation("to", "Manali");
		elementClick("select_date");
		elementscrollclick("date");
		elementClick("time");
		elementClick("search");
		multiclick("more-click");
		xlsx("cartype","carrent");
	}
		
	@Test
	public void testTwo() throws IOException {
		
	
		navigateBack("websiteURL");
		elementClick("offer");
		elementClick("viewOffer");
		switchTab();
		enterEmail("email", "abcdef");
		elementClick("signup");
		alertMessage("message");

	}
	
	@Test 
	public void testThree() throws IOException {
		
		navigateBack("websiteURL");
		elementClick("hotel");
		elementClick("room_click");
		elementClick("add_room");
		elementClick("add_child");
		elementClick("search_hotel");
		noOfAdults("adults");
		
		
	}
	
	
	@AfterClass
	public void close() {
		quitBrowser();
		
	}
	
	
}
