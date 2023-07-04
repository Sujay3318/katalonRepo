package setup

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
import java.util.concurrent.*
import java.io.BufferedReader.*

public class ConsoleInput {

	@Keyword
	def setThreadSleep(Number timeInMills){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in))
		def myReader =  [
			call: {
				String input=''
				try {
					while (!br.ready()) {
						Thread.yield()
						Thread.sleep(timeInMills)
						Thread.yield();
					}
					input = br.readLine()
				} catch (IOException e) {
					return ''
				}
				return input
			}
		] as Callable
	}


	@Keyword
	def setTimeout(Number count, Number timeInMills){
		def unit = TimeUnit.SECONDS

		def sleepObject = (setThreadSleep(timeInMills))

		ExecutorService ex = Executors.newSingleThreadExecutor()

		def input = ''
		Number input1 = 0

		try {
			Future<String> result = ex.submit(sleepObject)
			try {
				print("Enter count of accounts needed: ")
				input = result.get(timeInMills, unit)
				input1= Integer.parseInt(input)
			} catch (ExecutionException e) {
				e.getCause().printStackTrace()
			} catch (TimeoutException e) {
				result.cancel(true)
			}
		} finally {
			ex.shutdownNow()
		}
		if(input.isEmpty() || input.equals('') || input.equals(null)){
			return count
		}else{
			return input1
		}
	}

	@Keyword
	def setUserCount(Number count, Number timeInMills){
		def unit = TimeUnit.SECONDS

		def sleepObject = (setThreadSleep(timeInMills))

		ExecutorService ex = Executors.newSingleThreadExecutor()

		def input = ''
		Number input1 = 0

		try {
			Future<String> result = ex.submit(sleepObject)
			try {
				input = result.get(timeInMills, unit)
				input1= Integer.parseInt(input)
			} catch (ExecutionException e) {
				e.getCause().printStackTrace()
			} catch (TimeoutException e) {
				result.cancel(true)
			}
		} finally {
			ex.shutdownNow()
		}
		if(input.isEmpty() || input.equals('') || input.equals(null)){
			return count
		}else{
			return input1
		}
	}
}
