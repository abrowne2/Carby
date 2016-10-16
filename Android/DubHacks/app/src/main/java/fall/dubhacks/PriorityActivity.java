package fall.dubhacks;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Bum Mobile on 10/15/2016.
 */

public class PriorityActivity extends AppCompatActivity {
    ImageView carbonFootprint, cost, distance, time;
    ImageView box1, box2, box3, box4;

    ImageButton recycle, go;

    LinearLayout carbonC, costC, distanceC, timeC, box1C, box2C, box3C, box4C;
    EditText start, destination;

    Bitmap bitmap;
    Canvas canvas;
    Paint paint;

    ImageView draggedImage;

//    public char[] initialRow = {'a', 'b', 'c', 'd', };
//    public char[] priorities = {'z', 'z', 'z', 'z'};

    private char[] populated = {'a', 'b', 'c', 'd'};

    public char active = 'z';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priority);

        initAssets();
        renderSpaces();
    }

    private void initAssets() {
        recycle = (ImageButton) findViewById(R.id.recycle);
        recycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PriorityActivity.this, PriorityActivity.class);
                startActivity(i);
            }
        });


        go = (ImageButton) findViewById(R.id.goBTN);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //https://carby1.herokuapp.com/api?origin=&destination=&one=&two=&three=&four=
                String origin = start.getText().toString();
                String dest = destination.getText().toString();

                String one = charToString(populated[0]);
                check(one);
                String two = charToString(populated[1]);
                check(two);
                String three = charToString(populated[2]);
                check(three);
                String four = charToString(populated[3]);
                check(four);

                String request = ("https://carby1.herokuapp.com/api?" +
                        "origin="+origin+"" +
                        "&destination="+dest+"" +
                        "&one="+one+"" +
                        "&two="+two+"" +
                        "&three="+three+"" +
                        "&four="+four);

                URL url;
                HttpURLConnection urlConnection = null;
                try {
                  InputStream input = new URL(request).openStream();
                  Map<String, String> map = new Gson().fromJson(new InputStreamReader(input, "UTF-8"), new TypeToken<Map<String, String>>(){}.getType());
                }
                catch(Exception e){
                  System.out.println(e.toString());
                }
                Toast.makeText(getApplicationContext(), request, Toast.LENGTH_LONG).show();
            }
        });

        start = (EditText) findViewById(R.id.starting);
        destination = (EditText) findViewById(R.id.ending);

        //Listeners for our actual images to be moves
        carbonFootprint = (ImageView) findViewById(R.id.carbonFootprintIMG);
        carbonFootprint.setOnTouchListener(new MyTouchListener('a'));

        cost = (ImageView) findViewById(R.id.costIMG);
        cost.setOnTouchListener(new MyTouchListener('b'));

        distance = (ImageView) findViewById(R.id.distanceIMG);
        distance.setOnTouchListener(new MyTouchListener('c'));

        time = (ImageView) findViewById(R.id.timeIMG);
        time.setOnTouchListener(new MyTouchListener('d'));

        box1 = (ImageView) findViewById(R.id.box1);

        box2 = (ImageView) findViewById(R.id.box2);

        box3 = (ImageView) findViewById(R.id.box3);

        box4 = (ImageView) findViewById(R.id.box4);

        //listeners for all 8 containers possible to move to
        carbonC = (LinearLayout) findViewById(R.id.cfContainer);
        carbonC.setOnDragListener(new MyDragListener('a'));

        costC = (LinearLayout) findViewById(R.id.costContainer);
        costC.setOnDragListener(new MyDragListener('b'));

        distanceC = (LinearLayout) findViewById(R.id.distanceContainer);
        distanceC.setOnDragListener(new MyDragListener('c'));

        timeC = (LinearLayout) findViewById(R.id.timeContainer);
        timeC.setOnDragListener(new MyDragListener('d'));

        box1C = (LinearLayout) findViewById(R.id.box1Container);
        box1C.setOnDragListener(new MyDragListener('e'));

        box2C = (LinearLayout) findViewById(R.id.box2Container);
        box2C.setOnDragListener(new MyDragListener('f'));

        box3C = (LinearLayout) findViewById(R.id.box3Container);
        box3C.setOnDragListener(new MyDragListener('g'));

        box4C = (LinearLayout) findViewById(R.id.box4Container);
        box4C.setOnDragListener(new MyDragListener('h'));
    }

    private void renderSpaces(){
        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis() + 100;
        float x = 230.0f;
        float y = 720.0f;
// List of meta states found here:     developer.android.com/reference/android/view/KeyEvent.html#getMetaState()
        int metaState = 0;
        MotionEvent motionEvent = MotionEvent.obtain(
                downTime,
                eventTime,
                MotionEvent.ACTION_UP,
                x,
                y,
                metaState);
    }

    private void check(String s){
        if (s.equals("null")){
            //do something
        }
    }

    private String getPopulatedString(){
        return ("Populated fields are " + populated[0] + " " +populated[1] + " " +populated[2]+ " " +populated[3]);
    }

    private String charToString(char z){
        if(z == 'e'){
            return "green";
        }
        else if(z == 'f'){
            return "cost";
        }
        else if(z == 'g'){
            return "distance";
        }
        else if(z == 'h'){
            return "time";
        }
        else return "null";
    }

    // This defines your touch listener
    private final class MyTouchListener implements OnTouchListener {
        char act;
        public MyTouchListener (char act){
            this.act = act;
            active = act;
            try {
                //Toast.makeText(getApplicationContext(), " "+active+" ", Toast.LENGTH_SHORT).show();
            }catch (Exception e){

            }
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            active = this.act;
            //Toast.makeText(getApplicationContext(), " "+active+" ", Toast.LENGTH_SHORT).show();
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDrag(data, shadowBuilder, view, 0);
                //view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    class MyDragListener implements View.OnDragListener {
        Drawable enterShape = getResources().getDrawable(
                R.drawable.shape_droptarget);
        Drawable normalShape = getResources().getDrawable(R.drawable.shape);
        char prior;
        char box;
        boolean first = true;

        private MyDragListener (char dest){
            this.prior = active;
            this.box = dest;
        }

        @Override
        public boolean onDrag(View v, DragEvent event) {
            this.prior = active;
            //Toast.makeText(getApplicationContext(), (" Prior: "+prior+"  Box "+box), Toast.LENGTH_SHORT).show();
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    update();
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    LinearLayout container = (LinearLayout) v;
                    container.removeAllViews();
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);
                    setBackground(v);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //setBackground(v);
                    //first = false;
                    v.setBackgroundDrawable(normalShape);
                    setBackground(v);
                default:
                    break;
            }
            return true;
        }

        private void setBackground(View v){
            Drawable bg;
            switch(box){
                case 'e':
                    bg = getResources().getDrawable(R.drawable.dashbox1);
                    v.setBackgroundDrawable(bg);
                    break;
                case 'f':
                    bg = getResources().getDrawable(R.drawable.dashbox2);
                    v.setBackgroundDrawable(bg);
                    break;
                case 'g':
                    bg = getResources().getDrawable(R.drawable.dashbox3);
                    v.setBackgroundDrawable(bg);
                    break;
                case 'h':
                    bg = getResources().getDrawable(R.drawable.dashbox4);
                    v.setBackgroundDrawable(bg);
                    break;
//                default:
//                    return false;

            }
        }

        private void update(){
            for(int i = 0; i < populated.length; i++){
                if (populated[i] == prior){
                    for (int j = 0; j < i; j++){
                        if(populated[i] == populated[j]){
                            char temp = populated[i];
                            populated[i] = populated[j];
                            populated[j] = temp;
                            return;
                        }
                    }
                    for (int j = i+1; j < populated.length; j++){
                        if(populated[i] == populated[j]){
                            char temp = populated[i];
                            populated[i] = populated[j];
                            populated[j] = temp;
                            return;
                        }
                    }

                    //Toast.makeText(getApplicationContext(), " "+box+" ", Toast.LENGTH_SHORT).show();
                    populated[i] = box;
                }
            }
        }
    }
}

//    public boolean onTouch(View view, MotionEvent motionEvent) {
//        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//            ClipData clipData = ClipData.newPlainText("", "");
//            View.DragShadowBuilder dsb = new View.DragShadowBuilder(view);
//            view.startDrag(clipData, dsb, view, 0);
//            //view.setVisibility(View.INVISIBLE);
//            return true;
//        } else {
//            return false;
//        }
//    }

//    private boolean containsDragable;
//    @Override
//    public boolean onDrag(View view, DragEvent dragEvent) {
//        int dragAction = dragEvent.getAction();
//        View dragView = (View) dragEvent.getLocalState();
//        if (dragAction == DragEvent.ACTION_DRAG_EXITED) {
//            containsDragable = false;
//        } else if (dragAction == DragEvent.ACTION_DRAG_ENTERED) {
//            containsDragable = true;
//        } else if (dragAction == DragEvent.ACTION_DRAG_ENDED) {
//            if (dropEventNotHandled(dragEvent)) {
//                dragView.setVisibility(View.VISIBLE);
//            }
//        } else if (dragAction == DragEvent.ACTION_DROP && containsDragable) {
//            //checkForValidMove((ChessBoardSquareLayoutView) view, dragView);
//            dragView.setVisibility(View.VISIBLE);
//
//        }
//        return true;
//    }
//    private boolean dropEventNotHandled(DragEvent dragEvent) {
//        return !dragEvent.getResult();
//    }
//
//}
