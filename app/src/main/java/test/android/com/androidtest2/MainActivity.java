package test.android.com.androidtest2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    String GROUP_KEY_WORK_EMAIL = "com.android.example.WORK_EMAIL";
    private String CHANNEL_ID = "10";
    private CharSequence CHANNEL_NAME =" 1001";
    private String CHANNEL_ID_2 = "12";
    private int REQUEST_CODE_NOTIFY = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.iv_image).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
//                sendNotification();
                Intent intent = new Intent(MainActivity.this,ActivityB.class);
                PendingIntent pendingResult = PendingIntent.getActivity(MainActivity.this, REQUEST_CODE_NOTIFY, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                showNotification(MainActivity.this,pendingResult,0);
            }
        });
    }


    int countNumber = 1;
    /**
     * 测试Notification
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendNotification(){
//        Intent resultIntent = new Intent(this, MainActivity.class);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addParentStack(MainActivity.class);
//        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent resultPendingIntent =
//                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//        builder.setSmallIcon(R.mipmap.icon_archive_count);
//        builder.setContentTitle("Notification Title");
//        builder.setContentText("Notification content");
//        builder.setContentIntent(resultPendingIntent);
//        builder.setAutoCancel(true);
//        NotificationManager mNotificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.notify((int) System.currentTimeMillis(), builder.build());



//        getNotificationManager().createNotificationChannelGroup(new NotificationChannelGroup(GROUP_ID, "GROUP_CHANNEL"));
//
//        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
//        channel.setGroup(GROUP_ID);
//        channel.setShowBadge(true);
//        channel.setLightColor(Color.RED);
//        channel.enableLights(true);
//        getNotificationManager().createNotificationChannel(channel);
//
//        NotificationChannel channel2 = new NotificationChannel(CHANNEL_ID_2, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
//        channel2.setGroup(GROUP_ID);
//        channel2.setShowBadge(true);
//        channel2.setLightColor(Color.RED);
//        channel2.enableLights(true);
//        getNotificationManager().createNotificationChannel(channel2);


        Intent intent = new Intent(MainActivity.this,ActivityB.class);
//        intent.setType("image/*");
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
        PendingIntent pendingResult = PendingIntent.getActivity(this, REQUEST_CODE_NOTIFY, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setContentTitle("notification title_9")
                .setContentText("notification content_9")
                .setPriority(1000)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.icon_archive_count)
                .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                .setNumber(3)
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setContentIntent(pendingResult)
                .setOngoing(true);

        NotificationCompat.Builder mBuilder1 = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID_2)
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setContentTitle("notification title_10")
                .setContentText("notification content_10")
                .setSmallIcon(R.mipmap.icon_arrow_right)
                 .setPriority(1000)
                .setAutoCancel(true)
                .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                .setNumber(15)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingResult)
                .setOngoing(true);


        getNotificationManager().notify((int) System.currentTimeMillis(), mBuilder.build());
//        getNotificationManager().notify(10, mBuilder1.build());



    }
    private void showNotification(Context context,PendingIntent pendingIntent, int count) {


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setContentTitle("notification title_9")
                .setContentText("notification content_9")
                .setPriority(1000)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.icon_archive_count)
                .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                .setNumber(3)
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setContentIntent(pendingIntent)
                .setOngoing(true);
        getNotificationManager().notify((int) System.currentTimeMillis(), mBuilder.build());


//        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
//        mBuilder.setContentTitle("哈啊哈")//设置通知栏标题
//                .setContentText("djdjdjddddddd")//设置通知栏显示内容
//                .setContentIntent(pendingIntent) //设置通知栏点击意图
////                .setNumber(number) //设置通知集合的数量
//                .setTicker("dfdjfkdjfkdjkfjdkjfdlkj") //通知首次出现在通知栏，带上升动画效果的
//                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
////                .setPriority(Notification.PRIORITY_MAX) //设置该通知优先级
//                .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
////                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
//                .setDefaults(Notification.DEFAULT_ALL)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
//                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
//                .setSmallIcon(R.mipmap.ic_launcher);//设置通知小ICON
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
//            mBuilder.setPriority(Notification.PRIORITY_MAX); //设置该通知优先级
//        }
//        Notification notification = mBuilder.build();
////        BadgeUtil.setBadgeCount(notification, context, BadgeUtil.getCount() + 1);
//        mNotificationManager.notify((int) (Math.random() * Integer.MAX_VALUE), notification);
    }


    private NotificationManager getNotificationManager(){
        return (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
    }
}
