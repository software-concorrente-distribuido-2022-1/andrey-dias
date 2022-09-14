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
    def response(salario_liquido, nome, nivel):
        return json.dumps({'salario_liquido': salario_liquido, 'nome': nome, 'nivel': nivel})

    request = json.loads(data)
    nome = request.get('nome')
    nivel = request.get('nivel')
    dependentes = request.get('dependentes')
    salario = request.get('salario')

    fee = 0
    if nivel == 'A':
        fee = 0.08 if dependentes > 0 else 0.03
    if nivel == 'B':
        fee = 0.10 if dependentes > 0 else 0.05
    if nivel == 'C':
        fee = 0.15 if dependentes > 0 else 0.08
    if nivel == 'D':
        fee = 0.17 if dependentes > 0 else 0.10

    return response(salario - (salario * fee), nome, nivel)

server = BaseServer(3011, 1024, handler)
server.start_server()
