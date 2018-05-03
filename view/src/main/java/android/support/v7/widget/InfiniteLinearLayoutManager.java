package android.support.v7.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class InfiniteLinearLayoutManager extends LinearLayoutManager {

    private static final String TAG = "InfiniteLLManager";
    private final LayoutChunkResult mLayoutChunkResult = new LayoutChunkResult();

    public InfiniteLinearLayoutManager(Context context) {
        super(context);
    }

    public InfiniteLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public InfiniteLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    LayoutState createLayoutState() {
        return new InfiniteLayoutState();
    }

    class InfiniteLayoutState extends LayoutState {
        @Override
        boolean hasMore(RecyclerView.State state) {
            return mRecyclerView.getAdapter().getItemCount() > 1 ? true : super.hasMore(state);
        }

        View next(RecyclerView.Recycler recycler) {
            if (mScrapList != null) {
                return next(recycler);
            }
            int position = mCurrentPosition;
            int itemCount = mRecyclerView.getAdapter().getItemCount();
            if (itemCount > 0 && position >= itemCount) {
                position = position % itemCount;
            } else if (itemCount > 0 && position < 0) {
                position = itemCount - Math.abs(position) % itemCount;
            }
            final View view = recycler.getViewForPosition(position);
            mCurrentPosition += mItemDirection;
            return view;
        }
    }
}
