package com.combo.pedidosviajantes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.combo.pedidosviajantes.report.CreatePDF;
import com.combo.pedidosviajantes.util.Connection;
import com.combo.pedidosviajantes.util.CreateHierachyDirectories;
import com.combo.pedidosviajantes.vo.ArticuloVO;
import com.combo.pedidosviajantes.vo.ClientVO;
import com.combo.pedidosviajantes.vo.CompanyVO;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Image;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends Activity {

	TabHost contenedorPestana;
	TabSpec pestana;
	private static Integer idButtonBorrar = 0;
	private static ArticuloVO artVO = new ArticuloVO();
	private static HashMap<Integer, ArticuloVO> MapArtVO = new HashMap<Integer, ArticuloVO>();
	private static CompanyVO company = new CompanyVO();
	private static ClientVO cliente = new ClientVO();
	private static String viajante = "";
	private static Bitmap bitmapHeader = null;
	private static String urlDelete = "";
	private static String urlDeleteFileSD = "";
	private static Connection cn = new Connection();

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		// crearDirectorio();
		contenedorPestana = (TabHost) findViewById(android.R.id.tabhost);
		contenedorPestana.setup();

		pestana = contenedorPestana.newTabSpec("pestana1");
		pestana.setContent(R.id.tab1);
		pestana.setIndicator(getResources().getString(R.string.tabCliente),
				getResources().getDrawable(android.R.drawable.ic_menu_help));
		contenedorPestana.addTab(pestana);

		pestana = contenedorPestana.newTabSpec("pestana2");
		pestana.setContent(R.id.tab2);
		pestana.setIndicator(getResources().getString(R.string.tabPedido),
				getResources().getDrawable(android.R.drawable.ic_btn_speak_now));
		contenedorPestana.addTab(pestana);

		pestana = contenedorPestana.newTabSpec("pestana3");
		pestana.setContent(R.id.tab3);
		pestana.setIndicator(getResources().getString(R.string.tabArticulos),
				getResources().getDrawable(android.R.drawable.ic_menu_agenda));
		contenedorPestana.addTab(pestana);

		contenedorPestana.setCurrentTab(0);
		contenedorPestana.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String nombrePestana) {
				// aqui implementariamos el código
				Log.i("User_", "Has pulsado :" + nombrePestana);
			}
		});
		Spinner spinner = (Spinner) findViewById(R.id.spinner);
		ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource(this,
				R.array.usuario_array, android.R.layout.simple_spinner_item);
		spinner_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(spinner_adapter);

		CheckBox satView = (CheckBox) findViewById(R.id.checkBox1);

		DatePicker myDatePicker = (DatePicker) findViewById(R.id.dpResult);

		myDatePicker.setEnabled(false);

		satView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				DatePicker myDatePicker = (DatePicker) findViewById(R.id.dpResult);
				if (isChecked) {
					myDatePicker.setEnabled(false);
				} else {
					myDatePicker.setEnabled(true);
				}
			}
		});

		final Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// Intent itemintent = new Intent(this,DialogArt.this);

				// DialogArt cdd = new DialogArt(MainActivity.this);
				// cdd.show();

				final DialogArt alertDialog2 = new DialogArt(MainActivity.this);
				alertDialog2.show();
				// Setting Positive "Yes" Btn
				alertDialog2.yes.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						artVO = new ArticuloVO();

						final TableLayout tl = (TableLayout) findViewById(R.id.tabla_cuerpo);
						TableRow tr1 = new TableRow(arg0.getContext());
						TableRow trOrigin = (TableRow) findViewById(R.id.tableRow);
						LayoutParams layoutParams = trOrigin.getLayoutParams();
						tr1.setLayoutParams(layoutParams);
						TextView tvDescrip = new TextView(arg0.getContext());
						TextView tvPrecio = new TextView(arg0.getContext());
						TextView tvCantidad = new TextView(arg0.getContext());
						TextView tvDTO = new TextView(arg0.getContext());

						TextView tvDescripOrigin = (TextView) findViewById(R.id.tv1);
						TextView tvPrecioOrigin = (TextView) findViewById(R.id.tv3);
						TextView tvCantidadOrigin = (TextView) findViewById(R.id.tv2);
						TextView tvDTOOrigin = (TextView) findViewById(R.id.tv4);
						TextView tvBorrarBotonOrigin = (TextView) findViewById(R.id.tv5);

						EditText et2 = (EditText) alertDialog2
								.findViewById(R.id.cantidad);
						LayoutParams paramsET2 = tvCantidadOrigin
								.getLayoutParams();
						tvCantidad.setLayoutParams(paramsET2);
						tvCantidad.setGravity(Gravity.CENTER);

						EditText et3 = (EditText) alertDialog2
								.findViewById(R.id.precio);
						// LayoutParams paramsET3 = et3.getLayoutParams();
						// et3.setLayoutParams(paramsET3);

						LayoutParams paramsET3 = tvPrecioOrigin
								.getLayoutParams();
						tvPrecio.setGravity(Gravity.CENTER);
						tvPrecio.setLayoutParams(paramsET3);

						EditText et4 = (EditText) alertDialog2
								.findViewById(R.id.dto);

						LayoutParams paramsET4 = tvDTOOrigin.getLayoutParams();
						tvDTO.setGravity(Gravity.CENTER);
						tvDTO.setLayoutParams(paramsET4);

						// LayoutParams paramsET4 = et4.getLayoutParams();
						// et4.setLayoutParams(paramsET4);

						if (!et2.getText().toString().trim().equals("")) {
							artVO.setCantidad(String.valueOf(et2.getText()));
							tvCantidad.setText(String.valueOf(artVO
									.getCantidad()));

						}

						if (!et3.getText().toString().trim().equals("")) {
							artVO.setPrecio(Double.parseDouble(String
									.valueOf(et3.getText())));
							tvPrecio.setText(String.valueOf(artVO.getPrecio()));
						}

						if (!et4.getText().toString().trim().equals("")) {
							artVO.setDto(Double.parseDouble(String.valueOf(et4
									.getText())));
							tvDTO.setText(String.valueOf(artVO.getDto()));
						}

						EditText et1 = (EditText) alertDialog2
								.findViewById(R.id.descripcion);

						LayoutParams paramsET1 = tvDescripOrigin
								.getLayoutParams();
						tvDescrip.setLayoutParams(paramsET1);

						if (!et1.getText().toString().trim().equals("")) {
							artVO.setDescripcion(String.valueOf(et1.getText()));
							tvDescrip.setText(artVO.getDescripcion());
						}
						// LinearLayout.LayoutParams params = new
						// LinearLayout.LayoutParams(
						// LayoutParams.MATCH_PARENT,
						// LayoutParams.MATCH_PARENT);
						final ImageButton btnBorrar = new ImageButton(arg0
								.getContext());

						Drawable d = getResources().getDrawable(
								R.drawable.delete);
						Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
						bitmap = redimensionarImagenMaximo(bitmap, 30, 30);
						Drawable dr = new BitmapDrawable(getResources(), bitmap);
						btnBorrar.setImageDrawable(dr);
						LayoutParams paramsET5 = tvBorrarBotonOrigin
								.getLayoutParams();
						tvBorrarBotonOrigin.setGravity(Gravity.CENTER);
						btnBorrar.setLayoutParams(paramsET5);
						btnBorrar.setId(idButtonBorrar);
						idButtonBorrar = idButtonBorrar + 1;
						tr1.addView(btnBorrar);
						tr1.addView(tvDescrip);
						tr1.addView(tvCantidad);
						tr1.addView(tvPrecio);
						tr1.addView(tvDTO);

						tl.addView(tr1);
						MapArtVO.put(idButtonBorrar, artVO);
						// System.out.println(artVO.toString());
						alertDialog2.dismiss();
						btnBorrar.setOnClickListener((new OnClickListener() {
							@Override
							public void onClick(View arg0) {

								int id = btnBorrar.getId();
								MapArtVO.remove(id);
								tl.removeView(arg0);
								for (int i = 2; i < tl.getChildCount(); i++) {

									TableRow row = (TableRow) tl.getChildAt(i);

									ImageButton btn = (ImageButton) row
											.getChildAt(0);
									// Do what you need to do.
									if (btn.getId() == id) {
										tl.removeView(row);

									}
								}
							}
						}));
					}

				});

			}
		});

		// buttonPrev.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//

		// });

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// MenuInflater inflater = getMenuInflater();
		// inflater.inflate(R.menu.main, menu);
		// return true;
		getMenuInflater().inflate(R.menu.main, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_new:
			Log.i("ActionBar", "Acciones");
			clearDatos();
			break;
		case R.id.menu_save:
			crearPDFCompleto();
			break;

		case R.id.menu_previsualizacion:
			Log.i("ActionBar", "Previsualizar");
			// Recogemos los datos introducidos en las pestañas
			Spinner spinner = (Spinner) findViewById(R.id.spinner);
			viajante = String.valueOf(spinner.getSelectedItem());
			cn = new Connection();
			// Nos conectamos al servidor
			boolean conectado = cn.ftpConnect("decoracionessantander.com",
					"pedidos", "Decosan2007", 21);
			if (!conectado) {
				CharSequence text = "fallo de conexion";
				int duration = Toast.LENGTH_SHORT;
				Toast.makeText(getApplicationContext(), text, duration);
			}
			Vector<String> vectorPDF = new Vector<String>();
			vectorPDF = crearPDFCompleto();
			// Guardamos el pdf en el servidor
			cn.ftpUpload(vectorPDF.get(0), vectorPDF.get(1), vectorPDF.get(2));

			// Obtenemos la url del fichero

			String urlFtp = cn.getUrlFile(viajante.toLowerCase(),
					vectorPDF.get(1));
			urlDelete = vectorPDF.get(2) + "/" + vectorPDF.get(1);
			urlDeleteFileSD = vectorPDF.get(0);

			// Mostramos el pdf en el WebView
			WebView webView = new WebView(this);

			webView.loadUrl("https://docs.google.com/gview?embedded=true&url=http://"
					+ urlFtp);
			webView.getSettings().setJavaScriptEnabled(true);
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setView(webView);
			dialog.setPositiveButton("Okay",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// Connection cn = new Connection();
							// StrictMode.ThreadPolicy policy = new
							// StrictMode.ThreadPolicy.Builder()
							// .permitAll().build();
							// StrictMode.setThreadPolicy(policy);
							// boolean conectado = cn.ftpConnect(
							// "decoracionessantander.com", "pedidos",
							// "Decosan2007", 21);

							cn.ftpRemoveFile(urlDelete);
							cn.ftpDisconnect();
							new File(urlDeleteFileSD).delete();
							dialog.dismiss();
						}
					});
			dialog.show();
			break;
		// return true;
		case R.id.menu_mail:
			spinner = (Spinner) findViewById(R.id.spinner);
			viajante = String.valueOf(spinner.getSelectedItem());
			vectorPDF = new Vector<String>();
			vectorPDF = crearPDFCompleto();
			String[] to = { "decosan@decoracionesSantander.com" };
			String[] cc = {};
			enviar(to, cc, "Pedido Creado por el viajante:"+ viajante,
					"Pedido creado para el cliente: " + cliente.getName()
							+ ", Creado por el viajante:" + viajante, vectorPDF.get(0));
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	private void enviar(String[] to, String[] cc, String asunto, String mensaje, String urlFile) {
		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		emailIntent.setData(Uri.parse("mailto:"));
		// String[] to = direccionesEmail;
		// String[] cc = copias;
		File root = Environment.getExternalStorageDirectory();
		File file = new File(urlFile);
		Uri uri = Uri.parse("file://" + file);
		emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
		emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
		emailIntent.putExtra(Intent.EXTRA_CC, cc);
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, asunto);
		emailIntent.putExtra(Intent.EXTRA_TEXT, mensaje);
		emailIntent.setType("message/rfc822");
		startActivity(Intent.createChooser(emailIntent, "Email "));
	}

	public Vector<String> crearPDFCompleto() {
		recogerDatos();

		// Recogemos el viajante seleccionado
		Spinner spinner = (Spinner) findViewById(R.id.spinner);
		String viajante = String.valueOf(spinner.getSelectedItem());
		CreateHierachyDirectories dir = new CreateHierachyDirectories();

		boolean carpetasCreadas = dir.crearDirectories(viajante);

		if (carpetasCreadas) {
			CharSequence text = "Directorios Creados";
			int duration = Toast.LENGTH_SHORT;
			Toast.makeText(getApplicationContext(), text, duration);
		}
		Drawable dLogoHeader = getResources()
				.getDrawable(R.drawable.logoheader);
		bitmapHeader = ((BitmapDrawable) dLogoHeader).getBitmap();
		Image image = null;

		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		bitmapHeader.getByteCount();
		bitmapHeader.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		try {
			image = Image.getInstance(byteArray);
		} catch (BadElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Generamos el pdf
		CreatePDF generatePDF = new CreatePDF();
		Vector<String> vectorPDF = new Vector<String>();
		vectorPDF = generatePDF.generatePDF(MapArtVO, company, cliente,
				viajante, image);

		return vectorPDF;
	}

	public Bitmap redimensionarImagenMaximo(Bitmap mBitmap, float newWidth,
			float newHeigth) {
		// Redimensionamos
		int width = mBitmap.getWidth();
		int height = mBitmap.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeigth) / height;
		// create a matrix for the manipulation
		Matrix matrix = new Matrix();
		// resize the bit map
		matrix.postScale(scaleWidth, scaleHeight);
		// recreate the new Bitmap
		return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
	}

	public void clearDatos() {

		CheckBox satView = (CheckBox) findViewById(R.id.checkBox1);
		satView.setChecked(true);

		CheckBox check105 = (CheckBox) findViewById(R.id.checkBox2);
		check105.setChecked(false);

		DatePicker myDatePicker = (DatePicker) findViewById(R.id.dpResult);

		myDatePicker.setEnabled(false);

		EditText etNombreCliente = (EditText) findViewById(R.id.etCliente);
		etNombreCliente.setText("");

		EditText etCodCliente = (EditText) findViewById(R.id.etCodCliente);
		etCodCliente.setText("");

		EditText etDireccion = (EditText) findViewById(R.id.etDomicilio);
		etDireccion.setText("");

		EditText etPoblacion = (EditText) findViewById(R.id.etPoblacion);
		etPoblacion.setText("");

		EditText etTelefonoCliente = (EditText) findViewById(R.id.etTelefono);
		etTelefonoCliente.setText("");

		EditText etCifCliente = (EditText) findViewById(R.id.etCIF);
		etCifCliente.setText("");

		final TableLayout tl = (TableLayout) findViewById(R.id.tabla_cuerpo);
		int i = 0;
		while (tl.getChildCount() > 2) {

			// Do what you need to do.

			tl.removeViewAt(tl.getChildCount() - 1);
			i++;
			MapArtVO.remove(i+2);

		}

	}

	public void recogerDatos() {
		company.setAddress("c/ La salve nº 22");
		company.setName("DECORACIONES SANTANDER S.A");
		company.setPhone("925-76-26-54");

		EditText etNombreCliente = (EditText) findViewById(R.id.etCliente);
		String nombreCliente = etNombreCliente.getText().toString();
		cliente.setName(nombreCliente);

		EditText etCodCliente = (EditText) findViewById(R.id.etCodCliente);
		String codCliente = etCodCliente.getText().toString();
		cliente.setCodCompany(codCliente);

		EditText etDireccion = (EditText) findViewById(R.id.etDomicilio);
		String direccionCliente = etDireccion.getText().toString();
		cliente.setCodCompany(direccionCliente);

		EditText etPoblacion = (EditText) findViewById(R.id.etPoblacion);
		String poblacionCliente = etPoblacion.getText().toString();
		cliente.setCodCompany(poblacionCliente);

		EditText etTelefonoCliente = (EditText) findViewById(R.id.etTelefono);
		String telefonoCliente = etTelefonoCliente.getText().toString();
		cliente.setCodCompany(telefonoCliente);

		EditText etCifCliente = (EditText) findViewById(R.id.etCIF);
		String cifCliente = etCifCliente.getText().toString();
		cliente.setCodCompany(cifCliente);

	}

	public void crearDirectorio() {
		File sdCard, directory = null;
		if (Environment.getExternalStorageState().equals("mounted")) {

			// Obtenemos el directorio de la memoria externa
			sdCard = Environment.getExternalStorageDirectory();

			// Clase que permite grabar texto en un archivo

			// instanciamos un onjeto File para crear un nuevo
			// directorio
			// la memoria externa

			directory = new File(sdCard.getAbsolutePath() + "/Download/");
			// se crea el nuevo directorio donde se cerara el
			// archivo
			directory.mkdirs();

			// creamos el archivo en el nuevo directorio creado

			// Convierte un stream de caracteres en un stream de
			// bytes

			Toast.makeText(getBaseContext(), "Carpeta Creada",
					Toast.LENGTH_SHORT).show();

		}

	}

}
