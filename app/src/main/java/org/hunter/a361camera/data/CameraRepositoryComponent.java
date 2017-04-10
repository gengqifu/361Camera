package org.hunter.a361camera.data;

import org.hunter.a361camera.base.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * This is a Dagger component. Refer to {@link CameraApplication} for the list of Dagger components
 * used in this application.
 * <p>
 * Even though Dagger allows annotating a {@link Component @Component} as a singleton, the code
 * itself must ensure only one instance of the class is created. This is done in {@link
 * CameraApplication}.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface CameraRepositoryComponent {

    CameraRepository getTasksRepository();
}
