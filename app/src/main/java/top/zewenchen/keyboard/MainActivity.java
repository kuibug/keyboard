package top.zewenchen.keyboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private static String host = "192.168.137.1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editText = (EditText) findViewById(R.id.IP_Add);
        editText.setText(host);
        //Toast.makeText(MainActivity.this, "无线已开启", Toast.LENGTH_SHORT).show();

    }

    public void connect(View view) {
        final EditText editText = (EditText) findViewById(R.id.IP_Add);
        String add_str = editText.getText().toString();
        editText.setText(add_str);

        //TODO 修改地址之前先Ping一下是否联通，联通再设置
        host = add_str;//修改地址
        Log.d("IP_ADD", add_str);

        //失焦并并关闭键盘
        editText.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        //TODO 修改后谈一个弹窗出来，表明是否成功
        Toast.makeText(MainActivity.this, "正在连接到"+host, Toast.LENGTH_SHORT).show();
    }

    /**
     * 向预览中添加文字
     *
     * @param btn_str 所按按钮对应的文字
     */
//    private void addText(View views, String btn_str) {
    private void addText(String btn_str) {
        final TextView textView = (TextView) findViewById(R.id.textView);
        final String pre = "按键预览";

        String text = textView.getText().toString();
        if (text.equals(pre))
            textView.setText(btn_str);//如果是提示字符，清空，添加
        else textView.append(", " + btn_str);//非提示字符，添加
        //TODO 当文字过多时也清空一下前半段
    }

    /**
     * 利用 TCP 发送数据的方法
     */
    protected void connectServerWithTCPSocket(String btn_str) {
        //TODO 有空做一下SSL加密，局域网是信任的但不安全
        Socket socket;
        try {// 创建一个Socket对象，并指定服务端的IP及端口号
            socket = new Socket(host, 5021);
            Log.d("IP_ADD", host);

            // 获取Socket的OutputStream对象用于发送数据。
            OutputStream outputStream = socket.getOutputStream();

            // 把数据写入到OuputStream对象中
            outputStream.write(btn_str.getBytes());

            // 发送读取的数据到服务端
            outputStream.flush();

            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("TCP 传输错误",e.toString());
        }

    }

    /**
     * 公用的click方法
     *
     * @param str 要发送的字符
     */
    public void click(final String str) {
        final TextView textView = (TextView) findViewById(R.id.textView);

        addText(str);//添加预览

        //新建线程发送TCP数据，socket不能运行再主线程上
        new Thread(new Runnable() {
            @Override
            public void run() {
                connectServerWithTCPSocket(str);
            }
        }).start();
    }

    //  按键 mode
    public void click_mode(View view) {
        //Button btn = (Button) findViewById(R.id.btn_mode);
        //click(btn.getText().toString());
        final TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("按键预览");
    }

    // 按键 /
    public void click_mult(View view) {
        Button btn = (Button) findViewById(R.id.btn_mult);
        click(btn.getText().toString());
    }

    // 按键 *
    public void click_div(View view) {
        Button btn = (Button) findViewById(R.id.btn_div);
        click(btn.getText().toString());
    }

    // 按键 -
    public void click_sub(View view) {
        Button btn = (Button) findViewById(R.id.btn_sub);
        click(btn.getText().toString());
    }

    // 按键 +
    public void click_add(View view) {
        Button btn = (Button) findViewById(R.id.btn_add);
        click(btn.getText().toString());
    }

    // 按键 enter
    public void click_enter(View view) {
        Button btn = (Button) findViewById(R.id.btn_enter);
        click(btn.getText().toString());
    }

    // 按键 .
    public void click_dot(View view) {
        Button btn = (Button) findViewById(R.id.btn_dot);
        click(btn.getText().toString());
    }
    // 按键 DEL
    public void click_del(View view) {
        Button btn = (Button) findViewById(R.id.btn_del);
        click(btn.getText().toString());
    }

    // 按键 0
    public void click_0(View view) {
        Button btn = (Button) findViewById(R.id.btn_0);
        click(btn.getText().toString());
    }

    // 按键 1
    public void click_1(View view) {
        Button btn = (Button) findViewById(R.id.btn_1);
        click(btn.getText().toString());
    }

    // 按键 2
    public void click_2(View view) {
        Button btn = (Button) findViewById(R.id.btn_2);
        click(btn.getText().toString());
    }

    // 按键 3
    public void click_3(View view) {
        Button btn = (Button) findViewById(R.id.btn_3);
        click(btn.getText().toString());
    }

    // 按键 4
    public void click_4(View view) {
        Button btn = (Button) findViewById(R.id.btn_4);
        click(btn.getText().toString());
    }

    // 按键 5
    public void click_5(View view) {
        Button btn = (Button) findViewById(R.id.btn_5);
        click(btn.getText().toString());
    }

    // 按键 6
    public void click_6(View view) {
        Button btn = (Button) findViewById(R.id.btn_6);
        click(btn.getText().toString());
    }

    // 按键 7
    public void click_7(View view) {
        Button btn = (Button) findViewById(R.id.btn_7);
        click(btn.getText().toString());
    }

    // 按键 8
    public void click_8(View view) {
        Button btn = (Button) findViewById(R.id.btn_8);
        click(btn.getText().toString());
    }

    // 按键 9
    public void click_9(View view) {
        Button btn = (Button) findViewById(R.id.btn_9);
        click(btn.getText().toString());
    }

}
