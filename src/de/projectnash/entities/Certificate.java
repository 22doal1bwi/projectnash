package de.projectnash.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import de.projectnash.application.util.CertificateStatus;

/**
 * This class provides a realistic {@link Certificate} with all its attributes.
 * 
 * @author Marius Boepple, Jonathan Schlotz, Silvio D'Alessandro
 *
 */
@Entity
@Table(name = "certificates")
@NamedQueries({
	@NamedQuery(name = "QUERY_FIND_CERTIFICATE_BY_CERTIFICATE_ID", query = "SELECT c FROM Certificate c WHERE c.certificateId = :certificateId"),
	@NamedQuery(name = "QUERY_FIND_ALL_CERTIFICATES", query = "SELECT c FROM Certificate c"),
	@NamedQuery(name = "QUERY_REMOVE_CERTIFICATE_BY_CERTIFICATE", query = "DELETE FROM Certificate c WHERE c = :Certificate")
})
public class Certificate implements Serializable {

	private static final long serialVersionUID = -292148093669894026L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "crt_id", nullable = false)
	private int certificateId;
	
	@Column(name = "crt_file", nullable = false)
	private byte[] certificateFileP12;
	
	@Column(name = "crt_file_crt", nullable = false)
	private byte[] certificateFileCRT;
	
	@Column(name = "crt_country", nullable = false)
	private String countryName;
	
	@Column(name = "crt_state", nullable = false)
	private String state;
	
	@Column(name = "crt_locality", nullable = false)
	private String localityName;
	
	@Column(name = "crt_organization", nullable = false)
	private String organizationName;
	
	@Column(name = "crt_organization_unit", nullable = false)
	private String organizationalUnit;
	
	@Column(name = "crt_common_name", nullable = false)
	private String commonName;
	
	@Column(name = "crt_email_address", nullable = false)
	private String emailAddress;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "crt_initialization_date", nullable = false)
	private Date initializationDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "crt_expiration_date", nullable = false)
	private Date expirationDate;
	
	@Enumerated(EnumType.STRING)
    private CertificateStatus certificateStatus;
	
	@Column(name = "crt_revoke_reason", nullable = true)
	private String revokeReason;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "crt_created_on", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable= true, updatable= false)
	private Date creationDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "crt_modified_on", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", insertable= false, updatable= true)
	private Date modificationDate;
	
	/**
	 * This constructor is only needed for JPA.
	 */
	protected Certificate(){}
	
	public Certificate(byte[] certificateFileP12, byte[] certificateFileCRT,
			String countryName, String state, String localityName,
			String organizationName, String organizationalUnit,
			String commonName, String emailAddress, Date initializationDate,
			Date expirationDate, CertificateStatus certificateStatus) {
		super();
		this.certificateFileCRT = certificateFileCRT;
		this.certificateFileP12 = certificateFileP12;
		this.countryName = countryName;
		this.state = state;
		this.localityName = localityName;
		this.organizationName = organizationName;
		this.organizationalUnit = organizationalUnit;
		this.commonName = commonName;
		this.emailAddress = emailAddress;
		this.initializationDate = initializationDate;
		this.expirationDate = expirationDate;
		this.certificateStatus = certificateStatus;
	}

	@Override
	public String toString() {
		return "Certificate [certificateId=" + certificateId
				+ ", countryName=" + countryName + ", state=" + state
				+ ", localityName=" + localityName + ", organizationName="
				+ organizationName + ", organizationalUnit="
				+ organizationalUnit + ", commonName=" + commonName
				+ ", emailAddress=" + emailAddress + ", initializationDate="
				+ initializationDate + ", expirationDate=" + expirationDate
				+ ", certificateStatus=" + certificateStatus
				+ ", revokeReason=" + revokeReason + ", creationDate="
				+ creationDate + ", modificationDate=" + modificationDate + "]";
	}

	public int getCertificateId() {
		return certificateId;
	}

	public void setCertificateId(int certificateId) {
		this.certificateId = certificateId;
	}

	public byte[] getCertificateFile() {
		return certificateFileP12;
	}

	public void setCertificateFile(byte[] certificateFile) {
		this.certificateFileP12 = certificateFile;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLocalityName() {
		return localityName;
	}

	public void setLocalityName(String localityName) {
		this.localityName = localityName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getOrganizationalUnit() {
		return organizationalUnit;
	}

	public void setOrganizationalUnit(String organizationalUnit) {
		this.organizationalUnit = organizationalUnit;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public byte[] getCertificateCRT() {
		return certificateFileCRT;
	}

	public void setCertificateCRT(byte[] certificate) {
		this.certificateFileCRT = certificate;
	}
	
	public byte[] getCertificateP12() {
		return certificateFileP12;
	}

	public void setCertificateP12(byte[] certificate) {
		this.certificateFileP12 = certificate;
	}

	public Date getInitializationDate() {
		return initializationDate;
	}

	public void setInitializationDate(Date initializationDate) {
		this.initializationDate = initializationDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public CertificateStatus getCertificateStatus() {
		return certificateStatus;
	}

	public void setCertificateStatus(CertificateStatus certificateStatus) {
		this.certificateStatus = certificateStatus;
	}

	public String getRevokeReason() {
		return revokeReason;
	}

	public void setRevokeReason(String revokeReason) {
		this.revokeReason = revokeReason;
	}

}
