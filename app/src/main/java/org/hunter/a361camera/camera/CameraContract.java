package org.hunter.a361camera.camera;

import org.hunter.a361camera.base.BasePresenter;
import org.hunter.a361camera.base.BaseView;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface CameraContract {

    interface View extends BaseView<Presenter> {
        boolean isActive();
    }

    interface Presenter extends BasePresenter {

    }
}
