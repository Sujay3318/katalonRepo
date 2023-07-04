package voicemail_box

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

public class voicemail_action {

	//This keyword is to Configure the voicemail feature

	@Keyword
	def configureVoicemail(int userVoicemailBox,int voicemailToEmail,int attachVoicemailMsgEmail){

		if(userVoicemailBox == 1){
			println("Need to enable the user voicemail box")
			if(WebUI.verifyElementNotChecked(findTestObject('Apps/SmartPBX/Features/Voicemail_box/user_voicemail_box'), 5, FailureHandling.OPTIONAL)){
				WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Features/Voicemail_box/user_voicemail_box'), FailureHandling.STOP_ON_FAILURE)
			}else{
				println("User voicemail box is already enabled")
			}
			WebUI.delay(10)
			WebUI.verifyTextPresent('A voicemail box will be available for this user', true, FailureHandling.STOP_ON_FAILURE)

			WebUI.verifyTextPresent("Voicemail to e-mail", true, FailureHandling.STOP_ON_FAILURE)

			if(voicemailToEmail==1){
				if(WebUI.verifyElementNotChecked(findTestObject('Object Repository/Apps/SmartPBX/Features/Voicemail_box/Voicemail_to_email'), 5, FailureHandling.OPTIONAL)){
					println "Need to enable voicemail to email feature"
					WebUI.enhancedClick(findTestObject('Object Repository/Apps/SmartPBX/Features/Voicemail_box/Voicemail_to_email'), FailureHandling.STOP_ON_FAILURE)
				}else{
					println("User voicemail to email is already enabled")
				}

				if(attachVoicemailMsgEmail==1){
					if(WebUI.verifyElementChecked(findTestObject('Object Repository/Apps/SmartPBX/Features/Voicemail_box/checkbox_attach_voicemail_to_email'), 10, FailureHandling.OPTIONAL)){
						println("Checkbox is checked")
					}
					else{
						WebUI.enhancedClick(findTestObject('Object Repository/Apps/SmartPBX/Features/Voicemail_box/checkbox_attach_voicemail_to_email'), FailureHandling.STOP_ON_FAILURE)
					}
				}
				else if(attachVoicemailMsgEmail==0){
					if(WebUI.verifyElementChecked(findTestObject('Object Repository/Apps/SmartPBX/Features/Voicemail_box/checkbox_attach_voicemail_to_email'), 10, FailureHandling.OPTIONAL)){
						WebUI.enhancedClick(findTestObject('Object Repository/Apps/SmartPBX/Features/Voicemail_box/checkbox_attach_voicemail_to_email'), FailureHandling.OPTIONAL)
						println("Checkbox is unchecked")
					}
					else{
						println("Checkbox is unchecked")
					}
				}
			}
			else if(voicemailToEmail==0){
				if(WebUI.verifyElementChecked(findTestObject('Object Repository/Apps/SmartPBX/Features/Voicemail_box/Voicemail_to_email'), 5, FailureHandling.OPTIONAL)){
					WebUI.enhancedClick(findTestObject('Object Repository/Apps/SmartPBX/Features/Voicemail_box/Voicemail_to_email'), FailureHandling.STOP_ON_FAILURE)
				}else{
					println("User voicemail to email is already disabled")
				}
			}
		}
		else if(userVoicemailBox == 0){
			if(WebUI.verifyElementChecked(findTestObject('Apps/SmartPBX/Features/Voicemail_box/user_voicemail_box'), 5, FailureHandling.OPTIONAL)){
				println("Need to disable the user voicemail box")
				WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Features/Voicemail_box/user_voicemail_box'), FailureHandling.STOP_ON_FAILURE)
			}else{
				println("User voicemail box is already disabled")
			}
		}


		WebUI.delay(5)

		WebUI.enhancedClick(findTestObject('Object Repository/Apps/SmartPBX/Features/Button_save_changes'), FailureHandling.STOP_ON_FAILURE)

		WebUI.callTestCase(findTestCase('Common_Window_Handling/SC_Charge_Confirmation_OK'), [:], FailureHandling.OPTIONAL)

	}
}
