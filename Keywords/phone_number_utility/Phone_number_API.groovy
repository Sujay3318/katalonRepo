package phone_number_utility

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint as findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase as findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData as findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject as findTestObject
import com.kms.katalon.core.testobject.TestObjectProperty
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject as findWindowsObject
import java.util.ArrayList
import java.util.List
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import groovy.json.JsonSlurper as JsonSlurper
import internal.GlobalVariable
import com.kms.katalon.core.testobject.RequestObject as RequestObject
import com.kms.katalon.core.testobject.ResponseObject as ResponseObject
import com.kms.katalon.core.testobject.impl.HttpTextBodyContent as HttpTextBodyContent
import javax.media.rtp.GlobalReceptionStats
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Phone_number_API{
	/*
	 * Getting a list of numbers from an account
	 */
	@Keyword
	def getPhoneNumberListAPI(String account_id,String credentials,String auth_token){
		WS.sendRequest(findTestObject('API/phone_numbers/phone_numbers.list', [('base_url') : GlobalVariable.base_url
			, ('credentials') : credentials, ('auth_token') : auth_token, ('account_id') : account_id]))
	}

	/*
	 * Get List Of Available PhoneNumbers In Pool
	 */
	@Keyword
	def getAvailablePhoneNumberInPool(String account_id,String credentials,String auth_token,String country_code,String phone_number_quantity){
		WS.sendRequest(findTestObject('API/phone_numbers/phone_number.available', [('auth_token') : auth_token, ('base_url') : GlobalVariable.base_url, ('account_id') : account_id,
			('phone_number_quantity') : phone_number_quantity, ('country_code') : country_code, ('credentials') : credentials]))
	}

	/*
	 * put Activate Single Phone Number
	 */
	@Keyword
	def putActivateSinglePhoneNumber(String account_id,String credentials,String auth_token,String number_id){
		WS.sendRequest(findTestObject('API/phone_numbers/phone.number.single.activate', [('base_url') : GlobalVariable.base_url
			, ('account_id') : account_id, ('auth_token') : auth_token, ('number_id') : number_id, ('credentials') : credentials]))
	}

	/*
	 * Get information about the single number provided from an account
	 */
	@Keyword
	def getSinglePhoneNumber(String account_id,String credentials,String auth_token,String number_id){
		WS.sendRequest(findTestObject('API/phone_numbers/phone_numbers.get', [('base_url') : GlobalVariable.base_url, ('account_id') :account_id, ('number_id') :number_id,
			('credentials') : credentials, ('auth_token') : auth_token]))
	}

	/*
	 * Get first available number present in pool as per country_code
	 */
	@Keyword
	def getFirstAvailablePhoneNumber(String account_id, String credentials,String auth_token,String country_code){
		WS.sendRequest(findTestObject('API/phone_numbers/phone_number.available', [('auth_token') : auth_token
			, ('base_url') : GlobalVariable.base_url, ('account_id') : account_id, ('country_code') : country_code, ('credentials') : credentials]))
	}

	/*
	 * Get Phone Numbers And Account Details With No Meta-Data
	 */
	@Keyword
	def getPhoneNumberAndAccountDetailsNoMetaData(String account_id,String credentials,String auth_token,String number_id){
		WS.sendRequest(findTestObject('API/phone_numbers/phone_numbers.identify',[('base_url') : GlobalVariable.base_url,('auth_token') : auth_token,
			('account_id') : account_id ,('number_id') : number_id,('credentials') : credentials]))
	}

	/*
	 * Get Classification Details On Account Level
	 */
	@Keyword
	def getClassificationDetailsOnAccountLevel(String account_id,String credentials,String auth_token){
		WS.sendRequest(findTestObject('API/phone_numbers/phone_numbers.classifiers',[('base_url') : GlobalVariable.base_url, ('auth_token') : auth_token,
			('account_id') : account_id,('credentials') : credentials]))
	}

	/*
	 * Get Carrier Configuration Details
	 */
	@Keyword
	def getCarrierConfigurationAPI(String account_id,String credentials, String auth_token){
		WS.sendRequest(findTestObject('API/phone_numbers/phone_numbers.get.carrier_Configuration',[('base_url') : GlobalVariable.base_url,('auth_token') : auth_token,
			('account_id') : account_id, ('credentials') : credentials]))
	}

	/*
	 * Post Check Availability Of PhoneNumbers for buying
	 */
	@Keyword
	def postCheckAvailabilityOfPhoneNumber(String account_id,String credentials,String auth_token, ArrayList numbers){
		def request=((findTestObject('API/phone_numbers/phone_numbers.check',[('base_url') : GlobalVariable.base_url,('account_id') : account_id,
			('auth_token') : auth_token, ('credentials') : credentials])) as RequestObject)

		String body = """{"data":{"numbers":$numbers}}"""
		request.setBodyContent(new HttpTextBodyContent(body))
		def response = WS.sendRequest(request)
	}

	/*
	 * Delete available phone Number in an account
	 */
	@Keyword
	def deleteOneNumberFromOwnedAccount(String account_id,String number_id,String credentials,String auth_token){
		WS.sendRequest(findTestObject('API/phone_numbers/phone_number.delete_owned_number',[('base_url') : GlobalVariable.base_url,('account_id') : account_id,
			('number_id') : number_id, ('credentials') : credentials,  ('auth_token') : auth_token]))
	}

	/*
	 * Delete a phone Number in account by admin only
	 */
	@Keyword
	def deleteOneNumberByAdmin(String account_id,String number_id,String credentials,String auth_token){
		WS.sendRequest(findTestObject('API/phone_numbers/phone_number.delete_admin',[('base_url') : GlobalVariable.base_url,('account_id') : account_id,('number_id') : number_id,
			('credentials') : credentials,('auth_token') : auth_token]))
	}

	/*
	 * Delete a List Of Phone Numbers from Database
	 */
	@Keyword
	def deleteListOfNumbersFromDatabase(String account_id,String credentials,String auth_token,ArrayList number){
		def request=((findTestObject('API/phone_numbers/phone_numbers.delete_from_database',[('base_url') : GlobalVariable.base_url,('account_id') : account_id,
			('auth_token') : auth_token, ('credentials') : credentials])) as RequestObject)

		String body = """{"data":{"numbers":$number}}"""
		request.setBodyContent(new HttpTextBodyContent(body))
		def response = WS.sendRequest(request)
	}

	/*
	 * Put Update a Phone Number to a Reserve State
	 */
	@Keyword
	def putUpdatePhoneNumberToReserve(String account_id,String credentials, String auth_token,String number_id){
		WS.sendRequest(findTestObject('API/phone_numbers/phone_number.reserve',[('base_url') : GlobalVariable.base_url,('account_id') : account_id,('number_id') : number_id,
			('credentials') : credentials,('auth_token') : auth_token]))
	}

	/*
	 * Put Adding a list of numbers to an account
	 */
	@Keyword
	def putAddListOfNumbersToAccount(String account_id,String credentials,String auth_token,ArrayList numbers){
		def request=((findTestObject('API/phone_numbers/phone_numbers.list.activate',[('base_url') : GlobalVariable.base_url,('account_id') : account_id,
			('auth_token') : auth_token, ('credentials') : credentials ])) as RequestObject)

		String body = """{"data":{"numbers":$numbers}}"""
		println("the request body"+body)
		request.setBodyContent(new HttpTextBodyContent(body))
		def response = WS.sendRequest(request)
	}

	/*
	 * update public fields of the account
	 */
	@Keyword
	def patchUpdatePublicFields(String account_id,String credentials,String auth_token,String number_id,String jsonBody){
		def request=((findTestObject('API/phone_numbers/phone_number.update_fields',[('base_url') : GlobalVariable.base_url,('account_id') : account_id,('number_id') : number_id,
			('auth_token') : auth_token, ('credentials') : credentials])) as RequestObject)

		String body = jsonBody

		request.setBodyContent(new HttpTextBodyContent(body))

		def response = WS.sendRequest(request)
	}

	/*
	 * Get Available Phone number On System
	 */
	@Keyword
	def getAvailablePhoneNumberOnSystem(String credentials,String auth_token,String country_code,String phone_number_quantity){
		WS.sendRequest(findTestObject("API/phone_numbers/phone_number.available_on_system",[('base_url') : GlobalVariable.base_url, ('country_code') : country_code,
			('phone_number_quantity') : phone_number_quantity,('auth_token') : auth_token, ('credentials') : credentials]))
	}

	/*
	 * Update a phone number from other state to "port-in" state
	 */
	@Keyword
	def putUpdatePhoneNumberToPortin(String account_id ,String credentials,String auth_token,String number_id){
		WS.sendRequest(findTestObject('API/phone_numbers/phone_number.port_in',[('base_url') : GlobalVariable.base_url, ('account_id') : account_id, ('number_id') : number_id,('credentials') : credentials, ('auth_token') : auth_token]))
	}

	/*
	 * Put Adding a list of numbers to an database
	 */
	@Keyword
	def putAddListOfNumbersToDatabase(String account_id,String credentials,String auth_token,ArrayList numbers){
		def request=((findTestObject('API/phone_numbers/phone_numbers.add_to_database',[('base_url') : GlobalVariable.base_url,('account_id') : account_id,
			('auth_token') : auth_token, ('credentials') : credentials ])) as RequestObject)

		String body = """{"data":{"numbers":$numbers}}"""
		println("the request body"+body)
		request.setBodyContent(new HttpTextBodyContent(body))
		def response = WS.sendRequest(request)
	}

	/*
	 * Adding e911 address to a phone number
	 */
	@Keyword
	def postAddingE911ToPhoneNumber(String account_id,String credentials,String auth_token,String number_id,String jsonBody){
		def request = ((findTestObject('API/phone_numbers/activate_e911', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id,
			('auth_token') : auth_token, ('credentials') : credentials, ('number_id') : number_id])) as RequestObject)

		String body = jsonBody
		request.setBodyContent(new HttpTextBodyContent(body))
		def response = WS.sendRequest(request)
	}

	/*
	 * Adding Additional Features to a phone number 
	 * like sms , mms ,cnam ,e911,..... in form of Key-Value Pair
	 * Provide account id,phone number,Feature related data,credentials,auth_token
	 */
	@Keyword
	def postAddingCustomFieldsToPhoneNumber(String account_id,String credentials,String auth_token,String number_id,String jsonBody){
		def request = ((findTestObject('API/phone_numbers/add_public_fields',[('base_url') : GlobalVariable.base_url, ('account_id') : account_id,
			('auth_token') : auth_token, ('credentials') : credentials, ('number_id') : number_id])) as RequestObject)
		String body = jsonBody
		request.setBodyContent(new HttpTextBodyContent(body))
		def response = WS.sendRequest(request)
	}

	/*
	 * set carrier module as local
	 * using this number for offnet call
	 */
	@Keyword
	def setResetCarrierModule(String account_id,String phone_number,String carrierName){
		def request = ((findTestObject('API/phone_numbers/phone_numbers.carrier_setup', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
			, ('auth_token') : GlobalVariable.auth_token, ('phone_number') : phone_number])) as RequestObject)
		String body = """{"data":{"carrier_module":"${carrierName}","carrier_name":"${carrierName}"}}"""
		println(body)
		request.setBodyContent(new HttpTextBodyContent(body))
		WS.sendRequest(request)
	}

	/*
	 * Get a list of numbers owned by an account
	 * 
	 */
	def getListOfNumberOwnedByAccount(String account_id,String credentials, String auth_token) {
		WS.sendRequest(findTestObject('API/phone_numbers/listOfNumbersOwnedByAccount',[('base_url') : GlobalVariable.base_url, ('account_id') : account_id,
			('credentials') : credentials, ('auth_token') : auth_token]))
	}
}

