package net.masonliu.testmvp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AutoBreakTextView www = (AutoBreakTextView)findViewById(R.id.www);
        www.setText("测试测试测试水电费是的发生的sssssslajskdflkajsdjflsdkffewf");

    }

}
