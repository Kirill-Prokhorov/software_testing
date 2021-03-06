package ru.stqa.software_testing.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.software_testing.mantis.model.MailMessage;

import javax.mail.MessagingException;
import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.*;

public class RegistrationTests extends TestBase {

  //@BeforeMethod
  public void  startMailServer(){
    app.mail().start();
  }

  @Test
  public void testRegistrationJames() throws IOException, MessagingException, ServiceException {
    skipIfNotFixed(app.soap().issueId());
    long now = System.currentTimeMillis();
    String user = String.format("user%s", now);
    String password = "password";
    String email = String.format("user%s@localhost", now);
    app.james().createUser(user ,password);
    app.registration().start(user ,email);
    List<MailMessage> mailMessages = app.james().waitForMail(user ,password, 60000);
    String confirmationLink = findConfirmationLink(mailMessages, email);
    app.registration().finish(confirmationLink , password);
    assertTrue (app.newSession().login(user,password));
  }

  @Test(enabled = false)
  public void testRegistrationWiser() throws IOException, MessagingException {
    long now = System.currentTimeMillis();
    String user = String.format("user_%s", now);
    String password = "password";
    String email = String.format("user_%s@localhost.localdomain", now);
    app.registration().start(user, email);
    List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
    String confirmationLink = findConfirmationLink(mailMessages, email);
    app.registration().finish(confirmationLink, password);
    assertTrue(app.newSession().login(user, password));
  }


  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }

  //@AfterMethod(alwaysRun = true)
  public void stopMailServer(){
    app.mail().stop();
  }

}
