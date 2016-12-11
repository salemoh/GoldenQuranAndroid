#!/usr/bin/env python

import re
from gimpfu import *


def new_ayah_transform(x, y, width, height, page_desc, scale=1, x_offset=0, y_offset=0, x_scale=1, y_scale=1):
    print page_desc
    # Scale all dimensions
    x = x * scale
    y = y * scale
    width = width * scale
    height = height * scale
    # Scale the Y + height
    y = y * y_scale
    height = height * y_scale
    # Scale the X + width
    x = x * x_scale
    width = width * x_scale
    # Move the x,y corrdinates based on the offsets
    x = x + x_offset
    y = y + y_offset
    return [x, y, width, height]


def ayah_bounds(image, coordinates,
                x_offset=0, y_offset=0, scale=1,
                x_offset_even=0, y_offset_even=0, y_scale_even=1, x_scale_even=1,
                x_offset_odd=0, y_offset_odd=0, y_scale_odd=1, x_scale_odd=1,
                height=38):
    gimp.context_push()
    image.undo_group_start()
    # Get the drawable layer of the current image
    # image = gimp.image_list()[0]
    page = int(re.findall(r'\d+', image.name)[0])
    layer = image.active_layer
    for coordinate in coordinates:
        coordinate.append(height)
        ###########################################
        #
        # Even page transformations
        #
        ###########################################
        # If page number is even perform transformations
        if page % 2 == 0:
            coordinate = new_ayah_transform(x=coordinate[0], y=coordinate[1], width=coordinate[2], height=coordinate[3],
                                            page_desc="Even Page",
                                            x_offset=x_offset_even,
                                            y_offset=y_offset_even,
                                            y_scale=y_scale_even,
                                            x_scale=x_scale_even,
                                            scale=scale)
        ###########################################
        #
        # Odd page transformations
        #
        ###########################################
        # If page number is odd perform transformations
        if page % 2 != 0:
            coordinate = new_ayah_transform(x=coordinate[0], y=coordinate[1], width=coordinate[2], height=coordinate[3],
                                            page_desc="Odd Page",
                                            x_offset=x_offset_odd,
                                            y_offset=y_offset_odd,
                                            y_scale=y_scale_odd,
                                            x_scale=x_scale_odd,
                                            scale=scale)
        ###########################################
        #
        # Global transformations
        #
        ###########################################
        # coordinate = new_ayah_transform(x=coordinate[0],y=coordinate[1],width=coordinate[2],height=coordinate[3],
        #                                 page_desc="Global Transform ",
        #                                 y_offset=y_offset,
        #                                 scale=scale)
        # draw the ayah boxes
        # Generate strokes from coordinates to draw lines in gimp
        # Note: x = Coordinate[0], y = coordinate[1], width = coordinate[2],
        # height
        # is fixed for all pages
        strokes = [coordinate[0] + x_offset, coordinate[1],
                   coordinate[0] + coordinate[2] + x_offset, coordinate[1],
                   coordinate[0] + coordinate[2] + x_offset, coordinate[1] + coordinate[3],
                   coordinate[0] + x_offset, coordinate[1] + coordinate[3],
                   coordinate[0] + x_offset, coordinate[1]]
        print "strokes = ", strokes
        print "strokes updated = ", strokes
        pdb.gimp_pencil(layer, len(strokes), strokes)
    image.undo_group_end()
    gimp.context_pop()
    return


# layer = pdb.gimp_image_merge_visible_layers(image, 0)


register(
    "python-fu-ayah-boxes",
    "Ayah Rectangle Boxes",
    "Draws a bock around different ayah's",
    "Mohammad Saleh", "Blackstone eIT", "2016",
    "Ayah boxes",
    "",  # type of image it works on (*, RGB, RGB*, RGBA, GRAY etc...)
    [
        # basic parameters are: (UI_ELEMENT, "variable", "label", Default)
        (PF_IMAGE, "image", "takes current image", None),
        (PF_DRAWABLE, "drawable", "Input layer", None)
        # PF_SLIDER, SPINNER have an extra tuple (min, max, step)
        # PF_RADIO has an extra tuples within a tuple:
        # eg. (("radio_label", "radio_value), ...) for as many radio buttons
        # PF_OPTION has an extra tuple containing options in drop-down list
        # eg. ("opt1", "opt2", ...) for as many options
        # see ui_examples_1.py and ui_examples_2.py for live examples
    ],
    [],
    NAME_OF_MAIN_FUNCTION, menu="")
