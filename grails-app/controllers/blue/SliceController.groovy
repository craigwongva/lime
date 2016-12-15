package blue

import groovy.json.*

class SliceController {

    def random = {
	def env = System.getenv()
	def vcs = env.grep{it.key=='VCAP_SERVICES'} 
	//VCAP_SERVICES looks like this:
	//[{"user-provided":[{ "credentials": { "password": "judy", "username": "craig" }, "syslog_drain_url": "", "volume_mounts": [ ], "label": "user-provided", "name": "myups", "tags": [ ] }]}]
	//This is a string:
	//{"user-provided":[{ "credentials": { "password": "judy", "username": "craig" }, "syslog_drain_url": "", "volume_mounts": [ ], "label": "user-provided", "name": "myups", "tags": [ ] }]}
	def jsonSlurper = new JsonSlurper()
	def object = jsonSlurper.parseText(vcs.value[0])
	def credentials = object."user-provided"[0].credentials 
	//credentials looks like this:
	//{password=judy, username=craig}
	[credjson: credentials, 
         credslashstring: credentials.username + '/' + credentials.password ]
    }

    def servers = {
	def allProjectValuesAsNameCRLFValue = []
	//The following returns a literal 'Project' on one line, then a value like 'beachfront-int' on the next line.
        def getAllProjectValuesAsNameCRLFValue = "aws ec2 describe-instances --filter Name=tag-key,Values=Project --query Reservations[].Instances[].[Tags] --region us-west-2 --output text".execute()
	allProjectValuesAsNameCRLFValue = getAllProjectValuesAsNameCRLFValue.text.split()
	int allProjectValuesAsNameCRLFValueSize = allProjectValuesAsNameCRLFValue.size()

	def allProjectValues = []
	for (int i=0; i<allProjectValuesAsNameCRLFValueSize; i += 2) {
	    if (allProjectValuesAsNameCRLFValue[i] == 'Project') {
		allProjectValues.add(allProjectValuesAsNameCRLFValue[i+1])
	    }
	}

	//Use a list of allInstances to determine which instancs are untagged,
	// because the CLI doesn't seem to allow the same wildcards that the EC2 Dashboard GUI filter window allows
	// e.g. "Not tagged" in "tag:Project : Not tagged"
	def allInstances = []
	def allTagged = [:]
	//The following returns one InstanceId value per line.
	def all = "aws ec2 describe-instances --query Reservations[].Instances[].[InstanceId] --region us-west-2 --output text".execute()
	allInstances = all.text.split()

	//Sort the Project values (e.g. beachfront-dev, piazza-int) just for println readability.
	def projects = allProjectValues.sort()
	projects.each { p ->
	    //The following returns one InstanceID value per line.
	    def tagged = "aws ec2 describe-instances --filter Name=tag:Project,Values=$p --query Reservations[].Instances[].[InstanceId] --region us-west-2 --output text".execute()
	    allTagged[p] = tagged.text.split()
	}

	def untaggedInstances = allInstances
	projects.each { p ->
	    allTagged[p].each { i ->
		println "$i/$p"
	    }
	    untaggedInstances -= allTagged[p]
	}
	untaggedInstances.each { i ->
	    println "$i/untagged"
	}

        render 'leaving servers5'
    }
}
