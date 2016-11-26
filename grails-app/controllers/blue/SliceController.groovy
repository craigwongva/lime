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
	def staticAuthor = 'Anonymous'
	def staticContent = 'Red Sky at Dawn'
	[ author: staticAuthor, content: staticContent ]
    }
}
