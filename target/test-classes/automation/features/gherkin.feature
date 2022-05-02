Feature: EXYM-433 View activity ID on notes of the user Natalie Jones 

		 Background
			Given I am the clinician Natalie Jones: 
			When I view the vNext homepage, 
			I want to see my ID notes that need to be completed, 
			instead of having to click in to view the activity summary to see this number. 
	
	
	Scenario: Display in the notes table a column with a header of 'TITLE'
		Given I am a clinician user
		 When I go to the main page Exym vNext portal
		 Then I should see an 'i' icon on the right of activity name	 
		  And I can hover over the icon
          And I should see a popover
          And I should see a black background and white text with ‘Activity ID: 000000’ in the popover