package org.hunter.a361camera.camera;

import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link CameraPresenter}.
 */
@Module
public class CameraPresenterModule {

    private final CameraContract.View mView;

    public CameraPresenterModule(CameraContract.View view) {
        mView = view;
    }

    @Provides
    CameraContract.View provideCameraContractView() {
        return mView;
    }
}
