import Pyro4
import time

ipAddressServer = "0.0.0.0"

box = Pyro4.core.Proxy('PYRO:Box@' + ipAddressServer + ':9090')
while True:
    time.sleep(2)
    print(box.produce())
    print(box.consume())