# OlEQrCode
> 欧了出行二维码扫描识别.


为欧了产品提供二维码扫描识别服务

## 使用方式
###  Gradle配置
```groovy
//app build.gradle配置
implementation 'com.ole.travel:qr:1.0.2'
//project build.gradle配置（用户名和密码根据自己账号密码配置）
allprojects {
    repositories {
        maven {
            url 'https://nexus.olafuwu.com/repository/maven-oleyc-android-releases/'
            credentials {
                username "xxxx"
                password "xxxx"
            }
        }
    }
}
```

### 权限配置
```xml
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-permission android:name="android.permission.FLASHLIGHT" />
```

### 功能使用
#### 跳转到扫描页面
```java
//使用startActivityForResult跳转即可
 startActivityForResult(new Intent(context, QrActivity.class), 0x2b);
```
#### 结果接收
```java
 @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 0x2b && resultCode == RESULT_OK) {
            tv.setText(data.getStringExtra(Constants.KEY_QR_RESULT));
        }
    }
```

#### 混淆配置
```groovy
-keep class com.ole.travel.qr.**{*;}

```


