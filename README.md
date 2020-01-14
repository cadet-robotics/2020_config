# 2020_config
 2020 Config System Project
 
 Currently only planning for the Config System.
 
 The config system will be separated into two parts: The API and the GUI. The Config API will load and process files, and the GUI will provide a modular
editor for configuring robots.
 
# The API
 Requirements for the API include
 * File IO (load JSON)
 * Parsing into values
   * Config entries are placed into json objects with a name, description, and value
   * This value must be extracted
 * The API should eliminate the need for the Robot to use gson from its end

 # The GUI
  Requirements for the GUI include
  * Modularity
    * System for adding configurable components (RoboRIO, joysticks, etc)
	* System for viewing these components in a tabbed environment
	  * Replicate the result from the 2019 system
  * Usability
    * Replicate the 2019 editor
	* Per tab, dropdown menu with descriptions
	  * Optional image highlighting (again see 2019 gui)
  * Other
    * Deploy to robot
	
	DOSBox (shared) (file://DESKTOP-29LSOS8/Users/Cadet%20Robotics/Desktop/DOSBox%20(shared))
