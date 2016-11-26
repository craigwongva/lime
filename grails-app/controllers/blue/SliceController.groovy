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
}
