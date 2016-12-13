#!/usr/bin/env groovy
/*
def sendEmailViaSES(successOrFailure) {
    println successOrFailure
    def x5 = "python /var/lib/jenkins/jobs/mypipe/workspace/ses.py mypipe $successOrFailure".execute()
    x5.waitFor()
    println x5.text
    println x5.err.text    
}

node {
    
    stage 'git'
    git 'https://github.com/craigwongva/lime.git'

    stage 'build'
    def x1 = "/var/lib/jenkins/jobs/mypipe/workspace/build-grails-mypipe"
    def x1text = x1.execute().text
    println "@1"
    println x1text
    stage 'cf login'
    withCredentials([usernamePassword(credentialsId: '12345', passwordVariable: 'pwd', usernameVariable: 'user')]) {
        //println "${env.JOB_NAME} is where its at"

        def cfcmd = "/cf"
        def cftarget = "https://api.devops.geointservices.io"
        //cftarget = "https://api.run.pivotal.io"
        def cfskip = "--skip-ssl-validation"
        //cfskip = ""
        def cforg = "piazza"
        //cforg = "redf4th"
        def cfspace = "int"
        //cfspace = "development"
        def cmd = "$cfcmd login -a $cftarget -u $user -p $pwd -o $cforg -s $cfspace $cfskip"
        def cmdtext = cmd.execute().text
        println "@2"
        println cmdtext    
    }

    stage 'cf push'
    def cfcmd = "/cf"    
    def appname = "banno"    
    def jobsdir = "/var/lib/jenkins/jobs"
    def targetdir = "mypipe/workspace/target"
    def war = "blue-0.1.war"
    def cmd2 = "$cfcmd push $appname -p $jobsdir/$targetdir/$war"
    def cmdtext2 = cmd2.execute().text
    println "@3"
    println cmdtext2
    
    stage 'push to nexus'
    def mickey = [
      "curl",
      "--user", 
      "admin:admin123", 
      "--upload-file", 
      "/var/lib/jenkins/jobs/mypipe/workspace/target/blue-0.1.war", 
      "http://localhost:8079/repository/myrawrepo/blue-0.1.war"].execute().text
    println "@4"
    println mickey

    stage 'sonar'
    def cmd3 = "/var/lib/jenkins/jobs/mypipe/workspace/run-sonar"
    //I am Jenkins running from /
    def cmdtext3 = cmd3.execute().text
    println "@4"
    println cmdtext3

    stage 'notify'
    def x4 = 'cat /tmp/run-1300'.execute() | ['grep', 'EXECUTION SUCCESS'].execute()
    x4.waitFor()
    if (x4.text.size() == 0) {
        sendEmailViaSES('failure')
        'intentionally-fail-this-jenkins-job'.execute()
    }
    else {
        sendEmailViaSES('success')
    }
    
    stage 'map-route'
    println "map-route start"    
    def x5 = './cf map-route banno int.geointservices.io --hostname white'.execute()
    x5.waitFor()
    println x5.text
    println "map-route end"
}
*/
node {
    stage 'git'
    git 'https://github.com/craigwongva/lime.git'

    stage 'build'
    def x1 = "/var/lib/jenkins/jobs/mypipe/workspace/build-grails-mypipe"
    def x1text = x1.execute().text
    println "@1"
    println x1text
    stage 'cf login'
    withCredentials([usernamePassword(credentialsId: '12345', passwordVariable: 'pwd', usernameVariable: 'user')]) {
        //println "${env.JOB_NAME} is where its at"

        def cfcmd = "/cf"
        def cftarget = "https://api.devops.geointservices.io"
        //cftarget = "https://api.run.pivotal.io"
        def cfskip = "--skip-ssl-validation"
        //cfskip = ""
        def cforg = "piazza"
        //cforg = "redf4th"
        def cfspace = "int"
        //cfspace = "development"
        def cmd = "$cfcmd login -a $cftarget -u $user -p $pwd -o $cforg -s $cfspace $cfskip"
        def cmdtext = cmd.execute().text
        println "@2"
        println cmdtext    
    }

}
