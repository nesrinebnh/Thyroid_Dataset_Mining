package DataStructure;

import javax.swing.plaf.TableHeaderUI;
import java.util.ArrayList;
import java.util.Objects;

public class TableItem {
    private ArrayList<Integer> item;
    private ArrayList<Integer> rules;
    private int support;

    public TableItem(ArrayList<Integer> item , ArrayList<Integer> rules, int support){
        this.item = item;
        this.rules = rules;
        this.support = support;
    }

    public TableItem(){
        item = new ArrayList<>();
        rules = new ArrayList<>();
        this.support = 0;
    }

    public ArrayList<Integer> getItem(){
        return item;
    }

    public int getSupport(){
        return support;
    }

    public ArrayList<Integer> getRules() {
        return rules;
    }

    public void setRules(ArrayList<Integer> rules) {
        this.rules = rules;
        this.support = rules.size();
    }

    public void add_items(ArrayList<Integer> items){
        if(this.item!=null)
            for(Integer i : items)
                if(!this.item.contains(i))
                    this.item.add(i);
    }
    public void add_rule(int r){
        rules.add(r);
    }

    public void update_supp(){
        support = rules.size();
    }
    @Override
    public String toString() {
        return "TableItem{" +
                "item=" + item +
                ", rules=" + rules +
                ", support=" + support +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        //if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableItem tableItem = (TableItem) o;
        ArrayList<Integer> items = tableItem.item;
        for(Integer i1 : items)
                if(!this.item.contains(i1))
                    return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, rules, support);
    }
}

