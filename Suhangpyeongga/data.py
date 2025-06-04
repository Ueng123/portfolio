import math
import pygame as py
import asyncio
import time

class VectorError(Exception):
    def __init__(self, msg):
        super().__init__(msg)

class ColorError(Exception):
    def __init__(self, msg):
        super().__init__(msg)

class GameError(Exception):
    def __init__(self, msg):
        super().__init__(msg)

class Vector2:
    def __init__(self, x, y):

        self.x = x
        self.y = y

    def __add__(self, other):

        return Vector2(self.x + other.x, self.y + other.y)

    def __sub__(self, other):

        return Vector2(self.x - other.x, self.y - other.y)

    def __mul__(self, other:float):
        if not type(other) in [int, float]: raise VectorError("숫자 아니면 안됨")

        return Vector2(self.x * other, self.y * other)

    def __str__(self):

        return f"({self.x}, {self.y})"

    def __call__(self):

        return (self.x, self.y)

    def Polar2Plane(self):
        
        return Vector2(self.x * math.cos(self.y),self.x * math.sin(self.y))

    def Plane2Polar(self):
        
        return Vector2(math.sqrt((self.x)**2 + (self.y)**2), math.atan2(self.y, self.x))

class Color:

    def __init__(self, r:int, g:int, b:int):

        if (r>255 or g>255 or b>255): raise ColorError("255 넘으시면")
        if (r < 0 or g < 0 or b < 0): raise ColorError("0 이하 안되셔..")

        self.r = r
        self.g = g
        self.b = b

    def __call__(self):
        return (self.r, self.g, self.b)

class Camera:
    def __init__(self, pos:Vector2):
        self.pos = pos
        self.DISPLAY:py.Surface = None

    def MakeCameraPosition(self, pos:Vector2):
        return Vector2(pos.x - self.x, pos.y - self.y)

class GameObject:

    gameObjects = [
        [],
        [],
        [],
        [],
        [],
        [],
        [],
        [],
        [],
        []
    ]

    def __init__(self, pos:Vector2, size:Vector2, imagePATH:str, layer:int):
        
        if (math.fabs(layer-5)>5): raise GameError("layer value needs in between 0 to 10")

        GameObject.gameObjects[layer].append(self)

        self.isPolar = True
        self.pos = pos # 극좌표계
        self.size = size
        self.rect = py.Rect( *(pos - (size/2))() , size.x, size.y)
        self.image = py.image.load(imagePATH).convert_alpha()
        self.image = py.transform.scale(self.image, self.size)

    def AddPos(self, pos:Vector2):
        self.pos += pos
        self.rect.move(pos)

    def SetPos(self, pos:Vector2):
        self.rect.move(-self.pos + pos - (self.size/2))
        self.pos = pos - (self.size/2)

class Game:

    def __init__(self, camera:Camera, screenSize:Vector2, fps:int = 60):
        self.camera = camera
        self.screenSize=screenSize
        self.isRunning = True
        self.clock = py.time.Clock()

        self.fps = fps
        self.currTime = 0
        self.deltaTime = 0

    def StartMainRoutine(self):

        py.init()
        self.camera.DISPLAY = py.display.set_mode(self.screenSize())

        self.awake()
        self.start()

        self.currTime = time.time()

        while self.isRunning:

            # get DeltaTime
            self.deltaTime = time.time() - self.currTime
            self.currTime = time.time()

            # do MainRoutine(1)
            self.updateBE()

            # get Event
            for event in py.event.get():

                self.onEvent(event)

            # do MainRoutine(2)
            self.updateAE()

            self.rendering()

        self.clock.tick()
        self.end()

    def awake(self): # init
        pass

    def start(self): # init(2)
        pass

    def updateBE(self): # doMainRoutine ( Before Event )
        pass

    def updateAE(self): # doMainRoutine ( After  Event )
        pass

    def rendering(self): # rendering
        
        for layer in GameObject.gameObjects:
            for object in layer:

                self.camera.DISPLAY.blit()

        self.camera.DISPLAY.fill(Color(55, 55, 55)())
        py.display.update()


    def end(self):
        pass

    def onEvent(self, event:py.event.Event):

        if (event.type == py.QUIT): self.isRunning = False



game = Game(Camera(Vector2(0, 0)), Vector2(800, 450))

game.StartMainRoutine()
