from http import client
import json
import socket

class BaseClient:

    def __init__(self, port, message_max_size):
        self.port = port
        self.message_max_size = message_max_size
        self.host = socket.gethostname()
        self.client_socket = socket.socket()
        self.client_socket.connect((self.host, self.port))

    def send_message(self, message):
        self.client_socket.send(message.encode())

        data = self.client_socket.recv(self.message_max_size).decode()
        if not data:
            return 'ValorNulo'
        return data

    def close_connection(self):
        self.client_socket.close()

client = BaseClient(3009, 1024)
response = client.send_message(json.dumps({ 'altura': 1.83, 'genero': 'Masculino'}))
print(response)

response = client.send_message(json.dumps({ 'altura': 1.65, 'genero': 'Feminino'}))
print(response)