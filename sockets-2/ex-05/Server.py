import json
import socket
from threading import Thread

BACKLOG = 5

class BaseServer:

    def __init__(self, port, max_size, handler):
        self.port = port
        self.max_size = max_size
        self.handler = handler

    def start_server(self):
        host = '0.0.0.0'

        server_socket = socket.socket()
        server_socket.bind((host, self.port))

        print(f"Server started on port {self.port}")

        server_socket.listen(BACKLOG)

        while True:
            connection, address = server_socket.accept()
            t = Thread(target=self.new_client, args=(connection, address))
            t.setDaemon(True)
            t.start()

    def new_client(self, connection, address):
        while True:
            print(f"Incoming request from host {address}")
            data = connection.recv(self.max_size).decode()
            if not data:
                break
            message = self.handler(data)
            connection.send(message.encode())

        connection.close()

def handler(data):

    request = json.loads(data)
    idade = request.get('idade')

    print(idade)

    if 5 <= idade <= 7:
        return json.dumps({'Infantil A': True})
    elif 8 <= idade <= 10:
        return json.dumps({'Infantil B': True})
    elif 11 <= idade <= 13:
        return json.dumps({'Juvenil A': True})
    elif 14 <= idade <= 17:
        return json.dumps({'Juvenil B': True})
    elif idade >= 18:
        return json.dumps({'Adulto': True})

server = BaseServer(3010, 1024, handler)
server.start_server()
