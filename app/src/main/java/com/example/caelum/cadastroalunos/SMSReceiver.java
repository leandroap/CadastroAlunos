package com.example.caelum.cadastroalunos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.example.caelum.cadastroalunos.dao.AlunoDAO;

/**
 * Created by android5519 on 12/01/16.
 */
public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        Object messages[] = (Object[]) bundle.get("pdus");
        byte[] message = (byte[]) messages[0];

        SmsMessage sms =  SmsMessage.createFromPdu(message);
        String telefone = sms.getDisplayOriginatingAddress();

        telefone = telefone.substring(telefone.length() - 9);
        Toast.makeText(context, "Chegou mensagem de " + telefone, Toast.LENGTH_LONG).show();

        AlunoDAO dao = new AlunoDAO(context);
        if (dao.isAluno(telefone)) {
            Toast.makeText(context, "Chegou mensagem de aluno cadastrado " + telefone, Toast.LENGTH_LONG).show();
            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
            mp.start();
        }
    }
}
