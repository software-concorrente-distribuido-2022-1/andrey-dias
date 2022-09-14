import json
import socket
from random import random

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


class Card:
    def __init__(self, value, naipe):
        self.value = value
        self.naipe = naipe

    def get_description(self):
        print(self.get_naipe())
        print(self.naipe)
        return f"{self.get_value()} de {self.get_naipe()}"

    def get_value(self):
        if self.value == 1:
            return 'Ás'
        if self.value == 11:
            return 'Valete'
        if self.value == 12:
            return 'Rainha'
        if self.value == 13:
            return 'Rei'
        return self.value

    def get_naipe(self):
        if self.naipe == 1:
            return 'ouros'
        elif self.naipe == 2:
            return 'paus'
        elif self.naipe == 3:
            return 'copas'
        elif self.naipe == 4:
            return 'espadas'


def handler(data):
    print('Entrou aqui')

    card = Card(random.randint(1, 14), random.randint(1, 5))
    desc = card.get_description()
    print(desc)
    return desc

server = BaseServer(3015, 1024, handler)
server.start_server()
