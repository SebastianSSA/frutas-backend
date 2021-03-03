package com.tevo.frutas.models.services;

public interface IEmailService {
	
	public void sendEmail(String to, String subject, String content);
}
