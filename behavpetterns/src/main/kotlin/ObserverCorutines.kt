/*
Understanding Channels, Flows, and Dispatchers in Kotlin Coroutines

    Channels: Think of channels as pipes for communicating data between coroutines. One coroutine can send data into a channel, and another coroutine can receive that data from the channel. Channels support asynchronous communication, allowing coroutines to suspend and resume seamlessly as they send or receive data.

    Flows: Flows are a way to represent sequences of values that can be computed asynchronously. They are built on top of coroutines and provide a more structured and flexible way to handle streams of data compared to channels.

    Dispatchers: Dispatchers control which threads coroutines execute on. They help you manage concurrency and ensure that your coroutines run on the appropriate threads for their tasks. Common dispatchers include:
        Dispatchers.Default: Used for CPU-intensive tasks.
        Dispatchers.IO: Used for blocking I/O operations (e.g., network requests, file reading/writing).
        Dispatchers.Main: Used for UI-related tasks on Android.


        ConcreteSubject

    eventChannel: A Channel is used to communicate events between the subject and its observers. It allows for asynchronous sending and receiving of data.
    scope: A CoroutineScope is created using Dispatchers.Default. This scope is tied to the lifecycle of the ConcreteSubject and will be used to launch coroutines for handling events.

registerObserver

    When an observer is registered, a new coroutine is launched within the scope.
    This coroutine converts the eventChannel into a Flow using receiveAsFlow().
    It then uses collect to receive events from the flow and calls the observer's update method for each event.

notifyObservers

    When the subject's state changes, it calls notifyObservers to send the new data to all registered observers.
    The runBlocking block ensures that the send operation on the channel is performed synchronously, blocking the current coroutine until the data is sent.
 */

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

interface Observer {
    fun update(data: String)
}

interface Subject {
    fun registerObserver(observer: Observer)
    fun removeObserver(observer: Observer)
    fun notifyObservers(data:
                        String)
}

class ConcreteSubject : Subject {
    private val observers = mutableListOf<Observer>()
    private val eventChannel = Channel<String>()
    private val scope = CoroutineScope(Dispatchers.Default) // Create a CoroutineScope

    override fun registerObserver(observer: Observer) {
        observers.add(observer)
        // Launch a coroutine within the scope
        scope.launch {
            eventChannel.receiveAsFlow().collect { data ->
                observer.update(data)
            }
        }
    }

    override fun removeObserver(observer: Observer) {
        observers.remove(observer)
    }

    override fun notifyObservers(data: String) {
        runBlocking {
            eventChannel.send(data)
        }
    }

    fun someActionThatChangesState(newData: String) {
        // ... (Perform some action that changes the state)
        notifyObservers(newData)
    }

    // Optional: Cancel the scope when the subject is no longer needed
    fun close() {
        scope.cancel()
    }
}

class ConcreteObserver : Observer {
    override fun update(data: String) {
        println("Observer received update: $data")
    }
}