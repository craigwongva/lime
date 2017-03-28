This stack demonstrates a simple pipeline.

To demo it:

In CloudFormation run cf-jenkins.json.

Add credential (for PCF).

After building, use this to capture the config.xml:
java -jar jenkins-cli.jar -s http://localhost:8080/ get-job mypipe > mypipe.xml

The Sonar Scanner results are created via lime/run-sonar, and the output goes to the arbitrary location /tmp/run-1300.

--
The Grails code demonstrates how to interpret the PCF VCAP_SERVICES env variable.

The stack/pipeline demo and the Grails/VCAP demo aren't really related.

To demo it:

In Jenkins job 'mypipe' make sure the cf push is pushing to cfapps.io.
In Jenkins build the mypipe project. 
http://banno.cfapps.io/blue/slice/random
You should see:
{password=judy, username=craig}
craig/judy

Actually that first line shows itself surrounded by double quotes, but copying it and pasting it here doesn't show it.

--
The Grails code also tags instances and images.

Use case:
This code doesn't actually DO anything, i.e. it's read-only.
Use this code to generate create-tags commands, then you the human run those commands.

1. Create stack using https://s3.amazonaws.com/venicegeo-devops-dev-lime-project/cf-jenkins.json
2. It takes at least ten minutes. It does a lengthy Jenkins install before the Grails install.
3. Sign onto the instance.
4. cd /home/ec2-user/lime/
5. Edit grails-app/controllers/blue/ServiceController.groovy
6. Change the target region, if necessary. By default three regions are scanned.

grails -Dserver.port=8887 run-app

Look at the output that suggests tags needed.
Remove the --dry-run.
Run the commands to tag the instances. For example:
aws ec2 create-tags --resources i-0457cae63f023ed9a i-09daee605405d6bc6 --tags Key=Project,Value=geowave-dev --region us-east-1
aws ec2 create-tags --resources i-07014cdcf56b0d018 --tags Key=Project,Value=piazza-dev --region us-east-1
aws ec2 create-tags --resources i-00d5ed2271af2ae19 --tags Key=Project,Value=mrgeo-dev --region us-east-1
