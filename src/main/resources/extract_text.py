import pytesseract
import os
from PIL import Image

a = os.path.dirname(os.path.abspath(__file__))
print (a)
print(pytesseract.image_to_string(Image.open(a + '/texto.jpg')))
