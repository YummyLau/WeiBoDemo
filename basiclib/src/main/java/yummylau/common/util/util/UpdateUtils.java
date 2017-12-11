package yummylau.common.util.util;

/**
 * 升级控制
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public class UpdateUtils {

    private static final String TAG = UpdateUtils.class.getSimpleName();

//    /**
//     * 根据保存的数据判断是否需要去检查更新
//     *
//     * @param context 上下文
//     */
//    public static void checkUpdate(Context context) {
//        String lastShowVersion = AppConfig.getVersionShowUpdate();
//        String lastShowDate = AppConfig.getDateShowUpdate();
////        String lastShowVersion = mAppDataHelper.getString(AppDataHelper.VERSION_SHOW_UPDATE, "");
////        String lastShowDate = mAppDataHelper.getString(AppDataHelper.DATE_SHOW_UPDATE, "");
//        String serverVersionString;
//        serverVersionString = AppConfig.getVersionLushiBox();
////        serverVersionString = mAppDataHelper.getString(AppDataHelper.VERSION_DZ_ANDROID, "");
//
//        String nowDate = TimeUtils.getStringDate();
//
//        LogPrinter.i(TAG, "the version updated last time: " + lastShowVersion);
//        LogPrinter.i(TAG, "the date updated last time: " + lastShowDate);
//        AppConfig.setNeedCheckVersion(false);//设置是否执行版本更新
////        mAppDataHelper.putBoolean(AppDataHelper.NEED_CHECK_VERSION, false);
//
//        //如果未曾显示过提醒框，提醒并保存时间和版本
//        if (lastShowVersion == null || lastShowVersion.isEmpty() || lastShowDate == null || lastShowDate.isEmpty()) {
//            try {
////                mAppDataHelper.putBoolean(AppDataHelper.NEED_CHECK_VERSION, true);
////                mAppDataHelper.putString(AppDataHelper.VERSION_SHOW_UPDATE, serverVersionString);
////                mAppDataHelper.putString(AppDataHelper.DATE_SHOW_UPDATE, nowDate);
//                AppConfig.setNeedCheckVersion(true);
//                AppConfig.setVersionShowUpdate(serverVersionString);
//                AppConfig.setDateShowUpdate(nowDate);
////                Intent mServiceIntent = new Intent(context, DownloadApkService.class);
////                context.startService(mServiceIntent);
//            } catch (Exception e) {
////                mAppDataHelper.putString(AppDataHelper.VERSION_SHOW_UPDATE, serverVersionString);
////                mAppDataHelper.putString(AppDataHelper.DATE_SHOW_UPDATE, nowDate);
//                AppConfig.setVersionShowUpdate(serverVersionString);
//                AppConfig.setDateShowUpdate(nowDate);
//            }
//            return;
//        }
//
//        //如果服务器的版本比上一次提示的版本新，则提醒
//        if (versionIsNew(serverVersionString, lastShowVersion)) {
//            AppConfig.setNeedCheckVersion(true);
//            AppConfig.setVersionShowUpdate(serverVersionString);
//            AppConfig.setDateShowUpdate(nowDate);
////            mAppDataHelper.putBoolean(AppDataHelper.NEED_CHECK_VERSION, true);
////            mAppDataHelper.putString(AppDataHelper.VERSION_SHOW_UPDATE, serverVersionString);
////            mAppDataHelper.putString(AppDataHelper.DATE_SHOW_UPDATE, nowDate);
////            Intent mServiceIntent = new Intent(context, DownloadApkService.class);
////            context.startService(mServiceIntent);
//            return;
//        }
//
//        //如果不选中不再提醒复选框，则每次启动都检查提醒
//        //if (mAppDataHelper.getBoolean(AppDataHelper.NOTICE_UPDATE, true)) {
//        AppConfig.setNeedCheckVersion(true);
//        AppConfig.setVersionShowUpdate(serverVersionString);
//        AppConfig.setDateShowUpdate(nowDate);
//
////            mAppDataHelper.putBoolean(AppDataHelper.NEED_CHECK_VERSION, true);
////            mAppDataHelper.putString(AppDataHelper.VERSION_SHOW_UPDATE, serverVersionString);
////            mAppDataHelper.putString(AppDataHelper.DATE_SHOW_UPDATE, nowDate);
//        // }
//
//    }
//
//    /**
//     * 比较第一个版本是否比第二个新
//     *
//     * @param firstVersion  第一个版本
//     * @param secondVersion 第二个版本
//     * @return 是否较新
//     */
//    public static boolean versionIsNew(String firstVersion, String secondVersion) {
//        return !(firstVersion == null || secondVersion == null) && firstVersion.compareTo(secondVersion) > 0;
//    }
//
//    /**
//     * @param context       上下文
//     * @param manuallyClick 手动点击版本检测而不是自动
//     *                      检查是否更新版本
//     */
//    public static void checkVersion(final Context context, boolean manuallyClick) {
//        //服务器版本是否较新，较新则提示更新
//        if (isNeedUpdate(context)) {
//            String updateTipsString=AppConfig.getUpdateTipsAndroid();
//            updateTipsString = updateTipsString.replaceAll("<br>", "\n");
//            new DialogNormal.Builder(context)
//                    .contentBuild(updateTipsString)
//                    .leftBuild(context.getString(R.string.common_cancel))
//                    .rightBuild(context.getString(R.string.update), new DialogNormal.IAction() {
//                        @Override
//                        public void action(View view) {
//                            // 开启更新服务UpdateService
//                            try {
//                                download(context);
//                            } catch (Exception e) {
//                                LogPrinter.e(TAG, e.toString());
//                                e.printStackTrace();
//                            }
//                        }
//                    })
//                    .build(true);
//        } else {
//            if (manuallyClick) {
//                ToastUtils.showShort(context, "当前已是最新版本，么么哒");
//            }
//        }
//    }
//
//    /**
//     * 比较版本号，是否需要更新
//     *
//     * @param context 上下文
//     * @return 是或否
//     */
//    public static boolean isNeedUpdate(Context context) {
//        String serverVersionString;
////        serverVersionString = mAppDataHelper.getString(AppDataHelper.VERSION_DZ_ANDROID, null);
//        serverVersionString=AppConfig.getVersionLushiBox();
//        String appVersionNameString = getAppVersionName();
//
//        if (serverVersionString == null || appVersionNameString == null) {
//            return false;
//        }
//
//        LogPrinter.i(TAG, "Server Version ：" + serverVersionString);
//        LogPrinter.i(TAG, "Local Version ：" + appVersionNameString);
//
//        return versionIsNew(serverVersionString, appVersionNameString);
//    }
//
//    public static void download(Context context) {
//        ToastUtils.showShort(context, context.getString(R.string.update_downloading));
//        Intent mServiceIntent = new Intent(context, DownloadApkService.class);
//        String downLoadUrlString =  AppConfig.getUpdateApkUrl() ;
//        DownloadItem downloadItem = new DownloadItem(
//                downLoadUrlString,
//                context.getResources().getString(R.string.app_name),
//                context.getPackageName(),
//                context.getPackageName(),
//                DownloadApkService.NOTIFICATION_ID_OF_UPDATE_APP);
//        mServiceIntent.putExtra(DownloadApkService.INTENT_UPDATE_APP, downloadItem);
//        context.startService(mServiceIntent);
//    }
//
//    /**
//     * 获取应用的版本号
//     *
//     * @return 版本号
//     */
//    public static String getAppVersionName() {
//        try {
//            PackageInfo info = yummylau.modulea.App.getInstance().getPackageManager().getPackageInfo(yummylau.modulea.App.getInstance().getPackageName(), 0);
//            return info.versionName;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
