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
    def peso_ideal(peso):
        return json.dumps({'peso ideal': peso})

    request = json.loads(data)
    altura = request.get('altura')
    genero = request.get('genero')

    if genero == 'Masculino':
        return peso_ideal((62.1 * altura) - 44.7)
    elif genero == 'Feminino':
        return peso_ideal((72.7 * altura) - 58)

server = BaseServer(3009, 1024, handler)
server.start_server()
