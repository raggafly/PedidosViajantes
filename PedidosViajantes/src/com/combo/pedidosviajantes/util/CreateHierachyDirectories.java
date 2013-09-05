package com.combo.pedidosviajantes.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class CreateHierachyDirectories {
	public Boolean crearDirectories(String nameBussinesTraveler) {
		boolean sdDisponible = false;
		boolean sdAccesoEscritura = false;

		// Comprobamos el estado de la memoria externa (tarjeta SD)
		String estado = Environment.getExternalStorageState();
		boolean creadas = false;

		File folder = new File(Environment.getExternalStorageDirectory()+"/PedidosViajantes/"+nameBussinesTraveler+"/");
		boolean success = false;
		if (!folder.exists()) {
		    success = folder.mkdir();
		}
		if (success) {
		    // Do something on success
			creadas =true;
		} 
		
		
		
//		if (estado.equals(Environment.MEDIA_MOUNTED)) {
//			sdDisponible = true;
//			sdAccesoEscritura = true;
//			File wallpaperDirectory = new File(
//					Environment.getExternalStorageDirectory()+"/PedidosViajantes/"+nameBussinesTraveler+"/");
//			// have the object build the directory structure, if needed.
//			creadas = wallpaperDirectory.mkdirs();
//		} else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
//			sdDisponible = true;
//			sdAccesoEscritura = false;
//		} else {
//			sdDisponible = false;
//			sdAccesoEscritura = false;
//		}

		return creadas;
	}
}
