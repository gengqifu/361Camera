package org.hunter.a361camera.base;

import android.app.Application;

import org.hunter.a361camera.data.CameraRepositoryComponent;
import org.hunter.a361camera.data.DaggerCameraRepositoryComponent;

public class CameraApplication extends Application {

    private CameraRepositoryComponent mRepositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mRepositoryComponent = DaggerCameraRepositoryComponent.builder()
                .applicationModule(new ApplicationModule((getApplicationContext())))
                .build();

    }

    public CameraRepositoryComponent getTasksRepositoryComponent() {
        return mRepositoryComponent;
    }

}
