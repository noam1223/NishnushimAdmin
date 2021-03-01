package com.example.nishnushimadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.nishnushimadmin.adapters.ChangeDetailByTypeAdapter;
import com.example.nishnushimadmin.adapters.ClassificationMenuAdapter;
import com.example.nishnushimadmin.adapters.DishSelectionAdapter;
import com.example.nishnushimadmin.adapters.PizzaChangesAdapter;
import com.example.nishnushimadmin.adapters.RemoveChangeBaseAdapter;
import com.example.nishnushimadmin.helpClasses.Classification;
import com.example.nishnushimadmin.helpClasses.Dish;
import com.example.nishnushimadmin.helpClasses.Menu;
import com.example.nishnushimadmin.helpClasses.Restaurant;
import com.example.nishnushimadmin.helpClasses.callbacks.AddMenuItemCallback;
import com.example.nishnushimadmin.helpClasses.menuChanges.Changes;
import com.example.nishnushimadmin.helpClasses.menuChanges.PizzaChange;
import com.example.nishnushimadmin.helpClasses.menuChanges.RegularChange;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AddMenuActivity extends AppCompatActivity {

    TextView headLineTextView;
    Button addClassificationBtn, addChangesBtn, addMenuBtn;
    RecyclerView classificationRecyclerView;
    RecyclerView.Adapter classificationAdapter;
    Changes createChange;
    List<Changes> changesList = new ArrayList<>();

    Restaurant restaurant;


    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        headLineTextView = findViewById(R.id.head_line_activity_add_menu);
        addClassificationBtn = findViewById(R.id.add_classification_btn_add_menu_activity);
        addChangesBtn = findViewById(R.id.add_changes_btn_add_menu_activity);
        addMenuBtn = findViewById(R.id.add_menu_to_restaurant_btn_add_menu_activity);
        classificationRecyclerView = findViewById(R.id.recycler_view_classification_add_menu_activity);


        restaurant = (Restaurant) getIntent().getSerializableExtra("restaurant");


        //UPDATE UI
        headLineTextView.setText(restaurant.getName());
        initializeClassificationRecyclerView();

        //SERVER SIDE
        db = FirebaseFirestore.getInstance();


        addClassificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View popUpView = getLayoutInflater().inflate(R.layout.classification_name_pop_up_window, null);
                Dialog areaDialog = new Dialog(AddMenuActivity.this);
                areaDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                areaDialog.setContentView(popUpView);

                EditText editText = popUpView.findViewById(R.id.classification_name_edit_text_classification_pop_up_window);
                Button button = popUpView.findViewById(R.id.add_classification_name_btn_classification_pop_up_window);


                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!editText.getText().toString().isEmpty()) {

                            restaurant.getMenu().getClassifications().add(new Classification(editText.getText().toString(), new ArrayList<>(), new ArrayList<>()));

                            classificationAdapter.notifyDataSetChanged();
                            areaDialog.dismiss();

                        } else
                            Toast.makeText(AddMenuActivity.this, "יש למלא את שם הסיווג", Toast.LENGTH_SHORT).show();
                    }
                });


                areaDialog.create();
                areaDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                areaDialog.show();

            }
        });


        addMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (restaurant.getMenu() != null && restaurant.getMenu().getClassifications().size() > 0) {

                    volleyPostMenu();
                    finish();

//                        Map<String, Object> menuMap = new HashMap<>();
//                        menuMap.put("menu", restaurant.getMenu());
//                        db.collection(getResources().getString(R.string.RESTAURANTS_PATH)).document(restaurant.getId()).update(menuMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//
//                                if (task.isSuccessful()){
//
//                                    Toast.makeText(AddMenuActivity.this, "התפריט נוסף בהצלחה", Toast.LENGTH_SHORT).show();
//                                    finish();
//
//                                } else if (task.isCanceled()){
//
//                                    Toast.makeText(AddMenuActivity.this, "ישנה תקלה, אנא נסה שוב", Toast.LENGTH_SHORT).show();
//
//                                }
//
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//
//                                Toast.makeText(AddMenuActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                            }
//                        });
                }
            }
        });



        addChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                preformAddChange();

            }
        });
    }


    private void initializeClassificationRecyclerView() {

        classificationRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        classificationRecyclerView.setLayoutManager(layoutManager);
        classificationAdapter = new ClassificationMenuAdapter(this, restaurant.getMenu());
        classificationRecyclerView.setAdapter(classificationAdapter);

    }

    private void preformAddChange() {

        String[] changesStrings = {"צפייה בשינויים קיימים", "הוספת מנה לבחירה", "הוספת סיווג לבחירה", "הוספת שינויים לבחירה אחת", "הוספת שינויים לכמות", "הוספת שינויי פיצה"};


        View popUpView = LayoutInflater.from(this).inflate(R.layout.add_classification_change_pop_up_window, null);
        Dialog removeDialog = new Dialog(this);
        removeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        removeDialog.setContentView(popUpView);


        ViewFlipper changeViewFlipper = popUpView.findViewById(R.id.view_flipper_add_classification_change_pop_up_window);
        Button addChangeBtn = popUpView.findViewById(R.id.add_classification_btn_add_classification_change_pop_up);
        Spinner classificationChangeSpinner = popUpView.findViewById(R.id.classification_style_change_add_classification_change_pop_up);
        classificationChangeSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, changesStrings));

//                ViewStub addDishViewStub = popUpView.findViewById(R.id.change_view_stub_add_classification_change_pop_up);
//                addDishViewStub.setLayoutResource(R.layout.watch_existing_classification_changes_layout);
//                View view = addDishViewStub.inflate();

        View existingChangesView = popUpView.findViewById(R.id.watch_existing_classification_include_change_pop_up);
        View addDishChangeView = popUpView.findViewById(R.id.add_dish_change_by_dish_include_change_pop_up);
        View addClassificationChangeView = popUpView.findViewById(R.id._add_classification_cahnge_include_change_pop_up);
        View addRegularChangeView = popUpView.findViewById(R.id.add_regular_change_include_change_pop_up);
        View addVolumeChangeView = popUpView.findViewById(R.id.add_change_by_volume_include_change_pop_up);
        View addPizzaChangeView = popUpView.findViewById(R.id.add_change_pizza_include_change_pop_up);

        changeViewFlipper.setDisplayedChild(0);


        classificationChangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                createChange = new Changes();
                createChange.setFreeSelection(0);
                changeViewFlipper.setDisplayedChild(position);

                for (int i = 0; i < changeViewFlipper.getChildCount(); i++) {


                    if (i != position) {
                        changeViewFlipper.getChildAt(i).setVisibility(View.GONE);
                    } else {
                        changeViewFlipper.getChildAt(i).setVisibility(View.VISIBLE);
                    }

                }


                if (changeViewFlipper.getCurrentView().getId() == existingChangesView.getId()) {


                    Log.i("CHOOSER", Changes.ChangesTypesEnum.CHOICE_ALL.toString());
                    createChange.setChangesTypesEnum(Changes.ChangesTypesEnum.CHOICE_ALL);
                    ListView changesListView = existingChangesView.findViewById(R.id.changes_list_view_watch_existing_classification_changes_layout);
                    changesListView.setAdapter(new RemoveChangeBaseAdapter(getApplicationContext(), restaurant.getMenu().getChangesList()));


                } else if (changeViewFlipper.getCurrentView().getId() == addDishChangeView.getId()) {

                    Log.i("CHOOSER", Changes.ChangesTypesEnum.DISH_CHOICE.toString());

                    createChange = new Changes();
                    createChange.setFreeSelection(0);
                    createChange.setChangesTypesEnum(Changes.ChangesTypesEnum.DISH_CHOICE);
                    Spinner spinnerClassification = addDishChangeView.findViewById(R.id.spinner_classification_dish_add_dish_change_by_dish_layout);
                    String[] classificationStrings = new String[restaurant.getMenu().getClassifications().size()];


                    for (int i = 0; i < restaurant.getMenu().getClassifications().size(); i++) {
                        classificationStrings[i] = restaurant.getMenu().getClassifications().get(i).getClassificationName();
                    }


                    spinnerClassification.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, classificationStrings));

                    EditText amountOfFreeEditText = addDishChangeView.findViewById(R.id.amount_units_to_add_edit_text_add_dish_change_by_dish_layout);
                    ListView dishesListView = addDishChangeView.findViewById(R.id.dishes_list_view_to_choose_add_dish_change_by_dish_layout);
                    dishesListView.setAdapter(new DishSelectionAdapter(getApplicationContext(), restaurant.getMenu().getClassifications().get(0), createChange));

                    amountOfFreeEditText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                            if (s.length() > 0 && Integer.parseInt(s.toString()) > 0) {

                                createChange.setFreeSelection(Integer.parseInt(s.toString()));

                            } else createChange.setFreeSelection(0);

                        }
                    });


                    spinnerClassification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                            createChange = new Changes();
                            createChange.setFreeSelection(0);
                            createChange.setChangesTypesEnum(Changes.ChangesTypesEnum.DISH_CHOICE);
                            createChange.setChangeName(restaurant.getMenu().getClassifications().get(position).getClassificationName());
                            dishesListView.setAdapter(new DishSelectionAdapter(getApplicationContext(), restaurant.getMenu().getClassifications().get(position), createChange));


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });


                } else if (changeViewFlipper.getCurrentView().getId() == addClassificationChangeView.getId()) {

                    Log.i("CHOOSER", Changes.ChangesTypesEnum.CLASSIFICATION_CHOICE.toString());
                    createChange = new Changes();
                    createChange.setChangesTypesEnum(Changes.ChangesTypesEnum.CLASSIFICATION_CHOICE);
                    String[] classificationStrings = new String[restaurant.getMenu().getClassifications().size()];

                    for (int i = 0; i < restaurant.getMenu().getClassifications().size(); i++) {
                        classificationStrings[i] = restaurant.getMenu().getClassifications().get(i).getClassificationName();
                    }


                    Spinner classificationSpinner = addClassificationChangeView.findViewById(R.id.spinner_classification_dish_add_classification_change_layout);
                    EditText amountOfFreeEditText = addClassificationChangeView.findViewById(R.id.amount_units_to_add_edit_text_add_classification_change_layout);
                    classificationSpinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, classificationStrings));


                    classificationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                            AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("האם אתה בטוח להוסיף את: ").append(classificationSpinner.getAdapter().getItem(position));
                            stringBuilder.append("עם אפשרות בחירה :");

                            if (!amountOfFreeEditText.getText().toString().isEmpty()) {
                                stringBuilder.append(amountOfFreeEditText.getText().toString());
                            } else stringBuilder.append(0);


                            alertDialog.setTitle("הוספה");
                            alertDialog.setMessage(stringBuilder.toString());
                            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "הוסף", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    createChange = new Changes();
                                    createChange.setChangesTypesEnum(Changes.ChangesTypesEnum.CLASSIFICATION_CHOICE);

                                    if (!amountOfFreeEditText.getText().toString().isEmpty()) {
                                        createChange.setFreeSelection(Integer.parseInt(amountOfFreeEditText.getText().toString()));
                                    }

                                    createChange.setChangeName(restaurant.getMenu().getClassifications().get(position).getClassificationName());
                                    changesList.add(createChange);
                                    createChange = new Changes();
//                                        createChange.getChangesByTypesList().add(new ChangesByTypes<>(menu.getClassifications().get(position)));
//                                        createChange.getChangesByTypesList().addAll(menu.getClassifications().get(position).getDishList());
//                                        createChange.getChangesByTypesList().add(menu.getClassifications().get(position).getDishList());


//                                        for (int i = 0; i < menu.getClassifications().get(position).getDishList().size(); i++) {
//                                            createChange.getChangesByTypesList().add(new ChangesByTypes<>(menu.getClassifications().get(position).getDishList().get(i)));
//                                        }

                                }
                            });


                            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "לא", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    alertDialog.dismiss();
                                }
                            });

                            alertDialog.show();


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                    amountOfFreeEditText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                            if (s.length() > 0 && Integer.parseInt(s.toString()) > 0) {

                                createChange.setFreeSelection(Integer.parseInt(s.toString()));

                            } else createChange.setFreeSelection(0);

                        }
                    });


                } else if (changeViewFlipper.getCurrentView().getId() == addRegularChangeView.getId()) {

                    Log.i("CHOOSER", Changes.ChangesTypesEnum.ONE_CHOICE.toString());
                    createChange = new Changes();
                    createChange.setId(createIdForNewChange(restaurant.getMenu().getChangesList()));
                    createChange.setChangesTypesEnum(Changes.ChangesTypesEnum.ONE_CHOICE);
                    createChange.setFreeSelection(1);

                    List<String> changesNames = new ArrayList<>();
                    String[] unitStyleChooser = {"יחידה", "כמות"};
                    final int[] positionOfChange = {-1};


                    for (int i = 0; i < restaurant.getMenu().getChangesList().size(); i++) {
                        if (restaurant.getMenu().getChangesList().get(i).getChangesTypesEnum() == Changes.ChangesTypesEnum.ONE_CHOICE
                                || restaurant.getMenu().getChangesList().get(i).getChangesTypesEnum() == Changes.ChangesTypesEnum.MULTIPLE) {
                            changesNames.add(restaurant.getMenu().getChangesList().get(i).getChangeName());
                        }
                    }


                    AutoCompleteTextView autoCompleteTextViewChangesNames = addRegularChangeView.findViewById(R.id.changes_name_auto_complete_add_change_pop_up);
                    Spinner unitStyleSpinner = addRegularChangeView.findViewById(R.id.unit_style_spinner_add_chane_pop_up_window);
                    EditText changeDetailsEditText = addRegularChangeView.findViewById(R.id.change_detail_edit_text_add_change_pop_up_window);
                    EditText costChangeEditText = addRegularChangeView.findViewById(R.id.change_sum_edit_text_add_change_pop_up_window);
                    ListView regularChangesListView = addRegularChangeView.findViewById(R.id.regular_changes_list_view_add_change_pop_up_window);
                    Button addRegularChangeBtn = addRegularChangeView.findViewById(R.id.add_change_btn_add_change_pop_up_window);

                    unitStyleSpinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, unitStyleChooser));


                    autoCompleteTextViewChangesNames.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, changesNames));
                    autoCompleteTextViewChangesNames.setThreshold(1);

                    autoCompleteTextViewChangesNames.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                            if (s.length() > 0) {
                                boolean isExist = false;

                                for (int i = 0; i < autoCompleteTextViewChangesNames.getAdapter().getCount(); i++) {

                                    if (autoCompleteTextViewChangesNames.getAdapter().getItem(i).toString().equals(s.toString())) {
                                        isExist = true;
                                    }

                                }

                                unitStyleSpinner.setEnabled(!isExist);

                            }

                        }
                    });


                    autoCompleteTextViewChangesNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            for (int i = 0; i < restaurant.getMenu().getChangesList().size(); i++) {

                                if (restaurant.getMenu().getChangesList().get(i).getChangeName().equals(autoCompleteTextViewChangesNames.getText().toString())
                                        && restaurant.getMenu().getChangesList().get(i).getChangesTypesEnum() == createChange.getChangesTypesEnum()) {

                                    if (regularChangesListView.getAdapter() != null) {
                                        regularChangesListView.setAdapter(null);
                                    }

                                    positionOfChange[0] = i;
                                    regularChangesListView.setAdapter(new ChangeDetailByTypeAdapter(getApplicationContext(), restaurant.getMenu().getChangesList().get(i)));

                                    if (restaurant.getMenu().getChangesList().get(i).getChangesTypesEnum() == Changes.ChangesTypesEnum.ONE_CHOICE) {
                                        unitStyleSpinner.setSelection(0);
                                    } else unitStyleSpinner.setSelection(1);

                                    unitStyleSpinner.setEnabled(false);
                                }

                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                    unitStyleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position == 0) {
                                createChange.setChangesTypesEnum(Changes.ChangesTypesEnum.ONE_CHOICE);
                                createChange.setFreeSelection(1);
                            } else if (position == 1) {
                                createChange.setChangesTypesEnum(Changes.ChangesTypesEnum.MULTIPLE);
                                createChange.setFreeSelection(0);
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                    addRegularChangeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            RegularChange regularChange = new RegularChange();

                            for (int i = 0; i < restaurant.getMenu().getChangesList().size(); i++) {

                                if (restaurant.getMenu().getChangesList().get(i).getChangeName().equals(autoCompleteTextViewChangesNames.getText().toString())) {

                                    if (regularChangesListView.getAdapter() != null) {
                                        regularChangesListView.setAdapter(null);
                                    }

                                    positionOfChange[0] = i;
                                    regularChangesListView.setAdapter(new ChangeDetailByTypeAdapter(getApplicationContext(), restaurant.getMenu().getChangesList().get(i)));

                                    if (restaurant.getMenu().getChangesList().get(i).getChangesTypesEnum() == Changes.ChangesTypesEnum.ONE_CHOICE) {
                                        unitStyleSpinner.setSelection(0);
                                    } else unitStyleSpinner.setSelection(1);

                                    unitStyleSpinner.setEnabled(false);
                                }

                            }


                            String changeDetail = changeDetailsEditText.getText().toString();
                            String costChange = costChangeEditText.getText().toString();


                            if (!changeDetail.isEmpty() && !costChange.isEmpty() && autoCompleteTextViewChangesNames.getText().toString().length() > 0) {

                                regularChange.setChange(changeDetail);
                                regularChange.setPrice(Integer.parseInt(costChange));

                                if (positionOfChange[0] != -1) {
                                    restaurant.getMenu().getChangesList().get(positionOfChange[0]).getChangesByTypesList().add(regularChange);

                                    if (regularChangesListView.getAdapter() != null) {
                                        regularChangesListView.setAdapter(null);
                                    }

                                    regularChangesListView.setAdapter(new ChangeDetailByTypeAdapter(getApplicationContext(), restaurant.getMenu().getChangesList().get(positionOfChange[0])));
                                    Log.i("ADD", positionOfChange[0] + "");

                                } else {

                                    int selection = 0;
                                    if (unitStyleSpinner.getSelectedItemPosition() == 0) {
                                        createChange.setChangesTypesEnum(Changes.ChangesTypesEnum.ONE_CHOICE);
                                        selection = 1;
                                    } else {
                                        createChange.setChangesTypesEnum(Changes.ChangesTypesEnum.MULTIPLE);
                                        selection = -1;
                                    }

                                    createNewChanges(regularChange, autoCompleteTextViewChangesNames, createChange.getChangesTypesEnum(), selection);


                                    if (regularChangesListView.getAdapter() != null) {
                                        regularChangesListView.setAdapter(null);
                                    }

                                    regularChangesListView.setAdapter(new ChangeDetailByTypeAdapter(getApplicationContext(), createChange));
                                    Log.i("HERE", autoCompleteTextViewChangesNames.getText().toString());
                                }


                            }

                        }
                    });


                } else if (changeViewFlipper.getCurrentView().getId() == addVolumeChangeView.getId()) {

                    Log.i("CHOOSER", Changes.ChangesTypesEnum.VOLUME.toString());
                    createChange = new Changes();
                    createChange.setFreeSelection(0);
                    createChange.setChangesTypesEnum(Changes.ChangesTypesEnum.VOLUME);

                    RegularChange regularChange = new RegularChange();
                    List<String> changesNames = new ArrayList<>();
                    int[] positionOfChange = {-1};


                    for (int i = 0; i < restaurant.getMenu().getChangesList().size(); i++) {
                        if (restaurant.getMenu().getChangesList().get(i).getChangesTypesEnum() == Changes.ChangesTypesEnum.VOLUME) {
                            changesNames.add(restaurant.getMenu().getChangesList().get(i).getChangeName());
                        }
                    }


                    AutoCompleteTextView autoCompleteTextViewVolume = addVolumeChangeView.findViewById(R.id.classification_name_auto_complete_add_change_by_volume_layout);
                    EditText changeNameEditText = addVolumeChangeView.findViewById(R.id.change_name_edit_text_add_change_by_volume_layout);
                    EditText costChangeEditText = addVolumeChangeView.findViewById(R.id.cost_change_edit_text_add_change_by_volume_layout);
                    EditText unitChangeNameEditText = addVolumeChangeView.findViewById(R.id.units_change_edit_text_add_change_by_volume_layout);
                    Button addVolumeChangeBtn = addVolumeChangeView.findViewById(R.id.add_volume_change_btn_add_change_pop_up_window);
                    ListView volumeListView = addVolumeChangeView.findViewById(R.id.volume_changes_list_view_add_change_pop_up_window);


                    autoCompleteTextViewVolume.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, changesNames));
                    autoCompleteTextViewVolume.setThreshold(1);


                    autoCompleteTextViewVolume.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            for (int i = 0; i < restaurant.getMenu().getChangesList().size(); i++) {

                                if (restaurant.getMenu().getChangesList().get(i).getChangeName().equals(autoCompleteTextViewVolume.getText().toString())) {

                                    if (volumeListView.getAdapter() != null) {
                                        volumeListView.setAdapter(null);
                                    }

                                    positionOfChange[0] = i;

                                    if (volumeListView.getAdapter() != null) {
                                        volumeListView.setAdapter(null);
                                    }

                                    volumeListView.setAdapter(new ChangeDetailByTypeAdapter(getApplicationContext(), restaurant.getMenu().getChangesList().get(i)));


                                }

                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                    addVolumeChangeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            RegularChange regularChange = new RegularChange();

                            for (int i = 0; i < restaurant.getMenu().getChangesList().size(); i++) {

                                if (restaurant.getMenu().getChangesList().get(i).getChangeName().equals(autoCompleteTextViewVolume.getText().toString())) {

                                    if (volumeListView.getAdapter() != null) {
                                        volumeListView.setAdapter(null);
                                    }

                                    positionOfChange[0] = i;

                                    if (volumeListView.getAdapter() != null) {
                                        volumeListView.setAdapter(null);
                                    }

                                    volumeListView.setAdapter(new ChangeDetailByTypeAdapter(getApplicationContext(), restaurant.getMenu().getChangesList().get(i)));


                                }

                            }


                            String changeName = changeNameEditText.getText().toString();
                            String cost = costChangeEditText.getText().toString();
                            String unit = unitChangeNameEditText.getText().toString();


                            if (!changeName.isEmpty() && !cost.isEmpty()) {

                                StringBuilder stringBuilder = new StringBuilder();

                                stringBuilder.append(changeName);

                                if (!unit.isEmpty()) {
                                    stringBuilder.append("-").append(unit);
                                }

                                regularChange.setChange(stringBuilder.toString());
                                regularChange.setPrice(Integer.parseInt(cost));


                                if (positionOfChange[0] != -1) {
                                    restaurant.getMenu().getChangesList().get(positionOfChange[0]).getChangesByTypesList().add(regularChange);

                                    if (volumeListView.getAdapter() != null) {
                                        volumeListView.setAdapter(null);
                                    }

                                    volumeListView.setAdapter(new ChangeDetailByTypeAdapter(getApplicationContext(), restaurant.getMenu().getChangesList().get(positionOfChange[0])));

                                } else {

                                    createNewChanges(regularChange, autoCompleteTextViewVolume, Changes.ChangesTypesEnum.VOLUME, 0);


                                    if (volumeListView.getAdapter() != null) {
                                        volumeListView.setAdapter(null);
                                    }

                                    volumeListView.setAdapter(new ChangeDetailByTypeAdapter(getApplicationContext(), createChange));
                                }

                            }


                        }
                    });


                } else if (changeViewFlipper.getCurrentView().getId() == addPizzaChangeView.getId()) {


                    Log.i("CHOOSER", Changes.ChangesTypesEnum.PIZZA.toString());
                    createChange = new Changes();
                    createChange.setFreeSelection(0);
                    createChange.setChangesTypesEnum(Changes.ChangesTypesEnum.PIZZA);

                    int changePosition = -1;

                    for (int i = 0; i < restaurant.getMenu().getChangesList().size(); i++) {
                        if (restaurant.getMenu().getChangesList().get(i).getChangesTypesEnum() == Changes.ChangesTypesEnum.PIZZA) {
                            changePosition = i;
                            break;
                        }
                    }


                    EditText amountOfFreeEditText = addPizzaChangeView.findViewById(R.id.amount_of_free_edit_text_add_pizza_change_layout);
                    EditText allChangesCostEditText = addPizzaChangeView.findViewById(R.id.all_changes_cost_edit_text_add_pizza_change_layout);
                    EditText firstChangeCostEditText = addPizzaChangeView.findViewById(R.id.first_change_cost_edit_text_add_pizza_change_layout);
                    EditText secondChangeCostEditText = addPizzaChangeView.findViewById(R.id.second_change_cost_edit_text_add_pizza_change_layout);
                    EditText thirdChangeCostEditText = addPizzaChangeView.findViewById(R.id.third_change_cost_edit_text_add_pizza_change_layout);

                    ListView pizzaChangesListView = addPizzaChangeView.findViewById(R.id.pizza_change_list_view_add_pizza_change_layout);
                    ListView pizzaChangesAddedListView = addPizzaChangeView.findViewById(R.id.pizza_change_added_list_view_add_pizza_change_layout);


                    if (changePosition != -1) {
                        pizzaChangesAddedListView.setAdapter(new PizzaChangesAdapter(getApplicationContext(), restaurant.getMenu().getChangesList().get(changePosition)));
                        pizzaChangesListView.setAdapter(new PizzaChangesAdapter(getApplicationContext(), restaurant.getMenu().getChangesList().get(changePosition), pizzaChangesAddedListView));
                    } else {
                        pizzaChangesAddedListView.setAdapter(new PizzaChangesAdapter(getApplicationContext(), createChange));
                        pizzaChangesListView.setAdapter(new PizzaChangesAdapter(getApplicationContext(), createChange, pizzaChangesAddedListView));
                    }


                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });


        addChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (createChange.getChangesByTypesList().size() > 0 || changesList.size() > 0) {
//                    menu.getClassifications().get(holderPosition).getChangesList().add(createChange);

                    changesList.add(createChange);
                    restaurant.getMenu().getChangesList().addAll(changesList);
                    changesList.clear();

                }
                removeDialog.dismiss();

            }
        });


        removeDialog.create();

        removeDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        removeDialog.show();
    }




    private int createIdForNewChange(List<Changes> changesList) {
        int id = 0;
        List<Integer> ids = new ArrayList<>();

        for (int i = 0; i < changesList.size(); i++) {
            ids.add(changesList.get(i).getId());
        }

        do {
            Random random = new Random();
            id = random.nextInt(999 - 1) + 1;

        }while (ids.contains(id));


        return id;
    }



    private void createNewChanges(RegularChange regularChange, AutoCompleteTextView autoCompleteTextViewChangesNames, Changes.ChangesTypesEnum changesTypesEnum, int selection) {
        if (createChange.getChangeName() != null) {

            if (createChange.getChangeName().equals(autoCompleteTextViewChangesNames.getText().toString())) {
                createChange.getChangesByTypesList().add(regularChange);
            } else {
                changesList.add(createChange);
                createChange = new Changes();
                createChange.setChangesTypesEnum(changesTypesEnum);
                createChange.setChangeName(autoCompleteTextViewChangesNames.getText().toString());
                createChange.setFreeSelection(selection);
                createChange.getChangesByTypesList().add(regularChange);
            }

        } else {
            createChange.setChangeName(autoCompleteTextViewChangesNames.getText().toString());
            createChange.setFreeSelection(selection);
            createChange.getChangesByTypesList().add(regularChange);
        }
    }


    public void volleyPostMenu() {
        String postUrl = "http://10.0.2.2:8080/api/restaurants/" + restaurant.getId() + "/menu";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject menuJsonObject = getMenuJsonObject();


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, postUrl, menuJsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null && response.length() > 0) {
                    Log.i("RESPONSE", response.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("MENU ERROR ADD", error.getMessage());
            }
        }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));

                    JSONObject result = null;

                    if (jsonString.length() > 0)
                        result = new JSONObject(jsonString);

                    return Response.success(result,
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException | JSONException e) {
                    return Response.error(new ParseError(e));
                }
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

    private JSONObject getMenuJsonObject() {
        JSONObject menuJsonObject = new JSONObject();

        try {

            JSONArray classificationJsonArray = new JSONArray();

            for (Classification classification : restaurant.getMenu().getClassifications()) {

                Log.i("Classification ADD MENU", classification.toString());
                JSONObject classificationJsonObject = new JSONObject();
                JSONArray classificationChangesJsonArray = new JSONArray();
                classificationJsonObject.put("classificationName", classification.getClassificationName());
                JSONArray dishJsonArray = new JSONArray();


                for (Dish dish : classification.getDishList()) {

                    Log.i("DISH ADD MENU", dish.toString());

                    JSONObject dishJsonObject = new JSONObject();
                    JSONArray dishChangesJsonArray = new JSONArray();
                    dishJsonObject.put("name", dish.getName());
                    dishJsonObject.put("details", dish.getDetails());
                    dishJsonObject.put("price", dish.getPrice());


                    for (Changes changes : dish.getChanges()) {
                        JSONObject changeDishJsonObject = new JSONObject();
                        changeDishJsonObject.put("changeName", changes.getChangeName());
                        changeDishJsonObject.put("freeSelection", changes.getFreeSelection());
                        changeDishJsonObject.put("changesTypesEnum", changes.getChangesTypesEnum());

                        JSONArray changesTypeJsonArray = getChangesTypeJsonArray(changes);

                        changeDishJsonObject.put("changesByTypesList", changesTypeJsonArray);
                        dishChangesJsonArray.put(changeDishJsonObject);

                    }
                    dishJsonObject.put("changes", dishChangesJsonArray);
                    dishJsonArray.put(dishJsonObject);
                }


                for (Changes changes : classification.getChangesList()) {
                    JSONObject changeClassificationJsonObject = new JSONObject();
                    changeClassificationJsonObject.put("changeName", changes.getChangeName());
                    changeClassificationJsonObject.put("freeSelection", changes.getFreeSelection());
                    changeClassificationJsonObject.put("changesTypesEnum", changes.getChangesTypesEnum());

                    JSONArray changesTypeJsonArray = getChangesTypeJsonArray(changes);

                    changeClassificationJsonObject.put("changesByTypesList", changesTypeJsonArray);
                    classificationChangesJsonArray.put(changeClassificationJsonObject);
                }

                classificationJsonObject.put("dishList", dishJsonArray);
                classificationJsonObject.put("changesList", classificationChangesJsonArray);
                classificationJsonArray.put(classificationJsonObject);

            }

            menuJsonObject.put("classifications", classificationJsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return menuJsonObject;
    }

    private JSONArray getChangesTypeJsonArray(Changes changes) throws JSONException {
        JSONArray changesTypeJsonArray = new JSONArray();

        for (Object object : changes.getChangesByTypesList()) {
            JSONObject changeTypeJsonObject = new JSONObject();

            switch (changes.getChangesTypesEnum()) {

                case ONE_CHOICE:
                    RegularChange regularChange = (RegularChange) object;
                    changeTypeJsonObject.put("change", regularChange.getChange());
                    changeTypeJsonObject.put("price", regularChange.getPrice());
                    break;

                case PIZZA:
                    PizzaChange pizzaChange = (PizzaChange) object;
                    changeTypeJsonObject.put("id", pizzaChange.getId());
                    changeTypeJsonObject.put("name", pizzaChange.getName());
                    changeTypeJsonObject.put("cost", pizzaChange.getCost());
                    break;

                case CLASSIFICATION_CHOICE:

                    break;

                case MULTIPLE:
                    RegularChange regularChange1 = (RegularChange) object;
                    changeTypeJsonObject.put("change", regularChange1.getChange());
                    changeTypeJsonObject.put("price", regularChange1.getPrice());

                    break;

                case VOLUME:
                    RegularChange regularChange2 = (RegularChange) object;
                    changeTypeJsonObject.put("change", regularChange2.getChange());
                    changeTypeJsonObject.put("price", regularChange2.getPrice());
                    break;

                case DISH_CHOICE:
                    Dish dish = (Dish) object;
                    changeTypeJsonObject.put("name", dish.getName());
                    changeTypeJsonObject.put("details", dish.getDetails());
                    changeTypeJsonObject.put("price", dish.getPrice());
                    changeTypeJsonObject.put("changes", new ArrayList<>());

                    break;


            }
            changesTypeJsonArray.put(changeTypeJsonObject);
        }
        return changesTypeJsonArray;
    }
}