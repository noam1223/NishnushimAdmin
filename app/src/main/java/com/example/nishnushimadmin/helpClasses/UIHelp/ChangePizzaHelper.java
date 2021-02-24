package com.example.nishnushimadmin.helpClasses.UIHelp;

import com.example.nishnushimadmin.R;

import java.util.ArrayList;
import java.util.List;

public class ChangePizzaHelper {

    List<ChangePizzaType> changePizzaTypes;

    public ChangePizzaHelper() {

        this.changePizzaTypes = new ArrayList<>();


        ChangePizzaType cornPizzaType = new ChangePizzaType(1,"תירס", R.drawable.pizza_change_corn);
        ChangePizzaType blackOlivesPizzaType = new ChangePizzaType(2,"זיתים שחורים", R.drawable.pizza_change_black_olives);
        ChangePizzaType greenOlivesPizzaType = new ChangePizzaType(3,"זיתים ירוקים", R.drawable.pizza_change_green_olives);
        ChangePizzaType onionPizzaType = new ChangePizzaType(4 ,"בצל", R.drawable.pizza_change_onion);
        ChangePizzaType tomatoPizzaType = new ChangePizzaType(5,"עגבניות", R.drawable.pizza_change_tomato);
        ChangePizzaType pinapplePizzaType = new ChangePizzaType(6,"אננס", R.drawable.pizza_change_pineapple);
        ChangePizzaType bulgarianPizzaType = new ChangePizzaType(7,"גבינה בולגרית", R.drawable.pizza_change_bulgarian_cheese);
        ChangePizzaType hotPepperPizzaType = new ChangePizzaType(8,"פלפל חריף", R.drawable.pizza_change_hot_pepper);
        ChangePizzaType cheesePizzaType = new ChangePizzaType(9,"גבינה צהובה", R.drawable.pizza_change_cheese);


        this.changePizzaTypes.add(cornPizzaType);
        this.changePizzaTypes.add(blackOlivesPizzaType);
        this.changePizzaTypes.add(greenOlivesPizzaType);
        this.changePizzaTypes.add(onionPizzaType);
        this.changePizzaTypes.add(tomatoPizzaType);
        this.changePizzaTypes.add(pinapplePizzaType);
        this.changePizzaTypes.add(bulgarianPizzaType);
        this.changePizzaTypes.add(hotPepperPizzaType);
        this.changePizzaTypes.add(cheesePizzaType);

    }

    public List<ChangePizzaType> getChangePizzaTypes() {
        return changePizzaTypes;
    }

}
