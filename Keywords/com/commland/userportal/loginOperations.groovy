package com.commland.userportal
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import com.kms.katalon.core.util.KeywordUtil
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.chrome.ChromeDriver as ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions as ChromeOptions
import org.openqa.selenium.remote.DesiredCapabilities as DesiredCapabilities
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import org.openqa.selenium.Keys as Keys

public class loginOperations {

	/*
	 * @param {windowName} window name of driver
	 * @param {userName} user name of the user 
	 * @param {password} password of the user
	 * @param {accountName} account name of the user present in.
	 */
	@Keyword
	def signIn(String windowName,String userName,String password,String accountName){

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

		GlobalVariable.chromeDriverObjectMap.put(windowName, chromedriver)

		DriverFactory.changeWebDriver(GlobalVariable.chromeDriverObjectMap.get(windowName))

		WebUI.maximizeWindow()

		WebUI.navigateToUrl(GlobalVariable.COMMLAND_URL)

		WebUI.callTestCase(findTestCase('Comm.land/Sign_In_And_Sign_Out/SC_Verify_Login_Page'), [:], FailureHandling.STOP_ON_FAILURE)

		WebUI.verifyElementVisible(findTestObject('Comm.land/Sign_In/input_Username_username'), FailureHandling.STOP_ON_FAILURE)

		WebUI.setText(findTestObject('Comm.land/Sign_In/input_Username_username'), userName, FailureHandling.STOP_ON_FAILURE)

		WebUI.verifyElementVisible(findTestObject('Comm.land/Sign_In/input_Password_password'), FailureHandling.STOP_ON_FAILURE)

		WebUI.setText(findTestObject('Comm.land/Sign_In/input_Password_password'), password, FailureHandling.STOP_ON_FAILURE)

		WebUI.verifyElementVisible(findTestObject('Comm.land/Sign_In/input_AccountName_accountName'), FailureHandling.STOP_ON_FAILURE)

		WebUI.setText(findTestObject('Comm.land/Sign_In/input_AccountName_accountName'), accountName, FailureHandling.STOP_ON_FAILURE)

		WebUI.enhancedClick(findTestObject('Comm.land/Sign_In/button_SignIn'), FailureHandling.STOP_ON_FAILURE)

		WebUI.delay(5)
	}
}