#!/usr/bin/env python

from quranfile import *

class AyahTransform(object):
    """
    A class to transform an XML file of x,y,width,height into a coordinates for
    Ayah's in a Quran image and write to a DB
    """

    def __init__(self, db_file_name=None, quran_data_file_name=None, transform_dict=None):

        # the DB file name
        self.db_file_name = db_file_name
        self.quran_data_file_name = quran_data_file_name

        # Medina1 transforms ayah_bounds(image,coordinates,x_offset_odd=-70,y_offset_odd=-57,scale=1.2)
        self.transform_dict = transform_dict

    def parse_quran_data(self):

        # Open the xml file
        self.quran_file = QuranFile(file_name= self.quran_data_file_name,
                                    db_name=self.db_file_name)
        self.quran_file.initialize()

if __name__ == '__main__':

    # create an ayah transform page object
    ayah_transform = AyahTransform(quran_data_file_name='data/Medina1.xml', db_file_name="db/Medina1.db")

    # perform the parsing and storing in DB
    ayah_transform.parse_quran_data()

