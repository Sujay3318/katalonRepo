package webphone_Utility

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable
import java.security.MessageDigest


public class webphoneOperation {
	/*
	 * Convert the string credential to MD5SUM
	 * pass parameter as user credentials (UserName:Password)
	 */
	@Keyword
	def generateMD5Credential(String credential) {
		credential = MessageDigest.getInstance("MD5").digest(credential.bytes).encodeHex().toString()
		return credential
	}

	/*
	 * Convert json to Base64
	 */
	@Keyword
	def convertJsonToBase64(String deviceId,String accountId,String accountName,String accountRealm,String md5Credential) {
		def apiUrl = GlobalVariable.base_url
		def webSocketUrl = GlobalVariable.websocketUrl
		def webphoneWebSocketUrl = GlobalVariable.webphoneWebsocketUrl
		def jsonBody = """{
		"deviceId": "${deviceId}",
		"authRealm": "${accountRealm}",
		"account": {
		"accountId": "${accountId}",
		"name": "${accountName}"
		},
		  "credentials": {
		"credentials": "${md5Credential}",
		"account_name": "${accountName}"
		},
		"apiUrl": "${apiUrl}",
		"websocketUrl": "${webSocketUrl}",
		"webphoneWebsocketUrl": "${webphoneWebSocketUrl}"
		}"""

		println(jsonBody)
		def encoded = jsonBody.bytes.encodeBase64().toString()

		return encoded
	}
}
