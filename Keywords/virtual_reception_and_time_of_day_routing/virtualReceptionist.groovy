package virtual_reception_and_time_of_day_routing
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
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime
import java.util.Date
import java.util.Locale
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import com.kms.katalon.core.testdata.reader.ExcelFactory as Excel


public class virtualReceptionist {

	/*
	 * Get the current time 
	 * set timezone as America/Los_Angeles
	 * return the current time
	 */
	@Keyword
	def getCurrentTime() {

		DateFormat df = new SimpleDateFormat("HH:mm");
		//dispalying date on PST timezone
		df.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
		String time = df.format(Calendar.instance.getTimeInMillis());
		println("time in PST Timezone : " + time);
		return(time)
	}

	/*
	 * Get the day of week as per date
	 * return day
	 */
	@Keyword
	def getDayOfWeekValue(){

		SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
		Date datetime = new Date();
		sdf.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
		System.out.println("America/Los_Angeles " + sdf.format(datetime));
		return (sdf.format(datetime))
	}

	/*
	 * Get current time and add one hour to it
	 * set timezone as America/Los_Angeles
	 * return time
	 */
	@Keyword
	def addHourToTime(){
		Calendar calendar = Calendar.getInstance()
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		calendar.add(Calendar.HOUR_OF_DAY, 1);
		df.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"))
		String time = df.format(calendar.getTime());
		println(time)
		LocalDateTime ldt = LocalDateTime.now().plusHours(1);
		DateTimeFormatter formmat1 = DateTimeFormatter.ofPattern("HH:MM");
		println(ldt);
		return(time)
	}

	/*
	 * Keyword is used to set Virtual receptionist menu and text to speech in different hours
	 */
	@Keyword
	setupVirtualReceptionist(def hourType ,String sheetName){

		def inputTestData = Excel.getExcelDataWithDefaultSheet(GlobalVariable.DataFileSelection.get('DataFilePath'), GlobalVariable.DataFileSelection.get(
				sheetName), true)

		switch(hourType) {
			case "Open_Hours" :

				WebUI.callTestCase(findTestCase('Apps/App_SmartPBX/Main_Number/Setup_VR_and_Time_of_Day_Routing/Open_hours/SC_Select_open_hours_and_select_virtual_receptionist'),
				[:], FailureHandling.STOP_ON_FAILURE)

				WebUI.delay(15)

				WebUI.callTestCase(findTestCase('Apps/App_SmartPBX/Main_Number/Incoming_call_handling/SC_set_greeting_for_text_speech'),
						[('sheetName') : 'Testdata_VR_setup', ('rowNumber') : 1], FailureHandling.STOP_ON_FAILURE)

				def rowNumber = 1


				for(rowNumber;rowNumber<7;rowNumber++){

					println(rowNumber)
					println(inputTestData.getValue('Function', rowNumber))

					WebUI.waitForElementVisible(findTestObject('Object Repository/Apps/SmartPBX/Main_Number/Virtual_receptionist_Test_objects/link_add_route'), 10, FailureHandling.STOP_ON_FAILURE)
					//click on add route
					WebUI.enhancedClick(findTestObject('Object Repository/Apps/SmartPBX/Main_Number/Virtual_receptionist_Test_objects/link_add_route'), FailureHandling.STOP_ON_FAILURE)

					//select number key from drop down list
					WebUI.selectOptionByValue(findTestObject('Object Repository/Apps/SmartPBX/Main_Number/Virtual_receptionist_Test_objects/Select_Number_Dynamic',[('index') : rowNumber]), inputTestData.getValue('Key', rowNumber+1), true, FailureHandling.STOP_ON_FAILURE)

					WebUI.waitForElementVisible(findTestObject('Object Repository/Apps/SmartPBX/Main_Number/Virtual_receptionist_Test_objects/option_dynamic',[('feature') : inputTestData.getValue('Function', rowNumber+1)]), 15, FailureHandling.STOP_ON_FAILURE)
					//Select the function from drop down list
					WebUI.selectOptionByLabel(findTestObject('Object Repository/Apps/SmartPBX/Main_Number/Virtual_receptionist_Test_objects/div_select_action_dynamic',[('index') : rowNumber]), inputTestData.getValue('Function', rowNumber+1), true)

				}

				WebUI.delay(10)
			//Click on button save
				WebUI.enhancedClick(findTestObject('Object Repository/Standard_Objects/Common_Component_Objects/button_save'), FailureHandling.STOP_ON_FAILURE)

			//check the element
				WebUI.waitForElementPresent(findTestObject('Object Repository/Apps/SmartPBX/Main_Number/link_open_hours'), 10, FailureHandling.STOP_ON_FAILURE)

				WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/Main_Number/button_Save_Changes_Incoming_Call_handling'), 10, FailureHandling.STOP_ON_FAILURE)



				break;

			case "Lunch_Hours":

				WebUI.callTestCase(findTestCase('Apps/App_SmartPBX/Main_Number/Setup_VR_and_Time_of_Day_Routing/Lunch_hours/SC_select_lunch_hours_and_setup_virtual_receptionist'),
				[:], FailureHandling.STOP_ON_FAILURE)

				WebUI.delay(15)

				WebUI.callTestCase(findTestCase('Apps/App_SmartPBX/Main_Number/Incoming_call_handling/SC_set_greeting_for_text_speech'),
						[('sheetName') : 'Testdata_VR_setup', ('rowNumber') : 10], FailureHandling.STOP_ON_FAILURE)

				def rowNumber = 10

				def index = 1

				for(rowNumber;rowNumber<12;rowNumber++){

					println(index)
					println(inputTestData.getValue('Function', rowNumber))

					WebUI.waitForElementVisible(findTestObject('Object Repository/Apps/SmartPBX/Main_Number/Virtual_receptionist_Test_objects/link_add_route'), 10, FailureHandling.STOP_ON_FAILURE)
					//click on add route
					WebUI.enhancedClick(findTestObject('Object Repository/Apps/SmartPBX/Main_Number/Virtual_receptionist_Test_objects/link_add_route'), FailureHandling.STOP_ON_FAILURE)

					//select number key from drop down list
					WebUI.selectOptionByValue(findTestObject('Object Repository/Apps/SmartPBX/Main_Number/Virtual_receptionist_Test_objects/Select_Number_Dynamic',[('index') : index]), inputTestData.getValue('Key', rowNumber), true, FailureHandling.STOP_ON_FAILURE)

					WebUI.waitForElementVisible(findTestObject('Object Repository/Apps/SmartPBX/Main_Number/Virtual_receptionist_Test_objects/option_dynamic',[('feature') : inputTestData.getValue('Function', rowNumber)]), 15, FailureHandling.STOP_ON_FAILURE)
					//Select the function from drop down list
					WebUI.selectOptionByLabel(findTestObject('Object Repository/Apps/SmartPBX/Main_Number/Virtual_receptionist_Test_objects/div_select_action_dynamic',[('index') : index]), inputTestData.getValue('Function', rowNumber), true)

					index++
				}

				WebUI.delay(10)
			//Click on button save
				WebUI.enhancedClick(findTestObject('Object Repository/Standard_Objects/Common_Component_Objects/button_save'), FailureHandling.STOP_ON_FAILURE)

			//check the element
				WebUI.waitForElementPresent(findTestObject('Object Repository/Apps/SmartPBX/Main_Number/link_lunch_hours'), 10, FailureHandling.STOP_ON_FAILURE)

				WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/Main_Number/button_Save_Changes_Incoming_Call_handling'), 10, FailureHandling.STOP_ON_FAILURE)


				break;

			case "After_Hours":

				WebUI.callTestCase(findTestCase('Apps/App_SmartPBX/Main_Number/Setup_VR_and_Time_of_Day_Routing/After_hours/SC_select_after_hours_and_virtual_receptionist'),
				[:], FailureHandling.STOP_ON_FAILURE)

				WebUI.delay(15)

				WebUI.callTestCase(findTestCase('Apps/App_SmartPBX/Main_Number/Incoming_call_handling/SC_set_greeting_for_text_speech'),
						[('sheetName') : 'Testdata_VR_setup', ('rowNumber') : 8], FailureHandling.STOP_ON_FAILURE)

				def rowNumber = 8

				def index = 1
				for(rowNumber;rowNumber<10;rowNumber++){

					println(index)
					println(inputTestData.getValue('Function', rowNumber))

					WebUI.waitForElementVisible(findTestObject('Object Repository/Apps/SmartPBX/Main_Number/Virtual_receptionist_Test_objects/link_add_route'), 10, FailureHandling.STOP_ON_FAILURE)
					//click on add route
					WebUI.enhancedClick(findTestObject('Object Repository/Apps/SmartPBX/Main_Number/Virtual_receptionist_Test_objects/link_add_route'), FailureHandling.STOP_ON_FAILURE)

					//select number key from drop down list
					WebUI.selectOptionByValue(findTestObject('Object Repository/Apps/SmartPBX/Main_Number/Virtual_receptionist_Test_objects/Select_Number_Dynamic',[('index') : index]), inputTestData.getValue('Key', rowNumber), true, FailureHandling.STOP_ON_FAILURE)

					WebUI.waitForElementVisible(findTestObject('Object Repository/Apps/SmartPBX/Main_Number/Virtual_receptionist_Test_objects/option_dynamic',[('feature') : inputTestData.getValue('Function', rowNumber)]), 15, FailureHandling.STOP_ON_FAILURE)
					//Select the function from drop down list
					WebUI.selectOptionByLabel(findTestObject('Object Repository/Apps/SmartPBX/Main_Number/Virtual_receptionist_Test_objects/div_select_action_dynamic',[('index') : index]), inputTestData.getValue('Function', rowNumber), true)

					index++
				}

			//Click on button save
				WebUI.enhancedClick(findTestObject('Object Repository/Standard_Objects/Common_Component_Objects/button_save'), FailureHandling.STOP_ON_FAILURE)

			//check the element
				WebUI.waitForElementPresent(findTestObject('Object Repository/Apps/SmartPBX/Main_Number/link_after_hours'), 10, FailureHandling.STOP_ON_FAILURE)

				WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/Main_Number/button_Save_Changes_Incoming_Call_handling'), 10, FailureHandling.STOP_ON_FAILURE)


				break;

			case "Holidays" :

				WebUI.callTestCase(findTestCase('Apps/App_SmartPBX/Main_Number/Setup_VR_and_Time_of_Day_Routing/Holidays/SC_select_holidays_and_virtual_receptionist'),
				[:], FailureHandling.STOP_ON_FAILURE)


				WebUI.delay(15)

				WebUI.callTestCase(findTestCase('Apps/App_SmartPBX/Main_Number/Incoming_call_handling/SC_set_greeting_for_text_speech'),
						[('sheetName') : 'Testdata_VR_setup', ('rowNumber') : 12], FailureHandling.STOP_ON_FAILURE)

				println(inputTestData.getValue('Function', 12))

				WebUI.waitForElementVisible(findTestObject('Object Repository/Apps/SmartPBX/Main_Number/Virtual_receptionist_Test_objects/link_add_route'), 10, FailureHandling.STOP_ON_FAILURE)
			//click on add route
				WebUI.enhancedClick(findTestObject('Object Repository/Apps/SmartPBX/Main_Number/Virtual_receptionist_Test_objects/link_add_route'), FailureHandling.STOP_ON_FAILURE)

			//select number key from drop down list
				WebUI.selectOptionByValue(findTestObject('Object Repository/Apps/SmartPBX/Main_Number/Virtual_receptionist_Test_objects/Select_Number_Dynamic',[('index') : 1]), inputTestData.getValue('Key', 12), true, FailureHandling.STOP_ON_FAILURE)

				WebUI.waitForElementVisible(findTestObject('Object Repository/Apps/SmartPBX/Main_Number/Virtual_receptionist_Test_objects/option_dynamic',[('feature') : inputTestData.getValue('Function', 12)]), 15, FailureHandling.STOP_ON_FAILURE)
			//Select the function from drop down list
				WebUI.selectOptionByLabel(findTestObject('Object Repository/Apps/SmartPBX/Main_Number/Virtual_receptionist_Test_objects/div_select_action_dynamic',[('index') : 1]), inputTestData.getValue('Function', 12), true)

				WebUI.delay(10)
			//Click on button save
				WebUI.enhancedClick(findTestObject('Object Repository/Standard_Objects/Common_Component_Objects/button_save'), FailureHandling.STOP_ON_FAILURE)

			//check the element
				WebUI.waitForElementPresent(findTestObject('Object Repository/Apps/SmartPBX/Main_Number/link_holiday'), 10, FailureHandling.STOP_ON_FAILURE)

				WebUI.waitForElementVisible(findTestObject('Apps/SmartPBX/Main_Number/button_Save_Changes_Incoming_Call_handling'), 10, FailureHandling.STOP_ON_FAILURE)

			//Click on button save
			//WebUI.enhancedClick(findTestObject('Apps/SmartPBX/Main_Number/button_Save_Changes_Incoming_Call_handling'), FailureHandling.STOP_ON_FAILURE)




				break



		}
	}
}







