# ProgressBar
### Author: Harlan1994
### E-mail: harlan1994@yeah.net
### JitPack: [JitPack Link](https://jitpack.io)

## Descriptions
This is a simple custom ProgressBar. Two classes are included, one is Horizontal styled and another is Circular styled.

![alt text](https://github.com/Harlan1994/Progress/blob/master/gif/CustomProgressBar.gif "How it looks.")

### Instalation:

##### Add it in your root build.gradle at the end of repositories:

~~~~
allprojects {
   repositories {
	...
	maven { url "https://jitpack.io" }
  }
}
~~~~

##### in your app build.gradle add

~~~~
compile 'com.github.Harlan1994:Progress:v1.0.0'
~~~~


### Usage

#### Example
There 7 custom attrs can be used whitch can be seen in values. In your xml files, use the following attributes.

##### Like this to create a Horizontal styled progressbar:
~~~~
<com.harlan.progressbar.LineProgressBar
        ...
        harlan:textSize="14sp"
        harlan:reachColor="#0ff"
        harlan:unreachColor="#0b0"
        harlan:reachHeight="5dp"
        harlan:unreachHeight="3dp"
        harlan:textColor="@color/colorAccent"
        harlan:textOffset="10dp"
        .../>
~~~~

##### Or like this to create a Circular styled progressbar:
~~~~
<com.harlan.progressbar.CircleProgressBar
	...
        harlan:radius="45dp"
        harlan:reachHeight="6dp"
        harlan:unreachHeight="4dp"
        harlan:textColor="#0ff"
        harlan:textSize="15sp"
        .../>
~~~~
#### In your Activities, just use setProgress(int) method to change its progress.

##### Like this:

~~~~                
public class MainActivity extends AppCompatActivity {

    private LineProgressBar lineProgressBar1, lineProgressBar2;
    private CircleProgressBar circleProgressBar1, circleProgressBar2;

    private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ...
        lineProgressBar1 = (LineProgressBar) findViewById(R.id.line1);
        lineProgressBar2 = (LineProgressBar) findViewById(R.id.line2);
        circleProgressBar1 = (CircleProgressBar) findViewById(R.id.circle1);
        circleProgressBar2 = (CircleProgressBar) findViewById(R.id.circle2);
        mHandler.sendEmptyMessageDelayed(1, 200);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progress++;
            lineProgressBar1.setProgress(progress);
            lineProgressBar2.setProgress(progress);
            circleProgressBar1.setProgress(progress);
            circleProgressBar2.setProgress(progress);
            mHandler.sendEmptyMessageDelayed(1, 200);
        }
    };
}
~~~~

#### See more details, you can download this and run by yourself.

### Thanks
~~~~
http://www.imooc.com/
[@GcsSloop](http://weibo.com/GcsSloop)
~~~~

#### License

~~~~
Copyright 2015 Harlan1994

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
~~~~
