package com.example.reto2.datos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.reto2.R;
import com.example.reto2.adaptadores.ProductoAdapter;
import com.example.reto2.adaptadores.ServicioAdapter;

import com.example.reto2.casos_uso.CasoUsoProducto;
import com.example.reto2.casos_uso.CasoUsoServicio;
import com.example.reto2.modelos.Producto;
import com.example.reto2.modelos.Servicio;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ApiOracle {
    private RequestQueue queue;
    private Context context;
    private CasoUsoProducto casoUsoProducto;
    private CasoUsoServicio casoUsoServicio;
    private String urlServicios = "https://g3587aeb78a14ca-creatinganapex.adb.sa-santiago-1.oraclecloudapps.com/ords/admin/servicio/servicio";
    private String urlProductos = "https://g3587aeb78a14ca-creatinganapex.adb.sa-santiago-1.oraclecloudapps.com/ords/admin/producto/producto";

    public ApiOracle(Context context) {
        this.queue = Volley.newRequestQueue(context);
        this.context = context;
        casoUsoProducto = new CasoUsoProducto();
        casoUsoServicio = new CasoUsoServicio();
    }


    //POST
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void insertar(String tabla,String name, String description, String price, ImageView imageView, String favorite){
        JSONObject json = new JSONObject();
        String image;
        System.out.println("la tabla es: "+tabla);
        System.out.println("la comprovacion es: "+tabla.equals("PRODUCTOS"));
        if (tabla.equals("PRODUCTOS")) {
            image = casoUsoProducto.imageViewToString(imageView);
        }else{
            image = casoUsoServicio.imageViewToString(imageView);
        }
        try {
            json.put("name", name);
            json.put("description", description);
            json.put("price", price);
            json.put("image",image);
            json.put("favorite",favorite);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest;
        if (tabla.equals("PRODUCTOS")) {
            jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlProductos, json, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }else{
            jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlServicios, json, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
        queue.add(jsonObjectRequest);
    }

    //GETAll
    public void getAll(String tabla,GridView gridView, ProgressBar progressBar){
        JsonObjectRequest jsonObjectRequest;
        if(tabla.equals("PRODUCTOS")) {
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlProductos, null, new Response.Listener<JSONObject>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("items");
                        ArrayList<Producto> list = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            byte[] image = casoUsoProducto.stringToByte(jsonObject.getString("image"));

                            Producto producto = new Producto(
                                    jsonObject.getInt("id"),
                                    jsonObject.getString("name"),
                                    jsonObject.getString("description"),
                                    jsonObject.getString("price"),
                                    image,
                                    jsonObject.getString("favorite")
                            );
                            list.add(producto);
                        }
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                        ProductoAdapter productoAdapter = new ProductoAdapter(context, list);
                        gridView.setAdapter(productoAdapter);

                    } catch (JSONException error) {
                        error.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }else{
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlServicios, null, new Response.Listener<JSONObject>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("items");
                        ArrayList<Servicio> list = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            byte[] image = casoUsoServicio.stringToByte(jsonObject.getString("image"));

                            Servicio servicio = new Servicio(
                                    jsonObject.getInt("id"),
                                    jsonObject.getString("name"),
                                    jsonObject.getString("description"),
                                    jsonObject.getString("price"),
                                    image,
                                    jsonObject.getString("favorite")
                            );
                            list.add(servicio);
                        }
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                        ServicioAdapter servicioAdapter = new ServicioAdapter(context, list);
                        gridView.setAdapter(servicioAdapter);

                    } catch (JSONException error) {
                        error.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
        queue.add(jsonObjectRequest);
    }
    //delete
    public void delete(String tabla,String id){
        JsonObjectRequest jsonObjectRequest;
        if(tabla.equals("PRODUCTOS")) {
            jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, urlProductos + "/" + id, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }else{
            jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, urlServicios + "/" + id, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
        queue.add(jsonObjectRequest);
    }

    //GetById
    public void getById(String tabla, String id, ImageView imageView, EditText name, EditText description, TextView price, ImageButton favorite){
        JsonObjectRequest jsonObjectRequest;
        System.out.println("se ejecuto y tabla es: "+tabla);
        if(tabla.equals("PRODUCTOS")) {
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlProductos + "/" + id, null, new Response.Listener<JSONObject>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("items");
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        byte[] image = casoUsoProducto.stringToByte(jsonObject.getString("image"));

                        Producto producto = new Producto(
                                jsonObject.getInt("id"),
                                jsonObject.getString("name"),
                                jsonObject.getString("description"),
                                jsonObject.getString("price"),
                                image,
                                jsonObject.getString("favorite")
                        );
                        byte[] imagePro = producto.getImage();
                        Bitmap bitmap = BitmapFactory.decodeByteArray(imagePro, 0, imagePro.length);
                        imageView.setImageBitmap(bitmap);
                        name.setText(producto.getName());
                        description.setText(producto.getDescription());
                        price.setText(producto.getPrice());
                        if(producto.getFavorite().equals("1")) {
                            favorite.setImageResource(R.drawable.btn_star_on);
                        }else{
                            favorite.setImageResource(R.drawable.ic_baseline_star_24);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }else{
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlServicios + "/" + id, null, new Response.Listener<JSONObject>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("items");
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        byte[] image = casoUsoServicio.stringToByte(jsonObject.getString("image"));

                        Servicio servicio = new Servicio(
                                jsonObject.getInt("id"),
                                jsonObject.getString("name"),
                                jsonObject.getString("description"),
                                jsonObject.getString("price"),
                                image,
                                jsonObject.getString("favorite")
                        );
                        byte[] imageSer = servicio.getImage();
                        Bitmap bitmap = BitmapFactory.decodeByteArray(imageSer, 0, imageSer.length);
                        imageView.setImageBitmap(bitmap);
                        name.setText(servicio.getName());
                        description.setText(servicio.getDescription());
                        price.setText(servicio.getPrice());
                        if(servicio.getFavorite().equals("1")) {
                            favorite.setImageResource(R.drawable.btn_star_on);
                        }else{
                            favorite.setImageResource(R.drawable.ic_baseline_star_24);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }

        queue.add(jsonObjectRequest);
    }

    //PUT

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void update(String tabla, String id, String name, String description, String price, ImageView imageView,String favorite) {
        JSONObject json = new JSONObject();
            String image = casoUsoProducto.imageViewToString(imageView);
            try {
                json.put("id", id);
                json.put("name", name);
                json.put("description", description);
                json.put("price", price);
                json.put("image", image);
                json.put("favorite", favorite);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        JsonObjectRequest jsonObjectRequest;
            if(tabla.equals("PRODUCTOS")) {
                jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, urlProductos, json, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
            }else{
                jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, urlServicios, json, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
            }
            queue.add(jsonObjectRequest);
    }
}
