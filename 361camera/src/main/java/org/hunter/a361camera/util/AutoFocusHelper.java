package org.hunter.a361camera.util;

import android.graphics.PointF;
import android.graphics.Rect;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.params.MeteringRectangle;

public class AutoFocusHelper {
    private static final MeteringRectangle[] ZERO_WEIGHT_3A_REGION = new MeteringRectangle[]{
            new MeteringRectangle(0, 0, 0, 0, 0)};

    private static final float REGION_WEIGHT = 0.022f;

    /**
     * Width of touch AF region in [0,1] relative to shorter edge of the current
     * crop region. Multiply this number by the number of pixels along the
     * shorter edge of the current crop region's width to get a value in pixels.
     * <p>
     * <p>
     * This value has been tested on Nexus 5 and Shamu, but will need to be
     * tuned per device depending on how its ISP interprets the metering box and weight.
     * </p>
     * <p>
     * <p>
     * Values prior to L release:
     * Normal mode: 0.125 * longest edge
     * Gcam: Fixed at 300px x 300px.
     * </p>
     */
    private static final float AF_REGION_BOX = 0.2f;

    /**
     * Width of touch metering region in [0,1] relative to shorter edge of the
     * current crop region. Multiply this number by the number of pixels along
     * shorter edge of the current crop region's width to get a value in pixels.
     * <p>
     * <p>
     * This value has been tested on Nexus 5 and Shamu, but will need to be
     * tuned per device depending on how its ISP interprets the metering box and weight.
     * </p>
     * <p>
     * <p>
     * Values prior to L release:
     * Normal mode: 0.1875 * longest edge
     * Gcam: Fixed at 300px x 300px.
     * </p>
     */
    private static final float AE_REGION_BOX = 0.3f;

    /**
     * camera2 API metering region weight.
     */
    private static final int CAMERA2_REGION_WEIGHT = (int)
            (lerp(MeteringRectangle.METERING_WEIGHT_MIN, MeteringRectangle.METERING_WEIGHT_MAX,
                    REGION_WEIGHT));

    private AutoFocusHelper() {

    }

    public static MeteringRectangle[] getZeroWeightRegion() {
        return ZERO_WEIGHT_3A_REGION;
    }

    public static float lerp(float a, float b, float t) {
        return a + t * (b - a);
    }

    /**
     * Calculates sensor crop region for a zoom level (zoom >= 1.0).
     *
     * @return Crop region.
     */
    public static Rect cropRegionForZoom(CameraCharacteristics characteristics, float zoom) {
        Rect sensor = characteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
        int xCenter = sensor.width() / 2;
        int yCenter = sensor.height() / 2;
        int xDelta = (int) (0.5f * sensor.width() / zoom);
        int yDelta = (int) (0.5f * sensor.height() / zoom);
        return new Rect(xCenter - xDelta, yCenter - yDelta, xCenter + xDelta, yCenter + yDelta);
    }

    public static MeteringRectangle[] regionsForNormalizedCoord(float nx, float ny,
                                                                float fraction, final Rect cropRegion, int sensorOrientation) {
        // Compute half side length in pixels.
        int minCropEdge = Math.min(cropRegion.width(), cropRegion.height());
        int halfSideLength = (int) (0.5f * fraction * minCropEdge);

        // Compute the output MeteringRectangle in sensor space.
        // nx, ny is normalized to the screen.
        // Crop region itself is specified in sensor coordinates.

        // Normalized coordinates, now rotated into sensor space.
        PointF nsc = CameraUtil.normalizedSensorCoordsForNormalizedDisplayCoords(
                nx, ny, sensorOrientation);

        int xCenterSensor = (int) (cropRegion.left + nsc.x * cropRegion.width());
        int yCenterSensor = (int) (cropRegion.top + nsc.y * cropRegion.height());

        Rect meteringRegion = new Rect(xCenterSensor - halfSideLength,
                yCenterSensor - halfSideLength,
                xCenterSensor + halfSideLength,
                yCenterSensor + halfSideLength);

        // Clamp meteringRegion to cropRegion.
        meteringRegion.left = CameraUtil.clamp(meteringRegion.left, cropRegion.left, cropRegion.right);
        meteringRegion.top = CameraUtil.clamp(meteringRegion.top, cropRegion.top, cropRegion.bottom);
        meteringRegion.right = CameraUtil.clamp(meteringRegion.right, cropRegion.left, cropRegion.right);
        meteringRegion.bottom = CameraUtil.clamp(meteringRegion.bottom, cropRegion.top, cropRegion.bottom);

        return new MeteringRectangle[]{new MeteringRectangle(meteringRegion, CAMERA2_REGION_WEIGHT)};
    }

    /**
     * Return AE region(s) for a sensor-referenced touch coordinate.
     * <p>
     * <p>
     * Normalized coordinates are referenced to portrait preview window with
     * (0, 0) top left and (1, 1) bottom right. Rotation has no effect.
     * </p>
     *
     * @return AE region(s).
     */
    public static MeteringRectangle[] aeRegionsForNormalizedCoord(float nx,
                                                                  float ny, final Rect cropRegion, int sensorOrientation) {
        return regionsForNormalizedCoord(nx, ny, AE_REGION_BOX,
                cropRegion, sensorOrientation);
    }

    /**
     * Return AF region(s) for a sensor-referenced touch coordinate.
     * <p>
     * <p>
     * Normalized coordinates are referenced to portrait preview window with
     * (0, 0) top left and (1, 1) bottom right. Rotation has no effect.
     * </p>
     *
     * @return AF region(s).
     */
    public static MeteringRectangle[] afRegionsForNormalizedCoord(float nx,
                                                                  float ny, final Rect cropRegion, int sensorOrientation) {
        return regionsForNormalizedCoord(nx, ny, AF_REGION_BOX,
                cropRegion, sensorOrientation);
    }
}
