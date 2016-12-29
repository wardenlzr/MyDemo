package in.srain.cube.views.ptr.demo.ui.classic;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.demo.R;

public class FooterFeatures extends WithGridView {

    @Override
    protected void setupViews(final PtrClassicFrameLayout ptrFrame) {
        ptrFrame.setLoadingMinTime(3000);
        setHeaderTitle(R.string.ptr_demo_block_grid_view_footer);
        ptrFrame.setResistanceFooter(1.0f);
        ptrFrame.setDurationToCloseFooter(0); // footer will hide immediately when completed
        ptrFrame.setForceBackWhenComplete(true);
        ptrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrFrame.autoLoadMore(true);
            }
        }, 150);
    }
}