package cn.yzl.reactnative.phonepicker;

/**
 * Created by YZL on 2018/6/2.
 */

public interface ErrorCode {
    /**
     * 成功
     */
    int SUCCESS = 0;
    /**
     * 权限被拒绝
     */
    int PERMISSON_FAIL = 1;
    /**
     * 权限被拒绝,并不再询问
     */
    int PERMISSON_NOASK = 2;
    /**
     * 其他
     */
    int OTHER = 2;
}
