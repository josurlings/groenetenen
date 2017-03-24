package be.vdab.mail;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import be.vdab.entities.Filiaal;

@Component 
class DefaultMailSender implements MailSender 
{
private final static Logger LOGGER =
Logger.getLogger(DefaultMailSender.class.getName());
private final JavaMailSender sender; 
private final String webmaster;

DefaultMailSender(JavaMailSender sender, 
@Value("${webmaster}") String webmaster)
{
this.sender = sender;
this.webmaster = webmaster;
}

@Override
@Async
public void nieuwFiliaalMail(Filiaal filiaal, String urlFiliaal) 
{
try 
{
MimeMessage message = sender.createMimeMessage(); 
MimeMessageHelper helper = new MimeMessageHelper(message); 
helper.setTo(webmaster);
helper.setSubject("Nieuw filiaal");
// helper.setText(String.format("Filiaal <strong>%s</strong> is toegevoegd",
// filiaal.getNaam()), true); 
helper.setText(String.format(
"Je kan het nieuwe filiaal <strong>%s</strong> " +
"<a href='%s/wijzigen'>hier</a> nazien", filiaal.getNaam(), urlFiliaal), 
true);
sender.send(message); 
}
catch (MessagingException | MailException ex) 
{
LOGGER.log(Level.SEVERE, "kan mail nieuw filiaal niet versturen", ex);
throw new RuntimeException("Kan mail nieuw filiaal niet versturen", ex);
}
}
}