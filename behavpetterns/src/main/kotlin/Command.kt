/*
Core Idea

The Command pattern encapsulates a request as an object,
thereby letting you parameterize clients with different requests, queue or log requests,
and support undoable operations.

When to Use

    You want to decouple the invoker of an action from the object that performs the action.
    You want to support undo/redo operations.
    You want to queue or log requests.
    You want to parameterize objects with operations.

Key Components

    Command: An interface or abstract class that declares an execute() method (and optionally an undo() method).
    ConcreteCommand: Implements the Command interface, encapsulating a specific action and the receiver object on which the action is performed.
    Invoker: Holds a reference to a Command object and invokes its execute() method.
    Receiver: The object that performs the actual work associated with the command.
    Client: Creates ConcreteCommand objects and sets them on the Invoker.


Example

1. `NetworkCommand` Interface: Defines the `execute` and `undo` methods.
2. Concrete Commands:
   * `EnableWifiCommand`: Encapsulates the action of enabling Wi-Fi on a router.
   * `SetPasswordCommand`: Encapsulates the action of changing the router's password, storing the old password for potential undo.
3. `Router` (Receiver): Represents the network device on which commands are executed.
4. `NetworkAdminConsole` (Invoker):
   * Maintains a `commandHistory` to support undo functionality.
   * `executeCommand`: Executes a command and adds it to the history.
   * `undoLastCommand`: Removes the last command from history and calls its `undo` method.
5. `main` function:
   * Creates a `Router` and a `NetworkAdminConsole`.
   * Executes commands to enable Wi-Fi and change the password.
   * Demonstrates undo functionality by reverting the last two commands.

**Key Points**

* This example showcases how the Command pattern can be applied to network management tasks.
* It allows for decoupling between the invoker (`NetworkAdminConsole`) and the receiver (`Router`).
* The `commandHistory` enables basic undo functionality.
* You can easily add more commands (e.g., `ConfigureDHCPCommand`, `AddPortForwardingRuleCommand`) to extend the system's capabilities.

Feel free to ask if you have any further questions or would like to see more complex scenarios or modifications!

 */

// Command interface
interface NetworkCommand {
    fun execute()
    fun undo() // Optional for undoable commands
}

// Concrete Commands
class EnableWifiCommand(private val router: IGPRouter) : NetworkCommand {
    override fun execute() {
        router.enableWifi()
        println("Wi-Fi enabled")
    }

    override fun undo() {
        router.disableWifi()
        println("Wi-Fi disabled (undo)")
    }
}

class SetPasswordCommand(private val router: IGPRouter, private val newPassword: String) : NetworkCommand {
    private var oldPassword: String? = null

    override fun execute() {
        oldPassword = router.getPassword() // Store the old password for undo
        router.setPassword(newPassword)
        println("Password changed to $newPassword")
    }

    override fun undo() {
        oldPassword?.let { router.setPassword(it) }
        println("Password reverted to $oldPassword (undo)")
    }
}

// Receiver
class IGPRouter {
    fun enableWifi() {
        // ... (Logic to enable Wi-Fi)
    }

    fun disableWifi() {
        // ... (Logic to disable Wi-Fi)
    }

    fun getPassword(): String {
        // ... (Logic to get current password)
        return "old_password"
    }

    fun setPassword(newPassword: String) {
        // ... (Logic to set new password)
    }
}

// Invoker
class NetworkAdminConsole {
    private val commandHistory = mutableListOf<NetworkCommand>()

    fun executeCommand(command: NetworkCommand) {
        command.execute()
        commandHistory.add(command)
    }

    fun undoLastCommand() {
        if (commandHistory.isNotEmpty()) {
            val lastCommand = commandHistory.removeAt(commandHistory.size - 1)
            lastCommand.undo()
        } else {
            println("No commands to undo")
        }
    }
}

