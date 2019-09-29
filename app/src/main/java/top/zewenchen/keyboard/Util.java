package top.zewenchen.keyboard;

import android.content.Context;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.IOException;

import androidx.annotation.NonNull;

 class Util {

     static boolean connectTest(String host) {
        //boolean result = false;
        String cmd = "ping -c 2 -w 2 " + host;
        Runtime runtime = Runtime.getRuntime();
        Process ipProcess = null;

        try {
            //-c 后边跟随的是重复的次数，-w后边跟随的是超时的时间，单位是秒，不是毫秒，要不然也不会anr了
            ipProcess = runtime.exec(cmd);
            // 0成功 1权限不足 2文件或目录不存在
            int exitValue = ipProcess.waitFor();
            Log.i("SeverIsAvailable-->ping", "========Process exit code:" + exitValue);
            return (exitValue == 0);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            //在结束的时候应该对资源进行回收
            if (ipProcess != null) {
                ipProcess.destroy();
            }
            runtime.gc();
        }
        return false;
    }

    /**
     * 隐藏软键盘(有输入框)
     *
     * @param context   activity的ID
     * @param mEditText 输入框的ID
     */
     static void hideSoftKeyboard(@NonNull Context context,
                                        @NonNull EditText mEditText) {
        InputMethodManager input_manger = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        input_manger.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }
}
