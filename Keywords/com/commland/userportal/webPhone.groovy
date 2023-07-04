package com.commland.userportal
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


/*
 * This class provides method for web-phone functionality
 */

public class webPhone {

	/*
	 * Verify webPhone icon is visible, click it 
	 * When webPhone window is launched, verify page is loaded fully
	 * verify web-phone icon is displayed on title-bar
	 * click on web-phone icon to launch web-phone
	 */
	@Keyword
	def launch_webPhone(){
		WebUI.waitForElementVisible(findTestObject("Comm.land/App_User_Portal/HomePage/TitleBar/webPhone_icon"), 10, FailureHandling.STOP_ON_FAILURE)

		WebUI.verifyElementVisible(findTestObject("Comm.land/App_User_Portal/HomePage/TitleBar/webPhone_icon"), FailureHandling.STOP_ON_FAILURE)

		WebUI.click(findTestObject("Comm.land/App_User_Portal/HomePage/TitleBar/webPhone_icon"), FailureHandling.STOP_ON_FAILURE)

		WebUI.delay(15)

		WebUI.switchToWindowIndex(1, FailureHandling.STOP_ON_FAILURE)

		WebUI.delay(2)

		WebUI.waitForElementVisible(findTestObject("Comm.land/App_User_Portal/WebPhone/webPhone_status"), 15, FailureHandling.STOP_ON_FAILURE)

		WebUI.waitForElementVisible(findTestObject("Comm.land/App_User_Portal/WebPhone/number_Input"), 10, FailureHandling.STOP_ON_FAILURE)

		WebUI.verifyElementVisible(findTestObject("Comm.land/App_User_Portal/WebPhone/number_Input"), FailureHandling.STOP_ON_FAILURE)
	}

	/*
	 * Verify phone on idle state and 
	 * input calling number 
	 * dial
	 */
	@Keyword
	def call_phoneNumber(String phoneNumber){

		WebUI.waitForElementVisible(findTestObject("Comm.land/App_User_Portal/WebPhone/number_Input"), 5, FailureHandling.STOP_ON_FAILURE)

		WebUI.waitForElementClickable(findTestObject("Comm.land/App_User_Portal/WebPhone/number_Input"), 5, FailureHandling.STOP_ON_FAILURE)

		WebUI.setText(findTestObject("Comm.land/App_User_Portal/WebPhone/number_Input"), phoneNumber)

		WebUI.delay(2)

		WebUI.waitForElementVisible(findTestObject("Comm.land/App_User_Portal/WebPhone/dialButton"), 5, FailureHandling.STOP_ON_FAILURE)

		WebUI.verifyElementVisible(findTestObject("Comm.land/App_User_Portal/WebPhone/dialButton"), FailureHandling.STOP_ON_FAILURE)

		WebUI.enhancedClick(findTestObject("Comm.land/App_User_Portal/WebPhone/dialButton"), FailureHandling.STOP_ON_FAILURE)

		WebUI.delay(3)

		verify_calling_state()
	}

	/*
	 * Verify phone on idle state and 
	 * pass the single key  
	 * dial
	 */
	@Keyword
	def dial_key(String key){

		WebUI.setText(findTestObject("Comm.land/App_User_Portal/WebPhone/number_Input"), key)

		WebUI.delay(1)

		WebUI.click(findTestObject("Comm.land/App_User_Portal/WebPhone/dialButton"), FailureHandling.STOP_ON_FAILURE)

		WebUI.delay(1)

		verify_calling_state()
	}

	/*
	 * Verify ringing state and
	 * answer call 
	 * verify call is in active state when called. 
	 */
	@Keyword
	def answer_call(){

		verify_ringing_state()

		WebUI.waitForElementVisible(findTestObject("Comm.land/App_User_Portal/WebPhone/answerButton"), 5, FailureHandling.STOP_ON_FAILURE)

		WebUI.verifyElementVisible(findTestObject("Comm.land/App_User_Portal/WebPhone/answerButton"), FailureHandling.STOP_ON_FAILURE)

		WebUI.enhancedClick(findTestObject("Comm.land/App_User_Portal/WebPhone/answerButton"), FailureHandling.STOP_ON_FAILURE)

		WebUI.delay(5)

		if (WebUI.waitForElementVisible(findTestObject("Comm.land/App_User_Portal/WebPhone/answerButton"), 2, FailureHandling.OPTIONAL)){
			WebUI.enhancedClick(findTestObject("Comm.land/App_User_Portal/WebPhone/answerButton"), FailureHandling.STOP_ON_FAILURE)
		}

		WebUI.delay(3)

		verify_activeCall_state()
	}

	/*
	 * End call button is visible 
	 * end  call
	 * verify call is in idle state after hang-up.
	 */
	@Keyword
	def end_reject_call(){

		if(WebUI.waitForElementVisible(findTestObject('Comm.land/App_User_Portal/WebPhone/DialPad/button_hide_dialPad'),1, FailureHandling.OPTIONAL)) {
			WebUI.enhancedClick(findTestObject('Comm.land/App_User_Portal/WebPhone/DialPad/button_hide_dialPad'), FailureHandling.STOP_ON_FAILURE)
		}

		if(WebUI.waitForElementVisible(findTestObject("Comm.land/App_User_Portal/WebPhone/endButton"), 1, FailureHandling.OPTIONAL)) {

			WebUI.verifyElementVisible(findTestObject("Comm.land/App_User_Portal/WebPhone/endButton"), FailureHandling.STOP_ON_FAILURE)

			WebUI.enhancedClick(findTestObject("Comm.land/App_User_Portal/WebPhone/endButton"), FailureHandling.STOP_ON_FAILURE)

			WebUI.delay(3)
		}

		WebUI.waitForElementVisible(findTestObject("Comm.land/App_User_Portal/WebPhone/dialButton"), 10, FailureHandling.STOP_ON_FAILURE)
	}

	//Verify calling state on caller side.
	@Keyword
	def verify_calling_state(){
		WebUI.waitForElementVisible(findTestObject("Comm.land/App_User_Portal/WebPhone/callingState"), 10, FailureHandling.STOP_ON_FAILURE)
	}

	//Verify ringing state on callee side
	@Keyword
	def verify_ringing_state(){
		WebUI.waitForElementVisible(findTestObject("Comm.land/App_User_Portal/WebPhone/ringingState"), 3, FailureHandling.STOP_ON_FAILURE)
	}

	//Verify call is in active state after answered
	@Keyword
	def verify_activeCall_state(){
		WebUI.waitForElementVisible(findTestObject("Comm.land/App_User_Portal/WebPhone/activeCall_state"), 5, FailureHandling.STOP_ON_FAILURE)
	}
}

