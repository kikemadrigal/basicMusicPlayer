package es.tipolisto.basicMusicPlayer;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by kikemadrigal 2020
 */

public class HandlerMusic {
    private Activity activity;
    private  String[] arrayRutasPermitidas={"Audios","audios","sonidos","sonido","Sonidos","Sonido","musica","Musica","Music"};


    //Constructor
    public HandlerMusic(Activity activity){
        this.activity =activity;
    }


    // Método que te devuelve un arrayList de filesobtenidos a través del ContentResolver de android
    public ArrayList<File> getMusic(){
        ArrayList<File> files=new ArrayList<File>();
        String variableAyudaParaSaberElTipoArchivo="";

        ContentResolver contentResolver=this.activity.getContentResolver();
        Uri songUri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor=contentResolver.query(songUri,null,null,null,null);

        if(songCursor !=null && songCursor.moveToFirst()){
            int songTitle=songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist=songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songLocation=songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            do{
                String currentLocation=songCursor.getString(songLocation);
                variableAyudaParaSaberElTipoArchivo=currentLocation.substring(currentLocation.length()-3,currentLocation.length());
                //Miramos si contiene alguna de las rutas permitidas
                for(String ruta: arrayRutasPermitidas){
                    //containts devuelve si coincide el string con mayusculas y minusculas
                   if(currentLocation.contains(ruta)){
                       //Si la contiene miramos si es mp3
                       if(variableAyudaParaSaberElTipoArchivo.equalsIgnoreCase("mp3") || variableAyudaParaSaberElTipoArchivo.equalsIgnoreCase("wav")){
                           // Si se cumple la añadimos
                           files.add(new File(currentLocation));
                           Log.d("LOG", "Añadida la ruta: "+currentLocation);
                       }
                   }
                }
            }while(songCursor.moveToNext());
        }
        return files;
    }



    // Método que te devuelve un arrayList de Strings con las rutas a través del ContentResolver de android
    public ArrayList<String> getTitlesMusic(){
        ArrayList<String> files=new ArrayList<String>();
        String variableAyudaParaSaberElTipoArchivo;
        boolean variableParaSaberSiContieneAudioEnLaRuta=false;
        ContentResolver contentResolver=this.activity.getContentResolver();
        Uri songUri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor=contentResolver.query(songUri,null,null,null,null);

        if(songCursor !=null && songCursor.moveToFirst()){
            int songTitle=songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist=songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songLocation=songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            do{
                //String currentTitle=songCursor.getString(songTitle);
                //String currentArtist=songCursor.getString(songArtist);
                String currentLocation=songCursor.getString(songLocation);
                variableAyudaParaSaberElTipoArchivo=currentLocation.substring(currentLocation.length()-3,currentLocation.length());
                //variableParaSaberSiContieneAudioEnLaRuta=currentLocation.contains("Audios");
                for(String ruta: arrayRutasPermitidas){
                    if(currentLocation.contains(ruta)){
                        if(variableAyudaParaSaberElTipoArchivo.equalsIgnoreCase("mp3") || variableAyudaParaSaberElTipoArchivo.equalsIgnoreCase("wav")) {
                            files.add(currentLocation);
                        }
                    }
                }
            }while(songCursor.moveToNext());
        }
        return files;
    }
}
