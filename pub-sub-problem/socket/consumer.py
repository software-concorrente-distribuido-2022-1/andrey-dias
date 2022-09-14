import json
import time
from base_client import BaseClient

consumer = BaseClient(3000, 1024)

while True:
    time.sleep(1)
    response = consumer.send_message(json.dumps({ 'event_type': 'CONSUME' }))
    print(response)