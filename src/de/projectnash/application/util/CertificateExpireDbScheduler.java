package de.projectnash.application.util;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import de.projectnash.application.CertificateLogic;
import de.projectnash.databackend.UserPersistenceService;
import de.projectnash.entities.Certificate;
import de.projectnash.entities.User;
import de.projectnash.application.util.Constants;

/**
 * This class contains the logic to send an email to an {@link User} when his
 * {@link Certificate} will shortly expire.
 * 
 * @author Silvio D'Alessandro, Marius Boepple
 *
 */
public class CertificateExpireDbScheduler implements Job {

	@Override
	public void execute(JobExecutionContext jobToExecute)
			throws JobExecutionException {

		List<Certificate> allActiveCertificates = CertificateLogic
				.getAllCertificates(CertificateStatus.ACTIVE);

		/* set certificates to outdated & send reminding eMail */
		allActiveCertificates
				.forEach(certificate -> {
					try {
						if (!CertificateLogic.certificateIsValid(certificate)) {
							certificate
									.setCertificateStatus(CertificateStatus.OUTDATED);
							CertificateLogic.updateCertificate(certificate);
						}

						if (!certificate.isReminded()
								&& CertificateLogic.getTimeLeftForCertificate(
										certificate, TimeUnit.DAYS) < Constants.TIMEFRAME_FOR_REMINDER) {

							EmailUtility.sendMail(UserPersistenceService
									.loadUser(certificate.getEmailAddress()),
									EmailSubject.CERTIFICATE_EXPIRE);
							certificate.setReminded(true);
							CertificateLogic.updateCertificate(certificate);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				});
	}
}
