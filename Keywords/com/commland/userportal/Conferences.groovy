package com.commland.userportal
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.commland.userportal.webPhone


public class Conferences {

	/*
	 * Verify Personal Conference bridge passing 
	 * Expected Conference Name,Conference Id,Participant Pin, Moderator Pin
	 * Conference Bridge Number if we have multiple bridges
	 */
	@Keyword
	def verifyPersonalConferenceBridge(int conferenceBrideNumber,String conferenceName,String conferenceId,String participantPin,String moderatorPin){

		WebUI.verifyElementVisible(findTestObject('Comm.land/App_User_Portal/Conferences/Personal/h1_Personal'), FailureHandling.STOP_ON_FAILURE)

		WebUI.verifyElementVisible(findTestObject('Comm.land/App_User_Portal/Conferences/Personal/div_ConferenceName',[('row') : conferenceBrideNumber]), FailureHandling.STOP_ON_FAILURE)

		println("Conference Bridge Name is :" + WebUI.getText(findTestObject('Comm.land/App_User_Portal/Conferences/Personal/div_ConferenceName',[('row') : conferenceBrideNumber]), FailureHandling.STOP_ON_FAILURE))

		WebUI.verifyEqual(WebUI.getText(findTestObject('Comm.land/App_User_Portal/Conferences/Personal/div_ConferenceName',[('row') : conferenceBrideNumber]), FailureHandling.STOP_ON_FAILURE), conferenceName, FailureHandling.STOP_ON_FAILURE)

		println("Conference Bridge Id  is :" + WebUI.getText(findTestObject('Comm.land/App_User_Portal/Conferences/Personal/td_ConfernceId',[('row') : conferenceBrideNumber]), FailureHandling.STOP_ON_FAILURE))

		WebUI.verifyEqual(WebUI.getText(findTestObject('Comm.land/App_User_Portal/Conferences/Personal/td_ConfernceId', [('row') : conferenceBrideNumber]), FailureHandling.STOP_ON_FAILURE), conferenceId, FailureHandling.STOP_ON_FAILURE)

		println("Participant Pin  is :" + WebUI.getText(findTestObject('Comm.land/App_User_Portal/Conferences/Personal/participant_pin_Value',[('row') : conferenceBrideNumber]), FailureHandling.STOP_ON_FAILURE))

		WebUI.verifyEqual(WebUI.getText(findTestObject('Comm.land/App_User_Portal/Conferences/Personal/participant_pin_Value', [('row') : conferenceBrideNumber]), FailureHandling.STOP_ON_FAILURE),participantPin, FailureHandling.STOP_ON_FAILURE)

		println("Moderator Pin  is :" + WebUI.getText(findTestObject('Comm.land/App_User_Portal/Conferences/Personal/moderator_Pin_Value',[('row') : conferenceBrideNumber]), FailureHandling.STOP_ON_FAILURE))

		WebUI.verifyEqual(WebUI.getText(findTestObject('Comm.land/App_User_Portal/Conferences/Personal/moderator_Pin_Value', [('row') : conferenceBrideNumber]), FailureHandling.STOP_ON_FAILURE), moderatorPin, FailureHandling.STOP_ON_FAILURE)
	}


	/*
	 * Verify Virtual Office Conference bridge passing
	 * Expected Conference Name,Conference Id,Participant Pin, Moderator Pin
	 * Conference Bridge Number if we have multiple bridges
	 */
	@Keyword
	def verifyVirtualOfficeConferenceBridge(int conferenceBrideNumber,String conferenceName,String conferenceId,String participantPin,String moderatorPin){

		WebUI.verifyElementVisible(findTestObject('Comm.land/App_User_Portal/Conferences/Virtual_Office/h1_Virtual_Office'), FailureHandling.STOP_ON_FAILURE)

		WebUI.verifyElementVisible(findTestObject('Comm.land/App_User_Portal/Conferences/Virtual_Office/div_Conference_Name',[('row') : conferenceBrideNumber]), FailureHandling.STOP_ON_FAILURE)

		println("Conference Bridge Name is :" + WebUI.getText(findTestObject('Comm.land/App_User_Portal/Conferences/Virtual_Office/div_Conference_Name',[('row') : conferenceBrideNumber]), FailureHandling.STOP_ON_FAILURE))

		WebUI.verifyEqual(WebUI.getText(findTestObject('Comm.land/App_User_Portal/Conferences/Virtual_Office/div_Conference_Name',[('row') : conferenceBrideNumber]), FailureHandling.STOP_ON_FAILURE), conferenceName, FailureHandling.STOP_ON_FAILURE)

		println("Conference Bridge Id  is :" + WebUI.getText(findTestObject('Comm.land/App_User_Portal/Conferences/Virtual_Office/td_ConferenceId',[('row') : conferenceBrideNumber]), FailureHandling.STOP_ON_FAILURE))

		WebUI.verifyEqual(WebUI.getText(findTestObject('Comm.land/App_User_Portal/Conferences/Virtual_Office/td_ConferenceId', [('row') : conferenceBrideNumber]), FailureHandling.STOP_ON_FAILURE), conferenceId, FailureHandling.STOP_ON_FAILURE)

		println("Participant Pin  is :" + WebUI.getText(findTestObject('Comm.land/App_User_Portal/Conferences/Virtual_Office/participant_Pin_Value',[('row') : conferenceBrideNumber]), FailureHandling.STOP_ON_FAILURE))

		WebUI.verifyEqual(WebUI.getText(findTestObject('Comm.land/App_User_Portal/Conferences/Virtual_Office/participant_Pin_Value', [('row') : conferenceBrideNumber]), FailureHandling.STOP_ON_FAILURE),participantPin, FailureHandling.STOP_ON_FAILURE)

		println("Moderator Pin  is :" + WebUI.getText(findTestObject('Comm.land/App_User_Portal/Conferences/Virtual_Office/moderator_Pin_Value',[('row') : conferenceBrideNumber]), FailureHandling.STOP_ON_FAILURE))

		WebUI.verifyEqual(WebUI.getText(findTestObject('Comm.land/App_User_Portal/Conferences/Virtual_Office/moderator_Pin_Value', [('row') : conferenceBrideNumber]), FailureHandling.STOP_ON_FAILURE), moderatorPin, FailureHandling.STOP_ON_FAILURE)
	}

	/*
	 * Search for a conference bridge providing 
	 * Conference bridge name as parameter
	 */
	@Keyword
	def searchForConferenceBridge(String conferenceBridgeName){
		WebUI.verifyElementVisible(findTestObject('Comm.land/App_User_Portal/Conferences/Virtual_Office/h1_Virtual_Office'), FailureHandling.STOP_ON_FAILURE)

		WebUI.verifyElementVisible(findTestObject('Comm.land/App_User_Portal/Conferences/Virtual_Office/input_Search_Conference_Bridge'), FailureHandling.STOP_ON_FAILURE)

		WebUI.clearText(findTestObject('Comm.land/App_User_Portal/Conferences/Virtual_Office/input_Search_Conference_Bridge'), FailureHandling.STOP_ON_FAILURE)

		WebUI.setText(findTestObject('Comm.land/App_User_Portal/Conferences/Virtual_Office/input_Search_Conference_Bridge'), conferenceBridgeName, FailureHandling.STOP_ON_FAILURE)

		WebUI.sendKeys(findTestObject('Comm.land/App_User_Portal/Conferences/Virtual_Office/input_Search_Conference_Bridge'), Keys.chord(Keys.ENTER))

		WebUI.delay(5)
	}

	/*
	 * verify user joined in Personal conference bridge 
	 * Passing full user name as parameter
	 * 
	 */
	@Keyword
	def verifyUserJoinedInPersonalBridge(int conferenceRowId,String firstName,String lastName){

		String last_name = lastName.substring(0, 8)

		String fullName = firstName+" "+last_name

		fullName = fullName.substring(0, 15)

		int conferenceRow = conferenceRowId +1

		println("Duration is:"+ WebUI.getText(findTestObject('Comm.land/App_User_Portal/Conferences/Personal/p_Duration_value',[('row') : conferenceRow]), FailureHandling.STOP_ON_FAILURE))

		WebUI.waitForElementVisible(findTestObject('Comm.land/App_User_Portal/Conferences/Personal/div_active_participants',[('fullName') : fullName]), 5, FailureHandling.STOP_ON_FAILURE)
	}


	/*
	 * verify user joined in Virtual office conference bridge
	 * Passing full user name as parameter
	 *
	 */
	@Keyword
	def verifyUserJoinedInVirtualOfficeBridge(int conferenceRowId,String firstName,String lastName){

		String last_name = lastName.substring(0, 8)

		String fullName = firstName+" "+last_name

		fullName = fullName.substring(0, 15)

		int conferenceRow = conferenceRowId +1

		println("Duration is:"+ WebUI.getText(findTestObject('Comm.land/App_User_Portal/Conferences/Virtual_Office/p_Duration_value',[('row') : conferenceRow]), FailureHandling.STOP_ON_FAILURE))

		WebUI.waitForElementVisible(findTestObject('Comm.land/App_User_Portal/Conferences/Virtual_Office/div_active_participants',[('fullName') : fullName]), 5, FailureHandling.STOP_ON_FAILURE)
	}

	/*
	 * verify user leave from Virtual office conference bridge
	 * Passing full user name as parameter
	 *
	 */
	@Keyword
	def verifyUserLeaveFromVirtualOfficeBridge(String firstName,String lastName){

		String last_name = lastName.substring(0, 8)

		String fullName = firstName+" "+last_name

		fullName = fullName.substring(0, 15)

		WebUI.verifyElementNotPresent(findTestObject('Comm.land/App_User_Portal/Conferences/Virtual_Office/div_active_participants',[('fullName') : fullName]), 5 ,FailureHandling.STOP_ON_FAILURE)
	}

	/*
	 * verify user leave from Personal conference bridge
	 * Passing full user name as parameter
	 *
	 */
	@Keyword
	def verifyUserLeaveFromPersonalBridge(String firstName,String lastName){

		String last_name = lastName.substring(0, 8)

		String fullName = firstName+" "+last_name

		fullName = fullName.substring(0, 15)

		WebUI.verifyElementNotPresent(findTestObject('Comm.land/App_User_Portal/Conferences/Personal/div_active_participants',[('fullName') : fullName]), 5 ,FailureHandling.STOP_ON_FAILURE)
	}

	/*
	 * User can join quickly by having conference id
	 * passing conference id as Parameter,you can join quick in personal conference bridge.
	 */
	@Keyword
	def userQuickJoinConferenceBridge(String conferenceId){
		WebUI.verifyElementVisible(findTestObject('Comm.land/App_User_Portal/Conferences/title_Quick_Join'), FailureHandling.STOP_ON_FAILURE)

		WebUI.verifyElementVisible(findTestObject('Comm.land/App_User_Portal/Conferences/input_Search_Conference'), FailureHandling.STOP_ON_FAILURE)

		WebUI.verifyElementVisible(findTestObject('Comm.land/App_User_Portal/Conferences/button_Join'), FailureHandling.STOP_ON_FAILURE)

		WebUI.clearText(findTestObject('Comm.land/App_User_Portal/Conferences/input_Search_Conference'), FailureHandling.STOP_ON_FAILURE)

		WebUI.setText(findTestObject('Comm.land/App_User_Portal/Conferences/input_Search_Conference'), conferenceId, FailureHandling.STOP_ON_FAILURE)

		WebUI.enhancedClick(findTestObject('Comm.land/App_User_Portal/Conferences/button_Join'), FailureHandling.STOP_ON_FAILURE)

		WebUI.delay(5)
	}

	/*
	 * Input conferenceId or Participant pin number in webphone app dial pad
	 */
	@Keyword
	def dialConferenceBridgePins(String idNumber){
		String pin = idNumber

		for (int i = 0; i < pin.length(); i++) {
			WebUI.click(findTestObject('Comm.land/App_User_Portal/WebPhone/DialPad/dynamic_digits', [('char') : pin.charAt(i)]), FailureHandling.STOP_ON_FAILURE)
			
			WebUI.delay(1)
		}
		if(!(pin.equals(''))){
			WebUI.click(findTestObject('Comm.land/App_User_Portal/WebPhone/DialPad/button_hash'), FailureHandling.STOP_ON_FAILURE)

			WebUI.delay(1)
		}
	}

	/*
	 * Make a call to conference provided number
	 * Passing conference number and Conference Pin as parameters
	 */
	@Keyword
	def makeCallToConferenceBridge(String phoneNumber,String conferenceId, String conferencePinId){
		(new webPhone()).call_phoneNumber(phoneNumber)

		WebUI.delay(10)

		WebUI.waitForElementVisible(findTestObject('Comm.land/App_User_Portal/WebPhone/onCall_dialPad'), 5, FailureHandling.STOP_ON_FAILURE)

		WebUI.verifyElementVisible(findTestObject('Comm.land/App_User_Portal/WebPhone/onCall_dialPad'), FailureHandling.STOP_ON_FAILURE)

		WebUI.enhancedClick(findTestObject('Comm.land/App_User_Portal/WebPhone/onCall_dialPad'), FailureHandling.STOP_ON_FAILURE)

		'dialConferenceBridgePins'(conferenceId)

		WebUI.delay(4)

		'dialConferenceBridgePins'(conferencePinId)

		WebUI.delay(1)

		WebUI.waitForElementVisible(findTestObject('Comm.land/App_User_Portal/WebPhone/DialPad/button_hide_dialPad'), 10, FailureHandling.STOP_ON_FAILURE)

		WebUI.verifyElementVisible(findTestObject('Comm.land/App_User_Portal/WebPhone/DialPad/button_hide_dialPad'), FailureHandling.STOP_ON_FAILURE)

		WebUI.enhancedClick(findTestObject('Comm.land/App_User_Portal/WebPhone/DialPad/button_hide_dialPad'), FailureHandling.STOP_ON_FAILURE)
	}
}