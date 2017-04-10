package org.hunter.a361camera.camera;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.hunter.a361camera.R;
import org.hunter.a361camera.base.CameraApplication;
import org.hunter.a361camera.util.ActivityUtils;

import javax.inject.Inject;

public class CameraActivity extends AppCompatActivity {
    @Inject
    CameraPresenter mCameraPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CameraFragment statisticsFragment = (CameraFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (statisticsFragment == null) {
            statisticsFragment = CameraFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    statisticsFragment, R.id.contentFrame);
        }

        DaggerCameraComponent.builder()
                .cameraPresenterModule(new CameraPresenterModule(statisticsFragment))
                .cameraRepositoryComponent(((CameraApplication) getApplication())
                        .getTasksRepositoryComponent())
                .build().inject(this);
    }
}
