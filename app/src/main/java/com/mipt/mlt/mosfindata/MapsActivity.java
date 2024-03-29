package com.mipt.mlt.mosfindata;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolygonOptions;
import com.mipt.mlt.mosfindata.model.Category;
import com.mipt.mlt.mosfindata.model.PointData;
import com.mipt.mlt.mosfindata.ui.CategoryRecyclerAdapter;
import com.mipt.mlt.mosfindata.utils.JsonConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.apptik.widget.MultiSlider;

import static com.mipt.mlt.mosfindata.CategoryActivity.titles;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        MultiSlider.OnThumbValueChangeListener {

    @BindView(R.id.user_menu_view)
    LinearLayout userMenuView;

    @BindView(R.id.range_slider1)
    MultiSlider rangeSlider1;
    @BindView(R.id.range_slider2)
    MultiSlider rangeSlider2;
    @BindView(R.id.range_slider3)
    MultiSlider rangeSlider3;
    @BindView(R.id.range_slider4)
    MultiSlider rangeSlider4;

    @BindView(R.id.range_slider1_label)
    TextView rangeSlider1Label;
    @BindView(R.id.range_slider2_label)
    TextView rangeSlider2Label;
    @BindView(R.id.range_slider3_label)
    TextView rangeSlider3Label;
    @BindView(R.id.range_slider4_label)
    TextView rangeSlider4Label;

    @BindView(R.id.no_metrics)
    Button noMetricsButton;
    @BindView(R.id.metrics1_button)
    Button metrics1Button;
    @BindView(R.id.metrics2_button)
    Button metrics2Button;

    @BindView(R.id.toolbar_title_tv)
    TextView toolbarTitleTV;

    private GoogleMap googleMap;
    private PolygonOptions moscowBounds;

    private boolean isUserMenuShown = false;
    private boolean isUserMenuAnimated = true;
    private float animationHeight;

    private LinearLayout bottomNavigationPanel;

    private float startY = 0;
    private float panelHeight = 0;

    private int type = 0;

    private List<Integer> alphas;

    private final static String BORDER_JSON = "{ \"type\": \"Polygon\", \"coordinates\": [[[55.77927874237433,37.84322767990104],[55.778592526549154,37.84462243255609],[55.77795467284381,37.84704715347287],[55.776294873156004,37.84552366271971],[55.774973649464336,37.84382851058962],[55.77289334187904,37.84391418783564],[55.768550738653005,37.846317447113016],[55.76686343458331,37.84363531481935],[55.76440180720182,37.843571018524145],[55.75819499728007,37.84329173016357],[55.751600710845025,37.84292727249141],[55.74660817427406,37.84301333334347],[55.744391785830025,37.84732089157861],[55.740622748468134,37.84221930688473],[55.73407401835115,37.84146910317988],[55.72780194917509,37.84084647619628],[55.72272788617889,37.84026710317986],[55.7201393113315,37.8400364094009],[55.71716889859165,37.839644798950175],[55.71457062344018,37.83915150265495],[55.71037307955042,37.83707033862305],[55.70897958676407,37.8365124815063],[55.708449516873,37.83886203840631],[55.70673211534047,37.83988121963496],[55.70617628514688,37.83701122689044],[55.70542658916031,37.83474204896536],[55.698740768334346,37.83104035714714],[55.69511713278607,37.83021387434383],[55.69287499998757,37.8300098492126],[55.69075054404055,37.83022956018825],[55.684365313737736,37.837433896881095],[55.684399031442375,37.834335294998134],[55.68322046194683,37.83269581481932],[55.67833412137516,37.83497022222902],[55.67309563109611,37.83660056085204],[55.667540943627706,37.83876784921257],[55.66265330728847,37.84033396826166],[55.658595913451684,37.84048439419552],[55.658011379182014,37.84172365809626],[55.65718421361702,37.84167546166982],[55.656692522500194,37.84186054798121],[55.65583403439536,37.84127717446508],[55.65512110934118,37.839449255966024],[55.652481602966866,37.837812911392206],[55.64945439741911,37.83492662963869],[55.64514635383355,37.82836059259028],[55.64164525404918,37.82370407937614],[55.64063945438103,37.82532934926601],[55.63911252200289,37.82334717200843],[55.638071017368354,37.8177601058349],[55.628396778180196,37.802803679901125],[55.62389214883108,37.795808473541264],[55.618176787113114,37.785229656127804],[55.61648732827083,37.781538920654214],[55.61080561558188,37.771198645507816],[55.60546131975676,37.76199366403194],[55.60003191338934,37.75096482278432],[55.595974098813414,37.741201698425286],[55.59260844548467,37.733305296325625],[55.58947628182735,37.732189587341225],[55.58980119515209,37.726439201110786],[55.58378592661682,37.71129042858883],[55.580333884716545,37.70238576455686],[55.577051539112475,37.692729695770254],[55.57314305747472,37.69136709129325],[55.573865903630946,37.68464007940672],[55.57245525441111,37.67914677252193],[55.57130640373292,37.67031421326822],[55.57200552729745,37.65444353755947],[55.571847581125304,37.65102631333917],[55.5726015592777,37.64470684902951],[55.57187808955344,37.640865871582015],[55.57285667884823,37.63796903170772],[55.57413299797594,37.61809911904904],[55.57457352116457,37.60654997094726],[55.57149549362097,37.60148999009699],[55.56914674352011,37.601665681243865],[55.56851835249801,37.59682027711486],[55.57584108947529,37.59103073541259],[55.5771909325276,37.58706092324821],[55.57919687305002,37.5785634576721],[55.58047920470594,37.57287982371902],[55.579345709877416,37.570488617856945],[55.58066756260294,37.570801078681924],[55.58140891945835,37.56930433995056],[55.583912536799815,37.55892942327882],[55.58816610060177,37.541334592590324],[55.590727974877495,37.530266434278474],[55.58947406777632,37.52928338186263],[55.58963970324457,37.52748893840787],[55.5903598365055,37.5279340938568],[55.59165424488218,37.52650697616572],[55.59375611788519,37.51766620370477],[55.59589454565571,37.51110064285275],[55.59888385068756,37.50556440475464],[55.60352474716076,37.49997463093565],[55.60831084899122,37.49434194177244],[55.60891797864558,37.49226030422974],[55.61001102760222,37.49102636640931],[55.6100717107301,37.48877861441801],[55.612761894716215,37.48896365642161],[55.615937656323815,37.485543809509274],[55.622664412924465,37.47784057406612],[55.62971872057379,37.469450523803694],[55.636686137068224,37.46136117196654],[55.637356764530786,37.4562542294769],[55.64092204550342,37.45629709524535],[55.64646804191628,37.44994555819704],[55.65388118987271,37.44114798147584],[55.65946182657096,37.434448158737126],[55.66127521308183,37.428300592559765],[55.66397993614937,37.4302533544921],[55.667592665848794,37.42740483728779],[55.66790643119401,37.42631584919726],[55.668633314337384,37.425631880283206],[55.66960273067753,37.42507665740191],[55.67052283521834,37.42512492593376],[55.67563848665467,37.42161526174725],[55.68070497101994,37.417633528774154],[55.68218706386873,37.41509889879602],[55.68410853488231,37.41453574998467],[55.68540557584731,37.4148685740661],[55.68783611346694,37.414052989410315],[55.69043629353955,37.41252950793452],[55.69210903236244,37.411027677246096],[55.69409047139518,37.40858160052493],[55.696265714044294,37.40579220104977],[55.700015995357674,37.40067118786897],[55.70005803954154,37.39945547101394],[55.700675306977814,37.398225666942565],[55.70142511978896,37.397740164634655],[55.70350631016315,37.39462339280698],[55.70432434947448,37.3946554894104],[55.708698018429146,37.38858293121334],[55.71095754681208,37.38657668385311],[55.71284141681235,37.38165219308471],[55.71550050969125,37.38384065872189],[55.718401663685896,37.38251006613164],[55.72386359590511,37.379645218246466],[55.72922789125503,37.377123693115244],[55.731354421549,37.375790611431086],[55.731291606878195,37.37289247268862],[55.73233061460218,37.37289111968224],[55.73536044945085,37.37370109919732],[55.74052363501198,37.371222642852736],[55.74476000818395,37.36916230422973],[55.748875066441265,37.36842225263974],[55.7511018351588,37.36824010052489],[55.76232469923392,37.368567418647785],[55.76297191407176,37.36765019178771],[55.7638610777772,37.367891679222055],[55.76439149058835,37.36605336575744],[55.76581710929733,37.366532480881666],[55.76726497644937,37.36860651008029],[55.76856763214722,37.368684975771814],[55.77028150841747,37.36623143615338],[55.77091464749484,37.366426237122035],[55.77171711357751,37.36593439258286],[55.77245114643331,37.3683839310436],[55.77299995400157,37.36881981216431],[55.78219126224613,37.369098931213344],[55.78429546180488,37.36933489682004],[55.78596427164725,37.36978543914797],[55.78731691213963,37.37044525528714],[55.788161713178326,37.37041842591852],[55.79059631249865,37.3676879298629],[55.79208781696245,37.37354050265498],[55.79390709952012,37.37598664816287],[55.796627354759394,37.37823928039551],[55.80041078292988,37.38189786903378],[55.8036620818716,37.384698150787315],[55.80681629936088,37.38679294279862],[55.807021756089,37.38609823743442],[55.8076743528207,37.38582462565616],[55.80874985946135,37.386116973209404],[55.80934198849855,37.38572267525483],[55.80999454617376,37.386393201049835],[55.810018649757055,37.38795939682009],[55.813486412421646,37.38909643914796],[55.819859544349434,37.391156185180655],[55.82417822811241,37.3923361666565],[55.82792275755201,37.39314076850889],[55.83104308061352,37.39355243302337],[55.83216661284123,37.391987382911594],[55.83370485902745,37.392303908706594],[55.83451096136158,37.39486815078735],[55.83638877399578,37.394760962951715],[55.83929273513374,37.39411746295165],[55.84115814142083,37.393216470886216],[55.84357283689132,37.391736134918226],[55.84586684309255,37.390866929885874],[55.84816071335841,37.39089894708255],[55.852880929796605,37.39340923544309],[55.8598934560672,37.39701391268923],[55.86456313517681,37.40100499734494],[55.868206974029945,37.40585492855831],[55.870704480765774,37.410039362426765],[55.871856659584346,37.410951407394386],[55.8719471651236,37.4125930132141],[55.87603092691485,37.424287486816404],[55.87806371461185,37.42362234127808],[55.877689734832046,37.42666924337762],[55.878298861136365,37.42965181613148],[55.87817521136336,37.43151861177054],[55.87896828818063,37.4338574761962],[55.881428745457164,37.44070223544307],[55.882653158961766,37.44137836372367],[55.883961957253675,37.44175408459469],[55.88304549557238,37.44855627774044],[55.88323854103934,37.45041242192072],[55.88321449305057,37.45604511639405],[55.88349215849231,37.46235403173825],[55.88386598802799,37.46571217591857],[55.88443278002902,37.46907032009886],[55.885614633752816,37.474338369033816],[55.886953236580766,37.47839669409561],[55.88884125711965,37.48215299886393],[55.890551331808055,37.4831781085973],[55.890546808577184,37.48445585186769],[55.88879217518232,37.48592565211485],[55.88984723195758,37.48928372750852],[55.891028909608394,37.492437809509234],[55.89322342976871,37.49356423939511],[55.89477881640086,37.4943742175674],[55.89418197769941,37.49983248843756],[55.8951766861053,37.50232960055532],[55.90512269930867,37.52430255819701],[55.90703897263751,37.53269282012937],[55.907966572321136,37.53975289157102],[55.90902140062051,37.5412868875274],[55.910152880152935,37.54444915505595],[55.908585318736186,37.546545272951086],[55.908656907846144,37.54778308396145],[55.909690523107194,37.557412100524864],[55.91122066556895,37.57125242062374],[55.911437559326295,37.579127592590275],[55.910654096297904,37.58704567990107],[55.912121324335324,37.588574517860366],[55.91238335280755,37.590275017196596],[55.90885790058001,37.5938047619018],[55.90359012331049,37.613030746032685],[55.90097421501675,37.62455367990113],[55.898936844196996,37.635840796295106],[55.897212596581625,37.64888681216433],[55.89651934437922,37.656107194442775],[55.895994874494995,37.66251218518068],[55.89577799134736,37.66891730555725],[55.89758660915251,37.67233980950929],[55.895549234834554,37.67718912695309],[55.8951997684638,37.68744577777098],[55.8950009223146,37.694676796295134],[55.89404246953588,37.700985134918184],[55.8926075467526,37.706091711639424],[55.88997846529005,37.711928050262415],[55.887421737126566,37.716948796295135],[55.88540168994378,37.72126192327878],[55.88393632434494,37.72450216665644],[55.88437344390691,37.72914769313042],[55.88114407434355,37.73001666931145],[55.878641115474906,37.73450120370479],[55.872732665250815,37.745854576721214],[55.86776201928833,37.755510169311535],[55.860425036857976,37.769886407409665],[55.83078910124083,37.827577727508555],[55.83052957745761,37.830174193115255],[55.829155842957626,37.830292253952024],[55.82601862866738,37.8346589338684],[55.82196031935257,37.837598730163556],[55.819453890163324,37.83855353572079],[55.81685064752759,37.83907918783566],[55.815992865126056,37.84006086112207],[55.81532837468172,37.841900841293274],[55.81518340863169,37.84437917199699],[55.81304485328354,37.84215292330156],[55.81187283042828,37.840184166671676],[55.81080952208339,37.839824735450655],[55.80793359566687,37.840194865081756],[55.80558610526405,37.840237792327805],[55.799455472720524,37.84090299206543],[55.791386536255715,37.841739865081735],[55.77927874237433,37.84322767990104]]] }";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        type = getIntent().getIntExtra("categoryId", 0);

        ButterKnife.bind(this);
        toolbarTitleTV.setText(titles[type]);
        alphas = new ArrayList<>();
        animationHeight = Utils.dpToPx(56);
        bottomNavigationPanel = findViewById(R.id.bottom_agregation_menu);

        startY = bottomNavigationPanel.getY();
        panelHeight = 600;
        configPanel();
        // hide panel to bottom
        bottomNavigationPanel.setVisibility(View.INVISIBLE);
        bottomNavigationPanel.setY(panelHeight);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        moscowBounds = new PolygonOptions();

        try {
            JSONObject borderJson = new JSONObject(BORDER_JSON);
            JSONArray  coords     = borderJson.getJSONArray("coordinates").getJSONArray(0);
            for (int i = 0; i < coords.length(); i++) {
                JSONArray elem = coords.getJSONArray(i);
                moscowBounds.add(new LatLng(elem.getDouble(0), elem.getDouble(1)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        moscowBounds.fillColor(Color.argb(0, 107, 156, 228))
                .strokeColor(Color.argb(100, 107, 156, 228));

        alphas.add(50);
        alphas.add(75);
        alphas.add(25);
        alphas.add(10);
        rangeSlider1.getThumb(0).setValue(alphas.get(0));
        rangeSlider2.getThumb(0).setValue(alphas.get(1));
        rangeSlider3.getThumb(0).setValue(alphas.get(2));
        rangeSlider4.getThumb(0).setValue(alphas.get(3));

        rangeSlider1.setOnThumbValueChangeListener(this);
        rangeSlider2.setOnThumbValueChangeListener(this);
        rangeSlider3.setOnThumbValueChangeListener(this);
        rangeSlider4.setOnThumbValueChangeListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.addPolygon(moscowBounds);

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.12);

        LatLngBounds latLngBounds = getPolygonLatLngBounds(moscowBounds.getPoints());
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, width,
                height, padding));

//        googleMap.setOnMapClickListener(latLng -> {
//            if (bottomNavigationPanel.getVisibility() == View.VISIBLE) {
//                animateBottom();
//            } else {
//                animateTop();
//            }
//        });

        drawAllMetrics(0);
        drawCompaniesDotes(1);
//        drawAllMetrics(type);
    }

    private void drawCompaniesDotes(int index) {
        List<LatLng> dotes = JsonConstant.jsonData.get(index);

        for (LatLng latLng : dotes) {
            googleMap.addCircle(new CircleOptions()
                    .center(latLng)
                    .radius(900)
                    .strokeWidth(0)
                    .fillColor(Color.argb(30, 207, 156, 228)));
//
//            googleMap.addCircle(new CircleOptions()
//                    .center(latLng)
//                    .radius(100)
//                    .strokeWidth(0)
//                    .fillColor(Color.argb(255, 107, 156, 228)));
        }
    }

    private void drawTransportDotes(int index, int color) {
        List<LatLng> dotes = JsonConstant.jsonDataTransport.get(index);

        for (LatLng latLng : dotes) {
            googleMap.addCircle(new CircleOptions()
                    .center(latLng)
                    .radius(900)
                    .strokeWidth(0)
                    .fillColor(color));

            googleMap.addCircle(new CircleOptions()
                    .center(latLng)
                    .radius(100)
                    .strokeWidth(0)
                    .fillColor(Color.argb(255, 207, 156, 228)));
        }
    }

    private void drawAllMetrics(int type) {
        List<PointData> currentPointData = JsonConstant.allMetricsData.get(type);

        for (PointData pointData : currentPointData) {
            googleMap.addCircle(new CircleOptions()
                    .center(pointData.getLocation())
                    .radius(900)
                    .strokeWidth(0)
                    .fillColor(Color.argb(pointData.getIntensity(), 107, 156, 228)));
        }
    }

    private LatLngBounds getPolygonLatLngBounds(List<LatLng> polygon) {
        LatLngBounds.Builder centerBuilder = LatLngBounds.builder();
        for (LatLng point : polygon) {
            centerBuilder.include(point);
        }
        return centerBuilder.build();
    }

    @OnClick(R.id.no_metrics)
    public void noMetricsButtonClicked() {
        noMetricsButton.setBackgroundResource(R.drawable.metric_button_active);
        noMetricsButton.setTextColor(getResources().getColor(R.color.white));

        metrics2Button.setBackgroundResource(R.drawable.metric_button);
        metrics2Button.setTextColor(getResources().getColor(R.color.peacock_blue));

        metrics1Button.setBackgroundResource(R.drawable.metric_button);
        metrics1Button.setTextColor(getResources().getColor(R.color.peacock_blue));

        googleMap.clear();
        drawAllMetrics(type);
    }

    @OnClick(R.id.metrics1_button)
    public void metrics1ButtonClicked() {
        noMetricsButton.setBackgroundResource(R.drawable.metric_button);
        noMetricsButton.setTextColor(getResources().getColor(R.color.peacock_blue));

        metrics2Button.setBackgroundResource(R.drawable.metric_button);
        metrics2Button.setTextColor(getResources().getColor(R.color.peacock_blue));

        metrics1Button.setBackgroundResource(R.drawable.metric_button_active);
        metrics1Button.setTextColor(getResources().getColor(R.color.white));

    }

    @OnClick(R.id.metrics2_button)
    public void metrics2ButtonClicked() {
        noMetricsButton.setBackgroundResource(R.drawable.metric_button);
        noMetricsButton.setTextColor(getResources().getColor(R.color.peacock_blue));

        metrics2Button.setBackgroundResource(R.drawable.metric_button_active);
        metrics2Button.setTextColor(getResources().getColor(R.color.white));

        metrics1Button.setBackgroundResource(R.drawable.metric_button);
        metrics1Button.setTextColor(getResources().getColor(R.color.peacock_blue));

        googleMap.clear();
        drawTransportDotes(0, Color.argb(150, 207, 156, 228));
        drawTransportDotes(1, Color.argb(150, 107, 255, 228));
    }

    @OnClick(R.id.user_button)
    public void userButtonClicked() {
        if (isUserMenuAnimated && !isUserMenuShown || isUserMenuShown) {
            animateUserMenu();
            isUserMenuShown = !isUserMenuShown;
            isUserMenuAnimated = false;
        }
    }

    private void animateUserMenu() {
        if (!isUserMenuShown) {
            userMenuView.setVisibility(View.VISIBLE);
            userMenuView.setAlpha(0.0f);
            userMenuView.bringToFront();
            userMenuView.animate()
                    .translationY(animationHeight)
                    .alpha(1.0f);
        } else {
            userMenuView.animate()
                    .translationY(0)
                    .alpha(0.0f)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            if (!isUserMenuShown) {
                                userMenuView.setVisibility(View.GONE);
                                isUserMenuAnimated = true;
                            }
                        }
                    });
        }
    }

    private void configPanel() {
        String[] titles = {"CheapRent", "FastBuild", "Sberbank", "EasyStroy"};
        String[] descs = {"Дешевые предложения по аренде", "Строительная компания", "Кредит для малого бизнеса", "Строительная компания"};

        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            categories.add(new Category(titles[i], descs[i]));
        }

        RecyclerView recyclerView = findViewById(R.id.agreg_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CategoryRecyclerAdapter(categories, position -> {
//                Intent intent = new Intent(this, .class);
//                intent.putExtra("agregId", position);
//                startActivity(intent);
        }));

    }

    private void animateTop() {
        Log.d("TAG", "animate Top before: "+bottomNavigationPanel.getY());
        bottomNavigationPanel.setVisibility(View.VISIBLE);
        ObjectAnimator animationAlpha = ObjectAnimator.ofFloat(bottomNavigationPanel,
                "alpha", 1f);
        ObjectAnimator animationUp = ObjectAnimator.ofFloat(bottomNavigationPanel,
                "translationY", startY);
        AnimatorSet set = new AnimatorSet();
        set.play(animationUp).with(animationAlpha);
        set.setDuration(1000);
        set.start();
    }

    private void animateBottom() {
        Log.d("TAG", "animate Bottom: "+bottomNavigationPanel.getY());
        ObjectAnimator animationBottom = ObjectAnimator.ofFloat(bottomNavigationPanel,
                "translationY", panelHeight);
        ObjectAnimator animationAlpha = ObjectAnimator.ofFloat(bottomNavigationPanel,
                "alpha", 0f);
        AnimatorSet set = new AnimatorSet();
        set.play(animationBottom).with(animationAlpha);
        set.setDuration(1000);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                bottomNavigationPanel.setVisibility(View.INVISIBLE);
            }
        });
        set.start();
    }

    @Override
    public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {
        switch (multiSlider.getId()) {
            case R.id.range_slider1:
                alphas.set(0, value);
                rangeSlider1Label.setText(String.format("x %.2f", 1.0*value/100));
                break;
            case R.id.range_slider2:
                alphas.set(1, value);
                rangeSlider2Label.setText(String.format("x %.2f", 1.0*value/100));
                break;
            case R.id.range_slider3:
                alphas.set(2, value);
                rangeSlider3Label.setText(String.format("x %.2f", 1.0*value/100));
                break;
            case R.id.range_slider4:
                alphas.set(3, value);
                rangeSlider4Label.setText(String.format("x %.2f", 1.0*value/100));
                break;
        }
    }
}
