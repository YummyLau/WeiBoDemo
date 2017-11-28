package yummylau.common.util.util;

/**
 * 渠道相关工具处理
 * Created by yummyLau on 17-4-30
 * Email: yummyl.lau@gmail.com
 */

public class ChannelUtils {

    public static final String CHANNEL_GOOGLEPLAY = "gstore";

//    public static String getChannels(Context context) {
//        // TODO: 17-4-30 存在ｓｈａｒｅ ｐ
//        String channels = AppDataHelper.getInstance(context).getString(AppDataHelper.CHANNEL_AND_GAMEINFOS, "");
//        return channels;
//    }

//    public static boolean isGoogleplayChannel(Context context) {
//        String channels = AppDataHelper.getInstance(context).getString(AppDataHelper.CHANNEL_AND_GAMEINFOS, "");
//        if(null != channels) {
//            String[] infos = channels.split("_");
//            if (infos.length >= 4) {
//                return infos[3].equals(CHANNEL_GOOGLEPLAY);
//            }
//        }
//        return false;
//    }


    /**
     * 获取渠道信息
     * 先读去当前版本号，与上一版本号进行对比，若版本号一样，读取保存在SharePreference中的版本及渠道信息
     * 若版本号不一样说明刚安装或是升级安装，则从zip中读取新的版本渠道信息，更新SharePreference中的对应字段
     * @return
     */
//     String getChannelAndGameInfos() {
//        int curVersionCode = Commons.getAppVersionCode(this);
//        int lastVersionCode = mAppDataHelper.getInt(AppDataHelper.LAST_VERSION_CODE, 0);
//        String channelAndGameInfos = mAppDataHelper.getString(AppDataHelper.CHANNEL_AND_GAMEINFOS, null);
//        if (curVersionCode == lastVersionCode && (channelAndGameInfos != null && channelAndGameInfos.length() > 0)) {
//            return channelAndGameInfos;
//        }
//        else {
//            final String preFlag = "META-INF/nefchannelandinfo";
//            ApplicationInfo appinfo = this.getApplicationInfo();
//            String sourceDir = appinfo.sourceDir;
//            String ret = "";
//            ZipFile zipfile = null;
//            try {
//                zipfile = new ZipFile(sourceDir);
//                Enumeration<?> entries = zipfile.entries();
//                while (entries.hasMoreElements()) {
//                    ZipEntry entry = (ZipEntry) entries.nextElement();
//                    String entryName = entry.getName();
//                    if (entryName.startsWith(preFlag)) {
//                        ret = entryName;
//                        break;
//                    }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (zipfile != null) {
//                    try {
//                        zipfile.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            String[] split = ret.split("_");
//            if (split.length >= 5) {
//                channelAndGameInfos = ret.substring(split[0].length() + 1);
//                mAppDataHelper.putString(AppDataHelper.CHANNEL_AND_GAMEINFOS, channelAndGameInfos);
//            }
//            mAppDataHelper.putInt(AppDataHelper.LAST_VERSION_CODE, curVersionCode);
//        }
//        return channelAndGameInfos;
//    }

//    /**
//     * 获取渠道信息
//     * 先读去当前版本号，与上一版本号进行对比，若版本号一样，读取保存在SharePreference中的版本及渠道信息
//     * 若版本号不一样说明刚安装或是升级安装，则从zip中读取新的版本渠道信息，更新SharePreference中的对应字段
//     * 版本信息记录在zip文件的comment字段里面
//     * @return
//     */
//    public static String getChannelAndGameInfos2(Context context) {
//        AppDataHelper appDataHelper = AppDataHelper.getInstance(context);
//        int curVersionCode = Commons.getAppVersionCode(context);
//        int lastVersionCode = appDataHelper.getInt(AppDataHelper.LAST_VERSION_CODE, 0);
//        String channelAndGameInfos = appDataHelper.getString(AppDataHelper.CHANNEL_AND_GAMEINFOS, null);
//        if (curVersionCode == lastVersionCode && (channelAndGameInfos != null && channelAndGameInfos.length() > 0)) {
//            return channelAndGameInfos;
//        }
//        else {
//            ApplicationInfo appinfo = context.getApplicationInfo();
//            String sourceDir = appinfo.sourceDir;
//            String ret = "";
//            byte[] bytes;
//            File file = new File(sourceDir);
//            try {
//                RandomAccessFile accessFile = new RandomAccessFile(file, "r");
//                long index = accessFile.length();
//                bytes = new byte[2];
//                index = index - bytes.length;
//                accessFile.seek(index);
//                accessFile.readFully(bytes);
//
//                int contentLength = bytesToShort(bytes);
//
//                bytes = new byte[contentLength];
//                index = index - bytes.length;
//                accessFile.seek(index);
//                accessFile.readFully(bytes);
//
//                ret = new String(bytes, "utf-8");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            String[] split = ret.split("_");
//            if (split.length >= 5) {
//                channelAndGameInfos = ret.substring(split[0].length() + 1);
//                appDataHelper.putString(AppDataHelper.CHANNEL_AND_GAMEINFOS, channelAndGameInfos);
//            }
//            appDataHelper.putInt(AppDataHelper.LAST_VERSION_CODE, curVersionCode);
//        }
//        return channelAndGameInfos;
//    }

    private static short bytesToShort(byte[] bytes) {
        return (short) ((bytes[1] << 8) | (bytes[0] & 0xff));
    }
}
