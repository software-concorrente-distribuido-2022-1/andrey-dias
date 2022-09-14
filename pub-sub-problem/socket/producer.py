import json
import time
from base_client import BaseClient

producer = BaseClient(3000, 1024)

while True:
    time.sleep(2)
    response = producer.send_message(json.dumps({ 'event_type': 'PRODUCE' }))
    print(response)