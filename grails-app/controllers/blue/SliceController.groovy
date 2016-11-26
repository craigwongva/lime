package blue

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
	//fails: [ author: staticAuthor.value[0]["user-provided"], content: staticContent ]
	[ author: staticAuthor.value.grep{it.key=='user-provided'}, content: staticContent ]
    }
}
