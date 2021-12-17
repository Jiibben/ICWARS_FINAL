from PIL import Image

asset_to_resize = ""#path to the asset to resisze
img = Image.open(asset_to_resize)
img = img.resize((128,128), Image.ANTIALIAS)
img.save(asset_to_resize)
