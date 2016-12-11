#!/usr/bin/env python

import xml.etree.ElementTree as et
from quranfile import *

class AyahTransform(object):
    """
    A class to transform an XML file of x,y,width,height into a coordinates for
    Ayah's in a Quran image and write to a DB
    """

    def __init__(self, db_file_name = None, quran_data_file_name = None, transform_dict = None):

        # the DB file name
        self.db_file_name = db_file_name
        self.quran_data_file_name = quran_data_file_name
        self.transform_dict = transform_dict

    def parse_quran_data(self):

        # Open the xml file
        self.quran_file = QuranFile(self.quran_data_file_name)
        self.quran_file.intialize()

if __name__ == '__main__':
    ayah_transform = AyahTransform(quran_data_file_name='C:\Users\salemoh\PycharmProjects\quranpages\Medina1.xml')
    ayah_transform.parse_quran_data()