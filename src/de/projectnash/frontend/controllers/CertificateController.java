package de.projectnash.frontend.controllers;

import java.util.List;

import de.projectnash.application.CertificateLogic;
import de.projectnash.entities.Certificate;

/**
 * The controller for {@link Certificate}.
 * 
 * @author Silvio D'Alessandro
 *
 */
public class CertificateController {
	
	private Certificate certificate;
	
	/**
	 * Constructor to initialize the {@link CertificateController}. 
	 * 
	 * @param ssnId The {@link String} on basis which the {@link CertificateController} will be initialized.
	 */
	public CertificateController(String ssnId) {
		certificate = CertificateLogic.loadCertificate(ssnId);
	}
	
	public List<Certificate> getAllCertificates(){
		return CertificateLogic.getAllCertificates();
	}
	
	public String getExpirationDateForUI(){
		return CertificateLogic.getExpirationDateForUI(certificate);		
	}

	public String getCountryName() {
		return CertificateLogic.getCountryName(certificate);
	}

	public String getState() {
		return CertificateLogic.getState(certificate);
	}

	public String getCommonName() {
		return CertificateLogic.getCommonName(certificate);
	}

	public String getExpirationDate() {
		return CertificateLogic.getExpirationDate(certificate).toString();
	}

	public String getLocalityName() {
		return CertificateLogic.getLocalityName(certificate);
	}

	public String getOrganizationName() {
		return CertificateLogic.getOrganizationName(certificate);
	}

	public String getOrganizationalUnit() {
		return CertificateLogic.getOrganizationalUnit(certificate);
	}

	public String getEmailAddress() {
		return CertificateLogic.getEmailAddress(certificate);
	}
}