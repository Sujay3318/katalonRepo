import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.chrome.ChromeDriver as ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions as ChromeOptions
import org.openqa.selenium.remote.DesiredCapabilities as DesiredCapabilities
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW

WebUI.callTestCase(findTestCase('CDR/Setup/OnnetAccountUserDeviceWebrtcAppSetupByAPI'), [('numberOfUser') : 2], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('Webrtc/OnnetLoginToWebrtcAppWithUser'), [:], FailureHandling.STOP_ON_FAILURE)

/*
 *call_1: user Agent2 is calling to userAgent1
 *caller = user2
 *callee = user1
 */
//
CustomKeywords.'webRTC_Utility.WEBRTC.make_call'(GlobalVariable.user1_creation_details.get('phone_number'))

WebUI.delay(10)

def window1 = GlobalVariable.user1_creation_details.get('window')

DriverFactory.changeWebDriver(window1)

CustomKeywords.'webRTC_Utility.WEBRTC.receive_call'()

CustomKeywords.'webRTC_Utility.WEBRTC.hang_up_the_call'()

WebUI.delay(5)

/*
 *call_2:user Agent1 is calling to userAgent2
 *caller = user1
 *callee = user2
 */
CustomKeywords.'webRTC_Utility.WEBRTC.make_call'(GlobalVariable.user2_creation_details.get('phone_number'))

WebUI.delay(10)

def window2 = GlobalVariable.user2_creation_details.get('window')

DriverFactory.changeWebDriver(GlobalVariable.user2_creation_details.get('window'))

CustomKeywords.'webRTC_Utility.WEBRTC.receive_call'()

CustomKeywords.'webRTC_Utility.WEBRTC.hang_up_the_call'()

WebUI.delay(5)

GlobalVariable.user1_creation_details.get('window').close()

GlobalVariable.user2_creation_details.get('window').close()

