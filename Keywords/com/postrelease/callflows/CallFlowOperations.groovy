package com.postrelease.callflows
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable

import org.openqa.selenium.Keys as Keys

class CallFlowOperations {

	/*
	 * Search for a group pick using extension number
	 * pass extension number as a parameter in call flows app
	 */
	@Keyword
	def searchForGroupExtension(String extension){
		WebUI.enhancedClick(findTestObject('Apps/App_CallFlows/input_Search_Group_pick'), FailureHandling.STOP_ON_FAILURE)

		WebUI.setText(findTestObject('Apps/App_CallFlows/input_Search_Group_pick'), extension.toString(), FailureHandling.STOP_ON_FAILURE)

		WebUI.delay(3)

		WebUI.sendKeys(findTestObject('Apps/App_CallFlows/input_Search_Group_pick'), Keys.chord(Keys.ENTER))

		if(WebUI.verifyElementVisible(findTestObject('Apps/App_CallFlows/div_callflow_check_extension',[('extension') : extension.toString()]), FailureHandling.OPTIONAL)){

			WebUI.enhancedClick(findTestObject('Apps/App_CallFlows/div_callflow_check_extension',[('extension') : extension.toString()]), FailureHandling.STOP_ON_FAILURE)

			WebUI.delay(3)

			WebUI.enhancedClick(findTestObject('Apps/App_CallFlows/div_Delete_Callflow'), FailureHandling.STOP_ON_FAILURE)

			WebUI.enhancedClick(findTestObject('Standard_Objects/Charge_Confirmation/button_OK'), FailureHandling.STOP_ON_FAILURE)

			WebUI.delay(3)
		}
	}

	/*
	 * create a group pick in call flows app
	 * pass extension number and group name to set ring group as group pick up
	 */
	@Keyword
	def createGroupCallFlow(String extension,String groupName){
		WebUI.enhancedClick(findTestObject('Apps/App_CallFlows/span_Add_Callflow'), FailureHandling.STOP_ON_FAILURE)

		WebUI.verifyElementVisible(findTestObject('Apps/App_CallFlows/div_Save_Changes'), FailureHandling.STOP_ON_FAILURE)

		WebUI.verifyElementVisible(findTestObject('Apps/App_CallFlows/div_Delete_Callflow'), FailureHandling.STOP_ON_FAILURE)

		WebUI.verifyElementVisible(findTestObject('Apps/App_CallFlows/div_Click_to_add_number'), FailureHandling.STOP_ON_FAILURE)

		WebUI.enhancedClick(findTestObject('Apps/App_CallFlows/div_Click_to_add_number'), FailureHandling.STOP_ON_FAILURE)

		WebUI.waitForElementVisible(findTestObject('Apps/App_CallFlows/span_Add_number'), 5, FailureHandling.STOP_ON_FAILURE)

		WebUI.enhancedClick(findTestObject('Apps/App_CallFlows/label_Extensions'), FailureHandling.STOP_ON_FAILURE)

		WebUI.enhancedClick(findTestObject('Apps/App_CallFlows/input_extension_number'), FailureHandling.STOP_ON_FAILURE)

		WebUI.setText(findTestObject('Apps/App_CallFlows/input_extension_number'), extension.toString(), FailureHandling.STOP_ON_FAILURE)

		WebUI.enhancedClick(findTestObject('Apps/App_CallFlows/button_Add'), FailureHandling.STOP_ON_FAILURE)

		WebUI.delay(2)

		WebUI.verifyElementVisible(findTestObject('Apps/App_CallFlows/div_Actions'), FailureHandling.STOP_ON_FAILURE)

		WebUI.enhancedClick(findTestObject('Apps/App_CallFlows/input_Search'), FailureHandling.STOP_ON_FAILURE)

		WebUI.setText(findTestObject('Object Repository/Apps/App_CallFlows/input_Search'),'Group Pickup', FailureHandling.STOP_ON_FAILURE)

		WebUI.delay(2)

		WebUI.sendKeys(findTestObject('Object Repository/Apps/App_CallFlows/input_Search'), Keys.chord(Keys.ENTER))

		WebUI.dragAndDropToObject(findTestObject('Apps/App_CallFlows/div_group_pickup'), findTestObject('Apps/App_CallFlows/div_Click_to_add_number_empty_second'), FailureHandling.STOP_ON_FAILURE)

		WebUI.waitForElementVisible(findTestObject('Apps/App_CallFlows/span_Select_Endpoint'), 5, FailureHandling.STOP_ON_FAILURE)

		WebUI.verifyElementVisible(findTestObject('Apps/App_CallFlows/heading_Setting_group_pick-up'), FailureHandling.STOP_ON_FAILURE)

		WebUI.verifyElementVisible(findTestObject('Apps/App_CallFlows/label_which_endpoint'), FailureHandling.STOP_ON_FAILURE)

		WebUI.enhancedClick(findTestObject('Apps/App_CallFlows/select_endpoint_selector'), FailureHandling.STOP_ON_FAILURE)

		WebUI.delay(2)

		WebUI.enhancedClick(findTestObject('Apps/App_CallFlows/select_option_endpoint',[('endpoint') : groupName]), FailureHandling.STOP_ON_FAILURE)

		WebUI.enhancedClick(findTestObject('Apps/App_CallFlows/button_Save'), FailureHandling.STOP_ON_FAILURE)

		WebUI.delay(2)

		WebUI.verifyEqual(WebUI.getText(findTestObject('Apps/App_CallFlows/div_endpoint_detail'), FailureHandling.STOP_ON_FAILURE), groupName, FailureHandling.STOP_ON_FAILURE)

		WebUI.enhancedClick(findTestObject('Apps/App_CallFlows/div_Save_Changes'), FailureHandling.STOP_ON_FAILURE)

		WebUI.delay(5)
	}
}