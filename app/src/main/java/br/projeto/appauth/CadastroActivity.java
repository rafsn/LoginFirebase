package br.projeto.appauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.projeto.appauth.databinding.ActivityCadastroBinding;

public class CadastroActivity extends AppCompatActivity {

    private ActivityCadastroBinding binding;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        iniciaToolbar();

        mAuth = FirebaseAuth.getInstance();

        binding.btnCriarConta.setOnClickListener(view -> validaDados());
    }
    private void iniciaToolbar(){
        Toolbar toolbar = binding.toolbar;
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    private void validaDados() {
        String email = binding.editEmail.getText().toString().trim();
        String senha = binding.editSenha.getText().toString().trim();

        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {

                binding.progressBar.setVisibility(View.VISIBLE);

                criarContaFirebase(email, senha);
            } else {
                Toast.makeText(this, "Informe uma senha", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Informe seu e-mail", Toast.LENGTH_SHORT).show();
        }
    }

    private void criarContaFirebase(String email, String senha) {
        mAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                finish();
                startActivity(new Intent(this, MainActivity.class));
            } else {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "Senha ou Email inválida", Toast.LENGTH_SHORT).show();
            }
        });
    }
}