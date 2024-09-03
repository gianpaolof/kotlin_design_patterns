import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
interface NetworkObserver {
    fun onEvent(event: NetworkEvent)
}

interface NetworkSubject {
    fun registerObserver(observer: NetworkObserver)
    fun removeObserver(observer: NetworkObserver)
    fun notifyObservers(event: NetworkEvent)
}

class NetworkMonitor : NetworkSubject {
    private val observers = mutableListOf<NetworkObserver>()

    override fun registerObserver(observer: NetworkObserver) {
        observers.add(observer)
    }

    override fun removeObserver(observer: NetworkObserver) {
        observers.remove(observer)
    }

    override fun notifyObservers(event: NetworkEvent) {
        for (observer in observers) {
            observer.onEvent(event)
        }
    }

    fun newDeviceConnected(deviceName: String) {
        notifyObservers(NetworkEvent.DeviceConnected(deviceName))
    }

    fun deviceDisconnected(deviceName: String) {
        notifyObservers(NetworkEvent.DeviceDisconnected(deviceName))
    }

    // ... (Other event triggers)
}

sealed class NetworkEvent {
    data class DeviceConnected(val deviceName: String) : NetworkEvent()
    data class DeviceDisconnected(val deviceName: String) : NetworkEvent()
    // ... (Other event types)
}

class AdminDashboard(private val networkMonitor: NetworkSubject) : NetworkObserver {
    init {
        networkMonitor.registerObserver(this)
    }

    override fun onEvent(event: NetworkEvent) {
        when (event) {
            is NetworkEvent.DeviceConnected -> println("Dashboard: New device connected: ${event.deviceName}")
            is NetworkEvent.DeviceDisconnected -> println("Dashboard: Device disconnected: ${event.deviceName}")
            // ... (Handle other event types)
        }
    }
}

// Network Monitor (Subject)
class NetworkMonitor2 {
    private val eventSubject: PublishSubject<NetworkEvent> = PublishSubject.create()

    // Methods to trigger events
    fun newDeviceConnected(deviceName: String) {
        eventSubject.onNext(NetworkEvent.DeviceConnected(deviceName))
    }

    fun deviceDisconnected(deviceName: String) {
        eventSubject.onNext(NetworkEvent.DeviceDisconnected(deviceName))
    }

    // ... (Other event triggers)

    // Expose the event stream as an Observable
    fun events(): Observable<NetworkEvent> = eventSubject
}
// Observers
class AdminDashboard2(private val networkMonitor: NetworkMonitor2) {
    init {
        networkMonitor.events()
            .subscribe {
                when (it) {
                    is NetworkEvent.DeviceConnected -> println("Dashboard rx: New device connected: ${it.deviceName}")
                    is NetworkEvent.DeviceDisconnected -> println("Dashboard rx: Device disconnected: ${it.deviceName}")
                    // ... (Handle other event types)
                }
            }
    }
}