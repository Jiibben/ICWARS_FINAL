
from PIL import Image

#read the image

asset_to_rotate = "road.png"
angle_to_rotate = 90
im = Image.open(asset_to_rotate)

#rotate image

out = im.rotate(angle_to_rotate)
out.save('road-up.png')