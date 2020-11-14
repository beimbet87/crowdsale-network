package www.kaznu.kz.projects.m2.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.interfaces.Constants;

public class ImageAdapter extends PagerAdapter implements Constants {
    private Context mContext;
    private ArrayList<String> images;

    private int[] imageIDs = new int[] {
            R.drawable.default_appartment,
            R.drawable.default_appartment,
            R.drawable.default_appartment
    };

    public ImageAdapter(Context context, ArrayList<String> images) {
        this.images = new ArrayList<>();
        mContext = context;
        this.images.addAll(images);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        String url = BASE_URL.concat(images.get(position));
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(mContext).load(url).into(imageView);
        container.addView(imageView, 0);

        ImageView dots = new ImageView(mContext);
        dots.setImageResource(R.drawable.ic_pager_indicator_alpha);
        if(container.getId() == position) {
            dots.setImageResource(R.drawable.ic_pager_indicator);
        }
        container.addView(dots);
        return imageView;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
