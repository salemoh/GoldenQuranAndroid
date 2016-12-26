#!/usr/bin/env python

from sqlalchemy import Column, Date, Integer, String
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()

class AyahBox(Base):
    """
    Represents a box around an ayah line, in a surah
    in a page
    """

    __tablename__ = "page"

    x = Column(Integer)
    y = Column(Integer)
    upper_left = Column(Integer)
    upper_right = Column(Integer)
    lower_right = Column(Integer)
    lower_left = Column(Integer)

    ayah = Column(Integer)
    line = Column(Integer)

    surah = Column(Integer)

    page_number = Column(Integer)
    id = Column(Integer, primary_key=True)

    def __init__(self, page_number,
                 surah,
                 ayah, line,
                 x, y, height, width):
        # The box related fields
        self.x = x
        self.y = y
        self.height = height
        self.width = width

        # The ayah related fields
        self.ayah = ayah
        self.line = line

        # The surah related fields
        self.surah = surah

        # The page related fields
        self.page_number = page_number

        self.update_corners()

    def scale(self, scale=1):
        """
        Scales an ayah box
        :param scale: scaling factor
        :return: none
        """
        self.x *= scale
        self.y *= scale
        self.width *= scale
        self.height *= scale

        # Always update the corners after operation
        self.update_corners()
        return

    def stretch(self, x_stretch=1, y_stretch=1):
        """
        Stretches the width or height of the box
        :param x_stretch:
        :param y_stretch:
        :return:
        """
        self.width *= x_stretch
        self.height *= y_stretch

        # Always update the corners after operation
        self.update_corners()
        return

    def move(self, x_offset=0, y_offset=0):
        """
        Move the box by x,y offsets
        :param x_offset:
        :param y_offset:
        :return:
        """

        # move the box
        self.x += x_offset
        self.y += y_offset

        # Always update the corners after operation
        self.update_corners()
        return

    def update_corners(self):
        """
        Updates corners of an ayahbox
        :return:
        """
        self.upper_left = [self.x, self.y]
        self.upper_right = [self.x + self.width, self.y]
        self.lower_right = [self.x + self.width, self.y + self.height]
        self.lower_left = [self.x, self.y + self.height]

        return
