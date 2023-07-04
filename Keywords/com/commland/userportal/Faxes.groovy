package com.commland.userportal
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable
import org.openqa.selenium.By
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.util.KeywordUtil
import static org.junit.Assert.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.interactions.Actions as Actions
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor

public class Faxes {

	/*
	 * @Param {rowNumber} row number of a Fax
	 * @Param {toNumber} To Phone Number of Faxbox
	 * @Param {date } Date of a Fax
	 * @Param {Number of Pages} Attachment count of pages 
	 */
	@Keyword
	def verifyFaxinFaxbox(Integer rowNumber,String expectedStatus,String expectedToNumber,String expectedDate,Integer expectedNoOfPages){
		println('Actual Status of a Fax:' + WebUI.getText(findTestObject('Comm.land/App_User_Portal/Faxes/div_fax_status', [('row') : rowNumber]),
		FailureHandling.STOP_ON_FAILURE))

		WebUI.verifyEqual(WebUI.getText(findTestObject('Comm.land/App_User_Portal/Faxes/div_fax_status' ,[('row') : rowNumber]), FailureHandling.STOP_ON_FAILURE), expectedStatus)

		if(expectedToNumber.contains("+1")) {
			println('Actual To Value of Fax:' + WebUI.getText(findTestObject('Comm.land/App_User_Portal/Faxes/To_CalleeId', [('row') : rowNumber]),
			FailureHandling.STOP_ON_FAILURE))

			String toValue = WebUI.getText(findTestObject('Comm.land/App_User_Portal/Faxes/To_CalleeId',[('row') : rowNumber]), FailureHandling.STOP_ON_FAILURE)

			toValue = toValue.replaceAll('\\s', '')

			toValue = toValue.replaceAll('-', '')

			println('Actual To value :' + toValue)

			assertEquals(expectedToNumber, toValue)
		}else {
			println('Actual To Value of Fax:' + WebUI.getText(findTestObject('Comm.land/App_User_Portal/Faxes/To_CalleeName', [('row') : rowNumber]),
			FailureHandling.STOP_ON_FAILURE))

			String toValue = WebUI.getText(findTestObject('Comm.land/App_User_Portal/Faxes/To_CalleeName',[('row') : rowNumber]), FailureHandling.STOP_ON_FAILURE)

			assertEquals(expectedToNumber, toValue)
		}

		println('Date of Fax:' + WebUI.getText(findTestObject('Comm.land/App_User_Portal/Faxes/Date_value', [('row') : rowNumber]),
		FailureHandling.STOP_ON_FAILURE))

		WebUI.verifyEqual(WebUI.getText(findTestObject('Comm.land/App_User_Portal/Faxes/Date_value',[('row') : rowNumber]), FailureHandling.STOP_ON_FAILURE), expectedDate, FailureHandling.STOP_ON_FAILURE)

		println("Time of a fax is :" + WebUI.getText(findTestObject('Comm.land/App_User_Portal/Faxes/Time_value',[('row') : rowNumber]), FailureHandling.STOP_ON_FAILURE))

		println("Number of Pages in Fax attachement are:" + WebUI.getText(findTestObject('Comm.land/App_User_Portal/Faxes/attachment_fax_Pages_value',[('row') : rowNumber]), FailureHandling.STOP_ON_FAILURE))
	}


	/*
	 * @Param {order} Sorting order of Faxes
	 * @Param {currentActualDate} date of ith fax
	 * @Param {currentActualTime } time of a ith  Fax
	 * @Param {nextActualDate} date of i+1 th fax 
	 * @Param {nextActualTime} time of i+1 th fax
	 */
	@Keyword
	def verifyDateTimeSorting(String order, String currentActualDate , String currentActualTime,String nextActualDate,String nextActualTime){
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss")

		String currentActualDateTime = currentActualDate + " "+ currentActualTime

		String nextActualDateTime = nextActualDate + " "+ nextActualTime

		Date date1 = sdf.parse(currentActualDateTime)

		Date date2 = sdf.parse(nextActualDateTime)


		switch(order){

			case "Ascending" :

				assertTrue(date1.before(date2))

				break

			case "Descending" :

				assertTrue(date1.after(date2))

				break
		}
	}
}