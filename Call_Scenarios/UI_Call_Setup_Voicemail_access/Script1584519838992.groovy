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

WebUI.callTestCase(findTestCase('CDR/Setup/OnnetAccountUserDeviceWebrtcAppSetupByAPI'), [('numberOfUser') : 2], FailureHandling.STOP_ON_FAILURE)

/*Enable voicemail and DND feature of user*/

List<HashMap> listOfUserDetailsMaps = new ArrayList()

listOfUserDetailsMaps = CustomKeywords.'cdr_Utility.API_CDR_setup_keyword.list_of_2users'()

for (int userDetailsListIndex = 0; userDetailsListIndex < listOfUserDetailsMaps.size(); userDetailsListIndex++) {
    //def res = CustomKeywords.'cdr_Utility.API_CDR_setup_keyword.enable_DND_feature'(GlobalVariable.sub_account_details.get("account_id"),listOfUserDetailsMaps.get(userDetailsListIndex).get("user_id"))
    def vmbox_name = listOfUserDetailsMaps.get(userDetailsListIndex).get('first_name') + 'VMbox'

    def vmbox_response = CustomKeywords.'cdr_Utility.API_CDR_setup_keyword.new_VMbox_creation'(GlobalVariable.sub_account_details.get(
            'account_id'), listOfUserDetailsMaps.get(userDetailsListIndex).get('user_id'), listOfUserDetailsMaps.get(userDetailsListIndex).get(
            'extension'), vmbox_name)

    println(CustomKeywords.'kazoo_operation_Utility.ApiCommonOperations.parseResponseData'(vmbox_response))

    WS.verifyResponseStatusCode(vmbox_response, 201)

    def vmbox_id = CustomKeywords.'kazoo_operation_Utility.ApiCommonOperations.parseResponseData'(vmbox_response).data.id

    def patch_response = CustomKeywords.'cdr_Utility.API_CDR_setup_keyword.enable_VMbox_feature'(GlobalVariable.sub_account_details.get(
            'account_id'), listOfUserDetailsMaps.get(userDetailsListIndex).get('user_id'), listOfUserDetailsMaps.get(userDetailsListIndex).get(
            'callflow_id'), vmbox_id)

    //def patch_response = CustomKeywords.'cdr_Utility.API_CDR_setup_keyword.enable_VMbox_feature'("16c24b403365e0e19b7ba41d0988ef6b", "4a8aae20640487f98352d8a36a9a4d11", "f3e48a3642f410db1eceba1b8abe8f66",vmbox_id)
    println(CustomKeywords.'kazoo_operation_Utility.ApiCommonOperations.parseResponseData'(patch_response))

    WS.verifyResponseStatusCode(patch_response, 200)

    
}

WebUI.callTestCase(findTestCase('Webrtc/OnnetLoginToWebrtcAppWithUser'), [:], FailureHandling.STOP_ON_FAILURE)

/*
 *call_1
 *caller = user2
 *callee = user2(enable the feature of vaoicemail)
 *Check Voicemail access
 */
//DriverFactory.changeWebDriver(GlobalVariable.user1_creation_details.get('window'))
CustomKeywords.'webRTC_Utility.WEBRTC.make_call'(GlobalVariable.user2_creation_details.get('phone_number'))

WebUI.delay(10)

CustomKeywords.'webRTC_Utility.WEBRTC.hang_up_the_call'()

WebUI.delay(5)

/*
 *call_2
 *caller = user1
 *callee = user1(enable the feature of vaoicemail)
 *Check Voicemail access
 */
DriverFactory.changeWebDriver(GlobalVariable.user1_creation_details.get('window'))

CustomKeywords.'webRTC_Utility.WEBRTC.make_call'(GlobalVariable.user1_creation_details.get('phone_number'))

WebUI.delay(10)

CustomKeywords.'webRTC_Utility.WEBRTC.hang_up_the_call'()

WebUI.delay(5)

GlobalVariable.user1_creation_details.get('window').close()

GlobalVariable.user2_creation_details.get('window').close()

