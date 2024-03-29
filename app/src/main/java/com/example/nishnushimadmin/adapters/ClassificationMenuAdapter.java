package com.example.nishnushimadmin.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nishnushimadmin.R;
import com.example.nishnushimadmin.helpClasses.Dish;
import com.example.nishnushimadmin.helpClasses.Menu;
import com.example.nishnushimadmin.helpClasses.callbacks.AddMenuItemCallback;
import com.example.nishnushimadmin.helpClasses.menuChanges.Changes;

import com.example.nishnushimadmin.helpClasses.menuChanges.RegularChange;

import java.util.ArrayList;
import java.util.List;


public class ClassificationMenuAdapter extends RecyclerView.Adapter<ClassificationMenuAdapter.ClassificationMenuViewHolder> {

    Context context;
    Menu menu;
    List<DishMenuAdapter> dishMenuAdapters = new ArrayList<>();
    AddMenuItemCallback addMenuItemCallback;
    Changes createChange = new Changes();
    List<Changes> changesList = new ArrayList<>();


    public ClassificationMenuAdapter(Context context, Menu menu) {
        this.context = context;
        this.menu = menu;


        for (int i = 0; i < menu.getClassifications().size(); i++) {

            dishMenuAdapters.add(null);

        }

    }


    @NonNull
    @Override
    public ClassificationMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.classification_menu_item, parent, false);
        return new ClassificationMenuViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ClassificationMenuViewHolder holder, int position) {

        holder.headLineTextView.setText(menu.getClassifications().get(position).getClassificationName());


        holder.addDishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                View popUpView = LayoutInflater.from(context).inflate(R.layout.dish_details_pop_up_window, null);
                Dialog areaDialog = new Dialog(context);
                areaDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                areaDialog.setContentView(popUpView);


                EditText dishNameEditText = popUpView.findViewById(R.id.dish_name_edit_text_dish_details_pop_up_window);
                EditText dishDetailsEditText = popUpView.findViewById(R.id.dish_details_edit_text_dish_details_pop_up_window);
                EditText dishPriceEditText = popUpView.findViewById(R.id.dish_price_edit_text_dish_details_pop_up_window);
                Button takeDishPic = popUpView.findViewById(R.id.dish_picture_btn_dish_details_pop_up_window);
                Button addDishBtn = popUpView.findViewById(R.id.add_dish_btn_dish_details_pop_up_window);


                takeDishPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO: HANDLE ALL TAKING PICTURES BTN METHODS
                    }
                });




                addDishBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String name = dishNameEditText.getText().toString();
                        String details = dishDetailsEditText.getText().toString();
                        String price = dishPriceEditText.getText().toString();

                        Toast.makeText(context, name + " - " + details + " - " + price, Toast.LENGTH_SHORT).show();

                        if (!name.isEmpty() && !details.isEmpty() && !price.isEmpty()) {

                            Dish dish = new Dish(name, details, new ArrayList<>(), Integer.parseInt(price), null);

                            menu.getClassifications().get(position).getDishList().add(dish);
                            addInitializeDishMenuRecyclerView(holder, position, context);
                            areaDialog.dismiss();

                        } else
                            Toast.makeText(context, "יש למלא את הפרטים", Toast.LENGTH_SHORT).show();


                    }
                });


                areaDialog.create();
                areaDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                areaDialog.show();
            }
        });


        holder.deleteClassificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                preformAddChange(position);
                menu.getClassifications().remove(position);
                notifyDataSetChanged();

            }
        });


        //POSITION NEEDS TO BE SMALLER BY 1
        if (dishMenuAdapters.size() < position) {
            addInitializeDishMenuRecyclerView(holder, position, context);

        } else {

            if (dishMenuAdapters.size() > position) {
                if (dishMenuAdapters.get(position) == null) {
                    initializeDishMenuRecyclerView(holder, position, context);
                } else dishMenuAdapters.get(position).notifyDataSetChanged();
            } else addInitializeDishMenuRecyclerView(holder, position, context);
        }
    }


    private void preformAddChange(final int holderPosition) {
        String[] changesStrings = {"צפייה בשינויים קיימים", "הוספת מנה לבחירה", "הוספת סיווג לבחירה", "הוספת שינויים לבחירה אחת", "הוספת שינויים לכמות", "הוספת שינויי פיצה"};


        View popUpView = LayoutInflater.from(context).inflate(R.layout.add_classification_change_pop_up_window, null);
        Dialog removeDialog = new Dialog(context);
        removeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        removeDialog.setContentView(popUpView);


        ViewFlipper changeViewFlipper = popUpView.findViewById(R.id.view_flipper_add_classification_change_pop_up_window);
        Button addChangeBtn = popUpView.findViewById(R.id.add_classification_btn_add_classification_change_pop_up);
        Spinner classificationChangeSpinner = popUpView.findViewById(R.id.classification_style_change_add_classification_change_pop_up);
        classificationChangeSpinner.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, changesStrings));

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

        //TODO: ADD CHANGES LOGIC TO MENU, CLASSIFICATION AND DISH


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
                    changesListView.setAdapter(new RemoveChangeBaseAdapter(context, menu.getClassifications().get(holderPosition).getChangesList()));


                } else if (changeViewFlipper.getCurrentView().getId() == addDishChangeView.getId()) {

                    Log.i("CHOOSER", Changes.ChangesTypesEnum.DISH_CHOICE.toString());

                    createChange = new Changes();
                    createChange.setFreeSelection(0);
                    createChange.setChangesTypesEnum(Changes.ChangesTypesEnum.DISH_CHOICE);
                    Spinner spinnerClassification = addDishChangeView.findViewById(R.id.spinner_classification_dish_add_dish_change_by_dish_layout);
                    String[] classificationStrings = new String[menu.getClassifications().size()];


                    for (int i = 0; i < menu.getClassifications().size(); i++) {
                        classificationStrings[i] = menu.getClassifications().get(i).getClassificationName();
                    }


                    spinnerClassification.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, classificationStrings));

                    EditText amountOfFreeEditText = addDishChangeView.findViewById(R.id.amount_units_to_add_edit_text_add_dish_change_by_dish_layout);
                    ListView dishesListView = addDishChangeView.findViewById(R.id.dishes_list_view_to_choose_add_dish_change_by_dish_layout);
                    dishesListView.setAdapter(new DishSelectionAdapter(context, menu.getClassifications().get(0), createChange));

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

                            boolean isChoosed = false;
                            if (menu.getClassifications().get(holderPosition).getClassificationName().equals(menu.getClassifications().get(position).getClassificationName())) {
                                isChoosed = true;
                            }

                            if (!isChoosed) {
                                if (dishesListView.getAdapter() != null) {
                                    dishesListView.setAdapter(null);
                                }
                                createChange = new Changes();
                                createChange.setFreeSelection(0);
                                createChange.setChangesTypesEnum(Changes.ChangesTypesEnum.DISH_CHOICE);
                                createChange.setChangeName(menu.getClassifications().get(position).getClassificationName());
                                dishesListView.setAdapter(new DishSelectionAdapter(context, menu.getClassifications().get(position), createChange));
                            } else
                                Toast.makeText(context, "אין אפשרות לבחור מאותו סיווג", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });


                } else if (changeViewFlipper.getCurrentView().getId() == addClassificationChangeView.getId()) {

                    Log.i("CHOOSER", Changes.ChangesTypesEnum.CLASSIFICATION_CHOICE.toString());
                    createChange = new Changes();
                    createChange.setChangesTypesEnum(Changes.ChangesTypesEnum.CLASSIFICATION_CHOICE);
                    String[] classificationStrings = new String[menu.getClassifications().size()];

                    for (int i = 0; i < menu.getClassifications().size(); i++) {
                        classificationStrings[i] = menu.getClassifications().get(i).getClassificationName();
                    }


                    Spinner classificationSpinner = addClassificationChangeView.findViewById(R.id.spinner_classification_dish_add_classification_change_layout);
                    EditText amountOfFreeEditText = addClassificationChangeView.findViewById(R.id.amount_units_to_add_edit_text_add_classification_change_layout);
                    classificationSpinner.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, classificationStrings));


                    classificationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            boolean isChoosed = false;
                            if (menu.getClassifications().get(holderPosition).getClassificationName().equals(menu.getClassifications().get(position).getClassificationName())) {
                                isChoosed = true;
                            }


                            if (!isChoosed) {

                                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
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

                                        createChange.setChangeName(menu.getClassifications().get(position).getClassificationName());
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
                            } else
                                Toast.makeText(context, "אין אפשרות לבחור מאותו סיווג", Toast.LENGTH_SHORT).show();

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
                    createChange.setChangesTypesEnum(Changes.ChangesTypesEnum.ONE_CHOICE);
                    createChange.setFreeSelection(1);

                    List<String> changesNames = new ArrayList<>();
                    String[] unitStyleChooser = {"יחידה", "כמות"};
                    final int[] positionOfChange = {-1};


                    for (int i = 0; i < menu.getClassifications().get(holderPosition).getChangesList().size(); i++) {
                        if (menu.getClassifications().get(holderPosition).getChangesList().get(i).getChangesTypesEnum() == Changes.ChangesTypesEnum.ONE_CHOICE
                                || menu.getClassifications().get(holderPosition).getChangesList().get(i).getChangesTypesEnum() == Changes.ChangesTypesEnum.MULTIPLE) {
                            changesNames.add(menu.getClassifications().get(holderPosition).getChangesList().get(i).getChangeName());
                        }
                    }


                    AutoCompleteTextView autoCompleteTextViewChangesNames = addRegularChangeView.findViewById(R.id.changes_name_auto_complete_add_change_pop_up);
                    Spinner unitStyleSpinner = addRegularChangeView.findViewById(R.id.unit_style_spinner_add_chane_pop_up_window);
                    EditText changeDetailsEditText = addRegularChangeView.findViewById(R.id.change_detail_edit_text_add_change_pop_up_window);
                    EditText costChangeEditText = addRegularChangeView.findViewById(R.id.change_sum_edit_text_add_change_pop_up_window);
                    ListView regularChangesListView = addRegularChangeView.findViewById(R.id.regular_changes_list_view_add_change_pop_up_window);
                    Button addRegularChangeBtn = addRegularChangeView.findViewById(R.id.add_change_btn_add_change_pop_up_window);

                    unitStyleSpinner.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, unitStyleChooser));


                    autoCompleteTextViewChangesNames.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, changesNames));
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

                            for (int i = 0; i < menu.getClassifications().get(holderPosition).getChangesList().size(); i++) {

                                if (menu.getClassifications().get(holderPosition).getChangesList().get(i).getChangeName().equals(autoCompleteTextViewChangesNames.getText().toString())
                                        && menu.getClassifications().get(holderPosition).getChangesList().get(i).getChangesTypesEnum() == createChange.getChangesTypesEnum()) {

                                    if (regularChangesListView.getAdapter() != null) {
                                        regularChangesListView.setAdapter(null);
                                    }

                                    positionOfChange[0] = i;
                                    regularChangesListView.setAdapter(new ChangeDetailByTypeAdapter(context, menu.getClassifications().get(holderPosition).getChangesList().get(i)));

                                    if (menu.getClassifications().get(holderPosition).getChangesList().get(i).getChangesTypesEnum() == Changes.ChangesTypesEnum.ONE_CHOICE) {
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
                                createChange.setFreeSelection(-1);
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

                            for (int i = 0; i < menu.getClassifications().get(holderPosition).getChangesList().size(); i++) {

                                if (menu.getClassifications().get(holderPosition).getChangesList().get(i).getChangeName().equals(autoCompleteTextViewChangesNames.getText().toString())) {

                                    if (regularChangesListView.getAdapter() != null) {
                                        regularChangesListView.setAdapter(null);
                                    }

                                    positionOfChange[0] = i;
                                    regularChangesListView.setAdapter(new ChangeDetailByTypeAdapter(context, menu.getClassifications().get(holderPosition).getChangesList().get(i)));

                                    if (menu.getClassifications().get(holderPosition).getChangesList().get(i).getChangesTypesEnum() == Changes.ChangesTypesEnum.ONE_CHOICE) {
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
                                    menu.getClassifications().get(holderPosition).getChangesList().get(positionOfChange[0]).getChangesByTypesList().add(regularChange);

                                    if (regularChangesListView.getAdapter() != null) {
                                        regularChangesListView.setAdapter(null);
                                    }

                                    regularChangesListView.setAdapter(new ChangeDetailByTypeAdapter(context, menu.getClassifications().get(holderPosition).getChangesList().get(positionOfChange[0])));
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

                                    regularChangesListView.setAdapter(new ChangeDetailByTypeAdapter(context, createChange));
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


                    for (int i = 0; i < menu.getClassifications().get(holderPosition).getChangesList().size(); i++) {
                        if (menu.getClassifications().get(holderPosition).getChangesList().get(i).getChangesTypesEnum() == Changes.ChangesTypesEnum.VOLUME) {
                            changesNames.add(menu.getClassifications().get(holderPosition).getChangesList().get(i).getChangeName());
                        }
                    }


                    AutoCompleteTextView autoCompleteTextViewVolume = addVolumeChangeView.findViewById(R.id.classification_name_auto_complete_add_change_by_volume_layout);
                    EditText changeNameEditText = addVolumeChangeView.findViewById(R.id.change_name_edit_text_add_change_by_volume_layout);
                    EditText costChangeEditText = addVolumeChangeView.findViewById(R.id.cost_change_edit_text_add_change_by_volume_layout);
                    EditText unitChangeNameEditText = addVolumeChangeView.findViewById(R.id.units_change_edit_text_add_change_by_volume_layout);
                    Button addVolumeChangeBtn = addVolumeChangeView.findViewById(R.id.add_volume_change_btn_add_change_pop_up_window);
                    ListView volumeListView = addVolumeChangeView.findViewById(R.id.volume_changes_list_view_add_change_pop_up_window);


                    autoCompleteTextViewVolume.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, changesNames));
                    autoCompleteTextViewVolume.setThreshold(1);


                    autoCompleteTextViewVolume.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            for (int i = 0; i < menu.getClassifications().get(holderPosition).getChangesList().size(); i++) {

                                if (menu.getClassifications().get(holderPosition).getChangesList().get(i).getChangeName().equals(autoCompleteTextViewVolume.getText().toString())) {

                                    if (volumeListView.getAdapter() != null) {
                                        volumeListView.setAdapter(null);
                                    }

                                    positionOfChange[0] = i;

                                    if (volumeListView.getAdapter() != null) {
                                        volumeListView.setAdapter(null);
                                    }

                                    volumeListView.setAdapter(new ChangeDetailByTypeAdapter(context, menu.getClassifications().get(holderPosition).getChangesList().get(i)));


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

                            for (int i = 0; i < menu.getClassifications().get(holderPosition).getChangesList().size(); i++) {

                                if (menu.getClassifications().get(holderPosition).getChangesList().get(i).getChangeName().equals(autoCompleteTextViewVolume.getText().toString())) {

                                    if (volumeListView.getAdapter() != null) {
                                        volumeListView.setAdapter(null);
                                    }

                                    positionOfChange[0] = i;

                                    if (volumeListView.getAdapter() != null) {
                                        volumeListView.setAdapter(null);
                                    }

                                    volumeListView.setAdapter(new ChangeDetailByTypeAdapter(context, menu.getClassifications().get(holderPosition).getChangesList().get(i)));


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
                                    menu.getClassifications().get(holderPosition).getChangesList().get(positionOfChange[0]).getChangesByTypesList().add(regularChange);

                                    if (volumeListView.getAdapter() != null) {
                                        volumeListView.setAdapter(null);
                                    }

                                    volumeListView.setAdapter(new ChangeDetailByTypeAdapter(context, menu.getClassifications().get(holderPosition).getChangesList().get(positionOfChange[0])));

                                } else {

                                    createNewChanges(regularChange, autoCompleteTextViewVolume, Changes.ChangesTypesEnum.VOLUME, 0);


                                    if (volumeListView.getAdapter() != null) {
                                        volumeListView.setAdapter(null);
                                    }

                                    volumeListView.setAdapter(new ChangeDetailByTypeAdapter(context, createChange));
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

                    for (int i = 0; i < menu.getClassifications().get(holderPosition).getChangesList().size(); i++) {
                        if (menu.getClassifications().get(holderPosition).getChangesList().get(i).getChangesTypesEnum() == Changes.ChangesTypesEnum.PIZZA) {
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
                        pizzaChangesAddedListView.setAdapter(new PizzaChangesAdapter(context, menu.getClassifications().get(holderPosition).getChangesList().get(changePosition)));
                        pizzaChangesListView.setAdapter(new PizzaChangesAdapter(context, menu.getClassifications().get(holderPosition).getChangesList().get(changePosition), pizzaChangesAddedListView));
                    } else {
                        pizzaChangesAddedListView.setAdapter(new PizzaChangesAdapter(context, createChange));
                        pizzaChangesListView.setAdapter(new PizzaChangesAdapter(context, createChange, pizzaChangesAddedListView));
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
                    menu.getClassifications().get(holderPosition).getChangesList().addAll(changesList);
                    changesList.clear();

                }
                notifyDataSetChanged();
                removeDialog.dismiss();

            }
        });


        removeDialog.create();

        removeDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        removeDialog.show();
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


    private void initializeDishMenuRecyclerView(ClassificationMenuViewHolder holder, int position, Context context) {

        holder.dishRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, true);
        holder.dishRecyclerView.setLayoutManager(layoutManager);
        DishMenuAdapter dishMenuAdapter = new DishMenuAdapter(context, menu.getClassifications().get(position), menu);
        dishMenuAdapters.set(position, dishMenuAdapter);
        holder.dishRecyclerView.setAdapter(dishMenuAdapter);

    }


    private void addInitializeDishMenuRecyclerView(ClassificationMenuViewHolder holder, int position, Context context) {

        holder.dishRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, true);
        holder.dishRecyclerView.setLayoutManager(layoutManager);
        DishMenuAdapter dishMenuAdapter = new DishMenuAdapter(context, menu.getClassifications().get(position), menu);
        dishMenuAdapters.add(dishMenuAdapter);
        holder.dishRecyclerView.setAdapter(dishMenuAdapter);

    }


    @Override
    public int getItemCount() {
        return menu.getClassifications().size();
    }


    public class ClassificationMenuViewHolder extends RecyclerView.ViewHolder {

        TextView headLineTextView;
        Button addDishBtn, deleteClassificationBtn;
        RecyclerView dishRecyclerView;

        public ClassificationMenuViewHolder(@NonNull View itemView) {
            super(itemView);


            headLineTextView = itemView.findViewById(R.id.title_text_view_classification_menu_item);
            addDishBtn = itemView.findViewById(R.id.add_dish_btn_classification_menu_item);
            dishRecyclerView = itemView.findViewById(R.id.dish_recycler_view_classification_menu_dish_item);
            deleteClassificationBtn = itemView.findViewById(R.id.delete_classification_btn_classification_menu_item);

        }
    }
}
