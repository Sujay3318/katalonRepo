package release_utility

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
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public class Release_Utility {
	/*
	 * Some custom Keywords to get Releases Kicked off
	 */

	/*
	 * GET Latest meta-kazoo Release Tag
	 */
	@Keyword
	def GET_Latest_Meta_Kazoo_Release_Tag(){
		WS.sendRequest(findTestObject('API/GITHUB/GET_Latest_Meta_Kazoo_Release_Tag'))
	}

	/*
	 * GET Latest meta-kazoo-applications Release Tag
	 */
	@Keyword
	def GET_Latest_Meta_Kazoo_Applications_Release_Tag(){
		WS.sendRequest(findTestObject('API/GITHUB/GET_Latest_Meta_Kazoo_Applications_Release_Tag'))
	}

	/*
	 * GET Latest meta-kazoo-freeswitch Release Tag
	 */
	@Keyword
	def GET_Latest_Meta_Kazoo_Freeswitch_Release_Tag(){
		WS.sendRequest(findTestObject('API/GITHUB/GET_Latest_Meta_Kazoo_Freeswitch_Release_Tag'))
	}

	/*
	 * GET Latest meta-kazoo-kamailio Release Tag
	 */
	@Keyword
	def GET_Latest_Meta_Kazoo_Kamailio_Release_Tag(){
		WS.sendRequest(findTestObject('API/GITHUB/GET_Latest_Meta_Kazoo_Kamailio_Release_Tag'))
	}

	/*
	 * GET Latest meta-monster-ui Release Tag
	 */
	@Keyword
	def GET_Latest_Meta_Monster_Ui_Release_Tag(){
		WS.sendRequest(findTestObject('API/GITHUB/GET_Latest_Meta_Monster_Ui_Release_Tag'))
	}


	/*
	 * Send Upgrade Commencement event to Release Channel 
	 */
	@Keyword
	def sendUpgradeStartWebhookEvent(){
		WS.sendRequest(findTestObject('API/Slack/Send_Upgrade_Start_Webhook_Event_To_Release_Channel'))
	}

	/*
	 * Send Upgrade Completion event to Release Channel
	 */

	@Keyword
	def sendUpgradeCompleteWebhookEvent(){
		WS.sendRequest(findTestObject('API/Slack/Send_Upgrade_Completed_Webhook_Event_To_Release_Channel'))
	}

	/*
	 * Send Upgrade Completion event to Release Channel
	 */

	@Keyword
	def sendNoobNoobUpgradeCompleteWebhookEvent(){
		WS.sendRequest(findTestObject('API/Slack/Send_Upgrade_Completed_Webhook_Event_To_Release_Channel'))
	}

	/*
	 * Send Upgrade Failure event to Release Channel
	 */

	@Keyword
	def sendUpgradeFailureWebhookEvent(){
		WS.sendRequest(findTestObject('API/Slack/Send_Upgrade_Failure_Webhook_Event_To_Release_Channel'))
	}
	/*
	 * Get Last Release from Katalon TestOps Project
	 */

	@Keyword
	def getLastKatalonRelease(){
		def GetLastRelease = WS.sendRequest(findTestObject('API/Katalon/Search_Releases'))
		def GetLastKatalonReleaseId = WS.getElementPropertyValue(GetLastRelease, 'content[0].id')
		def GetLastKatalonReleaseName = WS.getElementPropertyValue(GetLastRelease, 'content[0].name')
		GlobalVariable.KATALON_RELEASE_ID = GetLastKatalonReleaseId
		println("*Last Release in Project*\n" + "\nRelease id:" + GlobalVariable.KATALON_RELEASE_ID + "\nName:" + GetLastKatalonReleaseName + "\n")
	}
	/*
	 *  Close Last Katalon Release from Specified Project in TestOps
	 */

	@Keyword
	def closeLastKatalonRelease(){

		getLastKatalonRelease()
		def CloseRelease = WS.sendRequest(findTestObject('API/Katalon/Close_Release'))
		def ClosedReleaseId = WS.getElementPropertyValue(CloseRelease, 'id')
		def ClosedReleaseName = WS.getElementPropertyValue(CloseRelease, 'name')
		def ReleaseStatus = WS.getElementPropertyValue(CloseRelease, 'closed')
		//assert ReleaseStatus == "true"
		if (ReleaseStatus == true )
		{
			println("*The Following Release is now closed in TestOps*\n" + "\nRelease id:" + ClosedReleaseId + "\nName:" + ClosedReleaseName + "\n")

		}
		else
		{
			println ("Release Status Change Failure. Check Exececution")
		}


	}
	/*
	 * Create a Release under specific Project in Katalon
	 */

	@Keyword
	def createNewKatalonRelease(){
		WS.sendRequest(findTestObject('API/Katalon/Create_Release'))
	}
}