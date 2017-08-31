package org.hunter.a361camera.camera;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.hunter.a361camera.R;
import org.hunter.a361camera.util.ActivityUtils;
import org.hunter.a361camera.view.Camera2Fragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Camera2Fragment camera2Fragment = (Camera2Fragment) getFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (camera2Fragment == null) {
            camera2Fragment = Camera2Fragment.newInstance();
            ActivityUtils.addFragmentToActivity(getFragmentManager(),
                    camera2Fragment, R.id.contentFrame);
        }
    }
}
