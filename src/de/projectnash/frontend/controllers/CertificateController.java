package de.projectnash.frontend.controllers;

import de.projectnash.application.CertificateLogic;
import de.projectnash.application.SessionLogic;
import de.projectnash.entities.Certificate;
import de.projectnash.frontend.interfaces.ICertificateController;

public class CertificateController implements ICertificateController{
	
	private Certificate certificate;
	
	public CertificateController(String sessionId) {
		certificate = SessionLogic.loadSession(sessionId).getUser().getCertificate();
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
	public String getCountryName() {
		return CertificateLogic.getCountryName(certificate);
	}

	@Override
	public String getState() {
		return CertificateLogic.getState(certificate);
	}

	@Override
	public String getCommonName() {
		return CertificateLogic.getCommonName(certificate);
	}

	@Override
	public String getExpirationDate() {
		return CertificateLogic.getExpirationDate(certificate).toString();
	}

	@Override
	public String getLocalityName() {
		return CertificateLogic.getLocalityName(certificate);
	}

	@Override
	public String getOrganizationName() {
		return CertificateLogic.getOrganizationName(certificate);
	}

	@Override
	public String getOrganizationalUnit() {
		return CertificateLogic.getOrganizationalUnit(certificate);
	}

	@Override
	public String getEmailAddress() {
		return CertificateLogic.getEmailAddress(certificate);
	}
}
