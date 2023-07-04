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
import groovy.json.internal.LazyMap

import org.apache.commons.lang.ObjectUtils
import org.apache.commons.lang.StringUtils
import org.junit.After

import java.util.ArrayList
import static org.junit.Assert.*
import java.lang.String

/*
 * keys and values objects are static of jsonResponse of API.
 */
@Keyword
def static_app_parameter_verification(LazyMap responseLeg){

	WS.verifyEqual(responseLeg.app_name,GlobalVariable.app_parameters_values.get("app_name"))
	println("	app_name is verified and value is : "+responseLeg.app_name)
	WS.verifyEqual(responseLeg.app_version,GlobalVariable.app_parameters_values.get("app_version"))
	println("	app_version is verified and value is : "+responseLeg.app_version)
	WS.verifyEqual(responseLeg.channel_state,GlobalVariable.app_parameters_values.get("channel_state") )
	println("	channel_state is verified and value is : "+responseLeg.channel_state)
	WS.verifyEqual(responseLeg.channel_call_state,GlobalVariable.app_parameters_values.get("channel_call_state") )
	println("	channel_call_state is verified and value is : "+responseLeg.channel_call_state)
	WS.verifyEqual(responseLeg.event_name,GlobalVariable.app_parameters_values.get("event_name") )
	println("	event_name is verified and value is : "+responseLeg.event_name)
	WS.verifyEqual(responseLeg.event_category,GlobalVariable.app_parameters_values.get("event_category") )
	println("	event_category is verified and value is : "+responseLeg.event_category)
	assertTrue(responseLeg.custom_application_vars.isEmpty())
	println("	custom_application_vars is verified and value is : "+responseLeg.custom_application_vars)
}

/*
 * verification_keys_list:Made list of keys in a Global Variable
 * lisValueOfCalleeTerminates:Make list of values of API json response
 * Variable paramMap:Making map of key object and value object of json response.
 */
@Keyword
def response_real_expected_value_list(LazyMap responseLeg,LinkedHashMap caller, LinkedHashMap callee,String to, String from, String request){
	def lisKey= GlobalVariable.verification_keys_list
	def lisValueOfCalleeTerminates=[caller.get('extension'), caller.get('first_name'), callee.get('extension'), callee.get('first_name'), to+"@" + GlobalVariable.sub_account_details.get('account_realm'), from +"@"+GlobalVariable.sub_account_details.get('account_realm'), to, caller.get('extension'), request+"@" + GlobalVariable.sub_account_details.get('account_realm')]
	Map paramMap =new LinkedHashMap<String,String>()
	for(int k=0;k<lisKey.size();k++){
		paramMap.put(lisKey[k],lisValueOfCalleeTerminates[k])
	}
	return paramMap
}
/*
 * verification_keys_list:Made list of keys in a Global Variable
 * lisValueOfCallerInitiateTransfer:Make list of values of API json response
 * Variable 'paramMap':Making map of key object and value object of json response.
 */
@Keyword
def response_real_expected_value_list_transfer(LazyMap responseLeg,LinkedHashMap caller, LinkedHashMap callee,String to ,String from,String calling_from,String request){
	def lisKey= GlobalVariable.verification_keys_list
	def lisValueOfCalleeTerminates=[caller.get('extension'), caller.get('first_name'), callee.get('extension'), callee.get('first_name'), to+"@" + GlobalVariable.sub_account_details.get('account_realm'), from +"@"+GlobalVariable.sub_account_details.get('account_realm'), to, calling_from, request+"@" + GlobalVariable.sub_account_details.get('account_realm')]
	Map paramMap =new LinkedHashMap<String,String>()
	for(int k=0;k<lisKey.size();k++){
		paramMap.put(lisKey[k],lisValueOfCalleeTerminates[k])
	}
	return paramMap
}

/*Variable 'paramMap': calling map contain keys and expected values
 * Iterating over map to get key name and expected values
 * On runtime fetching actual value object of jsonResponse and comparing with expected value
 */
@Keyword
def real_parameter_verification(LazyMap responseLeg,Map paramMap){
	println(responseLeg)
	for(Map.Entry<String,String> entry : paramMap.entrySet()){
		def key=entry.getKey();
		def value=entry.getValue();
		println(key)
		try{
			assertEquals(value,responseLeg.get(key))
			println("Object Key value is correct: "+"actual object key value is: "+responseLeg.get(key)+" and expected value is: "+value)
		}catch(Exception e){
			println("Object Key value is Incorrect: "+"actual object key value is: "+responseLeg.get(key)+" and expected value is: "+value)
		}
	}
}

/*
 * Verifying value object of jsonResponse is present and not empty.
 *All keys have system generated values.
 */
@Keyword
def format_parameter_verification(LazyMap responseLeg){
	boolean result =  (!StringUtils.isBlank(responseLeg.bridge_id)
			&& !StringUtils.isBlank(responseLeg.call_id)
			&& !StringUtils.isBlank(responseLeg.interaction_id)
			&& !StringUtils.isBlank(responseLeg.authorizing_id)
			&& !StringUtils.isBlank(responseLeg.id))
	println "	bridge_id is verified and value is : "+responseLeg.bridge_id
	println "	call_id is verified and value is : "+responseLeg.call_id
	println "	interaction_id is verified and value is : "+responseLeg.interaction_id
	println "	authorizing_id is verified and value is : "+responseLeg.authorizing_id
	println "	id is verified and value is : "+responseLeg.id
	return (result)
}

/*
 * Passing list of legs of API jsonResponse
 * Iterating each leg and storing filters like call_id,cdr_id and interaction-id.
 * Storing each filters of each leg in a map and making list of maps out of it.
 */
@Keyword
def store_filter_parameter(java.util.ArrayList response_leg){
	GlobalVariable.filterParametersPerLeg=[]
	for(int leg_index=0;leg_index<response_leg.size();leg_index++){
		Map leg_filter_parameter= new HashMap<String,String>()

		assertNotNull("interaction_id is null in CDR response leg.",response_leg[leg_index].interaction_id)
		assertNotNull("call_id is null in CDR response leg.",response_leg[leg_index].call_id)
		assertNotNull("cdr_id is null in CDR response leg.",response_leg[leg_index].id)
		leg_filter_parameter.put("interaction_id" , response_leg[leg_index].interaction_id)
		leg_filter_parameter.put("call_id" ,response_leg[leg_index].call_id)
		leg_filter_parameter.put("cdr_id" , response_leg[leg_index].id)
		GlobalVariable.filterParametersPerLeg.add(leg_filter_parameter)
	}
	println("all values of filters for each leg is: "+GlobalVariable.filterParametersPerLeg)
}

/*
 * keys_list_CDR_Id:Made list of keys in a Global Variable
 * lisValueOfCDRId:Make list of values of API json response
 * key-value-Map:Making map of key object and value object of json response.
 */
@Keyword
def response_real_expected_value_list_CDR_Id(LazyMap responseLeg,LinkedHashMap caller, LinkedHashMap callee,String to ,String from,String request){
	def lisKey=GlobalVariable.keys_list_CDR_Id
	def lisValueOfCDRId=[caller.get('extension'), caller.get('first_name'), callee.get('extension'), callee.get('first_name'), to+"@" + GlobalVariable.sub_account_details.get('account_realm'), from +"@"+GlobalVariable.sub_account_details.get('account_realm'), request+"@" + GlobalVariable.sub_account_details.get('account_realm')]
	Map keyValueMap =new LinkedHashMap<String,String>()
	for(int k=0;k<lisKey.size();k++){
		keyValueMap.put(lisKey[k],lisValueOfCDRId[k])
	}
	for(Map.Entry<String,String> entry : keyValueMap.entrySet()){
		def key=entry.getKey();
		def value=entry.getValue();
		try{
			assertEquals(value,responseLeg.get(key))
			println("Object Key value is correct: "+"key is: "+key+" actual value is: "+responseLeg.get(key)+" and expected value is: "+value)
		}catch(Exception e){
			println("Object Key value is Incorrect: "+"key is: "+key+" actual value is: "+responseLeg.get(key)+" and expected value is: "+value)
		}
	}
}

/*Specific to voicemail where callee details is empty
 * keys_list_CDR_Id:Made list of keys in a Global Variable
 * lisValueOfCDRId:Make list of values of API json response
 * key-value-Map:Making map of key object and value object of json response.
 */
@Keyword
def response_real_expected_value_list_voicemail_CDR_Id(LazyMap responseLeg,LinkedHashMap caller,String to,String from,String request){

	def lisKey=GlobalVariable.keys_list_voicemail_cdr_id
	def lisValueOfCDRId=[caller.get('extension'), caller.get('first_name'), to+"@" + GlobalVariable.sub_account_details.get('account_realm'), from+"@"+GlobalVariable.sub_account_details.get('account_realm'), request+"@" + GlobalVariable.sub_account_details.get('account_realm')]
	Map keyValueMap =new LinkedHashMap<String,String>()
	for(int k=0;k<lisKey.size();k++){
		keyValueMap.put(lisKey[k],lisValueOfCDRId[k])
	}
	for(Map.Entry<String,String> entry : keyValueMap.entrySet()){
		def key=entry.getKey();
		def value=entry.getValue();
		try{
			println key
			assertEquals(value,responseLeg.get(key))
			println("Object Key value of " + key +" matches the expected value is: "+value)
		}catch(Exception e){
			println("Object Key value is Incorrect: "+"key is: " +key+" actual value is: "+responseLeg.get(key)+" and expected value is: "+value)
		}
	}
}

/*lisKeyCustomChannelVarsParameters:list of key objects of jsonResponse under custom_channel_vars
 * lisValueOfCustomChannelVarsParameters: list of expected values objects under custom_channel_vars
 * customChannelVarsparamMap: map of keys objects and expected values
 */
@Keyword
def app_customChannelVars_parameter_CDRId(LazyMap responseLeg,String media_server,String owner_id,String username){
	def lisKeyCustomChannelVarsParameters= GlobalVariable.customChannelVars_keys_list
	def lisValueOfCustomChannelVarsParameters=[GlobalVariable.sub_account_details.get("account_id"), GlobalVariable.app_parameters_values.get("authorizing_type"), GlobalVariable.app_parameters_values.get("app_name")+"@"+media_server, owner_id, GlobalVariable.sub_account_details.get("account_realm"), //verifying realm
		username]
	Map customChannelVarsparamMap =new LinkedHashMap<String,String>()
	for(int k=0;k<lisKeyCustomChannelVarsParameters.size();k++){
		customChannelVarsparamMap.put(lisKeyCustomChannelVarsParameters[k],lisValueOfCustomChannelVarsParameters[k])
	}
	return customChannelVarsparamMap


}

/*lisKeyCustomChannelVarsParameters#list of key objects of jsonResponse under custom_channel_vars
 * lisValueOfCustomChannelVarsParameters# list of expected values objects under custom_channel_vars
 * customChannelVarsparamMap# map of keys objects and expected values
 */
@Keyword
def app_customChannelVars_parameter_CDRId_forward(LazyMap responseLeg,String media_server,String owner_id,String call_forward_from){

	def lisKeyCustomChannelVarsParameters= GlobalVariable.customChannelVars_keys_list_forward
	def lisValueOfCustomChannelVarsParameters=[GlobalVariable.sub_account_details.get("account_id"), GlobalVariable.app_parameters_values.get("authorizing_type"), "true", call_forward_from, "true", GlobalVariable.app_parameters_values.get("app_name")+"@"+media_server, owner_id, "true"] //verifying realm
	Map customChannelVarsparamMap =new LinkedHashMap<String,String>()
	for(int k=0;k<lisKeyCustomChannelVarsParameters.size();k++){
		customChannelVarsparamMap.put(lisKeyCustomChannelVarsParameters[k],lisValueOfCustomChannelVarsParameters[k])
	}
	return customChannelVarsparamMap

}

/*customChannelVarsparamMap: calling map contain keys and expected values
 * Iterating over map to get key name and expected values
 * On runtime fetching actual value object of jsonResponse and comparing with expected value
 */
@Keyword
def app_customChannelVars_parameter_verification(LazyMap responseLeg,Map customChannelVarsparamMap){

	for(Map.Entry<String,String> entry : customChannelVarsparamMap.entrySet()){
		def key=entry.getKey();
		def value=entry.getValue();
		try{
			assertEquals(value.toLowerCase(),responseLeg.custom_channel_vars.get(key).toLowerCase())
			println("Object Key value is correct: "+"key is: "+key+" actual value is: "+responseLeg.custom_channel_vars.get(key)+" and expected value is: "+value)
		}catch(Exception e){
			println("Object Key value is Incorrect: "+"key is: "+key+" actual value is: "+responseLeg.custom_channel_vars.get(key)+" and expected value is: "+value)
		}
	}
}
/*
 * GET CDR_CDR_id API: Verifying dynamic value objects of jsonResponse which vary for each leg and in every scenario.
 */
@Keyword
def real_parameter_verification_CDRId(LazyMap responseLeg,String media_server,String presence_id,String to_uri,String from_uri){
	WS.verifyEqual(responseLeg.from_uri,from_uri+"@"+GlobalVariable.sub_account_details.get('account_realm'))
	println "	from_uri is verified and value is "+responseLeg.from_uri
	WS.verifyEqual(responseLeg.to_uri,to_uri+"@"+GlobalVariable.sub_account_details.get('account_realm'))
	println "	to_uri is verified and value is "+responseLeg.to_uri
	WS.verifyEqual(responseLeg.switch_nodename, "freeswitch@"+media_server)
	println "	switch_nodename is verified and value is "+responseLeg.switch_nodename
	WS.verifyEqual(responseLeg.switch_hostname,media_server )
	println "	switch_hostname is verified and value is "+responseLeg.switch_hostname
	WS.verifyEqual(responseLeg.presence_id,presence_id+"@"+GlobalVariable.sub_account_details.get('account_realm') )
	println "	presence_id is verified and value is "+responseLeg.presence_id
	def listOfNode = getNodeValueList(media_server)
	assertTrue(listOfNode.contains(responseLeg.call_control.node))
	println "	node is verified and value is "+responseLeg.call_control.node
}

/*
 * GET CDR_CDR_id API: Verifying dynamic value objects of jsonResponse which vary for each leg like presence_id, from_uri is null,to_uri is null
 */
@Keyword
def real_parameter_verification_CDRId_forward(LazyMap responseLeg,String media_server,String presence_id){
	WS.verifyEqual(responseLeg.switch_nodename, "freeswitch@"+media_server)
	println "	switch_nodename is verified and value is "+responseLeg.switch_nodename
	WS.verifyEqual(responseLeg.switch_hostname,media_server )
	println "	switch_hostname is verified and value is "+responseLeg.switch_hostname
	WS.verifyEqual(responseLeg.presence_id,presence_id+"@"+GlobalVariable.sub_account_details.get('account_realm') )
	println "	presence_id is verified and value is "+responseLeg.presence_id
	def listOfNode = getNodeValueList(media_server)
	assertTrue(listOfNode.contains(responseLeg.call_control.node))
	println "	node is verified and value is "+responseLeg.call_control.node
}
/*
 * GET CDR_CDR_id API: Verifying value object of jsonResponse is present and not empty.
 * All keys object have system generated values.
 */
@Keyword
def app_parameters_system_generated_CDRId(LazyMap responseLeg){
	assertTrue(!StringUtils.isBlank(responseLeg.custom_channel_vars.authorizing_id))
	println "	authorizing_id is verified and value is "+responseLeg.custom_channel_vars.authorizing_id
	boolean result = (!StringUtils.isBlank(responseLeg.msg_id)
			&& !StringUtils.isBlank(responseLeg.from_tag)
			&& !StringUtils.isBlank(responseLeg.call_id)
			&& !StringUtils.isBlank(responseLeg.interaction_key)
			&& !StringUtils.isBlank(responseLeg.custom_channel_vars.bridge_id)
			&& !StringUtils.isBlank(responseLeg.id))
	println "	msg_id is verified and value is "+responseLeg.msg_id
	println "	from_tag is verified and value is "+responseLeg.from_tag
	println "	interaction_key is verified and value is "+responseLeg.interaction_key
	println "	bridge_id is verified and value is "+responseLeg.custom_channel_vars.bridge_id
	println "	id is verified and value is "+responseLeg.id
	return (result)
}

/*On-net
 *GET CDR_CDR_id API: Verifying extra key object in inbound leg of jsonResponse .
 */
@Keyword
def parameters_inboundLeg_CDRId(LazyMap responseLeg ,String media_server){
	WS.verifyEqual(responseLeg.custom_channel_vars.account_name,GlobalVariable.sub_account_details.get("account_name") )
	println "	account_name is verified and value is "+responseLeg.custom_channel_vars.account_name
	WS.verifyEqual(responseLeg.custom_channel_vars.account_realm,GlobalVariable.sub_account_details.get("account_realm") )
	println "	account_realm is verified and value is "+responseLeg.custom_channel_vars.account_realm
	WS.verifyEqual(responseLeg.custom_channel_vars.application_name,GlobalVariable.app_parameters_values .get("application_name"))
	println "	application_name is verified and value is "+responseLeg.custom_channel_vars.application_name
	WS.verifyEqual(responseLeg.custom_channel_vars.application_node,GlobalVariable.app_parameters_values .get("application_node")+"@"+media_server )
	println "	application_node is verified and value is "+responseLeg.custom_channel_vars.application_node
	return(!StringUtils.isBlank(responseLeg.custom_channel_vars.callflow_id)
			&& !StringUtils.isBlank(responseLeg.custom_channel_vars.fetch_id))
	println "	callflow_id is verified and value is "+responseLeg.custom_channel_vars.callflow_id
	println "	fetch_id is verified and value is "+responseLeg.custom_channel_vars.fetch_id
}
/*verifying json object present only on outbound leg of CDR response*/
@Keyword
def parameters_outboundLeg_CDRId(LazyMap responseLeg ,String other_leg_dest_num,String other_caller_number,String caller_id_name, hangup_cause){
	WS.verifyEqual(responseLeg.other_leg_destination_number,other_leg_dest_num )
	println "	other_leg_destination_number is verified and value is "+responseLeg.other_leg_destination_number
	WS.verifyEqual(responseLeg.other_leg_caller_id_number,other_caller_number )
	println "	other_caller_number is verified and value is "+responseLeg.other_caller_number
	WS.verifyEqual(responseLeg.other_leg_caller_id_name,caller_id_name)
	println "	other_leg_caller_id_name is verified and value is "+responseLeg.other_leg_caller_id_name
	WS.verifyEqual(responseLeg.hangup_cause, hangup_cause)
	println "	hangup_cause is verified and value is "+responseLeg.hangup_cause
}
/*
 * Verifying the json object in leg when callee details are empty
 * Scenario observed in Voicemail Access
 */
@Keyword
def real_parameter_verification_NoCalleeID(LazyMap responseLeg, Map paramMap){

	for(Map.Entry<String,String> entry : paramMap.entrySet()){
		def key=entry.getKey();
		def value=entry.getValue()
		if ( key == "callee_id_number" || key == "callee_id_name"){

			try{
				(WS.verifyEqual(responseLeg.get(key), ""))
				println("Object Key value of " + key +" is Null")

			}catch (Exception e){
				println("Object Key value of "+ key + "is not null, and actual value is:" (responseLeg.get(key)))

			}
		}else{

			try{
				println responseLeg.getKey()
				WS.verifyEqual(responseLeg.get(key), value)
				println("Object Key value of " + key +" matches the expected value is: "+value)
			}catch(Exception e){
				println("Object Key value is Incorrect, actual object key value is: "+ responseLeg.get(key)+"and expected value is: "+value)
			}
		}
	}
}

@Keyword
def real_parameter_verification_VoicemailAccess(LazyMap responseLeg, Map paramMap){

	for(Map.Entry<String,String> entry : paramMap.entrySet()){
		def key=entry.getKey();
		def value=entry.getValue()
		if ( key == "callee_id_number" || key == "callee_id_name"){
			continue
		}else{
			try{
				println responseLeg.getKey()
				(WS.verifyEqual(responseLeg.get(key), value))
				println("Object Key value of " + key +" matches the expected value is: "+value)
			}catch(Exception e){
				println("Object Key value is Incorrect, actual object key value is: "+ responseLeg.get(key)+"and expected value is: "+value)
			}

		}
	}
}


/*This keyword is used for specific fax call scenario
 *verification_keys_list:Made list of keys in a Global Variable
 *lisValueOfCalleeTerminates:Make list of values of API json response
 *paramMap:Making map of key object and value object of json response.
 */
@Keyword
def response_real_expected_value_list_fax_call(LazyMap responseLeg,LinkedHashMap caller,String to,String from,String account_realm,String request,String domain){
	def lisKey= GlobalVariable.verification_keys_list
	def lisValueOfCalleeTerminates=[caller.get('fax_number'), caller.get('first_name'), "context_2", "Outbound Call", to+"@"+account_realm, from+"@"+account_realm, to, caller.get('fax_number'), request+"@"+domain]

	Map paramMap =new LinkedHashMap<String,String>()
	for(int k=0;k<lisKey.size();k++){
		paramMap.put(lisKey[k],lisValueOfCalleeTerminates[k])
	}
	return paramMap

}

/*Keyword is used for specific fax call scenario
 *paramMap: calling map contain keys and expected values
 *Iterating over map to get key name and expected values
 *On runtime fetching actual value object of jsonResponse and comparing with expected value
 */

@Keyword
def real_parameter_verification_faxcall(LazyMap responseLeg, Map paramMap){

	for(Map.Entry<String,String> entry : paramMap.entrySet()){
		def key=entry.getKey();
		def value=entry.getValue()
		println(key)
		if ( key.equals("calling_from") && ((responseLeg.get(key))== null || (responseLeg.get(key)).isEmpty())){
			println("Object Key value of " + key +" is Null")
		}
		else{

			try{

				(WS.verifyEqual(responseLeg.get(key), value))
				println("Object Key value of " + key +" matches the expected value is: "+value)
			}catch(Exception e){
				println("Object Key value is Incorrect, actual object key value is: "+ responseLeg.get(key)+"and expected value is: "+value)
			}
		}
	}
}

/*Verifying value object of jsonResponse is present and not empty.
 *All keys have system generated values.
 */
@Keyword
def format_parameter_verification_faxcall(LazyMap responseLeg){
	boolean output = (!StringUtils.isBlank(responseLeg.call_id)
			&& !StringUtils.isBlank(responseLeg.interaction_id)
			&& !StringUtils.isBlank(responseLeg.id)
			&& !StringUtils.isBlank(responseLeg.other_leg_call_id))
	println "	call_id is verified and value is : "+responseLeg.call_id
	println "	interaction_id is verified and value is : "+responseLeg.interaction_id
	println "	id is verified and value is : "+responseLeg.id
	println "	other_leg_call_id is verified and value is : "+responseLeg.other_leg_call_id
	return(output)
}


/*
 *GET CDR_CDR_id API: Verifying extra key object in inbound leg of jsonResponse
 *This keyword is used for onnet  fax call inbound leg .
 */
@Keyword
def parameters_inboundLeg_CDRId_fax_call(LazyMap responseLeg ,String media_server,String phone_number){
	WS.verifyEqual(responseLeg.custom_channel_vars.application_name,GlobalVariable.app_parameters_values .get("application_name"))
	println "	application_name is verified and value is "+responseLeg.custom_channel_vars.application_name
	WS.verifyEqual(responseLeg.custom_channel_vars.application_node,GlobalVariable.app_parameters_values .get("application_node")+"@"+media_server )
	println "	application_node is verified and value is "+responseLeg.custom_channel_vars.application_node
	WS.verifyEqual(responseLeg.custom_channel_vars.privacy_hide_name,"false")
	println "	privacy_hide_name is verified and value is "+responseLeg.custom_channel_vars.privacy_hide_name
	WS.verifyEqual(responseLeg.custom_channel_vars.privacy_hide_number,"false")
	println "	privacy_hide_number is verified and value is "+responseLeg.custom_channel_vars.privacy_hide_number
	WS.verifyEqual(responseLeg.custom_channel_vars.inception,phone_number+"@"+GlobalVariable.sub_account_details.get("account_realm"))
	println "	inception is verified and value is "+responseLeg.custom_channel_vars.inception
	boolean result = (!StringUtils.isBlank(responseLeg.custom_channel_vars.callflow_id)
			&& !StringUtils.isBlank(responseLeg.custom_channel_vars.bridge_id)
			&& !StringUtils.isBlank(responseLeg.custom_channel_vars.fetch_id))
	println "	callflow_id is verified and value is "+responseLeg.custom_channel_vars.callflow_id
	println "	bridge_id is verified and value is "+responseLeg.custom_channel_vars.bridge_id
	println "	fetch_id is verified and value is "+responseLeg.custom_channel_vars.fetch_id
	return(result)
}

/*
 *GET CDR_CDR_id API: Verifying dynamic value objects of jsonResponse which vary for each leg and in every scenario.
 *keyword is used for onnet fax call
 */
@Keyword
def real_parameter_verification_CDRId_faxcall(LazyMap responseLeg,String media_server,String fromRealm){
	WS.verifyEqual(responseLeg.switch_nodename, "freeswitch@"+media_server)
	println "	switch_nodename is verified and value is : "+responseLeg.switch_nodename
	WS.verifyEqual(responseLeg.switch_hostname,media_server )
	println "	switch_hostname is verified and value is : "+responseLeg.switch_hostname
	def listOfNode = getNodeValueList(media_server)
	assertTrue(listOfNode.contains(responseLeg.call_control.node))
	println "	node is verified and value is "+responseLeg.call_control.node
	WS.verifyEqual(responseLeg.switch_url,"sip:mod_sofia@"+fromRealm+":11000")
	println "	switch_url is verified and value is : "+responseLeg.switch_url
	WS.verifyEqual(responseLeg.switch_uri,"sip:"+fromRealm+":11000")
	println "	switch_uri is verified and value is : "+responseLeg.switch_uri
	WS.verifyEqual(responseLeg.channel_call_state,"HANGUP")
	println "	channel_call_state is verified and value is : "+responseLeg.channel_call_state
	WS.verifyEqual(responseLeg.fax_info.fax_ecm_used,GlobalVariable.fax_ecm_used)
	println "	fax_ecm_used is verified and value is : "+responseLeg.fax_info.fax_ecm_used
}


/*
 *GET CDR_CDR_id API: Verifying value object of jsonResponse is present and not empty.
 *All keys object have system generated values
 *keyword is used for onnet fax call.
 */
@Keyword
def app_parameters_system_generated_CDRId_faxcall(LazyMap responseLeg){
	boolean result = (!StringUtils.isBlank(responseLeg.msg_id)
			&& !StringUtils.isBlank(responseLeg.other_leg_call_id)
			&& !StringUtils.isBlank(responseLeg.call_id)
			&& !StringUtils.isBlank(responseLeg.interaction_key)
			&& !StringUtils.isBlank(responseLeg.origination_call_id)
			)
	println "	msg_id is verified and value is : "+responseLeg.msg_id
	println "	other_leg_call_id is verified and value is : "+responseLeg.other_leg_call_id
	println "	call_id is verified and value is : "+responseLeg.call_id
	println "	interaction_key is verified and value is : "+responseLeg.interaction_key
	println "	origination_call_id is verified and value is : "+responseLeg.origination_call_id
	return(result)
}

/*
 * Contain channel loopback details of call forward scenarios
 */
@Keyword
def Verify_channel_Loopback_details_fax_call(LazyMap responseLeg,String channel_loopback_leg){
	WS.verifyEqual(responseLeg.channel_loopback_leg, channel_loopback_leg)
	println "	channel_loopback_leg is verified and value is : "+responseLeg.channel_loopback_leg
	WS.verifyEqual(responseLeg.channel_loopback_bowout_execute, false)
	println "	channel_loopback_bowout_execute is verified and value is : "+responseLeg.channel_loopback_bowout_execute
	WS.verifyEqual(responseLeg.channel_loopback_bowout, false)
	println "	channel_loopback_bowout is verified and value is : "+responseLeg.channel_loopback_bowout
	WS.verifyEqual(responseLeg.channel_is_loopback, true)
	println "	channel_is_loopback is verified and value is : "+responseLeg.channel_is_loopback
	return(!StringUtils.isBlank(responseLeg.channel_loopback_other_leg_id))
	println "	channel_loopback_other_leg_id is verified and value is : "+responseLeg.channel_loopback_other_leg_id
}

/*keys_list_CDR_Id:Made list of keys in a Global Variable
 *lisValueOfCDRId:Make list of values of API json response
 *key-value-Map:Making map of key object and value object of json response.
 *Keywotrd is used only for onnet fax call
 */
@Keyword
def verify_real_expected_value_CDR_Id_faxcall(LazyMap responseLeg,LinkedHashMap caller,String to,String from,String account_realm,String request,String media_server,String domain){
	def lisKey=GlobalVariable.keys_list_CDR_Id_faxcall
	def lisValueOfCDRId=[caller.get('fax_number'), caller.get('first_name'), "context_2", "Outbound Call", to+"@"+account_realm, from+"@"+account_realm, request+"@"+domain, GlobalVariable.sub_account_details.get("account_id"), "ecallmgr@" + media_server]
	Map keyValueMap =new LinkedHashMap<String,String>()
	for(int k=0;k<lisKey.size();k++){
		keyValueMap.put(lisKey[k],lisValueOfCDRId[k])
	}
	for(Map.Entry<String,String> entry : keyValueMap.entrySet()){
		def key=entry.getKey();
		def value=entry.getValue();
		if(key.equals("account_id") || key.equals("ecallmgr_node"))
		{
			try{

				assertEquals(value,responseLeg.custom_channel_vars.get(key))
				println("Object Key value is correct: "+"key is: "+key+" actual value is: "+responseLeg.custom_channel_vars.get(key)+" and expected value is: "+value)
			}catch(Exception e){
				println("Object Key value is Incorrect: "+"key is: "+key+" actual value is: "+responseLeg.custom_channel_vars.get(key)+" and expected value is: "+value)

			}
		}
		else{
			try{
				assertEquals(value,responseLeg.get(key))
				println("Object Key value is correct: "+"key is: "+key+" actual value is: "+responseLeg.get(key)+" and expected value is: "+value)
			}catch(Exception e){
				println("Object Key value is Incorrect: "+"key is: "+key+" actual value is: "+responseLeg.get(key)+" and expected value is: "+value)
			}
		}
	}
}

/*verifying json object present only on outbound leg of CDR response
 *This is keyword is used for on-net email to fax call
 * */
@Keyword
def parameters_outboundLeg_CDRId_faxcall(LazyMap responseLeg,String authorizing_type,String hangup_cause,String phone_no){
	WS.verifyEqual(responseLeg.custom_channel_vars.account_id,GlobalVariable.sub_account_details.get("account_id") )
	println "	account_id is verified and value is : "+responseLeg.custom_channel_vars.account_id
	WS.verifyEqual(responseLeg.custom_channel_vars.authorizing_type,authorizing_type )
	println "	authorizing_type is verified and value is : "+responseLeg.custom_channel_vars.authorizing_type
	WS.verifyEqual(responseLeg.hangup_cause, hangup_cause)
	println "	hangup_cause is verified and value is : "+responseLeg.hangup_cause
	WS.verifyEqual(responseLeg.custom_channel_vars.export_loopback_retain_cid, "true")
	println "	export_loopback_retain_cid is verified and value is : "+responseLeg.custom_channel_vars.export_loopback_retain_cid
	WS.verifyEqual(responseLeg.custom_channel_vars.export_loopback_account_id,GlobalVariable.sub_account_details.get("account_id"))
	println "	export_loopback_account_id is verified and value is : "+responseLeg.custom_channel_vars.export_loopback_account_id
	WS.verifyEqual(responseLeg.custom_channel_vars.resource_id,GlobalVariable.sub_account_details.get("account_id"))
	println "	resource_id is verified and value is : "+responseLeg.custom_channel_vars.resource_id
	WS.verifyEqual(responseLeg.custom_channel_vars.export_loopback_inception,phone_no+"@"+GlobalVariable.sub_account_details.get('account_realm'))
	println "	export_loopback_inception is verified and value is : "+responseLeg.custom_channel_vars.export_loopback_inception
	return(!StringUtils.isBlank(responseLeg.custom_channel_vars.authorizing_id))
	println "	authorizing_id is verified and value is : "+responseLeg.custom_channel_vars.account_id
}

/*
 * fax_info_details: made map of fax details as Global variable
 * keyword to update fax info list which is used for verification of fax details
 */
@Keyword
def response_fax_expected_value_map(String fax_num1,String fax_num2,String firstname){
	GlobalVariable.fax_info_details.put("fax_local_station_id",fax_num1)
	GlobalVariable.fax_info_details.put("fax_remote_station_id",fax_num2)
	GlobalVariable.fax_info_details.put("fax_identity_number",fax_num1)
	String name = firstname + "_printer"
	GlobalVariable.fax_info_details.put("fax_identity_name",name)
}
/*keys_list_CDR_Id:list of keys in a Global Variable
 * lisValueOfForward:list of json value of call details
 * key-value-Map:Making map of key object and value object of json response.
 */
@Keyword
def forward_callDetails_jsonRes_verify(LazyMap responseLeg,String caller_id_number,LinkedHashMap caller, LinkedHashMap callee,String to ,String toDomain,String from,String fromDomain,String request,String requestDomain){

	def lisKey=GlobalVariable.keys_list_CDR_Id
	def lisValueOfForward=[caller_id_number, caller.get('first_name'), callee.get('extension'), callee.get('first_name'), to+"@" +toDomain, from +"@"+fromDomain, request+"@" +requestDomain]
	Map keyValueMap =new LinkedHashMap<String,String>()
	for(int k=0;k<lisKey.size();k++){
		keyValueMap.put(lisKey[k],lisValueOfForward[k])
	}
	for(Map.Entry<String,String> entry : keyValueMap.entrySet()){
		def key=entry.getKey();
		def value=entry.getValue();
		try{

			assertEquals(value,responseLeg.get(key))
			println("Object Key value is correct: "+"key is: "+key+" actual value is: "+responseLeg.get(key)+" and expected value is: "+value)
		}catch(Exception e){
			println("Object Key value is Incorrect: "+"key is: "+key+" actual value is: "+responseLeg.get(key)+" and expected value is: "+value)
		}
	}
}



/*
 * Contain channel loopback details of call forward scenarios
 */
@Keyword
def channel_Loopback_details_forward(LazyMap responseLeg,String channel_loopback_leg){
	WS.verifyEqual(responseLeg.channel_loopback_leg, channel_loopback_leg)
	println("	channel_loopback_leg is verified and value is : "+responseLeg.channel_loopback_leg)
	WS.verifyEqual(responseLeg.channel_loopback_bowout_execute, false)
	println("	channel_loopback_bowout_execute is verified and value is : "+responseLeg.channel_loopback_bowout_execute)
	WS.verifyEqual(responseLeg.channel_loopback_bowout, true)
	println("	channel_loopback_bowout is verified and value is : "+responseLeg.channel_loopback_bowout)
	WS.verifyEqual(responseLeg.channel_is_loopback, true)
	println("	channel_is_loopback is verified and value is : "+responseLeg.channel_is_loopback)
}


/*
 * Making expected value list as per caller's account
 * this keyword is used for gateway call
 */
@Keyword
def list_of_expected_values_caller(String caller_num,LinkedHashMap caller,String callee_phno,String to,String from,String realm){
	def listofvalues=[caller_num, caller.get('first_name'), callee_phno.substring(2, 12), callee_phno.substring(2, 12), to+"@" + realm, from +"@"+realm, to, caller_num, to+"@" + realm]
	return listofvalues
}

/*
 * Making expected value list as per callee's account
 * this keyword is used for gateway call
 */
@Keyword

def list_of_expected_values_callee(String caller_phno,LinkedHashMap callee,String caller_realm,String callee_realm,String to,String request,String caller_name){

	def listofvalues=[caller_phno, caller_name, callee.get("phone_number"), callee.get("first_name")+" "+callee.get("last_name"), to+"@" + callee_realm, caller_phno +"@"+caller_realm, to, caller_phno, request+"@" + callee_realm]
	return listofvalues
}


/*verification_keys_list#Made list of keys in a Global Variable
 * paramMap#Making map of key object and value object of json response.
 */
@Keyword
def response_real_expected_value_list_offnet(LazyMap responseLeg,java.util.ArrayList expected_value_list){
	def lisKey= GlobalVariable.verification_keys_list
	Map paramMap =new LinkedHashMap<String,String>()
	for(int k=0;k<lisKey.size();k++){
		paramMap.put(lisKey[k],expected_value_list[k])
	}
	return paramMap
}

/*
 * Verifying value object of jsonResponse is present and not empty.
 * All keys have system generated values.
 * Keyword is used for format verification of gateway call
 */
@Keyword
def format_parameter_verification_gateway_call(LazyMap responseLeg){
	boolean result = (!StringUtils.isBlank(responseLeg.bridge_id)
			&& !StringUtils.isBlank(responseLeg.call_id)
			&& !StringUtils.isBlank(responseLeg.interaction_id)
			&& !StringUtils.isBlank(responseLeg.id))
	println("	bridge_id is verified and value is : "+responseLeg.bridge_id)
	println("	call_id is verified and value is : "+responseLeg.call_id)
	println("	interaction_id is verified and value is : "+responseLeg.interaction_id)
	println("	id is verified and value is : "+responseLeg.id)
	return(result)
}

/* Offnet Gateway calls
 * lisKeyCustomChannelVarsParameters:list of key objects of jsonResponse under custom_channel_vars
 * lisValueOfCustomChannelVarsParameters: list of expected values objects under custom_channel_vars
 * customChannelVarsparamMap: map of keys objects and expected values
 * verification of objects of custom_channel_var which are common to all leg if response of get CDR by CDR_id
 */
@Keyword
def offnet_common_app_customChannelVars_parameter_CDRId(LazyMap responseLeg,String account_id,String media_server,String inception){
	def lisKeyCustomChannelVarsParameters= GlobalVariable.offnet_customChannelVars_keys_list
	def lisValueOfCustomChannelVarsParameters=[account_id, 'true', GlobalVariable.app_parameters_values.get("app_name")+"@"+media_server, inception]
	Map customChannelVarsparamMap =new LinkedHashMap<String,String>()
	for(int k=0;k<lisKeyCustomChannelVarsParameters.size();k++){
		customChannelVarsparamMap.put(lisKeyCustomChannelVarsParameters[k],lisValueOfCustomChannelVarsParameters[k])
	}
	return customChannelVarsparamMap
}
/*offnet calls
 * lisKeyCustomChannelVarsParameters:list of key objects of jsonResponse under custom_channel_vars
 * lisValueOfCustomChannelVarsParameters: list of expected values objects under custom_channel_vars
 * customChannelVarsparamMap: map of keys objects and expected values
 */
@Keyword
def offnet_legWise_app_customChannelVars_parameter_CDRId(LazyMap responseLeg,String account_id,String media_server,String owner_id,String realm,String username){
	def lisKeyCustomChannelVarsParameters= GlobalVariable.customChannelVars_keys_list
	def lisValueOfCustomChannelVarsParameters=[account_id, GlobalVariable.app_parameters_values.get("authorizing_type"), GlobalVariable.app_parameters_values.get("app_name")+"@"+media_server, owner_id, realm, //verifying realm
		username]
	Map customChannelVarsparamMap =new LinkedHashMap<String,String>()
	for(int k=0;k<lisKeyCustomChannelVarsParameters.size();k++){
		customChannelVarsparamMap.put(lisKeyCustomChannelVarsParameters[k],lisValueOfCustomChannelVarsParameters[k])
	}
	return customChannelVarsparamMap

}
/*
 * Offnet Gateway calls
 * GET CDR_CDR_id API: Verifying dynamic value objects of jsonResponse which vary for each leg and in every scenario.
 */
@Keyword
def offnet_real_parameter_verification_CDRId(LazyMap responseLeg,String media_server,String presence_id,String to_uri,String from_uri,String toRealm,String fromRealm,String sipName){
	WS.verifyEqual(responseLeg.from_uri,from_uri+"@"+fromRealm)
	println "	from_uri is verified and value is : "+responseLeg.from_uri
	WS.verifyEqual(responseLeg.to_uri,to_uri+"@"+toRealm)
	println "	to_uri is verified and value is : "+responseLeg.to_uri
	WS.verifyEqual(responseLeg.switch_nodename, "freeswitch@"+media_server)
	println "	switch_nodename is verified and value is : "+responseLeg.switch_nodename
	WS.verifyEqual(responseLeg.switch_hostname,media_server )
	println "	switch_hostname is verified and value is : "+responseLeg.switch_hostname
	WS.verifyEqual(responseLeg.presence_id,presence_id+"@"+fromRealm)
	println "	presence_id is verified and value is : "+responseLeg.presence_id
	def listOfNode = getNodeValueList(media_server)
	assertTrue(listOfNode.contains(responseLeg.call_control.node))
	println "	node is verified and value is "+responseLeg.call_control.node
	WS.verifyEqual(responseLeg.channel_name,"sofia/sipinterface_1/"+sipName+"@"+fromRealm)
	println "	channel_name is verified and value is : "+responseLeg.channel_name

}


/*
 * Offnet termination gateway calls
 * GET CDR_CDR_id API: Verifying dynamic value objects of jsonResponse which vary for each leg and in every scenario.
 * Verify varying keys' value in outbound leg of offnet termination
 */
@Keyword
def offnet_outbound_real_parameter_verification_CDRId(LazyMap responseLeg,String media_server,String to_uri,String from_uri,String toRealm,String fromRealm,String sipName){
	WS.verifyEqual(responseLeg.from_uri,from_uri+"@"+fromRealm)
	println "	from_uri is verified and value is : "+responseLeg.from_uri
	WS.verifyEqual(responseLeg.to_uri,to_uri+"@"+toRealm)
	println "	to_uri is verified and value is : "+responseLeg.to_uri
	WS.verifyEqual(responseLeg.switch_nodename, "freeswitch@"+media_server)
	println "	switch_nodename is verified and value is : "+responseLeg.switch_nodename
	WS.verifyEqual(responseLeg.switch_hostname,media_server )
	println "	switch_hostname is verified and value is : "+responseLeg.switch_hostname
	def listOfNode = getNodeValueList(media_server)
	assertTrue(listOfNode.contains(responseLeg.call_control.node))
	println "	node is verified and value is "+responseLeg.call_control.node
	WS.verifyEqual(responseLeg.switch_url,"sip:mod_sofia@"+fromRealm+":11000")
	println "	switch_url is verified and value is : "+responseLeg.switch_url
	WS.verifyEqual(responseLeg.switch_uri,"sip:"+fromRealm+":11000")
	println "	switch_uri is verified and value is : "+responseLeg.switch_uri
	WS.verifyEqual(responseLeg.channel_name	,"sofia/sipinterface_1/"+sipName+"@"+toRealm)
	println "	channel_name is verified and value is : "+responseLeg.channel_name

}

/*Offnet Gateway calls
 *GET CDR_CDR_id API: Verifying extra key object in inbound leg of jsonResponse .
 */
@Keyword
def offnet_parameters_inboundLeg_CDRId(LazyMap responseLeg ,String media_server){
	WS.verifyEqual(responseLeg.custom_channel_vars.privacy_hide_name ,'false')
	println "	privacy_hide_name is verified and value is : "+responseLeg.custom_channel_vars.privacy_hide_name
	WS.verifyEqual(responseLeg.custom_channel_vars.privacy_hide_number,'false')
	println "	privacy_hide_number is verified and value is : "+responseLeg.custom_channel_vars.privacy_hide_number
	WS.verifyEqual(responseLeg.custom_channel_vars.application_name,GlobalVariable.app_parameters_values .get("application_name"))
	println "	application_name is verified and value is : "+responseLeg.custom_channel_vars.application_name
	WS.verifyEqual(responseLeg.custom_channel_vars.application_node,GlobalVariable.app_parameters_values .get("application_node")+"@"+media_server )
	println "	application_node is verified and value is : "+responseLeg.custom_channel_vars.application_node
	boolean result = (!StringUtils.isBlank(responseLeg.custom_channel_vars.callflow_id)
			&& !StringUtils.isBlank(responseLeg.custom_channel_vars.fetch_id))
	println "	callflow_id is verified and value is : "+responseLeg.custom_channel_vars.callflow_id
	println "	fetch_id is verified and value is : "+responseLeg.custom_channel_vars.fetch_id
	return(result)
}

/* Offnet calls
 * GET CDR_CDR_id API: Verifying value object of jsonResponse is present and not empty.
 * All keys object have system generated values.
 */
@Keyword
def offnet_app_parameters_system_generated_CDRId(LazyMap responseLeg){

	boolean output = (!StringUtils.isBlank(responseLeg.msg_id)
			&& !StringUtils.isBlank(responseLeg.other_leg_call_id)
			&& !StringUtils.isBlank(responseLeg.id)
			&& !StringUtils.isBlank(responseLeg.to_tag)
			&& !StringUtils.isBlank(responseLeg.from_tag)
			&& !StringUtils.isBlank(responseLeg.call_id)
			&& !StringUtils.isBlank(responseLeg.interaction_key)
			&& !StringUtils.isBlank(responseLeg.custom_channel_vars.bridge_id))
	println "	msg_id is verified and value is : "+responseLeg.msg_id
	println "	other_leg_call_id is verified and value is : "+responseLeg.other_leg_call_id
	println "	id is verified and value is : "+responseLeg.id
	println "	to_tag is verified and value is : "+responseLeg.to_tag
	println "	from_tag is verified and value is : "+responseLeg.from_tag
	println "	call_id is verified and value is : "+responseLeg.call_id
	println "	interaction_key is verified and value is : "+responseLeg.interaction_key
	println "	bridge_id is verified and value is : "+responseLeg.custom_channel_vars.bridge_id
	return(output)
}

/*
 * Verify the custom sip headers type
 *Verify global_resource value
 */
@Keyword
def customSipKeys(LazyMap responseLeg,String sipName,String account_realm){
	WS.verifyEqual(responseLeg.custom_sip_headers.x_kazoo_invite_format ,"contact")
	println "    x_kazoo_invite_format is verified and value is : "+responseLeg.custom_sip_headers.x_kazoo_invite_format
	WS.verifyEqual(responseLeg.custom_sip_headers.x_kazoo_aor ,"sip:"+sipName+"@"+account_realm)
	println "    x_kazoo_aor is verified and value is : "+responseLeg.custom_sip_headers.x_kazoo_aor
	WS.verifyEqual(responseLeg.user_agent ,"SIP.js/0.12.0")
	println "    user_agent is verified and value is : "+responseLeg.user_agent
}

/*
 *Verify common keys present in filter by cdr_id of response present for every scenario
 */
@Keyword
def commonKeys_CDRId(LazyMap apiResponse){
	WS.verifyEqual(apiResponse.version,GlobalVariable.version)
	println "    version is verified and value is : "+apiResponse.version
	WS.verifyEqual(apiResponse.status,"success" )
	println "    status is verified and value is : "+apiResponse.status
	boolean result = (!StringUtils.isBlank(apiResponse.revision)
			&& !StringUtils.isBlank(apiResponse.timestamp)
			&& !StringUtils.isBlank(apiResponse.node)
			&& !StringUtils.isBlank(apiResponse.request_id)
			&& !StringUtils.isBlank(apiResponse.auth_token))

	println "    revision is verified and value is : "+apiResponse.revision
	println "    timestamp is verified and value is : "+apiResponse.timestamp
	println "    node is verified and value is : "+apiResponse.node
	println "    request_id is verified and value is : "+apiResponse.request_id
	println "    auth_token is verified and value is : "+apiResponse.auth_token
	return (result)

}
/*
 *Verify common keys present in CDR response present for every scenario
 */
@Keyword
def commonKeys_CDR(LazyMap apiResponse){
	WS.verifyEqual(apiResponse.version,GlobalVariable.version)
	println "    version is verified and value is : "+apiResponse.version
	WS.verifyEqual(apiResponse.status,"success" )
	println "    status is verified and value is : "+apiResponse.status
	boolean result = (!StringUtils.isBlank(apiResponse.page_size)
			&& !StringUtils.isBlank(apiResponse.timestamp)
			&& !StringUtils.isBlank(apiResponse.node)
			&& !StringUtils.isBlank(apiResponse.request_id)
			&& !StringUtils.isBlank(apiResponse.auth_token)
			&& !StringUtils.isBlank(apiResponse.start_key))
	println "    page_size is verified and value is : "+apiResponse.page_size
	println "    timestamp is verified and value is : "+apiResponse.timestamp
	println "    node is verified and value is : "+apiResponse.node
	println "    request_id is verified and value is : "+apiResponse.request_id
	println "    auth_token is verified and value is : "+apiResponse.auth_token
	println "    start_key is verified and value is : "+apiResponse.start_key
	return(result)

}

/*
 * Verify sdp
 */
@Keyword
def verifySdp_CDR_Id(LazyMap responseLeg){
	boolean value =	(!StringUtils.isBlank(responseLeg.remote_sdp)
			&& !StringUtils.isBlank(responseLeg.local_sdp))
	println "    remote_sdp is verified and value is : "+responseLeg.remote_sdp
	println "    local_sdp is verified and value is : "+responseLeg.local_sdp
	return value
}

/*
 * Offnet termination
 * Verify specific objects related to termination present in outbound leg
 */
@Keyword
def offnet_parameters_resource_specific_CDR_Id(LazyMap responseLeg,matched_number,original_number,String destinationNum){
	WS.verifyEqual(responseLeg.custom_channel_vars.matched_number,matched_number)
	println "    matched_number is verified and value is : "+responseLeg.custom_channel_vars.matched_number
	WS.verifyEqual(responseLeg.custom_channel_vars.original_number,original_number )
	println "    original_number is verified and value is : "+responseLeg.custom_channel_vars.original_number
	assertTrue(!StringUtils.isBlank(responseLeg.custom_channel_vars.resource_id))
	WS.verifyEqual(responseLeg.e164_destination ,destinationNum)
	println "    resource_id is verified and value is : "+responseLeg.custom_channel_vars.resource_id
	println "    e164_destination is verified and value is : "+responseLeg.e164_destination
}

@Keyword
def get_carrier_and_node_IP_value(LazyMap responseLeg){
	def ipList=[]
	ipList.addAll(GlobalVariable.node_ip_list)
	ipList.addAll(GlobalVariable.carrier_ip_list)
	String ip_value = responseLeg.to
	ip_value = ip_value.substring((ip_value.indexOf("@")+1))
	if (ipList.contains(ip_value)) {

		GlobalVariable.node_ip = ip_value
	}

	String carrier_ip = responseLeg.from
	carrier_ip = carrier_ip.substring((carrier_ip.indexOf("@")+1))
	if (ipList.contains(carrier_ip)) {
		GlobalVariable.carrier_ip_value= carrier_ip
		println("Carrier_ip_value is: "+GlobalVariable.carrier_ip_value)
	}
}

/*
 * Offnet gateway calls
 * keys_list_CDR_Id:Made list of keys in a Global Variable
 * lisValueOfCDRId:Make list of values of API json response
 * key-value-Map:Making map of key object and value object of json response.
 * verify caller and callee details, to, from,request
 */
@Keyword
def offnet_response_real_expected_value_list_CDR_Id(LazyMap responseLeg, String caller_id_number,String caller_id_name, String callee_id_number,String callee_id_name,String to ,String from,String to_realm, String from_realm, String request){
	def lisKey=GlobalVariable.keys_list_CDR_Id
	def lisValueOfCDRId=[caller_id_number, caller_id_name, callee_id_number, callee_id_name, to+"@"+to_realm, from +"@"+from_realm, request+"@"+to_realm]
	Map keyValueMap =new LinkedHashMap<String,String>()
	for(int k=0;k<lisKey.size();k++){
		keyValueMap.put(lisKey[k],lisValueOfCDRId[k])
	}
	for(Map.Entry<String,String> entry : keyValueMap.entrySet()){
		def key=entry.getKey();
		def value=entry.getValue();
		try{

			assertEquals(value,responseLeg.get(key))
			println("Object Key value is correct: "+"key is: "+key+" actual value is: "+responseLeg.get(key)+" and expected value is: "+value)
		}catch(Exception e){
			println("Object Key value is Incorrect: "+"key is: "+key+" actual value is: "+responseLeg.get(key)+" and expected value is: "+value)
		}
	}
}
/*offnet calls
 * lisKeyCustomChannelVarsParameters:list of key objects of jsonResponse under custom_channel_vars
 * lisValueOfCustomChannelVarsParameters: list of expected values objects under custom_channel_vars
 * customChannelVarsparamMap: map of keys objects and expected values
 * Specific to outbound_offnet_termination
 */
@Keyword
def offnet_outbound_termination_customChannelVars_parameter_CDRId(LazyMap responseLeg,String account_id,String media_server){
	WS.verifyEqual(responseLeg.custom_channel_vars.account_id,GlobalVariable.gateway_call_account_details1.get("account_id"))
	println "    account_id is verified and value is : "+responseLeg.custom_channel_vars.account_id
	WS.verifyEqual(responseLeg.custom_channel_vars.channel_authorized,'true' )
	println "    channel_authorized is verified and value is : "+responseLeg.custom_channel_vars.channel_authorized
	WS.verifyEqual(responseLeg.custom_channel_vars.ecallmgr_node,GlobalVariable.app_parameters_values.get("app_name")+"@"+media_server )
	println "    ecallmgr_node is verified and value is : "+responseLeg.custom_channel_vars.ecallmgr_node
}

/*
 * Verify switch_url and switch_uri  object value  in response leg
 */
@Keyword
def switchIpVerification(LazyMap responseLeg){
	def ipList=[]
	ipList.addAll(GlobalVariable.node_ip_list)
	ipList.addAll(GlobalVariable.carrier_ip_list)
	for(String ip:ipList) {
		if(responseLeg.switch_url.contains(ip)) {
			WS.verifyEqual(responseLeg.switch_url,"sip:mod_sofia@"+ip+":11000")
			println "Verified value of switch_url and value is sip:mod_sofia@"+ip+":11000"
			WS.verifyEqual(responseLeg.switch_uri,"sip:"+ip+":11000")
			println "Verified value of switch_uri and value is sip:"+ip+":11000"
			GlobalVariable.switchIp=ip
			break;
		}
	}
}

/*
 * selecting media_server value at run-time
 */
@Keyword
def media_server_selection(LazyMap responseLeg){
	if (GlobalVariable.media_server_list.contains(responseLeg.media_server)) {
		GlobalVariable.media_server = responseLeg.media_server
	}
}

/* Offnet calls
 * GET CDR_CDR_id API: Verifying value object of jsonResponse is present and not empty.
 * All keys object have system generated values.
 */
@Keyword
def offnet_app_parameters_system_generated_CDRId_inbound(LazyMap responseLeg){

	boolean result = (!StringUtils.isBlank(responseLeg.msg_id)
			&& !StringUtils.isBlank(responseLeg.id)
			&& !StringUtils.isBlank(responseLeg.from_tag)
			&& !StringUtils.isBlank(responseLeg.call_id)
			&& !StringUtils.isBlank(responseLeg.interaction_key)
			&& !StringUtils.isBlank(responseLeg.custom_channel_vars.bridge_id))

	println "	msg_id is verified and value is : "+responseLeg.msg_id
	println "	id is verified and value is : "+responseLeg.id
	println "	from_tag is verified and value is : "+responseLeg.from_tag
	println "	call_id is verified and value is : "+responseLeg.call_id
	println "	interaction_key is verified and value is : "+responseLeg.interaction_key
	println "	bridge_id is verified and value is : "+responseLeg.custom_channel_vars.bridge_id

	WS.verifyEqual(responseLeg.media_server,GlobalVariable.media_server)
	println "	media_server is verified and value is : "+GlobalVariable.media_server
	return (result)
}

/*
 * store the node ip value
 * keyword is used for fax call
 */

@Keyword
def get_node_IP_value(LazyMap responseLeg){
	String ip_value = responseLeg.switch_url
	ip_value = ip_value.substring(ip_value.indexOf("@")+1)
	ip_value = ip_value.substring(0,ip_value.indexOf(":"))
	if (GlobalVariable.node_ip_list.contains(ip_value)) {

		GlobalVariable.node_ip = ip_value
	}
}

/*
 * Fetching caller_id_name value
 *
 */
@Keyword
def offnet_fetching_caller_id_name(LazyMap responseLeg){
	String phoneNumber1 = GlobalVariable.user1_creation_details.get('phone_number')
	assertTrue(responseLeg.caller_id_name.contains(GlobalVariable.user1_creation_details.get('first_name')) || phoneNumber1.substring(1, 12))
	GlobalVariable.offnet_caller_id_name=responseLeg.caller_id_name
}


/*
 * Passing list of legs of API jsonResponse
 * Iterating each leg and storing filters parameter.
 * Storing each filters of each leg in a list.
 */
@Keyword
def store_additional_filter_parameter(String account_id,String additional_filter){

	def response_cdr =(new cdr_Utility.CDR_API()).get_cdr_list(account_id)
	def response =  (new kazoo_operation_Utility.ApiCommonOperations()).parseResponseData(response_cdr)
	def jsonResponseBody = response.data

	assertFalse('jsonResponseBody is empty', jsonResponseBody.isEmpty())
	GlobalVariable.additionalFilterParameters=[]
	for(def leg :jsonResponseBody){
		def filterValue = "" +leg.get("${additional_filter}")
		if(StringUtils.isNotEmpty(filterValue)){
			GlobalVariable.additionalFilterParameters.add(leg.get("${additional_filter}"))
		}
	}
	println(GlobalVariable.additionalFilterParameters)
}

/*
 * Passing list of legs of API jsonResponse
 * Iterating each leg and storing custom channel vars filters parameter
 * Storing each filters of each leg in a list.
 */
@Keyword
def store_custom_channel_vars_filter_parameter(String account_id,String custom_channel_var_filter){

	def response_cdr =(new cdr_Utility.CDR_API()).get_cdr_list(account_id)
	def response =  (new kazoo_operation_Utility.ApiCommonOperations()).parseResponseData(response_cdr)
	def jsonResponseBody = response.data

	assertFalse('jsonResponseBody is empty', jsonResponseBody.isEmpty())
	(new cdr_Utility.CDR_data_verification()).store_filter_parameter(jsonResponseBody)
	assertFalse('filterParameterPerLeg is empty', GlobalVariable.filterParametersPerLeg.isEmpty())
	GlobalVariable.customChannelVarsFilterParameters=[]
	for (def filterPerLeg : GlobalVariable.filterParametersPerLeg){
		def cdrIdResponse =(new cdr_Utility.CDR_API()).get_CDR_byCDRId(account_id,filterPerLeg.get('cdr_id'))
		def apiResponse=(new kazoo_operation_Utility.ApiCommonOperations()).parseResponseData(cdrIdResponse)
		if(!(apiResponse.data.custom_channel_vars."${custom_channel_var_filter}").equals(null)){
			GlobalVariable.customChannelVarsFilterParameters.add(apiResponse.data.custom_channel_vars."${custom_channel_var_filter}")
		}
	}
	println(GlobalVariable.customChannelVarsFilterParameters)
}


/*
 * Offnet gateway calls
 * verification_keys_list:Made list of keys in a Global Variable
 * listValueOfCDR:Make list of values of API json response
 * key-value-Map:Making map of key object and value object of json response.
 * verify caller and callee details, to, from,request
 */
@Keyword
def offnet_response_real_expected_value_list(LazyMap responseLeg, String caller_id_number,String caller_id_name, String callee_id_number,String callee_id_name,String to ,String from,String to_realm, String from_realm,String dialed_num,String calling_from, String request){
	def listKey=GlobalVariable.verification_keys_list
	def listValueOfCDR=[caller_id_number, caller_id_name, callee_id_number, callee_id_name, to+"@"+to_realm, from +"@"+from_realm, dialed_num, calling_from, request+"@"+to_realm]
	Map keyValueMap =new LinkedHashMap<String,String>()
	for(int k=0;k<listKey.size();k++){
		keyValueMap.put(listKey[k],listValueOfCDR[k])
	}
	for(Map.Entry<String,String> entry : keyValueMap.entrySet()){
		def key=entry.getKey();
		def value=entry.getValue();
		try{
			assertEquals(value,responseLeg.get(key))
			println("Object Key value is correct: "+"key is: "+key+" actual value is: "+responseLeg.get(key)+" and expected value is: "+value)
		}catch(Exception e){
			println("Object Key value is Incorrect: "+"key is: "+key+" actual value is: "+responseLeg.get(key)+" and expected value is: "+value)
		}
	}
}

/*
 * Get response of GET_CDR by CDR_ID
 * store the value of filter parameter from response
 * cross check filter parameter present in filtered responses
 */
@Keyword
def verify_filter_parameter(LazyMap responseLeg,String account_id,String filterParameter,String realFilterValue){

	def cdrIdResponse =(new cdr_Utility.CDR_API()).get_CDR_byCDRId(account_id,responseLeg.id)
	def response=(new kazoo_operation_Utility.ApiCommonOperations()).parseResponseData(cdrIdResponse)
	def filterValue = response.data.custom_channel_vars.get(filterParameter)
	WS.verifyEqual(realFilterValue, filterValue, FailureHandling.STOP_ON_FAILURE)
}

/*
 * Fetching all call  details like caller_id_name,callee_id_name
 */
@Keyword
def offnet_conference_calling_details_cdr(String caller_name,String callee_id_name,String callee_id_number,
		String from,String request,String dialedNum,String calling_from){
	def listOfExpectedValue=[caller_name, callee_id_number, callee_id_name, from, dialedNum, calling_from, request]
	def lisKey= GlobalVariable.verification_keys_list_conference
	Map paramMap =new LinkedHashMap<String,String>()
	for(int k=0;k<lisKey.size();k++){
		paramMap.put(lisKey[k],listOfExpectedValue[k])
	}
	println("printing paramMap for GET CDR ################ "+paramMap.entrySet())
	return paramMap;
}

/*
 * Fetching all call  details like caller_id_name,from,request
 */
@Keyword
def offnet_conference_calling_details_cdrId(LazyMap responseLeg,String caller_id_number,String from,String request ,String toIp,String fromIp){
	WS.verifyEqual(responseLeg.caller_id_number, caller_id_number)
	println "	caller_id_number is verified and value is : "+responseLeg.caller_id_number
	WS.verifyEqual(responseLeg.caller_id_name, GlobalVariable.offnet_caller_id_name)
	println "	caller_id_name is verified and value is : "+responseLeg.caller_id_name
	WS.verifyEqual(responseLeg.from, from+"@"+fromIp)
	println "	from is verified and value is : "+responseLeg.from
	WS.verifyEqual(responseLeg.request, request+"@"+toIp)
	println "	request is verified and value is : "+responseLeg.request
}

/*Offnet Gateway calls
 *GET CDR_CDR_id API: Verifying custom channel vars of conference call
 *callee details is not present in this leg
 */
@Keyword
def offnet_custom_channel_vars_conference_CDRId(LazyMap responseLeg ,String media_server){
	WS.verifyEqual(responseLeg.custom_channel_vars.privacy_hide_name ,'false')
	println "	privacy_hide_name is verified and value is : "+responseLeg.custom_channel_vars.privacy_hide_name
	WS.verifyEqual(responseLeg.custom_channel_vars.privacy_hide_number,'false')
	println "	privacy_hide_number is verified and value is : "+responseLeg.custom_channel_vars.privacy_hide_number
	WS.verifyEqual(responseLeg.custom_channel_vars.ecallmgr_node,GlobalVariable.app_parameters_values .get("app_name")+"@"+media_server)
	println "	ecallmgr_node is verified and value is : "+responseLeg.custom_channel_vars.ecallmgr_node
	WS.verifyEqual(responseLeg.custom_channel_vars.application_name,"conference")
	println "	application_name is verified and value is : "+responseLeg.custom_channel_vars.application_name
	WS.verifyEqual(responseLeg.custom_channel_vars.application_node,GlobalVariable.app_parameters_values .get("application_node")+"@"+media_server )
	println "	application_node is verified and value is : "+responseLeg.custom_channel_vars.application_node
	boolean result = (!StringUtils.isBlank(responseLeg.custom_channel_vars.bridge_id)
			&& !StringUtils.isBlank(responseLeg.custom_channel_vars.fetch_id))
	println "	bridge_id is verified and value is : "+responseLeg.custom_channel_vars.bridge_id
	println "	fetch_id is verified and value is : "+responseLeg.custom_channel_vars.fetch_id
	return (result)
}


/*
 * Offnet Gateway calls
 * GET CDR_CDR_id API: Verifying dynamic value objects of jsonResponse which vary for each leg and in every scenario.
 */
@Keyword
def offnet_conference_real_parameter_verification_CDRId(LazyMap responseLeg,String media_server,String presence_id,String from_uri,String fromRealm,String sipName){
	WS.verifyEqual(responseLeg.from_uri,from_uri+"@"+fromRealm)
	println "	from_uri is verified and value is : "+responseLeg.from_uri
	WS.verifyEqual(responseLeg.switch_nodename, "freeswitch@"+media_server)
	println "	switch_nodename is verified and value is : "+responseLeg.switch_nodename
	WS.verifyEqual(responseLeg.switch_hostname,media_server )
	println "	switch_hostname is verified and value is : "+responseLeg.switch_hostname
	WS.verifyEqual(responseLeg.presence_id,presence_id+"@"+fromRealm)
	println "	presence_id is verified and value is : "+responseLeg.presence_id
	def listOfNode = getNodeValueList(media_server)
	assertTrue(listOfNode.contains(responseLeg.call_control.node))
	println "	node is verified and value is "+responseLeg.call_control.node
	WS.verifyEqual(responseLeg.channel_name,"sofia/sipinterface_1/"+sipName+"@"+fromRealm)
	println "	channel_name is verified and value is : "+responseLeg.channel_name
}

@Keyword
def key_verification(LazyMap responseLeg, List key_list, Set responseLeg_key_list){
	println("Entering into key verification")
	println("Printing Expectd key_list "+key_list)
	println("Printing actual key_list "+responseLeg_key_list)

	if(!responseLeg_key_list.empty){
		for (String key in key_list){
			if (key in responseLeg_key_list){
				println "${key} is present in response leg and values is:" + responseLeg.get(key)
			}
			else {
				println "${key} is not present in response leg "
			}
		}//for loop
	}//end of kw
}


@Keyword
def non_mandatory_parameter_verification(LazyMap responseLeg, List key_list){
	for (String object in GlobalVariable.object_list){
		try {
			def objLeg_key_list = responseLeg."${object}".keySet()
			println "Verifing parameters in object ============= ${object} ==================="
			key_verification(responseLeg."${object}", key_list, objLeg_key_list)

			println ("Verification completed under ================ ${object} ========================"+"\n")
		}catch (NullPointerException e){
			println "${object} parameters are not present in this leg"
		}
	}//end of for loop
	def responseLeg_key_list = responseLeg.keySet()
	println ("Verify parameters in response leg "+responseLeg_key_list)
	key_verification( responseLeg, key_list, responseLeg_key_list)
}//end of kw

/*
 * Verify object by selecting ip using "from" object value  in response leg
 */
@Keyword
def ipSelectionByFrom(LazyMap responseLeg){
	def ipList=[]
	ipList.addAll(GlobalVariable.node_ip_list)
	ipList.addAll(GlobalVariable.carrier_ip_list)
	for(String ip:ipList) {
		if(responseLeg.from.contains(ip)) {
			GlobalVariable.switchIp=ip
			break;
		}
	}
}

/*
 * Verify mandatory parameters timestamp, channel_created_time, duration_seconds, ringing seconds, billing_seconds,
 *  reseller_id, rate, rate_name, surcharge, reseller_billing, rate_no_charge, rate_minimum, rate_increment,
 *  rate_description, pvt_cost in  CDRID offnet-origination Inbound, InteractionID offnet-origination  Inbound leg
 *
 */
@Keyword
def offNetMandatoryParameterVerificationKW1(LazyMap responseLeg){
	assertNotNull("Timestamp is null.", responseLeg.timestamp)
	println "	timestamp is verified and value is: " + responseLeg.timestamp
	assertNotNull("channel_created_time is null.", responseLeg.channel_created_time)
	println "	channel_created_time is verified and value is: " + responseLeg.channel_created_time
	assertTrue(responseLeg.duration_seconds instanceof Integer)
	println "	duration_seconds is verified and value is: " + responseLeg.duration_seconds
	assertTrue(responseLeg.ringing_seconds instanceof Integer)
	println "	ringing_seconds is verified and value is: " + responseLeg.ringing_seconds
	assertTrue(responseLeg.billing_seconds instanceof Integer)
	println "	billing_seconds is verified and value is: " + responseLeg.billing_seconds
	WS.verifyEqual(responseLeg.custom_channel_vars.reseller_id, GlobalVariable.parent_account_id)
	println "	reseller_id is verified and value is: " + responseLeg.custom_channel_vars.reseller_id
	WS.verifyEqual(responseLeg.custom_channel_vars.rate, "2262")
	println "	rate is verified and value is: " + responseLeg.custom_channel_vars.rate
	WS.verifyEqual(responseLeg.custom_channel_vars.rate_name, "ANY")
	println "	rate_name is verified and value is: " + responseLeg.custom_channel_vars.rate_name
	assertNotNull("account_trunk_usage is null.", responseLeg.custom_channel_vars.account_trunk_usage)
	println "account_trunk_usage is verified and value is: " + responseLeg.custom_channel_vars.account_trunk_usage
	WS.verifyEqual(responseLeg.custom_channel_vars.surcharge, "0")
	println "	surcharge is verified value is: " + responseLeg.custom_channel_vars.surcharge
	assertNotNull("reseller_trunk_usage is null.", responseLeg.custom_channel_vars.reseller_trunk_usage)
	println "	reseller_trunk_usage is verified and value is :" + responseLeg.custom_channel_vars.reseller_trunk_usage
	WS.verifyEqual(responseLeg.custom_channel_vars.reseller_billing, "limits_disabled")
	println "	reseller_billing is verified and value is: " + responseLeg.custom_channel_vars.reseller_billing
	WS.verifyEqual(responseLeg.custom_channel_vars.rate_nocharge_time, "0")
	println "	rate_nocharge_time is verified and value is:" + responseLeg.custom_channel_vars.rate_nocharge_time
	WS.verifyEqual(responseLeg.custom_channel_vars.rate_minimum, "60")
	println "	rate_minimum is verified and value is: " + responseLeg.custom_channel_vars.rate_minimum
	WS.verifyEqual(responseLeg.custom_channel_vars.rate_increment, "60")
	println "	rate_increment is verified and value is: " + responseLeg.custom_channel_vars.rate_increment
	WS.verifyEqual(responseLeg.custom_channel_vars.rate_description, "ANY")
	println "	rate_description is verified and value is: " + responseLeg.custom_channel_vars.rate_description
	WS.verifyEqual(responseLeg.custom_channel_vars.pvt_cost, "0")
	println "	pvt_cost is verified and value is: " + responseLeg.custom_channel_vars.pvt_cost

}//end of kw

/*
 * Verify mandatory parameters timestamp, channel_created_time, duration_seconds, ringing seconds, billing_seconds, reseller_id in Offnet CDRID, offnet-termination Inbound leg
 *
 */
def offNetMandatoryParameterVerificationKW2(LazyMap responseLeg){
	assertNotNull("Timestamp is null.", responseLeg.timestamp)
	println "	timestamp is verified and value is: " + responseLeg.timestamp
	assertNotNull("channel_created_time is null.", responseLeg.channel_created_time)
	println "	channel_created_time is verified and value is: " + responseLeg.channel_created_time
	assertTrue(responseLeg.duration_seconds instanceof Integer)
	println " 	duration_seconds is verified and value is: " + responseLeg.duration_seconds
	assertTrue(responseLeg.ringing_seconds instanceof Integer)
	println "	ringing_seconds is verified and value is: " + responseLeg.ringing_seconds
	assertTrue(responseLeg.billing_seconds instanceof Integer)
	println "	billing_seconds is verified and value is: " + responseLeg.billing_seconds
	WS.verifyEqual(responseLeg.custom_channel_vars.reseller_id, GlobalVariable.parent_account_id)
	println "	reseller_id is verified and value is: " + responseLeg.custom_channel_vars.reseller_id

}//end of kw

/*
 * Verify mandatory parameters timestamp, channel_created_time, duration_seconds, ringing seconds, billing_seconds in InteractionID offnet-origination Outbound, CDRID offnet-origination  Outbound, Onnet CDRID inbound/outbound, Onnet InteractionID Inbound/Outbound leg
 *
 */
@Keyword
def onNetOffNetMandatoryParameterVerificationKW3(LazyMap responseLeg){
	assertNotNull("Timestamp is null.", responseLeg.timestamp)
	println "	timestamp is verified and value is: " + responseLeg.timestamp
	assertNotNull("channel_created_time is null.", responseLeg.channel_created_time)
	println "	channel_created_time is verified and value is: " + responseLeg.channel_created_time
	assertTrue(responseLeg.duration_seconds instanceof Integer)
	println "	duration_seconds is verified and value is: " + responseLeg.duration_seconds
	assertTrue(responseLeg.ringing_seconds instanceof Integer)
	println "	ringing_seconds is verified and value is: " + responseLeg.ringing_seconds
	assertTrue(responseLeg.billing_seconds instanceof Integer)
	println "	billing_seconds is verified and value is: " + responseLeg.billing_seconds
}//end of kw

/*
 * Verify mandatory parameters timestamp, channel_created_time, duration_seconds, ringing seconds, billing_seconds, reseller_id, rate, rate_name, account_trunk_usage surcharg,
 * reseller_trunk_usage, reseller_billing, rate_no_charge, rate_minimum, rate_increament, rate_description, pvt_cost in CDRID offnet-Termination Outbound, InteractionID Offnet-termination Inbound, InteractionID offnet-Termination Outbound leg
 *
 */
@Keyword
def offNetMandatoryParameterVerificationKW4(LazyMap responseLeg){
	assertNotNull("Timestamp is null.", responseLeg.timestamp)
	println "	timestamp is verified and value is: " + responseLeg.timestamp
	assertNotNull("channel_created_time is null.", responseLeg.channel_created_time)
	println "	channel_created_time is verified and value is: " + responseLeg.channel_created_time
	assertTrue(responseLeg.duration_seconds instanceof Integer)
	println "	duration_seconds is verified and value is: " + responseLeg.duration_seconds
	assertTrue(responseLeg.ringing_seconds instanceof Integer)
	println "	ringing_seconds is verified and value is: " + responseLeg.ringing_seconds
	assertTrue(responseLeg.billing_seconds instanceof Integer)
	println "	billing_seconds is verified and value is: " + responseLeg.billing_seconds
	WS.verifyEqual(responseLeg.custom_channel_vars.reseller_id, GlobalVariable.parent_account_id)
	println "	reseller_id is verified and value is: " + responseLeg.custom_channel_vars.reseller_id
	WS.verifyEqual(responseLeg.custom_channel_vars.rate, "2262")
	println "	rate is verified and value is :" + responseLeg.custom_channel_vars.rate
	WS.verifyEqual(responseLeg.custom_channel_vars.rate_name, "ANY")
	println "	rate_name is verified and value is: " + responseLeg.custom_channel_vars.rate_name
	assertNotNull("account_trunk_usage is null.", responseLeg.custom_channel_vars.account_trunk_usage)
	println "	account_trunk_usage is verified and value is: " + responseLeg.custom_channel_vars.account_trunk_usage
	WS.verifyEqual(responseLeg.custom_channel_vars.surcharge, "0")
	println "	surcharge is verified value is: " + responseLeg.custom_channel_vars.surcharge
	assertNotNull("reseller_trunk_usage is null.", responseLeg.custom_channel_vars.reseller_trunk_usage)
	println "	reseller_trunk_usage is verified and value is: " + responseLeg.custom_channel_vars.reseller_trunk_usage
	WS.verifyEqual(responseLeg.custom_channel_vars.reseller_billing, "flat_rate")
	println "	reseller_billing is verified and value is: " + responseLeg.custom_channel_vars.reseller_billing
	WS.verifyEqual(responseLeg.custom_channel_vars.rate_nocharge_time, "0")
	println "	rate_nocharge_time is verified and value is: " + responseLeg.custom_channel_vars.rate_nocharge_time
	WS.verifyEqual(responseLeg.custom_channel_vars.rate_minimum, "60")
	println "	rate_minimum is verified and value is: " + responseLeg.custom_channel_vars.rate_minimum
	WS.verifyEqual(responseLeg.custom_channel_vars.rate_increment, "60")
	println "	rate_increment is verified and value is: " + responseLeg.custom_channel_vars.rate_increment
	WS.verifyEqual(responseLeg.custom_channel_vars.rate_description, "ANY")
	println "	rate_description is verified and value is: " + responseLeg.custom_channel_vars.rate_description
	WS.verifyEqual(responseLeg.custom_channel_vars.pvt_cost, "0")
	println "	pvt_cost is verified and value is: " + responseLeg.custom_channel_vars.pvt_cost

}//end of kw


/*
 * Verify mandatory parameters timestamp,  duration_seconds, billing_seconds, call_priority, call_type, cost,
 *  datetime,iso_8601, iso_8601_combined, rate, rate_name, rfc_1036, Unix_timestamp, reseller_cost in Onnet callId, UserID,
 *   CDR offnet-origination Outbound, CDR offnet-termination Inbound leg
 *
 */
@Keyword
def OnNet_OffNetMandatoryParameterVerificationKW5(LazyMap responseLeg){
	assertNotNull("Timestamp is null.", responseLeg.timestamp)
	println "	timestamp is verified and value is: " + responseLeg.timestamp
	assertTrue(responseLeg.duration_seconds instanceof Integer)
	println "	duration_seconds is verified and value is: " + responseLeg.duration_seconds
	assertTrue(StringUtils.isBlank(responseLeg.recording_url))
	println "	recording_url is verified and value is blank"
	assertTrue(responseLeg.billing_seconds instanceof Integer)
	println "	billing_seconds is verified and value is:" + responseLeg.billing_seconds
	assertTrue(StringUtils.isBlank(responseLeg.call_priority))
	println "	call_priority is verified and value is blank"
	assertTrue(StringUtils.isBlank(responseLeg.call_type))
	println "	call_type is verified and value is blank"
	WS.verifyEqual(responseLeg.cost, "0")
	println "	cost is verified and value is: " + responseLeg.cost
	assertNotNull("rfc_1036 is null.", responseLeg.datetime)
	println "	datetime is verified and value is: " + responseLeg.datetime
	assertNotNull("rfc_1036 is null.", responseLeg.iso_8601)
	println "	iso_8601 is verified and value is: " + responseLeg.iso_8601
	assertNotNull("rfc_1036 is null.", responseLeg.iso_8601_combined)
	println "	iso_8601_combined is verified and value is: " + responseLeg.iso_8601_combined
	assertTrue(responseLeg.media_recordings.isEmpty())
	println "	media_recording is verified and value is empty "
	WS.verifyEqual(responseLeg.rate, "0")
	println "	rate is verified and value is:" + responseLeg.rate
	assertTrue(StringUtils.isBlank(responseLeg.rate_name))
	println "	rate_name is verified and value is blank"
	assertNotNull("rfc_1036 is null.", responseLeg.rfc_1036)
	println "	rfc_1036 is verified and value is " + responseLeg.rfc_1036
	assertNotNull("unix_timestamp is null.", responseLeg.unix_timestamp)
	println "	unix_timestamp is verified and value is: " + responseLeg.unix_timestamp
	WS.verifyEqual(responseLeg.reseller_cost, "0")
	println "	reseller_cost is verified and value is " + responseLeg.reseller_cost
	assertTrue(StringUtils.isBlank(responseLeg.reseller_call_type))
	println "	reseller_call_type is verified and value is blank"

}//end of kw

/*
 * Verify mandatory parameters timestamp,  duration_seconds, billing_seconds, call_priority, call_type, cost,
 *  datetime,iso_8601, iso_8601_combined, media_recordings,rate, rate_name, rfc_1036, Unix_timestamp, reseller_cost,
 *  reseller_call_type in CDR Offnet-origination Inbound leg.
 *
 */
@Keyword
def offNetMandatoryParameterVerificationKW6(LazyMap responseLeg){
	assertNotNull("Timestamp is null.", responseLeg.timestamp)
	println "	timestamp is verified and value is: " + responseLeg.timestamp
	assertTrue(responseLeg.duration_seconds instanceof Integer)
	println " 	duration_seconds is verified and value is: " + responseLeg.duration_seconds
	assertTrue(StringUtils.isBlank(responseLeg.recording_url))
	println " 	recording_url is verified and value is blank"
	assertTrue(responseLeg.billing_seconds instanceof Integer)
	println "	billing_seconds is verified and value is:" + responseLeg.billing_seconds
	assertTrue(StringUtils.isBlank(responseLeg.call_priority))
	println " 	call_priority is verified and value is blank"
	WS.verifyEqual(responseLeg.call_type, "limits_disabled")
	println " 	call_type is verified and value is: " + responseLeg.call_type
	WS.verifyEqual(responseLeg.cost, "0")
	println " 	cost is verified and value is " + responseLeg.cost
	assertNotNull("rfc_1036 is null.", responseLeg.datetime)
	println "	datetime is verified and value is " + responseLeg.datetime
	assertNotNull("iso_8601 is null.", responseLeg.iso_8601)
	println "	 iso_8601 is verified and value is " + responseLeg.iso_8601
	assertNotNull("iso_8601_combined is null.", responseLeg.iso_8601_combined)
	println "	iso_8601_combined is verified and value is " + responseLeg.iso_8601_combined
	assertTrue(responseLeg.media_recordings.isEmpty())
	println "	media_recording is verified and value is empty "
	WS.verifyEqual(responseLeg.rate, "0.2262")
	println "	rate is verified and value is: " + responseLeg.rate
	WS.verifyEqual(responseLeg.rate_name, "ANY")
	println "	rate_name is verified and value is: " + responseLeg.rate_name
	assertNotNull("rfc_1036 is null.", responseLeg.rfc_1036)
	println " 	rfc_1036 is verified and value is: " + responseLeg.rfc_1036
	assertNotNull("unix_timestamp is null.", responseLeg.unix_timestamp)
	println "	unix_timestamp is verified and value is: " + responseLeg.unix_timestamp
	WS.verifyEqual(responseLeg.reseller_cost, "0")
	println "	reseller_cost is verified and value is " + responseLeg.reseller_cost
	WS.verifyEqual(responseLeg.reseller_call_type, "limits_disabled")
	println "	reseller_call_type is verified and value is: " + responseLeg.reseller_call_type

}//end of kw

/*
 * Verify mandatory parameters timestamp,  duration_seconds, billing_seconds, call_priority, call_type, cost, datetime,iso_8601,
 * iso_8601_combined, media_recordings,rate, rate_name, rfc_1036, Unix_timestamp, reseller_cost, reseller_call_type in
 *   CDR Offnet-Termination Outbound leg
 *
 */
@Keyword
def offNetMandatoryParameterVerificationKW7(LazyMap responseLeg){
	assertNotNull("Timestamp is null.", responseLeg.timestamp)
	println "	timestamp is verified and value is: " + responseLeg.timestamp
	assertTrue(responseLeg.duration_seconds instanceof Integer)
	println " 	duration_seconds is verified and value is: " + responseLeg.duration_seconds
	assertTrue(StringUtils.isBlank(responseLeg.recording_url))
	println " 	recording_url is verified and value is blank"
	assertTrue(responseLeg.billing_seconds instanceof Integer)
	println "	billing_seconds is verified and value is: " + responseLeg.billing_seconds
	assertTrue(StringUtils.isBlank(responseLeg.call_priority))
	println " 	call_priority is verified and value is blank"
	WS.verifyEqual(responseLeg.call_type, "flat_rate")
	println " 	call_type is verified and value is: " + responseLeg.call_type
	WS.verifyEqual(responseLeg.cost, "0")
	println " 	cost is verified and value is " + responseLeg.cost
	assertNotNull("rfc_1036 is null.", responseLeg.datetime)
	println "	datetime is verified and value is: " + responseLeg.datetime
	assertNotNull("iso_8601 is null.", responseLeg.iso_8601)
	println " 	iso_8601 is verified and value is: " + responseLeg.iso_8601
	assertNotNull("iso_8601_combined is null.", responseLeg.iso_8601_combined)
	println "	iso_8601_combined is verified and value is " + responseLeg.iso_8601_combined
	assertTrue(responseLeg.media_recordings.isEmpty())
	println "	media_recording is verified and value is empty "
	WS.verifyEqual(responseLeg.rate, "0.2262")
	println "	rate is verified and value is: " + responseLeg.rate
	WS.verifyEqual(responseLeg.rate_name, "ANY")
	println "	rate_name is verified and value is: " + responseLeg.rate_name
	assertNotNull("rfc_1036 is null.", responseLeg.rfc_1036)
	println " 	rfc_1036 is verified and value is: " + responseLeg.rfc_1036
	assertNotNull("unix_timestamp is null.", responseLeg.unix_timestamp)
	println "	unix_timestamp is verified and value is: " + responseLeg.unix_timestamp
	WS.verifyEqual(responseLeg.reseller_cost, "0")
	println "	reseller_cost is verified and value is: " + responseLeg.reseller_cost
	WS.verifyEqual(responseLeg.reseller_call_type, "flat_rate")
	println "	reseller_call_type is verified and value is: " + responseLeg.reseller_call_type

}//end of kw


@Keyword
def get_toRealm_and_fromRealm(LazyMap responseLeg){
	String ip_value = responseLeg.to_uri
	ip_value = ip_value.substring((ip_value.indexOf("@")+1))
	GlobalVariable.node_ip = ip_value

	String carrier_ip = responseLeg.from_uri
	carrier_ip = carrier_ip.substring((carrier_ip.indexOf("@")+1))
	GlobalVariable.carrier_ip_value= carrier_ip
	println(GlobalVariable.carrier_ip_value)
}

/*
 * Verify object by selecting ip using "to_uri" object value  in response leg
 * keyword is specific to fax_call
 */
@Keyword
def ipSelectionByToUri(LazyMap responseLeg){
	String ip_value = responseLeg.to_uri
	ip_value = ip_value.substring((ip_value.indexOf("@")+1))
	return(ip_value)
}

/*
 * Verifying value object of jsonResponse is present and not empty
 * All keys object have system generated values
 * keyword is specific to offnet fax call
 */
@Keyword
def fax_info_parameters_system_generated_CDRId(LazyMap responseLeg){
	WS.verifyEqual(responseLeg.fax_info.fax_ecm_used,GlobalVariable.fax_ecm_used)
	println("  fax_ecm_used is verified and value is :" +responseLeg.fax_info.fax_ecm_used)
	assertTrue(!StringUtils.isBlank(responseLeg.fax_info.fax_image_size))
	println("  fax_image_size is verified and value is :" +responseLeg.fax_info.fax_image_size)
	assertTrue(!StringUtils.isBlank(responseLeg.fax_info.fax_doc_id))
	println("  fax_doc_id is verified and value is :" +responseLeg.fax_info.fax_doc_id)
	assertTrue(!StringUtils.isBlank(responseLeg.fax_info.fax_doc_db))
	println("  fax_doc_db is verified and value is :" +responseLeg.fax_info.fax_doc_db)
}

/*
 * Verify object by selecting ip using "to_uri" object value  in response leg
 * keyword is specific to fax_call
 */
@Keyword
def ipSelectionByInception(LazyMap responseLeg){
	String ipValue = responseLeg.custom_channel_vars.inception
	ipValue = ipValue.substring((ipValue.indexOf("@")+1))
	for(String ip:GlobalVariable.node_ip_list) {
		if(ipValue.equals(ip)) {
			GlobalVariable.node_ip=ipValue
		}
	}
}

/*
 * GET CDR_CDR_id API: Verifying value object of jsonResponse is present and not empty.
 * All keys object have system generated values.
 * keyword is specific to No answer call scenario e.g.voicemail access
 */
@Keyword
def app_parameters_system_generated_CDRId_no_answer(LazyMap responseLeg){

	assertTrue(!StringUtils.isBlank(responseLeg.custom_channel_vars.authorizing_id))
	println("	authorizing_id is verified and value is :" +responseLeg.custom_channel_vars.authorizing_id)
	boolean result = (!StringUtils.isBlank(responseLeg.msg_id)
			&& !StringUtils.isBlank(responseLeg.from_tag)
			&& !StringUtils.isBlank(responseLeg.call_id)
			&& !StringUtils.isBlank(responseLeg.interaction_key)
			&& !StringUtils.isBlank(responseLeg.custom_channel_vars.bridge_id)
			&& !StringUtils.isBlank(responseLeg.id))

	println("	msg_id is verified and value is :" +responseLeg.msg_id)
	println("	from_tag is verified and value is :" +responseLeg.from_tag)
	println("	call_id is verified and value is :" +responseLeg.call_id)
	println("	interaction_key is verified and value is :" +responseLeg.interaction_key)
	println("	bridge_id is verified and value is :" +responseLeg.custom_channel_vars.bridge_id)
	println("	id is verified and value is :" +responseLeg.id)
	return (result)
}

/**
 * Keyword 1
 * Having all key objects list of 4.3 version
 * Storing all keylist to map as a value
 */
def storeAllKeySet(){
	//Contain keys of all offnet scenarios
	def getCDRIDKeyListForCommonObjectsOutSideLeg	=							["revision", "timestamp", "version", "node", "request_id", "status", "auth_token", "data"]
	def getCDRIdListOfKeyForOffnetCallerCalleeTerminatesOriginationInbound =	["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "disposition", "duration_seconds", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "hangup_code", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "other_leg_call_id", "other_leg_caller_id_name", "other_leg_caller_id_number", "other_leg_destination_number", "other_leg_direction", "presence_id", "remote_sdp", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_tag", "to_uri", "account_billing", "account_id", "account_trunk_usage", "application_name", "application_node", "base_cost", "bridge_id", "callflow_id", "channel_authorized", "ecallmgr_node", "fetch_id" , "global_resource", "inception", "privacy_hide_name", "privacy_hide_number" , "pvt_cost", "rate", "rate_description", "rate_increment", "rate_minimum", "rate_name", "rate_nocharge_time", "realm", "reseller_billing", "reseller_id", "reseller_trunk_usage", "resource_type", "surcharge"]
	def getCDRIdListOfKeyForOffnetCallerCalleeTerminatesOriginationOutbound =	["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "disposition", "duration_seconds", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "hangup_code", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "other_leg_call_id", "other_leg_caller_id_name", "other_leg_caller_id_number", "other_leg_destination_number", "other_leg_direction", "presence_id", "remote_sdp", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_tag", "to_uri", "user_agent", "account_id", "authorizing_id", "authorizing_type", "bridge_id", "channel_authorized", "ecallmgr_node", "global_resource", "inception", "owner_id", "realm", "username", "x_kazoo_aor", "x_kazoo_invite_format"]
	def getCDRIdListOfKeyForOffnetCallerCalleeTerminatesTerminationOutbound=	["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "disposition", "duration_seconds", "e164_destination", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "hangup_code", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "other_leg_call_id", "other_leg_caller_id_name", "other_leg_caller_id_number", "other_leg_destination_number", "other_leg_direction", "remote_sdp", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_tag", "to_uri", "account_billing", "account_id", "account_trunk_usage", "base_cost", "bridge_id", "channel_authorized", "ecallmgr_node", "global_resource", "matched_number", "original_number", "pvt_cost", "rate", "rate_description", "rate_increment", "rate_minimum", "rate_name", "rate_nocharge_time", "realm", "reseller_billing", "reseller_id", "reseller_trunk_usage", "resource_id", "resource_type", "surcharge"]
	def getCDRIdListOfKeyForOffnetCallerCalleeTerminatesTerminationInbound=		["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "disposition", "duration_seconds", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "hangup_code", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "other_leg_call_id", "other_leg_caller_id_name", "other_leg_caller_id_number", "other_leg_destination_number", "other_leg_direction", "presence_id", "remote_sdp", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_tag", "to_uri", "user_agent", "account_id", "account_name", "account_realm", "application_name", "application_node", "authorizing_id", "authorizing_type", "bridge_id", "callflow_id", "channel_authorized", "ecallmgr_node", "fetch_id", "global_resource", "owner_id", "privacy_hide_name", "privacy_hide_number", "realm", "reseller_id", "username"]
	def getCDRIdListOfKeyOffnetConferenceCaller2or3CalleeConference =			["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "disposition", "duration_seconds", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "hangup_code", "id", "interaction_id", "interaction_key", "interaction_time", "is_conference", "local_sdp", "media_server", "msg_id", "node", "other_leg_call_id", "other_leg_caller_id_name", "other_leg_caller_id_number", "other_leg_destination_number", "other_leg_direction", "presence_id", "remote_sdp", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_tag", "to_uri", "account_billing", "account_id", "account_trunk_usage", "application_name", "application_node", "base_cost", "bridge_id", "callflow_id", "channel_authorized", "ecallmgr_node", "fetch_id", "global_resource", "inception", "privacy_hide_name", "privacy_hide_number", "pvt_cost", "rate", "rate_description", "rate_minimum", "rate_name", "rate_nocharge_time", "reseller_billing", "reseller_id", "reseller_trunk_usage", "resource_type", "surcharge"]
	def getCDRIdListOfKeyOffnetConferenceCaller2or3CalleeDetailsEmpty =			["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "disposition", "duration_seconds", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "hangup_code", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "presence_id", "remote_sdp", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_tag", "to_uri", "transfer_history", "user_agent", "account_id", "application_name", "application_node", "bridge_id", "ecallmgr_node", "fetch_id", "privacy_hide_name", "privacy_hide_number"]
	def getCDRIdListOfKeyOffnetConferenceCaller1 =								["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "disposition", "duration_seconds", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "hangup_code", "id", "interaction_id", "interaction_key", "interaction_time", "is_conference", "local_sdp", "media_server", "msg_id", "node", "presence_id", "remote_sdp", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_tag", "to_uri", "transfer_history", "user_agent", "account_id", "account_name", "account_realm", "application_name", "application_node", "authorizing_id", "authorizing_type", "bridge_id", "callflow_id", "channel_authorized", "ecallmgr_node", "fetch_id", "global_resource", "owner_id", "privacy_hide_name", "privacy_hide_number", "realm", "username"]
	def getCDRIdListOfKeyOffnetTollFreeInbound =								["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "disposition", "duration_seconds", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "hangup_code", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "other_leg_call_id", "other_leg_caller_id_name", "other_leg_caller_id_number", "other_leg_destination_number", "other_leg_direction", "presence_id", "remote_sdp", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_tag", "to_uri", "user_agent", "account_id", "account_name", "account_realm", "application_name", "application_node", "authorizing_id", "authorizing_type", "bridge_id", "callflow_id", "channel_authorized", "ecallmgr_node", "fetch_id", "global_resource", "owner_id", "privacy_hide_name", "privacy_hide_number", "realm", "reseller_id", "username"]
	def getCDRIdListOfKeyOffnetTollFreeOutbound =								["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "disposition", "duration_seconds", "e164_destination", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "hangup_code", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "other_leg_call_id", "other_leg_caller_id_name", "other_leg_caller_id_number", "other_leg_destination_number", "other_leg_direction", "remote_sdp", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_tag", "to_uri", "account_billing", "account_id", "account_trunk_usage", "base_cost", "bridge_id", "channel_authorized", "ecallmgr_node", "global_resource", "matched_number", "original_number", "pvt_cost", "rate", "rate_description", "rate_increment", "rate_minimum", "rate_name", "rate_nocharge_time", "realm", "reseller_billing", "reseller_id", "reseller_trunk_usage", "resource_id", "resource_type", "surcharge"]
	def getCDRIdListOfKeyOffnetForwardForwarderCallerIdOutbound =				["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "disposition", "duration_seconds", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "hangup_code", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "other_leg_call_id", "other_leg_caller_id_name", "other_leg_caller_id_number", "other_leg_destination_number", "other_leg_direction", "presence_id", "remote_sdp", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_tag", "to_uri", "user_agent", "account_id", "authorizing_id", "authorizing_type", "bridge_id", "channel_authorized", "ecallmgr_node", "global_resource", "inception", "owner_id", "realm", "username", "x_kazoo_aor", "x_kazoo_invite_format"]
	def getCDRIdListOfKeyOffnetForwardForwarderCallerIdInbound= 				["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "disposition", "duration_seconds", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "hangup_code", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "other_leg_call_id", "other_leg_caller_id_name", "other_leg_caller_id_number", "other_leg_destination_number", "other_leg_direction", "presence_id", "remote_sdp", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_tag", "to_uri", "account_billing", "account_id", "account_trunk_usage", "application_name", "application_node", "base_cost", "bridge_id", "callflow_id", "channel_authorized", "ecallmgr_node", "fetch_id", "global_resource", "inception", "privacy_hide_name", "privacy_hide_number", "pvt_cost", "rate", "rate_description", "rate_increment", "rate_minimum", "rate_name", "rate_nocharge_time", "reseller_billing", "reseller_id", "reseller_trunk_usage", "resource_type", "surcharge"]
	def getCDRIdListOfKeyOffnetCallerHangsUpBeforeAnswerTerminationInbound= 	["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "disposition", "duration_seconds", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "hangup_code", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "other_leg_call_id", "other_leg_caller_id_name", "other_leg_caller_id_number", "other_leg_destination_number", "other_leg_direction", "presence_id", "remote_sdp", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_uri", "user_agent", "account_id", "account_name", "account_realm", "application_name", "application_node", "authorizing_id", "authorizing_type", "bridge_id", "callflow_id", "channel_authorized", "ecallmgr_node", "fetch_id", "global_resource", "owner_id", "privacy_hide_name", "privacy_hide_number", "realm", "reseller_id", "username"]
	def getCDRIdListOfKeyOffnetCallerHangsUpBeforeAnswerTerminationOutbound= 	["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "disposition", "duration_seconds", "e164_destination", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "hangup_code", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "other_leg_call_id", "other_leg_caller_id_name", "other_leg_caller_id_number", "other_leg_destination_number", "other_leg_direction", "remote_sdp", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_tag", "to_uri", "account_billing", "account_id", "account_trunk_usage", "base_cost", "bridge_id", "channel_authorized", "ecallmgr_node", "global_resource", "matched_number", "original_number", "pvt_cost", "rate", "rate_description", "rate_increment", "rate_minimum", "rate_name", "rate_nocharge_time", "realm", "reseller_billing", "reseller_id", "reseller_trunk_usage", "resource_id", "resource_type", "surcharge"]
	def getCDRIdListOfKeyOffnetCallerHangsUpBeforeAnswerOriginationInbound=		["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "disposition", "duration_seconds", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "hangup_code", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "presence_id", "remote_sdp", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_tag", "to_uri", "account_billing", "account_id", "account_trunk_usage", "application_name", "application_node", "base_cost", "bridge_id", "callflow_id", "channel_authorized", "ecallmgr_node", "fetch_id", "global_resource", "inception", "privacy_hide_name", "privacy_hide_number", "pvt_cost", "rate", "rate_description", "rate_increment", "rate_minimum", "rate_name", "rate_nocharge_time", "reseller_billing", "reseller_id", "reseller_trunk_usage", "resource_type", "surcharge"]
	def getCDRIdListOfKeyOffnetCallerHangsUpBeforeAnswerOriginationOutbound=	["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "duration_seconds", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "hangup_code", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "other_leg_call_id", "other_leg_caller_id_name", "other_leg_caller_id_number", "other_leg_destination_number", "other_leg_direction", "presence_id", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_tag", "to_uri", "user_agent", "account_id", "authorizing_id", "authorizing_type", "bridge_id", "callflow_id", "ecallmgr_node", "global_resource", "inception", "owner_id", "realm", "username", "x_kazoo_aor", "x_kazoo_invite_format"]
	def getCDRIdListOfKeyOffnetUnansweredTimeoutTerminationInbound=				["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "disposition", "duration_seconds", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "hangup_code", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "other_leg_call_id", "other_leg_caller_id_name", "other_leg_caller_id_number", "other_leg_destination_number", "other_leg_direction", "presence_id", "remote_sdp", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_uri", "user_agent", "account_id", "account_name", "account_realm", "application_name", "application_node", "authorizing_id", "authorizing_type", "bridge_id", "callflow_id", "channel_authorized", "ecallmgr_node", "fetch_id", "global_resource", "owner_id", "privacy_hide_name", "privacy_hide_number", "realm", "reseller_id", "username"]
	def getCDRIdListOfKeyOffnetUnansweredTimeoutTerminationOutbound=			["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "disposition", "duration_seconds", "e164_destination", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "hangup_code", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "other_leg_call_id", "other_leg_caller_id_name", "other_leg_caller_id_number", "other_leg_destination_number", "other_leg_direction", "remote_sdp", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_tag", "to_uri", "account_billing", "account_id", "account_trunk_usage", "base_cost", "bridge_id", "channel_authorized", "ecallmgr_node", "global_resource", "matched_number", "original_number", "pvt_cost", "rate", "rate_description", "rate_increment", "rate_minimum", "rate_name", "rate_nocharge_time", "realm", "reseller_billing", "reseller_id", "reseller_trunk_usage", "resource_id", "resource_type", "surcharge"]
	def getCDRIdListOfKeyOffnetUnansweredTimeoutOriginationInbound= 			["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "disposition", "duration_seconds", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "presence_id", "remote_sdp", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_tag", "to_uri", "account_billing", "account_id", "account_trunk_usage", "application_name", "application_node", "base_cost", "bridge_id", "callflow_id", "channel_authorized", "ecallmgr_node", "fetch_id", "global_resource", "inception", "privacy_hide_name", "privacy_hide_number", "pvt_cost", "rate", "rate_description", "rate_increment", "rate_minimum", "rate_name", "rate_nocharge_time", "reseller_billing", "reseller_id", "reseller_trunk_usage", "resource_type", "surcharge"]
	def getCDRIdListOfKeyOffnetUnansweredTimeoutOriginationOutbound= 			["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "duration_seconds", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "other_leg_call_id", "other_leg_caller_id_name", "other_leg_caller_id_number", "other_leg_destination_number", "other_leg_direction", "presence_id", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_tag", "to_uri", "user_agent", "account_id", "authorizing_id", "authorizing_type", "bridge_id", "channel_authorized", "ecallmgr_node", "global_resource", "inception", "owner_id", "realm", "username", "x_kazoo_aor", "x_kazoo_invite_format"]
	def getCDRIdListOfKeyOffnetFaxOriginationInbound=							[
		"app_name",
		"app_version",
		"billing_seconds",
		"call_direction",
		"call_id",
		"callee_id_name",
		"callee_id_number",
		"caller_id_name",
		"caller_id_number",
		"channel_call_state",
		"channel_created_time",
		"channel_name",
		"channel_state",
		"custom_application_vars",
		"custom_channel_vars",
		"custom_sip_headers",
		"disposition",
		"duration_seconds",
		"event_category",
		"event_name",
		"fax_info",
		"from",
		"from_tag",
		"from_uri",
		"hangup_cause",
		"id",
		"interaction_id",
		"interaction_key",
		"interaction_time",
		"local_sdp",
		"media_server",
		"msg_id",
		"node",
		"origination_call_id",
		"presence_id",
		"remote_sdp",
		"request",
		"ringing_seconds",
		"switch_hostname",
		"switch_nodename",
		"switch_uri",
		"switch_url",
		"timestamp",
		"to",
		"to_tag",
		"to_uri",
		"account_billing",
		"account_id",
		"account_trunk_usage",
		"application_name",
		"application_node",
		"base_cost",
		"bridge_id",
		"callflow_id",
		"channel_authorized",
		"ecallmgr_node",
		"fetch_id",
		"global_resource",
		"inception",
		"privacy_hide_name",
		"privacy_hide_number",
		"pvt_cost",
		"rate",
		"rate_description",
		"rate_increment",
		"rate_minimum",
		"rate_name",
		"rate_nocharge_time",
		"reseller_billing",
		"reseller_id",
		"reseller_trunk_usage",
		"resource_type",
		"surcharge",
		"fax_bad_rows",
		"fax_doc_db",
		"fax_doc_id",
		"fax_ecm_used",
		"fax_encoding",
		"fax_encoding_name",
		"fax_file_image_pixel_size",
		"fax_file_image_resolution",
		"fax_identity_name",
		"fax_identity_number",
		"fax_image_pixel_size",
		"fax_image_resolution",
		"fax_image_size",
		"fax_local_station_id",
		"fax_longest_bad_row_run",
		"fax_remote_station_id",
		"fax_longest_bad_row_run",
		"fax_remote_station_id",
		"fax_result_code",
		"fax_result_text",
		"fax_success",
		"fax_timezone",
		"fax_total_pages",
		"fax_transfer_rate",
		"fax_transferred_pages",
		"fax_info"
	]
	def getCDRIdListOfKeyOffnetFaxTerminationOutbound=							["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "disposition", "duration_seconds", "e164_destination", "event_category", "event_name", "fax_info", "from", "from_tag", "from_uri", "hangup_cause", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "origination_call_id", "other_leg_call_id", "remote_sdp", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_tag", "to_uri", "account_billing", "account_id", "account_trunk_usage", "authorizing_id", "authorizing_type", "base_cost", "channel_authorized", "ecallmgr_node", "fetch_id", "global_resource", "matched_number", "original_number", "pvt_cost", "rate", "rate_description", "rate_increment", "rate_minimum", "rate_name", "rate_nocharge_time", "realm", "reseller_billing", "reseller_id", "reseller_trunk_usage", "resource_id", "resource_type", "surcharge", "fax_bad_rows", "fax_ecm_used", "fax_encoding", "fax_encoding_name", "fax_file_image_pixel_size", "fax_file_image_resolution", "fax_identity_name", "fax_identity_number", "fax_image_pixel_size", "fax_image_resolution", "fax_image_size", "fax_local_station_id", "fax_longest_bad_row_run", "fax_remote_station_id", "fax_result_code", "fax_result_text", "fax_success", "fax_timezone", "fax_total_pages", "fax_transfer_rate", "fax_transferred_pages", "fax_info"]
	//Contain all onnet scenarios
	def getCDRIdListOfKeyOnnetCallerCancelOutbound= 							["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "duration_seconds", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "hangup_code", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "other_leg_call_id", "other_leg_caller_id_name", "other_leg_caller_id_number", "other_leg_destination_number", "other_leg_direction", "presence_id", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_tag", "to_uri", "user_agent", "account_id", "authorizing_id", "authorizing_type", "bridge_id", "channel_authorized", "ecallmgr_node", "global_resource", "owner_id", "realm", "username", "x_kazoo_aor", "x_kazoo_invite_format"]
	def getCDRIdListOfKeyOnnetCallerCancelInbound=  							["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "disposition", "duration_seconds", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "hangup_code", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "presence_id", "remote_sdp", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_uri", "user_agent", "account_id", "account_name", "account_realm", "application_name", "application_node", "authorizing_id", "authorizing_type", "bridge_id", "callflow_id", "channel_authorized", "ecallmgr_node", "fetch_id", "global_resource", "owner_id", "privacy_hide_name", "privacy_hide_number", "realm", "username"]
	def getCDRIdListOfKeyOnnetCalleeRejectInbound=  							["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "disposition", "duration_seconds", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "hangup_code", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "presence_id", "remote_sdp", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_uri", "user_agent", "account_id", "account_name", "account_realm", "application_name", "application_node", "authorizing_id", "authorizing_type", "bridge_id", "callflow_id", "channel_authorized", "ecallmgr_node", "fetch_id", "global_resource", "owner_id", "privacy_hide_name", "privacy_hide_number", "realm", "username"]
	def getCDRIdListOfKeyOnnetCalleeRejectOutbound= 							["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "duration_seconds", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "hangup_code", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "other_leg_call_id", "other_leg_caller_id_name", "other_leg_caller_id_number", "other_leg_destination_number", "other_leg_direction", "presence_id", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_tag", "to_uri", "user_agent", "account_id", "authorizing_id", "authorizing_type", "bridge_id", "channel_authorized", "ecallmgr_node", "global_resource", "owner_id", "realm", "username", "x_kazoo_aor", "x_kazoo_invite_format"]
	def getCDRIdListOfKeyOnnetCallTimeoutInbound=   							["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "disposition", "duration_seconds", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "presence_id", "remote_sdp", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_uri", "user_agent", "account_id", "account_name", "account_realm", "application_name", "application_node", "authorizing_id", "authorizing_type", "bridge_id", "callflow_id", "channel_authorized", "ecallmgr_node", "fetch_id", "global_resource", "owner_id", "privacy_hide_name", "privacy_hide_number", "realm", "username"]
	def getCDRIdListOfKeyOnnetCallTimeoutOutbound=  							["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "duration_seconds", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "other_leg_call_id", "other_leg_caller_id_name", "other_leg_caller_id_number", "other_leg_destination_number", "other_leg_direction", "presence_id", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_tag", "to_uri", "user_agent", "account_id", "authorizing_id", "authorizing_type", "bridge_id", "channel_authorized", "ecallmgr_node", "global_resource", "owner_id", "realm", "username", "x_kazoo_aor", "x_kazoo_invite_format"]
	def getCDRIdListOfKeyOnnetCalleeTransferAgent3Outbound=						["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "duration_seconds", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "hangup_code", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "other_leg_call_id", "other_leg_caller_id_name", "other_leg_caller_id_number", "other_leg_destination_number", "other_leg_direction", "presence_id", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_tag", "to_uri", "user_agent", "account_id", "authorizing_id", "authorizing_type", "bridge_id", "channel_authorized", "ecallmgr_node", "global_resource", "owner_id", "realm", "username", "x_kazoo_aor", "x_kazoo_invite_format"]
	def getCDRIdListOfKeyOnnetCalleeTransferAgent2Inbound=						["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "disposition", "duration_seconds", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "hangup_code", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "other_leg_call_id", "other_leg_caller_id_name", "other_leg_caller_id_number", "other_leg_destination_number", "other_leg_direction", "presence_id", "remote_sdp", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_tag", "to_uri", "transfer_history", "user_agent", "account_id", "account_name", "account_realm", "application_name", "application_node", "authorizing_id", "authorizing_type", "bridge_id", "callflow_id", "channel_authorized", "ecallmgr_node", "fetch_id", "global_resource", "owner_id", "privacy_hide_name", "privacy_hide_number", "realm", "referred_by", "referred_to", "username", "referred_by"]
	def getCDRIdListOfKeyOnnetCalleeTransferAgent2Outbound=						["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "disposition", "duration_seconds", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "other_leg_call_id", "other_leg_caller_id_name", "other_leg_caller_id_number", "other_leg_destination_number", "other_leg_direction", "presence_id", "remote_sdp", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_tag", "to_uri", "user_agent", "account_id", "authorizing_id", "authorizing_type", "bridge_id", "channel_authorized", "ecallmgr_node", "global_resource", "owner_id", "realm", "username", "x_kazoo_aor", "x_kazoo_invite_format"]
	def getCDRIdListOfKeyOnnetCalleeTerminateOutbound=							["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "disposition", "duration_seconds", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "hangup_code", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "other_leg_call_id", "other_leg_caller_id_name", "other_leg_caller_id_number", "other_leg_destination_number", "other_leg_direction", "presence_id", "remote_sdp", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_tag", "to_uri", "user_agent", "account_id", "authorizing_id", "authorizing_type", "bridge_id", "channel_authorized", "ecallmgr_node", "global_resource", "owner_id", "realm", "username", "x_kazoo_aor", "x_kazoo_invite_format"]
	def getCDRIdListOfKeyOnnetCalleeTerminateInbound=							["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "disposition", "duration_seconds", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "hangup_code", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "other_leg_call_id", "other_leg_caller_id_name", "other_leg_caller_id_number", "other_leg_destination_number", "other_leg_direction", "presence_id", "remote_sdp", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_tag", "to_uri", "user_agent", "account_id", "account_name", "account_realm", "application_name", "application_node", "authorizing_id", "authorizing_type", "bridge_id", "callflow_id", "channel_authorized", "ecallmgr_node", "fetch_id", "global_resource", "owner_id", "privacy_hide_name", "privacy_hide_number", "realm", "username"]
	def getCDRIdListOfKeyOnnetForwardCallerAgent2Inbound=						["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "disposition", "duration_seconds", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "hangup_code", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "other_leg_call_id", "other_leg_caller_id_name", "other_leg_caller_id_number", "other_leg_destination_number", "other_leg_direction", "presence_id", "remote_sdp", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_tag", "to_uri", "transfer_history", "user_agent", "account_id", "account_name", "account_realm", "application_name", "application_node", "authorizing_id", "authorizing_type", "bridge_id", "callflow_id", "channel_authorized", "ecallmgr_node", "fetch_id", "global_resource", "owner_id", "privacy_hide_name", "privacy_hide_number", "realm", "username"]
	def getCDRIdListOfKeyOnnetForwardCallerAgent2Outbound=						["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "disposition", "duration_seconds", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "hangup_code", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "other_leg_call_id", "other_leg_caller_id_name", "other_leg_caller_id_number", "other_leg_destination_number", "other_leg_direction", "presence_id", "remote_sdp", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_tag", "to_uri", "transfer_history", "user_agent", "account_id", "authorizing_id", "authorizing_type", "bridge_id", "channel_authorized", "ecallmgr_node", "global_resource", "owner_id", "realm", "username", "x_kazoo_aor", "x_kazoo_invite_format"]
	def getCDRIdListOfKeyOnnetForwardCallerCalleeAgent2Inbound=					["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_is_loopback", "channel_loopback_bowout", "channel_loopback_bowout_execute", "channel_loopback_leg", "channel_loopback_other_leg_id", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "disposition", "duration_seconds", "event_category", "event_name", "from", "hangup_cause", "id", "interaction_id", "interaction_key", "interaction_time", "media_server", "msg_id", "node", "other_leg_call_id", "other_leg_caller_id_name", "other_leg_caller_id_number", "other_leg_destination_number", "other_leg_direction", "presence_id", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "account_id", "application_name", "application_node", "authorizing_id", "authorizing_type", "bridge_id", "call_forward", "call_forward_from", "callflow_id", "channel_authorized", "ecallmgr_node", "fetch_id", "global_resource", "owner_id", "privacy_hide_name", "privacy_hide_number", "require_ignore_early_media"]
	def getCDRIdListOfKeyOnnetForwardCalleeAgent2Outbound=						["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_is_loopback", "channel_loopback_bowout", "channel_loopback_bowout_execute", "channel_loopback_leg", "channel_loopback_other_leg_id", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "disposition", "duration_seconds", "event_category", "event_name", "from", "hangup_cause", "id", "interaction_id", "interaction_key", "interaction_time", "media_server", "msg_id", "node", "other_leg_call_id", "other_leg_caller_id_name", "other_leg_caller_id_number", "other_leg_destination_number", "other_leg_direction", "presence_id", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "account_id", "authorizing_id", "authorizing_type", "bridge_id", "call_forward", "call_forward_from", "channel_authorized", "ecallmgr_node", "global_resource", "owner_id", "require_ignore_early_media", "x_kazoo_aor", "x_kazoo_invite_format"]
	def getCDRIdListOfKeyOnnetVoicemailDueToDNDAgent1Inbound=					["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "disposition", "duration_seconds", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "hangup_code", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "presence_id", "remote_sdp", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_tag", "to_uri", "user_agent", "account_id", "account_name", "account_realm", "application_name", "application_node", "authorizing_id", "authorizing_type", "bridge_id", "callflow_id", "channel_authorized", "ecallmgr_node", "fetch_id", "global_resource", "media_group_id", "owner_id", "privacy_hide_name", "privacy_hide_number", "realm", "username"]
	def getCDRIdListOfKeyOnnetVoicemailDueToDNDAgent2Inbound=					["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "disposition", "duration_seconds", "event_category", "event_name", "from", "from_tag", "from_uri", "hangup_cause", "hangup_code", "id", "interaction_id", "interaction_key", "interaction_time", "local_sdp", "media_server", "msg_id", "node", "presence_id", "remote_sdp", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_tag", "to_uri", "user_agent", "account_id", "account_name", "account_realm", "application_name", "application_node", "authorizing_id", "authorizing_type", "bridge_id", "callflow_id", "channel_authorized", "ecallmgr_node", "fetch_id", "global_resource", "media_group_id", "owner_id", "privacy_hide_name", "privacy_hide_number", "realm", "username"]
	def getCDRIdListOfKeyOnnetEmailToFaxOutbound=								[
		"app_name",
		"app_version",
		"billing_seconds",
		"call_direction",
		"call_id",
		"callee_id_name",
		"callee_id_number",
		"caller_id_name",
		"caller_id_number",
		"channel_call_state",
		"channel_created_time",
		"channel_is_loopback",
		"channel_loopback_bowout",
		"channel_loopback_bowout_execute",
		"channel_loopback_leg",
		"channel_loopback_other_leg_id",
		"channel_name",
		"channel_state",
		"custom_application_vars",
		"custom_channel_vars",
		"custom_sip_headers",
		"disposition",
		"duration_seconds",
		"event_category",
		"event_name",
		"fax_info",
		"from",
		"from_uri",
		"hangup_cause",
		"id",
		"interaction_id",
		"interaction_key",
		"interaction_time",
		"media_server",
		"msg_id",
		"node",
		"origination_call_id",
		"other_leg_call_id",
		"request",
		"ringing_seconds",
		"switch_hostname",
		"switch_nodename",
		"switch_uri",
		"switch_url",
		"timestamp",
		"to",
		"to_uri",
		"fax_bad_rows",
		"fax_ecm_used",
		"fax_encoding",
		"fax_encoding_name",
		"fax_file_image_pixel_size",
		"fax_file_image_resolution",
		"fax_identity_name",
		"fax_identity_number",
		"fax_image_pixel_size",
		"fax_image_resolution",
		"fax_image_size",
		"fax_local_station_id",
		"fax_longest_bad_row_run",
		"fax_remote_station_id",
		"fax_result_code",
		"fax_result_text",
		"fax_success",
		"fax_timezone",
		"fax_total_pages",
		"fax_transfer_rate",
		"fax_transferred_pages",
		"account_billing",
		"account_id",
		"account_trunk_usage",
		"authorizing_id",
		"authorizing_type",
		"base_cost",
		"channel_authorized",
		"ecallmgr_node",
		"export_loopback_account_id",
		"export_loopback_from_uri",
		"export_loopback_inception",
		"export_loopback_request_uri",
		"export_loopback_reseller_id",
		"export_loopback_resource_type",
		"export_loopback_retain_cid",
		"export_loopback_sip_invite_domain",
		"export_loopback_to_uri",
		"fetch_id",
		"global_resource",
		"pvt_cost",
		"rate",
		"rate_description",
		"rate_increment",
		"rate_minimum",
		"rate_name",
		"rate_nocharge_time",
		"realm",
		"reseller_billing",
		"reseller_id",
		"reseller_trunk_usage",
		"resource_id",
		"resource_type",
		"surcharge"
	]
	def getCDRIdListOfKeyOnnetEmailToFaxInbound=								["app_name", "app_version", "billing_seconds", "call_direction", "call_id", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "channel_call_state", "channel_created_time", "channel_is_loopback", "channel_loopback_bowout", "channel_loopback_bowout_execute", "channel_loopback_leg", "channel_loopback_other_leg_id", "channel_name", "channel_state", "custom_application_vars", "custom_channel_vars", "custom_sip_headers", "disposition", "duration_seconds", "event_category", "event_name", "fax_info", "from", "from_uri", "hangup_cause", "id", "interaction_id", "interaction_key", "interaction_time", "media_server", "msg_id", "node", "origination_call_id", "other_leg_call_id", "request", "ringing_seconds", "switch_hostname", "switch_nodename", "switch_uri", "switch_url", "timestamp", "to", "to_uri", "fax_bad_rows", "fax_doc_db", "fax_doc_id", "fax_ecm_used", "fax_encoding", "fax_encoding_name", "fax_file_image_pixel_size", "fax_file_image_resolution", "fax_identity_name", "fax_identity_number", "fax_image_pixel_size", "fax_image_resolution", "fax_image_size", "fax_local_station_id", "fax_longest_bad_row_run", "fax_remote_station_id", "fax_result_code", "fax_result_text", "fax_success", "fax_timezone", "fax_total_pages", "fax_transfer_rate", "fax_transferred_pages", "account_billing", "account_id", "account_trunk_usage", "application_name", "application_node", "base_cost", "bridge_id", "callflow_id", "channel_authorized", "ecallmgr_node", "fetch_id", "filtered_by_loopback", "global_resource", "inception", "privacy_hide_name", "privacy_hide_number", "pvt_cost", "rate", "rate_description", "rate_increment", "rate_minimum", "rate_name", "rate_nocharge_time", "reseller_billing", "reseller_id", "reseller_trunk_usage", "resource_type", "retain_cid", "surcharge"]

	GlobalVariable.scenarioMappedKeySet.put("GETCDR_ID_CommonObjectsOutSideLeg",getCDRIDKeyListForCommonObjectsOutSideLeg)
	GlobalVariable.scenarioMappedKeySet.put("calleeTerminateOriginationOutbound",getCDRIdListOfKeyForOffnetCallerCalleeTerminatesOriginationOutbound)
	GlobalVariable.scenarioMappedKeySet.put("calleeTerminateOriginationInbound",getCDRIdListOfKeyForOffnetCallerCalleeTerminatesOriginationInbound)
	GlobalVariable.scenarioMappedKeySet.put("calleeTerminatesTerminationOutbound",getCDRIdListOfKeyForOffnetCallerCalleeTerminatesTerminationOutbound)
	GlobalVariable.scenarioMappedKeySet.put("calleeTerminatesTerminationInbound",getCDRIdListOfKeyForOffnetCallerCalleeTerminatesTerminationInbound)
	GlobalVariable.scenarioMappedKeySet.put("ConferenceCalleeConference",getCDRIdListOfKeyOffnetConferenceCaller2or3CalleeConference)
	GlobalVariable.scenarioMappedKeySet.put("ConferenceCalleeDetailsEmpty",getCDRIdListOfKeyOffnetConferenceCaller2or3CalleeDetailsEmpty)
	GlobalVariable.scenarioMappedKeySet.put("ConferenceCaller1",getCDRIdListOfKeyOffnetConferenceCaller1)
	GlobalVariable.scenarioMappedKeySet.put("TollFreeInbound",getCDRIdListOfKeyOffnetTollFreeInbound)
	GlobalVariable.scenarioMappedKeySet.put("TollFreeOutbound",getCDRIdListOfKeyOffnetTollFreeOutbound)
	GlobalVariable.scenarioMappedKeySet.put("ForwardForwarderCallerIdOutbound",getCDRIdListOfKeyOffnetForwardForwarderCallerIdOutbound)
	GlobalVariable.scenarioMappedKeySet.put("ForwardForwarderCallerIdInbound",getCDRIdListOfKeyOffnetForwardForwarderCallerIdInbound)
	GlobalVariable.scenarioMappedKeySet.put("CallerHangsUpBeforeAnswerTerminationInbound",getCDRIdListOfKeyOffnetCallerHangsUpBeforeAnswerTerminationInbound)
	GlobalVariable.scenarioMappedKeySet.put("CallerHangsUpBeforeAnswerTerminationOutbound",getCDRIdListOfKeyOffnetCallerHangsUpBeforeAnswerTerminationOutbound)
	GlobalVariable.scenarioMappedKeySet.put("CallerHangsUpBeforeAnswerOriginationInbound",getCDRIdListOfKeyOffnetCallerHangsUpBeforeAnswerOriginationInbound)
	GlobalVariable.scenarioMappedKeySet.put("CallerHangsUpBeforeAnswerOriginationOutbound",getCDRIdListOfKeyOffnetCallerHangsUpBeforeAnswerOriginationOutbound)
	GlobalVariable.scenarioMappedKeySet.put("UnansweredTimeoutTerminationInbound",getCDRIdListOfKeyOffnetUnansweredTimeoutTerminationInbound)
	GlobalVariable.scenarioMappedKeySet.put("UnansweredTimeoutTerminationOutbound",getCDRIdListOfKeyOffnetUnansweredTimeoutTerminationOutbound)
	GlobalVariable.scenarioMappedKeySet.put("UnansweredTimeoutOriginationInbound",getCDRIdListOfKeyOffnetUnansweredTimeoutOriginationInbound)
	GlobalVariable.scenarioMappedKeySet.put("UnansweredTimeoutOriginationOutbound",getCDRIdListOfKeyOffnetUnansweredTimeoutOriginationOutbound)
	GlobalVariable.scenarioMappedKeySet.put("FaxOriginationInbound",getCDRIdListOfKeyOffnetFaxOriginationInbound)
	GlobalVariable.scenarioMappedKeySet.put("FaxTerminationOutbound",getCDRIdListOfKeyOffnetFaxTerminationOutbound)



	GlobalVariable.scenarioMappedKeySet.put("OnnetCallerCancelOutbound",getCDRIdListOfKeyOnnetCallerCancelOutbound)
	GlobalVariable.scenarioMappedKeySet.put("OnnetCallerCancelInbound",getCDRIdListOfKeyOnnetCallerCancelInbound)
	GlobalVariable.scenarioMappedKeySet.put("OnnetCalleeRejectInbound",getCDRIdListOfKeyOnnetCalleeRejectInbound)
	GlobalVariable.scenarioMappedKeySet.put("OnnetCalleeRejectOutbound",getCDRIdListOfKeyOnnetCalleeRejectOutbound)
	GlobalVariable.scenarioMappedKeySet.put("OnnetCallTimeoutInbound",getCDRIdListOfKeyOnnetCallTimeoutInbound)
	GlobalVariable.scenarioMappedKeySet.put("OnnetCallTimeoutOutbound",getCDRIdListOfKeyOnnetCallTimeoutOutbound)
	GlobalVariable.scenarioMappedKeySet.put("OnnetCalleeTransferAgent3Outbound",getCDRIdListOfKeyOnnetCalleeTransferAgent3Outbound)
	GlobalVariable.scenarioMappedKeySet.put("OnnetCalleeTransferAgent2Inbound",getCDRIdListOfKeyOnnetCalleeTransferAgent2Inbound)
	GlobalVariable.scenarioMappedKeySet.put("OnnetCalleeTransferAgent2Outbound",getCDRIdListOfKeyOnnetCalleeTransferAgent2Outbound)
	GlobalVariable.scenarioMappedKeySet.put("OnnetCalleeTerminateOutbound",getCDRIdListOfKeyOnnetCalleeTerminateOutbound)
	GlobalVariable.scenarioMappedKeySet.put("OnnetCalleeTerminateInbound",getCDRIdListOfKeyOnnetCalleeTerminateInbound)
	GlobalVariable.scenarioMappedKeySet.put("OnnetForwardCallerAgent2Inbound",getCDRIdListOfKeyOnnetForwardCallerAgent2Inbound)
	GlobalVariable.scenarioMappedKeySet.put("OnnetForwardCallerAgent2Outbound",getCDRIdListOfKeyOnnetForwardCallerAgent2Outbound)
	GlobalVariable.scenarioMappedKeySet.put("OnnetForwardCallerCalleeAgent2Inbound",getCDRIdListOfKeyOnnetForwardCallerCalleeAgent2Inbound)
	GlobalVariable.scenarioMappedKeySet.put("OnnetForwardCalleeAgent2Outbound",getCDRIdListOfKeyOnnetForwardCalleeAgent2Outbound)
	GlobalVariable.scenarioMappedKeySet.put("OnnetVoicemailDueToDNDAgent1Inbound",getCDRIdListOfKeyOnnetVoicemailDueToDNDAgent1Inbound)
	GlobalVariable.scenarioMappedKeySet.put("OnnetVoicemailDueToDNDAgent2Inbound",getCDRIdListOfKeyOnnetVoicemailDueToDNDAgent2Inbound)
	GlobalVariable.scenarioMappedKeySet.put("OnnetFaxOutbound",getCDRIdListOfKeyOnnetEmailToFaxOutbound)
	GlobalVariable.scenarioMappedKeySet.put("OnnetFaxInbound",getCDRIdListOfKeyOnnetEmailToFaxInbound)



}


/**
 * Keyword 2- Assertion of keys list from current version of kazoo to 4.3 version
 * Fetch expected keys object list from global variable scenarioMappedKeySet
 * GET CDR_ID API request , keys inside leg
 * Verify Common keys outside leg
 */
def getCDR_IdVerifyNewKeyIdentification(LazyMap responseLeg, java.util.ArrayList scenarioLegName,LazyMap commonApiResponse){
	// keys outside leg
	def commonKeysList=commonApiResponse.keySet()
	commonKeysList.removeAll(GlobalVariable.scenarioMappedKeySet.get('GETCDR_ID_CommonObjectsOutSideLeg'))

	println "After comparing with 4.3,new common keys of GET CDR API added in current version of kazoo: "+commonKeysList
	//assertEquals("After comparing with 4.3,new common keys of GET CDR API added in current version of kazoo: "+commonKeysList, 0, commonKeysList.size())
	// keys inside leg
	def keysCDR_id=[]
	keysCDR_id= responseLeg.keySet();
	try{
		keysCDR_id=keysCDR_id+responseLeg.custom_channel_vars.keySet()
		println ("Adding keys under custom channel var: "+responseLeg.custom_channel_vars.keySet())
	}catch(Exception e){
		println ("custom_channel_vars is not present,so fetching no keys")
	}
	try{
		keysCDR_id=keysCDR_id+responseLeg.custom_sip_headers.keySet()
		println ("Adding keys under custom_sip_headers: "+responseLeg.custom_sip_headers.keySet())
	}catch(Exception e){
		println ("custom_sip_headers is not present,so fetching no keys ")
	}
	try{
		keysCDR_id=keysCDR_id+responseLeg.custom_application_vars.keySet()
		println ("Adding keys under custom_application_vars: "+responseLeg.custom_application_vars.keySet())
	}catch(Exception e){
		println ("custom_application_vars is not present,so fetching no keys")
	}
	println ("All keys get CDR_ID :   "+keysCDR_id)
	keysCDR_id.removeAll(scenarioLegName)

	println  "After comparing with 4.3,new keys of GET CDR_ID API added in current version of kazoo: "+keysCDR_id
	//		assertEquals("After comparing with 4.3,new keys of GET CDR_ID API added in current version of kazoo: "+keysCDR_id, 0, keysCDR_id.size())
}

/**
 * Keyword 3- Assertion of keys list from current version of kazoo to 4.3 version
 * Fetch expected keys object list from global variable scenarioMappedKeySet
 * GET CDR API request , keys inside leg
 * Verify Common keys outside leg
 */
def getCDRVerifyNewKeyIdentification(LazyMap responseLeg,LazyMap commonApiResponse){
	def getCDRKeyListForCommonObjectsOutSideLeg	=["page_size", "start_key", "status", "request_id", "node", "version", "timestamp", "auth_token", "data", "Yes"]
	def getCDRListOfKeyAllScenarios =["authorizing_id", "billing_seconds", "bridge_id", "call_id", "call_priority", "call_type", "callee_id_name", "callee_id_number", "caller_id_name", "caller_id_number", "calling_from", "cost", "datetime", "dialed_number", "direction"	 , "duration_seconds", "from", "hangup_cause", "id", "interaction_id", "iso_8601", "iso_8601_combined", "media_recordings", "media_server", "other_leg_call_id", "owner_id", "rate", "rate_name", "recording_url", "request", "reseller_call_type", "reseller_cost", "rfc_1036", "timestamp", "to", "unix_timestamp"]

	// keys outside leg
	def commonKeysList=commonApiResponse.keySet()
	commonKeysList.removeAll(getCDRKeyListForCommonObjectsOutSideLeg)

	println "After comparing with 4.3,new common keys of GET CDR API added in current version of kazoo outside leg: "+commonKeysList
	//		assertEquals("After comparing with 4.3,new common keys of GET CDR API added in current version of kazoo: "+commonKeysList, 0, commonKeysList.size())

	// keys inside leg
	def keysCDR=[]
	keysCDR= responseLeg.keySet();
	println ("All keys get CDR : "+keysCDR)

	keysCDR.removeAll(getCDRListOfKeyAllScenarios)

	println "After comparing with 4.3,new keys of GET CDR API added in current version of kazoo inside leg: "+keysCDR
	//		assertEquals("After comparing with 4.3,new keys of GET CDR API added in current version of kazoo: "+keysCDR, 0, keysCDR.size())
}

/**
 * Verify interaction_time, custom_channel_vars
 */
def offnet_verifyInteractionTime_CustomChannelVars(LazyMap responseLeg){
	assertNotNull(responseLeg.interaction_time)
	println "    interaction_time is verified and value is : "+responseLeg.interaction_time
	assertNotNull(responseLeg.custom_channel_vars)
	println "    custom_channel_vars is verified and value is : "+responseLeg.custom_channel_vars
}

/*
 * get the node values which is used for verification
 */
@Keyword
def getNodeValueList(String media_server){

	List nodeValueList = new ArrayList()
	for(String value:GlobalVariable.nodeList){
		nodeValueList.add(value+"@"+media_server)
	}
	return nodeValueList
}














