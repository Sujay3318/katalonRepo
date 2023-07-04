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

WebUI.callTestCase(findTestCase('CDR/Setup/OnnetAccountUserDeviceWebrtcAppSetupByAPI'), [('numberOfUser') : 3], FailureHandling.STOP_ON_FAILURE)

/*Enable call forward feature of user2
 *keep caller_id original 
 */
def response = CustomKeywords.'cdr_Utility.API_CDR_setup_keyword.enable_call_forwarding_feature'(GlobalVariable.sub_account_details.get(
        'account_id'), GlobalVariable.user2_creation_details.get('user_id'), GlobalVariable.user3_creation_details.get('phone_number'), 
    true)

println(CustomKeywords.'kazoo_operation_Utility.ApiCommonOperations.parseResponseData'(response))

WS.verifyResponseStatusCode(response, 200)

WebUI.callTestCase(findTestCase('Webrtc/OnnetLoginToWebrtcAppWithUser'), [:], FailureHandling.STOP_ON_FAILURE)

DriverFactory.changeWebDriver(GlobalVariable.user1_creation_details.get('window'))

CustomKeywords.'webRTC_Utility.WEBRTC.make_call'(GlobalVariable.user2_creation_details.get('phone_number'))

WebUI.delay(5)

DriverFactory.changeWebDriver(GlobalVariable.user3_creation_details.get('window'))

CustomKeywords.'webRTC_Utility.WEBRTC.receive_call'()

CustomKeywords.'webRTC_Utility.WEBRTC.hang_up_the_call'()

WebUI.delay(5)

GlobalVariable.user1_creation_details.get('window').close()

GlobalVariable.user2_creation_details.get('window').close()

GlobalVariable.user3_creation_details.get('window').close()

