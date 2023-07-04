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
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW


WebUI.callTestCase(findTestCase('CDR/Setup/OnnetAccountUserDeviceWebrtcAppSetupByAPI'), [('numberOfUser') : 2], FailureHandling.STOP_ON_FAILURE)

List<HashMap> listOfUserDetailsMaps = new ArrayList()
listOfUserDetailsMaps = CustomKeywords.'cdr_Utility.API_CDR_setup_keyword.list_of_2users'()

def limit_response = CustomKeywords.'cdr_Utility.API_CDR_setup_keyword.set_account_limits'(GlobalVariable.sub_account_details.get(
        'account_id'))

WS.verifyResponseStatusCode(limit_response, 200)

for (int userDetailsListIndex = 0; userDetailsListIndex < listOfUserDetailsMaps.size(); userDetailsListIndex++) {
    def availablePhoneNumResponse = CustomKeywords.'phone_number_utility.Phone_number_API.getFirstAvailablePhoneNumber'(
        GlobalVariable.sub_account_details.get('account_id'), GlobalVariable.credentials, GlobalVariable.auth_token, GlobalVariable.country_code)

    def fax_number_id = CustomKeywords.'kazoo_operation_Utility.ApiCommonOperations.parseResponseData'(availablePhoneNumResponse).data[
    0].number

    println(fax_number_id)

    CustomKeywords.'phone_number_utility.Phone_number_API.putActivateSinglePhoneNumber'(GlobalVariable.sub_account_details.get(
            'account_id'), GlobalVariable.credentials, GlobalVariable.auth_token, fax_number_id)

    listOfUserDetailsMaps.get(userDetailsListIndex).put('fax_number', fax_number_id)

    def faxbox_response = CustomKeywords.'cdr_Utility.API_CDR_setup_keyword.create_new_faxbox'(GlobalVariable.sub_account_details.get(
            'account_id'), listOfUserDetailsMaps.get(userDetailsListIndex).get('user_id'), fax_number_id, listOfUserDetailsMaps.get(
            userDetailsListIndex).get('email'), listOfUserDetailsMaps.get(userDetailsListIndex).get('first_name'))

    println(CustomKeywords.'kazoo_operation_Utility.ApiCommonOperations.parseResponseData'(faxbox_response))

    WS.verifyResponseStatusCode(faxbox_response, 201)

    def faxbox_id = CustomKeywords.'kazoo_operation_Utility.ApiCommonOperations.parseResponseData'(faxbox_response).data.id

    def faxbox_res = CustomKeywords.'cdr_Utility.API_CDR_setup_keyword.mapping_faxnumber_to_faxbox'(GlobalVariable.sub_account_details.get(
            'account_id'), listOfUserDetailsMaps.get(userDetailsListIndex).get('user_id'), fax_number_id, faxbox_id)

    println(CustomKeywords.'kazoo_operation_Utility.ApiCommonOperations.parseResponseData'(faxbox_res))

    WS.verifyResponseStatusCode(faxbox_res, 201)

    def patch_response = CustomKeywords.'cdr_Utility.API_CDR_setup_keyword.enable_faxbox_feature'(GlobalVariable.sub_account_details.get(
            'account_id'), listOfUserDetailsMaps.get(userDetailsListIndex).get('user_id'))

    println(CustomKeywords.'kazoo_operation_Utility.ApiCommonOperations.parseResponseData'(patch_response))

    WS.verifyResponseStatusCode(patch_response, 200)
}

/*
 * Call1 : agent1 will send email to agent2 
 * Email is send using fax number
 */
def receiver_mail_1 = (GlobalVariable.user2_creation_details.get('fax_number') + '@') + GlobalVariable.sub_account_details.get(
    'account_realm')

CustomKeywords.'email_Utility.email_operations.send_mail_with_attachement'(GlobalVariable.user1_creation_details.get('email'), 
    GlobalVariable.user1_creation_details.get('password'), receiver_mail_1)

/*
 * Call2 : agent2 will send email to agent1
 * Email is send using fax number
 */
def receiver_mail_2 = (GlobalVariable.user1_creation_details.get('fax_number') + '@') + GlobalVariable.sub_account_details.get(
    'account_realm')

CustomKeywords.'email_Utility.email_operations.send_mail_with_attachement'(GlobalVariable.user2_creation_details.get('email'), 
    GlobalVariable.user2_creation_details.get('password'), receiver_mail_2)

