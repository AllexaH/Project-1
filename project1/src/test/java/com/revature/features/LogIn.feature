Feature: Login Feature

Scenario: Login As Employee
	Given: I am on the login page
	When I type "JDoe" in the username text bar
	When I type "password2" in the password text bar
	And I click on the login button
	Then I should be redirected into the employee homepage
	
Scenario: Login As Manager
	Given: I am on the login page
	When I type "JaneD" in the username text bar
	When I type "password1" in the password text bar
	And I click on the login button
	Then I should be redirected into the manager homepage