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

/*
 * Scenario: Offnet origination termaination,call goes to voicemail due to no answer from callee
 * account1 user1=caller
 * account2 user2=callee
 * user1 calls to user2
 * call goes to voicemail
 */
WebUI.callTestCase(findTestCase('CDR/Setup/Gateway_Call_Setup/GenericAccountUserDeviceSetup'), [('accountNumber') : 2], 
    FailureHandling.STOP_ON_FAILURE)

def dndResponse = CustomKeywords.'cdr_Utility.API_CDR_setup_keyword.enable_DND_feature'(GlobalVariable.gateway_call_account_details2.get(
        'account_id'), GlobalVariable.user2_creation_details.get('user_id'))

println(CustomKeywords.'kazoo_operation_Utility.ApiCommonOperations.parseResponseData'(dndResponse))

WS.verifyResponseStatusCode(dndResponse, 200)

def vmboxName = GlobalVariable.user2_creation_details.get('first_name') + 'VMbox'

def vmboxResponse = CustomKeywords.'cdr_Utility.API_CDR_setup_keyword.new_VMbox_creation'(GlobalVariable.gateway_call_account_details2.get(
        'account_id'), GlobalVariable.user2_creation_details.get('user_id'), GlobalVariable.user2_creation_details.get('extension'), 
    vmboxName)

println(CustomKeywords.'kazoo_operation_Utility.ApiCommonOperations.parseResponseData'(vmboxResponse))

WS.verifyResponseStatusCode(vmboxResponse, 201)

def vmboxId = CustomKeywords.'kazoo_operation_Utility.ApiCommonOperations.parseResponseData'(vmboxResponse).data.id

def patchResponse = CustomKeywords.'cdr_Utility.API_CDR_setup_keyword.enable_VMbox_feature'(GlobalVariable.gateway_call_account_details2.get(
        'account_id'), GlobalVariable.user2_creation_details.get('user_id'), GlobalVariable.user2_creation_details.get('callflow_id'), 
    vmboxId)

println(CustomKeywords.'kazoo_operation_Utility.ApiCommonOperations.parseResponseData'(patchResponse))

WS.verifyResponseStatusCode(patchResponse, 200)

WebUI.callTestCase(findTestCase('Webrtc/Gateway_Call_Webrtc_login/LoginToWebappWithUserGatewayCall'), [:], FailureHandling.STOP_ON_FAILURE)

for (int i = 0; i < 2; i++) {
    if (i == 1) {
        WebUI.refresh()

        WebUI.waitForPageLoad(30)
    }
    
    //Move to caller window
    DriverFactory.changeWebDriver(GlobalVariable.user1_creation_details.get('window'))

    //Make a call through webrtc app
    CustomKeywords.'webRTC_Utility.WEBRTC.make_call'(GlobalVariable.user2_creation_details.get('phone_number'))

    //Ringing seconds
    WebUI.delay(15)

    //Hangsup the call
    CustomKeywords.'webRTC_Utility.WEBRTC.hang_up_the_call'()

    WebUI.delay(5)
}

//close both caller and callee window
GlobalVariable.user1_creation_details.get('window').close()

GlobalVariable.user2_creation_details.get('window').close()

