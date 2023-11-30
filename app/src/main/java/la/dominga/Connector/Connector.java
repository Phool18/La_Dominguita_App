package la.dominga.Connector;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Date;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

import la.dominga.utils.DateSerializer;
import la.dominga.utils.TimeSerializer;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Connector {

    public static final String baseUrlE = "http://10.0.2.2:8080";

    private static Retrofit retrofit;
    private static String token = "";
    private static UsuarioGateway usuarioApi;
    private static ClienteGateway clienteApi;
    private static PictureGateway fotoApi;
    private static TarjetaCreditoGateway tarjetaCreditoApi;
    private static CategoriaGateway categoriaApi;
    private static CarritoDeComprasGateway carritoDeComprasApi;
    private static ProductoGateway productoApi;

    static {
        initClient();
    }

    private static void initClient() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Time.class, new TimeSerializer())
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrlE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getClient())
                .build();
    }

    public static OkHttpClient getClient() {
        HttpLoggingInterceptor loggin = new HttpLoggingInterceptor();
        loggin.level(HttpLoggingInterceptor.Level.BODY);

        StethoInterceptor stetho = new StethoInterceptor();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.addInterceptor(loggin)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addNetworkInterceptor(stetho);
        return builder.build();
    }

    public static void setToken(String value) {
        token = value;
        initClient();
    }

    public static UsuarioGateway getUsuarioApi() {
        if (usuarioApi == null) {
            usuarioApi = retrofit.create(UsuarioGateway.class);
        }
        return usuarioApi;
    }

    public static ClienteGateway getClienteApi() {
        if (clienteApi == null) {
            clienteApi = retrofit.create(ClienteGateway.class);
        }
        return clienteApi;
    }

    public static PictureGateway getFotoApi() {
        if (fotoApi == null) {
            fotoApi = retrofit.create(PictureGateway.class);
        }
        return fotoApi;
    }

    // Agregados los nuevos APIs
    public static TarjetaCreditoGateway getTarjetaCreditoApi() {
        if (tarjetaCreditoApi == null) {
            tarjetaCreditoApi = retrofit.create(TarjetaCreditoGateway.class);
        }
        return tarjetaCreditoApi;
    }

    public static CategoriaGateway getCategoriaApi() {
        if (categoriaApi == null) {
            categoriaApi = retrofit.create(CategoriaGateway.class);
        }
        return categoriaApi;
    }

    public static CarritoDeComprasGateway getCarritoDeComprasApi() {
        if (carritoDeComprasApi == null) {
            carritoDeComprasApi = retrofit.create(CarritoDeComprasGateway.class);
        }
        return carritoDeComprasApi;
    }

    public static ProductoGateway getProductoApi() {
        if (productoApi == null) {
            productoApi = retrofit.create(ProductoGateway.class);
        }
        return productoApi;
    }




}
