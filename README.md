# General Controller

This project is a controller which purpose is to be on top of the Floodlight SDN controller to control simulated IOT objects in a OM2M network.


# Topology

The topology found in topo_sdci.py contains a python script that can be launched with this mininet command :
`sudo mn --custom topo_sdci.py --topo=mytopo --controller=remote,ip=10.0.2.15,port=2253 --mac`


