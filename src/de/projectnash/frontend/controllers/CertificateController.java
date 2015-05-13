package de.projectnash.frontend.controllers;

import de.projectnash.application.SessionLogic;
import de.projectnash.entities.Certificate;
import de.projectnash.entities.User;
import de.projectnash.frontend.interfaces.ICertificateController;

public class CertificateController implements ICertificateController{
	
	private Certificate certificate;
	
	public CertificateController(String sessionId) {
		certificate = SessionLogic.loadSession(sessionId).getUser().getCertificate();
	}

	@Override
	public String getCountryName() {
		return certificate.getCountryName();
	}

	@Override
	public String getState() {
		return certificate.getState();
	}

	@Override
	public String getCommonName() {
		return certificate.getCommonName();
	}

	@Override
	public boolean requestCertificate(User user) {
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
		return certificate.getExpirationDate().toString();
	}

	@Override
	public String getLocalityName() {
		return certificate.getLocalityName();
	}

	@Override
	public String getOrganizationName() {
		return certificate.getOrganizationName();
	}

	@Override
	public String getOrganizationalUnit() {
		return certificate.getOrganizationalUnit();
	}

	@Override
	public String getEmailAddress() {
		return certificate.getEmailAddress();
	}
}
