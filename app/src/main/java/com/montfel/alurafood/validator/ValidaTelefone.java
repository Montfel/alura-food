package com.montfel.alurafood.validator;

import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.montfel.alurafood.formatador.FormataTelefone;

public class ValidaTelefone implements Validador {

    private final TextInputLayout til;
    private final EditText et;
    private final ValidacaoPadrao validadorPadrao;
    private final FormataTelefone formatador = new FormataTelefone();

    public ValidaTelefone(TextInputLayout til) {
        this.til = til;
        this.et = this.til.getEditText();
        this.validadorPadrao = new ValidacaoPadrao(this.til);
    }

    private boolean temDezOuOnzeDigitos(String telefone) {
        int tamanho = telefone.length();
        if (tamanho < 10 || tamanho > 11) {
            til.setError("Telefone deve ter entre 10 e 11 dígitos");
            return false;
        }
        return true;
    }

    @Override
    public boolean estaValido() {
        String texto = et.getText().toString();
        String telefoneSemFormatacao = formatador.remove(texto);
        if (!validadorPadrao.estaValido()) return false;
        if (!temDezOuOnzeDigitos(telefoneSemFormatacao)) return false;
        adicionaFormatacao(telefoneSemFormatacao);
        return true;
    }

    private void adicionaFormatacao(String telefone) {
        String telefoneFormatado = formatador.formata(telefone);
        et.setText(telefoneFormatado);
    }
}