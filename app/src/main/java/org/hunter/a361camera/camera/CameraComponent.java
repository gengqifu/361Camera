package org.hunter.a361camera.camera;

import org.hunter.a361camera.data.CameraRepositoryComponent;
import org.hunter.a361camera.util.FragmentScoped;

import dagger.Component;

/**
 * This is a Dagger component. Refer to {@link CameraApplication} for the list of Dagger components
 * used in this application.
 * <p>
 * Because this component depends on the {@link CameraRepositoryComponent}, which is a singleton, a
 * scope must be specified. All fragment components use a custom scope for this purpose.
 */
@FragmentScoped
@Component(dependencies = CameraRepositoryComponent.class, modules = CameraPresenterModule.class)
public interface CameraComponent {

    void inject(CameraActivity cameraActivity);
}
