package com.example.reto2.ui.productos;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.reto2.FormActivity;
import com.example.reto2.R;
import com.example.reto2.adaptadores.ProductoAdapter;
import com.example.reto2.casos_uso.CasoUsoProducto;
import com.example.reto2.datos.DBHelper;
import com.example.reto2.modelos.Producto;
import java.util.ArrayList;

public class ProductosFragment extends Fragment {
    private String TABLE_NAME = "PRODUCTOS";
    private CasoUsoProducto casoUsoProducto;
    private GridView gridView;
    private DBHelper dbHelper;
    private ArrayList<Producto> productos;
    SwipeRefreshLayout refreshLayout;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_productos, container,false);
        refreshLayout = root.findViewById(R.id.refresco);
        try{
            casoUsoProducto = new CasoUsoProducto();
            dbHelper = new DBHelper(getContext());
            Cursor cursor = dbHelper.getData(TABLE_NAME);
            productos = casoUsoProducto.llenarListaProductos(cursor);
            gridView = (GridView) root.findViewById(R.id.gridProductos);
            ProductoAdapter productoAdapter = new ProductoAdapter(root.getContext(), productos);
            gridView.setAdapter(productoAdapter);
        }catch (Exception e){
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

        //refresco por wipe
        refreshLayout = root.findViewById(R.id.refresco);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try{
                    casoUsoProducto = new CasoUsoProducto();
                    dbHelper = new DBHelper(getContext());
                    Cursor cursor = dbHelper.getData(TABLE_NAME);
                    productos = casoUsoProducto.llenarListaProductos(cursor);
                    gridView = (GridView) root.findViewById(R.id.gridProductos);
                    ProductoAdapter productoAdapter = new ProductoAdapter(root.getContext(), productos);
                    gridView.setAdapter(productoAdapter);
                }catch (Exception e){
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
                refreshLayout.setRefreshing(false);
            }

        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                Intent intent = new Intent(getContext(), FormActivity.class);
                intent.putExtra("name","PRODUCTOS");
                getActivity().startActivity(intent);
                //Toast.makeText(getContext(), "Hola Productos", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemFavoritos:
                Toast.makeText(getContext(), "puedes marcar un producto como favorito en la seccionde agregar, consultalo, marcalo como favorito y actializa", Toast.LENGTH_LONG).show();
                return true;
            case R.id.itemPregunta:
                Toast.makeText(getContext(), "Para refrescar la vista desliza hacia abajo", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}