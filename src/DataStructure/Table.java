package DataStructure;

import java.util.ArrayList;
import java.util.Objects;

import DataStructure.Thyroid;

public class Table {
    public ArrayList<TableItem> table;
    public Table(){
        table = new ArrayList<>();
    }
    public Table(ArrayList<Thyroid> data){
        table = new ArrayList<>();
        for(int i=1 ; i<Apriori.nb_items;i++){
            ArrayList<Integer> items;
            items = new ArrayList<>();
            items.add(i);
            TableItem it = addItem(items,data);
            //on prendque les items qui existe
            if(it.getSupport()>0)
                table.add(it);
        }
    }

    public int GetSupportOfItem(ArrayList<Integer> item){
        boolean all = true;
        for(TableItem tableItem : this.table) {
            ArrayList<Integer> ti = tableItem.getItem();
            for (int i : item) {
               if (!ti.contains(i))
                    all = false;
            }
            if (all)
                return tableItem.getSupport();
            all = true;
        }
        //itm doent exist
        return -1;
    }
    private TableItem addItem(ArrayList<Integer> i , ArrayList<Thyroid> data){
        ArrayList<Integer>  rules = new ArrayList<>();
        int rule=0;
        for(Thyroid t : data){
            rule++;
            for(int item : i)
                if(/*item == t.getClasse() ||*/ item == t.getDtsh() || item==t.getT3() || item==t.getTys()|| item==t.getTri() || item==t.getTsh())
                    rules.add(rule);
        }
        return new TableItem(i , rules , rules.size());
    }

    private void addItem(TableItem tableItem){
        if(!table.contains(tableItem))
            table.add(tableItem);
    }

    public Table getL( int min_supp){
        Table L = new Table();
        for(TableItem ti : this.table)
            if(ti.getSupport()>=min_supp)
                L.addItem(ti);

        return L;
    }
    private ArrayList<Integer> intersectRules(ArrayList<Integer> rules1 , ArrayList<Integer> rules2){
        ArrayList<Integer> intersect = new ArrayList<>();
        for(Integer rule1: rules1)
            for(Integer rule2 : rules2)
                if(rule1.equals(rule2))
                    if(!intersect.contains(rule1))
                        intersect.add(rule1);

        return intersect;
    }

    public Table generateC(/*Table t,*/int k){
        Table C = new Table();
        ArrayList<TableItem> table = this.table;
        for(int i=0 ; i<table.size()-1; i++){
            TableItem curentItem = table.get(i);
            for(int j=i+1 ; j<table.size(); j++){
                TableItem nextItem = table.get(j);

                TableItem toAdd = new TableItem();
                toAdd.add_items(curentItem.getItem());
                toAdd.add_items(nextItem.getItem());

                if(toAdd.getItem().size()==k){
                    ArrayList<Integer> rules = intersectRules(curentItem.getRules(),nextItem.getRules());
                    toAdd.setRules(rules);
                    if(!C.table.contains(toAdd))
                        C.table.add(toAdd);
                }
            }
        }
        return C;
    }

    public void writeTable(){
        for(TableItem tableItem : table)
            System.out.println(tableItem.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Table table1 = (Table) o;
        for(TableItem ti:this.table)
            if(!table1.table.contains(ti))
                return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(table);
    }
}
