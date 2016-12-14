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
	    def allInstances = []
	    def allTagged = [:]
	    def all = "aws ec2 describe-instances --filter Name=key-name,Values=peizer Name=image-id,Values=ami-5ec1673e --query Reservations[].Instances[].[InstanceId] --region us-west-2 --output text".execute()
	    allInstances = all.text.split()
	def projects = ['beachfront', 'piazza']
	projects.each { p ->
	    def tagged = "aws ec2 describe-instances --filter Name=key-name,Values=peizer Name=image-id,Values=ami-5ec1673e Name=tag:craigproject,Values=$p --query Reservations[].Instances[].[InstanceId] --region us-west-2 --output text".execute()
	    allTagged[p] = tagged.text.split()
	}
	println "allInstances is $allInstances"
	def untaggedInstances = allInstances
	projects.each { p ->
	    allTagged[p].each { i ->
		println "$p/$i"
	    }
	    untaggedInstances -= allTagged[p]
	}
	untaggedInstances.each { i ->
	    println "untagged/$i"
	}
        render 'leaving servers5'
    }
}
