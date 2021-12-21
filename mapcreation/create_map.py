from sys import stdout
from PIL import Image
import numpy as np



def createImageLayoutFromTxt(path):
    meaning = {"o":"river.png",
    "r":"road.png", 
    "p":"plain.png",
     "w":"wood.png",
      "ru":"road_up.png", 
      "s":"sand.png",
      "c":"city.png",
      "t":"pipe.png",
      "d":"pipeup.png"}
    lines = open(path, "r").read().splitlines()
    imageLayout = []
    for i in lines:
        imageLayout.append(list(map(lambda x: meaning.get(x), i.split(" "))))

    return imageLayout




def createRgbMap(imageLayout):
    colors = {"road.png": (0, 0, 0), "plain.png": (40, 167, 69), "wood.png": (255, 0, 0), "river.png": (0, 0, 255), "road_up.png":(0, 0, 0), "sand.png":(254, 25, 138), "city.png":(255, 255, 255), "pipe.png":(255, 255, 22), "pipeup.png":(255, 255, 22)}
    image = []
    for y in imageLayout:
        row = []
        for cell in y:
            row.append(colors.get(cell))
        image.append(row)

    array = np.array(image, dtype=np.uint8)
    img = Image.fromarray(array)
    img.save("rgbMap.png")




def getImageMaxWidth(imageLayout):
    width = 0
    for col in imageLayout[0]:
        width += Image.open(col).size[0]
    return width


def getImageMaxHeight(imageLayout):
    height = 0
    for cell in imageLayout:
        height += Image.open(cell[0]).size[1]
    return height

def createMap(imageLayout):
    new_im = Image.new('RGB', (getImageMaxWidth(imageLayout), getImageMaxHeight(imageLayout)))
    y_offset = 0
    for row in imageLayout:
        x_offset = 0
        for cell in row:
            new_im.paste(Image.open(cell), (x_offset, y_offset))
            x_offset += Image.open(cell).size[0]
        y_offset += Image.open(cell).size[1]
    new_im.save('map.png')


imageLayout = createImageLayoutFromTxt("map.txt")
createRgbMap(imageLayout)
createMap(imageLayout)
