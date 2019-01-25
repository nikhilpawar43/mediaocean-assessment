# mediaocean-assessment

# Operations to be performed:
  1. Add all the teams using post request.
  2. Get the list of all teams
  3. Get the match schedule
  
# Http Urls for the actions:
  1. Add all the teams using post request.
      URL -> http://localhost:8888/api/teams/addTeams
	    method -> POST
	    Headers -> Content-Type: application/json 
      body ->
      [
	{
		"name" : "Bengal Warriors",
		"homeGround" : "Kolkata"
	},
	{
		"name" : "Bengaluru Bulls",
		"homeGround" : "Bengaluru"
	},
	{
		"name" : "Dabang Delhi KC",
		"homeGround" : "Delhi"
	},
	{
		"name" : "Gujarat Fortune Giants",
		"homeGround" : "Ahmedabad"
	},
	{
		"name" : "Haryana Steelers",
		"homeGround" : "Sonipat"
	},
	{
		"name" : "Jaipur Pink Panthers",
		"homeGround" : "Jaipur"
	},
	{
		"name" : "Patna Pirates",
		"homeGround" : "Patna"
	},
	{
		"name" : "Puneri Paltan",
		"homeGround" : "Pune"
	},
	{
		"name" : "Tamil Thalaivas",
		"homeGround" : "Chennai"
	},
	{
		"name" : "Telugu Titans",
		"homeGround" : "Hyderabad"
	},
	{
		"name" : "U Mumba",
		"homeGround" : "Mumbai"
	},
	{
		"name" : "UP Yoddha",
		"homeGround" : "Lucknow"
	}
]

2. Get the list of all teams
	
	URL -> http://localhost:8888/api/teams/getTeams
	method -> GET
	
3. Get the match schedule
	
	URL -> http://localhost:8888/api/tournaments/generateSchedule
	method -> GET
