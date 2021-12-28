package com.montfel.alurafood.validator;

import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class ValidaEmail implements Validador {

    private final TextInputLayout til;
    private final EditText et;
    private final ValidacaoPadrao validadorPadrao;

    public ValidaEmail(TextInputLayout til) {
        this.til = til;
        this.et = this.til.getEditText();
        this.validadorPadrao = new ValidacaoPadrao(this.til);
    }

    private boolean isEmailValido(String email) {
        if (email.matches(".+@.+\\..+")) {
            return true;
        }
        til.setError("E-mail inválido");
        return false;
    }

    @Override
    public boolean estaValido() {
        String texto = et.getText().toString();
        if (!validadorPadrao.estaValido()) return false;
        if (!isEmailValido(texto)) return false;
        return true;
    }
}
