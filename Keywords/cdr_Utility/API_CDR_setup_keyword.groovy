package cdr_Utility

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
import groovy.json.JsonSlurper as JsonSlurper
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.testobject.RequestObject as RequestObject
import com.kms.katalon.core.testobject.ResponseObject as ResponseObject
import com.kms.katalon.core.testobject.impl.HttpTextBodyContent as HttpTextBodyContent
import java.lang.String

public class API_CDR_setup_keyword {
	/*
	 * Create 6 digit random number to set password
	 */
	@Keyword
	def  getRandomNumberString() {
		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		// this will convert any number sequence into 6 character.
		return String.format("%06d", number);
	}
	/*
	 * Creating new user
	 * Provide unique username
	 * Runtime storing username,password,firstname and user_id in GlobalVariable
	 */
	@Keyword
	def new_user_creation(String uname,String email,String fname,String lname,String pswd,String account_id ){
		def request = ((findTestObject('API/users/user.put', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
			, ('auth_token') : GlobalVariable.auth_token])) as RequestObject)
		String body = """{"data": {"username": "${uname}","email": "${email}","first_name": "${fname}","last_name": "${lname}","password": "${pswd}","priv_level": "user","flags": [],"vm_to_email_enabled": true,"send_email_on_creation": true,"ui_help":{ "voip": {"showUsersWalkthrough": false,"showStrategySecondWalkthrough": false,"showGroupsWalkthrough": false,"showDashboardWalkthrough": false,"showStrategyFirstWalkthrough": false},"myaccount": {"showfirstUseWalkthrough": false}}}}"""
		println(body)
		request.setBodyContent(new HttpTextBodyContent(body))
		WS.sendRequest(request)
	}

	/*
	 * Create new device
	 * Mapping device to user using user_id
	 */
	@Keyword
	def new_device_creation(String devicename,String device_type,String sip_username,String sip_password,String user_id, String account_id) {
		def request = ((findTestObject('API/devices/device.put', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
			, ('auth_token') : GlobalVariable.auth_token])) as RequestObject)
		String body ="""{"data": {"sip": {"password" : "${sip_password}","username": "${sip_username}","expire_seconds" : 300,"invite_format": "contact","method": "password"}, "device_type": "${device_type}","enabled": true,"suppress_unregister_notifications": false,"name": "${devicename}","contact_list": {},"ringtones": {},"owner_id": "${user_id}"}}"""
		println(body)
		request.setBodyContent(new HttpTextBodyContent(body))
		WS.sendRequest(request)
	}

	/*
	 * convert any user to admin module
	 */

	@Keyword
	def update_user_to_admin(){
		def request = ((findTestObject('API/Features/users_feature_faxbox.patch', [('base_url') : GlobalVariable.base_url, ('account_id') : GlobalVariable.sub_account_details.get("account_id")
			, ('auth_token') : GlobalVariable.auth_token, ('user_id') : GlobalVariable.user_id])) as RequestObject)
		def builder = new groovy.json.JsonBuilder()
		builder.data(features  :["vm_to_email"],username : GlobalVariable.fname,priv_level : "admin")
		String s=builder.toString()
		println(s)
		request.setBodyContent(new HttpTextBodyContent(s))
		//println(request)
		WS.sendRequest(request)
	}


	/*
	 * Assign different app to account
	 * Provide unique secific app_id in Profile having global variable "APP_ID"
	 */
	@Keyword
	def assign_apps_to_account(String account_id) {
		def request = ((findTestObject('API/APPS/app.put', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id, ('auth_token') : GlobalVariable.auth_token,('webrtc_app_id') : GlobalVariable.webrtc_app_id])) as RequestObject)
		def builder = new groovy.json.JsonBuilder()
		builder.data(name  : "Webrtcapp", allowed_users: "all")
		String s=builder.toString()
		println(s)
		request.setBodyContent(new HttpTextBodyContent(s))
		//println(request)
		WS.sendRequest(request)
	}
	/*
	 * Mapping phone_number/extension to user
	 */
	@Keyword
	def update_phonenumber_extension(String extension,String phoneNumber,String user_id,String account_id){
		def request = ((findTestObject('API/callflows/callflows.create', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
			, ('auth_token') : GlobalVariable.auth_token, ('user_id') : user_id])) as RequestObject)
		String body = """{"data": {"contact_list": {"exclude": false},"flow": {"children": {},"data": {"can_call_self": false,"timeout": 20,"id": "${user_id}","delay": 0,"strategy": "simultaneous"},"module":"user"},"numbers": ["${extension}","${phoneNumber}"],"owner_id": "${user_id}","type": "mainUserCallflow","ui_metadata": {"ui": "monster-ui","origin": "voip"},"patterns": [],"metadata": {"${user_id}": {"pvt_type": "user"}}}}"""
		println(body)
		request.setBodyContent(new HttpTextBodyContent(body))
		//println(request)
		WS.sendRequest(request)

	}
	@Keyword
	def list_of_2users(){
		List<HashMap> listOfUserDetailsMaps = new ArrayList()
		listOfUserDetailsMaps.add(GlobalVariable.user1_creation_details)
		listOfUserDetailsMaps.add(GlobalVariable.user2_creation_details)
		return listOfUserDetailsMaps
	}
	@Keyword
	def list_of_3users(){
		List<HashMap> listOfUserDetailsMaps = new ArrayList()
		listOfUserDetailsMaps.add(GlobalVariable.user1_creation_details)
		listOfUserDetailsMaps.add(GlobalVariable.user2_creation_details)
		listOfUserDetailsMaps.add(GlobalVariable.user3_creation_details)
		return listOfUserDetailsMaps
	}

	@Keyword
	def user_lists(){
		List<HashMap> listOfUserDetailsMaps = new ArrayList()
		listOfUserDetailsMaps.add(GlobalVariable.user1_creation_details)
		listOfUserDetailsMaps.add(GlobalVariable.user2_creation_details)
		listOfUserDetailsMaps.add(GlobalVariable.user3_creation_details)
		return listOfUserDetailsMaps
	}

	/*
	 * Creating new VMbox
	 * Provide unique name
	 * Mapping VMbox with user
	 */
	@Keyword
	def new_VMbox_creation(String account_id,String user_id,String extension,String name){
		def request = ((findTestObject('API/Voicemailbox/vmbox.put', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
			, ('auth_token') : GlobalVariable.auth_token, ('user_id') : user_id])) as RequestObject)
		String body = """{"data":{"owner_id":"${user_id}","mailbox":"${extension}","name":"${name}","ui_metadata":{"version":"4.3-91","ui":"monster-ui","origin":"voip"},"check_if_owner":true,"delete_after_notify":false,"is_setup":false,"is_voicemail_ff_rw_enabled":false,"media":{},"media_extension":"mp3","not_configurable":false,"notify_email_addresses":[],"oldest_message_first":false,"require_pin":false,"save_after_notify":false,"seek_duration_ms":10000,"skip_envelope":false,"skip_greeting":false,"skip_instructions":false,"transcribe":false}}"""
		println(body)
		request.setBodyContent(new HttpTextBodyContent(body))
		//println(request)
		WS.sendRequest(request)

	}
	/*
	 * Mapping VMbox with callflow 
	 * Enable VMbox feature
	 */
	@Keyword
	def enable_voicemail_box_feature(String extension,String phoneNumber,String user_id,String account_id,String vmbox_id){
		def request = ((findTestObject('API/callflows/callflows_vmbox.create', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
			, ('auth_token') : GlobalVariable.auth_token])) as RequestObject)
		String body = """{"data":{"contact_list":{"exclude":false},"flow":{"children":{"_":{"children":{},"data":{"id":${vmbox_id},"action":"compose","callerid_match_login":false,"interdigit_timeout":2000,"max_message_length":500,"single_mailbox_login":false},"module":"voicemail"}},"data":{"can_call_self":false,"timeout":20,"id":${user_id},"delay":0,"strategy":"simultaneous"},"module":"user"},"name":"agent2000 qa1 SmartPBX's Callflow","numbers":[${extension},${phoneNumber}],
"owner_id":${user_id},"type":"mainUserCallflow","ui_metadata":{"version":"4.3-91","ui":"monster-ui","origin":"voip"},"patterns":[]}} """
		println(body)
		request.setBodyContent(new HttpTextBodyContent(body))
		//println(request)
		WS.sendRequest(request)

	}

	/*
	 * Enable DND feature
	 */
	@Keyword
	def enable_DND_feature(String account_id,String user_id){
		def request = ((findTestObject('API/Features/users_feature_DND.patch', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
			, ('auth_token') : GlobalVariable.auth_token, ('user_id') : user_id])) as RequestObject)
		String body ="""{"data":{"do_not_disturb": {"enabled": true}}}"""
		println(body)
		request.setBodyContent(new HttpTextBodyContent(body))
		//println(request)
		WS.sendRequest(request)
	}
	/*
	 * Enable call forwarding 
	 */
	@Keyword
	def enable_call_forwarding_feature(String account_id,String user_id,String phone_number,Boolean caller_id){
		def request = ((findTestObject('API/Features/users_feature_call_forwarding.patch', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
			, ('auth_token') : GlobalVariable.auth_token, ('user_id') : user_id])) as RequestObject)
		String body ="""{"data":{"call_forward": {"number": "${phone_number}","require_keypress": false,"direct_calls_only": true,"keep_caller_id": ${caller_id},"enabled": true,"failover": false,"ignore_early_media": true,"substitute": true}}}"""
		println(body)
		request.setBodyContent(new HttpTextBodyContent(body))
		//println(request)
		WS.sendRequest(request)

	}
	/*
	 * patching callflow
	 * Enable VMbox feature
	 */
	@Keyword
	def enable_VMbox_feature(String account_id,String user_id,String callflow_id,String vmbox_id){
		def request = ((findTestObject('API/callflows/callflows.patch', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
			, ('auth_token') : GlobalVariable.auth_token, ('user_id') : user_id,('callflow_id') : callflow_id])) as RequestObject)
		String body = """{"data": {"contact_list": {"exclude": false},"flow": {"children":{"_":{"children":{},"data":{"id":"${vmbox_id}","action":"compose","callerid_match_login":false,"interdigit_timeout":2000,"max_message_length":500,"single_mailbox_login":false},"module":"voicemail"}}}}}"""
		println(body)
		request.setBodyContent(new HttpTextBodyContent(body))
		//println(request)
		WS.sendRequest(request)

	}

	/*
	 * create new faxbox
	 */
	@Keyword
	def create_new_faxbox(String account_id,String user_id,String fax_number,String email,String name){
		def request = ((findTestObject('API/faxboxes/faxboxes.create', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
			, ('auth_token') : GlobalVariable.auth_token, ('user_id') : user_id])) as RequestObject)
		def faxbox_name = name + "_faxbox"
		def printer = name + "_printer"
		String body = """{"data": {"name": "${faxbox_name}" ,"caller_name": "${name}","caller_id": "${fax_number}","owner_id" : "${user_id}","fax_header": "${printer}","fax_identity": "${fax_number}","retries": 3,"notifications": {"inbound": {"email": {"send_to": ["${email}"]}},"outbound": {"email": {"send_to": [ "${email}"]}}}}}"""
		println(body)
		request.setBodyContent(new HttpTextBodyContent(body))
		//println(request)
		WS.sendRequest(request)

	}
	/*
	 * Enable the faxbox feature 
	 * mapping fax_number to faxbox
	 */
	@Keyword
	def mapping_faxnumber_to_faxbox(String account_id,String user_id,String fax_number,String faxbox_id){
		def request = ((findTestObject('API/callflows/callflows_faxbox.create', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
			, ('auth_token') : GlobalVariable.auth_token, ('user_id') : user_id])) as RequestObject)
		String body = """{"data": {"type": "faxing","owner_id": "${user_id}","numbers": ["${fax_number}"],"flow": {"module": "faxbox","children": {},"data": {"id": "${faxbox_id}"}},"ui_metadata": {"version": "4.3-91","ui": "monster-ui","origin": "voip"},"metadata": {"${faxbox_id}": {"name": "test qa1's Faxbox",
"pvt_type": "faxbox"}}}}"""
		println(body)
		request.setBodyContent(new HttpTextBodyContent(body))
		//println(request)
		WS.sendRequest(request)

	}

	@Keyword
	def enable_faxbox_feature(String account_id,String user_id){
		def request = ((findTestObject('API/Features/users_feature_faxbox.patch', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
			, ('auth_token') : GlobalVariable.auth_token, ('user_id') : user_id])) as RequestObject)
		String body = """{"data":{"smartpbx": {"faxing": {"enabled": true}}}}"""
		println(body)
		request.setBodyContent(new HttpTextBodyContent(body))
		//println(request)
		WS.sendRequest(request)

	}
	/*
	 * Enable caller_id feature for user
	 * assign presence id to user 
	 */
	@Keyword
	def enable_caller_id_feature(String account_id,String user_id,String extension,String phone_number,String name){
		def request = ((findTestObject('API/Features/users_feature_caller_id_presence_id.patch', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
			, ('auth_token') : GlobalVariable.auth_token, ('user_id') : user_id])) as RequestObject)
		String body = """{"data":{"presence_id":"${extension}","caller_id":{"internal":{"number":"${extension}","name":"${name}"},"external":{"number":"${phone_number}"}}}}"""
		println(body)
		request.setBodyContent(new HttpTextBodyContent(body))
		//println(request)
		WS.sendRequest(request)

	}


	/*
	 * set carrier module as local
	 * using this number for offnet call
	 */
	@Keyword
	def set_carrier_module(String account_id,String phone_number){
		def request = ((findTestObject('API/phone_numbers/phone_numbers.carrier_setup', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
			, ('auth_token') : GlobalVariable.auth_token, ('phone_number') : phone_number])) as RequestObject)
		String body = """{"data":{"carrier_module":"local","carrier_name":"local"}}"""
		println(body)
		request.setBodyContent(new HttpTextBodyContent(body))
		WS.sendRequest(request)
	}

	/*
	 * set limit for account
	 */
	@Keyword
	def set_account_limits(String account_id){
		def request = ((findTestObject('API/accounts/account.limit', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
			, ('auth_token') : GlobalVariable.auth_token])) as RequestObject)
		String body = """{"data": {"allow_postpay": false,"allow_prepay": false,"authz_resource_types": [],"burst_trunks": 0,"enabled": true,"inbound_trunks": 0,"max_postpay_ammount": 0,"outbound_trunks": 0,"reserve_ammount": 0,"soft_limit_inbound": false,"soft_limit_outbound": false,"twoway_trunks": 10,"id": "limits"}}"""
		println(body)
		request.setBodyContent(new HttpTextBodyContent(body))
		WS.sendRequest(request)
	}
	/*
	 * update callflow to enable offnet callflow
	 */
	def enable_off_net_callflow(String account_id){
		def request = ((findTestObject('API/callflows/callflows.create', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
			, ('auth_token') : GlobalVariable.auth_token])) as RequestObject)
		String body = """{"data": {"numbers": ["no_match"],"flow": {"children": {},"data": {"caller_id_type": "external","ignore_early_media": false,"outbound_flags": [],"use_local_resources": true},"module":"offnet"}}}"""
		println(body)
		request.setBodyContent(new HttpTextBodyContent(body))
		//println(request)
		WS.sendRequest(request)

	}

	/*
	 * Assign conferences app to account
	 * Provide unique specific app_id in Profile having global variable "conferences_app_id"
	 */
	@Keyword
	def assign_conferences_apps_to_account(String account_id) {
		def request = ((findTestObject('API/APPS/app_conference.put', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id, ('auth_token') : GlobalVariable.auth_token,('conferences_app_id') : GlobalVariable.conferences_app_id])) as RequestObject)
		def builder = new groovy.json.JsonBuilder()
		builder.data(name  : "Conferences", allowed_users: "all")
		String s=builder.toString()
		request.setBodyContent(new HttpTextBodyContent(s))
		//println(request)
		WS.sendRequest(request)
	}

	/*
	 * Create new conference
	 * Provide unique conference pin and number
	 */
	@Keyword
	def create_new_conferences(String account_id,String owner_id,String number) {
		def request = ((findTestObject('API/conferences/conferences.create', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id, ('auth_token') : GlobalVariable.auth_token,('conferences_app_id') : GlobalVariable.conferences_app_id])) as RequestObject)
		String body = """{"data":{"name":"test","owner_id":"${owner_id}","member":{"join_muted":false,"join_deaf":false,"pins":["${number}"]},"play_entry_tone":true,"play_exit_tone":true,"moderator":{"pins":["${number}"]},"conference_numbers":["${number}"],"ui_flags":{"type":"conferenceApp"},"ui_metadata":{"version":"4.3-116","ui":"monster-ui","origin":"conferences"}}}"""
		println(body)
		request.setBodyContent(new HttpTextBodyContent(body))
		WS.sendRequest(request)
	}

	/*
	 * set conference number
	 * Provide unique conference number
	 */
	@Keyword
	def set_conferences_number(String account_id,String number) {
		def request = ((findTestObject('API/conferences/callflows_conferences.create', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id, ('auth_token') : GlobalVariable.auth_token,('conferences_app_id') : GlobalVariable.conferences_app_id])) as RequestObject)
		String body = """{"data":{"contact_list":{"exclude":false},"numbers":["${number}"],"name":"MainConference","type":"conference","flow":{"children":{},"data":{},"module":"conference"},"ui_metadata":{"version":"4.3-116","ui":"monster-ui","origin":"voip"},"patterns":[]}}"""
		println(body)
		request.setBodyContent(new HttpTextBodyContent(body))
		WS.sendRequest(request)
	}

	/*
	 * disable trunk limit for account
	 */
	@Keyword
	def disable_account_limits(String account_id){
		def request = ((findTestObject('API/accounts/account.limit_disable', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
			, ('auth_token') : GlobalVariable.auth_token])) as RequestObject)
		String body = """{"data": {"id": "limits", "allow_postpay": false, "max_postpay_ammount": 0}}"""
		println(body)
		request.setBodyContent(new HttpTextBodyContent(body))
		WS.sendRequest(request)
	}
	/*
	 * Debit amount to an account
	 */
	@Keyword
	def debit_account(String account_id,String debit_amount){
		def request = ((findTestObject('API/accounts/ledger.debit', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
			, ('auth_token') : GlobalVariable.auth_token])) as RequestObject)
		String body = """{"data":{"amount": ${debit_amount},"source":{"service":"adjustments","id":""},"usage":{"type":"debit","quantity":0,"unit":"USD"},"description":"Credit removed by adminstrator","metadata":{"ui_request":true,"automatic_description":true}}}"""
		request.setBodyContent(new HttpTextBodyContent(body))
		WS.sendRequest(request)
	}


	/*
	 * Add Credit Amount to an Account
	 */
	@Keyword
	def credit_account(String account_id,String credit_amount){
		def request = ((findTestObject('API/accounts/ledger.credit', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
			, ('auth_token') : GlobalVariable.auth_token])) as RequestObject)
		String body = """{"data":{"amount": ${credit_amount},"source":{"service":"adjustments","id":""},"usage":{"type":"credit","quantity":0,"unit":"USD"},"description":"Credit Added by adminstrator","metadata":{"ui_request":true,"automatic_description":true}}}"""
		request.setBodyContent(new HttpTextBodyContent(body))
		WS.sendRequest(request)
	}
}

