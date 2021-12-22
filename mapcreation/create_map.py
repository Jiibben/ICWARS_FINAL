from sys import stdout
from PIL import Image
import numpy as np
import json
"""
super quick guide just change the map.txt according to the letters in the meaning json file (each letter corresponds to a cell)
 map must be in a  shape of a square or rectanlge (only full form are accepted otherwise error)
then save the map.txt and run create_map.py

check the output folder rgbMap.png is the behavior
and map.png is the background

They are ready to be used in ICWars :) just create the corresponding class Level

sorry for not putting variable for path -_-
"""

def createImageLayoutFromTxt(path):
    meaning = json.load(open("meaning.json", "r"))
    lines = open(path, "r").read().splitlines()
    imageLayout = []
    for i in lines:
        imageLayout.append(list(map(lambda x: meaning.get(x), i.split(" "))))

    return imageLayout




def createRgbMap(imageLayout):
    try:
        colors = {"road.png": (0, 0, 0), "plain.png": (40, 167, 69), "wood.png": (255, 0, 0), "river.png": (0, 0, 255), "road_up.png":(0, 0, 0), "sand.png":(254, 25, 138), "city.png":(255, 255, 255), "pipe.png":(255, 255, 22), "pipeup.png":(255, 255, 22)}
        image = []
        for y in imageLayout:
            row = []
            for cell in y:
                row.append(colors.get(cell))
            image.append(row)

        array = np.array(image, dtype=np.uint8)
        img = Image.fromarray(array)
        img.save("output/rgbMap.png")
    except Exception:
        print("Error image isn't square or rectangle (check if a letter is missing)")




def getImageMaxWidth(imageLayout):
    width = 0
    for col in imageLayout[0]:
        width += Image.open("assets/"+col).size[0]
    return width


def getImageMaxHeight(imageLayout):
    height = 0
    for cell in imageLayout:
        height += Image.open("assets/"+cell[0]).size[1]
    return height

def createMap(imageLayout):
    new_im = Image.new('RGB', (getImageMaxWidth(imageLayout), getImageMaxHeight(imageLayout)))
    y_offset = 0
    for row in imageLayout:
        x_offset = 0
        for cell in row:
            new_im.paste(Image.open("assets/"+cell), (x_offset, y_offset))
            x_offset += Image.open("assets/"+cell).size[0]
        y_offset += Image.open("assets/"+cell).size[1]
    new_im.save('output/map.png')


imageLayout = createImageLayoutFromTxt("map.txt")
createRgbMap(imageLayout)
createMap(imageLayout)
