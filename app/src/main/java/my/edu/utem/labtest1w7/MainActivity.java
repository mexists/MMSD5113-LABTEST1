package my.edu.utem.labtest1w7;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import my.edu.utem.labtest1w7.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    Executor executor;
    Handler handler;
    Bitmap bitmap = null;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater()); // setContentView(R.layout.activity_login);
        setContentView(binding.getRoot()); // setContentView(R.layout.activity_login);

        binding.btnCalculate.setOnClickListener(this::fnCalculate);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(), "Welcome mate!", Toast.LENGTH_SHORT).show();

        loadImageThread("https://lh3.googleusercontent.com/p/AF1QipPH8YIRrh-GRTaCH4z4Vz5cFjJsxLt845rqWy4-=w1080-h608-p-no-v0", binding.imageViewMenu1);
        loadImageThread("https://img-global.cpcdn.com/recipes/f220b4b19b7a5831/1360x964cq70/turkish-pizza-pide-foto-resep-utama.webp", binding.imageViewMenu2);

        binding.textMenu1.setText("Wooden Oven Pizza RM18");
        binding.textMenu2.setText("Turkish Pizza RM22");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public void fnCalculate(View view) {
        int textQty1 = Integer.parseInt(binding.editTextQty1.getText().toString());
        int textQty2 = Integer.parseInt(binding.editTextQty2.getText().toString());
        float menu1Price = 18, menu2Price = 22;
        float totalPrice = (textQty1 * menu1Price) + (textQty2 * menu2Price);
        float totalItemQty = textQty1 + textQty2;

        binding.textViewTotalItem.setText(binding.textViewTotalItem.getText() + " " + (int) totalItemQty);
        binding.textViewTotalPrice.setText(binding.textViewTotalPrice.getText() + " " + (int) totalPrice);
    }

    protected void loadImageThread(String url, ImageView imageBinding) {
        // using threads to load image from URL
        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());

        ConnectivityManager manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if(networkInfo != null  && networkInfo.isConnected()) {
            // the background task executor and handler is done within this checking
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL ImageURL = new URL(url);
                        HttpURLConnection connection = (HttpURLConnection) ImageURL.openConnection();
                        connection.setDoInput(true);
                        connection.connect();
                        InputStream inputStream = connection.getInputStream();
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Bitmap.Config.RGB_565;
                        bitmap = BitmapFactory.decodeStream(inputStream,null,options);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {  // this is to update main thread -- UI
                        @Override
                        public void run() {
                            imageBinding.setImageBitmap(bitmap);
                        }
                    });
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Network!! Please add data plan or connect to wifi network!", Toast.LENGTH_SHORT).show();
        }
    }
}