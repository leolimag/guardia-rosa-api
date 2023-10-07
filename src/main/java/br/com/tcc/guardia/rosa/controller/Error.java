package br.com.tcc.guardia.rosa.controller;

public class Error {
	
	private String message;
	
	public Error(String message) {
		super();
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
