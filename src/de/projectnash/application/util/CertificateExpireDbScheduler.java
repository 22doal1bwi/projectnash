package de.projectnash.application.util;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import de.projectnash.application.CertificateLogic;
import de.projectnash.databackend.UserPersistenceService;
import de.projectnash.entities.Certificate;
import de.projectnash.entities.User;
import de.projectnash.application.util.Constants;

/**
 * This class contains the logic to send an email to an {@link User} when his {@link Certificate} will shortly expire.
 * @author Silvio D'Alessandro
 *
 */
public class CertificateExpireDbScheduler implements Job {

	@Override
	public void execute(JobExecutionContext jobToExecute) throws JobExecutionException {
		
			/** Loads all certificates that belong to a user. */
			List<Certificate> allCertificates = UserPersistenceService
					.loadAllUsers().stream().map(User::getCertificate)
					.collect(Collectors.toList());

			allCertificates.forEach(certificate -> {
				try{
						if (CertificateLogic.getTimeLeftForCertificate(certificate, TimeUnit.DAYS) < Constants.TIMEFRAME_FOR_REMINDER) {

							/** Needed because the email address always contains '\n' as its last index. */
							String mailAddress = certificate.getEmailAddress().substring(0,certificate.getEmailAddress().length() - 1);

							EmailUtility.sendMail(UserPersistenceService.loadUser(mailAddress), EmailSubject.CERTIFICATE_EXPIRE);
						}
				} catch (Exception e) {
					e.printStackTrace();
				}
		   });
	}
}
