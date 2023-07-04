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
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.testobject.RequestObject as RequestObject
import com.kms.katalon.core.testobject.ResponseObject as ResponseObject
import com.kms.katalon.core.testobject.impl.HttpTextBodyContent as HttpTextBodyContent
import java.lang.String
/*
 * Calling Object repository to call the API
 * Calling get_cdr_list API
 */
@Keyword
def get_cdr_list(String account_id){
	def request = ((findTestObject('API/CDR/CDR.get', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
		, ('auth_token') : GlobalVariable.auth_token])) as RequestObject)
	WS.sendRequest(request)
}

/*
 * Calling Object repository to call the API
 * Calling get_CDR_byCallId API by call_id filter
 */
@Keyword
def get_CDR_byCallId(String account_id,String call_id){
	WS.sendRequest(findTestObject('API/CDR/CDR.details - ByCallId', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
		, ('auth_token') : GlobalVariable.auth_token,('call_id') : call_id]))
}

/*
 * Calling Object repository to call the API
 * Calling get_CDR_byCDRId API by cdr_id filter
 */
@Keyword
def get_CDR_byCDRId(String account_id,String cdr_id){
	WS.sendRequest(findTestObject('API/CDR/CDR.get - ByCDRId', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
		, ('auth_token') : GlobalVariable.auth_token, ('CDR_ID') : cdr_id]))
}
/*
 * Calling Object repository to call the API
 * Calling get_CDR_byInteraction_Leg API by InteractionId filter
 */
@Keyword
def get_CDR_byInteraction_Leg(String account_id,String interaction_id){
	WS.sendRequest(findTestObject('API/CDR/CDR.get_ByInteractionLeg', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
		, ('auth_token') : GlobalVariable.auth_token,('interaction_id') : interaction_id]))
}


/*
 * Calling Object repository to call the API
 * Calling get_CDR_byInteraction API by Interaction filter
 */
@Keyword
def get_CDR_byInteraction(String account_id){
	WS.sendRequest(findTestObject('API/CDR/CDR.get - Interaction', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
		, ('auth_token') : GlobalVariable.auth_token]))
}
/*
 * Calling Get CDR Interaction by time-stamp filter via created_from and created_to
 */
@Keyword
def get_CDR_Interaction_byTime_stamp(String account_id, String created_from, String created_to){
	def interactionTimeStamp = (findTestObject('API/CDR/CDR_Interaction_byTime_stamp', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
		, ('auth_token') : GlobalVariable.auth_token, ('created_from') : created_from, ('created_to') : created_to]))
	WS.sendRequest(interactionTimeStamp)
}
/*
 * Calling Object repository to call the API
 * Calling get_CDR_byUser API by UserId filter
 */
@Keyword
def get_CDR_byUser(String account_id,String user_id){
	WS.sendRequest(findTestObject('API/CDR/CDR.get - ByUser', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
		, ('auth_token') : GlobalVariable.auth_token,('user_id') : user_id]))
}
/*
 * Calling Object repository to call the API
 * Calling get_CDR_byDuration API by Duration filter
 */
@Keyword
def get_CDR_byDuration(String account_id,int duration_sec){
	WS.sendRequest(findTestObject('API/CDR/CDR.details-ByDuration', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
		, ('auth_token') : GlobalVariable.auth_token,('duration_seconds') : duration_sec]))
}
/*
 * Calling Object repository to call the API
 * Calling get_CDR_byCSVFilename API by CSVFilename filter
 */
@Keyword
def get_CDR_byCSVFilename(String account_id,String file_name){
	def request = ((findTestObject('API/CDR/CDR.GET - CSV - FileName', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
		, ('auth_token') : GlobalVariable.auth_token,('FILE_NAME') : file_name])) as RequestObject)
	WS.sendRequest(request)
}

/*
 * Calling Object repository to call the API
 * Calling get_CDR_byHangupCause API by byHangupCause filter
 */
@Keyword
def get_CDR_byHangupCause(String account_id,String hangup_cause){
	def request = ((findTestObject('API/CDR/CDR.details-ByHangupCause', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
		, ('auth_token') : GlobalVariable.auth_token,('hangup_cause') : hangup_cause])) as RequestObject)
	WS.sendRequest(request)
}

/*
 * Calling Object repository to call the API
 * Calling get_CDR_bycallflow_id API by callflow_id filter
 */
@Keyword
def get_CDR_byCallflow_id(String account_id,String callflow_id){
	def request = ((findTestObject('API/CDR/CDR.details-Bycallflow_id', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
		, ('auth_token') : GlobalVariable.auth_token,('callflow_id') : callflow_id])) as RequestObject)
	WS.sendRequest(request)
}

/*
 * Calling Object repository to call the API
 * Calling get_CDR_bymedia_server API by media_server filter
 */
@Keyword
def get_CDR_byMedia_server(String account_id,String media_server){
	def request = ((findTestObject('API/CDR/CDR.details-ByMediaServer', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
		, ('auth_token') : GlobalVariable.auth_token,('media_server') : media_server])) as RequestObject)
	WS.sendRequest(request)
}

/*
 * Calling Object repository to call the API
 * Calling get_CDR_byecallmgr_node API by ecallmgr_node filter
 */
@Keyword
def get_CDR_byEcallmgr_node(String account_id,String ecallmgr_node){
	def request = ((findTestObject('API/CDR/CDR.details-ByEcallmgr_node', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
		, ('auth_token') : GlobalVariable.auth_token,('ecallmgr_node') : ecallmgr_node])) as RequestObject)
	WS.sendRequest(request)
}

/*
 * Calling Object repository to call the API
 * Calling get_CDR_byOther_leg_call_id API by Other_leg_call_id filter
 */
@Keyword
def get_CDR_byOther_leg_call_id(String account_id,String other_leg_call_id){
	def request = ((findTestObject('API/CDR/CDR.details-ByOtherLegId', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
		, ('auth_token') : GlobalVariable.auth_token,('other_leg_call_id') : other_leg_call_id])) as RequestObject)
	WS.sendRequest(request)
}

/*
 * Calling Object repository to call the API
 * Calling get_CDR_byfrom API by from filter
 */
@Keyword
def get_CDR_byFrom(String account_id,String from){
	def request = ((findTestObject('API/CDR/CDR.details-ByFrom', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
		, ('auth_token') : GlobalVariable.auth_token,('from') : from])) as RequestObject)
	WS.sendRequest(request)
}
/*
 * Calling Object repository to call the API
 * Calling get_CDR_byRequest API by request filter
 */
@Keyword
def get_CDR_byRequest(String account_id,String request_value){
	def request = ((findTestObject('API/CDR/CDR.details-ByRequest', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
		, ('auth_token') : GlobalVariable.auth_token,('request') : request_value])) as RequestObject)
	WS.sendRequest(request)
}
/*
 * Calling Object repository to call the API
 * Calling get_CDR_byto API by to filter
 */
@Keyword
def get_CDR_byTo(String account_id,String to){
	def request = ((findTestObject('API/CDR/CDR.details-ByTo', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
		, ('auth_token') : GlobalVariable.auth_token,('to') : to])) as RequestObject)
	WS.sendRequest(request)
}

/*
 * Calling Object repository to call the API
 * Calling get_CDR_byDirection API by direction filter
 */
@Keyword
def get_CDR_byDirection(String account_id,String direction){
	def request = ((findTestObject('API/CDR/CDR.details-ByDirection', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
		, ('auth_token') : GlobalVariable.auth_token,('direction') : direction])) as RequestObject)
	WS.sendRequest(request)
}

/*
 * Calling Object repository to call the API
 * Calling get_CDR_by Bridge_id API by direction filter
 */
@Keyword
def get_CDR_byBridge_id(String account_id,String bridge_id){
	def request = ((findTestObject('API/CDR/CDR.details - ByBridgeID', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
		, ('auth_token') : GlobalVariable.auth_token,('bridge_id') : bridge_id])) as RequestObject)
	WS.sendRequest(request)
}

/*
 * Calling Object repository to call the API
 * Calling get_CDR_by Bridge_id API by direction filter
 */
@Keyword
def get_CDR_bytimestamptofrom(String account_id,Long from,Long to){
	def request = ((findTestObject('API/CDR/CDR.get - ByTimestampToFrom', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id
		, ('auth_token') : GlobalVariable.auth_token,('from') : (to-1000),('to') : (to+1000)])) as RequestObject)
	WS.sendRequest(request)
}


/*
 * Calling Object repository to call the API
 * Calling  get_CDR_byFetch_id by fetch_id filter
 */
@Keyword
def get_CDR_byFetch_id(String account_id,String fetch_id){
	WS.sendRequest(findTestObject('API/CDR/CDR.details-Byfetch_id',[('base_url') : GlobalVariable.base_url, ('account_id') : account_id,
		('auth_token') : GlobalVariable.auth_token, ('fetch_id') : fetch_id]))
}

/*
 * Calling object repository to call the API
 * Calling get_CDR_byReseller_id by reseller_id filter
 */
@Keyword
def get_CDR_byReseller_id(String account_id,String reseller_id){
	def request = ((findTestObject('API/CDR/CDR.details-ByReseller_id', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id,
		('auth_token') : GlobalVariable.auth_token, ('reseller_id') : reseller_id])) as RequestObject)
	WS.sendRequest(request)
}

/*
 * Calling object repository to call the API
 * Calling get_CDR_byGlobal_resource by global_resource filter
 */
@Keyword
def get_CDR_byGlobal_resource(String account_id, String global_resource){
	WS.sendRequest(findTestObject('API/CDR/CDR.details-ByGlobal_resource', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id,
		('auth_token') : GlobalVariable.auth_token, ('global_resource') : global_resource]))
}

/*
 * Calling object repository to call the API
 *  Calling get_CDR_byRate_name by rate_name filter
 */
@Keyword
def get_CDR_byRate_name(String account_id,String rate_name){
	WS.sendRequest(findTestObject('API/CDR/CDR.details-ByRate_name', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id,
		('auth_token') : GlobalVariable.auth_token, ('rate_name') : rate_name]))
}

/*
 * Calling object repository to call the API
 *  Calling get_CDR_byInception by inception filter
 */
@Keyword
def get_CDR_byInception(String account_id,String inception_id){
	WS.sendRequest(findTestObject('API/CDR/CDR.details-ByInception', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id,
		('auth_token') : GlobalVariable.auth_token, ('inception') : inception_id]))
}

/*
 * Calling object repository to call the API
 *  Calling get_CDR_byResourceType by resource_type filter
 */
@Keyword
def get_CDR_byResource_type(String account_id,String resource_type){
	WS.sendRequest(findTestObject('API/CDR/CDR.details-ByResource_type', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id,
		('auth_token') : GlobalVariable.auth_token, ('resource_type') : resource_type]))
}

/*
 * Calling object repository to call the API
 *  Calling get_CDR_byPrivacy_hide_name by privacy_hide_name filter
 */
@Keyword
def get_CDR_byPrivacy_hide_name(String account_id,String privacy_hide_name){
	WS.sendRequest(findTestObject('API/CDR/CDR.details-ByPrivacy_hide_name', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id,
		('auth_token') : GlobalVariable.auth_token, ('privacy_hide_name') : privacy_hide_name]))
}

/*
 * Calling object repository to call the API
 *  Calling get_CDR_byPrivacy_hide_number by privacy_hide_number filter
 */
@Keyword
def get_CDR_byPrivacy_hide_number(String account_id,String privacy_hide_number){
	WS.sendRequest(findTestObject('API/CDR/CDR.details-ByPrivacy_hide_number', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id,
		('auth_token') : GlobalVariable.auth_token, ('privacy_hide_number') : privacy_hide_number]))
}


/*
 * Calling Object repository to call the API
 * Calling get_CDR_ByAccount_id by filter_custom_channel_vars.account_id filter
 */
@Keyword
def get_CDR_ByAccount_id(String account_id,String acc_id){
	WS.sendRequest(findTestObject('API/CDR/CDR.get-ByAccount_id', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id,
		('auth_token') : GlobalVariable.auth_token,('account_id') : acc_id]))
}

/*
 * Calling Object repository to call the API
 * Calling get_CDR_ByCV_application_name by filter_custom_channel_vars.application_name filter
 */
@Keyword
def get_CDR_ByCV_application_name(String account_id,String application_name){
	WS.sendRequest(findTestObject('API/CDR/CDR.get-ByCV_application_name', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id,
		('auth_token') : GlobalVariable.auth_token,('application_name') : application_name]))
}

/*
 * Calling Object repository to call the API
 * Calling get_CDR_ByCV_application_node  by filter_custom_channel_vars.application_node filter
 */
@Keyword
def get_CDR_ByCV_application_node(String account_id,String application_node){
	WS.sendRequest(findTestObject('API/CDR/CDR.get-ByCV_application_node',[('base_url') : GlobalVariable.base_url, ('account_id') : account_id,
		('auth_token') : GlobalVariable.auth_token,('application_node') : application_node]))
}


/*
 * Calling Object repository to call the API
 * Calling get_CDR_ByChannel_authorized by authorized_channel filter
 */
@Keyword
def get_CDR_ByChannel_authorized(String account_id, String  authorized_channel){
	WS.sendRequest(findTestObject('API/CDR/CDR.details - ByChannel_authorized',[('base_url') : GlobalVariable.base_url,
		('account_id') : account_id, ('auth_token') : GlobalVariable.auth_token, ('authorized_channel') : authorized_channel]))
}

/*
 * Calling Object repository to call the API
 * Calling get_CDR_ByRate_nocharge_time by rate_nocharge_time filter
 */
@Keyword
def get_CDR_ByRate_nocharge_time(String account_id,String rate_nocharge_time){
	WS.sendRequest(findTestObject('API/CDR/CDR.details-ByRate_nocharge_time', [('base_url') : GlobalVariable.base_url,
		('account_id') : account_id, ('auth_token') : GlobalVariable.auth_token, ('rate_nocharge_time') : rate_nocharge_time]))
}

/*
 * Calling object repository to call the API
 *  Calling get_CDR_byRate_description by rate_description filter
 */
@Keyword
def get_CDR_byRate_description(String account_id,String rate_description){
	WS.sendRequest(findTestObject('API/CDR/CDR.details-ByRate_description', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id,
		('auth_token') : GlobalVariable.auth_token, ('rate_description') : rate_description]))
}

/*
 * Calling object repository to call the API
 *  Calling get_CDR_byRate by rate filter
 */
@Keyword
def get_CDR_byRate(String account_id,String rate){
	WS.sendRequest(findTestObject('API/CDR/CDR.details-ByRate', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id,
		('auth_token') : GlobalVariable.auth_token, ('rate') : rate]))
}


/*
 * Calling object repository to call the API
 *  Calling get_CDR_byAccountBilling by AccountBilling filter
 */
@Keyword
def get_CDR_byAccountBilling(String account_id,String account_billing){
	WS.sendRequest(findTestObject('API/CDR/CDR.get-AccountBilling', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id,
		('auth_token') : GlobalVariable.auth_token, ('account_billing') : account_billing]))
}

/*
 * Calling object repository to call the API
 * Calling get_CDR_bySurcharge by surcharge filter
 */
@Keyword
def get_CDR_bySurcharge(String account_id,String surcharge){
	WS.sendRequest(findTestObject('API/CDR/CDR.details-BySurcharge', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id,
		('auth_token') : GlobalVariable.auth_token, ('surcharge') : surcharge]))
}

/*
 * Calling Object repository to call the API
 * Calling get_CDR_byReseller_billing by reseller_billing filter
 */
@Keyword
def get_CDR_byReseller_billing(String account_id,String reseller_billing){
	WS.sendRequest(findTestObject('API/CDR/CDR.details-ByReseller_billing', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id,
		('auth_token') : GlobalVariable.auth_token, ('reseller_billing') : reseller_billing]))
}


/*
 * Calling object repository to call the API
 *  Calling get_CDR_byAccountBilling by AccountBilling filter
 */
@Keyword
def get_CDR_byOwnerId(String account_id,String user_id,String owner_id){
	WS.sendRequest(findTestObject('API/CDR/CDR.get - ByOwnerId', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id,
		('auth_token') : GlobalVariable.auth_token,('user_id') : user_id, ('owner_id') : owner_id]))
}

/*
 * Calling object repository to call the API
 *  Calling get_CDR_byPvt_cost by pvt_cost filter
 */
@Keyword
def get_CDR_byPvt_cost(String account_id,String pvt_cost){
	WS.sendRequest(findTestObject('API/CDR/CDR.details-ByPvt_cost', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id,
		('auth_token') : GlobalVariable.auth_token, ('pvt_cost') : pvt_cost]))
}

/*
 * Calling object repository to call the API
 *  Calling get_CDR_byRate_increment by rate_increment filter
 */
@Keyword
def get_CDR_byRate_increment(String account_id,String rate_increment){
	WS.sendRequest(findTestObject('API/CDR/CDR.get-Byrate_increment', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id,
		('auth_token') : GlobalVariable.auth_token, ('rate_increment') : rate_increment]))
}

/*
 * Calling object repository to call the API
 *  Calling get_CDR_byReseller_trunk_usage by reseller_trunk_usage filter
 */
@Keyword
def get_CDR_byReseller_trunk_usage(String account_id,String reseller_trunk_usage){
	WS.sendRequest(findTestObject('API/CDR/CDR.details-ByReseller_trunk_usage', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id,
		('auth_token') : GlobalVariable.auth_token, ('reseller_trunk_usage') :reseller_trunk_usage]))
}


/*
 * Calling Object repository to call the API
 * Calling get_CDR_ByCV_base_cost  by filter_custom_channel_vars.base_cost filter
 */
@Keyword
def get_CDR_ByCV_base_cost(String account_id, String base_cost){
	WS.sendRequest(findTestObject('API/CDR/CDR.get-ByCV_base_cost',[('base_url') : GlobalVariable.base_url, ('account_id') : account_id,
		('auth_token') : GlobalVariable.auth_token, ('base_cost') : base_cost]))
}


/*
 * Calling Object repository to call the API
 * Calling get_CDR_ByAccount_trunk_Usage by account_trunk_usage filter
 */
@Keyword
def get_CDR_ByAccount_trunk_usage(String account_id,String account_trunk_usage){
	WS.sendRequest(findTestObject('API/CDR/CDR.details-ByAccount_trunk_usage', [('base_url') : GlobalVariable.base_url,
		('account_id') : account_id, ('auth_token') : GlobalVariable.auth_token, ('account_trunk_usage') : account_trunk_usage]))
}

/*
 * Calling object repository to call the API
 *  Calling get_CDR_byRate_minimum by rate_minimum filter
 */
@Keyword
def get_CDR_byRate_minimum(String account_id, String rate_minimum){
	WS.sendRequest(findTestObject('API/CDR/CDR.details-ByRate_minimum', [('base_url') : GlobalVariable.base_url, ('account_id') : account_id,
		('auth_token') : GlobalVariable.auth_token, ('rate_minimum') :rate_minimum]))
}