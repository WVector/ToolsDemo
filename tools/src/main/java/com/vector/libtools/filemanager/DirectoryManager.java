package com.vector.libtools.filemanager;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class DirectoryManager {
    private final static String TAG = DirectoryManager.class.getSimpleName();

    private DirectoryContext mContext;
    private ArrayList<DirMap> mDirs;

    public DirectoryManager(DirectoryContext context) {
        mContext = context;
        mDirs = new ArrayList<DirMap>();
    }

    public File getDir(DirType type) {
        for (DirMap map : mDirs) {
            if (map.mType.equals(type)) {
                return map.mFile;
            }
        }
        return null;
    }

    public String getDirPath(DirType type) {
        File file = getDir(type);
        if (file != null) {
            return file.getAbsolutePath();
        }
        return null;
    }

    public boolean createAll() {
        Directory directory = mContext.getBaseDirectory();
        return createDirectory(directory);
    }

    private boolean createDirectory(Directory directory) {
        boolean ret = true;
        String path = null;
        Directory parent = directory.getParent();
        // 这是根目录
        if (parent == null) {
            path = directory.getName();
        } else {
            File file = getDir(parent.getType());
            path = file.getAbsolutePath() + File.separator
                    + directory.getName();
        }

        // 先检测当前目前是否存在
        File file = new File(path);
        if (!file.exists()) {
            ret = file.mkdirs();
        }

        if (!ret) {
            return false;
        }

        mDirs.add(new DirMap(directory.getType(), file));
        // 再检测各子目录是否存在
        Collection<Directory> children = directory.getChildren();
        if (children != null) {
            for (Directory dir : children) {
                if (!createDirectory(dir)) {
                    return false;
                }
            }
        }

        return ret;
    }
}

class DirMap {
    public DirType mType;
    public File mFile;

    public DirMap(DirType type, File file) {
        this.mType = type;
        this.mFile = file;
    }
}
