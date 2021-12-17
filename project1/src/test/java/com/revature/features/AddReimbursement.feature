Feature: Add Reimbursement Feature

Scenario: Adding Reimbursement Successfully (positive test)
	Given I am logged in as an employee
	When I type in the number 432 in the amount text bar
	When I select "TRAVEL" in the drop down menu
	When I type "Buisness flight" in the description text bar
	When I click the choose file button
	And I submit an image file
	And I click on submit reimbursement
	Then I should see the reimbursement added to the table 