package blue

class SliceController {

    def index() { 
        render{['friend': 'Monica']}
/*
        render(contentType: 'text/json') {[
            'dotStatus': stringOfDotStatusEachRepresentsAPiazzaJob(),
            'dotDuration': stringOfDotDurationEachRepresentsAPiazzaJob(),
            'squareHealth': stringOfSquareHealthEachRepresentsAContainerOrProcess(),
            'dotCompletion': stringOfDotCompletion()
        ]}
*/
    }
}
