package com.blackstone.goldenquran.ui;


public interface DrawerCloser {
    void close(boolean isDrawerLocked);
    void title(int pos);
    void moveToolbarDown ();
    void moveToolbarUp();
}
