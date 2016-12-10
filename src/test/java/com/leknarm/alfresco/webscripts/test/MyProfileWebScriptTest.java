package com.leknarm.alfresco.webscripts.test;

import java.io.IOException;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationComponent;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.web.scripts.BaseWebScriptTest;
import org.alfresco.service.cmr.security.MutableAuthenticationService;
import org.alfresco.service.cmr.security.PersonService;
import org.alfresco.util.GUID;
import org.alfresco.util.PropertyMap;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.TestWebScriptServer.GetRequest;
import org.springframework.extensions.webscripts.TestWebScriptServer.Response;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:alfresco/application-context.xml")
public class MyProfileWebScriptTest extends BaseWebScriptTest {
	
	private Logger log = Logger.getLogger(MyProfileWebScriptTest.class);
	
	private MutableAuthenticationService authenticationService;
	private AuthenticationComponent authenticationComponent;
	private PersonService personService;
	
	private static final String USER_ONE = "USER" + GUID.generate();
	private static final String MYPROFILE_WEBSCRIPTS_URL = "/api/people/myprofile";

	@Before
	public void setUp() throws Exception {
		super.setUp();
		this.authenticationService = (MutableAuthenticationService) getServer().getApplicationContext().getBean("AuthenticationService");
		this.authenticationComponent = (AuthenticationComponent) getServer().getApplicationContext().getBean("authenticationComponent");
		this.personService = (PersonService) getServer().getApplicationContext().getBean("PersonService");
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testGetUserOneProfile() throws IOException, JSONException {
		this.authenticationComponent.setCurrentUser(AuthenticationUtil.getAdminUserName());
		// Create users
		log.debug("create user " + USER_ONE + " for tests.");
		createUser(USER_ONE);
		
		log.debug("test get " + USER_ONE + " myprofile.");
		log.debug("set current user to " + USER_ONE + ".");
		authenticationComponent.setCurrentUser(USER_ONE);
		Response response = sendRequest(new GetRequest(MYPROFILE_WEBSCRIPTS_URL), Status.STATUS_OK);
		assertEquals(USER_ONE, new JSONObject(response.getContentAsString()).getString("userName"));
		
		this.authenticationComponent.setCurrentUser(AuthenticationUtil.getAdminUserName());
		// Clear the users
		log.debug("delete user " + USER_ONE + " after tests.");
		deleteUser(USER_ONE);
	}
	
	@Test
	public void testGetAdminProfile() throws IOException, JSONException {
		log.debug("test get " + AuthenticationUtil.getAdminUserName() + " myprofile.");
		log.debug("set current user to " + AuthenticationUtil.getAdminUserName() + ".");
		authenticationComponent.setCurrentUser(AuthenticationUtil.getAdminUserName());
		Response response = sendRequest(new GetRequest(MYPROFILE_WEBSCRIPTS_URL), Status.STATUS_OK);
		assertEquals(AuthenticationUtil.getAdminUserName(), new JSONObject(response.getContentAsString()).getString("userName"));
	}

	private void createUser(String userName) {
		if (this.authenticationService.authenticationExists(userName) == false) {
			this.authenticationService.createAuthentication(userName, "PWD".toCharArray());

			PropertyMap properties = new PropertyMap(4);
			properties.put(ContentModel.PROP_USERNAME, userName);
			properties.put(ContentModel.PROP_FIRSTNAME, "firstName");
			properties.put(ContentModel.PROP_LASTNAME, "lastName");
			properties.put(ContentModel.PROP_EMAIL, "email@email.com");
			properties.put(ContentModel.PROP_JOBTITLE, "jobTitle");

			this.personService.createPerson(properties);
		}
	}

	private void deleteUser(String username) {
		this.personService.deletePerson(username);
		if (this.authenticationService.authenticationExists(username)) {
			this.authenticationService.deleteAuthentication(username);
		}
	}

}
