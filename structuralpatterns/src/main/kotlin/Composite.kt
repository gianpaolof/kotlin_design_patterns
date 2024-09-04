interface GraphComponent {
    val name: String
    fun display()
}

class Node(override val name: String, val isRoot: Boolean = false) : GraphComponent {
    private val connections = mutableListOf<Node>()

    fun addConnection(node: Node) {
        connections.add(node)
    }

    override fun display() {
        val rootIndicator = if (isRoot) " (Root)" else ""
        println("Node: $name$rootIndicator, Connections: ${connections.joinToString { it.name }}")
    }
}

class Graph(val root: Node) : GraphComponent { // Store the root node directly
    override val name: String = "My Graph" // You can set a name for the graph if needed

    override fun display() {
        println("Graph: $name")
        root.display() // Start displaying from the root node
    }
}
