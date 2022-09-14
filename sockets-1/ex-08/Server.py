import json
import socket

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
            self.new_client(connection, address)

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
    def response(taxa, valor):
        return json.dumps({'taxa': taxa, 'valor_credito': valor * taxa})

    request = json.loads(data)
    balanco_medio = request.get('balanco_medio')

    if 0 <= balanco_medio <= 200:
        return response(0.0, balanco_medio)
    if 201 <= balanco_medio <= 400:
        return response(0.2, balanco_medio)
    if 401 <= balanco_medio <= 600:
        return response(0.3, balanco_medio)
    if balanco_medio > 600:
        return response(0.4, balanco_medio)

server = BaseServer(3014, 1024, handler)
server.start_server()
