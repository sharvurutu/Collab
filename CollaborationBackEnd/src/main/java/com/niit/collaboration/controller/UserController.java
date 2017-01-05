package com.niit.collaboration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collaboration.dao.UserDAO;
import com.niit.collaboration.model.User;

@RestController
public class UserController {

	@Autowired
	User user;

	@Autowired
	UserDAO userDAO;

	// headers="Accept=application/json"
	@RequestMapping(value = "/helloworld", method = RequestMethod.GET)

	public String Hello() {
		return "Hello";
	}

	// Get List of all users
	// http://localhost:8080/collaboration/allusers
	@RequestMapping(value = "/allUsers", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getAllUser() {
		List<User> users = userDAO.list();

		if (users.isEmpty()) {
			user.setErrorCode("404");
			user.setErrorMessage("No User are available");
			users.add(user);
		}

		// errorcode :200 :404
		// errormessage Success :Not Found

		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	// Get User by Id
	// http://localhost:8080/collaboration/allusers/id

	@RequestMapping(value = "/userById/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> getUserById(@PathVariable("id") String userEmailId) {
		user = userDAO.get(userEmailId);

		if (user == null) {
			user = new User();// to avoid NullPointerException
			user.setErrorCode("404");
			user.setErrorMessage("User Not Found with ID  " + userEmailId);
		}

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	// http://localhost:8080/collaboration/allusers/id/password
	// Instead of Request mapping POST method we can use
	// @PostMapping("authenticate") also
	// sending values from request body--- {"Id":"101", "password":"12345"}

	/*
	 * @RequestMapping(value = "/authenticate/{Id}/{password}", method =
	 * RequestMethod.POST)
	 */
	@RequestMapping(value = "/authenticate/", method = RequestMethod.POST)
	public ResponseEntity<User> authenticate(@RequestBody User user) {
		user = userDAO.IsValidUser(user.getId(), user.getPassword());

		if (user == null) {
			user = new User();// to avoid NullPointerException
			user.setErrorCode("404");
			user.setErrorMessage("Invalid Credentials");
		}

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public ResponseEntity<User> registerUser(@RequestBody User user) {

		if (userDAO.save(user) == false) {
			user.setErrorCode("404");
			user.setErrorMessage("Registration Unsuccessfull");
		} else {
			user.setErrorCode("200");
			user.setErrorMessage("Thankyou for Registration...");
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/UpdateUser", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@RequestBody User user) {

		if (userDAO.update(user) == false) {
			user.setErrorCode("404");
			user.setErrorMessage("Updation Unsuccessfull");
		} else {
			user.setErrorCode("200");
			user.setErrorMessage("Update is Successfull..");
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

}
