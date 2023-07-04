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
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

/*Scenario:Offnet call to tollfree with credit amount(rate feature and trunk both are disabled)
 *account1 user1=caller
 *Make call to toll free number
 *Hangup call after 30 sec
 */
WebUI.callTestCase(findTestCase('CDR/Setup/Gateway_Call_Setup/GenericAccountUserDeviceSetup'), [('accountNumber') : 1], 
    FailureHandling.STOP_ON_FAILURE)

//add amount to account

def creditResponse = CustomKeywords.'cdr_Utility.API_CDR_setup_keyword.credit_amount_to_accounts'(GlobalVariable.gateway_call_account_details1.get(
        'account_id'))

WS.verifyResponseStatusCode(creditResponse, 200)

WebUI.callTestCase(findTestCase('Webrtc/Gateway_Call_Webrtc_login/LoginToWebappWithUserGatewayCall'), [:], FailureHandling.STOP_ON_FAILURE)

//make call to tollfree number
CustomKeywords.'webRTC_Utility.WEBRTC.make_call'(GlobalVariable.tollfree_number)

WebUI.delay(30)

//hangup the call
CustomKeywords.'webRTC_Utility.WEBRTC.hang_up_the_call'()

WebUI.delay(5)

GlobalVariable.user1_creation_details.get('window').close()

