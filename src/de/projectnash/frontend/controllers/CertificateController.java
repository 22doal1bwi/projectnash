package de.projectnash.frontend.controllers;

import java.util.List;

import de.projectnash.application.CertificateLogic;
import de.projectnash.entities.Certificate;
import de.projectnash.frontend.interfaces.ICertificateController;

public class CertificateController implements ICertificateController{
	
	private Certificate certificate;
	
	public CertificateController(String ssnId) {
		certificate = CertificateLogic.loadCertificate(ssnId);
	}
	
	public List<Certificate> getAllCertificates(){
		return CertificateLogic.getAllCertificates();
	}
	
	public String getExpirationDateForUI(){
		return CertificateLogic.getExpirationDateForUI(certificate);		
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
