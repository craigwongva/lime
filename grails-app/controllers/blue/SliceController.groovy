package blue

import groovy.json.*
//import groovyx.net.http.*
//import static groovyx.net.http.ContentType.JSON

class SliceController {

    def index() { 
        render(view:'index',model:[friend: 'Monica'])
/*
        render(contentType: 'text/json') {[
            'dotStatus': stringOfDotStatusEachRepresentsAPiazzaJob(),
            'dotDuration': stringOfDotDurationEachRepresentsAPiazzaJob(),
            'squareHealth': stringOfSquareHealthEachRepresentsAContainerOrProcess(),
            'dotCompletion': stringOfDotCompletion()
        ]}
*/
    }

    def random = {
def env = System.getenv()
//Print all the environment variables.

env.each{
println it
} 
// You can also access the specific variable, say 'username', as show below 
String user= env['USERNAME']
	def staticAuthor = env.grep{it.key=='VCAP_SERVICES'} //['alabama','georgia','florida'] //'Anonymous'
	def staticContent = 'Red Sky at Dawn'
//[{"user-provided":[{ "credentials": { "password": "judy", "username": "craig" }, "syslog_drain_url": "", "volume_mounts": [ ], "label": "user-provided", "name": "myups", "tags": [ ] }]}]
	//this is a string:
	//{"user-provided":[{ "credentials": { "password": "judy", "username": "craig" }, "syslog_drain_url": "", "volume_mounts": [ ], "label": "user-provided", "name": "myups", "tags": [ ] }]}
	def jsonSlurper = new JsonSlurper()
	def object = jsonSlurper.parseText(
	 staticAuthor)
	[ author: object.value[0]."user-provided", content: staticContent ]
    }
}
