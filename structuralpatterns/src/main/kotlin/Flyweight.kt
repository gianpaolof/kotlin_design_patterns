/*
Core Idea

    The Flyweight pattern aims to optimize memory usage by sharing common parts of state between multiple objects instead of storing all the data in each object.
    It achieves this by separating the intrinsic state (shared, unchanging data) from the extrinsic state (context-dependent, variable data).
    The intrinsic state is stored in Flyweight objects, which are shared among multiple Context objects. The Context objects hold the extrinsic state and provide a way to interact with the Flyweight objects.

When to Use

    You have a large number of similar objects that consume a significant amount of memory.
    Most of the object's state can be made extrinsic (i.e., passed in from the outside).
    The application doesn't depend on object identity.


    Illustrative Scenario: Network Packet Processing

In network communication, a massive number of packets are constantly transmitted and received.
Each packet contains various information, such as:

    Source IP address
    Destination IP address
    Protocol (TCP, UDP, etc.)
    Payload (data being transmitted)

While the payload is unique to each packet, the source and destination IP addresses,
 along with the protocol, might be shared among multiple packets, especially in scenarios like bulk data transfers or streaming.
 */

interface INetworkPacket {
    fun process(payload: ByteArray)
}

data class NetworkPacketFlyweight(val sourceIP: String, val destinationIP: String, val protocol: String) : INetworkPacket {
    override fun process(payload: ByteArray) {
        println("Processing packet: Source IP: $sourceIP, Destination IP: $destinationIP, Protocol: $protocol, Payload size: ${payload.size}")
        // ... (Actual packet processing logic)
    }
}

object NetworkPacketFactory {
    private val packets = mutableMapOf<String, NetworkPacketFlyweight>()

    fun getPacket(sourceIP: String, destinationIP: String, protocol: String): INetworkPacket {
        val key = "$sourceIP-$destinationIP-$protocol"
        return packets.getOrPut(key) { NetworkPacketFlyweight(sourceIP, destinationIP, protocol) }
    }
}