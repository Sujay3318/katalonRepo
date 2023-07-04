package com.postrelease.conferences
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject
import internal.GlobalVariable
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.webui.exception.WebElementNotFoundException
import webRTC_Utility.WEBRTC

public class ConferencesOperations {

	/*
	 * Provide Input for new Conference Creation
	 * Conference Name, Conference Id Number,Moderator Pin, Participant Pin,
	 * Conference Owner First Name and Last Name
	 */
	@Keyword
	def createNewConference(String conferenceName,String ownerFirstName,String ownerLastName,String moderatorPin,String participantPin,String conferenceIdNumber){
		WebUI.setText(findTestObject('Apps/App_Conferences/input_Conference_Name'), conferenceName.toString(), FailureHandling.STOP_ON_FAILURE)

		WebUI.setText(findTestObject('Apps/App_Conferences/input_Moderator_Pin'), moderatorPin.toString(), FailureHandling.STOP_ON_FAILURE)

		WebUI.setText(findTestObject('Apps/App_Conferences/input_Participant_Pin'), participantPin.toString(), FailureHandling.STOP_ON_FAILURE)

		WebUI.setText(findTestObject('Apps/App_Conferences/input_Conference_Id'), conferenceIdNumber.toString(), FailureHandling.STOP_ON_FAILURE)

		WebUI.enhancedClick(findTestObject('Apps/App_Conferences/a_conference_owner'), FailureHandling.STOP_ON_FAILURE)

		String owner_name = ownerFirstName+" "+ownerLastName

		println(owner_name)

		WebUI.delay(2)

		WebUI.enhancedClick(findTestObject('Apps/App_Conferences/select_conference_owner',[('owner_name'):owner_name]), FailureHandling.STOP_ON_FAILURE)

		WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/button_Save_Changes'), FailureHandling.STOP_ON_FAILURE)

		WebUI.callTestCase(findTestCase('Common_Window_Handling/SC_Charge_Confirmation_OK'), [:], FailureHandling.OPTIONAL)

		WebUI.delay(5)
	}

	/*
	 * Make a call to conference provided number
	 * Passing conference number and Conference Pin as parameters
	 */
	@Keyword
	def makeCallToConferenceBridge(String phoneNumber,String conferenceId, String conferencePinId){
		(new webRTC_Utility.WEBRTC()).make_call(phoneNumber)

		WebUI.delay(15)

		'dialConferenceBridgePins'(conferenceId)

		WebUI.delay(4)

		'dialConferenceBridgePins'(conferencePinId)

		WebUI.delay(1)

		WebUI.waitForElementPresent(findTestObject('Apps/App_WebRTC/Call_time'), 20, FailureHandling.STOP_ON_FAILURE)
	}

	/*
	 * Verify user joined in conference Bridge
	 * passing full name and extension of user.
	 */
	@Keyword
	def verifyUserJoinedInConferenceBridge(String userFirstName, String userLastName,String userExtension){
		String fullUserName =userFirstName+" "+userLastName

		println("Full User name:" + fullUserName)

		WebUI.waitForElementPresent(findTestObject('Apps/App_Conferences/div_user_name_wrapper',[('fullName') : fullUserName]), 5, FailureHandling.STOP_ON_FAILURE)

		if(WebUI.waitForElementPresent(findTestObject('Apps/App_Conferences/div_caller_id_number',[('extension') : userExtension]), 1, FailureHandling.OPTIONAL)){
			WebUI.verifyElementPresent(findTestObject('Apps/App_Conferences/div_caller_id_number',[('extension') : userExtension]), 5, FailureHandling.OPTIONAL)
		}
	}

	/*
	 * Input conferenceId or Participant pin number in webrtc app dial pad
	 */
	@Keyword
	def dialConferenceBridgePins(String idNumber){
		String pin = idNumber

		for (int i = 0; i < pin.length(); i++) {
			WebUI.setText(findTestObject('Apps/App_WebRTC/Webrtc_input_number'), '' + pin.charAt(i))

			WebUI.delay(1)
		}
		if(!(pin.equals(''))){
			WebUI.click(findTestObject('Apps/App_WebRTC/Button_hash'))

			WebUI.delay(1)
		}
	}

	/*
	 * Enable / Disable the main conference number in smartPBX
	 * Passing "Enable","Disable" as a parameter to the keyword
	 */
	@Keyword
	def configureConferenceBridge(String Enable, String conferenceId){
		println(Enable)
		switch(Enable){
			case "Enable" :
			//verify features column present in Users
				WebUI.waitForElementPresent(findTestObject('Apps/SmartPBX/Users/user_features'), 10, FailureHandling.STOP_ON_FAILURE)

			//click on user features of user1
				WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Users/user_features'), FailureHandling.STOP_ON_FAILURE)

			//verify conference bridge present on UI
				WebUI.waitForElementPresent(findTestObject('Apps/SmartPBX/Users/feature_conference_bridge'), 10, FailureHandling.STOP_ON_FAILURE)

			//click on conference bridge feature on UI
				WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Users/feature_conference_bridge'), FailureHandling.STOP_ON_FAILURE)

			//verify header conference bridge present on UI
				WebUI.verifyElementVisible(findTestObject('Apps/App_Conferences/txt_header_Conference_Bridge'), FailureHandling.STOP_ON_FAILURE)

			//verify text for conference bridge present on UI
				WebUI.verifyEqual(WebUI.getText(findTestObject('Apps/App_Conferences/txt_Personal_Conference_Bridge'), FailureHandling.STOP_ON_FAILURE),
						'Personal Conference Bridge', FailureHandling.STOP_ON_FAILURE)

			//click on Conference bridge on UI
				WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Enable_Conference_Feature'), FailureHandling.STOP_ON_FAILURE)

				WebUI.verifyElementPresent(findTestObject('Apps/SmartPBX/label_Conferencing_Phone_Numbers'), 5, FailureHandling.STOP_ON_FAILURE)

				WebUI.verifyElementPresent(findTestObject('Apps/SmartPBX/label_Personal_Conference_Room_number'), 2, FailureHandling.STOP_ON_FAILURE)

			//fetching phone number from a UI, while using for a conference number to join in
				def mainConferenceNumber = WebUI.getText(findTestObject('Apps/App_Conferences/div_main_conference_number'), FailureHandling.STOP_ON_FAILURE)

			//Removing Spaces from Phone number
				String phoneNumber1 = mainConferenceNumber.replaceAll('\\s', '')

				GlobalVariable.gateway_numbers.add(phoneNumber1)

				println('Main Conference number :' + (GlobalVariable.gateway_numbers[1]))

				WebUI.verifyElementPresent(findTestObject('Apps/SmartPBX/label_Enable_Video_Conferencing'), 2, FailureHandling.STOP_ON_FAILURE)

				WebUI.verifyElementPresent(findTestObject('Apps/SmartPBX/txt_conference_bridge_hint'), 2, FailureHandling.STOP_ON_FAILURE)

				WebUI.clearText(findTestObject('Apps/SmartPBX/input_Personal_Conference_Number'), FailureHandling.OPTIONAL)

			//Set Text conference id as a room number
				WebUI.setText(findTestObject('Apps/SmartPBX/input_Personal_Conference_Number'), conferenceId, FailureHandling.STOP_ON_FAILURE)

			//click on save changes button
				WebUI.enhancedClick(findTestObject('Apps/SmartPBX/PhoneNumbers/button_Save_Changes'), FailureHandling.STOP_ON_FAILURE)

			//click on charges confirmation
				WebUI.callTestCase(findTestCase('Common_Window_Handling/SC_Charge_Confirmation_OK'), [:], FailureHandling.OPTIONAL)

				WebUI.delay(5)

				break

			case "Disable" :
			//Conference bridge icon present on features
				WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/Features/Conference_bridge/i_conference_bridge_tooltip'), 10, FailureHandling.STOP_ON_FAILURE)

			//click on user features of user1
				WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Users/user_features'), FailureHandling.STOP_ON_FAILURE)

			//verify conference bridge present on UI
				WebUI.waitForElementPresent(findTestObject('Apps/SmartPBX/Users/feature_conference_bridge'), 10, FailureHandling.STOP_ON_FAILURE)

			//click on conference bridge feature on UI
				WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Users/feature_conference_bridge'), FailureHandling.STOP_ON_FAILURE)

			//verify header conference bridge present on UI
				WebUI.verifyElementVisible(findTestObject('Apps/App_Conferences/txt_header_Conference_Bridge'), FailureHandling.STOP_ON_FAILURE)

			//verify text for conference bridge present on UI
				WebUI.verifyEqual(WebUI.getText(findTestObject('Apps/App_Conferences/txt_Personal_Conference_Bridge'), FailureHandling.STOP_ON_FAILURE),
						'Personal Conference Bridge', FailureHandling.STOP_ON_FAILURE)

				if(WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/Features/span_enabled'), 5, FailureHandling.OPTIONAL)){

					//Click on Conference bridge on UI
					WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Enable_Conference_Feature'), FailureHandling.STOP_ON_FAILURE)

					WebUI.waitForElementPresent(findTestObject('Apps/SmartPBX/PhoneNumbers/button_Save_Changes'), 10, FailureHandling.STOP_ON_FAILURE)

					WebUI.click(findTestObject('Apps/SmartPBX/PhoneNumbers/button_Save_Changes'),FailureHandling.STOP_ON_FAILURE)

					WebUI.delay(5)

					if(WebUI.verifyElementPresent(findTestObject('Apps/SmartPBX/PhoneNumbers/button_Save_Changes'), 10,FailureHandling.OPTIONAL)){

						//click on save changes button
						WebUI.click(findTestObject('Apps/SmartPBX/PhoneNumbers/button_Save_Changes'), FailureHandling.OPTIONAL)
					}

					WebUI.callTestCase(findTestCase('Common_Window_Handling/SC_Charge_Confirmation_OK'), [:], FailureHandling.OPTIONAL)

					WebUI.delay(10)
				}else{

					WebUI.waitForElementPresent(findTestObject('Apps/SmartPBX/PhoneNumbers/button_Save_Changes'), 10, FailureHandling.STOP_ON_FAILURE)

					//click on save changes button
					WebUI.click(findTestObject('Apps/SmartPBX/PhoneNumbers/button_Save_Changes'), FailureHandling.STOP_ON_FAILURE)

					WebUI.callTestCase(findTestCase('Common_Window_Handling/SC_Charge_Confirmation_OK'), [:], FailureHandling.OPTIONAL)

					WebUI.delay(10)
				}

				break

			default: println("Enter a Proper configuration present in case statement")

		}
	}
}
