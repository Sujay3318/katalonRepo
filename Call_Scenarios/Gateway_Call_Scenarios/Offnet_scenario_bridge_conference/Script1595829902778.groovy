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

/*
Scenario:Gateway call,conference bridge
user1,user2 and user 3 are in conference call, and user1 having conference feature,user2 and user3 joined conference call by dialing conference number.
*/
WebUI.callTestCase(findTestCase('CDR/Setup/Gateway_Call_Setup/GenericAccountUserDeviceSetup'), [('accountNumber') : 3], 
    FailureHandling.STOP_ON_FAILURE)

def availablePhoneNumResponse = CustomKeywords.'phone_number_utility.Phone_number_API.getFirstAvailablePhoneNumber'(GlobalVariable.gateway_call_account_details1.get(
        'account_id'), GlobalVariable.credentials, GlobalVariable.auth_token, GlobalVariable.country_code)

def number_id = CustomKeywords.'kazoo_operation_Utility.ApiCommonOperations.parseResponseData'(availablePhoneNumResponse).data[
0].number

println(number_id)

CustomKeywords.'phone_number_utility.Phone_number_API.putActivateSinglePhoneNumber'(GlobalVariable.gateway_call_account_details1.get(
        'account_id'), GlobalVariable.credentials, GlobalVariable.auth_token, number_id)

def response1 = CustomKeywords.'cdr_Utility.API_CDR_setup_keyword.set_carrier_module'(GlobalVariable.gateway_call_account_details1.get(
        'account_id'), number_id)

WS.verifyResponseStatusCode(response1, 200)

GlobalVariable.user1_creation_details.put('conference_number', number_id)

def app_response = CustomKeywords.'cdr_Utility.API_CDR_setup_keyword.assign_conferences_apps_to_account'(GlobalVariable.gateway_call_account_details1.get(
        'account_id'))

WS.verifyResponseStatusCode(app_response, 201)

def callflow_res = CustomKeywords.'cdr_Utility.API_CDR_setup_keyword.set_conferences_number'(GlobalVariable.gateway_call_account_details1.get(
        'account_id'), number_id)

WS.verifyResponseStatusCode(callflow_res, 201)

def conference_res = CustomKeywords.'cdr_Utility.API_CDR_setup_keyword.create_new_conferences'(GlobalVariable.gateway_call_account_details1.get(
        'account_id'), GlobalVariable.user1_creation_details.get('user_id'), GlobalVariable.conference_pin)

WS.verifyResponseStatusCode(conference_res, 201)

WebUI.callTestCase(findTestCase('Webrtc/Gateway_Call_Webrtc_login/LoginToWebappWithUserGatewayCall'), [:], FailureHandling.STOP_ON_FAILURE)

List<HashMap> listOfUserDetailsMaps = new ArrayList()

listOfUserDetailsMaps = CustomKeywords.'cdr_Utility.API_CDR_setup_keyword.list_of_3users'()

for (int userDetailsListIndex = 0; userDetailsListIndex < listOfUserDetailsMaps.size(); userDetailsListIndex++) {
    DriverFactory.changeWebDriver(listOfUserDetailsMaps.get(userDetailsListIndex).get('window'))

    CustomKeywords.'webRTC_Utility.WEBRTC.make_call'(GlobalVariable.user1_creation_details.get('conference_number'))

    //WebUI.waitForElementVisible(findTestObject('Apps/App_WebRTC/Call_time'), 10)
    WebUI.waitForElementNotPresent(findTestObject('Apps/App_WebRTC/button_connecting'), 10, FailureHandling.STOP_ON_FAILURE)

    WebUI.delay(5)

    for (int x = 0; x < 2; x++) {
        WebUI.delay(5)

        String pin = GlobalVariable.conference_pin

        for (int i = 0; i < pin.length(); i++) {
            WebUI.setText(findTestObject('Apps/App_WebRTC/Webrtc_input_number'), '' + pin.charAt(i))

            WebUI.delay(1)
        }
        
        WebUI.click(findTestObject('Apps/App_WebRTC/Button_hash'))

        WebUI.delay(1)
    }
    
    WebUI.delay(10)

    WebUI.waitForElementPresent(findTestObject('Apps/App_WebRTC/Call_time'), 20, FailureHandling.STOP_ON_FAILURE)
}

WebUI.delay(20)

CustomKeywords.'webRTC_Utility.WEBRTC.hang_up_the_call'()

DriverFactory.changeWebDriver(GlobalVariable.user1_creation_details.get('window'))

CustomKeywords.'webRTC_Utility.WEBRTC.hang_up_the_call'()

DriverFactory.changeWebDriver(GlobalVariable.user2_creation_details.get('window'))

CustomKeywords.'webRTC_Utility.WEBRTC.hang_up_the_call'()

GlobalVariable.user1_creation_details.get('window').close()

GlobalVariable.user2_creation_details.get('window').close()

GlobalVariable.user3_creation_details.get('window').close()

