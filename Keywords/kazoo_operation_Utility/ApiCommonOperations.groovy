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
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.*
import org.apache.poi.ss.util.*
import java.io.File
/*import org.apache.poi.ss.usermodel.Cell
 import org.apache.poi.ss.usermodel.Font
 import org.apache.poi.ss.usermodel.IndexedColors
 import org.apache.poi.ss.usermodel.Row
 import org.apache.poi.ss.usermodel.Sheet
 import org.apache.poi.ss.usermodel.Workbook
 import org.apache.poi.xssf.usermodel.XSSFSheet
 import org.apache.poi.xssf.usermodel.XSSFWorkbook*/
import javax.media.rtp.GlobalReceptionStats as GlobalReceptionStats
import phone_number_utility.Phone_number_API


public class ApiCommonOperations {
	//fetch auth token
	@Keyword
	def auth_token(String credentials, String account_name){
		def request = ((findTestObject('API/user_auth/user_auth.create', [('base_url') : GlobalVariable.base_url, ('credentials') : credentials
			, ('account_name') : account_name]))as RequestObject)

		String body = """{"data":{"credentials": "${credentials}","account_name": "${account_name}"}}"""

		request.setBodyContent(new HttpTextBodyContent(body))
		def response = WS.sendRequest(request)
		WS.verifyResponseStatusCode(response, 201)
		def value = 'parseResponseData'(response).auth_token
		return value
	}

	/*
	 * Parsing API response data
	 */
	@Keyword
	def parseResponseData(response){
		def slurper = new JsonSlurper()
		def parsedJson = slurper.parseText(response.getResponseBodyContent())
	}
	/*
	 * Creating new account
	 * No phone numbers added
	 * Provide unique account name in Profile having global variable "newAccountNamePut"
	 * Runtime storing account_id  in Global variable "account_id"
	 */
	@Keyword
	def newAccountCreation(String newAccountNamePut,String auth_token){
		def request = ((findTestObject('API/accounts/account.create', [('base_url') : GlobalVariable.base_url, ('auth_token') : auth_token])) as RequestObject)
		//'Make request to call new account creation api'
		String body = """{"data":{"name":"${newAccountNamePut}"},"ui_help": {"voip":{"showUsersWalkthrough": false}}}"""
		request.setBodyContent(new HttpTextBodyContent(body))
		def accountResponse=WS.sendRequest(request)
		GlobalVariable.account_id = parseResponseData(accountResponse).data.id
		GlobalVariable.sub_account_details.put("account_id",(parseResponseData(accountResponse)).data.id)
		GlobalVariable.sub_account_details.put("account_name",(parseResponseData(accountResponse)).data.name)
		GlobalVariable.sub_account_details.put("account_realm",(parseResponseData(accountResponse)).data.realm)
	}
	/*
	 * Writing data to excel sheet
	 */
	@Keyword
	def writeToExcel(String excelSheetPath, List<String> numList,List<String> stateList){
		String headerPhoneNumber='Phone_Number';
		String headerState='State';
		Workbook workbook = new XSSFWorkbook()
		Sheet sheet = workbook.createSheet("PhoneNumbersList")
		Row headerRow = sheet.createRow(0);
		Cell cell = headerRow.createCell(0);
		cell.setCellValue(headerPhoneNumber);
		cell = headerRow.createCell(1);
		cell.setCellValue(headerState);
		int rowNum = 1
		for(int k=0; k<numList.size(); k++){
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(numList.get(k));
			row.createCell(1).setCellValue(stateList.get(k));
		}
		FileOutputStream fileOut = new FileOutputStream(excelSheetPath);
		workbook.write(fileOut);
		fileOut.close();
		// Closing the workbook
		workbook.close();
	}

	/*
	 * Reading data from excel sheet
	 */
	@Keyword
	def readFromExcel(String excelSheetPath){
		File file = new File(excelSheetPath);
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheetAt(0);
		Map<String, String> phoneNumberStateMap = new HashMap<String, String>();
		for (int i=1;i<=sheet.getLastRowNum();i++){
			Row row=sheet.getRow(i)
			Cell phoneNumberCell = row.getCell(0);
			Cell stateCell = row.getCell(1);
			phoneNumberStateMap.put(phoneNumberCell.getStringCellValue(), stateCell.getStringCellValue())
		}
		return phoneNumberStateMap
		file.close();
	}

	/*
	 * Available phone number API#{{accounts_api}}/phone_numbers?prefix={{country_code}}&quantity=2
	 * Retrieve available number
	 * activate numbers and add it on account level API:{{accounts_api}}/phone_numbers/{{number_id}}/activate
	 * Store all activated numbers in excel
	 */
	@Keyword
	def storeActivatedPhoneNumberToExcel(String account_id, String auth_token, String credentials){
		def response= (new phone_number_utility.Phone_number_API()).getAvailablePhoneNumberInPool(account_id, credentials, auth_token, GlobalVariable.country_code, GlobalVariable.phone_number_quantity)
		def dataList=(parseResponseData(response)).data
		def availableNumList = []
		def stateList=[]
		for (def i : dataList) {
			GlobalVariable.number_id = i.number
			def state=i.state
			//Calling activation API
			(new phone_number_utility.Phone_number_API()).putActivateSinglePhoneNumber(account_id, credentials, auth_token, GlobalVariable.number_id)
			availableNumList.add(GlobalVariable.number_id)
			stateList.add(state)}
		/*
		 * Path of the excel file "C:\\KazooProject\\kazoo-qa"
		 */
		writeToExcel(System.getProperty("user.dir")+'\\Hard Data Files\\PhoneNumberTestData.xlsx',availableNumList,stateList)
	}
	/*
	 * Getting a list of numbers from specified country_code
	 * activating those numbers
	 * Storing in the Global Variable
	 */
	@Keyword
	def listOfActivatedNumbers(String account_id,String credentials,String auth_token){
		def availablePhoneResponse= (new phone_number_utility.Phone_number_API()).getAvailablePhoneNumberInPool(account_id, credentials,auth_token,GlobalVariable.country_code ,GlobalVariable.phone_number_quantity)
		def listOfNumbers=parseResponseData(availablePhoneResponse).data
		for( def index : listOfNumbers) {
			def phoneNum=index.number
			(new phone_number_utility.Phone_number_API()).putActivateSinglePhoneNumber(account_id,credentials,auth_token,phoneNum)
			GlobalVariable.numberList.add("\""+phoneNum+"\"")}
	}

	/*
	 * Get List Of Numbers From Account
	 */
	@Keyword
	def listOfNumberfromAccount(String account_id,String credentials,String auth_token){
		def response = (new phone_number_utility.Phone_number_API()).getPhoneNumberListAPI(account_id, credentials, auth_token)
		def listOfNumbers = parseResponseData(response).data.numbers.keySet()
		def numberList = []
		for(def i : listOfNumbers){
			numberList.add("\""+i+"\"")}
		return numberList
	}

	/*
	 * Get a list of numbers from pool with country code and phone number quantity
	 * store a list of numbers into a profile in numbers and numberList
	 */
	@Keyword
	def listOfNumbersFromPool(String account_id,String credentials,String auth_token){
		def listOfNumbersResponse = (new phone_number_utility.Phone_number_API()).getAvailablePhoneNumberInPool(account_id,credentials, auth_token ,GlobalVariable.country_code, GlobalVariable.phone_number_quantity)
		def listOfNumbers = parseResponseData(listOfNumbersResponse).data
		for( def index : listOfNumbers){
			def phoneNum=index.number
			GlobalVariable.numbers.add(phoneNum)
			(new phone_number_utility.Phone_number_API()).putActivateSinglePhoneNumber(account_id,credentials,auth_token,phoneNum)
			GlobalVariable.number_list_activated.add("\""+phoneNum+"\"")}
	}

	/*
	 * Creating new child account
	 * No phone numbers added
	 * Provide unique account name in Profile having global variable "child account"
	 * Runtime storing account_id value in Global variable "child account "
	 */
	@Keyword
	def newChildAccountCreation(String newAccountNamePut,String auth_token){
		def request = ((findTestObject('API/accounts/account.create', [('base_url') : GlobalVariable.base_url, ('auth_token') : auth_token])) as RequestObject)
		//'Make request to call new account creation api'
		String body = """{"data":{"name":"${newAccountNamePut}"},"ui_help": {"voip":{"showUsersWalkthrough": false}}}"""
		request.setBodyContent(new HttpTextBodyContent(body))
		def accountResponse=WS.sendRequest(request)
		GlobalVariable.child_account.put("account_id",(parseResponseData(accountResponse)).data.id)
		GlobalVariable.child_account.put("account_name",(parseResponseData(accountResponse)).data.name)
		GlobalVariable.child_account.put("account_realm",(parseResponseData(accountResponse)).data.realm)
	}

	/*
	 * Delete an account by providing account id
	 */
	@Keyword
	def deleteAccount(String account_id){
		def response = WS.sendRequest(findTestObject('API/accounts/account.delete', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
			, ('auth_token') : GlobalVariable.auth_token]))}

	/*
	 * Creating new account
	 * No phone numbers added
	 * Provide unique account name in Profile having global variable "new_account_name_list"
	 * Return the account creation response for further use
	 */
	@Keyword
	def newAccountCreation_gateway_call(String newAccountName){
		def request = ((findTestObject('API/accounts/account.create', [('base_url') : GlobalVariable.base_url, ('auth_token') : GlobalVariable.auth_token])) as RequestObject)
		//'Make request to call new account creation api'
		String body = """{"data":{"name":"${newAccountName}"},"ui_help": {"voip":{"showUsersWalkthrough": false}}}"""
		request.setBodyContent(new HttpTextBodyContent(body))
		def accountResponse=WS.sendRequest(request)
		return accountResponse
	}

	/*
	 * Get a list of numbers from pool with country code and phone number quantity
	 * store a list of numbers into a profile in numbers and numberList
	 */
	@Keyword
	def listOfNumbersFromPoolOnSystem(String account_id,String credentials,String auth_token){
		def listOfNumbersResponse = (new phone_number_utility.Phone_number_API()).getAvailablePhoneNumberInPool(account_id,credentials, auth_token ,GlobalVariable.country_code, GlobalVariable.phone_number_quantity)
		def listOfNumbers = parseResponseData(listOfNumbersResponse).data
		for( def index : listOfNumbers){
			def phoneNum=index.number
			GlobalVariable.numbers.add(phoneNum)
			GlobalVariable.number_list_discovery.add("\""+phoneNum+"\"")}
	}

	/*
	 * Getting a numbers from specified country_code
	 * check if number is available for required country code
	 * set country code value
	 */
	@Keyword
	def set_country_code(String account_id,String credentials,String auth_token){
		for(String code :GlobalVariable.country_code_list){
			def availablePhoneResponse= WS.sendRequest(findTestObject('API/phone_numbers/phone_number.available', [('auth_token') : auth_token
				, ('base_url') : GlobalVariable.base_url, ('account_id') : account_id, ('country_code') : code, ('credentials') : credentials]))
			def listOfNumbers=parseResponseData(availablePhoneResponse).data
			if(!listOfNumbers.isEmpty()){
				GlobalVariable.country_code = code
				println(GlobalVariable.country_code)
				break
			}
		}

	}

	/*
	 * Check if account name exist
	 * Delete the account if account name exist
	 */
	@Keyword
	def check_accountname_exist_and_delete_account(String account_id,String accountName,String credentials,String auth_token)
	{
		def parentAccResponse = WS.sendRequest(findTestObject('API/accounts/account.children.list', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
			, ('auth_token') : GlobalVariable.auth_token]))
		def account_details = parseResponseData(parentAccResponse).data
		for(def leg : account_details)
		{
			if(leg.name.equals(accountName))
			{
				println("Account name exist")
				def childAccResponse = WS.sendRequest(findTestObject('API/accounts/account.children.list', [('base_url') : GlobalVariable.base_url, ('account_id') : leg.id
					, ('auth_token') : GlobalVariable.auth_token]))
				def childAccDetails = parseResponseData(childAccResponse).data
				if(!childAccDetails.isEmpty()){
					for(def childLeg: childAccDetails){
						println("Child account is present")
						def res = deleteAccount(childLeg.id)
						WS.verifyResponseStatusCode(res, 200)
						println("Child account is deleted")
					}
				}
				def res = deleteAccount(leg.id)
				WS.verifyResponseStatusCode(res, 200)
				println("Account is deleted")
				break

			}
		}

	}

	/*
	 * check account exist by providing account name as parameter 
	 * 
	 */
	@Keyword
	def check_accountname_exist(String accountName){
		def accountCount = 0
		def parentAccResponse = WS.sendRequest(findTestObject('API/accounts/children.filter_account_name', [('base_url') : GlobalVariable.base_url, ('account_id') : GlobalVariable.parent_account_id
			, ('auth_token') : GlobalVariable.auth_token, ('account_name') : accountName]))
		def account_details = parseResponseData(parentAccResponse).data
		for(def leg : account_details){
			if(leg.name.equals(accountName)){
				println("Account name exist")
				accountCount++
			}
		}
		return accountCount
	}

	/*
	 * check if account name exist then return account id 
	 */
	@Keyword
	def checkAccountName_ReturnAccountId(String accountName){
		def parentAccResponse = WS.sendRequest(findTestObject('API/accounts/children.filter_account_name', [('base_url') : GlobalVariable.base_url, ('account_id') : GlobalVariable.parent_account_id
			, ('auth_token') : GlobalVariable.auth_token, ('account_name') : accountName]))
		def account_details = parseResponseData(parentAccResponse).data
		for(def leg : account_details){
			if(leg.name.equals(accountName)){
				return leg.id
			}
		}
	}

	/*
	 * check if account name exist then return account details
	 */
	@Keyword
	def getAccountRealm(String accountName){
		def parentAccResponse = WS.sendRequest(findTestObject('API/accounts/children.filter_account_name', [('base_url') : GlobalVariable.base_url, ('account_id') : GlobalVariable.parent_account_id
			, ('auth_token') : GlobalVariable.auth_token, ('account_name') : accountName]))
		def account_details = parseResponseData(parentAccResponse).data
		for(def leg : account_details){
			if(leg.name.equals(accountName)){
				return leg.realm
			}
		}
	}

	/*
	 * Check account have users present in it or not
	 * if exists then return page size.
	 */
	@Keyword
	def checkAccountHaveUsers(String account_id){
		def response = WS.sendRequest(findTestObject('API/users/users.list',[('base_url') : GlobalVariable.base_url, ('account_id') : account_id,
			('auth_token') : GlobalVariable.auth_token, ('credentials') : GlobalVariable.credentials]))
		def userCountResponse = parseResponseData(response).page_size

		if(userCountResponse != null || userCountResponse.isEmpty()){
			return userCountResponse
		}else{
			return 0
		}
	}

	/*
	 * Get the device list
	 * check if username exist then return deviceId
	 */
	@Keyword
	def getDeviceId(String accountId,String userName){
		def parentAccResponse = WS.sendRequest(findTestObject('API/devices/devices.list', [('base_url') : GlobalVariable.base_url, ('account_id') : accountId
			, ('auth_token') : GlobalVariable.auth_token]))
		def deviceDetail = parseResponseData(parentAccResponse).data
		for(def leg : deviceDetail){
			if(leg.username.equals(userName)){
				return leg.id
			}
		}
	}


	/*
	 * Get the device list of account
	 * check if name exist then return deviceId
	 */
	@Keyword
	def getDeviceIdByDeviceName(String accountId,String deviceName){
		def parentAccResponse = WS.sendRequest(findTestObject('API/devices/devices.list', [('base_url') : GlobalVariable.base_url, ('account_id') : accountId
			, ('auth_token') : GlobalVariable.auth_token]))
		def deviceDetail = parseResponseData(parentAccResponse).data
		for(def leg : deviceDetail){
			if(leg.name.equals(deviceName)){
				return leg.id
			}
		}
	}

	/*
	 * Fetch list of devices from an account
	 * Passing a account Name as Parameter, checkAccountName and Return AccountId 
	 * Using account Id to fetch list of devices from account
	 */
	@Keyword
	def getNumberOfDevicesOwnedByAccount(String acountName) {
		String accountId = checkAccountName_ReturnAccountId(acountName)
		def parentAccResponse = WS.sendRequest(findTestObject('API/devices/devices.list', [('base_url') : GlobalVariable.base_url, ('account_id') : accountId
			, ('auth_token') : GlobalVariable.auth_token]))
		def deviceCountResponse = parseResponseData(parentAccResponse).page_size
		if(deviceCountResponse != null || deviceCountResponse.isEmpty()){
			return deviceCountResponse
		}else{
			return 0
		}
	}

}/*End of ApiCommonOperations class*/
