package ru.stqa.software_testing.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.software_testing.addressbook.model.ContactData;
import ru.stqa.software_testing.addressbook.model.Contacts;
import ru.stqa.software_testing.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    application.goTo().homePage();
    if (application.db().contacts().size() == 0) {

      application.group().create(new GroupData().withName("Test").withHeader("test2"));
      application.contact().create(new ContactData().withFirstname("First Name").withLastname("Last Name")
              .withHomePhone("111").withMobilePhone("222").withWorkPhone("333").withCompanyAddress("  Elm st.  ")
              .withEmail1("Fred@ ").withEmail2(" Kruger@").withEmail3("Nightmare@"), true);
    }
  }


  @Test
  public void testContactDeletion() throws Exception {

    Contacts before = application.db().contacts();
    ContactData deletedContact = before.iterator().next();
    application.contact().delete(deletedContact);
    application.goTo().homePage();
    assertThat( application.contact().count(), equalTo(before.size()-1));
    Contacts after = application.db().contacts();
    before.remove(deletedContact);
    assertThat(after, equalTo(before.withOut(deletedContact)));



  }

}

