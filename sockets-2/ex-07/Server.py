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
    def response(aposentar):
        return json.dumps({ 'aposentar': aposentar })

    request = json.loads(data)
    anos_trabalhados = request.get('anos_trabalhados')
    idade = request.get('idade')

    if (idade >= 65) and (anos_trabalhados >= 30) and (idade >= 60) and (anos_trabalhados >= 25):
        return response(True)
    else:
        return response(False)

server = BaseServer(3013, 1024, handler)
server.start_server()
