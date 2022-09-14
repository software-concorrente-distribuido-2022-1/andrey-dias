import functools
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
    n1 = request.get('n1')
    n2 = request.get('n2')
    n3 = request.get('n3')


    def medianormal(nota1, nota2):
        return (nota1 + nota2 )/ 2

    def mediatresnotas(nota1, nota2, nota3):
        return (nota1 + nota2 + nota3)/ 3

    media = medianormal(n1, n2)
    if media >= 7.0:
        return json.dumps({'Aprovado!': True})
    elif 3.0 < media < 7.0:
        nova_media = mediatresnotas(n1, n2, n3)
        if nova_media >= 5.0:
            return json.dumps({'Aprovado!': True})
        return json.dumps({'Reprovado!': True})
    else:
        return json.dumps({'Reprovado!': True})

server = BaseServer(3008, 1024, handler)
server.start_server()
