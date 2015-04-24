package de.projectnash.frontend.interfaces;

public interface ICertificateController {
	
	String getCountry();
	
	String getState();
	
	String getLocality();
	
	String getOrganisationName();
	
	String getOrganisationUnit();
	
	String getCommonName();
	
	String getMail();
	
	boolean requestCertificate();
	
	boolean revokeCertificate();
	
	boolean extendCertificate();

	String getExpirationDate();
	

}
