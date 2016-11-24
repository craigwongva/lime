In CloudFormation run cf-jenkins.json.

Once CloudFormation is done, in Jenkins "Manage Configuration":
* Add JDK ('J1') using defaults. (I tried to paste the path /usr/lib/jvm/java-1.8.0-openjdk, but it doesn't find it.)
* Add Maven ('M1'): install automatically
(Actually the above two aren't necessary.)

Add credential (for PCF).

After building, use this to capture the config.xml:
java -jar jenkins-cli.jar -s http://localhost:8080/ get-job atmosphere-pipeline

The Sonar Scanner results are created via lime/run-sonar, and the output goes to the arbitrary location /tmp/run-1300.

