package la.dominga.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

import la.dominga.R;

public class OfertaAdapter extends PagerAdapter {

    private Context context;
    private List<Integer> imagenesOfertas;

    public OfertaAdapter(Context context) {
        this.context = context;
        this.imagenesOfertas = new ArrayList<>();
        // Agregamos las imágenes
        imagenesOfertas.add(R.drawable.torta1);
        imagenesOfertas.add(R.drawable.torta2);
        imagenesOfertas.add(R.drawable.torta4);
        imagenesOfertas.add(R.drawable.torta5);
        imagenesOfertas.add(R.drawable.torta6);
        imagenesOfertas.add(R.drawable.torta7);
        imagenesOfertas.add(R.drawable.torta8);
        imagenesOfertas.add(R.drawable.torta9);
        imagenesOfertas.add(R.drawable.torta10);
        imagenesOfertas.add(R.drawable.torta11);
        // ...
    }

    @Override
    public int getCount() {
        return imagenesOfertas.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_oferta, container, false);
        ImageView imageView = view.findViewById(R.id.imagenOferta);
        imageView.setImageResource(imagenesOfertas.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
