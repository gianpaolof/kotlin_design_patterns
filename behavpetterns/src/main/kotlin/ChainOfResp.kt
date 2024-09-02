/*
Core Idea

    The Chain of Responsibility pattern creates a chain of handler objects.


Each handler in the chain has the opportunity to process a request.

If a handler cannot handle the request, it passes it along the chain to the next handler.

This continues until a handler processes the request or the end of the chain is reached.


When to Use

    You have multiple handlers that can potentially process a request, but the specific handler that should handle it isn't known beforehand.


You want to decouple the sender of a request from its receiver(s).
The set of objects that can handle a request should be specified dynamically.


Benefits

    Decouples the sender and receiver of a request.

Source icon
Avoids coupling the sender of a request to its receiver, giving more than one object a chance to handle the request.
Source icon
Increased flexibility in assigning responsibilities to objects.
Source icon
You can add or change responsibilities for handling a request by adding to or otherwise modifying the chain at run-time.
 */

abstract class SupportHandler(val nextHandler: SupportHandler?) {
    abstract fun handleRequest(request: SupportRequest)
}

class Level1SupportHandler(nextHandler: SupportHandler?) : SupportHandler(nextHandler) {
    override fun handleRequest(request: SupportRequest) {
        if (request.complexity == "basic") {
            println("Level 1 Support handling request: ${request.description}")
        } else {
            nextHandler?.handleRequest(request)
        }
    }
}

class Level2SupportHandler(nextHandler: SupportHandler?) : SupportHandler(nextHandler) {
    override fun handleRequest(request: SupportRequest) {
        if (request.complexity == "complex") {
            println("Level 2 Support handling request: ${request.description}")
        } else {
            nextHandler?.handleRequest(request)
        }
    }
}

class Level3SupportHandler(nextHandler: SupportHandler?) : SupportHandler(nextHandler) {
    override fun handleRequest(request: SupportRequest) {
        if (request.complexity == "highest") {
            println("Level 3 Support handling request: ${request.description}")
        } else {
            nextHandler?.handleRequest(request)
        }
    }
}


// ... (Similar classes for Level2SupportHandler and Level3SupportHandler)

data class SupportRequest(val description: String, val complexity: String)

fun main() {

}