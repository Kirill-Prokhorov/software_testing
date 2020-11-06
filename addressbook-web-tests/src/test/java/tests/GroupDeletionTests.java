package tests;

import org.testng.annotations.*;


public class GroupDeletionTests extends TestBase {


  @Test
  public void testGroupDeletion() throws Exception {

    application.getNavigationHelper().gotoGroupPage();
    application.getGroupHelper().selectGroup();
    application.getGroupHelper().deleteSelectedGroups();
    application.getNavigationHelper().gotoGroupPage();

  }

}