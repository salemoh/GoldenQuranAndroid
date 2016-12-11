#!/usr/bin/env python

import xml.etree.ElementTree as et
import re
from ayahbox import *

class QuranFile(object):
    """
    Used to enumerate a file with quran ayah data and
    """

    def __init__(self, file_name):

        # The file to be read
        self.file_name = file_name
        self.pages = dict()

    def intialize(self):

        # Read all pages of the file
        tree = et.parse(self.file_name)
        root = tree.getroot()

        # Iterate and fill the dictionary
        for page in root:

            # init the empty page record
            page_number = int(page.attrib['p'])
            ayah_boxes = []

            for line in page:

                # parse the surah, verse, and line number
                id = line.attrib['id'].split('_')
                surah = int(re.findall(r'\d+', id[0])[0])
                ayah = int(id[1])

                # We always have line_number
                line_number = 1
                if len(id) > 2:
                    line_number = int(id[2])

                x = int(line.attrib['x'])
                y = int(line.attrib['y'])
                height = int(line.attrib['h'])
                width = int(line.attrib['w'])

                # generate an ayah box from the data
                ayah_box = AyahBox(surah, ayah, line_number, x, y, height, width)

                # build a dictionary of the verse
                ayah_boxes.append(ayah_box)
                print 'page: ', page_number, ' surah: ', surah, ' ayah: ', ayah

            # Add the page to the dictioary
            self.pages[page_number] = ayah_boxes
            print self.pages[page_number]
