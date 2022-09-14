import xmlrpc.client
import time

while True:
    time.sleep(1)
    with xmlrpc.client.ServerProxy("http://localhost:8000/") as proxy:
        message = proxy.produce()
        print(message)