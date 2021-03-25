package com.example.kettu.webselain;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class MainActivity extends Activity {
    WebView www;
    EditText editUrl;
    ArrayList<String> history = new ArrayList<String>();
    ListIterator historyIterator = history.listIterator();
    private int historyIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        www = findViewById(R.id.webView);
        www.setWebViewClient(new WebViewClient());
        www.getSettings().setJavaScriptEnabled(true);
        //www.loadUrl("http://www.yle.fi");
        editUrl = (EditText) findViewById(R.id.txtUrl);

        editUrl.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) { // Wait for user to finish the typing
                String urli = "" + editUrl.getText();

                if (urli.contains("http") == false) {
                    urli = "http://" + editUrl.getText();
                }

                if (urli.contains("index.html")){
                    System.out.println("file:///android_asset/index.html");
                    urli = "file:///android_asset/index.html";
                }
                else{
                    System.out.println("Go to page: " + urli);
                }

                // Go
                loadUrl(urli);

                // Update the list iterator
                //historyIterator = history.listIterator();

                // Hide keyboard
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                return false;
            }
        });
    }


    public void refresh(View v){
        System.out.println("Refresh!");
        www.reload();
    }


    public void goBäk(View v){

        if (www.canGoBack()) {
            www.goBack();
            editUrl.setText(www.getUrl());
        }

/*        int i = 0;
        // This seems a bit stupid
        if (historyIterator.hasPrevious()){
            String derp = (String)historyIterator.previous();
            System.out.println("this is now = " + derp);

            if (historyIterator.hasPrevious()){
                derp = (String)historyIterator.previous();
                System.out.println("this you need to go = " + derp);
                loadUrl(derp);
            }
        }*/
    }

    // load and store the address for later on
    private void loadUrl(String address){
        System.out.println("Load url = " + address);
        try{
            // Add and move to next
//            historyIterator.add(address);

            // Indicate the position and go
            editUrl.setText(address);
            www.loadUrl(address);
        }
        catch (NoSuchElementException e){
            e.printStackTrace();
        }
    }

    public void goForward(View v){
/*        if (historyIterator.hasNext()){
            String derp = (String)historyIterator.next();
            System.out.println("forward to url = " + derp);

            loadUrl(derp);
        }*/
        if (www.canGoForward()) {
            www.goForward();
            editUrl.setText(www.getUrl());
        }

    }

    public void shoutOut(View v){
        System.out.println("SHOUT OUT!");
        www.evaluateJavascript("javascript:shoutOut()",null);
    }

    public void shoutClear(View v){
        System.out.println("SHOUT CLEAAAAR!");
        www.evaluateJavascript("javascript:initialize()",null);
    }
}
