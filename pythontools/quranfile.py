#!/usr/bin/env python

import xml.etree.ElementTree as et
import re
from sqlalchemy.orm import sessionmaker
from sqlalchemy import create_engine
from ayahbox import *


class QuranFile(object):
    """
    Used to enumerate a file with quran ayah data and
    """

    def __init__(self, file_name, db_name=None):

        # The file to be read
        self.file_name = file_name

        # Get the filename
        if db_name is None:
            # Split the file name based on the dot
            file_name_parts = file_name.split('.')

            # Create a db in a db folder with .db extension
            self.db_name = str(file_name_parts[0])+'.db'
        else:
            self.db_name = db_name

        # The db to be used for storage and db engine
        self.engine = create_engine('sqlite:///'+self.db_name, echo=True)
        Session = sessionmaker(bind=self.engine)
        self.session = Session()

        # In memory of pages
        self.pages = dict()

    def initialize(self):

        # Create the meta data for the DB
        Base.metadata.create_all(self.engine)

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
                ayah_box = AyahBox(page_number,
                                   surah,
                                   ayah, line_number,
                                   x, y, height, width)

                # Add to the DB session
                self.session.add(ayah_box)

                # build a dictionary of the verse
                ayah_boxes.append(ayah_box)

            # Commit to the DB
            self.session.commit()

            # Add the page to the dictioary
            self.pages[page_number] = ayah_boxes
            print self.pages[page_number]
