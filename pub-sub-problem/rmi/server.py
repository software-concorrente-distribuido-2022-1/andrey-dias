import Pyro4

@Pyro4.expose
class Box:
    def __init__(self):
        self.items = 0
        self.max_items = 10
        self.can_produce = True
        self.can_consume = False
        
    def produce(self):
        if not self.can_produce:
            return str(self.items) 
        if self.items < self.max_items:
            self.can_consume = True
            self.items += 1
            print(f"Adding a new item on box. Total: {self.items}")
        else:
            self.can_produce = False
            print(f"Box is full. Cannot add new item: {self.items}")
        return str(self.items)
    
    def consume(self):
        if not self.can_consume:
            return str(self.items)
        if self.items > 0:
            self.items -= 1
            self.can_produce = True
            print(f"Removing an item from box: Remaing: {self.items}")
        else:
            self.can_consume = False
            print("Empty box")
        return str(self.items)

Pyro4.Daemon.serveSimple({
    Box: 'Box',
}, host="0.0.0.0", port=9090, ns=False, verbose=True) 