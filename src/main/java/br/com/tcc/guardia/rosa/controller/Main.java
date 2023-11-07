package br.com.tcc.guardia.rosa.controller;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class Main {
	
	  public static final String ACCOUNT_SID = "AC5bef8193db6e696a53cd199c627e9694";
	  public static final String AUTH_TOKEN = "2ea763b0868fd576a76875c979534e12";
	
	public static void main(String[] args) {
	    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
	    Message message = Message.creator(
	      new PhoneNumber("whatsapp:+5513981177560"),
	      new PhoneNumber("whatsapp:+5513981177560"),
	      "Socorro! Estou em perigo!").create();

	    System.out.println(message.getSid());

	}
	



}
