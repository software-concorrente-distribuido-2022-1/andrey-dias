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
            return 'Null'
        return data

    def close_connection(self):
        self.client_socket.close()

client = BaseClient(3002, 1024)
response = client.send_message(json.dumps({ 'Idade': 30, 'Nome': 'Carlos', 'Genero': 'Masculino'}))
print(response)

response = client.send_message(json.dumps({ 'Idade': 16, 'Nome': 'Gabriela', 'Genero': 'Feminino'}))
print(response)

response = client.send_message(json.dumps({ 'Idade': 14, 'Nome': 'Jacson', 'Genero': 'Masculino'}))
print(response)

response = client.send_message(json.dumps({ 'Idade': 21, 'Nome': 'Amanda', 'Genero': 'Feminino'}))
print(response)

response = client.send_message(json.dumps({ 'Idade': 18, 'Nome': 'Gabriel', 'Genero': 'Masculino'}))
print(response)