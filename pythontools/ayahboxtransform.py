#!/usr/bin/env python

from ayahbox import AyahBox
from operator import methodcaller

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


class AyahBoxTransform(object):
    """
    Used to capture the settings to transform an ayah file through
    scaling and offseting into a set of x,y corrdinates for each line
    of an Ayah
    """

    def __init__(self, transform_dict):

        # Set default list
        self.transforms_odd = []
        self.transforms_even = []

        # Capture the ALL transforms if any and include it both ODD and EVEN
        self.transforms_odd.extend(transform_dict.get(ALL, []))
        self.transforms_even.extend(transform_dict.get(ALL, []))

        # Extend any extra odd/even transforms
        self.transforms_odd.extend(transform_dict.get(ODD, []))
        self.transforms_even.extend(transform_dict.get(EVEN, []))

    def apply(self, ayahbox):

        # Check the page number (these all apply the ALL transforms)
        if ayahbox.page_number % 2 == 0:
            # Apply EVEN transform
            for even_transform in self.transforms_even:
                even_transform(ayahbox)

        # Apply the ODD transform
        else:
            for odd_transform in self.transforms_odd:
                odd_transform(ayahbox)

    @staticmethod
    def Scale(scale=1):
        return methodcaller('scale', scale=scale)

    @staticmethod
    def Stretch(x_stretch=1, y_stretch=1):
        return methodcaller('stretch', x_stretch=x_stretch, y_stretch=y_stretch)

    @staticmethod
    def Move(x_offset=1, y_offset=1):
        return methodcaller('move', x_offset=x_offset, y_offset=y_offset)
