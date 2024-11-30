package my.edu.utem.labtest1w7;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import my.edu.utem.labtest1w7.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(this::fnLogin);

        String[] dineModes = new String[4];
        dineModes[0] = "Dine In";
        dineModes[1] = "Take Away";
        dineModes[2] = "Delivery";
        dineModes[3] = "Pick Up";

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dineModes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerDine.setAdapter(adapter);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    public void fnLogin(View view) {
        String dineMode = (String) binding.spinnerDine.getSelectedItem();
        String username = binding.editUsername.getText().toString();
        String password = binding.editPassword.getText().toString();

        if(username.isEmpty() || password.isEmpty()){
            binding.editUsername.setError("Username is required");
            binding.editPassword.setError("Password is required");
            Toast.makeText(getApplicationContext(), "Username and Password is required", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "Dine Mode: " + dineMode, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        startActivity(intent);
    }
}