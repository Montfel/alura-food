package com.montfel.alurafood.validator;

import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class ValidacaoPadrao implements Validador{

    private final TextInputLayout til;
    private final EditText et;

    public ValidacaoPadrao(TextInputLayout til) {
        this.til = til;
        this.et = this.til.getEditText();
    }

    private boolean estaVazio() {
        String texto = et.getText().toString();
        if (texto.isEmpty()) {
            til.setError("Campo obrigatório");
            return true;
        }
        return false;
    }

    private void removeErro() {
        til.setError(null);
        til.setErrorEnabled(false);
    }

    @Override
    public boolean estaValido() {
        if (estaVazio()) return false;
        removeErro();
        return true;
    }
}
