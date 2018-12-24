package com.example.eag.almacenamientointerno;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    EditText texto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texto = (EditText) findViewById(R.id.texto);

        //Recuperamos los archivos disponibles de nuestra app
        String archivos [] = fileList();


        if(archivoExiste(archivos, "bitacora.txt")){
            //Abrimos un flujo de lectura para leer el archivo
            try {
                InputStreamReader lectura = new InputStreamReader(openFileInput("bitacora.txt"));

                //Comenzamos a leer
                BufferedReader br = new BufferedReader(lectura);

                String texto_leido = "";
                String leida="";


                while((leida = br.readLine())!= null){
                    texto_leido = texto_leido + leida;
                }

                br.close();
                lectura.close();

                //Introducimos el texto del archivo en el EditText
                texto.setText(texto_leido);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private boolean archivoExiste(String archivos [], String nom_archivo){
        boolean existe = false;

        //Comprobamos si el archivo existe
        for (int i=0;i<archivos.length;i++){
            if(nom_archivo.equals(archivos[i])){
                existe = true;
            }
        }
        return existe;
    }

    public void guardar(View view)  {

        try {
            //Abrimos flujo de escritura
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput("bitacora.txt", Activity.MODE_PRIVATE));
            //Escribimos el contenido de nuestro EditText
            archivo.write(texto.getText().toString());
            //Limpiamos el buffer
            archivo.flush();

            //Cerramos el flujo de escritura
            archivo.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Notificamos
        Toast.makeText(this, "Archivo guardado correctamente",Toast.LENGTH_SHORT).show();
        //Cerramos la Activity
        finish();
    }
}
