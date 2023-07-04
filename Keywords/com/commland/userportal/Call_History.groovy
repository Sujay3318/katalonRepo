package com.commland.userportal

import java.util.regex.*

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.util.KeywordUtil

public class Call_History {

	/*
	 * Convert the give date to timestamps
	 * Verify the date of subject is under given range
	 */
	@Keyword
	def verify_date_in_range(Date fromDate, Date toDate, Date callDate) {

		return ((callDate >= fromDate) && (callDate <= toDate))
	}

	@Keyword
	def verify_Date_sorting(String order, Date currentCell_date, Date nextCell_date, int currentCell_time, int nextCell_time ){

		switch (order){
			case 'Ascending':
				if (currentCell_date < nextCell_date){
					return true
				}
				else if (currentCell_time <= nextCell_time){
					return true
				}
				else{
					return false
				}

			case 'Descending':
				if (currentCell_date > nextCell_date){
					return true
				}
				else if (currentCell_time >= nextCell_time){
					return true
				}
				else{
					return false
				}
		}
	}

	@Keyword
	def verify_Duration_sorting(order, currentCell_duration, nextCell_duration){

		switch (order){
			case 'Ascending':
				if (currentCell_duration <= nextCell_duration){
					return true
				}
				else{
					return false
				}

			case 'Descending':
				if (currentCell_duration >= nextCell_duration){
					return true
				}
				else{
					return false
				}
		}
	}

	@Keyword
	def verifyCallDurationFormat(String actual_CallDuration) {

		try{
			actual_CallDuration.split(":")[0] as int
			actual_CallDuration.split(":")[1] as int
			println('Call duration is present : ' + actual_CallDuration + " and format is verified")
		}catch(Exception e2){

			if (e2 != null) {
				e2.printStackTrace()
			}

			KeywordUtil.markFailed('Call duration is either not present, or format it is not as expected')
		}
	}

	@Keyword
	def verifyCallTimeFormat(String actual_CallTime) {
		def lst = ['am', 'pm', 'AM', 'PM']
		def period =  actual_CallTime.split(" ")[1]
		def t = actual_CallTime.split(" ")[0]
		def t_hours = t.split(":")[0]
		def t_mins = t.split(":")[1]
		println period
		println t_hours
		println t_mins
		try{
			assert (period in lst)
			t_hours as int
			t_mins as int
			println('Call time is present : ' + actual_CallTime +" and format is verified")
		}catch(Exception e2){

			if (e2 != null) {
				e2.printStackTrace()
			}

			KeywordUtil.markFailed('Call time format is not correct')
		}
	}
}
