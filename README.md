# 2020_config
 2020 Config System Project
 
 Currently only planning for the Config System.
 
 The config system will be separated into two parts: The API and the GUI. The Config API will load and process files, and the GUI will provide a modular
editor for configuring robots.

## Installation
 To use, add the .jar from the latest `Release`. Further, install Gson as a library, as this depends on it.
 
# The API
 The API can currently:
 * Load files
 * Save files
 * Parse JSON to Entries
 * Eliminate direct use of Gson
 * Return values
 * Return separate instances for categories

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
