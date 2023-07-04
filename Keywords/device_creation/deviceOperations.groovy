package device_creation
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import internal.GlobalVariable
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.exception.WebElementNotFoundException
import static org.junit.Assert.*


class deviceOperations {
	/*
	 * provide first name and last name of user
	 * add device to user name provided
	 */
	@Keyword
	def addDeviceToUser(String firstName, String lastName){
		//Get the empty device column data of user

		def user_name = " "+firstName+" "+lastName

		def deviceAssigned = WebUI.getText(findTestObject('Apps/SmartPBX/Devices/specific_user_device',[('user_name') : user_name]), FailureHandling.STOP_ON_FAILURE)

		//Check for None Text Present in Device div
		if (WebUI.verifyEqual(deviceAssigned, 'None', FailureHandling.STOP_ON_FAILURE)) {
			//Verify the text present on add new device page
			WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Devices/specific_user_device',[('user_name') : user_name]), FailureHandling.STOP_ON_FAILURE)

			WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/Devices/Empty_Device_Text'), FailureHandling.STOP_ON_FAILURE)

			WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/Devices/a_New_Device'))

			WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Devices/a_New_Device'), FailureHandling.STOP_ON_FAILURE)

			WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/Devices/a_Soft_Phone'))

			WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Devices/a_Soft_Phone'), FailureHandling.STOP_ON_FAILURE)

			WebUI.callTestCase(findTestCase('Apps/App_SmartPBX/Device_Creation/SC_Verify_Add_Device_Page'), [:], FailureHandling.STOP_ON_FAILURE)

			//providing valid text for device creation
			WebUI.setText(findTestObject('Apps/SmartPBX/Devices/input_Device_Name_name'), firstName, FailureHandling.STOP_ON_FAILURE)

			WebUI.clearText(findTestObject('Apps/SmartPBX/Devices/input_SIP_Username_sipusername'))

			//input sip username
			WebUI.setText(findTestObject('Apps/SmartPBX/Devices/input_SIP_Username_sipusername'), firstName, FailureHandling.STOP_ON_FAILURE)

			WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/Devices/input_Sip_Password'), FailureHandling.STOP_ON_FAILURE)

			//verifying realm
			def realmValue = WebUI.getText(findTestObject('Apps/SmartPBX/Devices/Add_Softphone/Realm_value'))

			if (realmValue.isEmpty() || realmValue.equals('NULL')) {
				assertNotNull('Realm Value is null',  realmValue.isEmpty())

			} else {
				println(WebUI.getText(findTestObject('Apps/SmartPBX/Devices/Add_Softphone/Realm_value'), FailureHandling.OPTIONAL))

				WebUI.verifyEqual(true, GlobalVariable.accountRealm.contains(realmValue), FailureHandling.OPTIONAL)
			}

			//Click on Advanced Tab Present on Page
			WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Devices/Add_Softphone/advance_text'), FailureHandling.STOP_ON_FAILURE)

			//Verify text present on Advanced page
			WebUI.callTestCase(findTestCase('Apps/App_SmartPBX/Device_Creation/SC_Verify_Advanced_Tab'), [:], FailureHandling.STOP_ON_FAILURE)

			//Verify 'un-assign' button is visible after device is created
			WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/Devices/button_Unassign'), FailureHandling.STOP_ON_FAILURE)

			//verify sip user name of device
			def afterDeviceEnabled = WebUI.getText(findTestObject('Apps/SmartPBX/Devices/Device_Enabled'), FailureHandling.STOP_ON_FAILURE)

			WebUI.verifyEqual(afterDeviceEnabled, firstName, FailureHandling.STOP_ON_FAILURE)

			//Click on Save Changes
			WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Devices/button_Save_Changes'), FailureHandling.STOP_ON_FAILURE)

			//Verify Status message after device added to user
			WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/Devices/status_message'), FailureHandling.STOP_ON_FAILURE)

			println(WebUI.getText(findTestObject('Apps/SmartPBX/Devices/status_message'), FailureHandling.STOP_ON_FAILURE))

			//verification
			WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/Devices/device_softphone_icon'),
					FailureHandling.STOP_ON_FAILURE)

			println('First Name : ' + " "+ firstName)

			WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Devices/specific_user_device',[('user_name') : user_name]), FailureHandling.STOP_ON_FAILURE)

			WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/Devices/button_Unassign'), 30, FailureHandling.STOP_ON_FAILURE)

			WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Devices/specific_device_Enabled',[('user_name') : firstName]), FailureHandling.STOP_ON_FAILURE)

			WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/Devices/Add_Softphone/edit_softphone_title'), 30, FailureHandling.STOP_ON_FAILURE)

			def editTitle = WebUI.getText(findTestObject('Apps/SmartPBX/Devices/Add_Softphone/edit_softphone_title'),
					FailureHandling.STOP_ON_FAILURE)

			println('Title of softphone:' + " "+ editTitle)

			WebUI.verifyEqual(editTitle, 'Editing Softphone: ' + firstName, FailureHandling.STOP_ON_FAILURE)

			WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/Devices/Add_Softphone/device_sofphone_model'),
					FailureHandling.STOP_ON_FAILURE)

			WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/Devices/Add_Softphone/DeviceName_label'), FailureHandling.STOP_ON_FAILURE)

			WebUI.verifyEqual(WebUI.getAttribute(findTestObject('Apps/SmartPBX/Devices/input_Device_Name_name'), 'value',
					FailureHandling.STOP_ON_FAILURE), firstName, FailureHandling.STOP_ON_FAILURE)

			WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/Devices/Add_Softphone/UserName_label'), FailureHandling.STOP_ON_FAILURE)

			WebUI.verifyEqual(WebUI.getAttribute(findTestObject('Apps/SmartPBX/Devices/input_SIP_Username_sipusername'),
					'value', FailureHandling.STOP_ON_FAILURE), firstName, FailureHandling.STOP_ON_FAILURE)

			WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Devices/Add_Softphone/advance_text'), FailureHandling.STOP_ON_FAILURE)

			WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/Devices/Add_Softphone/Miscellanous_link'), FailureHandling.STOP_ON_FAILURE)

			WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Devices/Add_Softphone/Miscellanous_link'), FailureHandling.STOP_ON_FAILURE)

			WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/Devices/Add_Softphone/Miscellanous_Tab/Miscellanous_text'),
					FailureHandling.STOP_ON_FAILURE)

			WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/Devices/Add_Softphone/Miscellanous_Tab/WebRTC_Checkbox'),
					FailureHandling.STOP_ON_FAILURE)

			WebUI.delay(5)

			WebUI.verifyElementChecked(findTestObject('Apps/SmartPBX/Devices/Add_Softphone/Miscellanous_Tab/Verify_webrtc_checkbox'),
					20)

			WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Devices/Invalid_Data/button_Close'), FailureHandling.STOP_ON_FAILURE)

			WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Devices/cancel_link'), FailureHandling.STOP_ON_FAILURE)
		}
	}
}