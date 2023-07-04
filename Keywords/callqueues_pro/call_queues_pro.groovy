package callqueues_pro
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling

import internal.GlobalVariable

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import com.kms.katalon.core.webui.driver.DriverFactory

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty

import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil

import com.kms.katalon.core.webui.exception.WebElementNotFoundException


class call_queues_pro {

	/**
	 * Click "Begin Session" button on CC Basic/Pro Landing Page
	 */

	@Keyword
	def click_begin_session() {
		try {
			WebElement element = WebUI.findWebElement('Apps/App_CallCenter_Pro/Agent_State_Changes/button_BeginSession');
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
			WebElement element = WebUI.findWebElement('Apps/App_CallCenter_Pro/Agent_State_Changes/a_End Session');
			KeywordUtil.logInfo("Clicking 'End Session' button")
			element.click()
			KeywordUtil.markPassed("'End Session' has been clicked")
		} catch (WebElementNotFoundException e) {
			KeywordUtil.markFailed("'End Session' button not found")
		} catch (Exception e) {
			KeywordUtil.markFailed("Fail to click on 'End Session'")
		}
	}
}