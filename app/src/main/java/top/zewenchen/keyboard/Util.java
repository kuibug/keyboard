package top.zewenchen.keyboard;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Util {
    public static boolean connetTest(String host) {
        boolean result = false;
        String cmd = "ping -c 1" + host;
        /*try {
*//*            Process ps = Runtime.getRuntime().exec(cmd);
            InputStream input = ps.getInputStream();
            //ps.wait();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null){
                buffer.append(line);
                //TODO 这里的网络判断仍然是错的
            }*//*
            int exe_code = Runtime.getRuntime().exec(cmd).waitFor();
            if (exe_code==0){
                result = true;
            }
            //Log.d("Log","Return ============" + buffer.toString());
            Log.d("Log", "Exe Code ============" + exe_code);
            //System.out.println("Return ============" + ps.toString());
        } catch (Exception e) {
            Log.e("NET", "网络连接发生异常");
            Log.e("ErrInf", e.toString());
            //result = false;
        }
        return result;*/

        Runtime runtime = Runtime.getRuntime();
        Process ipProcess = null;
        try {
            //-c 后边跟随的是重复的次数，-w后边跟随的是超时的时间，单位是秒，不是毫秒，要不然也不会anr了
            ipProcess = runtime.exec("ping -c 3 -w 3 "+host);
            int exitValue = ipProcess.waitFor();
            Log.i("ping isAvalible", "========Process exit code:" + exitValue);
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
}
