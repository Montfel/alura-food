package com.montfel.alurafood.validator;

import android.util.Log;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import br.com.caelum.stella.format.CPFFormatter;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

public class ValidaCPF implements Validador {

    private final TextInputLayout til;
    private final EditText et;
    private final ValidacaoPadrao validadorPadrao;
    private final CPFFormatter cpfFormatter = new CPFFormatter();
    public static final String ERRO_FORMATACAO_CPF = "erro formatação cpf";

    public ValidaCPF(TextInputLayout til) {
        this.til = til;
        this.et = this.til.getEditText();
        this.validadorPadrao = new ValidacaoPadrao(this.til);
    }

    private boolean isCPFValido(String cpf) {
        try {
            CPFValidator cpfValidator = new CPFValidator();
            cpfValidator.assertValid(cpf);
        } catch (InvalidStateException e) {
            til.setError("CPF inválido");
            return false;
        }
        return true;
    }

    private boolean temOnzeDigitos(String cpf) {
        if (cpf.length() != 11) {
            til.setError("O CPF precisa ter 11 dígitos");
            return false;
        }
        return true;
    }

    @Override
    public boolean estaValido() {
        String cpf = getCPF();
        String cpfSemFormato = cpf;
        if (!validadorPadrao.estaValido()) return false;
        try {
            cpfSemFormato = cpfFormatter.unformat(cpf);
        } catch (IllegalArgumentException e) {
            Log.e(ERRO_FORMATACAO_CPF, e.getMessage());
        }
        if (!temOnzeDigitos(cpfSemFormato)) return false;
        if (!isCPFValido(cpfSemFormato)) return false;
        adicionaFormatacao(cpfSemFormato);
        return true;
    }

    private void adicionaFormatacao(String cpf) {
        String cpfFormatado = cpfFormatter.format(cpf);
        et.setText(cpfFormatado);
    }

    private String getCPF() {
        return et.getText().toString();
    }
}