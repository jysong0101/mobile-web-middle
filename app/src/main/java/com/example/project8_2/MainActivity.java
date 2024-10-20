package com.example.project8_2;

import com.example.project8_2.myPictureView;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnPrev, btnNext;
    myPictureView myPicture;
    int curNum = 0;
    ArrayList<File> imageFiles = new ArrayList<>();
    String imageFname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("간단 이미지 뷰어");

        // 버튼 및 뷰 초기화
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        myPicture = findViewById(R.id.myPictureView1);

        // 권한 요청
        requestPermissionsIfNecessary();
    }

    // 권한 요청 다이얼로그 표시 메서드
    private void requestPermissionsIfNecessary() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13 이상
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO
            }, MODE_PRIVATE);
        } else {
            // Android 13 미만
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, MODE_PRIVATE);
        }
    }

    // 권한 요청 결과 처리 메서드
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MODE_PRIVATE) {
            boolean allGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }

            if (allGranted) {
                // 권한이 허용된 경우 이미지 로드
                loadImages();
            } else {
                // 권한이 거부된 경우 메시지 출력
                Toast.makeText(this, "외부 저장소 권한이 필요합니다. 권한을 허용해주세요.", Toast.LENGTH_LONG).show();
            }
        }
    }

    // 이미지를 불러오는 메서드
    private void loadImages() {
        // SD 카드의 Project8_2 폴더에서 파일 읽어오기
        File pictureDir = new File("/storage/emulated/0/Pictures");
        File[] files = pictureDir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    imageFiles.add(file);
                }
            }
        }

        // 이미지 파일이 있는 경우 첫 번째 이미지 설정
        if (imageFiles.size() > 0) {
            imageFname = imageFiles.get(curNum).getAbsolutePath();
            myPicture.setImagePath(imageFname);
        }

        // 이전 버튼 클릭
        btnPrev.setOnClickListener(v -> {
            if (curNum > 0) {
                curNum--;
                imageFname = imageFiles.get(curNum).getAbsolutePath();
                myPicture.setImagePath(imageFname);
            }
        });

        // 다음 버튼 클릭
        btnNext.setOnClickListener(v -> {
            if (curNum < imageFiles.size() - 1) {
                curNum++;
                imageFname = imageFiles.get(curNum).getAbsolutePath();
                myPicture.setImagePath(imageFname);
            }
        });
    }
}
