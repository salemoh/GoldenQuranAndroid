#!/usr/bin/env python

class TransformSettings(object):
    """
    Used to capture the settings to transform an ayah file through
    scaling and offseting into a set of x,y corrdinates for each line
    of an Ayah
    """

    def __init__(self, scale=1, x_offset=0, y_offset=0):

        # The transform values
        self.scale = scale
        self.x_offset = x_offset
        self.y_offset = y_offset

