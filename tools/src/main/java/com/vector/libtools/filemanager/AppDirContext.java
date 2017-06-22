package com.vector.libtools.filemanager;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AppDirContext extends DirectoryContext {

    public AppDirContext(String appName) {
        super(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + appName);
    }

    @Override
    protected List<Directory> initDirectories() {
        ArrayList<Directory> children = new ArrayList<Directory>();

        // addChild(children, DirType.LYRICS, "lyrics");
        addChild(children, DirType.CACHE, "cache");
        addChild(children, DirType.DOWNLOAD, "download");
        addChild(children, DirType.LOG, "log");
        // addChild(children, DirType.MUSIC, "music");
        addChild(children, DirType.PICTURE, "picture");
        addChild(children, DirType.TEMP, "temp");
        // addChild(children, DirType.SEARCH, "search");
        // addChild(children, DirType.UPDATE, "update");
        // addChild(children, DirType.SKIN, "skins");
        // addChild(children, DirType.CODEC, "codecs");
        // addChild(children, DirType.PREFETCH, "prefetch");
        addChild(children, DirType.WELCOME, "welcome");
        addChild(children, DirType.CRASH, "crash");
        addChild(children, DirType.CONFIG, "config");
        // addChild(children, DirType.GAME, "game");

        // Directory recordDirectory = addChild(children, DirType.RECORD,"recorder");
        // recordDirectory.addChild(DirType.TEXT, "text");
        // recordDirectory.addChild(DirType.PHOTO, "photo");
        // recordDirectory.addChild(DirType.AUDIO, "audio");
        // recordDirectory.addChild(DirType.VEDIO, "vedio");

        return children;
    }

    private Directory addChild(ArrayList<Directory> children, DirType type, String name) {
        Directory child = new Directory();
        child.setType(type);
        child.setName(name);
        children.add(child);
        return child;
    }
}
