package com.example.yummylau.rapiddvpt.helper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yummyLau on 17-6-11
 * Email: yummyl.lau@gmail.com
 */

public class ImageSelectHelper {

    public static final int DEFAULT_MAX_COUNT = 10;
    public static boolean sOriginalImage = false;

    private static ArrayList<String> sSelectedImageList = new ArrayList<>();                          // 用户选择的图片，存储为图片的完整路径

    private static ArrayList<String> sReadySelectedImageList = new ArrayList<>();                     //预备选择图片list，点击完成后才add到sSelectedImageList

    public static ArrayList<String> getSelectedImageList() {
        return sSelectedImageList;
    }

    public static ArrayList<String> getReadySelectedImageList() {
        return sReadySelectedImageList;
    }


    /**
     * 滤除无效的没有保存到指定目录的图片地址
     * @return
     */
//    public static ArrayList<String> getSelectedImageListWithCheckInvalid(){
//        //检查照片是否已经保存到了指定目录
//        //地址没有该图片的，删除列表中的地址
//        List<String> inexistentImageList = new ArrayList<>();
//        for (String imagePath : sSelectedImageList) {
//            File imageFile = new File(imagePath);
//            if (!imageFile.exists()) {
//                inexistentImageList.add(imagePath);
//            }
//        }
//        for (String inexistentImagePath : inexistentImageList) {
//            sSelectedImageList.remove(inexistentImagePath);
//        }
//        return sSelectedImageList;
//
//    }

    /**
     * 滤除无效的没有保存到指定目录的图片地址
     *
     * @return
     */
    public static void filterSelectedImageListInvalid() {
        //检查照片是否已经保存到了指定目录
        //地址没有该图片的，删除列表中的地址
        List<String> inexistentImageList = new ArrayList<>();
        for (String imagePath : sSelectedImageList) {
            File imageFile = new File(imagePath);
            if (!imageFile.exists()) {
                inexistentImageList.add(imagePath);
            }
        }
        for (String inexistentImagePath : inexistentImageList) {
            sSelectedImageList.remove(inexistentImagePath);
        }
    }

    //    public static int getSelectedCount () {
//        return sSelectedImageList.size();
//    }
    public static int getSelectedCount() {
        return sSelectedImageList.size();
    }

    public static int getReadySelectedCount() {
        return sReadySelectedImageList.size();
    }

    public static boolean isSelected() {
        return sSelectedImageList.size() > 0;
    }

//    public static String get(int index) {
//        if (index < 0 || index >= getSelectedCount()) return null;
//        return sSelectedImageList.get(index);
//    }

    public static void add(String item) {
        sSelectedImageList.add(item);
    }

    public static void addReadySelected(String item) {
        sReadySelectedImageList.add(item);
    }

    public static void addSelectedList() {
        sSelectedImageList.addAll(sReadySelectedImageList);
    }

    public static void clearReadySelectedList() {
        sReadySelectedImageList.clear();
    }


    public static void remove(String item) {
        sSelectedImageList.remove(item);
    }

    public static void removeReadySelected(String item) {
        sReadySelectedImageList.remove(item);
    }

    public static boolean contains(String item) {
        return sReadySelectedImageList.contains(item);
    }

    public static void clear() {
        sSelectedImageList.clear();
    }
}
