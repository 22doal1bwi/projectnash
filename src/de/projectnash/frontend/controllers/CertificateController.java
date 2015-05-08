package de.projectnash.frontend.controllers;

import java.text.ParseException;

import de.projectnash.application.CertificateLogic;
import de.projectnash.entities.User;
import de.projectnash.frontend.interfaces.ICertificateController;

public class CertificateController implements ICertificateController{
	
	@Override
	public String getCountry() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCommonName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean requestCertificate(User user) {
		try {
			if(CertificateLogic.createCertificate(user)){
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean revokeCertificate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean extendCertificate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getExpirationDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLocalityName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOrganizationName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOrganizationUnit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEmailAddress() {
		// TODO Auto-generated method stub
		return null;
	}
}
