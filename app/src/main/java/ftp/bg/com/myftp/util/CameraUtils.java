package ftp.bg.com.myftp.util;

import android.app.Activity;
import android.widget.Toast;

import java.util.List;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import ftp.bg.com.myftp.app.GlideImageLoader;

/**
 * Created by hoauadmin on 2016/11/20.
 */

public class CameraUtils {
    /**
     * 相机
     */
    private final int REQUEST_CODE_CAMERA = 1000;
    /**
     * 相册
     */
    private final int REQUEST_CODE_GALLERY = 1001;
    /**
     * 相册
     */
    private final int REQUEST_CODE_GALLERYS = 1001;
    /**
     * 编辑
     */
    private final int REQUEST_CODE_EDIT = 1003;

    private Activity mActivity;

    public CameraUtils(Activity activity) {
        mActivity = activity;
    }
    public interface CameraCallBack{
        public void ok(List<PhotoInfo> resultList);
        public void fail(String errorMsg);
    }
    /**
     * 打开相机
     */
    public void openCamera(final CameraCallBack ccb) {
        FunctionConfig functionConfig = getFunctionConfig();
        GalleryFinal.openCamera(REQUEST_CODE_CAMERA, functionConfig, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                ccb.ok(resultList);
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
                ccb.fail(errorMsg);
                Toast.makeText(mActivity, errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 打开相册
     *
     * @methodName- openGallerySingle
     * @Parameters- []
     * @ReturnType- void
     * @mail------- yinshengok@gmail.com
     * @createtime- 2016/11/20 10:24
     */

    public void openGallerySingle(final CameraCallBack ccb) {
        FunctionConfig functionConfig = getFunctionConfig();


        GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, functionConfig, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                ccb.ok(resultList);
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
                ccb.fail(errorMsg);
                Toast.makeText(mActivity, errorMsg, Toast.LENGTH_SHORT).show();
            }


        });

    }
    public void openGallery(final CameraCallBack ccb) {
        GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERYS, 3, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                ccb.ok(resultList);
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
                ccb.fail(errorMsg);
            }
        });
    }

    /**
     * 编辑
     *
     * @methodName- openEdit
     * @Parameters- []
     * @ReturnType- void
     * @mail------- yinshengok@gmail.com
     * @createtime- 2016/11/20 10:30
     */
    public void openEdit(String path) {
        FunctionConfig functionConfig = getFunctionConfig();
        GalleryFinal.openEdit(REQUEST_CODE_EDIT, functionConfig, path, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
                Toast.makeText(mActivity, errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 相机初始化
     *
     * @methodName- getFunctionConfig
     * @Parameters- []
     * @ReturnType- cn.finalteam.galleryfinal.FunctionConfig
     * @mail------- yinshengok@gmail.com
     * @createtime- 2016/11/20 10:32
     */
    private FunctionConfig getFunctionConfig() {
        FunctionConfig.Builder functionConfigBuilder = new FunctionConfig.Builder();
        ThemeConfig themeConfig = ThemeConfig.TEAL; //样式
        //储存方式
        functionConfigBuilder.setEnableEdit(false);//可编辑
        functionConfigBuilder.setEnableCrop(false); //裁剪
        functionConfigBuilder.setCropReplaceSource(false);//裁剪翻盖原图
        functionConfigBuilder.setEnableRotate(true);//旋转
        functionConfigBuilder.setRotateReplaceSource(true); //旋转替换原图
        functionConfigBuilder.setCropSquare(false);   //裁剪正方形
        functionConfigBuilder.setForceCrop(false); //强制裁剪
        functionConfigBuilder.setForceCropEdit(false); //强制裁剪可编辑
        functionConfigBuilder.setEnableCamera(true);// 显示相机
        functionConfigBuilder.setEnablePreview(true); //启动预览
        //设置分辨率
        functionConfigBuilder.setCropWidth(720);
        functionConfigBuilder.setCropHeight(1280);

        GlideImageLoader imageLoader = new GlideImageLoader();
//        GlidePauseOnScrollListener pauseOnScrollListener = new GlidePauseOnScrollListener(false, true);

        final FunctionConfig functionConfig = functionConfigBuilder.build();
        CoreConfig coreConfig = new CoreConfig.Builder(mActivity, imageLoader, themeConfig)
                .setFunctionConfig(functionConfig)
//                .setPauseOnScrollListener(pauseOnScrollListener)
                .build();
        GalleryFinal.init(coreConfig);
        return functionConfig;
    }
}
