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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nishnushimadmin.R;
import com.example.nishnushimadmin.helpClasses.Classification;
import com.example.nishnushimadmin.helpClasses.Menu;
import com.example.nishnushimadmin.helpClasses.menuChanges.Changes;
import com.example.nishnushimadmin.helpClasses.menuChanges.RegularChange;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DishMenuAdapter extends RecyclerView.Adapter<DishMenuAdapter.DishMenuViewHolder> {

    Context context;
    Classification classification;
    Menu menu;
    Changes createChange;
    List<Changes> changesList = new ArrayList<>();


    public DishMenuAdapter(Context context, Classification classification, Menu menu) {
        this.context = context;
        this.classification = classification;
        this.menu = menu;
    }


    @NonNull
    @Override
    public DishMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_menu_item, parent, false);
        return new DishMenuViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull DishMenuViewHolder holder, int position) {

        holder.dishNameTextView.setText(classification.getDishList().get(position).getName());
        holder.dishDetailsTextView.setText(classification.getDishList().get(position).getDetails());
        holder.priceTextView.setText(String.valueOf(classification.getDishList().get(position).getPrice()));


        holder.addChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preformAddChange(position);
            }
        });


    }

//    private void preformAddChange(final int holderPosition) {
//        String[] changesStrings = {"צפייה בשינויים קיימים", "הוספת מנה לבחירה", "הוספת סיווג לבחירה", "הוספת שינויים לבחירה אחת", "הוספת שינויים לכמות"};
//
//
//        View popUpView = LayoutInflater.from(context).inflate(R.layout.add_classification_change_pop_up_window, null);
//        Dialog removeDialog = new Dialog(context);
//        removeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        removeDialog.setContentView(popUpView);
//
//
//        ViewFlipper changeViewFlipper = popUpView.findViewById(R.id.view_flipper_add_classification_change_pop_up_window);
//        Button addChangeBtn = popUpView.findViewById(R.id.add_classification_btn_add_classification_change_pop_up);
//        Spinner classificationChangeSpinner = popUpView.findViewById(R.id.classification_style_change_add_classification_change_pop_up);
//        classificationChangeSpinner.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, changesStrings));
//
////                ViewStub addDishViewStub = popUpView.findViewById(R.id.change_view_stub_add_classification_change_pop_up);
////                addDishViewStub.setLayoutResource(R.layout.watch_existing_classification_changes_layout);
////                View view = addDishViewStub.inflate();
//
//        View existingChangesView = popUpView.findViewById(R.id.watch_existing_classification_include_change_pop_up);
//        View addDishChangeView = popUpView.findViewById(R.id.add_dish_change_by_dish_include_change_pop_up);
//        View addClassificationChangeView = popUpView.findViewById(R.id._add_classification_cahnge_include_change_pop_up);
//        View addRegularChangeView = popUpView.findViewById(R.id.add_regular_change_include_change_pop_up);
//        View addVolumeChangeView = popUpView.findViewById(R.id.add_change_by_volume_include_change_pop_up);
//
//        changeViewFlipper.setDisplayedChild(0);
//
//        //TODO: ADD CHANGES LOGIC TO MENU, CLASSIFICATION AND DISH
//
//
//        classificationChangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                createChange = new Changes();
//                createChange.setFreeSelection(0);
//                changeViewFlipper.setDisplayedChild(position);
//
//                for (int i = 0; i < changeViewFlipper.getChildCount(); i++) {
//
//
//                    if (i != position) {
//                        changeViewFlipper.getChildAt(i).setVisibility(View.GONE);
//                    } else {
//                        changeViewFlipper.getChildAt(i).setVisibility(View.VISIBLE);
//                    }
//
//                }
//
//
//                if (changeViewFlipper.getCurrentView().getId() == existingChangesView.getId()) {
//
//
//                    Log.i("CHOOSER", Changes.ChangesTypesEnum.CHOICE_ALL.toString());
//                    createChange.setChangesTypesEnum(Changes.ChangesTypesEnum.CHOICE_ALL);
//                    ListView changesListView = existingChangesView.findViewById(R.id.changes_list_view_watch_existing_classification_changes_layout);
////                    changesListView.setAdapter(new RemoveChangeBaseAdapter(context, menu.getClassifications().get(holderPosition).getChangesList()));
//                    changesListView.setAdapter(new RemoveChangeBaseAdapter(context, classification.getDishList().get(holderPosition).getChanges()));
//
//
//                } else if (changeViewFlipper.getCurrentView().getId() == addDishChangeView.getId()) {
//
//                    Log.i("CHOOSER", Changes.ChangesTypesEnum.DISH_CHOICE.toString());
//
//                    createChange = new Changes();
//                    createChange.setFreeSelection(0);
//                    createChange.setChangesTypesEnum(Changes.ChangesTypesEnum.DISH_CHOICE);
//                    Spinner spinnerClassification = addDishChangeView.findViewById(R.id.spinner_classification_dish_add_dish_change_by_dish_layout);
////                    String[] classificationStrings = new String[menu.getClassifications().size()];
//                    String[] classificationStrings = new String[menu.getClassifications().size()];
//
//
//                    for (int i = 0; i < menu.getClassifications().size(); i++) {
//                        classificationStrings[i] = menu.getClassifications().get(i).getClassificationName();
//                    }
//
//
//                    spinnerClassification.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, classificationStrings));
//
//                    EditText amountOfFreeEditText = addDishChangeView.findViewById(R.id.amount_units_to_add_edit_text_add_dish_change_by_dish_layout);
//                    ListView dishesListView = addDishChangeView.findViewById(R.id.dishes_list_view_to_choose_add_dish_change_by_dish_layout);
//                    dishesListView.setAdapter(new DishSelectionAdapter(context, menu.getClassifications().get(0), createChange));
//
//                    amountOfFreeEditText.addTextChangedListener(new TextWatcher() {
//                        @Override
//                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                        }
//
//                        @Override
//                        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                        }
//
//                        @Override
//                        public void afterTextChanged(Editable s) {
//
//                            if (s.length() > 0 && Integer.parseInt(s.toString()) > 0) {
//
//                                createChange.setFreeSelection(Integer.parseInt(s.toString()));
//
//                            } else createChange.setFreeSelection(0);
//
//                        }
//                    });
//
//
//
//                    spinnerClassification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//
//                            createChange = new Changes();
//                            createChange.setFreeSelection(0);
//                            createChange.setChangesTypesEnum(Changes.ChangesTypesEnum.DISH_CHOICE);
//                            amountOfFreeEditText.setText("0");
//
//
//                            boolean isExist = false;
//                            int changePosition = -1;
//
//
//                            for (int i = 0; i < classification.getDishList().get(holderPosition).getChanges().size(); i++) {
//
//                                if (classification.getDishList().get(holderPosition).getChanges().get(i).getChangeName().equals(menu.getClassifications().get(position).getClassificationName())){
//                                    isExist = true;
//                                    changePosition = i;
//                                }
//
//                            }
//
//
//                            if (dishesListView.getAdapter() != null) {
//                                dishesListView.setAdapter(null);
//                            }
//
//
//
//                            if (isExist){
//
//                                dishesListView.setAdapter(new DishSelectionAdapter(context, menu.getClassifications().get(position),
//                                        classification.getDishList().get(holderPosition).getChanges().get(changePosition)));
//
//                            }else {
//                                createChange.setChangeName(menu.getClassifications().get(position).getClassificationName());
//                                dishesListView.setAdapter(new DishSelectionAdapter(context, menu.getClassifications().get(position), createChange));
//                            }
//
//
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parent) {
//                        }
//                    });
//
//
//
//
//                } else if (changeViewFlipper.getCurrentView().getId() == addClassificationChangeView.getId()) {
//
//                    Log.i("CHOOSER", Changes.ChangesTypesEnum.CLASSIFICATION_CHOICE.toString());
//                    createChange = new Changes();
//                    createChange.setChangesTypesEnum(Changes.ChangesTypesEnum.CLASSIFICATION_CHOICE);
//                    String[] classificationStrings = new String[menu.getClassifications().size()];
//
//                    for (int i = 0; i < menu.getClassifications().size(); i++) {
//                        classificationStrings[i] = menu.getClassifications().get(i).getClassificationName();
//                    }
//
//
//                    Spinner classificationSpinner = addClassificationChangeView.findViewById(R.id.spinner_classification_dish_add_classification_change_layout);
//                    EditText amountOfFreeEditText = addClassificationChangeView.findViewById(R.id.amount_units_to_add_edit_text_add_classification_change_layout);
//                    classificationSpinner.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, classificationStrings));
//
//
//                    classificationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                            boolean isChoosed = false;
//                            if (classification.getClassificationName().equals(menu.getClassifications().get(position).getClassificationName())) {
//                                isChoosed = true;
//                            }
//
//
//                            if (!isChoosed) {
//
//                                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
//                                StringBuilder stringBuilder = new StringBuilder();
//                                stringBuilder.append("האם אתה בטוח להוסיף את: ").append(classificationSpinner.getAdapter().getItem(position));
//                                stringBuilder.append("\n");
//                                stringBuilder.append("עם אפשרות בחירה :");
//
//
//                                if (!amountOfFreeEditText.getText().toString().isEmpty()) {
//                                    stringBuilder.append(amountOfFreeEditText.getText().toString());
//                                } else stringBuilder.append(0);
//
//
//                                alertDialog.setTitle("הוספה");
//                                alertDialog.setMessage(stringBuilder.toString());
//                                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "הוסף", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        createChange.setChangesTypesEnum(Changes.ChangesTypesEnum.CLASSIFICATION_CHOICE);
//
//                                        if (!amountOfFreeEditText.getText().toString().isEmpty()) {
//                                            createChange.setFreeSelection(Integer.parseInt(amountOfFreeEditText.getText().toString()));
//                                        }
//
//                                        createChange.setChangeName(menu.getClassifications().get(position).getClassificationName());
//
////                                        createChange.getChangesByTypesList().addAll(menu.getClassifications().get(position).getDishList());
//                                        changesList.add(createChange);
//                                        createChange = new Changes();
//
////                                        for (int i = 0; i < menu.getClassifications().get(position).getDishList().size(); i++) {
////                                            createChange.getChangesByTypesList().add(new ChangesByTypes<>(menu.getClassifications().get(position).getDishList().get(i)));
////                                        }
//
//                                    }
//                                });
//
//
//                                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "לא", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        alertDialog.dismiss();
//                                    }
//                                });
//
//                                alertDialog.show();
//                            } else
//                                Toast.makeText(context, "אין אפשרות לבחור מאותו סיווג", Toast.LENGTH_SHORT).show();
//
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parent) {
//
//                        }
//                    });
//
//
//
//
//                    amountOfFreeEditText.addTextChangedListener(new TextWatcher() {
//                        @Override
//                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                        }
//
//                        @Override
//                        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                        }
//
//                        @Override
//                        public void afterTextChanged(Editable s) {
//
//                            if (s.length() > 0 && Integer.parseInt(s.toString()) > 0) {
//
//                                createChange.setFreeSelection(Integer.parseInt(s.toString()));
//
//                            } else createChange.setFreeSelection(0);
//
//                        }
//                    });
//
//
//
//                    ///////////////REGULAR CHANGE////////////////////
//                    //////////////////////////////////////////////////
//
//                } else if (changeViewFlipper.getCurrentView().getId() == addRegularChangeView.getId()) {
//
//                    Log.i("CHOOSER", Changes.ChangesTypesEnum.ONE_CHOICE.toString());
//                    createChange = new Changes();
//                    createChange.setChangesTypesEnum(Changes.ChangesTypesEnum.ONE_CHOICE);
//                    createChange.setFreeSelection(1);
//
//                    List<String> changesNames = new ArrayList<>();
//                    String[] unitStyleChooser = {"יחידה", "כמות"};
//                    final int[] positionOfChange = {-1};
//
//
////                    for (int i = 0; i < menu.getClassifications().get(holderPosition).getChangesList().size(); i++) {
////                        if (menu.getClassifications().get(holderPosition).getChangesList().get(i).getChangesTypesEnum() == Changes.ChangesTypesEnum.ONE_CHOICE
////                                || menu.getClassifications().get(holderPosition).getChangesList().get(i).getChangesTypesEnum() == Changes.ChangesTypesEnum.MULTIPLE) {
////                            changesNames.add(menu.getClassifications().get(holderPosition).getChangesList().get(i).getChangeName());
////                        }
////                    }
//
//                    for (int i = 0; i < classification.getDishList().get(holderPosition).getChanges().size(); i++) {
//                        if (classification.getDishList().get(holderPosition).getChanges().get(i).getChangesTypesEnum() == Changes.ChangesTypesEnum.ONE_CHOICE
//                                || classification.getDishList().get(holderPosition).getChanges().get(i).getChangesTypesEnum() == Changes.ChangesTypesEnum.MULTIPLE) {
//                            changesNames.add(classification.getDishList().get(holderPosition).getChanges().get(i).getChangeName());
//                        }
//                    }
//
//
//                    AutoCompleteTextView autoCompleteTextViewChangesNames = addRegularChangeView.findViewById(R.id.changes_name_auto_complete_add_change_pop_up);
//                    Spinner unitStyleSpinner = addRegularChangeView.findViewById(R.id.unit_style_spinner_add_chane_pop_up_window);
//                    EditText changeDetailsEditText = addRegularChangeView.findViewById(R.id.change_detail_edit_text_add_change_pop_up_window);
//                    EditText costChangeEditText = addRegularChangeView.findViewById(R.id.change_sum_edit_text_add_change_pop_up_window);
//                    ListView regularChangesListView = addRegularChangeView.findViewById(R.id.regular_changes_list_view_add_change_pop_up_window);
//                    Button addRegularChangeBtn = addRegularChangeView.findViewById(R.id.add_change_btn_add_change_pop_up_window);
//
//                    unitStyleSpinner.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, unitStyleChooser));
//
//
//                    autoCompleteTextViewChangesNames.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, changesNames));
//                    autoCompleteTextViewChangesNames.setThreshold(1);
//
//                    autoCompleteTextViewChangesNames.addTextChangedListener(new TextWatcher() {
//                        @Override
//                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                        }
//
//                        @Override
//                        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                        }
//
//                        @Override
//                        public void afterTextChanged(Editable s) {
//
//                            if (s.length() > 0) {
//                                boolean isExist = false;
//
//                                for (int i = 0; i < autoCompleteTextViewChangesNames.getAdapter().getCount(); i++) {
//
//                                    if (autoCompleteTextViewChangesNames.getAdapter().getItem(i).toString().equals(s.toString())) {
//                                        isExist = true;
//                                    }
//
//                                }
//
//                                unitStyleSpinner.setEnabled(!isExist);
//
//                            }
//
//                        }
//                    });
//
//
//
//                    autoCompleteTextViewChangesNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                            for (int i = 0; i < classification.getDishList().get(holderPosition).getChanges().size(); i++) {
//
//                                if (classification.getDishList().get(holderPosition).getChanges().get(i).getChangeName().equals(autoCompleteTextViewChangesNames.getText().toString())
//                                        && classification.getDishList().get(holderPosition).getChanges().get(i).getChangesTypesEnum() == createChange.getChangesTypesEnum()) {
//
//                                    if (regularChangesListView.getAdapter() != null) {
//                                        regularChangesListView.setAdapter(null);
//                                    }
//
//                                    positionOfChange[0] = i;
//                                    regularChangesListView.setAdapter(new ChangeDetailByTypeAdapter(context, classification.getDishList().get(holderPosition).getChanges().get(i)));
//
//                                    if (classification.getDishList().get(holderPosition).getChanges().get(i).getChangesTypesEnum() == Changes.ChangesTypesEnum.ONE_CHOICE) {
//                                        unitStyleSpinner.setSelection(0);
//                                    } else unitStyleSpinner.setSelection(1);
//
//                                    unitStyleSpinner.setEnabled(false);
//                                }
//
//                            }
//
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parent) {
//
//                        }
//                    });
//
//
//                    unitStyleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                            if (position == 0) {
//                                createChange.setChangesTypesEnum(Changes.ChangesTypesEnum.ONE_CHOICE);
//                                createChange.setFreeSelection(1);
//                            } else if (position == 1) {
//                                createChange.setChangesTypesEnum(Changes.ChangesTypesEnum.MULTIPLE);
//                                createChange.setFreeSelection(-1);
//                            }
//
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parent) {
//
//                        }
//                    });
//
//
//
//                    addRegularChangeBtn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            RegularChange regularChange = new RegularChange();
//
//                            for (int i = 0; i < classification.getDishList().get(holderPosition).getChanges().size(); i++) {
//
//                                if (classification.getDishList().get(holderPosition).getChanges().get(i).getChangeName().equals(autoCompleteTextViewChangesNames.getText().toString())) {
//
//                                    if (regularChangesListView.getAdapter() != null) {
//                                        regularChangesListView.setAdapter(null);
//                                    }
//
//                                    positionOfChange[0] = i;
//                                    regularChangesListView.setAdapter(new ChangeDetailByTypeAdapter(context, classification.getDishList().get(holderPosition).getChanges().get(i)));
//
//                                    if (classification.getDishList().get(holderPosition).getChanges().get(i).getChangesTypesEnum() == Changes.ChangesTypesEnum.ONE_CHOICE) {
//                                        unitStyleSpinner.setSelection(0);
//                                    } else unitStyleSpinner.setSelection(1);
//
//                                    unitStyleSpinner.setEnabled(false);
//                                }
//
//                            }
//
//
//                            String changeDetail = changeDetailsEditText.getText().toString();
//                            String costChange = costChangeEditText.getText().toString();
//
//
//                            if (!changeDetail.isEmpty() && !costChange.isEmpty() && autoCompleteTextViewChangesNames.getText().toString().length() > 0) {
//
//                                regularChange.setChange(changeDetail);
//                                regularChange.setPrice(Integer.parseInt(costChange));
//
//                                if (positionOfChange[0] != -1) {
//                                    classification.getDishList().get(holderPosition).getChanges().get(positionOfChange[0]).getChangesByTypesList().add(regularChange);
//
//                                    if (regularChangesListView.getAdapter() != null) {
//                                        regularChangesListView.setAdapter(null);
//                                    }
//
//                                    regularChangesListView.setAdapter(new ChangeDetailByTypeAdapter(context, classification.getDishList().get(holderPosition).getChanges().get(positionOfChange[0])));
//                                    Log.i("ADD", positionOfChange[0] + "");
//
//                                } else {
//
//                                    int selection = 0;
//                                    if (unitStyleSpinner.getSelectedItemPosition() == 0) {
//                                        createChange.setChangesTypesEnum(Changes.ChangesTypesEnum.ONE_CHOICE);
//                                        selection = 1;
//                                    } else {
//                                        createChange.setChangesTypesEnum(Changes.ChangesTypesEnum.MULTIPLE);
//                                        selection = -1;
//                                    }
//
//                                    createNewChanges(regularChange, autoCompleteTextViewChangesNames, createChange.getChangesTypesEnum(), selection);
//
//
//                                    if (regularChangesListView.getAdapter() != null) {
//                                        regularChangesListView.setAdapter(null);
//                                    }
//
//                                    regularChangesListView.setAdapter(new ChangeDetailByTypeAdapter(context, createChange));
//                                    Log.i("HERE", autoCompleteTextViewChangesNames.getText().toString());
//                                }
//
//
//                            }
//
//                        }
//                    });
//
//
//                } else if (changeViewFlipper.getCurrentView().getId() == addVolumeChangeView.getId()) {
//
//                    Log.i("CHOOSER", Changes.ChangesTypesEnum.VOLUME.toString());
//                    createChange = new Changes();
//                    createChange.setFreeSelection(1);
//                    createChange.setChangesTypesEnum(Changes.ChangesTypesEnum.VOLUME);
//
//                    RegularChange regularChange = new RegularChange();
//                    List<String> changesNames = new ArrayList<>();
//                    int[] positionOfChange = {-1};
//
//
//                    for (int i = 0; i < classification.getDishList().get(holderPosition).getChanges().size(); i++) {
//                        if (classification.getDishList().get(holderPosition).getChanges().get(i).getChangesTypesEnum() == Changes.ChangesTypesEnum.VOLUME) {
//                            changesNames.add(classification.getDishList().get(holderPosition).getChanges().get(i).getChangeName());
//                        }
//                    }
//
//
//                    AutoCompleteTextView autoCompleteTextViewVolume = addVolumeChangeView.findViewById(R.id.classification_name_auto_complete_add_change_by_volume_layout);
//                    EditText changeNameEditText = addVolumeChangeView.findViewById(R.id.change_name_edit_text_add_change_by_volume_layout);
//                    EditText costChangeEditText = addVolumeChangeView.findViewById(R.id.cost_change_edit_text_add_change_by_volume_layout);
//                    EditText unitChangeNameEditText = addVolumeChangeView.findViewById(R.id.units_change_edit_text_add_change_by_volume_layout);
//                    Button addVolumeChangeBtn = addVolumeChangeView.findViewById(R.id.add_volume_change_btn_add_change_pop_up_window);
//                    ListView volumeListView = addVolumeChangeView.findViewById(R.id.volume_changes_list_view_add_change_pop_up_window);
//
//
//                    autoCompleteTextViewVolume.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, changesNames));
//                    autoCompleteTextViewVolume.setThreshold(1);
//
//
//                    autoCompleteTextViewVolume.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                            for (int i = 0; i < classification.getDishList().get(holderPosition).getChanges().size(); i++) {
//
//                                if (classification.getDishList().get(holderPosition).getChanges().get(i).getChangeName().equals(autoCompleteTextViewVolume.getText().toString())) {
//
//                                    if (volumeListView.getAdapter() != null) {
//                                        volumeListView.setAdapter(null);
//                                    }
//
//                                    positionOfChange[0] = i;
//
//                                    if (volumeListView.getAdapter() != null) {
//                                        volumeListView.setAdapter(null);
//                                    }
//
//                                    volumeListView.setAdapter(new ChangeDetailByTypeAdapter(context, classification.getDishList().get(holderPosition).getChanges().get(i)));
//                                    break;
//
//                                }
//
//                            }
//
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parent) {
//
//                        }
//                    });
//
//
//
//
//                    addVolumeChangeBtn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            RegularChange regularChange = new RegularChange();
//
//                            for (int i = 0; i < classification.getDishList().get(holderPosition).getChanges().size(); i++) {
//
//                                if (classification.getDishList().get(holderPosition).getChanges().get(i).getChangeName().equals(autoCompleteTextViewVolume.getText().toString())) {
//
//                                    if (volumeListView.getAdapter() != null) {
//                                        volumeListView.setAdapter(null);
//                                    }
//
//                                    positionOfChange[0] = i;
//
//                                    if (volumeListView.getAdapter() != null) {
//                                        volumeListView.setAdapter(null);
//                                    }
//
//
//                                    volumeListView.setAdapter(new ChangeDetailByTypeAdapter(context, classification.getDishList().get(holderPosition).getChanges().get(i)));
//                                    break;
//                                }
//                            }
//
//
//
//
//                            String changeName = changeNameEditText.getText().toString();
//                            String cost = costChangeEditText.getText().toString();
//                            String unit = unitChangeNameEditText.getText().toString();
//
//
//                            if (!changeName.isEmpty() && !cost.isEmpty()) {
//
//                                StringBuilder stringBuilder = new StringBuilder();
//
//                                stringBuilder.append(changeName);
//
//                                if (!unit.isEmpty()) {
//                                    stringBuilder.append("-").append(unit);
//                                }
//
//                                regularChange.setChange(changeName);
//                                regularChange.setPrice(Integer.parseInt(cost));
//
//
//                                if (positionOfChange[0] != -1) {
//                                    classification.getDishList().get(holderPosition).getChanges().get(positionOfChange[0]).getChangesByTypesList().add(regularChange);
//
//                                    if (volumeListView.getAdapter() != null) {
//                                        volumeListView.setAdapter(null);
//                                    }
//
//                                    volumeListView.setAdapter(new ChangeDetailByTypeAdapter(context, classification.getDishList().get(holderPosition).getChanges().get(positionOfChange[0])));
//                                    Log.i("ADD", positionOfChange[0] + "");
//
//                                } else {
//
//                                    createNewChanges(regularChange, autoCompleteTextViewVolume, Changes.ChangesTypesEnum.VOLUME, 0);
//
//
//                                    if (volumeListView.getAdapter() != null) {
//                                        volumeListView.setAdapter(null);
//                                    }
//
//                                    volumeListView.setAdapter(new ChangeDetailByTypeAdapter(context, createChange));
//                                    Log.i("HERE", autoCompleteTextViewVolume.getText().toString());
//                                }
//
//                            }
//
//
//                        }
//                    });
//
//
//                }
//
//            }
//
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//
//            }
//        });
//
//
//
//        addChangeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //TODO: CHECK THE CLOSE WINDOW AFTER CLICK
////                Log.i("CHANGE - CLICKED", createChange.getChangesTypesEnum().toString());
//
//
//
//                if (changesList.size() > 0) {
////                    menu.getClassifications().get(holderPosition).getChangesList().add(createChange);
//
//                    if (createChange.getChangesByTypesList().size() > 0)
//                        changesList.add(createChange);
//
//
////                    menu.getClassifications().get(holderPosition).getChangesList().addAll(changesList);
//                    classification.getDishList().get(holderPosition).getChanges().addAll(changesList);
//                    changesList.clear();
//
//                }
//                removeDialog.dismiss();
//
//            }
//        });
//
//
//        removeDialog.create();
//
//        removeDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//
//        removeDialog.show();
//    }

    private void preformAddChange(final int holderPosition) {
        String[] changesStrings = {"צפייה בשינויים קיימים", "הוספת מנה לבחירה", "הוספת סיווג לבחירה", "הוספת שינויים לבחירה אחת", "הוספת שינויים לכמות"};


        View popUpView = LayoutInflater.from(context).inflate(R.layout.add_classification_change_pop_up_window, null);
        Dialog removeDialog = new Dialog(context);
        removeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        removeDialog.setContentView(popUpView);


        ViewFlipper changeViewFlipper = popUpView.findViewById(R.id.view_flipper_add_classification_change_pop_up_window);
        Button addChangeBtn = popUpView.findViewById(R.id.add_classification_btn_add_classification_change_pop_up);
        Spinner classificationChangeSpinner = popUpView.findViewById(R.id.classification_style_change_add_classification_change_pop_up);
        classificationChangeSpinner.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, changesStrings));


        View existingChangesView = popUpView.findViewById(R.id.watch_existing_classification_include_change_pop_up);
        View addDishChangeView = popUpView.findViewById(R.id.add_dish_change_by_dish_include_change_pop_up);
        View addClassificationChangeView = popUpView.findViewById(R.id._add_classification_cahnge_include_change_pop_up);
        View addRegularChangeView = popUpView.findViewById(R.id.add_regular_change_include_change_pop_up);
        View addVolumeChangeView = popUpView.findViewById(R.id.add_change_by_volume_include_change_pop_up);

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
                    changesListView.setAdapter(new RemoveChangeBaseAdapter(context, classification.getDishList().get(holderPosition).getChanges()));


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

                                        if (!amountOfFreeEditText.getText().toString().isEmpty()){
                                            createChange.setFreeSelection(Integer.parseInt(amountOfFreeEditText.getText().toString()));
                                        }

                                        createChange.setChangeName(menu.getClassifications().get(position).getClassificationName());
                                        changesList.add(createChange);

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

                            for (int i = 0; i < classification.getChangesList().size(); i++) {

                                if (classification.getChangesList().get(i).getChangeName().equals(autoCompleteTextViewChangesNames.getText().toString())) {

                                    if (regularChangesListView.getAdapter() != null) {
                                        regularChangesListView.setAdapter(null);
                                    }

                                    positionOfChange[0] = i;
                                    regularChangesListView.setAdapter(new ChangeDetailByTypeAdapter(context, classification.getChangesList().get(i)));

                                    if (classification.getChangesList().get(i).getChangesTypesEnum() == Changes.ChangesTypesEnum.ONE_CHOICE) {
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
                                    classification.getChangesList().get(positionOfChange[0]).getChangesByTypesList().add(regularChange);

                                    if (regularChangesListView.getAdapter() != null) {
                                        regularChangesListView.setAdapter(null);
                                    }

                                    regularChangesListView.setAdapter(new ChangeDetailByTypeAdapter(context, classification.getChangesList().get(positionOfChange[0])));
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


                    for (int i = 0; i < classification.getChangesList().size(); i++) {
                        if (classification.getChangesList().get(i).getChangesTypesEnum() == Changes.ChangesTypesEnum.VOLUME) {
                            changesNames.add(classification.getChangesList().get(i).getChangeName());
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

                            for (int i = 0; i < classification.getChangesList().size(); i++) {

                                if (classification.getChangesList().get(i).getChangeName().equals(autoCompleteTextViewVolume.getText().toString())) {

                                    if (volumeListView.getAdapter() != null) {
                                        volumeListView.setAdapter(null);
                                    }

                                    positionOfChange[0] = i;

                                    if (volumeListView.getAdapter() != null) {
                                        volumeListView.setAdapter(null);
                                    }

                                    volumeListView.setAdapter(new ChangeDetailByTypeAdapter(context, classification.getChangesList().get(i)));


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

                            for (int i = 0; i < classification.getChangesList().size(); i++) {

                                if (classification.getChangesList().get(i).getChangeName().equals(autoCompleteTextViewVolume.getText().toString())) {

                                    if (volumeListView.getAdapter() != null) {
                                        volumeListView.setAdapter(null);
                                    }

                                    positionOfChange[0] = i;

                                    if (volumeListView.getAdapter() != null) {
                                        volumeListView.setAdapter(null);
                                    }

                                    volumeListView.setAdapter(new ChangeDetailByTypeAdapter(context, classification.getChangesList().get(i)));


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
                                    classification.getChangesList().get(positionOfChange[0]).getChangesByTypesList().add(regularChange);

                                    if (volumeListView.getAdapter() != null) {
                                        volumeListView.setAdapter(null);
                                    }

                                    volumeListView.setAdapter(new ChangeDetailByTypeAdapter(context, classification.getChangesList().get(positionOfChange[0])));

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
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });





        addChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: CHECK THE CLOSE WINDOW AFTER CLICK
                Log.i("CHANGE - CLICKED", createChange.getChangesTypesEnum().toString());


                if (createChange.getChangesByTypesList().size() > 0 || changesList.size() > 0) {
                    changesList.add(createChange);
                    classification.getDishList().get(holderPosition).getChanges().addAll(changesList);
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




    @Override
    public int getItemCount() {
        return classification.getDishList().size();
    }


    public class DishMenuViewHolder extends RecyclerView.ViewHolder {

        TextView dishNameTextView, dishDetailsTextView, priceTextView;
        ImageView dishImageView;
        Button addChangeBtn;

        public DishMenuViewHolder(@NonNull View itemView) {
            super(itemView);

            dishNameTextView = itemView.findViewById(R.id.dish_name_text_view_dish_menu_item);
            dishDetailsTextView = itemView.findViewById(R.id.dish_detail_text_view_dish_menu_item);
            priceTextView = itemView.findViewById(R.id.dish_price_text_view_dish_menu_item);

            dishImageView = itemView.findViewById(R.id.dish_image_view_dish_menu_item);


            addChangeBtn = itemView.findViewById(R.id.add_change_btn_dish_menu_item);

        }
    }
}
