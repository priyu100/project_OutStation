package com.datadriven.framework.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
//import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
//import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseUI {

	public WebDriver driver;
	public Properties prop;
	public static String nodeURL;

	public void invokeBrowser(String browserName) {

		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_setting_values.notifications", 2);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);
		try {
			if (browserName.equalsIgnoreCase("chrome")) {

				nodeURL = "http://192.168.43.84:5786/wd/hub";
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				driver = new RemoteWebDriver(new URL(nodeURL), capabilities);

			} else {
				nodeURL = "http://192.168.43.84:5786/wd/hub";
				DesiredCapabilities capabilities = DesiredCapabilities.firefox();
				driver = new RemoteWebDriver(new URL(nodeURL), capabilities);

			}

			driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			driver.manage().timeouts().pageLoadTimeout(180, TimeUnit.SECONDS);

			if (prop == null) {
				prop = new Properties();
				try {
					FileInputStream file = new FileInputStream(System.getProperty("user.dir")
							+ "//src//test//resources//ObjectRepository//projectConfig.properties");

					prop.load(file);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		} catch (Exception e) {

		}

	}

	public void openURL(String websiteURLKey) {
		driver.get(prop.getProperty(websiteURLKey));

	}

	public void quitBrowser() {
		driver.quit();
	}

	public void enterlocation(String xpathKey, String data) {

		driver.findElement(By.xpath(prop.getProperty(xpathKey))).clear();

		driver.findElement(By.xpath(prop.getProperty(xpathKey))).sendKeys(data);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Actions act = new Actions(driver);
		act.sendKeys(Keys.DOWN).perform();
		act.sendKeys(Keys.ENTER).perform();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void elementscrollclick(String xpathKey) throws InterruptedException {
		WebElement element = driver.findElement(By.xpath(prop.getProperty(xpathKey)));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(500);
		driver.findElement(By.xpath(prop.getProperty(xpathKey))).click();

	}

	public void elementClick(String xpathKey) {

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(prop.getProperty(xpathKey))));

		driver.findElement(By.xpath(prop.getProperty(xpathKey))).click();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void multiclick(String xpathKey) {
		List<WebElement> cl = driver.findElements(By.xpath(prop.getProperty(xpathKey)));
		for (int i = 0; i < cl.size(); i++) {
			cl.get(i).click();
		}

	}

	public void xlsx(String xpathKey1, String xpathKey2) throws IOException {
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		List<WebElement> NAME = driver.findElements(By.xpath(prop.getProperty(xpathKey1)));
		List<WebElement> PRICE = driver.findElements(By.xpath(prop.getProperty(xpathKey2)));

		// EXCEL SHEET FORMATION
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("CAR DETAILS");
		String Data[] = new String[6];

		// Entering the title for worksheet
		Row first = sheet.createRow(0);
		Cell Title = first.createCell(0);
		Title.setCellValue("CAR NAME");
		Cell dt = first.createCell(1);
		dt.setCellValue("CAR RENT");

		// INSERTING VALUE IN EXCEL SHEET

		for (int i = 0; i < 6; i++) {
			XSSFRow row = sheet.createRow(i + 1);

			Data[0] = NAME.get(i).getText();
			Data[1] = PRICE.get(i).getText();

			for (int j = 0; j < 6; j++) {
				XSSFCell cell = row.createCell(j);
				cell.setCellValue(Data[j]);
			}
		}

		FileOutputStream fileOutputStream = new FileOutputStream("OutstationCab DETAILS.xlsx");
		workbook.write(fileOutputStream);
		workbook.close();

	}

	public void navigateBack(String websiteURLKey) {
		driver.navigate().to(prop.getProperty(websiteURLKey));
	}

	public void switchTab() {
		ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs2.get(1));

	}

	public void enterEmail(String emailKey, String emailid) {

		driver.findElement(By.xpath(prop.getProperty(emailKey))).sendKeys(emailid);

	}

	public void alertMessage(String alertKey) throws IOException {
		String currentUsersHomeDir = System.getProperty("user.dir");
		String path = currentUsersHomeDir + "\\OutstationCab DETAILS.xlsx";
		FileInputStream fileinp = new FileInputStream(path);
		XSSFWorkbook workbook = new XSSFWorkbook(fileinp);
		XSSFSheet sheet = workbook.createSheet("Alert Details");

		WebElement message = driver.findElement(By.xpath(prop.getProperty(alertKey)));
		String text = message.getText();

		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue("Alert Message");
		Cell alert = row.createCell(1);
		alert.setCellValue(text);

		FileOutputStream fileOut = new FileOutputStream(path);
		workbook.write(fileOut);
		workbook.close();
	}

	public void noOfAdults(String xpathKey) throws IOException {
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(prop.getProperty(xpathKey))));

		String currentUsersHomeDir = System.getProperty("user.dir");
		String path = currentUsersHomeDir + "\\OutstationCab DETAILS.xlsx";
		FileInputStream fileinp = new FileInputStream(path);
		XSSFWorkbook workbook = new XSSFWorkbook(fileinp);
		XSSFSheet sheet = workbook.createSheet("Adults Details");

		WebElement message = driver.findElement(By.xpath(prop.getProperty(xpathKey)));
		String text = message.getText();

		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue("Adults");
		Cell al = row.createCell(1);
		al.setCellValue(text);

		FileOutputStream fileOut = new FileOutputStream(path);
		workbook.write(fileOut);
		workbook.close();
	}

}
