color = -124534;

# Components will be in the range of 0..255:
blue = color & 0xff;
green = (color & 0xff00) >> 8;
red = (color & 0xff0000) >> 16;

print((red,green,blue))