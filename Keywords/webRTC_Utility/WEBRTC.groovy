package webRTC_Utility
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.openqa.selenium.remote.DesiredCapabilities as DesiredCapabilities
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By

import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty

import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil

import com.kms.katalon.core.webui.exception.WebElementNotFoundException
import org.openqa.selenium.chrome.ChromeDriver as ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions as ChromeOptions
class WEBRTC {

	/*
	 * Here user can make call
	 * pass phone number as parameter 
	 */
	@Keyword
	def make_call(String number) {
		WebUI.waitForElementPresent(findTestObject('Apps/App_WebRTC/Webrtc_input_number'), 30)
		WebUI.waitForElementClickable(findTestObject('Apps/App_WebRTC/Webrtc_input_number'), 20, FailureHandling.STOP_ON_FAILURE)
		WebUI.setText(findTestObject('Apps/App_WebRTC/Webrtc_input_number'), number)

		WebUI.enhancedClick(findTestObject('Object Repository/Apps/App_WebRTC/button_call'))

		//WebUI.waitForElementVisible(findTestObject('Apps/App_WebRTC/button_connecting'), 30)
		WebUI.waitForElementPresent(findTestObject('Apps/App_WebRTC/button_connecting'), 20, FailureHandling.STOP_ON_FAILURE)
	}

	/*
	 * Reject and cancel the call
	 */

	@Keyword
	def Reject_and_cancel_call() {
		WebUI.delay(5)

		WebUI.waitForElementClickable(findTestObject('Apps/App_WebRTC/button_reject'), 10)

		WebUI.enhancedClick(findTestObject('Apps/App_WebRTC/button_reject'))

		WebUI.waitForElementNotVisible(findTestObject('Apps/App_WebRTC/button_reject'), 20)
	}

	/*
	 * Receive the incoming call
	 */

	@Keyword
	def receive_call() {

		WebUI.waitForElementVisible(findTestObject('Apps/App_WebRTC/Button_receive'), 10, FailureHandling.STOP_ON_FAILURE)
		WebUI.enhancedClick(findTestObject('Apps/App_WebRTC/Button_receive'))
		WebUI.waitForElementVisible(findTestObject('Apps/App_WebRTC/Call_time'), 20)
	}
	/*
	 * caller/callee terminates the call
	 */

	@Keyword
	def hang_up_the_call() {

		WebUI.waitForElementVisible(findTestObject('Apps/App_WebRTC/Button_Hangup'),20, FailureHandling.STOP_ON_FAILURE)

		WebUI.enhancedClick(findTestObject('Apps/App_WebRTC/Button_Hangup'),FailureHandling.STOP_ON_FAILURE)

		WebUI.waitForElementVisible(findTestObject('Apps/App_WebRTC/button_call'), 10, FailureHandling.STOP_ON_FAILURE)
	}


	/*
	 * Keyword to transfer to another user
	 */

	@Keyword
	def transfer_the_call(String number) {

		WebUI.delay(5)
		WebUI.waitForElementVisible(findTestObject('Apps/App_WebRTC/Button_transfer_call'),10, FailureHandling.STOP_ON_FAILURE)
		WebUI.enhancedClick(findTestObject('Apps/App_WebRTC/Button_transfer_call'), FailureHandling.STOP_ON_FAILURE)
		WebUI.verifyTextPresent("Transfer", false, FailureHandling.STOP_ON_FAILURE)
		WebUI.waitForElementPresent(findTestObject('Apps/App_WebRTC/input_transfer_number'), 20, FailureHandling.STOP_ON_FAILURE)
		WebUI.setText(findTestObject('Apps/App_WebRTC/input_transfer_number'), number)
		WebUI.enhancedClick(findTestObject('Apps/App_WebRTC/button_transfercall'))
	}
}
