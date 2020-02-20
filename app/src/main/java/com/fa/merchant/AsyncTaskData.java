package com.fa.merchant;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.fa.merchant.adapter.CustomAdapter;
import com.fa.merchant.modelclass.Category;
import com.fa.merchant.modelclass.Merchant;
import com.fa.merchant.modelclass.Product;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AsyncTaskData extends AsyncTask<String,Void, ArrayList<Product>> {
    ProgressDialog pd;
    WeakReference<CustomAdapter> customAdapter;
    Context context;

    public AsyncTaskData(CustomAdapter customAdapter, Context context) {
        this.customAdapter = new WeakReference<>(customAdapter);
        this.context = context;
    }

    @Override
    protected void onPreExecute() {

      }

    @Override
    protected ArrayList<Product> doInBackground(String... url) {

        ArrayList<Product> products = null;
        String json = loadJsonFromApi(url[0]);
        products = deserialisasiJSON(json);
        return products;
    }

    @Override
    protected void onPostExecute(ArrayList<Product> products) {
        CustomAdapter adapter = customAdapter.get();
        adapter.adddata(products);
        adapter.notifyDataSetChanged();

        Toast.makeText(context, String.valueOf(adapter.getItemCount()), Toast.LENGTH_SHORT).show();
    }

    public String loadJsonDataFromRaw(InputStream isParam){
        String json = null;
        try{
            InputStream is = isParam;
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);

            StringBuffer sb = new StringBuffer();
            String eachLine;
            while ((eachLine = reader.readLine()) != null){
                sb.append(eachLine);
            }

            json = sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }

    private ArrayList<Product> deserialisasiJSON(String jsonparam) {
        ArrayList<Product> data = new ArrayList<>();
        try {
            JSONObject jsonawal = new JSONObject(jsonparam);
            JSONArray jsonData = jsonawal.getJSONArray("data");

            for (int i = 0; i < jsonData.length(); i++) {
                JSONObject jsonObject = jsonData.getJSONObject(i);

                Gson gson = new Gson();
                Product product = gson.fromJson(jsonObject.toString(), Product.class);
                data.add(product);

                /*int pId = jsonObject.getInt("productId");
                String pName = jsonObject.getString("productName");
                String pSlug = jsonObject.getString("productSlug");
                int pQty = jsonObject.getInt("productQty");
                String pImage = jsonObject.getString("productImage");

                final int img = context.getResources().getIdentifier(pImage, "drawable", context.getPackageName());

                //merchant

                JSONObject jsonMerchant = jsonObject.getJSONObject("merchant");
                int mId = jsonMerchant.getInt("merchantId");
                String mName = jsonMerchant.getString("merchantName");
                String mSlug = jsonMerchant.getString("merchantSlug");

                Merchant merchant = new Merchant(mId, mName, mSlug);

                // Category

                JSONObject jsonCategory = jsonObject.getJSONObject("category");
                int cId = jsonCategory.getInt("categoryId");
                String cName = jsonCategory.getString("categoryName");

                Category productCategory = new Category(cId, cName);

                Product product = new Product(pId, pQty, pName, pSlug, pImage, merchant, productCategory);
                data.add(product);*/
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    private String loadJsonFromApi(String urlParam) {
        String json = null;

        //network calls
        try {
            HttpURLConnection connection = null;
            URL url = new URL(urlParam);
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                BufferedReader reader = new BufferedReader(isr);

                StringBuffer sb = new StringBuffer();
                String eachLine;
                while ((eachLine = reader.readLine()) != null){
                    sb.append(eachLine);
                }

                json = sb.toString();

            }catch (Exception ex){
                ex.printStackTrace();
            }finally {
                if (connection != null){
                    connection.disconnect();
                }
            }
        }catch (MalformedURLException ex){
            ex.printStackTrace();
            return ex.getMessage();
        }


        return json;
    }
}


