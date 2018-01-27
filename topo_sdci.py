"""Topologie Partie 2

Ce fichier permet de creer une topologie contenant de 3 hotes et un switch : 
"""

from mininet.topo import Topo

class MyTopo( Topo ):

    def __init__( self ):
        "Create custom topo."

        # Initialize topology
        Topo.__init__( self )

	
        # Add (hosts or dockers) and switches
        zone1 = self.addDocker('Zone1', ip='10.0.0.1', dimage="zone1")
        zone2 = self.addDocker('Zone2', ip='10.0.0.2', dimage="zone2")
        zone3 = self.addDocker('Zone3', ip='10.0.0.3', dimage="zone3")
        gi = self.addDocker('GI', ip='10.0.0.4', dimage="gi")
        server = self.addDocker('Server', ip='10.0.0.5', dimage="server")
	switch1 = self.addSwitch( 'Switch1' )
	switch2 = self.addSwitch( 'Switch2' )

        # Add links
        self.addLink( server, switch1 )
        self.addLink( switch1, gi )
        self.addLink( switch1, switch2 )
        self.addLink( switch2, zone1 )
        self.addLink( switch2, zone2 )
        self.addLink( switch2, zone3 )

topos = { 'mytopo': ( lambda: MyTopo() ) }
