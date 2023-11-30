package la.dominga.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class EspacioItemDecoration extends RecyclerView.ItemDecoration {
    private final int espacioVertical;
    private final int espacioHorizontal;

    public EspacioItemDecoration(int espacioHorizontal, int espacioVertical) {
        this.espacioHorizontal = espacioHorizontal;
        this.espacioVertical = espacioVertical;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = espacioHorizontal;
        outRect.right = espacioHorizontal;
        outRect.bottom = espacioVertical;
        outRect.top = espacioVertical;
    }
}

