from PIL import Image


img = Image.open('river.png')
img = img.resize((128,128), Image.ANTIALIAS)
img.save('river.png')