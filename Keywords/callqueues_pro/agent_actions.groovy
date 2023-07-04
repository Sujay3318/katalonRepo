package callqueues_pro

/* 
 * Added the Queue Creation Keyword in here. 
 */



import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.WebElement

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.exception.WebElementNotFoundException
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testdata.reader.ExcelFactory as Excel
import internal.GlobalVariable



class agent_actions {

	/**
	 * Click "Begin Session" button on CC Basic/Pro Landing Page
	 */

	@Keyword
	def click_begin_session() {
		try {
			WebElement element = WebUI.findWebElement('Apps/App_CallCenter_Pro/Agent_State_Changes/button_BeginSession')
			KeywordUtil.logInfo("Clicking 'Begin Session' button")
			element.click()
			KeywordUtil.markPassed("'Begin Session' has been clicked")
		} catch (WebElementNotFoundException e) {
			KeywordUtil.markFailed("'Begin Session' button not found")
		} catch (Exception e) {
			KeywordUtil.markFailed("Fail to click on 'Begin Session'")
		}
	}

	/**
	 * Click "End Session" button on CC Basic/Pro Landing Page
	 */

	@Keyword
	def click_end_session() {
		try {
			WebElement element = WebUI.findWebElement('Apps/App_CallCenter_Pro/Agent_State_Changes/a_End Session')
			KeywordUtil.logInfo("Clicking 'End Session' button")
			element.click()
			KeywordUtil.markPassed("'End Session' has been clicked")
		} catch (WebElementNotFoundException e) {
			KeywordUtil.markFailed("'End Session' button not found")
		} catch (Exception e) {
			KeywordUtil.markFailed("Fail to click on 'End Session'")
		}
	}

	/**
	 * Click "Mark As Away" button. Change state from Away to Ready
	 */

	@Keyword
	def click_mark_as_ready() {
		try {
			WebElement element1 = WebUI.findWebElement('Apps/App_CallCenter_Pro/Agent_State_Changes/button_Away')
			WebElement element2 = WebUI.findWebElement('Apps/App_CallCenter_Pro/Agent_State_Changes/a_Mark as Ready')
			KeywordUtil.logInfo("Clicking 'Mark As Ready' button")
			element1.click()
			element2.click()
			KeywordUtil.markPassed("'Mark As Ready' has been clicked")
		} catch (WebElementNotFoundException e) {
			KeywordUtil.markFailed("'Mark As Ready' button not found")
		} catch (Exception e) {
			KeywordUtil.markFailed("Fail to click on 'Mark As Ready'")
		}
	}

	/**
	 * Click "Mark As Ready" button. Change state from Ready to Away
	 */

	@Keyword
	def click_mark_as_away() {
		try {
			WebElement element1 = WebUI.findWebElement('Apps/App_CallCenter_Pro/Agent_State_Changes/button_Ready')
			WebElement element2 = WebUI.findWebElement('Apps/App_CallCenter_Pro/Agent_State_Changes/a_Mark as Away')
			KeywordUtil.logInfo("Clicking 'Mark As Away' button")
			element1.click()
			element2.click()
			KeywordUtil.markPassed("'Mark As Away' has been clicked")
		} catch (WebElementNotFoundException e) {
			KeywordUtil.markFailed("'Mark As Away' button not found")
		} catch (Exception e) {
			KeywordUtil.markFailed("Fail to click on 'Mark As Away'")
		}
	}


	/**
	 * Click "Pause All Queues" button
	 */

	@Keyword
	def click_pause_all_queues() {
		try {
			WebElement element = WebUI.findWebElement('Apps/App_CallCenter_Pro/Agent_State_Changes/button_Pause All Queues')
			KeywordUtil.logInfo("Clicking 'Pause All Queues' button")
			element.click()
			KeywordUtil.markPassed("'Pause All Queues' has been clicked")
		} catch (WebElementNotFoundException e) {
			KeywordUtil.markFailed("'Pause All Queues' button not found")
		} catch (Exception e) {
			KeywordUtil.markFailed("Fail to click on 'Pause All Queues'")
		}
	}


	/**
	 * Click "New Queue" to create a Queue
	 * Only admin user can create a queue 
	 */

	@Keyword
	def create_queue(String q_name, int dispAgntToggle, int allowAgentRecovery, boolean assignPhoneNumber, int queueCount) {

		def data = Excel.getExcelDataWithDefaultSheet(GlobalVariable.DataFileSelection.get('DataFilePath'), GlobalVariable.DataFileSelection.get(
				'Test_Data'), true)

		WebUI.waitForElementVisible(findTestObject('Apps/App_CallCenter_Pro/button_New_Queue'), 10, FailureHandling.STOP_ON_FAILURE)
		WebUI.enhancedClick(findTestObject('Apps/App_CallCenter_Pro/button_New_Queue'),FailureHandling.STOP_ON_FAILURE)

		WebUI.callTestCase(findTestCase('Apps/App_Call_Center_Pro/SC_Verify_BasicInfo_tab'), [:], FailureHandling.STOP_ON_FAILURE)
		WebUI.clearText(findTestObject('Apps/App_CallCenter_Pro/input_Name_queuename'), FailureHandling.STOP_ON_FAILURE)
		WebUI.setText(findTestObject('Apps/App_CallCenter_Pro/input_Name_queuename'),q_name, FailureHandling.STOP_ON_FAILURE)

		if(assignPhoneNumber) {
			WebUI.click(findTestObject('Apps/App_CallCenter_Pro/div_Call-in Number or Ext_number-selector-clickable'), FailureHandling.STOP_ON_FAILURE)

			WebUI.click(findTestObject('Apps/App_CallCenter_Pro/Tab_BasicInfo_CreateQueue/Select_Spare_Number_BasicInfo_tab'), FailureHandling.STOP_ON_FAILURE)
			WebUI.delay(3)


			if(WebUI.verifyElementVisible(findTestObject('Apps/SmartPBX/Main_Number/span_United_States_Symbol'), FailureHandling.OPTIONAL)) {
				WebUI.callTestCase(findTestCase('Apps/App_Call_Center_Pro/SC_AssignSpareNumber_to_Queue'), [:], FailureHandling.STOP_ON_FAILURE)
			}
			else {
				WebUI.click(findTestObject('Apps/App_CallCenter_Pro/button_Cancel_ListOfSpareNumbers_popup'), FailureHandling.STOP_ON_FAILURE)
				WebUI.click(findTestObject('Apps/App_CallCenter_Pro/div_Call-in Number or Ext_number-selector-clickable'), FailureHandling.STOP_ON_FAILURE)
				WebUI.click(findTestObject('Apps/App_CallCenter_Pro/Tab_BasicInfo_CreateQueue/BuyNumber_BasicInfoTab'), FailureHandling.STOP_ON_FAILURE)
				//Buy a number
				WebUI.callTestCase(findTestCase('Apps/App_Call_Center_Pro/SC_Buynew_number_and_assign_to_Queue'), [:], FailureHandling.STOP_ON_FAILURE)
				//set it to 'carrier' module
			}

			//store the number assigned to the Queue
			String queue_ph_no = WebUI.getAttribute(findTestObject('Apps/App_CallCenter_Pro/dropdown_phoneNumberAssignedToQueue'), 'value', FailureHandling.STOP_ON_FAILURE)

			//Removing Spaces from Phone number
			queue_ph_no = queue_ph_no.replaceAll('\\s', '')

			println "Number assigned to the queue is :" +queue_ph_no

			(GlobalVariable.gateway_numbers[queueCount-1]) = queue_ph_no

			println('Queue Phone Number is added to List' + (GlobalVariable.gateway_numbers[queueCount-1]))
		}
		else {
			def extension = data.getValue('queueExtension', queueCount)

			WebUI.callTestCase(findTestCase('Apps/App_Call_Center_Pro/SC_Assign_Extension_to_Queue'), [('extension') : extension],
			FailureHandling.STOP_ON_FAILURE)
		}
		WebUI.delay(3)
		WebUI.enhancedClick(findTestObject('Apps/App_CallCenter_Pro/button_Next_CreateQueue_wizard'), FailureHandling.STOP_ON_FAILURE)
		WebUI.callTestCase(findTestCase('Apps/App_Call_Center_Pro/SC_Verify_GeneralSettings_tab'), [:], FailureHandling.STOP_ON_FAILURE)
		WebUI.delay(3)
		WebUI.clearText(findTestObject('Apps/App_CallCenter_Pro/input_Queue_TimeOut'), FailureHandling.STOP_ON_FAILURE)
		WebUI.setText(findTestObject('Apps/App_CallCenter_Pro/input_Queue_TimeOut'),'80', FailureHandling.STOP_ON_FAILURE)

		if(dispAgntToggle==1) {
			// Verifying if it's toggled false, we are toggling true
			if(WebUI.verifyElementNotChecked(findTestObject('Apps/App_CallCenter_Pro/toggle_DisplayAgent_CreateQueue_wizard'), 2,FailureHandling.OPTIONAL)){
				WebUI.enhancedClick(findTestObject('Apps/App_CallCenter_Pro/toggle_DisplayAgent_CreateQueue_wizard'), FailureHandling.STOP_ON_FAILURE)
			}
			else{
				println " Display_Agent_to_other_Agents toggle is already toggled true"
			}
		}

		else if(dispAgntToggle==0) {
			//Verifying it's already checked, we are un-checking it

			if(WebUI.verifyElementChecked(findTestObject('Apps/App_CallCenter_Pro/toggle_DisplayAgent_CreateQueue_wizard'),5,FailureHandling.OPTIONAL))
			{
				WebUI.delay(2)
				WebUI.enhancedClick(findTestObject('Apps/App_CallCenter_Pro/toggle_DisplayAgent_CreateQueue_wizard'), FailureHandling.STOP_ON_FAILURE)
			}
			else{
				println "Display_Agent_to_other_Agents Toggle is already toggled false"
			}
		}

		else{
			println "Wrong choice"
		}

		WebUI.delay(5)
		WebUI.enhancedClick(findTestObject('Apps/App_CallCenter_Pro/button_Next_CreateQueue_wizard'), FailureHandling.STOP_ON_FAILURE)
		WebUI.callTestCase(findTestCase('Apps/App_Call_Center_Pro/SC_Routing_Strategy_tab'), [:], FailureHandling.STOP_ON_FAILURE)
		WebUI.enhancedClick(findTestObject('Apps/App_CallCenter_Pro/button_Next_CreateQueue_wizard'), FailureHandling.STOP_ON_FAILURE)

		WebUI.callTestCase(findTestCase('Apps/App_Call_Center_Pro/SC_Verify_Hold_Treatment_Tab'), [:], FailureHandling.STOP_ON_FAILURE)
		WebUI.enhancedClick(findTestObject('Apps/App_CallCenter_Pro/button_Next_CreateQueue_wizard'), FailureHandling.STOP_ON_FAILURE)

		WebUI.callTestCase(findTestCase('Apps/App_Call_Center_Pro/SC_Agent_Behavior_tab'), [:], FailureHandling.STOP_ON_FAILURE)


		if(allowAgentRecovery==1) {
			// Verifying if it's toggled false, we are toggling true
			if(WebUI.verifyElementNotChecked(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/toggle_AllowAgentRecoveryTime'), 2,FailureHandling.OPTIONAL)){
				WebUI.enhancedClick(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/toggle_AllowAgentRecoveryTime'), FailureHandling.STOP_ON_FAILURE)

				WebUI.clearText(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/input_AllowAgentRecoveryTime'), FailureHandling.STOP_ON_FAILURE)
				WebUI.setText(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/input_AllowAgentRecoveryTime'),'30', FailureHandling.STOP_ON_FAILURE)


			}
			else
			{
				println " allowAgentRecovery toggle is already toggled true"
			}
		}

		else if(allowAgentRecovery==0) {
			//Verifying it's already checked, we are un-checking it

			if(WebUI.verifyElementChecked(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/toggle_AllowAgentRecoveryTime'),5,FailureHandling.OPTIONAL))
			{
				WebUI.delay(2)
				WebUI.enhancedClick(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/toggle_AllowAgentRecoveryTime'), FailureHandling.STOP_ON_FAILURE)
			}
			else
			{
				println "allowAgentRecovery Toggle is already toggled false"
			}
		}

		else
		{
			println "Wrong choice"
		}


		WebUI.enhancedClick(findTestObject('Apps/App_CallCenter_Pro/button_Next_CreateQueue_wizard'), FailureHandling.STOP_ON_FAILURE)


		WebUI.callTestCase(findTestCase('Apps/App_Call_Center_Pro/SC_Member_Assignment_Tab'), [:], FailureHandling.STOP_ON_FAILURE)
		//assign members to queue
		WebUI.enhancedClick(findTestObject('Apps/App_CallCenter_Pro/button_Next_CreateQueue_wizard'), FailureHandling.STOP_ON_FAILURE)


		WebUI.callTestCase(findTestCase('Apps/App_Call_Center_Pro/SC_Verify_Review_and_Confirm_tab'), [('queueName'):q_name], FailureHandling.STOP_ON_FAILURE)

		WebUI.enhancedClick(findTestObject('Apps/App_CallCenter_Pro/button_CreateQueue'), FailureHandling.STOP_ON_FAILURE)

	}



	/**
	 * Click on the existing Queue and edit the agent recovery feature
	 * Only admin user can create/edit a queue
	 */

	@Keyword
	def edit_queue(int allowAgentRecovery) {

		WebUI.click(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/label_tabName_AgentBehavior'), FailureHandling.STOP_ON_FAILURE)

		WebUI.callTestCase(findTestCase('Apps/App_Call_Center_Pro/SC_Agent_Behavior_tab'), [:], FailureHandling.STOP_ON_FAILURE)


		if(allowAgentRecovery==1) {
			// Verifying if it's toggled false, we are toggling true
			if(WebUI.verifyElementNotChecked(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/toggle_AllowAgentRecoveryTime'), 2,FailureHandling.OPTIONAL)){
				WebUI.enhancedClick(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/toggle_AllowAgentRecoveryTime'), FailureHandling.STOP_ON_FAILURE)

				WebUI.clearText(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/input_AllowAgentRecoveryTime'), FailureHandling.STOP_ON_FAILURE)
				WebUI.setText(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/input_AllowAgentRecoveryTime'),'30', FailureHandling.STOP_ON_FAILURE)


			}
			else
			{
				println " allowAgentRecovery toggle is already toggled true"
			}
		}

		else if(allowAgentRecovery==0) {
			//Verifying it's already checked, we are un-checking it

			if(WebUI.verifyElementChecked(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/toggle_AllowAgentRecoveryTime'),5,FailureHandling.OPTIONAL))
			{
				WebUI.delay(2)
				WebUI.enhancedClick(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/toggle_AllowAgentRecoveryTime'), FailureHandling.STOP_ON_FAILURE)
			}
			else
			{
				println "allowAgentRecovery Toggle is already toggled false"
			}
		}

		else
		{
			println "Wrong choice"
		}


		WebUI.enhancedClick(findTestObject('Apps/App_CallCenter_Pro/button_Next_CreateQueue_wizard'), FailureHandling.STOP_ON_FAILURE)
		WebUI.delay(5)
		WebUI.enhancedClick(findTestObject('Apps/App_CallCenter_Pro/button_Next_CreateQueue_wizard'), FailureHandling.STOP_ON_FAILURE)
		WebUI.delay(5)
		WebUI.enhancedClick(findTestObject('Apps/App_CallCenter_Pro/button_Update_Queue'), FailureHandling.STOP_ON_FAILURE)
		WebUI.delay(5)
		println "Queue Updated Successfully"
	}


	/**
	 * Click on the existing Queue and edit the agent Behavior for enabling/disabling agent Recovery/ForceAwayOnRejectMissed/AllowRecoveryTimeExtension and multipleExtensions features.
	 */


	@Keyword
	def edit_agentBehavior_tab(int isAllowAgentRecovery,int isForceAwayOnRejectedMissed, int isAllowRecoveryTimeExtension,int isAllowMultipleExtensions) {

		WebUI.waitForElementVisible(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/label_tabName_AgentBehavior'), 10, FailureHandling.STOP_ON_FAILURE)
		WebUI.click(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/label_tabName_AgentBehavior'), FailureHandling.STOP_ON_FAILURE)

		WebUI.callTestCase(findTestCase('Apps/App_Call_Center_Pro/SC_Agent_Behavior_tab'), [:], FailureHandling.STOP_ON_FAILURE)


		if (isAllowAgentRecovery == 1) {
			// Verifying if it's toggled false, we are toggling true
			if (WebUI.verifyElementNotChecked(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/toggle_AllowAgentRecoveryTime'),
			2, FailureHandling.OPTIONAL)) {
				WebUI.enhancedClick(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/toggle_AllowAgentRecoveryTime'),
						FailureHandling.STOP_ON_FAILURE)

				WebUI.clearText(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/input_AllowAgentRecoveryTime'),
						FailureHandling.STOP_ON_FAILURE)

				WebUI.setText(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/input_AllowAgentRecoveryTime'),
						'30', FailureHandling.STOP_ON_FAILURE)

				println(' allowAgentRecovery toggle is enabled.')
			} else {
				println(' allowAgentRecovery toggle is already toggled true')
			}

			if (isAllowRecoveryTimeExtension == 1) {
				// Verifying if it's toggled false, we are toggling true
				if (WebUI.verifyElementNotChecked(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/toggle_AllowRecoveryTimeExtension'),
				2, FailureHandling.OPTIONAL)) {
					WebUI.enhancedClick(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/toggle_AllowRecoveryTimeExtension'),
							FailureHandling.STOP_ON_FAILURE)

					println('AllowRecoveryTimeExtension is enabled')
				} else {
					println(' AllowRecoveryTimeExtension - toggle is already toggled true')
				}

				if (isAllowMultipleExtensions == 1) {
					// Verifying if it's toggled false, we are toggling true
					if (WebUI.verifyElementNotChecked(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/checkbox_Allow_multiple_ext'),
					2, FailureHandling.OPTIONAL)) {
						WebUI.enhancedClick(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/checkbox_Allow_multiple_ext'),
								FailureHandling.STOP_ON_FAILURE)

						println('AllowMultipleExtensions is enabled now')
					} else {
						println(' AllowMultipleExtensions - toggle is already toggled true')
					}

				}
			} else if (isAllowRecoveryTimeExtension == 0) {
				if (WebUI.verifyElementChecked(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/toggle_AllowRecoveryTimeExtension'),
				5, FailureHandling.OPTIONAL)) {
					WebUI.delay(2)

					WebUI.enhancedClick(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/checkbox_Allow_multiple_ext'),
							FailureHandling.STOP_ON_FAILURE)

					println('AllowRecoveryTimeExtension is disabled now')
				} else {
					println('AllowRecoveryTimeExtension - Toggle is already toggled false')
				}


				if (isAllowMultipleExtensions == 1) {
					if (WebUI.verifyElementNotClickable(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/checkbox_Allow_multiple_ext'),
					FailureHandling.OPTIONAL)) {
						String clickable = WebUI.verifyElementNotClickable(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/checkbox_Allow_multiple_ext'),
								FailureHandling.OPTIONAL)

						println(('Verified AllowMultipleExtensions is not clickable, as the value for present but not clickable is: ' +
								clickable) + ' implies Multiple Recovery Time extension can not be toggled true till the time Recovery feature is enabled')
					}
					else {
						println "It's allowing to check multiple extensions even when AllowRecoveryTimeExtension is disabled."
					}

				}

			}
		}
		else if (isAllowAgentRecovery == 0) {
			if (WebUI.verifyElementChecked(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/toggle_AllowAgentRecoveryTime'),
			5, FailureHandling.OPTIONAL)) {
				WebUI.delay(2)

				WebUI.enhancedClick(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/toggle_AllowAgentRecoveryTime'),
						FailureHandling.STOP_ON_FAILURE)
			} else {
				println('allowAgentRecovery Toggle is already toggled false')
			}

			if (isAllowRecoveryTimeExtension == 1) {
				if (WebUI.verifyElementNotClickable(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/toggle_AllowRecoveryTimeExtension'),
				FailureHandling.OPTIONAL)) {
					String AllowRecoveryTimeExtensionVal = WebUI.verifyElementNotClickable(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/toggle_AllowRecoveryTimeExtension'),
							FailureHandling.STOP_ON_FAILURE)

					println('Verified AllowRecoveryTimeExtension is not clickable, as the value for present but not clickable is: ' +
							AllowRecoveryTimeExtensionVal + ' implies  RecoveryTimeExtension can not be toggled true till the time Recovery feature is enabled')
				}

				if (isAllowMultipleExtensions == 1) {
					if (WebUI.verifyElementNotClickable(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/checkbox_Allow_multiple_ext'),
					FailureHandling.OPTIONAL)) {
						String AllowMultipleExtensionsVal = WebUI.verifyElementNotClickable(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/checkbox_Allow_multiple_ext'),
								FailureHandling.STOP_ON_FAILURE)

						println('Verified AllowMultipleExtensions is not clickable, as the value for present but not clickable is: ' +
								AllowMultipleExtensionsVal + ' implies  Multiple Recovery Time extension can not be checked till the time Recovery feature is enabled')

						WebUI.delay(5)
					}
				} else if (isAllowRecoveryTimeExtension == 0) {
					if (WebUI.verifyElementNotClickable(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/toggle_AllowRecoveryTimeExtension'),
					FailureHandling.OPTIONAL)) {
						String AllowRecoveryTimeExtensionVal = WebUI.verifyElementNotClickable(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/toggle_AllowRecoveryTimeExtension'),
								FailureHandling.STOP_ON_FAILURE)

						println('Verified AllowRecoveryTimeExtension is not clickable, as the value for present but not clickable is: ' +
								AllowRecoveryTimeExtensionVal + ' implies Recovery Time extension can not be checked till the time Recovery feature is enabled')

						WebUI.delay(5)
					}
				} else if (isAllowMultipleExtensions == 0) {
					if (WebUI.verifyElementNotClickable(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/checkbox_Allow_multiple_ext'),
					FailureHandling.OPTIONAL)) {
						String AllowMultipleExtensionsVal = WebUI.verifyElementNotClickable(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/checkbox_Allow_multiple_ext'),
								FailureHandling.STOP_ON_FAILURE)

						println('Verified AllowMultipleExtensions is not clickable, as the value for present but not clickable is: ' +
								AllowMultipleExtensionsVal + ' implies  Multiple Recovery Time extension can not be checked/clicked true till the time Recovery feature is enabled')
					}
				}
			}
		}

		if (isForceAwayOnRejectedMissed == 1) {
			// Verifying if it's toggled false, we are toggling true
			if (WebUI.verifyElementNotChecked(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/toggle_Force_Away_on_RejectedMissed'),
			2, FailureHandling.OPTIONAL)) {
				WebUI.enhancedClick(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/toggle_Force_Away_on_RejectedMissed'),
						FailureHandling.STOP_ON_FAILURE)

				println('ForceAwayOnRejectedMissed is enabled')
			} else {
				println(' ForceAway_on_Reject/Missed - toggle is already toggled true')
			}
		} else if (isForceAwayOnRejectedMissed == 0) {
			if (WebUI.verifyElementChecked(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/toggle_Force_Away_on_RejectedMissed'),
			5, FailureHandling.OPTIONAL)) {
				WebUI.delay(2)

				WebUI.enhancedClick(findTestObject('Apps/App_CallCenter_Pro/Tab_AgentBehavior/toggle_Force_Away_on_RejectedMissed'),
						FailureHandling.STOP_ON_FAILURE)

				println('ForceAwayOnRejectedMissed is disabled')
			} else {
				println('ForceAway_on_Reject/Missed - Toggle is already toggled false')
			}
		} else {
			println('Wrong choice for AllowMultipleExtensions')
		}

		WebUI.enhancedClick(findTestObject('Apps/App_CallCenter_Pro/button_Next_CreateQueue_wizard'), FailureHandling.STOP_ON_FAILURE)
		WebUI.delay(5)
		WebUI.enhancedClick(findTestObject('Apps/App_CallCenter_Pro/button_Next_CreateQueue_wizard'), FailureHandling.STOP_ON_FAILURE)
		WebUI.delay(2)
		WebUI.enhancedClick(findTestObject('Apps/App_CallCenter_Pro/button_Update_Queue'), FailureHandling.STOP_ON_FAILURE)
		WebUI.delay(5)

	}




	/**
	 * Click "Resume All Queues" button
	 */

	@Keyword
	def click_resume_all_queues() {
		try {
			WebElement element = WebUI.findWebElement('Apps/App_CallCenter_Pro/Agent_State_Changes/button_Resume All Queues')
			KeywordUtil.logInfo("Clicking 'Resume All Queues' button")
			element.click()
			KeywordUtil.markPassed("'Resume All Queues' has been clicked")
		} catch (WebElementNotFoundException e) {
			KeywordUtil.markFailed("'Resume All Queues' button not found")
		} catch (Exception e) {
			KeywordUtil.markFailed("Fail to click on 'Resume All Queues'")
		}
	}
}