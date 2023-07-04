package loginOperation

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.chrome.ChromeDriver as ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions as ChromeOptions
import org.openqa.selenium.remote.DesiredCapabilities as DesiredCapabilities
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import org.openqa.selenium.Keys as Keys

public class LoginToMonsterUI {

	@Keyword
	def loginUsingChromeDriver(String windowName){
		System.setProperty('webdriver.chrome.driver', DriverFactory.getChromeDriverPath())

		ChromeOptions options = new ChromeOptions()

		Map<String, Object> chromePrefs = new HashMap<Integer, String>()

		chromePrefs.put('profile.default_content_setting_values.notifications', 2)

		chromePrefs.put('profile.default_content_setting_values.media_stream_mic', 1)

		chromePrefs.put("profile.default_content_setting_values.media_stream_camera", 1)

		options.setExperimentalOption('prefs', chromePrefs)

		DesiredCapabilities cap = DesiredCapabilities.chrome()

		cap.setCapability(ChromeOptions.CAPABILITY, options)

		WebDriver chromedriver = new ChromeDriver(cap)

		DriverFactory.changeWebDriver(chromedriver)

		GlobalVariable.chromeDriverObjectMap.put(windowName, chromedriver)
	}
}
