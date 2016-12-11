#!/usr/bin/env python

class AyahBox(object):
    """
    Represents a box around an ayah line
    """


    def __init__(self, surah, ayah, line, x, y, height, width):

        self.x = x
        self.y = y
        self.height = height
        self.width = width
        self.surah = surah
        self.ayah = ayah
        self.line = line

        self.update_corners()

    def scale(self, scale = 1):
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

    def stretch(self, x_scale = 1, y_scale =1):
        """
        Stretches the width or height of the box
        :param x_scale:
        :param y_scale:
        :return:
        """
        self.width *= x_scale
        self.height *= y_scale

        # Always update the corners after operation
        self.update_corners()
        return

    def move(self, x_offset = 0, y_offset = 0):
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
        self.upper_left = [self.x,self.y]
        self.upper_right = [self.x + self.width, self.y]
        self.lower_right = [self.x + self.width, self.y + self.height]
        self.lower_left = [self.x, self.y + self.height]

        return
