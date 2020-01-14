# Format
The config file has a simple format: An encompasing object where each entry
is a Category, an array of objects which hold config entries. Each entry
needs a name, description, and value.

Example:
```json
{
	"category_name": [
		{
			"name": "test entry",
			"description": "entry description",
			"value": 37
		},
		{
			"name": "test entry 2",
			"description": "another entry",
			"value": "test"
		}
	],
	"": [
		{
			"name": "test entry 3",
			"description": "an entry without a category",
			"value": "test 2"
		}
	]
}
```