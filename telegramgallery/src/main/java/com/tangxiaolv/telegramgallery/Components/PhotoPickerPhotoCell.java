
package com.tangxiaolv.telegramgallery.Components;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tangxiaolv.telegramgallery.AnimatorListenerAdapterProxy;
import com.tangxiaolv.telegramgallery.R;
import com.tangxiaolv.telegramgallery.Utils.AndroidUtilities;
import com.tangxiaolv.telegramgallery.Utils.LayoutHelper;

import java.util.concurrent.TimeUnit;

import static com.tangxiaolv.telegramgallery.PhotoAlbumPickerActivity.DarkTheme;

public class PhotoPickerPhotoCell extends FrameLayout {

    public BackupImageView photoImage;
    public FrameLayout checkFrame;
    public CheckBox checkBox;
    private AnimatorSet animator;
    public int itemWidth;
    public TextView textView;

    public PhotoPickerPhotoCell(Context context) {
        super(context);

        photoImage = new BackupImageView(context);
        addView(photoImage,
                LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));

        checkFrame = new FrameLayout(context);
        addView(checkFrame, LayoutHelper.createFrame(42, 42, Gravity.RIGHT | Gravity.TOP));

        checkBox = new CheckBox(context, R.drawable.checkbig);
        checkBox.setSize(24);
        checkBox.setCheckOffset(AndroidUtilities.dp(1));
        checkBox.setDrawBackground(true);
        checkBox.setColor(0xff007aff);
        addView(checkBox,
                LayoutHelper.createFrame(24, 24, Gravity.RIGHT | Gravity.TOP, 0, 4, 4, 0));

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        addView(linearLayout, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.BOTTOM));

        textView = new TextView(context);
        textView.setTextColor(0xffffffff);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
        textView.setSingleLine(true);
        textView.setMaxLines(1);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        linearLayout.addView(textView,
            LayoutHelper.createLinear(44, LayoutHelper.WRAP_CONTENT, 1.0f, 0, 0, 8, 0));
    }

    public void setVideoLength(long videoDuration) {
        final String displayString = String.format("%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(videoDuration),
            TimeUnit.MILLISECONDS.toSeconds(videoDuration) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(videoDuration))
        );
        textView.setText(displayString);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(itemWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(itemWidth, MeasureSpec.EXACTLY));
    }

    public void setChecked(final boolean checked, final boolean animated) {
        checkBox.setChecked(checked, animated);
        if (animator != null) {
            animator.cancel();
            animator = null;
        }
        if (animated) {
            if (checked) {
                setBackgroundColor(DarkTheme ? 0xff0A0A0A : 0xffffffff);
            }
            animator = new AnimatorSet();
            animator.playTogether(
                    ObjectAnimator.ofFloat(photoImage, "scaleX", checked ? 0.85f : 1.0f),
                    ObjectAnimator.ofFloat(photoImage, "scaleY", checked ? 0.85f : 1.0f));
            animator.setDuration(200);
            animator.addListener(new AnimatorListenerAdapterProxy() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (animator != null && animator.equals(animation)) {
                        animator = null;
                        if (!checked) {
                            setBackgroundColor(0);
                        }
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    if (animator != null && animator.equals(animation)) {
                        animator = null;
                    }
                }
            });
            animator.start();
        } else {
            setBackgroundColor(checked ? DarkTheme ? 0xff0A0A0A : 0xffffffff : 0);
            photoImage.setScaleX(checked ? 0.85f : 1.0f);
            photoImage.setScaleY(checked ? 0.85f : 1.0f);
        }
    }

    public void setChecked(int num, final boolean checked, final boolean animated) {
        checkBox.setChecked(num, checked, animated);
        if (animator != null) {
            animator.cancel();
            animator = null;
        }
        if (animated) {
            if (checked) {
                setBackgroundColor(DarkTheme ? 0xff0A0A0A : 0xffffffff);
            }
            animator = new AnimatorSet();
            animator.playTogether(
                    ObjectAnimator.ofFloat(photoImage, "scaleX", checked ? 0.85f : 1.0f),
                    ObjectAnimator.ofFloat(photoImage, "scaleY", checked ? 0.85f : 1.0f));
            animator.setDuration(200);
            animator.addListener(new AnimatorListenerAdapterProxy() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (animator != null && animator.equals(animation)) {
                        animator = null;
                        if (!checked) {
                            setBackgroundColor(0);
                        }
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    if (animator != null && animator.equals(animation)) {
                        animator = null;
                    }
                }
            });
            animator.start();
        } else {
            setBackgroundColor(checked ? DarkTheme ? 0xff0A0A0A : 0xffffffff : 0);
            photoImage.setScaleX(checked ? 0.85f : 1.0f);
            photoImage.setScaleY(checked ? 0.85f : 1.0f);
        }
    }
}
