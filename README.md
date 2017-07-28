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

--
Instructions for building a j** from scratch:

10 rem aws cloudformation create-stack --stack-name craigj31 --template-url https://s3.amazonaws.com/venicegeo-devops-dev-root/lime/cf-jenkins.json --region us-west-2 --parameters ParameterKey=instancetype,ParameterValue=t2.large ParameterKey=tomcatmgrpassword,ParameterValue=M2p1c3Rhc3RoRSM= ParameterKey=tomcaturl,ParameterValue=34.210.232.195

20 rem with a t53 in mind (already built)

22 add credentials 12345 in Manage Jenkins for PCF

24 add craigradiantblueoregon.pem to /home/jenkins

26 open security group :8080

30 in CF run lime/cf-jenkins (or use the above CLI statement)

40 in Jenkins run something-green with parameter values t53, t53

50 rem this will crash quickly: go to Manage Jenkins > In Process Script Approval to allow scripting

60 goto 40


