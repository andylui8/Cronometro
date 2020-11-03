package com.example.cronometro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button iniciar, reiniciar;
    private TextView crono;
    private Thread tarea;
    private boolean isOn = true;
    private int horas = 0;
    private int minutos = 0;
    private int segundos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciar = (Button) findViewById(R.id.button);
        reiniciar = (Button) findViewById(R.id.button2);
        crono = (TextView) findViewById(R.id.textView);

    }

    public void cronometro()
    {
        tarea = new Thread(new Runnable() {
            @Override
            public void run() {
                long msInicial = System.currentTimeMillis();
                long msActuales;

                while (true) {
                    if (isOn == true){
                        msActuales = System.currentTimeMillis();

                    if (msActuales - msInicial > 50) {
                        ++segundos;

                        if (segundos == 60) {
                            segundos = 0;
                            ++minutos;
                            if (minutos == 60) {
                                minutos = 0;
                                ++horas;
                            }
                        }
                        msInicial = msActuales;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                crono.setText(String.format("%02d:%02d:%02d", horas, minutos, segundos));
                            }
                        });

                    }
                }
                }
            }

        });
    }

    public void Iniciar(View view) {
        if (iniciar.getText().equals("INICIAR")) {
            isOn=true;
            cronometro();
            tarea.start(); //Ejecuta el metodo RUN. Esto es el hilo.
            iniciar.setText("PARAR");
        } else {
            isOn=false;
            //tarea.stop(); no funciona, hace que se bloquee la aplicacion
            reiniciar.setEnabled(true);
            iniciar.setEnabled(false);
        }
    }

    public void Reiniciar(View view) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                crono.setText("00:00:00");
            }
        });

        //Al reiniciar el contador, reiniciamos el hilo

        isOn=false;
        cronometro();
        tarea.start();
        iniciar.setEnabled(true);
        iniciar.setText("INICIAR");
        reiniciar.setEnabled(false);
    }
}