#!/usr/bin/env python

# a dictionary for transform would use the below
# to specify which pages are impacted in the specified order
#
# page_number = a page with this specific number is impacted
# ODD - impacts only odd page
# EVEN - impacts even pages
# ALL - impacts all page
ODD = 'odd'
EVEN = 'even'
ALL = 'all'

class TransformSettings(object):
    """
    Used to capture the settings to transform an ayah file through
    scaling and offseting into a set of x,y corrdinates for each line
    of an Ayah
    """

    def __init__(self, scale=1,
                 x_stretch=1, y_stretch=1,
                 x_offset=0, y_offset=0):

        # scale a box
        self.scale = scale

        # stretch a box
        self.x_scale = x_stretch
        self.y_scale = y_stretch

        # move a box
        self.x_offset = x_offset
        self.y_offset = y_offset

