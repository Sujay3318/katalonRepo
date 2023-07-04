package call_forward
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static org.junit.Assert.*

import org.javatuples.*

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testdata.reader.ExcelFactory as Excel
import internal.GlobalVariable as GlobalVariable
import webRTC_Utility.WEBRTC


class CallForward_Actions {

	//This keyword is to Configure the Call forwarding feature

	@Keyword
	def Configure_Call_Forward(String callForwardStatus,int voicemailEnabledOnfwdNum,int FwdDirCallEnabled, int origCallerId, String phoneNumber1 ){



		switch(callForwardStatus)

		{
			case "Off" :
				WebUI.enhancedClick(findTestObject('Object Repository/Apps/SmartPBX/Call_Forward/Button_Off_CallForward_DialogBox'))
				WebUI.delay(3)
				WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/btn_Save_Changes'),FailureHandling.STOP_ON_FAILURE)
				WebUI.delay(5)
				WebUI.doubleClick(findTestObject("Apps/SmartPBX/Call_Forward/label_UserFeatures"))

				println "Call forward is disabled, as it is set to OFF"
				break;

			case "On":

				WebUI.delay(2)

				println "In On mode"

				WebUI.enhancedClick(findTestObject('Object Repository/Apps/SmartPBX/Call_Forward/Button_ON_CallForward_DialogBox'))
				WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Call_Forward/InputBox_UserPhoneNumber_CallToBeForwardOn'), FailureHandling.STOP_ON_FAILURE)
				WebUI.clearText(findTestObject('Apps/SmartPBX/Call_Forward/InputBox_UserPhoneNumber_CallToBeForwardOn'), FailureHandling.STOP_ON_FAILURE)
				WebUI.setText(findTestObject('/Apps/SmartPBX/Call_Forward/InputBox_UserPhoneNumber_CallToBeForwardOn'),phoneNumber1, FailureHandling.STOP_ON_FAILURE)

				WebUI.delay(2)


				if(voicemailEnabledOnfwdNum==1)
				{// Verifying it's already unchecked, we are checking it
					if(WebUI.verifyElementNotChecked(findTestObject('Apps/SmartPBX/Call_Forward/Checkbox_Leave_voicemails_on_forwarded_numbers'), 2,FailureHandling.OPTIONAL))
						WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Call_Forward/Checkbox_Leave_voicemails_on_forwarded_numbers'), FailureHandling.STOP_ON_FAILURE)

					else
						println " voicemailEnabledOnfwdNum is already checked"
				}

				else if(voicemailEnabledOnfwdNum==0)
				{//Verifying it's already checked, we are un-checking it
					println "checking for VM=0"

					if(WebUI.verifyElementChecked(findTestObject('Apps/SmartPBX/Call_Forward/Checkbox_Leave_voicemails_on_forwarded_numbers'),5,FailureHandling.OPTIONAL))
					{
						WebUI.delay(2)
						WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Call_Forward/Checkbox_Leave_voicemails_on_forwarded_numbers'), FailureHandling.STOP_ON_FAILURE)
					}
					else
						println "voicemailEnabledOnfwdNum is already Un-checked"
				}

				else
					println "Wrong choice"

				if(FwdDirCallEnabled==1)
				{// Verifying it's already unchecked, we are checking it
					if(WebUI.verifyElementNotChecked(findTestObject('/Apps/SmartPBX/Call_Forward/Checkbox_Forward_direct_calls_only'), 2,FailureHandling.OPTIONAL))
						WebUI.enhancedClick(findTestObject('/Apps/SmartPBX/Call_Forward/Checkbox_Forward_direct_calls_only'), FailureHandling.STOP_ON_FAILURE)

					else
						println "Checkbox_Forward_direct_calls_only is already checked"

				}
				else if(FwdDirCallEnabled==0)
				{//Verifying it's already checked, we are un-checking it
					//to be checked
					if(WebUI.verifyElementChecked(findTestObject('/Apps/SmartPBX/Call_Forward/Checkbox_Forward_direct_calls_only'),5,FailureHandling.OPTIONAL))
					{
						WebUI.delay(2)
						WebUI.enhancedClick(findTestObject('/Apps/SmartPBX/Call_Forward/Checkbox_Forward_direct_calls_only'), FailureHandling.STOP_ON_FAILURE)
					}
					else
						println "Checkbox_Forward_direct_calls_only is already Un-checked"
				}
				else
					println "Wrong input choice for Checkbox_Forward_direct_calls_only!"


				if(origCallerId==1)
				{
					if(WebUI.verifyElementNotChecked(findTestObject('Apps/SmartPBX/Call_Forward/Checkbox_Keep_Original_CallerID'), 2,FailureHandling.OPTIONAL))
						WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Call_Forward/Checkbox_Keep_Original_CallerID'), FailureHandling.STOP_ON_FAILURE)

					else
						println "Checkbox_Keep_Original_CallerID is already checked"
				}

				else if(origCallerId==0)
				{//Verifying it's already checked, we are un-checking it
					if(WebUI.verifyElementChecked(findTestObject('Apps/SmartPBX/Call_Forward/Checkbox_Keep_Original_CallerID'),5,FailureHandling.OPTIONAL))
					{
						WebUI.delay(3)
						WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Call_Forward/Checkbox_Keep_Original_CallerID'), FailureHandling.STOP_ON_FAILURE)
					}
					else
						println "Checkbox_Keep_Original_CallerID is already Un-checked"
				}
				else
					println "Wrong input choice for Checkbox_Keep_Original_CallerID!"

				WebUI.enhancedClick(findTestObject('/Apps/SmartPBX/PhoneNumbers/btn_Save_Changes'),FailureHandling.STOP_ON_FAILURE)
			//WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Call_Forward/Button_Cancel_UserFields'))
				WebUI.delay(5)
				WebUI.doubleClick(findTestObject("Apps/SmartPBX/Call_Forward/label_UserFeatures"))
				WebUI.delay(3)

				break;

			case "Failover Mode" :
				println "In FailOver Mode"
				WebUI.delay(2)
				WebUI.enhancedClick(findTestObject('Object Repository/Apps/SmartPBX/Call_Forward/Button_FailOverMode_CallForward_DialogBox'))

				WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Call_Forward/InputBox_UserPhoneNumber_CallToBeForwardOn'), FailureHandling.STOP_ON_FAILURE)
				WebUI.clearText(findTestObject('Apps/SmartPBX/Call_Forward/InputBox_UserPhoneNumber_CallToBeForwardOn'), FailureHandling.STOP_ON_FAILURE)
				WebUI.setText(findTestObject('/Apps/SmartPBX/Call_Forward/InputBox_UserPhoneNumber_CallToBeForwardOn'),phoneNumber1, FailureHandling.STOP_ON_FAILURE)

				WebUI.delay(2)


				if(voicemailEnabledOnfwdNum==1)
				{// Verifying it's already unchecked, we are checking it
					if(WebUI.verifyElementNotChecked(findTestObject('Apps/SmartPBX/Call_Forward/Checkbox_Leave_voicemails_on_forwarded_numbers'), 2,FailureHandling.OPTIONAL))
						WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Call_Forward/Checkbox_Leave_voicemails_on_forwarded_numbers'), FailureHandling.STOP_ON_FAILURE)

					else
						println " voicemailEnabledOnfwdNum is already checked"
				}

				else if(voicemailEnabledOnfwdNum==0)
				{//Verifying it's already checked, we are un-checking it
					println "checking for VM=0"

					if(WebUI.verifyElementChecked(findTestObject('Apps/SmartPBX/Call_Forward/Checkbox_Leave_voicemails_on_forwarded_numbers'),5,FailureHandling.OPTIONAL))
					{
						WebUI.delay(2)
						WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Call_Forward/Checkbox_Leave_voicemails_on_forwarded_numbers'), FailureHandling.STOP_ON_FAILURE)
					}
					else
						println "voicemailEnabledOnfwdNum is already Un-checked"
				}

				else
					println "Wrong choice"

				if(FwdDirCallEnabled==1)
				{// Verifying it's already unchecked, we are checking it
					if(WebUI.verifyElementNotChecked(findTestObject('/Apps/SmartPBX/Call_Forward/Checkbox_Forward_direct_calls_only'), 2,FailureHandling.OPTIONAL))
						WebUI.enhancedClick(findTestObject('/Apps/SmartPBX/Call_Forward/Checkbox_Forward_direct_calls_only'), FailureHandling.STOP_ON_FAILURE)

					else
						println "Checkbox_Forward_direct_calls_only is already checked"

				}
				else if(FwdDirCallEnabled==0)
				{//Verifying it's already checked, we are un-checking it
					//to be checked
					if(WebUI.verifyElementChecked(findTestObject('/Apps/SmartPBX/Call_Forward/Checkbox_Forward_direct_calls_only'),5,FailureHandling.OPTIONAL))
					{
						WebUI.delay(2)
						WebUI.enhancedClick(findTestObject('/Apps/SmartPBX/Call_Forward/Checkbox_Forward_direct_calls_only'), FailureHandling.STOP_ON_FAILURE)
					}
					else
						println "Checkbox_Forward_direct_calls_only is already Un-checked"
				}
				else
					println "Wrong input choice for Checkbox_Forward_direct_calls_only!"


				if(origCallerId==1)
				{
					if(WebUI.verifyElementNotChecked(findTestObject('Apps/SmartPBX/Call_Forward/Checkbox_Keep_Original_CallerID'), 2,FailureHandling.OPTIONAL))
						WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Call_Forward/Checkbox_Keep_Original_CallerID'), FailureHandling.STOP_ON_FAILURE)

					else
						println "Checkbox_Keep_Original_CallerID is already checked"
				}

				else if(origCallerId==0)
				{//Verifying it's already checked, we are un-checking it
					if(WebUI.verifyElementChecked(findTestObject('Apps/SmartPBX/Call_Forward/Checkbox_Keep_Original_CallerID'),5,FailureHandling.OPTIONAL))
					{
						WebUI.delay(3)
						WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Call_Forward/Checkbox_Keep_Original_CallerID'), FailureHandling.STOP_ON_FAILURE)
					}
					else
						println "Checkbox_Keep_Original_CallerID is already Un-checked"
				}
				else
					println "Wrong input choice for Checkbox_Keep_Original_CallerID!"

				WebUI.enhancedClick(findTestObject('/Apps/SmartPBX/PhoneNumbers/btn_Save_Changes'),FailureHandling.STOP_ON_FAILURE)
			//WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Call_Forward/Button_Cancel_UserFields'))
				WebUI.delay(5)
				WebUI.doubleClick(findTestObject("Apps/SmartPBX/Call_Forward/label_UserFeatures"))
				WebUI.delay(3)

				break;
		}

	}

}