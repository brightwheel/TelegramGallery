
package com.tangxiaolv.telegramgallery;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * the {@link GalleryActivity} of buidler.
 */
public class GalleryConfig implements Parcelable {

    private String hintOfPick;
    private boolean singleEntity;
    private int limitPickPhoto;
    private PendingIntent limitReachedIntent;
    private long maxVideoDuration;
    private long maxVideoSize;
    private long maxPhotoSize;

    private @PickerMode int pickerMode;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
        PHOTO_MODE,
        VIDEO_MODE
    })
    public @interface PickerMode {}
    public final static int PHOTO_MODE = 0;
    public final static int VIDEO_MODE = 1;

    private GalleryConfig(){

    }

    private GalleryConfig(String hintOfPick, boolean singleEntity, int limitPickPhoto,
                          PendingIntent limitReachedIntent, @PickerMode int pickerMode,
                          long maxVideoDuration, long maxVideoSize, long maxPhotoSize) {
        this.hintOfPick = hintOfPick;
        this.singleEntity = singleEntity;
        this.limitPickPhoto = limitPickPhoto;
        this.limitReachedIntent = limitReachedIntent;
        this.pickerMode = pickerMode;
        this.maxVideoDuration = maxVideoDuration;
        this.maxVideoSize = maxVideoSize;
        this.maxPhotoSize = maxPhotoSize;
    }

    String getHintOfPick() {
        return hintOfPick;
    }

    boolean isSingleEntity() {
        return singleEntity;
    }

    int getLimitPickPhoto() {
        return limitPickPhoto;
    }

    PendingIntent getLimitReachedIntent() {
        return limitReachedIntent;
    }

    @GalleryConfig.PickerMode int getPickerMode() {
        return pickerMode;
    }

    long getMaxVideoDuration() {
        return maxVideoDuration;
    }

    long getMaxVideoSize() {
        return maxVideoSize;
    }

    long getMaxPhotoSize() {
        return maxPhotoSize;
    }

    public static class Build {
        private String hintOfPick;
        private boolean singleEntity = false;
        private int limitPickPhoto = 9;
        private PendingIntent multiPhotoSelectedPendingIntent = null;
        private @PickerMode int pickerMode = PHOTO_MODE;
        private long maxVideoDuration = 30*1000;
        private long maxVideoSize = 50*1024*1024;
        private long maxPhotoSize = 10*1024*1024;

        /**
         * @param hintOfPick hint of Toast when limit is reached
         */
        public Build hintOfPick(String hintOfPick) {
            this.hintOfPick = hintOfPick;
            return this;
        }

        /**
         * @param singleEntity true:single pick false:multi pick
         */
        public Build singleEntity(boolean singleEntity) {
            this.singleEntity = singleEntity;
            return this;
        }

        /**
         * @param limitPickPhoto the limit of photos those can be selected
         */
        public Build limitPickPhoto(int limitPickPhoto) {
            this.limitPickPhoto = limitPickPhoto;
            return this;
        }

        /**
         * @param pendingIntent the pendingIntent that will be sent when user selects a picture
         *                      and {@code limitPickPhoto} photos are already selected.
         *                      This will be ignored if {@code singlePicture} is true. If both
         *                      {@code hintOfPick} and pendingIntent are provided, only pendingIntent
         *                      will be used.
         */
        public Build limitReachedIntent(PendingIntent pendingIntent) {
            this.multiPhotoSelectedPendingIntent = pendingIntent;
            return this;
        }

        public Build pickerMode(@PickerMode int pickerMode) {
            this.pickerMode = pickerMode;
            return this;
        }

        public Build maxVideoDuration(long maxVideoDuration) {
            this.maxVideoDuration = maxVideoDuration;
            return this;
        }

        public Build maxVideoSize(long maxVideoSize) {
            this.maxVideoSize = maxVideoSize;
            return this;
        }

        public Build maxPhotoSize(long maxPhotoSize) {
            this.maxPhotoSize = maxPhotoSize;
            return this;
        }

        public GalleryConfig build() {
            this.limitPickPhoto = singleEntity ? 1 : limitPickPhoto > 0 ? limitPickPhoto : 1;
            if (singleEntity) {
                this.multiPhotoSelectedPendingIntent = null;
            }
            return new GalleryConfig(
                hintOfPick,
                singleEntity,
                limitPickPhoto,
                multiPhotoSelectedPendingIntent,
                pickerMode,
                maxVideoDuration,
                maxVideoSize,
                maxPhotoSize);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hintOfPick);
        dest.writeByte(singleEntity ? (byte) 1 : (byte) 0);
        dest.writeInt(limitPickPhoto);
        dest.writeValue(limitReachedIntent);
        dest.writeInt(pickerMode);
        dest.writeLong(maxVideoDuration);
        dest.writeLong(maxVideoSize);
        dest.writeLong(maxPhotoSize);
    }

    private GalleryConfig(Parcel in) {
        hintOfPick = in.readString();
        singleEntity = in.readByte() != 0;
        limitPickPhoto = in.readInt();
        limitReachedIntent =
            (PendingIntent) in.readValue(PendingIntent.class.getClassLoader());
        pickerMode = in.readInt();
        maxVideoDuration = in.readLong();
        maxVideoSize = in.readLong();
        maxPhotoSize = in.readLong();
    }

    public static final Creator<GalleryConfig> CREATOR = new Creator<GalleryConfig>() {
        @Override
        public GalleryConfig createFromParcel(Parcel source) {
            return new GalleryConfig(source);
        }

        @Override
        public GalleryConfig[] newArray(int size) {
            return new GalleryConfig[size];
        }
    };
}
