package setup
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import java.util.Scanner
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import internal.GlobalVariable
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.junit.After
import org.openqa.selenium.By
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.webui.exception.WebElementNotFoundException
import excelOperations.WriteExcel
import com.kms.katalon.core.testdata.reader.ExcelFactory as Excel
import kazoo_operation_Utility.ApiCommonOperations
import java.lang.Thread
import static org.junit.Assert.*
import java.lang.Integer

public class CountAccountsUsers {

	/*
	 * providing Scanner class object as parameter
	 * Setup number of  Accounts and Users for Gateway call
	 * 
	 */
	@Keyword
	def readAccountsAndUsersCountFromInteractiveConsole(Number noOfAccounts, List<Number> noOfUsers, def accountReferenceObject){

		//Required number of users count
		List<Number> numberOfUsers = new ArrayList<Number>()

		//Existing number of users count
		List<Number> existingNumberOfUsers = new ArrayList<Number>()

		//Final required number of users count
		List<Number> requiredExistingNumberOfUsers = new ArrayList<Number>()

		//Existing number of accounts count
		def existingAccountsCount = 0

		//check and count the number of accounts exist
		for(def row = 1 ; row <= noOfAccounts; row++){
			println("Account Name : " + accountReferenceObject.getValue('AccountName',row))

			existingAccountsCount += (new kazoo_operation_Utility.ApiCommonOperations()).check_accountname_exist(accountReferenceObject.getValue('AccountName', row))
		}
		println("existing Accounts in the system are: " + existingAccountsCount)

		GlobalVariable.existing_Accounts = existingAccountsCount

		//Remove existing account name count from input count
		def numberOfAccounts = noOfAccounts - existingAccountsCount

		//sum of existing and newly creating accounts
		def totalAccountCount = (numberOfAccounts + existingAccountsCount)


		//Writing Existing Accounts and User Counts to data file
		if(existingAccountsCount > 0){
			for(def row = 0; row < existingAccountsCount; row++){
				def account_id = (new kazoo_operation_Utility.ApiCommonOperations()).checkAccountName_ReturnAccountId(accountReferenceObject.getValue('AccountName', (row+1)))
				assertNotNull('Account id is null', account_id)
				def userResponseCount = (new kazoo_operation_Utility.ApiCommonOperations()).checkAccountHaveUsers(account_id)
				if(userResponseCount <= 0 || userResponseCount.equals(null)){
					existingNumberOfUsers.add(row,0)
				}else{
					existingNumberOfUsers.add(row,userResponseCount)
				}
			}

			if(existingNumberOfUsers.size() < totalAccountCount){
				def sizeOfArrayList = existingNumberOfUsers.size()
				for(def element = sizeOfArrayList; element < totalAccountCount; element++){
					existingNumberOfUsers.add(element ,0)
				}
			}
			(new excelOperations.WriteExcel()).ExistingAccountsData(existingAccountsCount, existingNumberOfUsers)
		}else{
			List<Number> duplicateNumberOfUsers = new ArrayList<Number>()
			for(def i = 0;i < numberOfAccounts;i++){
				duplicateNumberOfUsers.add(i, 0)
			}
			(new excelOperations.WriteExcel()).ExistingAccountsData(0, duplicateNumberOfUsers)
		}


		//Taking User count for each account present
		if(totalAccountCount > 0){
			for(def row = 0; row < totalAccountCount; row++){
				println("Enter Number of Users for account" + (row+1) + ":")
				def userCount = (new setup.ConsoleInput()).setUserCount(noOfUsers[row], 10)
				numberOfUsers.add(row,userCount)
			}

			//Appending 0 for few elements into a list
			if(numberOfUsers.size() < existingNumberOfUsers.size()){
				for(int sizeOfArray = numberOfUsers.size(); sizeOfArray < existingNumberOfUsers.size(); sizeOfArray++){
					numberOfUsers.add(sizeOfArray,0)
				}
			}else{
				for(int sizeOfArray = existingNumberOfUsers.size(); sizeOfArray < numberOfUsers.size(); sizeOfArray++){
					existingNumberOfUsers.add(sizeOfArray,0)
				}
			}

			//Appending 0 for few elements into a list
			for(def element : totalAccountCount ){
				if(element < 0){
					existingNumberOfUsers[element] = 0
				}
			}
			println("List of Number of required users :" + numberOfUsers)

			println("List of Number of existing users in existing accounts :" + existingNumberOfUsers)

			//Removing the existing users count  from console Input
			for(def i = 0; i < totalAccountCount; i++){
				def newUser = (numberOfUsers.get(i) - existingNumberOfUsers.get(i))
				if(newUser >= 0){
					requiredExistingNumberOfUsers.add(i,newUser)
				}else{
					requiredExistingNumberOfUsers.add(i,0)
				}
			}
		}
		println("List of Number of new required users :" + requiredExistingNumberOfUsers)
		//Writing data to the Generic test data file
		(new excelOperations.WriteExcel()).writeToExcel(numberOfAccounts,requiredExistingNumberOfUsers)
	}
}