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
	def staticAuthor = env.grep{it.key=='PORT'} //['alabama','georgia','florida'] //'Anonymous'
	def staticContent = 'Red Sky at Dawn'
	[ author: staticAuthor, content: staticContent ]
    }
}
