package verificationCallLogs

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
import static org.junit.Assert.*
import internal.GlobalVariable

public class callLogs {

	/*
	 * Captures Call-ID value from Monster UI SmartPBX > Call Logs
	 * Pass CDR string from SmartPBX->Call Logs
	 */
	@Keyword
	def get_pbx_logs_call_id(String cdr) {
		def read_lines = cdr.readLines()
		def result = read_lines.findAll { it.contains('"call_id":') }
		return result
	}

	/*
	 * Verifying to ,from and call_direction from call logs
	 */
	@Keyword
	def verifyCallLogsParameters(String from,String to,String callDirection){

		String direction = WebUI.getAttribute(findTestObject('Object Repository/Apps/SmartPBX/callLogs/span_call_direction'), 'class', FailureHandling.STOP_ON_FAILURE)
		println(direction)
		if(direction!=null){
			if(direction.contains('orange')){
				assertEquals(callDirection, 'outbound')
			}
			else if(direction.contains('green')){
				println(callDirection)
				assertEquals(callDirection, 'inbound')
			}
			String fromValue= WebUI.getText(findTestObject('Object Repository/Apps/SmartPBX/callLogs/span_from'), FailureHandling.STOP_ON_FAILURE)
			assertEquals(from, fromValue.replaceAll('\\s', ''))
			println("from is verified and value is : "+fromValue)

			String toValue = WebUI.getText(findTestObject('Object Repository/Apps/SmartPBX/callLogs/span_to'), FailureHandling.STOP_ON_FAILURE)
			assertEquals(to, toValue.replaceAll('\\s', ''))

			println("to is verified and value is : "+toValue)
		}
		else{
			assertFalse()
		}
	}

	/*
	 * Verifying to ,from and call_direction from call logs
	 */
	@Keyword
	def verifyFaxInfo(String responseLeg,String senderFaxNumber,String receiverFaxNumber){
		List<String> faxData = new ArrayList<>()
		faxData = GlobalVariable.FaxParameterList
		for(String i : faxData){
			println(i)
			assertTrue(responseLeg.contains(i))
		}

		if(responseLeg.contains('"call_direction": "inbound"')){
			println("inbound leg")
			println('"fax_identity_number": "'+receiverFaxNumber+'"')
			assertTrue(responseLeg.contains('"fax_identity_number": "'+receiverFaxNumber+'"'))
			println('"fax_local_station_id": "'+receiverFaxNumber+'"')
			assertTrue(responseLeg.contains('"fax_local_station_id": "'+receiverFaxNumber+'"'))
			println('"fax_remote_station_id": "'+senderFaxNumber+'"')
			assertTrue(responseLeg.contains('"fax_remote_station_id": "'+senderFaxNumber+'"'))
		}
		else if(responseLeg.contains('"call_direction": "outbound"')){
			println("outbound leg")
			println('"fax_identity_number": "'+senderFaxNumber+'"')
			assertTrue(responseLeg.contains('"fax_identity_number": "'+senderFaxNumber+'"'))
			println('"fax_local_station_id": "'+senderFaxNumber+'"')
			assertTrue(responseLeg.contains('"fax_local_station_id": "'+senderFaxNumber+'"'))
			println('"fax_remote_station_id": "'+receiverFaxNumber+'"')
			assertTrue(responseLeg.contains('"fax_remote_station_id": "'+receiverFaxNumber+'"'))
		}
	}
}
