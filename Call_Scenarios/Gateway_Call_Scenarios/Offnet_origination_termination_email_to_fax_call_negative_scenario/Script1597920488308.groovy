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

/*Scenario :Offnet origination termination send Fax Call from one user to another user(use non fax number)
 * Account1-UserAgent1
 * Account2-UserAgent2
 * UserAgent1 will send email to UserAgent2(using phonenumber)
 */
WebUI.callTestCase(findTestCase('CDR/Setup/Gateway_Call_Setup/GenericAccountUserDeviceSetup'), [('accountNumber') : 2], 
    FailureHandling.STOP_ON_FAILURE)

List<HashMap> listOfUserDetailsMaps = new ArrayList()

listOfUserDetailsMaps = CustomKeywords.'cdr_Utility.API_CDR_setup_keyword.list_of_2users'()

List<HashMap> listOfaccountDetailsMaps = new ArrayList()

listOfaccountDetailsMaps.add(GlobalVariable.gateway_call_account_details1)

listOfaccountDetailsMaps.add(GlobalVariable.gateway_call_account_details2)

for (int userDetailsListIndex = 0; userDetailsListIndex < listOfUserDetailsMaps.size(); userDetailsListIndex++) {
    //get available phone_number	
    def availablePhoneNumResponse = CustomKeywords.'phone_number_utility.Phone_number_API.getFirstAvailablePhoneNumber'(
        listOfaccountDetailsMaps.get(userDetailsListIndex).get('account_id'), GlobalVariable.credentials, GlobalVariable.auth_token, 
        GlobalVariable.country_code)

    def fax_number_id = CustomKeywords.'kazoo_operation_Utility.ApiCommonOperations.parseResponseData'(availablePhoneNumResponse).data[
    0].number

    println(fax_number_id)

    //activate the phonenumber
    CustomKeywords.'phone_number_utility.Phone_number_API.putActivateSinglePhoneNumber'(listOfaccountDetailsMaps.get(userDetailsListIndex).get(
            'account_id'), GlobalVariable.credentials, GlobalVariable.auth_token, fax_number_id)

    listOfUserDetailsMaps.get(userDetailsListIndex).put('fax_number', fax_number_id)

    //create new faxbox
    def faxboxResponse = CustomKeywords.'cdr_Utility.API_CDR_setup_keyword.create_new_faxbox'(listOfaccountDetailsMaps.get(
            userDetailsListIndex).get('account_id'), listOfUserDetailsMaps.get(userDetailsListIndex).get('user_id'), fax_number_id, 
        listOfUserDetailsMaps.get(userDetailsListIndex).get('email'), listOfUserDetailsMaps.get(userDetailsListIndex).get(
            'first_name'))

    println(CustomKeywords.'kazoo_operation_Utility.ApiCommonOperations.parseResponseData'(faxboxResponse))

    WS.verifyResponseStatusCode(faxboxResponse, 201)

    def faxboxId = CustomKeywords.'kazoo_operation_Utility.ApiCommonOperations.parseResponseData'(faxboxResponse).data.id

    //assign fax_number to faxbox and user
    def faxboxRes = CustomKeywords.'cdr_Utility.API_CDR_setup_keyword.mapping_faxnumber_to_faxbox'(listOfaccountDetailsMaps.get(
            userDetailsListIndex).get('account_id'), listOfUserDetailsMaps.get(userDetailsListIndex).get('user_id'), fax_number_id, 
        faxboxId)

    println(CustomKeywords.'kazoo_operation_Utility.ApiCommonOperations.parseResponseData'(faxboxRes))

    WS.verifyResponseStatusCode(faxboxRes, 201)

    //enable the faxbox
    def patchResponse = CustomKeywords.'cdr_Utility.API_CDR_setup_keyword.enable_faxbox_feature'(listOfaccountDetailsMaps.get(
            userDetailsListIndex).get('account_id'), listOfUserDetailsMaps.get(userDetailsListIndex).get('user_id'))

    println(CustomKeywords.'kazoo_operation_Utility.ApiCommonOperations.parseResponseData'(patchResponse))

    WS.verifyResponseStatusCode(patchResponse, 200)

    //set carrier module as local for fax_number
    def response1 = CustomKeywords.'cdr_Utility.API_CDR_setup_keyword.set_carrier_module'(listOfaccountDetailsMaps.get(userDetailsListIndex).get(
            'account_id'), fax_number_id)

    WS.verifyResponseStatusCode(response1, 200)
}

def receiverMail_1 = (GlobalVariable.user2_creation_details.get('phone_number') + '@') + GlobalVariable.gateway_call_account_details1.get(
    'account_realm')

CustomKeywords.'email_Utility.email_operations.send_mail_with_attachement'(GlobalVariable.user1_creation_details.get('email'), 
    GlobalVariable.user1_creation_details.get('password'), receiverMail_1)

WS.delay(30)

CustomKeywords.'email_Utility.email_operations.send_mail_with_attachement'(GlobalVariable.user1_creation_details.get('email'), 
    GlobalVariable.user1_creation_details.get('password'), receiverMail_1)

