package top.zewenchen.keyboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private static String host = "192.168.109.114";
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.IP_Add);
        editText.setText(host);
        //Toast.makeText(MainActivity.this, "无线已开启", Toast.LENGTH_SHORT).show();

    }

    //连接指定IP
    public void connect(View view) {

        //处理失焦并关闭软键盘
        Util.hideSoftKeyboard(MainActivity.this, editText);
        editText.clearFocus();
        Log.d("TEST", "********关闭键盘");


        final String add_str = editText.getText().toString();
        //editText.setText(add_str);

        // 修改地址之前先Ping一下是否联通，联通再设置
        if (Util.connectTest(add_str)) {
            host = add_str;//修改地址
            Log.d("IP_ADD", add_str);

            //修改IP将文本清空
            final TextView textView = findViewById(R.id.textView);
            textView.setText("按键预览");
            Toast.makeText(MainActivity.this, "正在连接到" + add_str, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "目标主机拒绝连接或不可用" + add_str, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 向预览中添加文字
     *
     * @param btn_str 所按按钮对应的文字
     */
//    private void addText(View views, String btn_str) {
    private void addText(String btn_str) {
        final TextView textView = findViewById(R.id.textView);
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
        // TODO 局域网是信任的但不安全
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
            Log.e("TCP 传输错误", e.toString());
        }

    }


    public void clicked(View view) {
        Button btn = (Button) view;
        final String text = btn.getText().toString();
        addText(text);//添加预览

        //新建线程发送TCP数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                connectServerWithTCPSocket(text);
            }
        }).start();
    }
}
