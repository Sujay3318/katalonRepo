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

/*Scenario:Offnet origination termination Gateway call,user1 calls to user2,user2 receive the call and after 70 sec user1 hangs up the call
 *here trunk limit is disabled and credit added to account
 *account1 user1=caller
 *account2 user2=callee
 *user1 calls to user2
 *user2 receive the call
 *user1 hangs up the call
 */
WebUI.callTestCase(findTestCase('CDR/Setup/Gateway_Call_Setup/GenericAccountUserDeviceSetup'), [('accountNumber') : 2], 
    FailureHandling.STOP_ON_FAILURE)

List<HashMap> listOfaccountDetailsMap = new ArrayList()

listOfaccountDetailsMap.add(GlobalVariable.gateway_call_account_details1)

listOfaccountDetailsMap.add(GlobalVariable.gateway_call_account_details2)

for (int accountDetailsMapIndex = 0; accountDetailsMapIndex < listOfaccountDetailsMap.size(); accountDetailsMapIndex++) {
    /*
 * disable the trunk limit
 */
    def limitResponse = CustomKeywords.'cdr_Utility.API_CDR_setup_keyword.disable_account_limits'(listOfaccountDetailsMap.get(
            accountDetailsMapIndex).get('account_id'))

    WS.verifyResponseStatusCode(limitResponse, 200)

    /*
 * add credit to account
 */
    def creditResponse = CustomKeywords.'cdr_Utility.API_CDR_setup_keyword.credit_amount_to_accounts'(listOfaccountDetailsMap.get(
            accountDetailsMapIndex).get('account_id'))

    WS.verifyResponseStatusCode(creditResponse, 200)
}

WebUI.callTestCase(findTestCase('Webrtc/Gateway_Call_Webrtc_login/LoginToWebappWithUserGatewayCall'), [:], FailureHandling.STOP_ON_FAILURE)

for (int i = 0; i < 2; i++) {
    DriverFactory.changeWebDriver(GlobalVariable.user1_creation_details.get('window'))

    CustomKeywords.'webRTC_Utility.WEBRTC.make_call'(GlobalVariable.user2_creation_details.get('phone_number'))

    WebUI.delay(10)

    DriverFactory.changeWebDriver(GlobalVariable.user2_creation_details.get('window'))

    CustomKeywords.'webRTC_Utility.WEBRTC.receive_call'()

    DriverFactory.changeWebDriver(GlobalVariable.user1_creation_details.get('window'))

    WebUI.delay(60)

    CustomKeywords.'webRTC_Utility.WEBRTC.hang_up_the_call'()

    WebUI.delay(5)
}

GlobalVariable.user1_creation_details.get('window').close()

GlobalVariable.user2_creation_details.get('window').close()

