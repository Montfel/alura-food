package com.montfel.alurafood.ui.actitvity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.montfel.alurafood.R;
import com.montfel.alurafood.formatador.FormataTelefone;
import com.montfel.alurafood.validator.ValidaCPF;
import com.montfel.alurafood.validator.ValidaEmail;
import com.montfel.alurafood.validator.ValidaTelefone;
import com.montfel.alurafood.validator.ValidacaoPadrao;
import com.montfel.alurafood.validator.Validador;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.stella.format.CPFFormatter;

public class FormularioCadastroActivity extends AppCompatActivity {

    public static final String ERRO_FORMATACAO_CPF = "erro formatação cpf";
    private TextInputLayout tilNomeCompleto, tilCPF, tilTelefone, tilEmail, tilSenha;
    private Button botao;
    private final List<Validador> validadores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_cadastro);

        inicializaCampos();
    }

    private void inicializaCampos() {
        tilNomeCompleto = findViewById(R.id.nomeCompleto);
        adicionaValidacaoPadrao(tilNomeCompleto);

        configuraCampoCPF();
        configuraCampoTelefone();
        configuraCampoEmail();

        tilSenha = findViewById(R.id.senha);
        adicionaValidacaoPadrao(tilSenha);

        configuraBotaoCadastrar();
    }

    private void configuraBotaoCadastrar() {
        botao = findViewById(R.id.botao);
        botao.setOnClickListener(view -> {
            boolean formularioEstaValido = true;
            for (Validador validador : validadores) {
                if (!validador.estaValido()) {
                    formularioEstaValido = false;
                }
            }
            if (formularioEstaValido) {
                Toast.makeText(
                        this,
                        "Cadastro realizado com sucesso!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configuraCampoEmail() {
        tilEmail = findViewById(R.id.email);
        EditText etEmail = tilEmail.getEditText();
        ValidaEmail validador = new ValidaEmail(tilEmail);
        validadores.add(validador);
        etEmail.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                validador.estaValido();
            }
        } );
    }

    private void configuraCampoTelefone() {
        tilTelefone = findViewById(R.id.telefone);
        EditText etTelefone = tilTelefone.getEditText();
        ValidaTelefone validador = new ValidaTelefone(tilTelefone);
        validadores.add(validador);
        FormataTelefone formatador = new FormataTelefone();
        etTelefone.setOnFocusChangeListener((view, hasFocus) -> {
            String telefone = etTelefone.getText().toString();
            if (hasFocus) {
                etTelefone.setText(formatador.remove(telefone));
            } else {
                validador.estaValido();
            }
        });
    }

    private void configuraCampoCPF() {
        tilCPF = findViewById(R.id.CPF);
        EditText etCPF = tilCPF.getEditText();
        CPFFormatter cpfFormatter = new CPFFormatter();
        ValidaCPF validador = new ValidaCPF(tilCPF);
        validadores.add(validador);
        etCPF.setOnFocusChangeListener((view, hasFocus) -> {
                    if (hasFocus) {
                        retiraFormatacao(etCPF, cpfFormatter);
                    } else {
                        validador.estaValido();
                    }
                }
        );
    }

    private void retiraFormatacao(EditText etCPF, CPFFormatter cpfFormatter) {
        String cpf = etCPF.getText().toString();
        try {
            String cpfSemFormato = cpfFormatter.unformat(cpf);
            etCPF.setText(cpfSemFormato);
        } catch (IllegalArgumentException e) {
            Log.e(ERRO_FORMATACAO_CPF, e.getMessage());
        }
    }

    private void adicionaValidacaoPadrao(TextInputLayout til) {
        EditText campo = til.getEditText();
        ValidacaoPadrao validador = new ValidacaoPadrao(til);
        validadores.add(validador);
        campo.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus)
                validador.estaValido();
        });
    }
}