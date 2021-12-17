from sys import stdout
from PIL import Image
import numpy as np
colors = {"road.png":(0, 0, 0),"plain.png":(40, 167, 69),"wood.png":(255, 0, 0),"river.png":(0, 0, 255)}
halfsized = ["road.png", "river.png"]

width = 60
#put twice more halfsized
river_line = ["river.png" for i in range(0,width)]
plain_line = ["plain.png" for i in range(0,width)]
wood_line = ["wood.png" for i in range(0,width)]
road_line = ["road.png" for i in range(0,width)]
imageLayout = [ wood_line, wood_line,wood_line, river_line, plain_line, plain_line, plain_line, wood_line,road_line, plain_line, plain_line, river_line, wood_line,river_line,  wood_line]

def getRgbMap(imageLayout):
    image =[]
    for y in imageLayout:
        row = []
        for cell in y:
            # if cell not in halfsized:
            #     row.append(colors.get(cell))
            #     row.append(colors.get(cell))
            # else:
            row.append(colors.get(cell))
        image.append(row)


    
    array = np.array(image, dtype=np.uint8)
    img = Image.fromarray(array)
    img.save("rgbMap.png")


getRgbMap(imageLayout)

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


new_im = Image.new('RGB', (getImageMaxWidth(imageLayout), getImageMaxHeight(imageLayout)))
y_offset = 0
for row in imageLayout:
    x_offset = 0
    for cell in row:
        new_im.paste(Image.open(cell), (x_offset,y_offset))
        x_offset += Image.open(cell).size[0]
    y_offset += Image.open(cell).size[1]

new_im.save('map.png')

# total_width = sum(widths)
# max_height = sum(heights)

# new_im = Image.new('RGB', (total_width, max_height))
# for im in images:
#   new_im.paste(im, (x_offset,0))
#   x_offset += im.size[0]

# new_im.save('test.jpg')




# import sys
# from PIL import Image

# images = [Image.open(x) for x in ['Test1.jpg', 'Test2.jpg', 'Test3.jpg']]
# widths, heights = zip(*(i.size for i in images))

# total_width = sum(widths)
# max_height = max(heights)

# new_im = Image.new('RGB', (total_width, max_height))

# x_offset = 0
# for im in images:
#   new_im.paste(im, (x_offset,0))
#   x_offset += im.size[0]

# new_im.save('test.jpg')