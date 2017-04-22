package net.jiawa.jobhunter.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;

import net.jiawa.debughelper.XLog;
import net.jiawa.jobhunter.R;

/**
 * Created by zhaoxin5 on 2017/4/21.
 */

/**
 * 拷贝自
 * https://github.com/MarkMjw/PullScrollView
 *
 * 增加部分注释
 */
public class PullNestedScrollView extends NestedScrollView {

    private static final String LOG_TAG = "PullNestedScrollView";
    /** 阻尼系数,越小阻力就越大. */
    private static final float SCROLL_RATIO = 0.5f;

    /** 滑动至翻转的距离. */
    private static final int TURN_DISTANCE = 100;

    /** 头部view. */
    private View mHeader;

    /** 头部view的id. */
    private int mHeaderId;

    /** 头部view高度. */
    /*private int mHeaderHeight;*/

    /** 头部view显示高度. */
    private int mHeaderVisibleHeight;

    /** ScrollView的content view. */
    private View mContentView;

    /** ScrollView的content view矩形. */
    private Rect mContentRect = null;

    /** 首次点击的Y坐标. */
    private PointF mStartPoint = new PointF();

    /** 是否开始移动. */
    private boolean isMoving = false;

    /** 是否移动到顶部位置. */
    private boolean isTop = false;

    /** 头部图片初始顶部和底部. */
    // private int mInitTop, mInitBottom;

    /** 头部图片拖动时顶部和底部. */
    private int mCurrentTop, mCurrentBottom;

    /** 状态变化时的监听器. */
    private OnTurnListener mOnTurnListener;

    /**
     * 保存顶部图片的最原始的left, top, right, bottom
     *  */
    private Rect mHeaderRect = null;

    private enum State {
        /**顶部*/
        UP,
        /**底部*/
        DOWN,
        /**正常*/
        NORMAL
    }

    /** 状态. */
    private State mState = State.NORMAL;

    public PullNestedScrollView(Context context) {
        super(context);
        init(context, null);
    }

    public PullNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PullNestedScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // set scroll mode
        setOverScrollMode(OVER_SCROLL_NEVER);

        if (null != attrs) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PullNestedScrollView);

            if (ta != null) {
                // mHeaderHeight = (int) ta.getDimension(R.styleable.PullNestedScrollView_headerHeight, -1);
                mHeaderVisibleHeight = (int) ta.getDimension(R.styleable
                        .PullNestedScrollView_headerVisibleHeight, -1);
                mHeaderId = ta.getResourceId(R.styleable.PullNestedScrollView_header, -1);

                // mHeaderHeight: 600
                // 图片正常要显示的高度是600
                // XLog.d(false, 1, "mHeaderHeight: " + mHeaderHeight);
                ta.recycle();
            }
        }
    }

    /**
     * 设置Header
     *
     * @param view
     */
    public void setHeader(View view) {
        mHeader = view;
    }

    /**
     * 设置状态改变时的监听器
     *
     * @param turnListener
     */
    public void setOnTurnListener(OnTurnListener turnListener) {
        mOnTurnListener = turnListener;
    }

    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            mContentView = getChildAt(0);
        }
    }

    @Override
    protected void onScrollChanged(int scrollX, int scrollY,
                                   int oldScrollX, int oldScrollY) {
        super.onScrollChanged(scrollX, scrollY, oldScrollX, oldScrollY);

        XLog.d(false, 1, "scrollY: " + scrollY);
        final int originalTop = mHeaderRect.top;
        final int maxMove = (int) (Math.abs(originalTop) / 0.5f / SCROLL_RATIO);
        if (0 <= scrollY && scrollY <= maxMove) {
            // 在此范围内
            // 上滑ScrollView,会把顶部的图片也滑动上去
            mHeader.layout(mHeaderRect.left, mHeaderRect.top - scrollY,
                             mHeaderRect.right, mHeaderRect.bottom - scrollY);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return onTouchEvent(ev) || super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mContentView != null) {
            int action = ev.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    // 记录触摸的初始位置信息
                    mStartPoint.set(ev.getX(), ev.getY());
                    mCurrentTop = mHeader.getTop();
                    mCurrentBottom = mHeader.getBottom();
                    if (null == mHeaderRect) {
                        // 顶部图片最原始的位置信息
                        mHeaderRect = new Rect();
                        mHeaderRect.set(mHeader.getLeft(), mHeader.getTop(),
                                mHeader.getRight(), mHeader.getBottom());
                        XLog.d(true, 1, mHeaderRect.toString());
                    }
                    // 初始化content view矩形
                    if (null == mContentRect) {
                        // 保存正常的布局位置
                        // 这是最开始的布局位置信息
                        mContentRect = new Rect();
                        mContentRect.set(mContentView.getLeft(), mContentView.getTop(),
                                mContentView.getRight(), mContentView.getBottom());
                        XLog.d(false, 1, mContentRect.toString());
                    }
                    //
                    // 初始位置信息是负的,因为这个顶部有1/3是隐藏的
                    // [mInitTop: -199, mInitBottom: 401]
                    //
                    // 可以看到图片正常应该显示的高度就是
                    //  401-(-199) = 600
                    //  public final int getHeight() {
                    //      return mBottom - mTop;
                    //  }
                    //
                    return super.onTouchEvent(ev);
                case MotionEvent.ACTION_MOVE:
                    float deltaY = Math.abs(ev.getY() - mStartPoint.y);
                    // 确保是纵轴方向的滑动
                    if (deltaY > 0 && getScrollY() < 1/*deltaY > 10 && deltaY > Math.abs(ev.getX() - mStartPoint.x)*/) {
                        mHeader.clearAnimation();
                        mContentView.clearAnimation();
                        doActionMove(ev);
                    }
                    if (getScrollY() > 0) {
                        /**
                         * 修复将ScrollView先上滚一段距离
                         * 然后下拉图片时,会有突变的情形出现
                         */
                        mStartPoint.set(ev.getX(), ev.getY());
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    // 回滚动画
                    if (isNeedAnimation()) {
                        rollBackAnimation();
                    }

                    // 只有在结束滑动时才清除当前状态
                    // 此时才可以在doActionMove中进行UP和DOWN的切换
                    mState = State.NORMAL;

                    isMoving = false;
                    break;
                default:
                    break;

            }
            XLog.d(true, 1, "state: " + mState + ", isMoving: " + isMoving + ", ScrollY: " + getScrollY());
        }

        // 禁止控件本身的滑动.
        boolean isHandle = isMoving;
        if (!isMoving) {
            try {
                isHandle = super.onTouchEvent(ev);
            } catch (Exception e) {
                Log.w(LOG_TAG, e);
            }
        }
        return isHandle;
    }

    /**
     * 执行移动动画
     *
     * @param event
     */
    private void doActionMove(MotionEvent event) {

        float deltaY = event.getY() - mStartPoint.y;

        // 对于首次Touch操作要判断方位：UP OR DOWN
        if (deltaY < 0 && mState == State.NORMAL) {
            mState = State.UP;
        } else if (deltaY > 0 && mState == State.NORMAL) {
            mState = State.DOWN;
        }

        if (mState == State.UP) {
            deltaY = deltaY < 0 ? deltaY : 0;

            isMoving = false;

        } else if (mState == State.DOWN) {
            XLog.d(false, 1, "getScrollY(): " + getScrollY() + ", deltaY: " +deltaY);
            if (getScrollY() <= deltaY) {
                isMoving = true;
            }
            // 不要越界
            // 最小是0， 最大是顶部图片的高度
            /***
             *
             * deltaY 只能分一半给顶部的imageview的top值
             * 同时这个一半还需要乘以一个阻尼系数SCROLL_RATIO
             *
             * 然后初始的mInitTop肯定是负的
             * 只有将这个mInitTop从负值变成0
             * 才能实现完整的将顶部图片拖出的全过程
             *
             */
            int maxMove = (int) (Math.abs(mHeaderRect.top) / 0.5f / SCROLL_RATIO);
            deltaY = deltaY < 0 ? 0 : (deltaY > maxMove ? maxMove : deltaY);
        }

        if (isMoving) {

            // 计算header移动距离(手势移动的距离*阻尼系数*0.5)
            float headerMoveHeight = deltaY * 0.5f * SCROLL_RATIO;
            // 这里的0.5是表明上下各分一半
            mCurrentTop = (int) (mHeaderRect.top + headerMoveHeight);
            mCurrentBottom = (int) (mHeaderRect.bottom + headerMoveHeight);

            // 计算content移动距离(手势移动的距离*阻尼系数)
            float contentMoveHeight = deltaY * SCROLL_RATIO;
            XLog.d(false, 1, "mCurrentTop: " + mCurrentTop + ", mCurrentBottom: " + mCurrentBottom);
            XLog.d(false, 1, "headerMoveHeight: " + headerMoveHeight + ", contentMoveHeight : " + contentMoveHeight);

            /***
             *
             * 刚开始:
             *
             *          --------------- <- mInitTop,
             *          |             |    顶部图片的原始top位置,
             *          |   invisible |    layout_marginTop="-100dp"实现的
             *          |             |
             *          |             |
             *  phone --------------------------------------------------------------------- <-  mContentRect.top,
             *          |             |                              |                   |      contentView的原始top值
             *          |             |                              |   invisible       |
             *          |     visible |                              |                   |
             *          |             |                              |                   |
             *          ------------------------------------------------------------------  <-  layout_marginTop="100dp"实现的
             *          |             |                              |   visible area    |
             *          |   invisible |                              |   of the          |
             *          |             |                              |   scrollView      |
             *          |             |                              |                   |
             *          --------------- <- mInitBottom,              |                   |
             *                             original bottom of the    |                   |
             *                             top image                 |                   |
             *                                                       ---------------------- <-  mContentRect.bottom,
             *                                                                                  contentView的原始bottom值
             *  移动中:
             *
             *          --------------- <- mCurrentTop = mInitTop + headerMoveHeight,
             *          |   invisible |    当前顶部图片的top位置
             *          |             |    移动了2个单位
             *  phone ----------------------------------------------------------------------------
             *          |             |                                                        ↑
             *          |     visible |                                                      位移了
             *          |             |   image visible area                                 4个单位
             *          |             |   becomes enlarge                                      ↓
             *               ----                                    ------------------------------ <-  top
             *          |             |                              |                   |
             *          |     visible |                              |   invisible       |
             *          |             |                              |                   |
             *          |             |                              |                   |
             *          ------------------------------------------------------------------  <-  layout_marginTop="100dp"实现的
             *          |   invisible |                              |                   |      这个必须要大于mCurrentBottom
             *          |             |                              |  visible area     |
             *          --------------- <- mCurrentBottom,           |  of the           |
             *                             current bottom of the     |  scrollView       |
             *                             top image view            |                   |
             *                             move down for             |                   |
             *                             2                         |                   |
             *                                                       ---------------------  <-  bottom
             *
             *
             *
             *  移动到最下面:          mCurrentTop = mInitTop + headerMoveHeight,
             *                        ↓  当前顶部图片的top位置，移动了4个单位
             *  phone ----------------------------------------------------------------------------
             *          |             |                                                       |
             *          |             |                                                       |
             *          |             |                                                       |
             *          |     visible |
             *               ----                                                         移动了8个单位
             *          |             |   image visible area
             *          |             |   becomes enlarge                                     |
             *          |             |                                                       |
             *          |     visible |                                                       |
             *               ----                                    ------------------------------ <-  top
             *          |             |                              |                   |
             *          |             |                              |   invisible       |
             *          |             |                              |                   |
             *          |     visible |                              |                   |
             *          ------------------------------------------------------------------  <-  layout_marginTop="100dp"实现的
             *                       ↑                              |                   |      这个必须要大于mCurrentBottom
             *                        mCurrentBottom                 |  visible area     |      不能再往下移动
             *                        current bottom of the          |  of the           |      否则，盖不住顶部的图片
             *                        top image view                 |  scrollView       |
             *                        move down for  4               |                   |
             *                                                       |                   |
             *                                                       |                   |
             *                                                       ---------------------  <-  bottom
             *
             *   从上图可知
             *   top + layout_marginTop="100dp" <= mCurrentBottom
             *
             */

            // 修正content移动的距离，避免超过header的底边缘
            int headerBottom = mCurrentBottom - mHeaderVisibleHeight;
            int top = (int) (mContentRect.top + contentMoveHeight);
            int bottom = (int) (mContentRect.bottom + contentMoveHeight);

            XLog.d(false, 1, "top: " + top + ", bottom: " + bottom);
            if (top <= headerBottom) {
                // 移动content view
                mContentView.layout(mContentRect.left, top, mContentRect.right, bottom);

                // 移动header view
                mHeader.layout(mHeader.getLeft(), mCurrentTop, mHeader.getRight(), mCurrentBottom);
            }
        }
    }

    private void rollBackAnimation() {
        TranslateAnimation tranAnim = new TranslateAnimation(0, 0,
                Math.abs(mHeaderRect.top - mCurrentTop), 0);
        tranAnim.setDuration(200);
        mHeader.startAnimation(tranAnim);

        mHeader.layout(mHeaderRect.left, mHeaderRect.top, mHeaderRect.right, mHeaderRect.bottom);

        // 开启移动动画
        TranslateAnimation innerAnim = new TranslateAnimation(0, 0, mContentView.getTop(), mContentRect.top);
        innerAnim.setDuration(200);
        mContentView.startAnimation(innerAnim);
        mContentView.layout(mContentRect.left, mContentRect.top, mContentRect.right, mContentRect.bottom);

        // mContentRect.setEmpty();

        // 回调监听器
        if (mCurrentTop > mHeaderRect.top + TURN_DISTANCE && mOnTurnListener != null){
            mOnTurnListener.onTurn();
        }
    }

    /**
     * 是否需要开启动画
     */
    private boolean isNeedAnimation() {
        return !mContentRect.isEmpty() && isMoving && getScrollY() < 1;
    }

    /**
     * 翻转事件监听器
     *
     * @author markmjw
     */
    public interface OnTurnListener {
        /**
         * 翻转回调方法
         */
        public void onTurn();
    }
}
