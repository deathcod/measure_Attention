package info.androidhive.navigationdrawer.other;

/**
 * Created by chinmay on 18-May-17.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.View;

import java.io.InputStream;

import info.androidhive.navigationdrawer.R;


public class GIFView extends View {
    public Movie mMovie;
    public long movieStart;
    private int gifId = -1;

    public GIFView(Context context) {
        super(context);
        initializeView();
    }

    public GIFView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView();
    }

    public GIFView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeView();
    }

    private void initializeView() {
//R.drawable.loader - our animated GIF
        InputStream is = getContext().getResources().openRawResource(R.drawable.congratulations1);

        if (gifId != -1)
            is = getContext().getResources().openRawResource(gifId);
        mMovie = Movie.decodeStream(is);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        super.onDraw(canvas);
        long now = android.os.SystemClock.uptimeMillis();
        if (movieStart == 0) {
            movieStart = now;
        }
        if (mMovie != null) {
            int relTime = (int) ((now - movieStart) % mMovie.duration());
            mMovie.setTime(relTime);
            mMovie.draw(canvas, getWidth() - mMovie.width(), getHeight() - mMovie.height());
            this.invalidate();
        }
    }

    public int getGIFResource() {
        return this.gifId;
    }

    public void setGIFResource(int resId) {
        this.gifId = resId;
        initializeView();
    }
}