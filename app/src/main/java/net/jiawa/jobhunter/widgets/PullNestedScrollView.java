package net.jiawa.jobhunter.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.TranslateAnimation;
import android.support.v4.widget.NestedScrollView;

import net.jiawa.debughelper.XLog;
import net.jiawa.jobhunter.R;

/**
 * Created by zhaoxin5 on 2017/4/21.
 */

/**
 * 改编自
 * https://github.com/MarkMjw/PullScrollView
 *
 * 说明
 * 1，onTouchEvent只处理向下滑动
 * 2，onScrollChanged处理向上滑动
 */
public class PullNestedScrollView extends NestedScrollView {

    private static final String LOG_TAG = "PullNestedScrollView";
    /** 阻尼系数,越小阻力就越大. */
    private static final float SCROLL_RATIO = 0.5f;
    /** 滑动至翻转的距离. */
    private static final int TURN_DISTANCE = 100;
    /** 头部view. */
    private View mHeader;
    /** 头部view显示高度. */
    private int mHeaderVisibleHeight;
    /** ScrollView的content view. */
    private View mContentView;
    /**
     * ScrollView的content view矩形.
     * 记录最开始的坐标
     * */
    private Rect mContentRect = null;
    /**
     * 首次点击的Y坐标.
     * 当现在是由NestedScrollView本身处理的向上滑动时
     * 要时刻更新这个Y坐标
     * 避免由onTouchEvent中进行处理时
     * 计算y位移差出现较大的偏差
     * */
    private PointF mStartPoint = new PointF();
    /**
     * 用于onInterceptTouchEvent进行判断的变量
     * 记录从dispatchTouchEvent中记录的初次
     * 点击位置
     */
    private float FirstTouchY;
    /** 是否开始向下移动. */
    private boolean isMovingDown = false;
    /** 头部图片拖动时顶部和底部. */
    private int mCurrentTop, mCurrentBottom;
    /** 状态变化时的监听器. */
    private OnTurnListener mOnTurnListener;
    /**
     * 保存顶部图片的最原始的left, top, right, bottom
     *  */
    private Rect mHeaderRect = null;

    private int TouchSlop;

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

        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        TouchSlop = configuration.getScaledTouchSlop();

        if (null != attrs) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PullNestedScrollView);
            if (ta != null) {
                // mHeaderHeight = (int) ta.getDimension(R.styleable.PullNestedScrollView_headerHeight, -1);
                mHeaderVisibleHeight = (int) ta.getDimension(R.styleable
                        .PullNestedScrollView_headerVisibleHeight, -1);

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

        XLog.d(true, 1, "scrollY: " + scrollY);
        final int originalTop = mHeaderRect.top;
        final int maxMove = (int) (Math.abs(originalTop) / 0.5f / SCROLL_RATIO);
        if (0 <= scrollY && scrollY <= maxMove) {
            // 在此范围内
            // 上滑ScrollView,会把顶部的图片也滑动上去
            mHeader.layout(mHeaderRect.left, mHeaderRect.top - scrollY,
                             mHeaderRect.right, mHeaderRect.bottom - scrollY);
        }
    }

    /***
     *
     * 不对这里进行特殊的拦截
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                final float yDiff = ev.getY() - FirstTouchY;
                if (getScrollY() == 0 && yDiff > TouchSlop) {
                    XLog.d(true, 1, ".................... y: " + ev.getY() + ", FirstTouchY: " + FirstTouchY + ", yDiff: " + yDiff);
                    return true;
                }
                break;
        }

        boolean onInterceptTouchEvent = super.onInterceptTouchEvent(ev);
        XLog.d(true, 1, "y: " + ev.getY() + ", onInterceptTouchEvent: " + onInterceptTouchEvent + ", isMovingDown: " + isMovingDown);
        return onInterceptTouchEvent;
    }

    /***
     * Touch的处理是这样的：
     * 1, dispatchTouchEvent
     * 2，onInterceptTouchEvent
     * 3, onTouchEvent
     *
     * 肯定首先进入这个dispatchTouchEvent, 在这里记录当前点击位置,
     * 因为有可能不进入onTouchEvent的MotionEvent.ACTION_DOWN分支
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                XLog.d(true, 1, "dispatchTouchEvent: " + ev.getY());
                mStartPoint.set(ev.getX(), ev.getY());
                FirstTouchY = ev.getY();
                if (null == mHeaderRect) {
                    // 顶部图片最原始的位置信息
                    mHeaderRect = new Rect();
                    mHeaderRect.set(mHeader.getLeft(), mHeader.getTop(),
                            mHeader.getRight(), mHeader.getBottom());
                    XLog.d(false, 1, mHeaderRect.toString());
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
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mContentView != null) {
            int action = ev.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    return super.onTouchEvent(ev);
                case MotionEvent.ACTION_MOVE:
                    XLog.d(true, 1, "Y: " + ev.getY());
                    float deltaY = ev.getY() - mStartPoint.y;
                    // 确保是纵轴方向
                    // 向下的滑动
                    if (deltaY > 0 && getScrollY() == 0/*deltaY > 10 && deltaY > Math.abs(ev.getX() - mStartPoint.x)*/) {
                        mHeader.clearAnimation();
                        mContentView.clearAnimation();
                        isMovingDown = true;
                        doMoveDown(ev);
                        XLog.d(false, 1, "mStartPoint.y: " + mStartPoint.y + ", deltaY: " + deltaY);
                    } else {
                        if (deltaY < 0.01f && isMovingDown) {
                            /**
                             * 用户正在向下滑动
                             * 然后继续回退准备向上滑动
                             *
                             * 要将ImageView和ContentView回退到原始的位置
                             */
                            rollBackAnimation(false);
                            XLog.d(false, 1, "deltaY < 0.01f && isMovingDown");
                        }
                        /**
                         * 修复将ScrollView先上滚一段距离
                         * 然后下拉图片时,会有突变的情形出现
                         */
                        mStartPoint.set(ev.getX(), ev.getY());
                        isMovingDown = false;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    // 回滚动画
                    if (isNeedAnimation()) {
                        rollBackAnimation();
                    }

                    isMovingDown = false;
                    break;
                default:
                    break;

            }
            // XLog.d(false, 1, "state: " + mState + ", isMovingDown: " + isMovingDown + ", ScrollY: " + getScrollY());
        }

        // 禁止控件本身的滑动.
        boolean isHandle = isMovingDown;
        if (!isMovingDown) {
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
    private void doMoveDown(MotionEvent event) {

        float deltaY = event.getY() - mStartPoint.y;
        XLog.d(false, 1, "getScrollY(): " + getScrollY() + ", deltaY: " +deltaY);

        // 不要越界
        // 最小是0， 最大是顶部图片的高度
        /***
         *
         * deltaY 只能分一半给顶部的imageview的top值
         * 同时这个一半还需要乘以一个阻尼系数SCROLL_RATIO
         *
         * 然后初始的mHeaderRect.top肯定是负的
         * 只有将这个mHeaderRect.top从负值变成0
         * 才能实现完整的将顶部图片拖出的全过程
         *
         */
        int maxMove = (int) (Math.abs(mHeaderRect.top) / 0.5f / SCROLL_RATIO);
        deltaY = deltaY < 0 ? 0 : (deltaY > maxMove ? maxMove : deltaY);

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
         *          --------------- <- mHeaderRect.top,
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
         *          --------------- <- mHeaderRect.bottom,       |                   |
         *                             original bottom of the    |                   |
         *                             top image                 |                   |
         *                                                       ---------------------- <-  mContentRect.bottom,
         *                                                                                  contentView的原始bottom值
         *  移动中:
         *
         *          --------------- <- mCurrentTop = mHeaderRect.top + headerMoveHeight,
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
         *  移动到最下面:          mCurrentTop = mHeaderRect.top + headerMoveHeight,
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
         *                        A                              |                   |      这个必须要大于mCurrentBottom
         *                        |                              |  visible area     |      不能再往下移动
         *                        mCurrentBottom                 |  of the           |      否则，盖不住顶部的图片
         *                        current bottom of the          |  scrollView       |
         *                        top image view                 |                   |
         *                        move down for  4               |                   |
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

    private void rollBackAnimation() {
        rollBackAnimation(true);
    }

    private void rollBackAnimation(boolean animate) {
        if (animate) {
            TranslateAnimation tranAnim = new TranslateAnimation(0, 0,
                    Math.abs(mHeaderRect.top - mCurrentTop), 0);
            tranAnim.setDuration(200);
            mHeader.startAnimation(tranAnim);
        }
        mHeader.layout(mHeaderRect.left, mHeaderRect.top, mHeaderRect.right, mHeaderRect.bottom);

        // 开启移动动画
        if (animate) {
            TranslateAnimation innerAnim = new TranslateAnimation(0, 0, mContentView.getTop(), mContentRect.top);
            innerAnim.setDuration(200);
            mContentView.startAnimation(innerAnim);
        }
        mContentView.layout(mContentRect.left, mContentRect.top, mContentRect.right, mContentRect.bottom);

        // 回调监听器
        if (mCurrentTop > mHeaderRect.top + TURN_DISTANCE && mOnTurnListener != null){
            mOnTurnListener.onTurn();
        }
    }

    /**
     * 是否需要开启动画
     */
    private boolean isNeedAnimation() {
        return !mContentRect.isEmpty() && isMovingDown && getScrollY() == 0;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
        XLog.d(true, 1, "   y: " + y);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        XLog.d(true, 1, "****   scrollY: " + scrollY);
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


    /**
     *
     *
     *
     [  170][dispatchTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][dispatchTouchEvent: 1237.0]
     [  149][onInterceptTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][y: 1237.0, onInterceptTouchEvent: false, isMovingDown: false]
     [  149][onInterceptTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][y: 1245.2056, onInterceptTouchEvent: false, isMovingDown: false]
     [  149][onInterceptTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][y: 1258.9221, onInterceptTouchEvent: false, isMovingDown: false]
     [  149][onInterceptTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][y: 1276.4095, onInterceptTouchEvent: true, isMovingDown: false]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1307.2354]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1331.1896]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1356.1062]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1384.5074]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1406.2168]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1427.0698]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1441.1742]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1462.5365]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1482.4337]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1499.0128]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1511.8423]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1526.7844]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1544.4924]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1558.532]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1577.6796]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1590.9167]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1603.6583]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1612.8148]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1625.1334]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1636.46]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1645.2489]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1650.9336]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1656.3372]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1659.4869]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1662.7518]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1666.001]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1668.633]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1671.5255]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1673.8933]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1676.5237]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1678.151]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1679.7804]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1681.4154]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1681.0]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1677.5437]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1666.473]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1646.0253]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1619.888]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1595.6139]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1572.7375]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1540.1705]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1520.7085]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1503.5487]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1485.2524]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1462.1482]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1445.871]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1429.3323]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1408.5646]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1394.7302]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1377.727]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1364.8948]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1351.8595]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1342.0438]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1332.0367]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1323.4875]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1314.3314]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1303.8839]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1289.7837]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1277.9354]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1269.6835]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1260.836]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1252.4127]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1247.202]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1240.2489]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1234.1461]
     [  128][onScrollChanged][net.jiawa.jobhunter.widgets.PullNestedScrollView][scrollY: 42]
     [  434][onOverScrolled][net.jiawa.jobhunter.widgets.PullNestedScrollView][****   scrollY: 42]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1226.7917]
     [  128][onScrollChanged][net.jiawa.jobhunter.widgets.PullNestedScrollView][scrollY: 50]
     [  434][onOverScrolled][net.jiawa.jobhunter.widgets.PullNestedScrollView][****   scrollY: 50]
     [  201][onTouchEvent][net.jiawa.jobhunter.widgets.PullNestedScrollView][Y: 1220.1495]
     *
     *
     */
}
