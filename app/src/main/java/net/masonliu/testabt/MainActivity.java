package net.masonliu.testabt;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import net.masonliu.autobreaktextview.AutoBreakTextView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AutoBreakTextView www = (AutoBreakTextView)findViewById(R.id.www);
        www.setText("测试测试测试水电费是的发生的sssssslajskdflkajsdjflsdkffewf");

    }

}
