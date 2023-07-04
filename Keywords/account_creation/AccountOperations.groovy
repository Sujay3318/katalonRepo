package account_creation
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject
import internal.GlobalVariable
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.exception.WebElementNotFoundException
import com.kms.katalon.core.testdata.reader.ExcelFactory as Excel


public class AccountOperations{
	/*
	 * verifying account creation wizard values
	 */
	@Keyword
	def accountWizardVerification(accountName,adminEmail,firstName,lastName,street,city,state,timeZone,language,trunkLimit){

		WebUI.callTestCase(findTestCase('Apps/App_Accounts/SC_Verify_Account_Wizard_Page_Load'), [:], FailureHandling.STOP_ON_FAILURE)

		//verify Account Name
		def account_Name = WebUI.getText(findTestObject('Object Repository/Apps/App_Accounts/Account_Creation/Account_Wizard/AccountName_Value'),
				FailureHandling.STOP_ON_FAILURE)
		WebUI.verifyEqual(account_Name, accountName, FailureHandling.STOP_ON_FAILURE)

		//verify trunk value
		def trunkValue = WebUI.getText(findTestObject('Object Repository/Apps/App_Accounts/Account_Creation/Account_Wizard/TrunkValue'),FailureHandling.STOP_ON_FAILURE)
		WebUI.verifyEqual(trunkValue,trunkLimit , FailureHandling.STOP_ON_FAILURE)

		//verify TimeZone
		def time_Zone = WebUI.getText(findTestObject('Object Repository/Apps/App_Accounts/Account_Creation/Account_Wizard/TimeZone'),
				FailureHandling.STOP_ON_FAILURE)
		WebUI.verifyEqual(time_Zone, timeZone, FailureHandling.STOP_ON_FAILURE)

		//verify Language
		def lang = WebUI.getText(findTestObject('Object Repository/Apps/App_Accounts/Account_Creation/Account_Wizard/Language'),
				FailureHandling.STOP_ON_FAILURE)
		WebUI.verifyEqual(lang, language, FailureHandling.STOP_ON_FAILURE)

		//verify Email
		def admin_Email = WebUI.getText(findTestObject('Object Repository/Apps/App_Accounts/Account_Creation/Account_Wizard/admin_Email'), FailureHandling.STOP_ON_FAILURE)
		WebUI.verifyEqual(admin_Email, adminEmail, FailureHandling.STOP_ON_FAILURE)

		//verify Name
		def name = firstName+' '+lastName
		def admin_Name = WebUI.getText(findTestObject('Object Repository/Apps/App_Accounts/Account_Creation/Account_Wizard/admin_Name'), FailureHandling.STOP_ON_FAILURE)
		WebUI.verifyEqual(admin_Name, name, FailureHandling.STOP_ON_FAILURE)

		//verify Realm
		def realm = WebUI.getText(findTestObject('Object Repository/Apps/App_Accounts/Account_Creation/Account_Wizard/realm'), FailureHandling.STOP_ON_FAILURE)
		realm.isEmpty()?FailureHandling.STOP_ON_FAILURE : WebUI.verifyEqual(realm, GlobalVariable.accountRealm, FailureHandling.STOP_ON_FAILURE)

		//verify Service Plan
		if(WebUI.verifyElementVisible(findTestObject('Apps/App_Accounts/Account_Creation/Account_Wizard/Service_Plan'), FailureHandling.OPTIONAL)){
			def service_Plan = WebUI.getText(findTestObject('Object Repository/Apps/App_Accounts/Account_Creation/Account_Wizard/Service_Plan'), FailureHandling.OPTIONAL)

			if(service_Plan.isEmpty() || service_Plan == 'null'){
				WebUI.verifyEqual(service_Plan, 'No service plan selected', FailureHandling.STOP_ON_FAILURE)}
			else{WebUI.verifyEqual(service_Plan, GlobalVariable.servicePlan , FailureHandling.STOP_ON_FAILURE)}
		}

		//verify Primary ServiceAddress
		def streetAddress = street+" "+"\r\n"+city+','+' '+state+' '+'14620'
		def address = WebUI.getText(findTestObject('Object Repository/Apps/App_Accounts/Account_Creation/Account_Wizard/Address'),FailureHandling.STOP_ON_FAILURE)
		address.contains(streetAddress)?println('Address is verified') : FailureHandling.STOP_ON_FAILURE

		//verify Email enabled
		def emailEnabled = WebUI.getText(findTestObject('Apps/App_Accounts/Account_Creation/Account_Wizard/email_Enabled'))
		emailEnabled.equals('No') ? WebUI.verifyEqual(emailEnabled,'No') : WebUI.verifyEqual(emailEnabled, 'Yes')

		WebUI.callTestCase(findTestCase('Apps/App_Accounts/SC_Verify_Usage_Call_Restrictions'), [:], FailureHandling.STOP_ON_FAILURE)

		WebUI.callTestCase(findTestCase('Apps/App_Accounts/SC_Verify_Credit_Balance_Features'), [:], FailureHandling.STOP_ON_FAILURE)
	}
	/*
	 * resign-in into a newly created account to assign apps
	 */
	@Keyword
	def signIn(userName,password,accountName){
		WebUI.waitForElementVisible(findTestObject('Sign_in_Landing_Page_Objects/button_Sign in'), 30, FailureHandling.STOP_ON_FAILURE)

		WebUI.setText(findTestObject('Sign_in_Landing_Page_Objects/input_Username_login'), userName)

		WebUI.setText(findTestObject('Sign_in_Landing_Page_Objects/input_Password_password'), password)

		WebUI.setText(findTestObject('Sign_in_Landing_Page_Objects/input_Account Name_account_name'), accountName)

		WebUI.click(findTestObject('Sign_in_Landing_Page_Objects/button_Sign in'),FailureHandling.STOP_ON_FAILURE)

		if(WebUI.waitForElementPresent(findTestObject('Kazoo_Landing_Page/svg_Welcome_to_the_platform'),20, FailureHandling.OPTIONAL)){
			println(WebUI.getText(findTestObject('Kazoo_Landing_Page/svg_Welcome_to_the_platform'),FailureHandling.OPTIONAL))

			WebUI.enhancedClick(findTestObject('Kazoo_Landing_Page/window_close'), FailureHandling.OPTIONAL)}

		if(WebUI.waitForElementPresent(findTestObject('Object Repository/Kazoo_Landing_Page/TopBar_myaccount_user'), 15, FailureHandling.OPTIONAL)){
			println("Login is successful")
		}
		else if(WebUI.verifyElementPresent(findTestObject('Object Repository/Sign_in_Landing_Page_Objects/div_error_message'), 15, FailureHandling.OPTIONAL)){

			println(WebUI.getText(findTestObject('Object Repository/Sign_in_Landing_Page_Objects/div_error_message')))
			KeywordUtil.markFailedAndStop('Login is failed')

		}
	}

	/*
	 *  verifying the account overview steps
	 */
	@Keyword
	def  verifyCreatedAccount(accountName,time_Zone){
		WebUI.delay(10)

		def status = WebUI.getText(findTestObject('Object Repository/Apps/App_Accounts/Account_Creation/Account_Overview/a_AccountStatus'),
				FailureHandling.STOP_ON_FAILURE)
		status.contains('Active')?println("Status is active") : FailureHandling.STOP_ON_FAILURE

		// verify Account Name
		def account_Name = WebUI.getText(findTestObject('Object Repository/Apps/App_Accounts/Account_Creation/Account_Overview/a_AccountName'),
				FailureHandling.STOP_ON_FAILURE)
		WebUI.verifyEqual(account_Name, accountName, FailureHandling.STOP_ON_FAILURE)

		//verify Realm
		def realm = WebUI.getText(findTestObject('Object Repository/Apps/App_Accounts/Account_Creation/Account_Overview/a_Realm'),
				FailureHandling.STOP_ON_FAILURE)

		// Added as a workaround till MACT-89 is resolved
		if (GlobalVariable.version.startsWith("5")){
			realm.isEmpty()? FailureHandling.STOP_ON_FAILURE : WebUI.verifyEqual(realm, GlobalVariable.accountRealm.toLowerCase(), FailureHandling.STOP_ON_FAILURE)
		}
		else {

			realm.isEmpty()? FailureHandling.STOP_ON_FAILURE : WebUI.verifyEqual(realm, GlobalVariable.accountRealm, FailureHandling.STOP_ON_FAILURE)
		}


		//verify TimeZone
		def timeZone = WebUI.getText(findTestObject('Object Repository/Apps/App_Accounts/Account_Creation/Account_Overview/a_Timezone_Language'),
				FailureHandling.STOP_ON_FAILURE)
		timeZone.contains(time_Zone)? println("TimeZone is Valid") : FailureHandling.STOP_ON_FAILURE


		WebUI.verifyEqual(WebUI.getText(findTestObject('Apps/App_Accounts/Account_Creation/Account_Overview/span_language'), FailureHandling.STOP_ON_FAILURE), "en-US", FailureHandling.STOP_ON_FAILURE)

		WebUI.callTestCase(findTestCase('Apps/App_Accounts/SC_Verify_Account_Overview_Limits'), [:], FailureHandling.STOP_ON_FAILURE)
	}

	/*
	 * delete an account by providing account name
	 */
	@Keyword
	def deleteAccount(accountName){
		WebUI.callTestCase(findTestCase('Test Cases/Default_Kazoo_Sign_In and SIgn_Out/Official_Super_Admin_Kazoo_Sign_In'), [:],
		FailureHandling.STOP_ON_FAILURE)

		WebUI.callTestCase(findTestCase('Apps/Click_Apps_Menu'), [:], FailureHandling.STOP_ON_FAILURE)

		WebUI.callTestCase(findTestCase('Apps/App_Accounts/SC_Click_Accounts_App'), [:], FailureHandling.STOP_ON_FAILURE)

		WebUI.click(findTestObject('Apps/App_Accounts/Delete_Account/search_box'))

		WebUI.setText(findTestObject('Apps/App_Accounts/Delete_Account/search_box'), accountName, FailureHandling.STOP_ON_FAILURE)

		WebUI.click(findTestObject('Object Repository/Apps/App_Accounts/Delete_Account/specific_account', [('name') : accountName]))

		WebUI.click(findTestObject('Object Repository/Apps/App_Accounts/span_Account Status'))

		WebUI.click(findTestObject('Apps/App_Accounts/button_Delete'))

		WebUI.setText(findTestObject('Post_Release_Validation/input_Confirmation of Account Deletion_dele_2e6bf5'), 'DELETE')

		WebUI.click(findTestObject('Apps/App_Accounts/button_Delete Account'))

		WebUI.callTestCase(findTestCase('Default_Kazoo_Sign_In and SIgn_Out/Official_Kazoo_Sign_Out'), [:], FailureHandling.STOP_ON_FAILURE)

		WebUI.closeBrowser()
	}
}