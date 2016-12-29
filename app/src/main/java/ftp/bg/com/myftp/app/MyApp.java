package ftp.bg.com.myftp.app;

import android.app.Application;


import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import ftp.bg.com.myftp.R;

/**
 * Created by pyj on 2016/11/25.
 */

public class MyApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        initGallerFinal();
    }
    private void initGallerFinal() {
        //设置主题
        //ThemeConfig.CYAN
        ThemeConfig themeConfig = new ThemeConfig.Builder()
        .build();
//        //配置功能
//        FunctionConfig functionConfig = new FunctionConfig.Builder()
//                .setEnableCamera(true)
//                .setEnableEdit(true)
//                .setEnableCrop(true)
//                .setEnableRotate(true)
//                .setCropSquare(true)
//                .setEnablePreview(true)
//        .build();
        FunctionConfig.Builder functionConfigBuilder = new FunctionConfig.Builder();
//        ThemeConfig themeConfig = ThemeConfig.TEAL; //样式
        //储存方式
        functionConfigBuilder.setEnableEdit(true);//可编辑
        functionConfigBuilder.setEnableCrop(true); //裁剪
        functionConfigBuilder.setCropReplaceSource(true);//裁剪翻盖原图
        functionConfigBuilder.setEnableRotate(true);//旋转
        functionConfigBuilder.setRotateReplaceSource(true); //旋转替换原图
        functionConfigBuilder.setCropSquare(true);   //裁剪正方形
        functionConfigBuilder.setForceCrop(true); //强制裁剪
        functionConfigBuilder.setForceCropEdit(true); //强制裁剪可编辑
        functionConfigBuilder.setEnableCamera(true);// 显示相机
        functionConfigBuilder.setEnablePreview(true); //启动预览
        //设置分辨率
        functionConfigBuilder.setCropWidth(720);
        functionConfigBuilder.setCropHeight(1280);

        GlideImageLoader imageLoader = new GlideImageLoader();
//        GlidePauseOnScrollListener pauseOnScrollListener = new GlidePauseOnScrollListener(false, true);

        FunctionConfig functionConfig = functionConfigBuilder.build();
        CoreConfig coreConfig = new CoreConfig.Builder(getApplicationContext(), imageLoader, themeConfig)
                .setFunctionConfig(functionConfig)
//                .setPauseOnScrollListener(pauseOnScrollListener)
                .build();
        GalleryFinal.init(coreConfig);

//        //配置 imageloader
//        ImageLoader imageloader = new GlideImageLoader();
//        CoreConfig coreConfig = new CoreConfig.Builder(getApplicationContext(), imageloader, theme)
//                .setFunctionConfig(functionConfig)
////                .setDebug(BuildConfig.DEBUG)
//                .build();
//        GalleryFinal.init(coreConfig);
    }

}
