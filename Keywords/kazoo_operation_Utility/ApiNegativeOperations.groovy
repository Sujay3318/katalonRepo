package kazoo_operation_Utility
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.util.List
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.main.CustomKeywordDelegatingMetaClass
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.media.rtp.GlobalReceptionStats as GlobalReceptionStats

import phone_number_utility.Phone_number_API
import kazoo_operation_Utility.ApiCommonOperations

class ApiNegativeOperations {
	/**
	 * Delete_Account_With_Incorrect_Token
	 */
	@Keyword
	def deleteAccountWithInvalidToken(String account_id){
		def response = WS.sendRequest(findTestObject('API/accounts/account.delete', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
			, ('auth_token') : 'sssss']))
	}

	/*
	 * Negative Test Case Keyword: Creating new account with Invalid Token. 
	 * No phone numbers added
	 * Provide unique account name in Profile having global variable "newAccountNamePut"
	 * Runtime storing account_id  in Global variable "account_id"
	 */
	@Keyword
	def newAccountCreationWithInvalidToken(String newAccountNamePut){
		def request = ((findTestObject('API/accounts/account.create', [('base_url') : GlobalVariable.base_url, ('auth_token') : 'tsssssss'])) as RequestObject)
		//'Make request to call new account creation api'
		String body = """{"data":{"name":"${newAccountNamePut}"},"ui_help": {"voip":{"showUsersWalkthrough": false}}}"""
		request.setBodyContent(new HttpTextBodyContent(body))
		WS.sendRequest(request)
	}

	/*
	 * Creating new user with Invalid Token
	 * Provide unique username
	 * Runtime storing username,password,firstname and user_id in GlobalVariable
	 */
	@Keyword
	def newUserCreationWithInvalidToken(String uname,String email,String fname,String lname,String pswd,String account_id ){
		def request = ((findTestObject('API/users/user.put', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
			, ('auth_token') : 'ssssss'])) as RequestObject)
		String body = """{"data": {"username": "${uname}","email": "${email}","first_name": "${fname}","last_name": "${lname}","password": "${pswd}","priv_level": "user","flags": [],"vm_to_email_enabled": true,"send_email_on_creation": true,"ui_help":{ "voip": {"showUsersWalkthrough": false,"showStrategySecondWalkthrough": false,"showGroupsWalkthrough": false,"showDashboardWalkthrough": false,"showStrategyFirstWalkthrough": false},"myaccount": {"showfirstUseWalkthrough": false}}}}"""
		println(body)
		request.setBodyContent(new HttpTextBodyContent(body))
		WS.sendRequest(request)
	}

	/*
	 * Create new device with invalid Token 
	 * Mapping device to user using user_id
	 */
	@Keyword
	def newDeviceCreationWithInvalidToken(String account_id) {
		def request = ((findTestObject('API/devices/device.put', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
			, ('auth_token') : 'sssssss'])) as RequestObject)
		String body ="""{"data": {"sip": {"password" : "test1234","username": "sip_username","expire_seconds" : 300,"invite_format": "contact","method": "password"}, "device_type": "softphone","enabled": true,"suppress_unregister_notifications": false,"name": "soft_phone","contact_list": {},"ringtones": {},"owner_id": "6"}}"""
		println(body)
		request.setBodyContent(new HttpTextBodyContent(body))
		WS.sendRequest(request)
	}

	/*
	 * patch public fields of a device
	 * provide device_id as parameter
	 */
	@Keyword
	def patchDeviceFields(String account_id,String credentials,String auth_token,String device_id){
		WS.sendRequest(findTestObject("API/devices/device.patch", [('base_url') : GlobalVariable.base_url, ('account_id') : account_id, ('auth_token') :
			auth_token, ('device_id') : device_id, ('credentials') : credentials ]))
	}

	/*
	 * patch information of user
	 * by providing user_id as a paramter
	 */
	@Keyword
	def patchUserPublicFields(String account_id,String credentials,String auth_token,String user_id){
		def request = ((findTestObject('API/users/user.patch', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id,
			('credentials') : credentials, ('auth_token') : auth_token, ('user_id') : user_id])) as RequestObject)

		String body = """{"data":{"enabled":false} }"""
		request.setBodyContent(new HttpTextBodyContent(body))
		WS.sendRequest(request)
	}

	/*
	 * Delete a device from an account
	 * By providing device_id as a parameter
	 */
	@Keyword
	def deleteDevice(String account_id,String credentials,String auth_token,String device_id){
		WS.sendRequest(findTestObject('API/devices/device.delete', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id,
			('auth_token') : auth_token, ('device_id') : device_id, ('credentials') : credentials]))
	}

	/*
	 * Delete a user from account
	 * By providing user_id as a parameter
	 */
	@Keyword
	def deleteUser(String account_id,String credentials,String auth_token,String user_id){
		WS.sendRequest(findTestObject('API/users/user.delete', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id,
			('auth_token') : auth_token, ('user_id') : user_id, ('credentials') : credentials]))
	}
	/*
	 * Creating new user
	 * Provide unique username
	 * Runtime storing username,password,firstname and user_id in GlobalVariable
	 */
	@Keyword
	def new_user_creation(String uname,String email,String fname,String lname,String pswd,String account_id,String auth_token,String credentials){
		def request = ((findTestObject('API/users/user.put', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
			, ('auth_token') : auth_token, ('credentials') : credentials])) as RequestObject)
		String body = """{"data": {"username": "${uname}","email": "${email}","first_name": "${fname}","last_name": "${lname}","password": "${pswd}","priv_level": "user","flags": [],"vm_to_email_enabled": true,"send_email_on_creation": true,"ui_help":{ "voip": {"showUsersWalkthrough": false,"showStrategySecondWalkthrough": false,"showGroupsWalkthrough": false,"showDashboardWalkthrough": false,"showStrategyFirstWalkthrough": false},"myaccount": {"showfirstUseWalkthrough": false}}}}"""
		println(body)
		request.setBodyContent(new HttpTextBodyContent(body))
		WS.sendRequest(request)
	}

	/*
	 * Create new device
	 * Mapping device to user using user_id
	 */
	@Keyword
	def new_device_creation(String devicename,String device_type,String sip_username,String sip_password,String user_id, String account_id,String credentials,String auth_token) {
		def request = ((findTestObject('API/devices/device.put', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id, ('credentials') : credentials
			, ('auth_token') : auth_token])) as RequestObject)
		String body ="""{"data": {"sip": {"password" : "${sip_password}","username": "${sip_username}","expire_seconds" : 300,"invite_format": "contact","method": "password"}, "device_type": "${device_type}","enabled": true,"suppress_unregister_notifications": false,"name": "${devicename}","contact_list": {},"ringtones": {},"owner_id": "${user_id}"}}"""
		println(body)
		request.setBodyContent(new HttpTextBodyContent(body))
		WS.sendRequest(request)
	}

	/*
	 * Delete an account by providing account id,auth_token
	 */
	@Keyword
	def deleteAccount(String account_id,String credentials,String auth_token){
		def response = WS.sendRequest(findTestObject('API/accounts/account.delete', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
			, ('auth_token') : auth_token, ('credentials') : credentials]))
	}
}