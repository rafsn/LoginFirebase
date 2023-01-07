package br.projeto.appauth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import br.projeto.appauth.databinding.ActivityRecuperaContaBinding;

public class RecuperaContaActivity extends AppCompatActivity {

    private ActivityRecuperaContaBinding binding;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecuperaContaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        iniciaToolbar();
        binding.btnRecuperaConta.setOnClickListener(view -> {
            validaDados();
        });
    }

    private void iniciaToolbar(){
        Toolbar toolbar = binding.toolbar;
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    private void validaDados() {
        String email = binding.editEmail.getText().toString().trim();

        if (!email.isEmpty()) {
            binding.progressBar.setVisibility(View.VISIBLE);
            recuperaContaFirebase(email);
        } else {
            Toast.makeText(this, "Informe seu e-mail", Toast.LENGTH_SHORT).show();
        }
    }

    private void recuperaContaFirebase(String email) {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Já pode verificar seu e-mail", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Senha ou Email inválida", Toast.LENGTH_SHORT).show();
            }
                binding.progressBar.setVisibility(View.GONE);
        });
    }
}