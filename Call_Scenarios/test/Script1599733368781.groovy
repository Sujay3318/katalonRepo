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
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
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
//caller terminates
WebUI.callTestCase(findTestCase('Base/Basic_TestCases/FetchAuthToken'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('CDR/Setup/Gateway_Call_Setup/GenericAccountUserDeviceSetup'), [('accountNumber') : 2], 
    FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('Webrtc/Gateway_Call_Webrtc_login/LoginToWebappWithUserGatewayCall'), [:], FailureHandling.STOP_ON_FAILURE)

for (int i = 0; i < 2; i++) {
    DriverFactory.changeWebDriver(GlobalVariable.user1_creation_details.get('window'))

    CustomKeywords.'webRTC_Utility.WEBRTC.make_call'(GlobalVariable.user2_creation_details.get('phone_number'))

    WebUI.delay(10)

    DriverFactory.changeWebDriver(GlobalVariable.user2_creation_details.get('window'))

    CustomKeywords.'webRTC_Utility.WEBRTC.receive_call'()

    DriverFactory.changeWebDriver(GlobalVariable.user1_creation_details.get('window'))

    CustomKeywords.'webRTC_Utility.WEBRTC.hang_up_the_call'()

    WebUI.delay(5)
}


GlobalVariable.user1_creation_details.get('window').close()

GlobalVariable.user2_creation_details.get('window').close()

