package com.qa.tests;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.utils.TestUtils;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.InputStream;
import java.lang.reflect.Method;

public class NotificationTest extends BaseTest{
	LoginPage loginPage;
	JSONObject loginUsers;
	TestUtils utils = new TestUtils();
	
	  @BeforeClass
	  public void beforeClass() throws Exception {
			InputStream datais = null;
		  try {
			  String dataFileName = "data/loginUsers.json";
			  datais = getClass().getClassLoader().getResourceAsStream(dataFileName);
			  JSONTokener tokener = new JSONTokener(datais);
			  loginUsers = new JSONObject(tokener);
		  } catch(Exception e) {
			  e.printStackTrace();
			  throw e;
		  } finally {
			  if(datais != null) {
				  datais.close();
			  }
		  }
		  closeApp();
		  launchApp();
	  }

	 
	  @BeforeMethod
	  public void beforeMethod(Method m) {
		  utils.log().info("\n" + "****** starting test:" + m.getName() + "******" + "\n");
		  loginPage = new LoginPage();		  
		 loginPage.login(loginUsers.getJSONObject("validUser").getString("username"), 
				  loginUsers.getJSONObject("validUser").getString("password"));

		 loginPage.pressLoginBtn();
	  }

	  @AfterMethod
	  public void afterMethod() {
		  loginPage.pressLogoutBtn();
	  }
	  
	  @Test
      public void testNotificationCount() {
   
    MobileElement notificationCountElement = driver.findElementById("notificationCount");
    int initialCount = Integer.parseInt(notificationCountElement.getText());

    int updatedCount = Integer.parseInt(notificationCountElement.getText());
    Assert.assertEquals(updatedCount, initialCount + 1);
}
@Test
public void testMarkNotificationAsRead() {
   
    MobileElement notificationElement = driver.findElementById("notificationText");

    Assert.assertTrue(isNotificationUnread(notificationElement));

    notificationElement.click();

    Assert.assertFalse(isNotificationUnread(notificationElement));
}

private boolean isNotificationUnread(MobileElement notificationElement) {
   
}

 @Test
public void testListUnreadNotifications() {
   
    List<MobileElement> notificationElements = driver.findElementsById("notificationItem");

    int unreadCount = countUnreadNotifications(notificationElements);
    Assert.assertEquals(unreadCount, expectedUnreadCount);
}

private int countUnreadNotifications(List<MobileElement> notificationElements) {
    int unreadCount = 0;
    for (MobileElement notification : notificationElements) {
        if (isNotificationUnread(notification)) {
            unreadCount++;
        }
    }
    return unreadCount;
}
private boolean isNotificationUnread(MobileElement notificationElement) {

}


@Test
public void testNotificationDismissal() {
   
    MobileElement notificationElement = driver.findElementById("notificationText");
    Assert.assertFalse(notificationElement.isDisplayed());
}

@Test
public void testNotificationSound() {
   
    MobileElement notificationElement = driver.findElementById("notificationText");
    String notificationText = notificationElement.getText();
    String expectedNotificationText = "Your Notification Message";
    Assert.assertEquals(notificationText, expectedNotificationText);
}

@Test
public void testNotificationAction() {
 
    MobileElement notificationElement = driver.findElementById("notificationText");

    notificationElement.click();
}


}
