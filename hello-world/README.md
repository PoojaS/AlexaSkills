# AlexaSkills

1. Create an AWS Lambda with basic execution policy and an alexa skill kit trigger
2. Upload the artifact for this project as zip file
    * ../gradlew fatJar
    * jar file gets generated in build/libs
3. Increase the timeout from default 3 sec to 10 sec
4. Create an Alexa Skill and link the ARN of the above lambda function
5. Copy the IntentSchema.json and SampleUtterances.txt into the intent model configuration
6. Update the application id of the skill in the supported application id list and build and upload zip again
7. Save and test