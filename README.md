README for hiringdefined
==========================

And you can view the logs with this command
* heroku logs --tail
	
After application modification, repackage it with
* ./gradlew -Pprod bootRepackage -x test
	
And then re-deploy it with
* ./gradlew -Pprod bootRepackage -x test && heroku deploy:jar --jar build/libs/*.war
