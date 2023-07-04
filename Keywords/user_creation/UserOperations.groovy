package user_creation
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords
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
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.exception.WebElementNotFoundException
import static org.junit.Assert.*

public class UserOperations {

	/*
	 * provide user name 
	 * Store the phone number of user name to list,
	 * To fetch phone number from list, we need to use index
	 */
	@Keyword
	def storePhoneNumberToList(String firstName, String lastName){

		String user_name = " "+firstName+ " "+lastName

		String phoneNumber = WebUI.getText(findTestObject('Apps/SmartPBX/PhoneNumbers/specific_user_phoneNumber',[('user_name') : user_name]),FailureHandling.STOP_ON_FAILURE)

		//Removing Spaces from Phone number
		phoneNumber = phoneNumber.replaceAll('\\s', '').replaceAll("-", "")

		GlobalVariable.gateway_numbers.add(phoneNumber)
	}

	/*
	 * Add Phone Numbers to number of Users  
	 * Provide Number of Users as parameter
	 */
	@Keyword
	def addPhoneNumberToUsers(Number numberOfUsers){

		for (def row1 = 1; row1 <= numberOfUsers; row1++) {
			//Get the  phone number cell data of user
			TestObject phoneNumberCell = findTestObject('Apps/SmartPBX/PhoneNumbers/empty_phonenumber')

			//change Xpath property to a new value
			phoneNumberCell.findProperty('xpath').setValue((('(//*[@class =\'phone-number grid-cell\' and @data-type = \'numbers\'])' +
					'[') + row1) + ']')

			def  phoneNumberCellText = WebUI.getText(phoneNumberCell, FailureHandling.STOP_ON_FAILURE)

			//Check for None Text Present in phone number
			if (WebUI.verifyEqual(phoneNumberCellText, 'None', FailureHandling.OPTIONAL)) {

				WebUI.enhancedClick(phoneNumberCell, FailureHandling.STOP_ON_FAILURE)

				WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/div_EmptyNumber_Text'), 30, FailureHandling.STOP_ON_FAILURE)

				def emptyText = WebUI.getText(findTestObject('Apps/SmartPBX/PhoneNumbers/div_EmptyNumber_Text'), FailureHandling.STOP_ON_FAILURE)

				WebUI.verifyEqual(emptyText, 'There are no numbers assigned to this user.',FailureHandling.STOP_ON_FAILURE)

				WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/buy_numbers'), FailureHandling.STOP_ON_FAILURE)

				WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/local'), FailureHandling.STOP_ON_FAILURE)

				WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/title_buy_numbers'), FailureHandling.STOP_ON_FAILURE)

				WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/label_country'), FailureHandling.STOP_ON_FAILURE)

				WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/label_area_code'), FailureHandling.STOP_ON_FAILURE)

				WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/button_Search'), FailureHandling.STOP_ON_FAILURE)

				for(def code : GlobalVariable.country_code_list){

					println(code)

					WebUI.click(findTestObject('Apps/SmartPBX/PhoneNumbers/input_country_code'))

					WebUI.setText(findTestObject('Apps/SmartPBX/PhoneNumbers/input_country_code'), code, FailureHandling.STOP_ON_FAILURE)

					WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/button_Search'), FailureHandling.STOP_ON_FAILURE)

					println(WebUI.getText(findTestObject('Apps/SmartPBX/PhoneNumbers/span_probe_area_code'), FailureHandling.STOP_ON_FAILURE))

					if(WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/phone_number_list'), FailureHandling.STOP_ON_FAILURE)){
						WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/add_number_to_cart'), FailureHandling.STOP_ON_FAILURE)

						WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/button_buy_numbers'), FailureHandling.STOP_ON_FAILURE)

						WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/button_buy_numbers'), FailureHandling.STOP_ON_FAILURE)

						if(WebUI.waitForElementPresent(findTestObject('Apps/SmartPBX/PhoneNumbers/charges_confirmation'), 5, FailureHandling.OPTIONAL)){
							WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/charges_confirmation_circle'), FailureHandling.STOP_ON_FAILURE)

							WebUI.waitForElementVisible(findTestObject('Apps/App_Accounts/button_OK'), 10, FailureHandling.STOP_ON_FAILURE)

							WebUI.enhancedClick(findTestObject('Apps/App_Accounts/button_OK'), FailureHandling.STOP_ON_FAILURE)
						}
						WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/button_Save_Changes'), 10, FailureHandling.STOP_ON_FAILURE)

						WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/button_Save_Changes'), FailureHandling.STOP_ON_FAILURE)

						//Toast message  after phone number added successfully
						if(WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/phoneNumber_added_message'), 10, FailureHandling.STOP_ON_FAILURE)){
							println(WebUI.getText(findTestObject('Apps/SmartPBX/PhoneNumbers/phoneNumber_added_message'), FailureHandling.STOP_ON_FAILURE))
						}

						break
					}
					else{
						WebUI.waitForElementPresent(findTestObject('Apps/SmartPBX/PhoneNumbers/empty_number_text'),10, FailureHandling.STOP_ON_FAILURE)

						println(WebUI.getText(findTestObject('Apps/SmartPBX/PhoneNumbers/empty_number_text'), FailureHandling.STOP_ON_FAILURE))

						WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/back_to_search'), FailureHandling.STOP_ON_FAILURE)
					}
				}
			}else{ println("Phone Number present for User" + row1+" :" + phoneNumberCellText)}
		}
	}

	/*
	 * Provide user name as input
	 * delete an user from an user tab
	 */
	@Keyword
	def deleteUser(String firstName, String lastName){
		def user_name = " "+firstName+" "+lastName

		WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/Users/specific_User', [('user_name') : user_name]), 10, FailureHandling.STOP_ON_FAILURE)

		WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Users/specific_User', [('user_name') : user_name]), FailureHandling.STOP_ON_FAILURE)

		WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/Users/delete_user'), 20, FailureHandling.STOP_ON_FAILURE)

		WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Users/delete_user'), FailureHandling.STOP_ON_FAILURE)

		WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/charges_confirmation_circle'), 20, FailureHandling.STOP_ON_FAILURE)

		WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/Users/checkbox_remove_device'), 5, FailureHandling.STOP_ON_FAILURE)

		WebUI.check(findTestObject('Apps/SmartPBX/Users/checkbox_remove_device'), FailureHandling.STOP_ON_FAILURE)

		WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/Users/checkbox_remove_conference'), 5, FailureHandling.STOP_ON_FAILURE)

		WebUI.check(findTestObject('Apps/SmartPBX/Users/checkbox_remove_conference'), FailureHandling.STOP_ON_FAILURE)

		WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Users/button_Proceed'), FailureHandling.STOP_ON_FAILURE)

		println(WebUI.getText(findTestObject('Apps/SmartPBX/Devices/status_message'),FailureHandling.STOP_ON_FAILURE))
	}

	/*
	 * Provide First name and Last name of User
	 * Add Carrier Module for the Phone number of User name provide
	 */
	@Keyword
	def setCarrierModule(String firstName,String lastName, String carrierModule){

		def user_name = " "+firstName+" "+lastName

		WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/specific_user_phoneNumber', [('user_name') : user_name]), 10, FailureHandling.STOP_ON_FAILURE)

		def phoneNumberCellText = WebUI.getText(findTestObject('Apps/SmartPBX/PhoneNumbers/specific_user_phoneNumber', [('user_name') : user_name]), FailureHandling.STOP_ON_FAILURE)

		//Check for None Text Present in phone number div
		if (WebUI.verifyEqual(phoneNumberCellText.contains('+1'), true, FailureHandling.STOP_ON_FAILURE)) {
			WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/specific_user_phoneNumber', [('user_name') : user_name]), FailureHandling.STOP_ON_FAILURE)

			WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/edit_features'), 10, FailureHandling.STOP_ON_FAILURE)

			switch (carrier) {
				case 'local':
					if (WebUI.waitForElementNotPresent(findTestObject('Apps/SmartPBX/PhoneNumbers/local_symbol'), 5,
					FailureHandling.OPTIONAL)) {
						WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/edit_features'), FailureHandling.STOP_ON_FAILURE)

						if (WebUI.waitForElementPresent(findTestObject('Apps/SmartPBX/PhoneNumbers/Carrier_module'),
						10, FailureHandling.STOP_ON_FAILURE)) {
							WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/Carrier_module'), FailureHandling.STOP_ON_FAILURE)

							WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/rename_carrier_text'),
									10, FailureHandling.STOP_ON_FAILURE)

							WebUI.verifyEqual(WebUI.getText(findTestObject('Apps/SmartPBX/PhoneNumbers/rename_carrier_text'),
									FailureHandling.STOP_ON_FAILURE), 'Rename Carrier Module', FailureHandling.STOP_ON_FAILURE)

							WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/label_carrier_module'),
									10, FailureHandling.STOP_ON_FAILURE)

							WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/select_carrier_module'),
									FailureHandling.STOP_ON_FAILURE)

							WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/option_carrier', [('carrier') : carrier]),
							FailureHandling.STOP_ON_FAILURE)

							WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/btn_Save_Changes'), FailureHandling.STOP_ON_FAILURE)

							println(WebUI.getText(findTestObject('Apps/SmartPBX/PhoneNumbers/phoneNumber_added_message'),
									FailureHandling.OPTIONAL))

							if (WebUI.waitForElementPresent(findTestObject('Apps/SmartPBX/PhoneNumbers/charges_confirmation'),
							5, FailureHandling.OPTIONAL)) {
								WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/charges_confirmation_circle'),
										FailureHandling.STOP_ON_FAILURE)

								WebUI.waitForElementVisible(findTestObject('Apps/App_Accounts/button_OK'), 5, FailureHandling.STOP_ON_FAILURE)

								WebUI.enhancedClick(findTestObject('Apps/App_Accounts/button_OK'), FailureHandling.STOP_ON_FAILURE)
							}

							WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/local_symbol'), 5,
									FailureHandling.OPTIONAL)

							WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/button_Save_Changes'),
									10, FailureHandling.STOP_ON_FAILURE)

							WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/button_Save_Changes'), FailureHandling.STOP_ON_FAILURE)

							WebUI.delay(5)
						}
					} else {
						println('for Phone Number already set a Local Carrier Module')

						WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/button_Save_Changes'),
								10, FailureHandling.STOP_ON_FAILURE)

						WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/button_Save_Changes'), FailureHandling.STOP_ON_FAILURE)

						WebUI.delay(5)
					}

					break
				case 'bandwidth2':
					if (WebUI.waitForElementPresent(findTestObject('Apps/SmartPBX/PhoneNumbers/local_symbol'), 5, FailureHandling.OPTIONAL)) {
						WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/edit_features'), FailureHandling.STOP_ON_FAILURE)

						if (WebUI.waitForElementPresent(findTestObject('Apps/SmartPBX/PhoneNumbers/Carrier_module'),
						10, FailureHandling.STOP_ON_FAILURE)) {
							WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/Carrier_module'), FailureHandling.STOP_ON_FAILURE)

							WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/rename_carrier_text'),
									10, FailureHandling.STOP_ON_FAILURE)

							WebUI.verifyEqual(WebUI.getText(findTestObject('Apps/SmartPBX/PhoneNumbers/rename_carrier_text'),
									FailureHandling.STOP_ON_FAILURE), 'Rename Carrier Module', FailureHandling.STOP_ON_FAILURE)

							WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/label_carrier_module'),
									10, FailureHandling.STOP_ON_FAILURE)

							WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/select_carrier_module'),
									FailureHandling.STOP_ON_FAILURE)

							WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/option_carrier', [('carrier') : carrier]),
							FailureHandling.STOP_ON_FAILURE)

							WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/btn_Save_Changes'), FailureHandling.STOP_ON_FAILURE)

							println(WebUI.getText(findTestObject('Apps/SmartPBX/PhoneNumbers/phoneNumber_added_message'),
									FailureHandling.OPTIONAL))

							if (WebUI.waitForElementPresent(findTestObject('Apps/SmartPBX/PhoneNumbers/charges_confirmation'),
							5, FailureHandling.OPTIONAL)) {
								WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/charges_confirmation_circle'),
										FailureHandling.STOP_ON_FAILURE)

								WebUI.waitForElementVisible(findTestObject('Apps/App_Accounts/button_OK'), 5, FailureHandling.STOP_ON_FAILURE)

								WebUI.enhancedClick(findTestObject('Apps/App_Accounts/button_OK'), FailureHandling.STOP_ON_FAILURE)
							}

							WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/local_symbol'), 5,
									FailureHandling.OPTIONAL)

							WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/button_Save_Changes'),
									10, FailureHandling.STOP_ON_FAILURE)

							WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/button_Save_Changes'), FailureHandling.STOP_ON_FAILURE)

							WebUI.delay(5)
						}
					} else {
						println('for Phone Number already set a Bandwidth2 Carrier Module')

						WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/button_Save_Changes'),
								10, FailureHandling.STOP_ON_FAILURE)

						WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/button_Save_Changes'), FailureHandling.STOP_ON_FAILURE)

						WebUI.delay(5)
					}

					break
				default:
					println('Provide a valid carrier module to set to phone number')}
		} else {
			println('Please add a phone number to user')
		}

	}

	/*
	 * Add Phone number to user
	 * provide first name and last name as parameter
	 */
	def addPhoneNumberToUser(String firstName, String lastName) {
		def user_name = ((' ' + firstName) + ' ') + lastName

		WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/specific_user_phoneNumber', [('user_name') : user_name]),
		10, FailureHandling.STOP_ON_FAILURE)

		def phoneNumberCellText = WebUI.getText(findTestObject('Apps/SmartPBX/PhoneNumbers/specific_user_phoneNumber', [('user_name') : user_name]),
		FailureHandling.STOP_ON_FAILURE)

		//Check for None Text Present in phone number division
		if (WebUI.verifyEqual(phoneNumberCellText, 'None', FailureHandling.OPTIONAL)) {
			WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/specific_user_phoneNumber', [('user_name') : user_name]),
			FailureHandling.STOP_ON_FAILURE)

			WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/div_EmptyNumber_Text'), 30, FailureHandling.STOP_ON_FAILURE)

			WebUI.verifyEqual(WebUI.getText(findTestObject('Apps/SmartPBX/PhoneNumbers/div_EmptyNumber_Text'), FailureHandling.STOP_ON_FAILURE),
					'There are no numbers assigned to this user.', FailureHandling.STOP_ON_FAILURE)

			//Add phonenumber from spare numbers
			if (WebUI.verifyElementClickable(findTestObject('Apps/SmartPBX/Main_Number/a_Add_from_Spare_Numbers'), FailureHandling.OPTIONAL)) {
				//Click on Add from spare numbers
				WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Main_Number/a_Add_from_Spare_Numbers'), FailureHandling.STOP_ON_FAILURE)

				WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/Main_Number/span_List_of_Spare_Numbers'), 5, FailureHandling.STOP_ON_FAILURE)

				WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/Main_Number/span_United_States_Symbol'), 20, FailureHandling.STOP_ON_FAILURE)

				WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/Main_Number/span_United_States_Symbol'), FailureHandling.STOP_ON_FAILURE)

				//click on phone number present in spare numbers
				WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Main_Number/span_United_States_Symbol'), FailureHandling.STOP_ON_FAILURE)

				WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/Main_Number/button_Add_selected_numbers'), 5, FailureHandling.STOP_ON_FAILURE)

				WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/Main_Number/button_Add_selected_numbers'), 20, FailureHandling.STOP_ON_FAILURE)

				WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/Main_Number/button_Add_selected_numbers'), FailureHandling.STOP_ON_FAILURE)

				//add selected numbers present on UI
				WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Main_Number/button_Add_selected_numbers'), FailureHandling.STOP_ON_FAILURE)

				WebUI.delay(10)

				WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/button_Save_Changes'), 10, FailureHandling.STOP_ON_FAILURE)

				WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/button_Save_Changes'), FailureHandling.STOP_ON_FAILURE)

				//Toast message  after phone number added successfully
				if (WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/phoneNumber_added_message'), 10,
				FailureHandling.OPTIONAL)) {
					println(WebUI.getText(findTestObject('Apps/SmartPBX/PhoneNumbers/phoneNumber_added_message'), FailureHandling.STOP_ON_FAILURE))
				}

				WebUI.delay(5)
			} else {//Buy new phonenumber
				WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/buy_numbers'), FailureHandling.STOP_ON_FAILURE)

				WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/local'), FailureHandling.STOP_ON_FAILURE)

				WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/title_buy_numbers'), FailureHandling.STOP_ON_FAILURE)

				WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/label_country'), FailureHandling.STOP_ON_FAILURE)

				WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/label_area_code'), FailureHandling.STOP_ON_FAILURE)

				WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/button_Search'), FailureHandling.STOP_ON_FAILURE)

				for (def code : GlobalVariable.country_code_list) {
					WebUI.click(findTestObject('Apps/SmartPBX/PhoneNumbers/input_country_code'))

					WebUI.setText(findTestObject('Apps/SmartPBX/PhoneNumbers/input_country_code'), code, FailureHandling.STOP_ON_FAILURE)

					WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/button_Search'), FailureHandling.STOP_ON_FAILURE)

					println(WebUI.getText(findTestObject('Apps/SmartPBX/PhoneNumbers/span_probe_area_code'), FailureHandling.STOP_ON_FAILURE))

					if (WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/phone_number_list'), FailureHandling.STOP_ON_FAILURE)) {
						WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/add_number_to_cart'), FailureHandling.STOP_ON_FAILURE)

						WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/button_buy_numbers'), FailureHandling.STOP_ON_FAILURE)

						WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/button_buy_numbers'), FailureHandling.STOP_ON_FAILURE)

						WebUI.delay(5)

						if (WebUI.waitForElementPresent(findTestObject('Apps/SmartPBX/PhoneNumbers/charges_confirmation'), 3,
						FailureHandling.OPTIONAL)) {
							WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/charges_confirmation_circle'),
									FailureHandling.STOP_ON_FAILURE)

							WebUI.waitForElementVisible(findTestObject('Apps/App_Accounts/button_OK'), 5, FailureHandling.STOP_ON_FAILURE)

							WebUI.enhancedClick(findTestObject('Apps/App_Accounts/button_OK'), FailureHandling.STOP_ON_FAILURE)
						}

						WebUI.delay(10)

						WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/button_Save_Changes'), 10, FailureHandling.STOP_ON_FAILURE)

						WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/button_Save_Changes'), FailureHandling.STOP_ON_FAILURE)

						//Toast message  after phone number added successfully
						if (WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/phoneNumber_added_message'),
						10, FailureHandling.OPTIONAL)) {
							println(WebUI.getText(findTestObject('Apps/SmartPBX/PhoneNumbers/phoneNumber_added_message'), FailureHandling.STOP_ON_FAILURE))
						}

						WebUI.delay(5)

						WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/Users/a_Add User'), 20, FailureHandling.STOP_ON_FAILURE)

						break
					} else {
						WebUI.waitForElementPresent(findTestObject('Apps/SmartPBX/PhoneNumbers/empty_number_text'), 10, FailureHandling.STOP_ON_FAILURE)

						println(WebUI.getText(findTestObject('Apps/SmartPBX/PhoneNumbers/empty_number_text'), FailureHandling.STOP_ON_FAILURE))

						WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/back_to_search'), FailureHandling.STOP_ON_FAILURE)
					}
				}
			}
		} else {
			println(('Phone Number present for User' + ' :') + phoneNumberCellText)
		}
	}

	/*
	 * set carrier module for given phone number
	 * provide phone number and carrier module type as parameter
	 */
	@Keyword
	def phoneNumberCarrierModuleSetup(ArrayList<String> phoneNumberList, String carrierModule){

		for(String phoneNumber : phoneNumberList) {
			println(phoneNumber)

			phoneNumber = phoneNumber.replaceAll(" ","")
			println(phoneNumber)
			if(WebUI.verifyElementPresent(findTestObject('Object Repository/Apps/SmartPBX/PhoneNumbers/div_setting_phone_number',['phoneNumber':phoneNumber]), 15, FailureHandling.OPTIONAL))
			{
				println("in progress")


				switch (carrierModule) {
					case 'local':
						if (WebUI.waitForElementNotPresent(findTestObject('Object Repository/Apps/SmartPBX/PhoneNumbers/i_symbol_local',['phoneNumber':phoneNumber]), 10,
						FailureHandling.OPTIONAL)) {

							WebUI.delay(5)
							WebUI.enhancedClick(findTestObject('Object Repository/Apps/SmartPBX/PhoneNumbers/div_setting_phone_number',['phoneNumber':phoneNumber]))

							if (WebUI.waitForElementPresent(findTestObject('Object Repository/Apps/SmartPBX/PhoneNumbers/link_carrier_module',['phoneNumber':phoneNumber]),
							10, FailureHandling.STOP_ON_FAILURE)) {
								WebUI.enhancedClick(findTestObject('Object Repository/Apps/SmartPBX/PhoneNumbers/link_carrier_module',['phoneNumber':phoneNumber]), FailureHandling.STOP_ON_FAILURE)

								WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/rename_carrier_text'),
										10, FailureHandling.STOP_ON_FAILURE)

								WebUI.verifyEqual(WebUI.getText(findTestObject('Apps/SmartPBX/PhoneNumbers/rename_carrier_text'),
										FailureHandling.STOP_ON_FAILURE), 'Rename Carrier Module', FailureHandling.STOP_ON_FAILURE)

								WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/label_carrier_module'),
										10, FailureHandling.STOP_ON_FAILURE)

								WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/select_carrier_module'),
										FailureHandling.STOP_ON_FAILURE)

								WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/option_carrier', [('carrier') : carrierModule]),
								FailureHandling.STOP_ON_FAILURE)

								WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/btn_Save_Changes'), FailureHandling.STOP_ON_FAILURE)

								println(WebUI.getText(findTestObject('Apps/SmartPBX/PhoneNumbers/phoneNumber_added_message'),
										FailureHandling.OPTIONAL))

								if (WebUI.waitForElementPresent(findTestObject('Apps/SmartPBX/PhoneNumbers/charges_confirmation'),
								5, FailureHandling.OPTIONAL)) {
									WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/charges_confirmation_circle'),
											FailureHandling.STOP_ON_FAILURE)

									WebUI.waitForElementVisible(findTestObject('Apps/App_Accounts/button_OK'), 5, FailureHandling.STOP_ON_FAILURE)

									WebUI.enhancedClick(findTestObject('Apps/App_Accounts/button_OK'), FailureHandling.STOP_ON_FAILURE)
								}

								WebUI.waitForElementVisible(findTestObject('Object Repository/Apps/SmartPBX/PhoneNumbers/i_symbol_local',['phoneNumber':phoneNumber]), 10,
								FailureHandling.OPTIONAL)

								WebUI.delay(5)
							}
						} else {
							println('for Phone Number already set a Local Carrier Module')

							WebUI.delay(5)
						}

						break
					case 'bandwidth2':

						WebUI.enhancedClick(findTestObject('Object Repository/Apps/SmartPBX/PhoneNumbers/div_setting_phone_number',['phoneNumber':phoneNumber]))

						if (WebUI.waitForElementPresent(findTestObject('Object Repository/Apps/SmartPBX/PhoneNumbers/link_carrier_module',['phoneNumber':phoneNumber]),
						10, FailureHandling.STOP_ON_FAILURE)) {
							WebUI.enhancedClick(findTestObject('Object Repository/Apps/SmartPBX/PhoneNumbers/link_carrier_module',['phoneNumber':phoneNumber]), FailureHandling.STOP_ON_FAILURE)

							WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/rename_carrier_text'),
									10, FailureHandling.STOP_ON_FAILURE)

							WebUI.verifyEqual(WebUI.getText(findTestObject('Apps/SmartPBX/PhoneNumbers/rename_carrier_text'),
									FailureHandling.STOP_ON_FAILURE), 'Rename Carrier Module', FailureHandling.STOP_ON_FAILURE)

							WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/label_carrier_module'),
									10, FailureHandling.STOP_ON_FAILURE)

							WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/select_carrier_module'),
									FailureHandling.STOP_ON_FAILURE)

							WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/option_carrier', [('carrier') : carrierModule]),
							FailureHandling.STOP_ON_FAILURE)

							WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/btn_Save_Changes'), FailureHandling.STOP_ON_FAILURE)

							println(WebUI.getText(findTestObject('Apps/SmartPBX/PhoneNumbers/phoneNumber_added_message'),
									FailureHandling.OPTIONAL))

							if (WebUI.waitForElementPresent(findTestObject('Apps/SmartPBX/PhoneNumbers/charges_confirmation'),
							5, FailureHandling.OPTIONAL)) {
								WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/PhoneNumbers/charges_confirmation_circle'),
										FailureHandling.STOP_ON_FAILURE)

								WebUI.waitForElementVisible(findTestObject('Apps/App_Accounts/button_OK'), 5, FailureHandling.STOP_ON_FAILURE)

								WebUI.enhancedClick(findTestObject('Apps/App_Accounts/button_OK'), FailureHandling.STOP_ON_FAILURE)
							}

							WebUI.waitForElementNotVisible(findTestObject('Object Repository/Apps/SmartPBX/PhoneNumbers/i_symbol_local',['phoneNumber':phoneNumber]), 10,
							FailureHandling.OPTIONAL)

							WebUI.delay(5)
						}


						break
					default:
						println('Provide a valid carrier module to set to phone number')}

			}
		}
	}

}