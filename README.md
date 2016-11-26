In CloudFormation run cf-jenkins.json.

Add credential (for PCF).

After building, use this to capture the config.xml:
java -jar jenkins-cli.jar -s http://localhost:8080/ get-job atmosphere-pipeline

The Sonar Scanner results are created via lime/run-sonar, and the output goes to the arbitrary location /tmp/run-1300.

--
In Jenkins job 'mypipe' make sure the cf push is pushing to cfapps.io.
In Jenkins build mypipe. 
http://banno.cfapps.io/slice/random
You should see:
{password=judy, username=craig}
craig/judy

Actually that first line shows itself surrounded by double quotes, but copying/pasting here doesn't show it.

